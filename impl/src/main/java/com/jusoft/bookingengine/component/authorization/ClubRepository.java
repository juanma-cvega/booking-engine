package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.repository.Repository;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

interface ClubRepository extends Repository {

  Optional<Club> find(long clubId);

  void save(Club club);

  void execute(long clubId, UnaryOperator<Club> function, Supplier<RuntimeException> notFoundExceptionSupplier);
}
