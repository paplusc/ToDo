package com.pcremades.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcremades.todo.service.JobService;

@RestController
@RequestMapping("/odilo/tests/1")
public class JobController {

  private final JobService service;

  @Autowired
  public JobController(JobService service) {
    this.service = service;
  }

  @GetMapping
  public List<String> listActiveJobs() {
    return service.getActiveTasks();
  }

  @DeleteMapping
  public String stopAllActiveJobs() {
    service.stopTasks();
    return "All jobs have been stopped";
  }
}
