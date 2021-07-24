package com.pcremades.todo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pcremades.todo.ToDo;

@Entity
@Table(name = "todos")
public class ToDoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "USERID")
  private Integer userId;
  private String title;
  private Boolean completed;

  public ToDoEntity() {}

  public ToDoEntity(Integer id, Integer userId, String title, Boolean completed) {
    this.id = id;
    this.userId = userId;
    this.title = title;
    this.completed = completed;
  }

  public static ToDoEntity fromToDo(ToDo toDo) {
    return new ToDoEntity(toDo.id(), toDo.userId(), toDo.title(), toDo.completed());
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }
}
