package com.jusoft.component.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/room_management.feature", glue = {"com.jusoft.component.room", "com.jusoft.component.slot"})
public class RoomManagementCTest {
}
