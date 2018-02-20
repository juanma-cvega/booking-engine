package com.jusoft.bookingengine.repository;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class InMemoryRepository<I, T> {

  public <R> R execute(I id, Function<T, R> mapper, Supplier<? extends RuntimeException> notFoundException) {
    AtomicReference<R> reference = new AtomicReference<>();
    T modifiedClub = getStore().computeIfPresent(id, (key, entity) -> {
      reference.set(mapper.apply(entity));
      if (entity == null) {
        throw new IllegalArgumentException("Mapper function returned null value");
      }
      return entity;
    });
    if (modifiedClub == null) {
      throw notFoundException.get();
    }
    return reference.get();
  }

  protected abstract Map<I, T> getStore();
}
