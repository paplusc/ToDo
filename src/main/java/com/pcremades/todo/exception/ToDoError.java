package com.pcremades.todo.exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ToDoError {
  private final Integer code;
  private final String message;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final List<String> errors;

  public ToDoError(Integer code, String message, List<String> errors) {
    this.code = code;
    this.message = message;
    this.errors = errors;
  }

  @JsonGetter("code")
  public Integer code() {
    return code;
  }

  @JsonGetter("message")
  public String message() {
    return message;
  }

  @JsonGetter("errors")
  public List<String> errors() {
    return errors;
  }
}
