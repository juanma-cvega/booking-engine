package com.jusoft.bookingengine.repository;

import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@AllArgsConstructor
public abstract class AbstractRepositoryInMemory<ID, T> {

  protected final ConcurrentMap<ID, T> store;

  protected void save(T entity) {
    if (store.get(getIdFrom(entity)) != null) {
      throw new IllegalArgumentException("Unable to save new entity. Entity already in store");
    }
    store.put(getIdFrom(entity), entity);
  }

  protected Optional<T> find(ID entityId) {
    return Optional.ofNullable(store.get(entityId));
  }

  protected void execute(ID id, UnaryOperator<T> modifier) {
    T entityModified = store.computeIfPresent(id, (entityId, entityFound) -> modifier.apply(entityFound));
    if (entityModified == null) {
      throw createNotFoundException(id);
    }
  }

  protected boolean deleteIf(ID entityId, Predicate<T> condition) {
    AtomicBoolean isFound = new AtomicBoolean(false);
    T isEntityRemoved = store.computeIfPresent(entityId, (id, entityFound) -> remapToNullIfOrReturn(condition, entityFound, isFound));
    if (!isFound.get()) {
      throw createNotFoundException(entityId);
    }
    return isEntityRemoved == null;
  }

  private T remapToNullIfOrReturn(Predicate<T> condition, T entityFound, AtomicBoolean isFound) {
    isFound.set(true);
    if (condition.test(entityFound)) {
      return null;
    }
    return entityFound;
  }

  protected abstract ID getIdFrom(T entity);

  protected abstract RuntimeException createNotFoundException(ID entityId);

}
