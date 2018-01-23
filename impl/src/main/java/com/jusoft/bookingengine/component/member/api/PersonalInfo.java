package com.jusoft.bookingengine.component.member.api;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class PersonalInfo {

  @NonNull
  private final String name;
  @NonNull
  private final String surname;
  private final LocalDateTime dateOfBirth;
}
