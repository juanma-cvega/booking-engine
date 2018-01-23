package com.jusoft.bookingengine.component.club.api;

import lombok.Data;

@Data
public class CreateJoinRequestCommand {

  private final long clubId;
  private final long userId;
}
