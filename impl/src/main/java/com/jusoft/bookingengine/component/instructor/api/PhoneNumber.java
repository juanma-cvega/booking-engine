package com.jusoft.bookingengine.component.instructor.api;

import lombok.Data;
import org.apache.commons.lang3.Validate;

@Data(staticConstructor = "of")
public class PhoneNumber {

  public static final int MAX_SIZE_PHONE_NUMBER = 13;
  private final String number;

  private PhoneNumber(String number) {
    Validate.isTrue(number.length() < MAX_SIZE_PHONE_NUMBER);
    this.number = number;
  }

  public static PhoneNumber of(String number) {
    return new PhoneNumber(number);
  }
}
