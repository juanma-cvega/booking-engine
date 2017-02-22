package com.jusoft.app;

import com.jusoft.booking.BookingComponentConfig;
import com.jusoft.shared.SharedConfig;
import com.jusoft.slot.SlotComponentConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SharedConfig.class, SlotComponentConfig.class, BookingComponentConfig.class})
public class AppMain {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppMain.class);
    }
}
