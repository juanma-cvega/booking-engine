package com.jusoft.bookingengine.component.scheduler;

import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import java.time.Clock;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerComponentConfig {

    @Autowired private Clock clock;

    @Autowired private MessagePublisher messagePublisher;

    @Bean
    public SchedulerComponent schedulerComponent() {
        return new SchedulerComponentInMemory(
                clock, messagePublisher, executor(), tasks(), scheduledExecutorService());
    }

    @Bean
    public List<ScheduledTask> tasks() {
        return new CopyOnWriteArrayList<>();
    }

    private Executor executor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    private ScheduledExecutorService scheduledExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}
