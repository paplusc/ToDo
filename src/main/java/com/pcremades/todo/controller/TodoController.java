package com.pcremades.todo.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pcremades.todo.ToDo;
import com.pcremades.todo.service.ToDoService;

@RestController
@RequestMapping("/odilo/tests/2")
public class TodoController {

  private final ToDoService toDoService;

  @Autowired
  public TodoController(ToDoService toDoService) {
    this.toDoService = toDoService;
  }

  @PostMapping
  public ToDo createToDo(@RequestBody @Valid final ToDo request) {
    return toDoService.create(request);
  }

  @DeleteMapping("/{toDoId}")
  public String deleteToDo(@PathVariable("toDoId") final Integer toDoId) {
    toDoService.delete(toDoId);
    return "Deleted with success!";
  }

  @GetMapping
  public Collection<ToDo> listAllToDo(@RequestParam(required = false) final Boolean completed) {
    if (completed == null) {
      return toDoService.getAll();
    } else {
      return toDoService.getByStatus(completed);
    }
  }

  @GetMapping("/user/{userId}")
  public Collection<ToDo> listAllToDoByUser(@PathVariable("userId") final Integer userId) {
    return toDoService.getByUserId(userId);
  }

  @GetMapping("/stats")
  public Map<Boolean, Long> getStats() {
    return toDoService.getStats();
  }

  @GetMapping("/titles")
  public List<String> listAllToDoTitles() {
    return toDoService.getTitles();
  }
}
