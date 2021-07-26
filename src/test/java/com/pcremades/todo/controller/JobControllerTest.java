package com.pcremades.todo.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.pcremades.todo.service.JobService;

public class JobControllerTest {

  private final JobService service = mock(JobService.class);
  private final JobController controller = new JobController(service);

  @Test
  void listAllActiveTasksThenSuccess() {
    // Arrange
    when(service.getActiveTasks()).thenReturn(List.of("My-Scheduled-task-1","My-Scheduled-task-2"));

    // Act
    final List<String> response = controller.listActiveJobs();

    // Assert
    assertThat(response.get(0), is("My-Scheduled-task-1"));
    assertThat(response.get(1), is("My-Scheduled-task-2"));
  }

  @Test
  void stopAllScheduledTasksThenSuccess() {
    // Arrange
    doNothing().when(service).stopTasks();

    // Act
    final String response = controller.stopAllActiveJobs();

    // Assert
    assertThat(response, is("All jobs have been stopped"));
  }
}
