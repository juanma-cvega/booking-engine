package com.jusoft.bookingengine.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TimeUtil {

  //FIXME findBySlot better serialization
  public static long getTimeFrom(ZonedDateTime time) {
    return time.toInstant().getEpochSecond();
  }

  public static ZonedDateTime getLocalDateTimeFrom(long time) {
    return ZonedDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneOffset.UTC);
  }
}
