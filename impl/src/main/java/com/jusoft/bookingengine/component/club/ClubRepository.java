package com.jusoft.bookingengine.component.club;

import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.repository.Repository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

interface ClubRepository extends Repository<Club> {

  void save(Club building);

  Optional<Club> find(long clubId);

  Optional<Club> findBy(String name);

  JoinRequest removeJoinRequest(long clubId, Function<Club, JoinRequest> function, Supplier<RuntimeException> clubNotFoundException);

}
