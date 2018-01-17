package com.jusoft.bookingengine.component.club;

import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.CreateClubCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClubFactory {

  private final Supplier<Long> idSupplier;

  Club createFrom(CreateClubCommand command) {
    return new Club(
      idSupplier.get(),
      command.getDescription()
    );
  }

  public ClubView createFrom(Club building) {
    return new ClubView(
      building.getId(),
      building.getDescription()
    );
  }
}
