package com.jusoft.bookingengine;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectDirectories;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectDirectories("src/test/resources")
@ConfigurationParameter(
        key = Constants.GLUE_PROPERTY_NAME,
        value = "com.jusoft.bookingengine.usecase,com.jusoft.bookingengine.config")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty")
class UseCaseCTest {}
