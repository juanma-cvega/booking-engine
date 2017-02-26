package com.jusoft.app;

import com.jusoft.component.booking.BookingComponentConfig;
import com.jusoft.component.slot.SlotComponentConfig;
import com.jusoft.component.timer.TimerConfig;
import com.jusoft.controller.ControllerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({TimerConfig.class, SlotComponentConfig.class, BookingComponentConfig.class, ControllerConfig.class})
public class AppMain {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppMain.class);
    }
}
