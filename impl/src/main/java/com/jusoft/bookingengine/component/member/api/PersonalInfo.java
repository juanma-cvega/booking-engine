package com.jusoft.bookingengine.component.member.api;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data(staticConstructor = "of")
public class PersonalInfo {

  @NonNull
  private final String name;
  @NonNull
  private final String surname;
  @NonNull
  private final LocalDateTime dateOfBirth;
}
