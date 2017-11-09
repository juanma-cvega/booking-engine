package com.jusoft.component.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/slot_management.feature", glue = {"com.jusoft.component.slot", "com.jusoft.component.room"})
public class SlotManagementCTest {
}
