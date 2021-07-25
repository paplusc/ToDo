package com.pcremades.todo.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.pcremades.todo.DataLoader;
import com.pcremades.todo.ToDo;
import com.pcremades.todo.exception.ToDoException;
import com.pcremades.todo.domain.ToDoEntity;

@Service
@Profile("dev")
public class ToDoServiceImpl implements ToDoService {

  private final List<ToDo> toDos;

  @Autowired
  public ToDoServiceImpl(@Value("${todo.data.url:https://jsonplaceholder.typicode.com/todos}") String url) {
    toDos = DataLoader.loadToDos(url);
  }

  @Override
  public ToDo create(final ToDo item) {
    item.generateId();
    toDos.add(item);
    return item;
  }

  @Override
  public void delete(final int id) {
    boolean removed = toDos.removeIf(todo -> todo.id() == id);
    if (!removed) {
      throw new ToDoException(ToDoEntity.class, "id", String.valueOf(id));
    }
  }

  @Override
  public Collection<ToDo> getAll() {
    return toDos;
  }

  @Override
  public Collection<ToDo> getByStatus(final boolean isCompleted) {
    return toDos.stream().filter(todo -> todo.completed() == isCompleted).collect(Collectors.toList());
  }

  @Override
  public Collection<ToDo> getByUserId(final int userId) {
    if (userId <= 0) {
      throw new ToDoException(ToDoEntity.class, "userId", String.valueOf(userId));
    }
    return toDos.stream().filter(todo -> todo.userId() == userId).collect(Collectors.toList());
  }

  @Override
  public Map<Boolean, Long> getStats() {
    final long totalCompleted = getByStatus(true).size();
    final long totalUncompleted = getByStatus(false).size();
    return Map.of(true, totalCompleted, false, totalUncompleted);
  }

  @Override
  public List<String> getTitles() {
    return toDos.stream().map(ToDo::title).sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
  }
}
