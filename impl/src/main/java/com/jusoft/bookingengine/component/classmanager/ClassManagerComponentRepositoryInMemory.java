package com.jusoft.bookingengine.component.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
class ClassManagerComponentRepositoryInMemory implements ClassManagerComponentRepository {

  private final ConcurrentMap<Long, Class> store;

  @Override
  public void save(Class newClass) {
    if (store.get(newClass.getId()) != null) {
      throw new IllegalArgumentException("Unable to save new entity. Entity already in store");
    }
    store.put(newClass.getId(), newClass);
  }

  @Override
  public Optional<Class> find(long classId) {
    return Optional.ofNullable(store.get(classId));
  }

  @Override
  public boolean removeIf(long classId, Predicate<Class> condition) {
    try {
      Class classRemoved = store.computeIfPresent(classId, (id, classFound) -> remapToNullIfOrReturn(condition, classFound));
      return classRemoved == null;
    } catch (NullPointerException npe) {
      throw new ClassNotFoundException(classId);
    }
  }

  private Class remapToNullIfOrReturn(Predicate<Class> condition, Class classFound) {
    if (condition.test(classFound)) {
      return null;
    }
    return classFound;
  }

  @Override
  public void execute(long classId, UnaryOperator<Class> modifier) {
    Class classModified = store.computeIfPresent(classId, (id, classFound) -> modifier.apply(classFound));
    if (classModified == null) {
      throw new ClassNotFoundException(classId);
    }
  }

  @Override
  public List<Class> findByBuildingId(long buildingId) {
    return store.values().stream().filter(classFound -> classFound.getBuildingId() == buildingId).collect(toList());
  }
}
