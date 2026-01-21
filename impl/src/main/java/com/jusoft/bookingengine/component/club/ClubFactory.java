package com.jusoft.bookingengine.component.club;

import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.CreateClubCommand;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClubFactory {

    private final Supplier<Long> idSupplier;

    Club createFrom(CreateClubCommand command) {
        return new Club(
                idSupplier.get(),
                command.getName(),
                command.getDescription(),
                command.getAdminId());
    }

    public ClubView createFrom(Club club) {
        return new ClubView(club.getId(), club.getName(), club.getDescription(), club.getAdmins());
    }
}
