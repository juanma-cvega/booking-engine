package com.jusoft.bookingengine.component.club;

import static com.jusoft.bookingengine.util.LockingTemplate.withLock;

import com.jusoft.bookingengine.component.club.api.JoinRequest;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClubRepositoryInMemory implements ClubRepository {

    private final Map<Long, Club> store;
    private final Lock lock;

    @Override
    public void save(Club club) {
        withLock(
                lock,
                () -> {
                    Optional<Club> clubFound =
                            store.values().stream()
                                    .filter(
                                            currentClub ->
                                                    currentClub.getName().equals(club.getName()))
                                    .findFirst();
                    if (clubFound.isPresent()) {
                        throw new IllegalArgumentException(
                                String.format("Club with name %s already created", club.getName()));
                    }
                    store.put(club.getId(), club);
                });
    }

    @Override
    public Optional<Club> findBy(String name) {
        return store.values().stream()
                .filter(club -> club.getName().equals(name))
                .findFirst()
                .map(this::copyClub);
    }

    @Override
    public JoinRequest removeJoinRequest(
            long clubId,
            Function<Club, JoinRequest> function,
            Supplier<RuntimeException> clubNotFoundException) {
        return withLock(
                lock,
                () -> {
                    Club club = find(clubId).orElseThrow(clubNotFoundException);
                    JoinRequest joinRequest = function.apply(club);
                    store.put(clubId, club);
                    return joinRequest;
                });
    }

    @Override
    public void addJoinRequest(
            long clubId,
            UnaryOperator<Club> function,
            Supplier<RuntimeException> notFoundException) {
        withLock(
                lock,
                () -> {
                    Club club = find(clubId).orElseThrow(notFoundException);
                    store.put(clubId, function.apply(club));
                });
    }

    @Override
    public Optional<Club> find(long clubId) {
        Club value = store.get(clubId);
        if (value != null) {
            return Optional.of(copyClub(value));
        }
        return Optional.empty();
    }

    private Club copyClub(Club value) {
        return new Club(
                value.getId(),
                value.getName(),
                value.getDescription(),
                value.getAdmins(),
                value.getJoinRequests());
    }
}
