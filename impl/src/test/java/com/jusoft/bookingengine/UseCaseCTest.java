package com.jusoft.bookingengine;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(
  features = "src/test/resources",
  glue = "com.jusoft.bookingengine.usecase",
  plugin = {"pretty"})
@RunWith(Cucumber.class)
public class UseCaseCTest {
}

