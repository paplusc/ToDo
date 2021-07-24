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

  public Integer id() {
    return id;
  }

  public Integer userId() {
    return userId;
  }

  public String title() {
    return title;
  }

  public Boolean completed() {
    return completed;
  }
}
