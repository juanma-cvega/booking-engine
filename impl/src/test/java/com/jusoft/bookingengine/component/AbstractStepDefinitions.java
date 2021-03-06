//package com.jusoft.bookingengine.component;
//
//import com.jusoft.bookingengine.MainConfig;
//import com.jusoft.bookingengine.component.mock.ClockStub;
//import com.jusoft.bookingengine.component.mock.HolderConfig;
//import com.jusoft.bookingengine.component.mock.MessagesSink;
//import com.jusoft.bookingengine.component.mock.MessagesSinkConfig;
//import com.jusoft.bookingengine.component.mock.ScheduledTasksExecutor;
//import com.jusoft.bookingengine.component.mock.ScheduledTasksExecutorConfig;
//import com.jusoft.bookingengine.component.mock.TimerMockConfig;
//import com.jusoft.bookingengine.component.shared.MessagePublisher;
//import cucumber.api.java8.En;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//
//@ContextConfiguration(classes = {
//  MainConfig.class,
//  TimerMockConfig.class,
//  HolderConfig.class,
//  ScheduledTasksExecutorConfig.class,
//  MessagesSinkConfig.class})
//@DirtiesContext
//public class AbstractStepDefinitions implements En {
//
//  @Autowired
//  protected ScheduledTasksExecutor scheduledTasksExecutor;
//
//  @Autowired
//  protected ClockStub clock;
//
//  @Autowired
//  protected MessagesSink messagesSink;
//
//  @Autowired
//  protected MessagePublisher messagePublisher;
//}
