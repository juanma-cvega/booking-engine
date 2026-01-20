// package com.jusoft.bookingengine.component.mock;
//
// import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
// import com.jusoft.bookingengine.component.scheduler.ScheduledTask;
// import com.jusoft.bookingengine.component.scheduler.api.ScheduledEvent;
// import com.jusoft.bookingengine.component.shared.MessagePublisher;
// import org.junit.Before;
// import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
// import org.mockito.Mock;
// import org.mockito.junit.MockitoJUnitRunner;
//
// import java.time.Clock;
// import java.time.ZoneId;
// import java.time.ZonedDateTime;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.concurrent.Executors;
// import java.util.concurrent.ScheduledExecutorService;
// import java.util.concurrent.ScheduledFuture;
// import java.util.concurrent.TimeUnit;
//
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.verifyNoMoreInteractions;
//
// @RunWith(MockitoJUnitRunner.class)
// class ScheduledTasksExecutorTest {
//
//  private static final int NORMAL_EXECUTION_DELAY_IN_SECONDS = 1;
//  private static final int PASSED_EXECUTION_DELAY_IN_SECONDS = 2;
//  private static final int PASSED_EXECUTION_TIME_IN_MILLIS = 2000;
//  private static final Clock CLOCK = Clock.fixed(ZonedDateTime.now().toInstant(),
// ZoneId.systemDefault());
//  private static final ZonedDateTime CREATION_TIME = ZonedDateTime.now(CLOCK);
//  private static final ZonedDateTime EXECUTION_TIME =
// ZonedDateTime.now(CLOCK).plusSeconds(NORMAL_EXECUTION_DELAY_IN_SECONDS);
//  private static final AuctionFinishedEvent MESSAGE = new AuctionFinishedEvent(1, 1, 1);
//  private static final ScheduledEvent SCHEDULED_EVENT = new ScheduledEvent(MESSAGE,
// EXECUTION_TIME);
//
//  @Mock
//  private MessagePublisher messagePublisher;
//
//  private ScheduledTasksExecutor scheduledTasksExecutor;
//  private List<ScheduledTask> scheduledTasks;
//  private ScheduledExecutorService scheduledExecutorService;
//  private Runnable task;
//
//  @Before
//  public void setup() {
//    scheduledTasks = new ArrayList<>();
//    scheduledTasksExecutor = new ScheduledTasksExecutor(scheduledTasks, messagePublisher);
//    scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//    task = () -> messagePublisher.publish(MESSAGE);
//  }
//
//  @Test
//  public void scheduledTasksExecutorExecutesTaskBeforeExpectedTimeAndTaskIsNotExecutedAgain()
// throws
// InterruptedException {
//    ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(task,
// NORMAL_EXECUTION_DELAY_IN_SECONDS,
// TimeUnit.SECONDS);
//    scheduledTasks.add(new ScheduledTask(scheduledFuture, SCHEDULED_EVENT));
//
//
// scheduledTasksExecutor.executeLateTasks(CREATION_TIME.plusSeconds(PASSED_EXECUTION_DELAY_IN_SECONDS),
// CREATION_TIME);
//    verify(messagePublisher).publish(MESSAGE);
//    Thread.sleep(PASSED_EXECUTION_TIME_IN_MILLIS);
//    verifyNoMoreInteractions(messagePublisher);
//  }
//
//  @Test
//  public void
// scheduledTasksExecutorDoesNotExecuteTaskWhenPassedTimeIsBeforeExpectedExecutionTime() throws
// InterruptedException {
//    ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(task,
// NORMAL_EXECUTION_DELAY_IN_SECONDS,
// TimeUnit.SECONDS);
//    scheduledTasks.add(new ScheduledTask(scheduledFuture, SCHEDULED_EVENT));
//
//    scheduledTasksExecutor.executeLateTasks(CREATION_TIME, CREATION_TIME);
//    verify(messagePublisher, times(0)).publish(MESSAGE);
//    Thread.sleep(PASSED_EXECUTION_TIME_IN_MILLIS);
//    verify(messagePublisher).publish(MESSAGE);
//  }
// }
