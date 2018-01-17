package com.jusoft.bookingengine.component.club;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
class Club {

  private final long id;
  private final String description;
}
