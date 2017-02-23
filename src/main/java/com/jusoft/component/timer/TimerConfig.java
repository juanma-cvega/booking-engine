package com.jusoft.component.timer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Configuration
public class TimerConfig {

    @Bean
    public Supplier<LocalDateTime> instantSupplier() {
        return LocalDateTime::now;
    }
}
