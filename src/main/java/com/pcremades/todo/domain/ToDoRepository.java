package com.pcremades.todo.domain;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ToDoRepository extends JpaRepository<ToDoEntity, Integer> {

  @Query(value = "SELECT * FROM todos t WHERE t.completed = ?1", nativeQuery = true)
  Collection<ToDoEntity> findByStatus(boolean completed);

  @Query(value = "SELECT * FROM todos t WHERE t.userId = ?1", nativeQuery = true)
  Collection<ToDoEntity> findAllByUserId(Integer userId);

  @Query(value = "SELECT t.title FROM todos t", nativeQuery = true)
  Collection<String> findAllTitles();
}
