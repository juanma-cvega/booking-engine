package com.jusoft.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtil {

    //FIXME find better serialization
    public static long getTimeFrom(LocalDateTime time) {
        return time.toInstant(ZoneOffset.UTC).getEpochSecond();
    }

    public static LocalDateTime getLocalDateTimeFrom(long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneOffset.UTC);
    }
}
