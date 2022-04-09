package defs;

import java.net.URL;
import org.junit.Assert;
import pageObject.HomePage;
import pageObject.ProductPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StepDefinitions {

  WebDriver driver;
  WebDriverWait wait;
	HomePage home;
  ProductPage product;

  @Before
  public void setup() {
    ChromeOptions options = new ChromeOptions();
    options.setHeadless(false);
    try {
      URL remoteURL = new URL("http://localhost:4444/wd/hub");
      driver = new RemoteWebDriver(remoteURL, options);
      //Thread.sleep(5000);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

	@Given("Customer navigates to home page")
  public void navigateToHomepage() {
    driver.get("https://weathershopper.pythonanywhere.com");
  }

  @Given("Current temperature on home page is <19 degrees")
  public void currentTempLower19() {
    home = new HomePage(driver);
    home.loadPageWithTempLower19();
  }

  @Given("Current temperature on home page is >34 degrees")
  public void currentTempHigher34() {
    home = new HomePage(driver);
    home.loadPageWithTempHigher34();
  }
  
  @When("Customer clicks on 'Buy moisturizers' button")
  public void clickBuyMoist() {
  	home.clickMoistButton();
  }
  
  @When("Customer clicks on 'Buy sunscreens' button")
  public void clickBuySun() {
  	home.clickSunButton();
  }

  @Then("Moisturizers category page is displayed")  
  public void MoistPageDisplayed() {
    product = new ProductPage(driver);
    Assert.assertEquals(product.getTitle(), "Moisturizers");
  }
  
  @Then("Sunscreens category page is displayed")
  public void SunPageDisplayed() {
    product = new ProductPage(driver);
    Assert.assertEquals(product.getTitle(), "Sunscreens");
  }

  @After
  public void close() {
    driver.close();
    driver.quit();
  }
  
}