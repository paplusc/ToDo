package com.pcremades.todo.controller;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pcremades.todo.ToDo;
import com.pcremades.todo.service.ToDoService;

@RestController
public class TodoController {

  private final ToDoService toDoService;

  @Autowired
  public TodoController(ToDoService toDoService) {
    this.toDoService = toDoService;
  }

  @PostMapping("/odilo/tests/2")
  public ToDo createToDo(@RequestBody @Valid ToDo request) {
    return toDoService.create(request);
  }

  @DeleteMapping("/odilo/tests/2/{toDoId}")
  public String deleteToDo(@PathVariable("toDoId") final Integer toDoId) {
    toDoService.delete(toDoId);
    return "Deleted with success!";
  }

  @GetMapping("/odilo/tests/2")
  public Collection<ToDo> listAllToDo(@RequestParam(required = false) final Boolean completed) {
    if (completed == null) {
      return toDoService.getAll();
    } else {
      return toDoService.getByStatus(completed);
    }
  }

  @GetMapping("/odilo/tests/2/user/{userId}")
  public Collection<ToDo> listAllToDoByUser(@PathVariable("userId") Integer userId) {
    return toDoService.getByUserId(userId);
  }

  @GetMapping("/odilo/tests/2/stats")
  public Map<Boolean, Long> getStats() {
    return toDoService.getStats();
  }

  @GetMapping("/odilo/tests/2/titles")
  public Collection<String> listAllToDoTitles() {
    return toDoService.getTitles();
  }
}
