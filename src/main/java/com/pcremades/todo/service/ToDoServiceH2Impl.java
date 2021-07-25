package com.pcremades.todo.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pcremades.todo.ToDo;
import com.pcremades.todo.exception.ToDoException;
import com.pcremades.todo.domain.ToDoEntity;
import com.pcremades.todo.domain.ToDoRepository;

@Service
@Profile("!dev")
public class ToDoServiceH2Impl implements ToDoService {

  private final ToDoRepository repository;

  @Autowired
  public ToDoServiceH2Impl(ToDoRepository repository) {
    this.repository = repository;
  }

  @Override
  public ToDo create(final ToDo item) {
    final ToDoEntity response = repository.save(ToDoEntity.fromToDo(item));
    return ToDo.fromEntity(response);
  }

  @Override
  public void delete(final int id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ToDoException(ToDoEntity.class, "id", String.valueOf(id));
    }
  }

  @Override
  public Collection<ToDo> getAll() {
    final List<ToDoEntity> toDoEntityList = repository.findAll();
    return toDoEntityList.stream()
               .map(ToDo::fromEntity)
               .collect(Collectors.toList());
  }

  @Override
  public Collection<ToDo> getByStatus(final boolean isCompleted) {
    final Collection<ToDoEntity> toDoEntityList = repository.findByStatus(isCompleted);
    return toDoEntityList.stream()
               .map(ToDo::fromEntity)
               .collect(Collectors.toList());
  }

  @Override
  public Collection<ToDo> getByUserId(final int userId) {
    if (userId <= 0) {
      throw new ToDoException(ToDoEntity.class, "userId", String.valueOf(userId));
    }
    final Collection<ToDoEntity> toDoEntityList = repository.findAllByUserId(userId);
    return toDoEntityList.stream()
               .map(ToDo::fromEntity)
               .collect(Collectors.toList());
  }

  @Override
  public Map<Boolean, Long> getStats() {
    final long totalCompleted = repository.findByStatus(true).size();
    final long totalUncompleted = repository.findByStatus(false).size();
    return Map.of(true, totalCompleted, false, totalUncompleted);
  }

  @Override
  public List<String> getTitles() {
    return repository.findAllTitles().stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
  }
}
