package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlotLifeCycleManagerComponentConfig {

    @Autowired private MessagePublisher messagePublisher;

    @Autowired private Clock clock;

    @Bean
    public SlotLifeCycleManagerComponent slotLifeCycleManagerComponent() {
        return new SlotLifeCycleManagerComponentImpl(
                slotLifeCycleManagerRepository(),
                messagePublisher,
                slotLifeCycleEventFactory(),
                clock);
    }

    private SlotLifeCycleManagerRepository slotLifeCycleManagerRepository() {
        return new SlotLifeCycleManagerRepositoryInMemory(
                new ConcurrentHashMap<>(), new ReentrantLock());
    }

    private SlotLifeCycleEventFactory slotLifeCycleEventFactory() {
        return new SlotLifeCycleEventFactory();
    }
}
