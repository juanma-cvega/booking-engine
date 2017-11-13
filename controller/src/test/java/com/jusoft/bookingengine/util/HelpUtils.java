package com.jusoft.bookingengine.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HelpUtils {

  public final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
}
