package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.scheduler.api.ScheduledEvent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;

@AllArgsConstructor
public class SchedulerEventListener implements MessageListener<ScheduledEvent> {

  private final SchedulerComponent schedulerComponent;

  @EventListener(ScheduledEvent.class)
  @Override
  public void consume(ScheduledEvent event) {
    schedulerComponent.schedule(event);
  }
}
