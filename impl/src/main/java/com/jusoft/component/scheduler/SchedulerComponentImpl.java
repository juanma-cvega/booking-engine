package com.jusoft.component.scheduler;

import com.jusoft.component.shared.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SchedulerComponentImpl implements SchedulerComponent {

    private final Clock clock;
    private final MessagePublisher messagePublisher;

    @Override
    public void schedule(Consumer<TaskBuilder> consumer) {
        TaskBuilder taskBuilder = new TaskBuilder();
        consumer.accept(taskBuilder);
        Task task = new Task(taskBuilder.getExecutionTime(), taskBuilder.getMessage());
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                messagePublisher.publish(task.getMessage());
            }
        }, getDelay(task.getExecutionTime()));
    }

    private long getDelay(ZonedDateTime executionTime) {
        long delay = ChronoUnit.MILLIS.between(ZonedDateTime.now(clock), executionTime);
        return delay > 0 ? delay : 0;
    }
}
