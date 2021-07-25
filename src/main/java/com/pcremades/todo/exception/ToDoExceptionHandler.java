package com.pcremades.todo.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ToDoExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
             MethodArgumentNotValidException ex,
             HttpHeaders headers,
             HttpStatus status,
             WebRequest request) {
    final List<String> errors = new ArrayList<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
               errors.add(error.getField() + " -> " + error.getDefaultMessage())
    );
    return new ResponseEntity<>(new ToDoError(400, "Validation errors", errors), BAD_REQUEST);
  }

  @ExceptionHandler(ToDoException.class)
  protected ResponseEntity<Object> handleEntityNotFound(
             ToDoException ex) {
    ToDoError toDoError = new ToDoError(404, ex.getMessage(), null);
    return new ResponseEntity<>(toDoError, NOT_FOUND);
  }
}
