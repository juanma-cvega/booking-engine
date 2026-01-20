package com.jusoft.bookingengine.component.authorization;

import static com.jusoft.bookingengine.util.LockingTemplate.withLock;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClubRepositoryInMemory implements ClubRepository {

    private final Map<Long, Club> store;
    private final Lock lock;

    @Override
    public Optional<Club> find(long clubId) {
        Optional<Club> clubFound = Optional.ofNullable(store.get(clubId));
        return clubFound.map(this::copyOf);
    }

    private Club copyOf(Club club) {
        return Club.of(club.getId(), club.getBuildings());
    }

    @Override
    public void save(Club club) {
        store.put(club.getId(), club);
    }

    @Override
    public void execute(
            long clubId,
            UnaryOperator<Club> function,
            Supplier<RuntimeException> notFoundExceptionSupplier) {
        withLock(
                lock,
                () -> {
                    Club club = find(clubId).orElseThrow(notFoundExceptionSupplier);
                    store.put(clubId, function.apply(club));
                });
    }
}
