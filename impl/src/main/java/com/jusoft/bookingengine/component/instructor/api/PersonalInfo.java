package com.jusoft.bookingengine.component.instructor.api;

import lombok.Data;

@Data(staticConstructor = "of")
public class PersonalInfo {

  private final String name;
  private final String surname;
  private final Address address;
  private final String phoneNumber;
}
