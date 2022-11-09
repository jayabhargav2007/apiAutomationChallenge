package com.mobiquity.testRunners;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Integration test using cucumber runner
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/testFeatures/", glue = "com.mobiquity.stepDefinitions",tags = "@Regression")
public class RunAllTest {
}
