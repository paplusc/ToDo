package com.pcremades.todo.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.pcremades.todo.ToDo;
import com.pcremades.todo.exception.ToDoException;

public class ToDoServiceImplTest {
  private final String url = "todos.json";
  private final ToDoService service = new ToDoServiceImpl(url);

  @Test
  void createThenSuccess() {
    // Arrange
    // Act
    final ToDo actual = service.create(new ToDo(null, 123, "title", false));

    // Assert
    assertThat(actual.id(), greaterThan(0));
    assertThat(actual.userId(), is(123));
    assertThat(actual.title(), is("title"));
    assertThat(actual.completed(), is(false));
  }

  @Nested
  class Delete {
    @Test
    void thenSuccess() {
      // Arrange
      // Act
      service.delete(1);

      // Assert
      // No exceptions produced
    }

    @Test
    void thenNotFoundException() {
      // Arrange
      // Act
      try {
        service.delete(0);
      } catch (ToDoException e) {
        // Assert
        assertThat(e.getMessage(), is("ToDoEntity was not found for parameters {id=0}"));
      }
    }
  }

  @Test
  void getAllThenSuccess() {
    // Arrange
    // Act
    final Collection<ToDo> actual = service.getAll();

    // Assert
    assertThat(actual.size(), is(200));
    assertThat(actual.stream().findAny().get(), instanceOf(ToDo.class));
  }

  @Test
  void getAllByStatusWhenCompletedThenSuccess() {
    // Arrange
    // Act
    final Collection<ToDo> actual = service.getByStatus(true);

    // Assert
    assertThat(actual.stream().findAny().get().completed(), is(true));
  }

  @Nested
  class GetByUserId {
    @Test
    void thenSuccess() {
      // Arrange
      // Act
      final Collection<ToDo> actual = service.getByUserId(1);

      // Assert
      assertThat(actual.size(), is(20));
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
    // Act
    final Map<Boolean, Long> actual = service.getStats();

    // Assert
    assertThat(actual.get(true), is(90L));
    assertThat(actual.get(false), is(110L));
  }
  @Test
  void getTitlesThenSuccess() {
    // Arrange
    // Act
    final List<String> actual = service.getTitles();

    // Assert
    assertThat(actual.get(0), is("qui sit non"));
    assertThat(actual.get(1), is("sed et ea eum"));
    assertThat(actual.get(2), is("totam quia non"));
  }
}
