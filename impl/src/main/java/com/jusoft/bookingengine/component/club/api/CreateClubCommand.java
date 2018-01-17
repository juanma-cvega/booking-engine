package com.jusoft.bookingengine.component.club.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateClubCommand {

  private final String description;
}
