package com.jusoft.bookingengine.usecase.club;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.ClubView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindClubByNameUseCase {

  private final ClubManagerComponent clubManagerComponent;

  public ClubView findByName(String name) {
    return clubManagerComponent.findByName(name);
  }
}
