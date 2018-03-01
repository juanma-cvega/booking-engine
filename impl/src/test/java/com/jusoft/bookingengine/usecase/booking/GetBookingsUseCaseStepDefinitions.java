package com.jusoft.bookingengine.usecase.booking;

import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.bookingsFetched;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingsUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private GetBookingsUseCase getBookingsUseCase;

  public GetBookingsUseCaseStepDefinitions() {
    When("^user (.*) asks for his bookings$", (Long userId) ->
      bookingsFetched = getBookingsUseCase.getBookingsFor(userId));
    Then("^user (.*) should see (.*) bookings$", (Long userId, Integer bookingsExpected) -> {
      assertThat(bookingsFetched).hasSize(bookingsExpected);
      if (bookingsExpected > 0) {
        assertThat(bookingsFetched).extracting("userId").containsOnly(userId);
      }
    });

  }
}
