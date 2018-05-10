package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.repository.Repository;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

interface SlotLifeCycleManagerRepository extends Repository {

  void save(SlotLifeCycleManager slotLifeCycleManager);

  Optional<SlotLifeCycleManager> find(long roomId);

  void execute(long roomId, UnaryOperator<SlotLifeCycleManager> modifier, Supplier<? extends RuntimeException> notFoundExceptionSupplier);

}
