package com.jusoft.bookingengine.component.classmanager;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

interface ClassManagerComponentRepository {
  void save(Class from);

  Optional<Class> find(long classId);

  void remove(long classId);

  boolean removeIf(long classId, Predicate<Class> condition);

  void execute(long classId, UnaryOperator<Class> modifier);

  List<Class> findByBuildingId(long buildingId);
}
