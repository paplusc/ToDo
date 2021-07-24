package com.pcremades.todo.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.pcremades.todo.ToDo;
import com.pcremades.todo.service.ToDoService;
import com.pcremades.todo.service.ToDoServiceH2Impl;

public class ToDoControllerTest {

  private final ToDoService service = mock(ToDoServiceH2Impl.class);
  private final TodoController controller = new TodoController(service);

  @Test
  void createToDoWithSuccess() {
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
}
