package com.jusoft.component.timer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class TimerConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
