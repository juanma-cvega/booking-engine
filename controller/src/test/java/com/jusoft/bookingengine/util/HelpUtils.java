package com.jusoft.bookingengine.util;

import lombok.experimental.UtilityClass;
import tools.jackson.databind.ObjectMapper;

@UtilityClass
public class HelpUtils {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
}
