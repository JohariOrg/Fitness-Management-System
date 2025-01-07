package com.fitness.management;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features", // Path to feature files
    glue = "com.fitness.management.steps",    // Path to step definitions
    plugin = {"pretty", "html:target/cucumber-reports.html"} // Report generation
)
public class CucumberTestRunner {
}
