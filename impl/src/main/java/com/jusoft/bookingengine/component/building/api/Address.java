package com.jusoft.bookingengine.component.building.api;

import lombok.Data;

@Data(staticConstructor = "of")
public class Address {

  private final String street;
  private final String zipCode;
  private final String city;
}
