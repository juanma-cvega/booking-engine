package com.jusoft.component.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.function.Supplier;

@Configuration
public class MockConfig {

    @Bean
    public Supplier<LocalDateTime> instantSupplier() {
        return new InstantSupplierStub(Clock.systemDefaultZone());
    }
}
