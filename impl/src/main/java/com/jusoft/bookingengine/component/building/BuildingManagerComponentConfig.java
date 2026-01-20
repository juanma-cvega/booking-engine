package com.jusoft.bookingengine.component.building;

import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuildingManagerComponentConfig {

    @Autowired private MessagePublisher messagePublisher;

    @Bean
    public BuildingManagerComponent buildingComponent() {
        return new BuildingManagerComponentImpl(buildingFactory(), repository(), messagePublisher);
    }

    private BuildingFactory buildingFactory() {
        return new BuildingFactory(idGenerator());
    }

    private Supplier<Long> idGenerator() {
        AtomicLong idGenerator = new AtomicLong(1);
        return idGenerator::getAndIncrement;
    }

    private BuildingRepository repository() {
        return new BuildingRepositoryInMemory(new ConcurrentHashMap<>());
    }
}
