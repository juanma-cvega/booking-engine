package com.jusoft.bookingengine.component.shared;

import com.jusoft.bookingengine.component.AbstractStepDefinitions;
import com.jusoft.bookingengine.component.mock.ClockStub;
import org.springframework.beans.factory.annotation.Autowired;

public class SharedManagementStepDefinitions extends AbstractStepDefinitions {

  @Autowired
  private ClockStub clock;

  public SharedManagementStepDefinitions() {
    Given("^current time is (.*)$", (String currentTime) ->
      clock.setClock(getFixedClockAt(currentTime)));
  }

}
