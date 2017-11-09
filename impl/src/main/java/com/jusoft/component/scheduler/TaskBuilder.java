package com.jusoft.component.scheduler;

import com.jusoft.component.shared.Message;

import java.time.ZonedDateTime;

public class TaskBuilder {

  private ZonedDateTime executionTime;
  private Message message;

  public TaskBuilder executionTime(ZonedDateTime executionTime) {
    this.executionTime = executionTime;
    return this;
  }

  public TaskBuilder event(Message message) {
    this.message = message;
    return this;
  }

  public ZonedDateTime getExecutionTime() {
    return executionTime;
  }

  public Message getMessage() {
    return message;
  }
}
