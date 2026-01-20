package com.jusoft.bookingengine.component.authorization.api;

import java.time.temporal.TemporalUnit;
import lombok.Data;

@Data(staticConstructor = "of")
public class SlotAuthorizationConfig {

    private final long amount;
    private final TemporalUnit temporalUnit;
}
