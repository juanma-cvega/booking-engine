package com.jusoft.bookingengine;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(
  features = "src/test/resources",
  glue = {
    "com.jusoft.bookingengine.usecase",
    "com.jusoft.bookingengine.config"
  },
  plugin = {"pretty"})
@RunWith(Cucumber.class)
public class UseCaseCTest {
}

