package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubComponent;
import com.jusoft.bookingengine.component.club.api.ClubView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindClubByNameUseCase {

  private final ClubComponent clubComponent;

  public ClubView findByName(String name) {
    return clubComponent.findByName(name);
  }
}
