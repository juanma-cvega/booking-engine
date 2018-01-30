package com.jusoft.bookingengine.component.club;

import com.jusoft.bookingengine.component.club.api.JoinRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.jusoft.bookingengine.util.LockingTemplate.withLock;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClubRepositoryInMemory implements ClubRepository {

  private final Map<Long, Club> store;
  private final Lock lock;

  @Override
  public void save(Club club) {
    withLock(lock, () -> {
      Optional<Club> clubFound = store.values().stream()
        .filter(currentClub -> currentClub.getName().equals(club.getName()))
        .findFirst();
      if (clubFound.isPresent()) {
        throw new IllegalArgumentException(String.format("Club with name %s already created", club.getName()));
      }
      store.put(club.getId(), club);
    });
  }

  @Override
  public Optional<Club> findBy(String name) {
    return store.values().stream().filter(club -> club.getName().equals(name)).findFirst();
  }

  @Override
  public JoinRequest removeJoinRequest(long clubId, Function<Club, JoinRequest> function, Supplier<RuntimeException> clubNotFoundException) {
    return withLock(lock, () -> {
      Club club = find(clubId).orElseThrow(clubNotFoundException);
      JoinRequest joinRequest = function.apply(club);
      store.put(clubId, club);
      return joinRequest;
    });
  }

  @Override
  public Optional<Club> find(long clubId) {
    return Optional.ofNullable(store.get(clubId));
  }
}
