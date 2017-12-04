package com.jusoft.bookingengine.app;

import com.jusoft.bookingengine.component.booking.BookingComponentConfig;
import com.jusoft.bookingengine.component.slot.SlotComponentConfig;
import com.jusoft.bookingengine.component.timer.TimerConfig;
import com.jusoft.bookingengine.controller.ControllerConfig;
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