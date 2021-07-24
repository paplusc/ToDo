package com.pcremades.todo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.pcremades.todo.domain.ToDoEntity;

public class ToDo {

  private final Integer id;
  @NotNull
  @Positive
  private final Integer userId;
  @NotNull
  @Size(max = 250, message
             = "Title can not be longer than 250 characters")
  private final String title;
  @NotNull
  private final Boolean completed;

  public ToDo(Integer id, Integer userId, String title, Boolean completed) {
    this.id = id;
    this.userId = userId;
    this.title = title;
    this.completed = completed;
  }

  public static ToDo fromEntity(ToDoEntity entity) {
    return new ToDo(entity.getId(), entity.getUserId(), entity.getTitle(), entity.getCompleted());
  }

  @JsonGetter("id")
  public Integer id() {
    return id;
  }

  @JsonGetter("userId")
  public Integer userId() {
    return userId;
  }

  @JsonGetter("title")
  public String title() {
    return title;
  }

  @JsonGetter("completed")
  public Boolean completed() {
    return completed;
  }

  @Override
  public String toString() {
    return "ToDo{" +
               "id=" + id +
               ", userId=" + userId +
               ", title='" + title + '\'' +
               ", completed=" + completed +
               '}';
  }
}
