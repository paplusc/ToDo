package com.pcremades.todo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class JobService {

  private final ThreadPoolTaskScheduler scheduler;

  @Autowired
  public JobService(ThreadPoolTaskScheduler scheduler) {
    this.scheduler = scheduler;
  }

  @Async
  @Scheduled(initialDelayString = "${scheduler.initialDelay}", fixedRateString = "${scheduler.fixedRate}")
  public void jobLauncher() throws InterruptedException {
    LocalDateTime date = LocalDateTime.now();
    System.out.println("Thread name: " + Thread.currentThread().getName() + " -> Test job executed at: " + date);
    Thread.sleep(3000);
  }

  public List<String> getActiveTasks() {
    final Set<Thread> allActiveThreads = Thread.getAllStackTraces().keySet();
    return allActiveThreads.stream()
               .filter(thread -> thread.getName().startsWith("My-Scheduled-task-"))
               .filter(thread -> thread.getState().equals(Thread.State.TIMED_WAITING))
               .map(Thread::getName)
               .collect(Collectors.toList());
  }

  public void stopTasks() {
    scheduler.shutdown();
  }
}
