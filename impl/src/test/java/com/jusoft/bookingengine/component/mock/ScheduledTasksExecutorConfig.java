// package com.jusoft.bookingengine.component.mock;
//
// import com.jusoft.bookingengine.component.scheduler.ScheduledTask;
// import com.jusoft.bookingengine.component.shared.MessagePublisher;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import java.util.List;
//
// @Configuration
// public class ScheduledTasksExecutorConfig {
//
//  @Autowired
//  private List<ScheduledTask> tasks;
//
//  @Autowired
//  private MessagePublisher messagePublisher;
//
//  @Bean
//  public ScheduledTasksExecutor scheduledTasksExecutor() {
//    return new ScheduledTasksExecutor(tasks, messagePublisher);
//  }
// }
