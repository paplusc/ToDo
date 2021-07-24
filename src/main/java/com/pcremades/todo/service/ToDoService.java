package com.pcremades.todo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.pcremades.todo.ToDo;

public interface ToDoService {
  ToDo create(ToDo item);
  void delete(Integer id);
  Collection<ToDo> getAll();
  Collection<ToDo> getByStatus(boolean isCompleted);
  Collection<ToDo> getByUserId(int userId);
  Map<Boolean, Long> getStats();
  List<String> getTitles();
}
