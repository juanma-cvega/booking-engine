package com.jusoft.bookingengine.component.timer;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimerConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
