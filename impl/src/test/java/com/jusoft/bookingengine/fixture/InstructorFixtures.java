package com.jusoft.bookingengine.fixture;

import com.jusoft.bookingengine.component.instructor.api.Address;
import com.jusoft.bookingengine.component.instructor.api.PersonalInfo;
import com.jusoft.bookingengine.component.instructor.api.PhoneNumber;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InstructorFixtures {

  public static final String INSTRUCTOR_NAME = "name";
  public static final String INSTRUCTOR_SURNAME = "surname";
  public static final String INSTRUCTOR_ADDRESS_STREET = "street";
  public static final String INSTRUCTOR_ADDRESS_POSTCODE = "postcode";
  public static final String INSTRUCTOR_ADDRESS_CITY = "city";
  public static final Address INSTRUCTOR_ADDRESS = Address.of(INSTRUCTOR_ADDRESS_STREET, INSTRUCTOR_ADDRESS_POSTCODE, INSTRUCTOR_ADDRESS_CITY);
  public static final PhoneNumber INSTRUCTOR_PHONE_NUMBER = PhoneNumber.of("phoneNumber");
  public static final PersonalInfo INSTRUCTOR_PERSONAL_INFO = PersonalInfo.of(INSTRUCTOR_NAME, INSTRUCTOR_SURNAME, INSTRUCTOR_ADDRESS, INSTRUCTOR_PHONE_NUMBER);
}
