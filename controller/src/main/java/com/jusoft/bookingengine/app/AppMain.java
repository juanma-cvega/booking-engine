package com.jusoft.bookingengine.app;

import com.jusoft.bookingengine.component.booking.BookingManagerComponentConfig;
import com.jusoft.bookingengine.component.slot.SlotManagerComponentConfig;
import com.jusoft.bookingengine.component.timer.TimerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({TimerConfig.class, SlotManagerComponentConfig.class, BookingManagerComponentConfig.class})
public class AppMain {

    public static void main(String[] args) {
        SpringApplication.run(AppMain.class);
    }
}
