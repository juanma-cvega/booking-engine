package com.jusoft.bookingengine.component.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassNotFoundException;
import com.jusoft.bookingengine.repository.AbstractRepositoryInMemory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static java.util.stream.Collectors.toList;

class ClassManagerComponentRepositoryInMemory extends AbstractRepositoryInMemory<Long, Class> implements ClassManagerComponentRepository {

  ClassManagerComponentRepositoryInMemory(ConcurrentMap<Long, Class> store) {
    super(store);
  }

  @Override
  public Optional<Class> find(long classId) {
    return super.find(classId);
  }

  @Override
  public boolean removeIf(long classId, Predicate<Class> condition) {
    return super.deleteIf(classId, condition);
  }

  @Override
  public void execute(long classId, UnaryOperator<Class> modifier) {
    super.execute(classId, modifier);
  }

  @Override
  public void save(Class newClass) {
    super.save(newClass);
  }

  @Override
  public boolean deleteIf(Long classId, Predicate<Class> condition) {
    return super.deleteIf(classId, condition);
  }

  @Override
  public List<Class> findByBuildingId(long buildingId) {
    return store.values().stream().filter(classFound -> classFound.getBuildingId() == buildingId).collect(toList());
  }

  @Override
  protected Long getIdFrom(Class entity) {
    return entity.getId();
  }

  @Override
  protected RuntimeException createNotFoundException(Long entityId) {
    return new ClassNotFoundException(entityId);
  }
}
