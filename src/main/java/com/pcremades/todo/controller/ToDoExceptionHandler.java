package com.pcremades.todo.controller;

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

import com.pcremades.todo.ToDoError;
import com.pcremades.todo.ToDoException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ToDoExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
   *
   * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ToDoError object
   */
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

  /**
   * Handles ToDoException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
   *
   * @param ex the ToDoException
   * @return the ToDoError object
   */
  @ExceptionHandler(ToDoException.class)
  protected ResponseEntity<Object> handleEntityNotFound(
             ToDoException ex) {
    ToDoError toDoError = new ToDoError(404, ex.getMessage(), null);
    return new ResponseEntity<>(toDoError, NOT_FOUND);
  }
}
