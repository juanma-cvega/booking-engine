package com.jusoft.shared;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Configuration
public class SharedConfig {

    @Bean
    public Supplier<LocalDateTime> instantSupplier() {
        return LocalDateTime::now;
    }
}
