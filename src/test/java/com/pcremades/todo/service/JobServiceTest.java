package com.pcremades.todo.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class JobServiceTest {

  private final ThreadPoolTaskScheduler scheduler = mock(ThreadPoolTaskScheduler.class);
  private final JobService service = new JobService(scheduler);

  @Test
  void jobLauncherThenSuccess() {}

  @Test
  void listAllActiveTasksThenSuccess() {
    // Arrange
    final NewThread t1 = new NewThread();
    t1.setName("My-Scheduled-task-1");
    final NewThread t2 = new NewThread();
    t2.setName("My-Scheduled-task-2");
    t1.start();
    t2.start();

    // Act
    final List<String> response = service.getActiveTasks();

    // Assert
    assertThat(response.containsAll(List.of("My-Scheduled-task-1","My-Scheduled-task-2")), is(true));
  }

  @Test
  void stopTasksThenSuccess() {
    // Arrange
    doNothing().when(scheduler).shutdown();

    // Act
    service.stopTasks();

    // Assert
    verify(scheduler, times(1)).shutdown();
  }

  static class NewThread extends Thread {
    public void run() {
      int i = 0;
      while (true) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
