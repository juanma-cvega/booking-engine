package com.jusoft.bookingengine.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

import static java.lang.System.currentTimeMillis;

@UtilityClass
public class LockingTemplate {

  private static final long TIMEOUT_MILLIS = 1000;

  public static void withLock(Lock lock, Runnable runnable) {
    lock.lock();
    try {
      runnable.run();
    } finally {
      lock.unlock();
    }
  }

  public static <T> T withLock(Lock lock, Supplier<T> supplier) {
    T result;
    lock.lock();
    try {
      result = supplier.get();
    } finally {
      lock.unlock();
    }
    return result;
  }

  public static void tryCompareAndSwap(Supplier<Boolean> action) {
    boolean replaced = false;
    long startTime = currentTimeMillis();
    while (!replaced && currentTimeMillis() - startTime < TIMEOUT_MILLIS) {
      replaced = action.get();
    }
    if (!replaced) {
      throw new RuntimeException("Failed to update. The action timed out while trying to update the value");
    }
  }
}
