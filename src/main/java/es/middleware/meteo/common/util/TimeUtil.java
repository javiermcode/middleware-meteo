package es.middleware.meteo.common.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtil {
    private TimeUtil() {
    }

    public static long getMillisElapsed(LocalDateTime input) {
        return ChronoUnit.MILLIS.between(input, LocalDateTime.now());
    }
}
