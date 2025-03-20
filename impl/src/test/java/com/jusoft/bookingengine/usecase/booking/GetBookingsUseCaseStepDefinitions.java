package com.jusoft.bookingengine.usecase.booking;

import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.bookingsFetched;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingsUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private GetBookingsUseCase getBookingsUseCase;

  @When("^user (.*) asks for his bookings$")
  public void user_asks_for_his_bookings (Long userId) {
    bookingsFetched = getBookingsUseCase.getBookingsFor(userId);
  }
  @Then("^user (.*) should see (.*) bookings$")
  public void user_should_see_bookings (Long userId, Integer bookingsExpected) {
    assertThat(bookingsFetched).hasSize(bookingsExpected);
    if (bookingsExpected > 0) {
      assertThat(bookingsFetched).extracting("userId").containsOnly(userId);
    }
  };
}
