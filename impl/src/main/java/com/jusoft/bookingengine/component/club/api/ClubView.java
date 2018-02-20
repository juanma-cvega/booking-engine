package com.jusoft.bookingengine.component.club.api;

import lombok.Data;

import java.util.Set;

@Data(staticConstructor = "of")
public class ClubView {

  private final long id;
  private final String name;
  private final String description;
  private final Set<Long> admins;
}
