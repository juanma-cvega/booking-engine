package com.jusoft.bookingengine.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;

@Slf4j
class LockingTemplateTest {

  private static final int SLOW_TASK = 500;
  private static final int FAST_TASK = 10;
  private static final int INITIAL_VALUE = 0;
  private static final int SLOW_TASK_VALUE = 10;
  private static final int FAST_TASK_VALUE = 20;
  private static final int TOTAL_TASK_VALUE = 30;
  private static final int SLEEP_TIME_BETWEEN_THREAD_EXECUTION = 200; //Ensures the right order of execution

  private final Lock lock = new ReentrantLock();
  private final ExecutorService executor = Executors.newFixedThreadPool(3);

  @Test
  public void test_constructor_is_private() throws NoSuchMethodException {
    Constructor<LockingTemplate> constructor = LockingTemplate.class.getDeclaredConstructor();
    assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
    constructor.setAccessible(true);
    assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class);
  }

  @Test
  public void runnable_with_lock_should_make_second_thread_wait_for_first_thread_to_finish() throws ExecutionException, InterruptedException {
    AtomicLong testObject = new AtomicLong(INITIAL_VALUE);
    TestRunnable slowTaskSupplier = new TestRunnable(testObject, SLOW_TASK_VALUE, SLOW_TASK);
    Future<?> slowTask = executor.submit(() -> LockingTemplate.withLock(lock, slowTaskSupplier));
    await().atMost(SLEEP_TIME_BETWEEN_THREAD_EXECUTION, TimeUnit.MILLISECONDS).until(() -> slowTaskSupplier.isRunning);
    Future<?> fastTask = executor.submit(() -> LockingTemplate.withLock(lock, new TestRunnable(testObject, FAST_TASK_VALUE, FAST_TASK)));

    assertThat(testObject.get()).isEqualTo(INITIAL_VALUE);
    slowTask.get();
    assertThat(testObject.get()).isEqualTo(SLOW_TASK_VALUE);
    assertThat(fastTask.isDone()).isFalse();
    fastTask.get();
    assertThat(testObject.get()).isEqualTo(FAST_TASK_VALUE);
  }

  @Test
  public void runnable_without_lock_should_let_both_threads_run_the_code_at_same_time() throws ExecutionException, InterruptedException {
    AtomicLong testObject = new AtomicLong(INITIAL_VALUE);
    TestRunnable slowTaskSupplier = new TestRunnable(testObject, SLOW_TASK_VALUE, SLOW_TASK);
    Future<?> slowTask = executor.submit(slowTaskSupplier);
    await().atMost(SLEEP_TIME_BETWEEN_THREAD_EXECUTION, TimeUnit.MILLISECONDS).until(() -> slowTaskSupplier.isRunning);
    Future<?> fastTask = executor.submit(new TestRunnable(testObject, FAST_TASK_VALUE, FAST_TASK));

    assertThat(testObject.get()).isEqualTo(INITIAL_VALUE);
    fastTask.get();
    assertThat(testObject.get()).isEqualTo(FAST_TASK_VALUE);
    assertThat(slowTask.isDone()).isFalse();
    slowTask.get();
    assertThat(testObject.get()).isEqualTo(SLOW_TASK_VALUE);
  }

  @Test
  public void supplier_with_lock_should_make_second_thread_wait_for_first_thread_to_finish() throws ExecutionException, InterruptedException {
    AtomicLong testObject = new AtomicLong(INITIAL_VALUE);
    TestSupplier slowTaskSupplier = new TestSupplier(testObject, SLOW_TASK_VALUE, SLOW_TASK);
    Future<Long> slowTask = executor.submit(() -> LockingTemplate.withLock(lock, slowTaskSupplier));
    await().atMost(SLEEP_TIME_BETWEEN_THREAD_EXECUTION, TimeUnit.MILLISECONDS).until(() -> slowTaskSupplier.isRunning);
    Future<Long> fastTask = executor.submit(() -> LockingTemplate.withLock(lock, new TestSupplier(testObject, FAST_TASK_VALUE, FAST_TASK)));

    assertThat(testObject.get()).isEqualTo(INITIAL_VALUE);
    Long firstValue = slowTask.get();
    assertThat(firstValue).isEqualTo(SLOW_TASK_VALUE);
    assertThat(testObject.get()).isEqualTo(SLOW_TASK_VALUE);
    assertThat(fastTask.isDone()).isFalse();
    Long secondValue = fastTask.get();
    assertThat(secondValue).isEqualTo(TOTAL_TASK_VALUE);
    assertThat(testObject.get()).isEqualTo(TOTAL_TASK_VALUE);
  }

  @Test
  public void supplier_with_lock_throws_exception_should_release_lock_after_failing() throws InterruptedException {
    executor.submit(() -> LockingTemplate.withLock(lock, (Supplier<RuntimeException>) RuntimeException::new));
    boolean isLockAcquired = lock.tryLock(SLEEP_TIME_BETWEEN_THREAD_EXECUTION, TimeUnit.MILLISECONDS);
    lock.unlock();
    assertThat(isLockAcquired).isTrue();
  }

  @Test
  public void supplier_without_lock_runnable_should_let_both_threads_run_the_code_at_same_time() throws ExecutionException, InterruptedException {
    AtomicLong testObject = new AtomicLong(0);
    TestCallable testCallable = new TestCallable(testObject, SLOW_TASK_VALUE, SLOW_TASK);
    Future<Long> slowTask = executor.submit(testCallable);
    await().atMost(SLEEP_TIME_BETWEEN_THREAD_EXECUTION, TimeUnit.MILLISECONDS).until(() -> testCallable.isRunning);
    Future<Long> fastTask = executor.submit(new TestCallable(testObject, FAST_TASK_VALUE, FAST_TASK));

    assertThat(testObject.get()).isEqualTo(INITIAL_VALUE);
    Long firstValue = fastTask.get();
    assertThat(firstValue).isEqualTo(FAST_TASK_VALUE);
    assertThat(testObject.get()).isEqualTo(FAST_TASK_VALUE);
    assertThat(slowTask.isDone()).isFalse();
    Long secondValue = slowTask.get();
    assertThat(secondValue).isEqualTo(TOTAL_TASK_VALUE);
    assertThat(testObject.get()).isEqualTo(TOTAL_TASK_VALUE);
  }

  @RequiredArgsConstructor
  private static class TestRunnable implements Runnable {

    private volatile boolean isRunning;

    private final AtomicLong testObject;
    private final long newValue;
    private final long sleepTime;

    @Override
    public void run() {
      isRunning = true;
      sleep(sleepTime);
      testObject.set(newValue);
      isRunning = false;
    }
  }

  @RequiredArgsConstructor
  private static class TestSupplier implements Supplier<Long> {

    private volatile boolean isRunning;

    private final AtomicLong testObject;
    private final long newValue;
    private final long sleepTime;

    @Override
    public Long get() {
      isRunning = true;
      sleep(sleepTime);
      testObject.updateAndGet(current -> current + newValue);
      isRunning = false;
      return testObject.get();
    }

  }

  @RequiredArgsConstructor
  private static class TestCallable implements Callable<Long> {

    private volatile boolean isRunning;

    private final AtomicLong testObject;
    private final long newValue;
    private final long sleepTime;

    @Override
    public Long call() {
      isRunning = true;
      sleep(sleepTime);
      testObject.updateAndGet(current -> current + newValue);
      isRunning = false;
      return testObject.get();
    }

  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      log.error("Awaken from sleep", e);
    }
  }
}
