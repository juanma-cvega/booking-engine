package com.jusoft.bookingengine.component.club;

import java.util.Optional;

interface ClubRepository {

  void save(Club building);

  Optional<Club> find(long clubId);
}
