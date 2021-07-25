package com.pcremades.todo.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import com.pcremades.todo.ToDo;
import com.pcremades.todo.exception.ToDoException;
import com.pcremades.todo.domain.ToDoEntity;
import com.pcremades.todo.domain.ToDoRepository;

public class ToDoServiceH2ImplTest {
  private final ToDoRepository repository = mock(ToDoRepository.class);
  private final ToDoService service = new ToDoServiceH2Impl(repository);

  @Test
  void createThenSuccess() {
    // Arrange
    when(repository.save(any())).thenReturn(new ToDoEntity(1, 123, "title", false));

    // Act
    final ToDo actual = service.create(new ToDo(null, 123, "title", false));

    // Assert
    assertThat(actual.id(), is(1));
    assertThat(actual.userId(), is(123));
    assertThat(actual.title(), is("title"));
    assertThat(actual.completed(), is(false));
  }

  @Nested
  class Delete {
    @Test
    void thenSuccess() {
      // Arrange
      doNothing().when(repository).deleteById(anyInt());

      // Act
      service.delete(1);

      // Assert
      // No exceptions produced
    }

    @Test
    void thenNotFoundException() {
      // Arrange
      doThrow(new EmptyResultDataAccessException(1)).when(repository).deleteById(anyInt());

      // Act
      try {
        service.delete(1);
      } catch (ToDoException e) {
        // Assert
        assertThat(e.getMessage(), is("ToDoEntity was not found for parameters {id=1}"));
      }
    }
  }

  @Test
  void getAllThenSuccess() {
    // Arrange
    final List<ToDoEntity> expected = List.of(new ToDoEntity(1,123, "first", true),
               new ToDoEntity(2, 123, "second", false));
    when(repository.findAll()).thenReturn(expected);

    // Act
    final Collection<ToDo> actual = service.getAll();

    // Assert
    assertThat(actual.contains(ToDo.fromEntity(expected.get(0))), is(true));
    assertThat(actual.contains(ToDo.fromEntity(expected.get(1))), is(true));
  }

  @Test
  void getAllByStatusWhenCompletedThenSuccess() {
    // Arrange
    final List<ToDoEntity> expected = List.of(new ToDoEntity(1,123, "first", true),
               new ToDoEntity(2, 123, "second", true));
    when(repository.findByStatus(anyBoolean())).thenReturn(expected);

    // Act
    final Collection<ToDo> actual = service.getByStatus(true);

    // Assert
    assertThat(actual.contains(ToDo.fromEntity(expected.get(0))), is(true));
    assertThat(actual.contains(ToDo.fromEntity(expected.get(1))), is(true));
  }

  @Nested
  class GetByUserId {
    @Test
    void thenSuccess() {
      // Arrange
      final List<ToDoEntity> expected = List.of(new ToDoEntity(1,123, "first", true),
                 new ToDoEntity(2, 123, "second", true));
      when(repository.findAllByUserId(anyInt())).thenReturn(expected);

      // Act
      final Collection<ToDo> actual = service.getByUserId(123);

      // Assert
      assertThat(actual.contains(ToDo.fromEntity(expected.get(0))), is(true));
      assertThat(actual.contains(ToDo.fromEntity(expected.get(1))), is(true));
    }

    @Test
    void ThenNotFoundException() {
      // Arrange
      // Act
      try {
        service.getByUserId(-123);
      } catch (ToDoException e) {
        // Assert
        assertThat(e.getMessage(), is("ToDoEntity was not found for parameters {userId=-123}"));
      }
    }
  }

  @Test
  void getStatsThenSuccess() {
    // Arrange
    final List<ToDoEntity> completed = List.of(new ToDoEntity(1,123, "first", true),
               new ToDoEntity(2, 123, "second", true));
    final List<ToDoEntity> uncompleted = List.of(new ToDoEntity(1,123, "third", false),
               new ToDoEntity(2, 123, "forth", false),
               new ToDoEntity(3, 123, "fifth", false));
    when(repository.findByStatus(true)).thenReturn(completed);
    when(repository.findByStatus(false)).thenReturn(uncompleted);

    // Act
    final Map<Boolean, Long> actual = service.getStats();

    // Assert
    assertThat(actual.get(true), is(2L));
    assertThat(actual.get(false), is(3L));
  }
  @Test
  void getTitlesThenSuccess() {
    // Arrange
    when(repository.findAllTitles()).thenReturn(List.of("title134", "title1", "title23"));

    // Act
    final List<String> actual = service.getTitles();

    // Assert
    assertThat(actual.get(0), is("title1"));
    assertThat(actual.get(1), is("title23"));
    assertThat(actual.get(2), is("title134"));
  }
}
