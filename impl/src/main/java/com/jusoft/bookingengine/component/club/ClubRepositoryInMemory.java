package com.jusoft.bookingengine.component.club;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClubRepositoryInMemory implements ClubRepository {

  private final Map<Long, Club> store;

  @Override
  public void save(Club building) {
    store.put(building.getId(), building);
  }

  @Override
  public Optional<Club> find(long clubId) {
    return Optional.ofNullable(store.get(clubId));
  }
}
