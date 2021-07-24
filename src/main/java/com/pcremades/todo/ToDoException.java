package com.pcremades.todo;

import java.util.Map;

import org.springframework.util.StringUtils;

public class ToDoException extends RuntimeException {

  public ToDoException(Class clazz, String id, String value) {
    super(ToDoException.generateMessage(clazz.getSimpleName(), Map.of(id, value)));
  }

  private static String generateMessage(String entity, Map<String, String> searchParams) {
    return StringUtils.capitalize(entity) +
               " was not found for parameters " +
               searchParams;
  }
}
