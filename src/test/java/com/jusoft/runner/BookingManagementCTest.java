package com.jusoft.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/booking_management.feature", glue = {"com.jusoft.booking", "com.jusoft.slot"})
public class BookingManagementCTest {
}
