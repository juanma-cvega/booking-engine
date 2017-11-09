package com.jusoft.component.mock;

import com.jusoft.component.scheduler.SchedulerComponent;
import com.jusoft.component.scheduler.TaskBuilder;
import com.jusoft.component.shared.Message;
import com.jusoft.component.shared.MessagePublisher;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
public class SchedulerComponentMock implements SchedulerComponent {

  private final List<Message> messages;
  private final MessagePublisher messagePublisher;
  private final Clock clock;

  @Override
  public void schedule(Consumer<TaskBuilder> consumer) {
    TaskBuilder taskBuilder = new TaskBuilder();
    consumer.accept(taskBuilder);
    if (taskBuilder.getExecutionTime().isEqual(ZonedDateTime.now(clock))
      || taskBuilder.getExecutionTime().isBefore(ZonedDateTime.now(clock))) {
      messagePublisher.publish(taskBuilder.getMessage());
    } else {
      messages.add(taskBuilder.getMessage());
    }
  }
}
