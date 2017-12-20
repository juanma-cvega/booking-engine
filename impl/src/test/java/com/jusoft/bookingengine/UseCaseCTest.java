package com.jusoft.bookingengine;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {
  "src/test/resources/features/add_buyer_to_auction_use_case.feature",
  "src/test/resources/features/cancel_booking_use_case.feature",
  "src/test/resources/features/create_booking_use_case.feature",
  "src/test/resources/features/create_room_use_case.feature",
  "src/test/resources/features/finish_auction_use_case.feature",
  "src/test/resources/features/open_next_slot_use_case.feature",
  "src/test/resources/features/schedule_next_slot_use_case.feature",
  "src/test/resources/features/start_auction_use_case.feature"
}, glue = {"com.jusoft.bookingengine.usecase"})
public class UseCaseCTest {
}
