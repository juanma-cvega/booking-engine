package com.jusoft.bookingengine.component.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/slot_management.feature", glue = {"com.jusoft.bookingengine.component.slot",
  "com.jusoft.bookingengine.component.room", "com.jusoft.bookingengine.component.shared"})
public class SlotManagementCTest {
}
