package com.pcremades.todo;

import java.util.Objects;

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
    return new ToDo(entity.id(), entity.userId(), entity.title(), entity.completed());
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ToDo)) return false;

    ToDo toDo = (ToDo) o;

    if (!Objects.equals(id, toDo.id)) return false;
    if (!userId.equals(toDo.userId)) return false;
    if (!title.equals(toDo.title)) return false;
    return completed.equals(toDo.completed);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + userId.hashCode();
    result = 31 * result + title.hashCode();
    result = 31 * result + completed.hashCode();
    return result;
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
