package com.jusoft.bookingengine.component.authorization.api;

import lombok.Data;

import java.time.temporal.TemporalUnit;

@Data(staticConstructor = "of")
public class SlotAuthorizationConfig {

  private final long amount;
  private final TemporalUnit temporalUnit;

}
