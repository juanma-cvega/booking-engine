package com.jusoft;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/slot_management.feature", glue = "com.jusoft.slot")
public class SlotManagementCTest {
}
