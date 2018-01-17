package com.jusoft.bookingengine;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
  features = "src/test/resources",
  glue = "com.jusoft.bookingengine.usecase",
  plugin = {"pretty"})
public class UseCaseCTest {
}
