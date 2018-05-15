package com.jusoft.bookingengine.component.instructor.api;

import lombok.Data;

@Data(staticConstructor = "of")
public class Address {

  private final String street;
  private final String postcode;
  private final String city;
}
