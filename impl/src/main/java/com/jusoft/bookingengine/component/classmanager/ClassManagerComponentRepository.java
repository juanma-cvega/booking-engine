package com.jusoft.bookingengine.component.classmanager;

import com.jusoft.bookingengine.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

interface ClassManagerComponentRepository extends Repository {

  void save(Class from);

  Optional<Class> find(long classId);

  boolean removeIf(long classId, Predicate<Class> condition);

  void execute(long classId, UnaryOperator<Class> modifier);

  List<Class> findByBuildingId(long buildingId);
}
