package com.jusoft.bookingengine.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

@UtilityClass
public class LockingTemplate {

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
}
