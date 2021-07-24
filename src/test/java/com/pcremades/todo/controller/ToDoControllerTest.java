package com.pcremades.todo.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.pcremades.todo.ToDo;
import com.pcremades.todo.ToDoException;
import com.pcremades.todo.domain.ToDoEntity;
import com.pcremades.todo.service.ToDoService;
import com.pcremades.todo.service.ToDoServiceH2Impl;

public class ToDoControllerTest {

  private final ToDoService service = mock(ToDoServiceH2Impl.class);
  private final TodoController controller = new TodoController(service);

  @Test
  void createToDoThenSuccess() {
    // Arrange
    when(service.create(any())).thenReturn(new ToDo(1, 123, "test title", false));

    // Act
    final ToDo actual = controller.createToDo(new ToDo(null, 123, "test title", false));

    // Assert
    assertThat(actual.id(), is(1));
    assertThat(actual.userId(), is(123));
    assertThat(actual.title(), is("test title"));
    assertThat(actual.completed(), is(false));
  }

  @Nested
  class DeleteToDo {
    @Test
    void thenSuccess() {
      // Arrange
      doNothing().when(service).delete(anyInt());

      // Act
      final String response = controller.deleteToDo(1);

      // Assert
      assertThat(response, is("Deleted with success!"));
    }

    @Test
    void thenNotFoundException() {
      // Arrange
      doThrow(new ToDoException(ToDoEntity.class, "id", "1")).when(service).delete(anyInt());

      // Act
      try {
        controller.deleteToDo(1);
      } catch (ToDoException e) {
        // Assert
        assertThat(e.getMessage(), is("ToDoEntity was not found for parameters {id=1}"));
      }
    }
  }

  @Nested
  class ListAllToDo {
    @Test
    void thenSuccess() {
      // Arrange
      final List<ToDo> expected = List.of(new ToDo(1,123, "first", true),
                 new ToDo(2, 123, "second", false));
      when(service.getAll()).thenReturn(expected);

      // Act
      final Collection<ToDo> actual = controller.listAllToDo(null);

      // Assert
      assertThat(actual.containsAll(expected), is(true));
    }

    @Test
    void whenCompletedThenSuccess() {
      // Arrange
      final List<ToDo> expected = List.of(new ToDo(1,123, "first", true),
                 new ToDo(2, 123, "second", true));
      when(service.getByStatus(true)).thenReturn(expected);

      // Act
      final Collection<ToDo> actual = controller.listAllToDo(true);

      // Assert
      assertThat(actual.containsAll(expected), is(true));
    }
  }

  @Nested
  class ListAllToDoByUserId {
    @Test
    void thenSuccess() {
      // Arrange
      final List<ToDo> expected = List.of(new ToDo(1,123, "first", true),
                 new ToDo(2, 123, "second", true));
      when(service.getByUserId(anyInt())).thenReturn(expected);

      // Act
      final Collection<ToDo> actual = controller.listAllToDoByUser(123);

      // Assert
      assertThat(actual.containsAll(expected), is(true));
    }

    @Test
    void ThenNotFoundException() {
      // Arrange
      when(service.getByUserId(anyInt())).thenThrow(new ToDoException(ToDoEntity.class, "userId", String.valueOf(-123)));

      // Act
      try {
        controller.listAllToDoByUser(-123);
      } catch (ToDoException e) {
        // Assert
        assertThat(e.getMessage(), is("ToDoEntity was not found for parameters {userId=-123}"));
      }
    }
  }

  @Test
  void getStatsThenSuccess() {
    // Arrange
    when(service.getStats()).thenReturn(Map.of(true, 23L, false, 44L));

    // Act
    final Map<Boolean, Long> actual = controller.getStats();

    // Assert
    assertThat(actual.get(true), is(23L));
    assertThat(actual.get(false), is(44L));
  }

  @Test
  void getTitlesThenSuccess() {
    // Arrange
    when(service.getTitles()).thenReturn(List.of("title1", "title12", "title123"));

    // Act
    final List<String> actual = controller.listAllToDoTitles();

    // Assert
    assertThat(actual.get(0), is("title1"));
    assertThat(actual.get(1), is("title12"));
    assertThat(actual.get(2), is("title123"));
  }
}
