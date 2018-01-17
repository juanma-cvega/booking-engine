package com.jusoft.bookingengine.component.club.api;

public interface ClubComponent {

  ClubView create(CreateClubCommand command);

  boolean isAvailable(long clubId);

  ClubView find(long clubId);
}
