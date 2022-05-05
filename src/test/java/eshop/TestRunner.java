package eshop;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
  plugin = "pretty",
  features = "src/test/resources/features",
  glue = "eshop.steps",
  tags = "@automated"
)
public class TestRunner {}