package defs;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import org.junit.Assert;
import defs.PropertiesHandler;
import pageObject.HomePage;
import pageObject.ProductPage;
import pageObject.ProductPage.Product;
import pageObject.CartPage;
import pageObject.ConfirmPage;
import java.util.Comparator;
import java.util.stream.Collectors;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StepDefinitions {

  PropertiesHandler prop;
  WebDriver driver;
  WebDriverWait wait;
	HomePage home;
  ProductPage product;
  CartPage cart;
  ConfirmPage confirm;

  @Before
  public void setup() {
    prop.loadFromFile("/src/test/resources/test.properties");
    ChromeOptions options = new ChromeOptions();
    options.setHeadless(false);
    try {
      URL remoteURL = new URL(prop.get("webDriverURL"));
      driver = new RemoteWebDriver(remoteURL, options);
      //Thread.sleep(5000);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

	@Given("Customer navigates to home page")
  public void navigateToHomepage() {
    driver.get(prop.get("homePageURL"));
  }

  @When("Customer clicks correct button based on temperature")
  public void clickCorrectButtonBasedOnTemp() {
    // Ensure that temperature is <19 or >34 degrees
    Integer temp;
    home = new HomePage(driver);
    home.navigateTo(prop.get("homePageURL"));
    home.waitUntilVisibility(home.tempCont);
    temp = home.getTempValue();
    while ((19 <= temp) && (temp <= 34)) {
      home.navigateTo(prop.get("homePageURL"));
      home.waitUntilVisibility(home.tempCont);
      temp = home.getTempValue();
    }
    // Click on button 'Buy moisturizers' in case
    // temperature < 19 degrees
    if (temp < 19) {
      prop.set("shopFor", "M");
      home.clickMoistButton();
    }
    // Click on button 'Buy sunscreens' in case
    // temperature > 34 degrees
    if (temp > 34) {
      prop.set("shopFor", "S");
      home.clickSunButton();
    }
  }

  @Then("Correct category page is displayed")
  public void MoistPageDisplayed() {
    product = new ProductPage(driver);
    if (prop.get("shopFor") == "M") {
      Assert.assertEquals(product.getTitle(), "Moisturizers");
    }
    if (prop.get("shopFor") == "S") {
      Assert.assertEquals(product.getTitle(), "Sunscreens");
    }
  }

  @When("Customer adds first item into cart")
  public void addFirstItemToCart() {
    List<Product> sortedArray = new ArrayList<>();
    product = new ProductPage(driver);
    if (prop.get("shopFor") == "M") {
      sortedArray = product.productArray
        .stream()
        // select moisturizers that contain Aloe
        .filter(p -> p.getName().toLowerCase().contains("aloe")) 
        // select least expensive moisturizer
        .sorted(Comparator.comparing(Product::getPrice))
        .collect(Collectors.toList());
      //product.print(sortedArray);
      //List<Integer> lst = sortedArray.stream().map(p -> p.getPrice()).collect(Collectors.toList()); 
      //product.print(lst);
    }
    if (prop.get("shopFor") == "S") {
      sortedArray = product.productArray
        .stream()
        // select sunscreens that contain SPF-50
        .filter(p -> p.getName().toLowerCase().contains("spf-50"))
        // select least expensive sunscreen
        .sorted(Comparator.comparing(Product::getPrice))
        .collect(Collectors.toList());
    }
    try {
      // Click to Add button for selected product
      product.clickBuyButton(sortedArray.get(0).getEl());
      // Save data about selected product
      prop.set("firstProductName", sortedArray.get(0).getName());
      prop.set("firstProductPrice", sortedArray.get(0).getPrice());
    } catch (IndexOutOfBoundsException ex) {
      ex.printStackTrace();
    }
  }

  @When("Customer adds second item into cart")
  public void addSecondItemToCart() {
    List<Product> sortedArray = new ArrayList<>();
    product = new ProductPage(driver);
    if (prop.get("shopFor") == "M") {
      sortedArray = product.productArray
        .stream()
        // select moisturizers that contain almond
        .filter(p -> p.getName().toLowerCase().contains("almond")) 
        // select least expensive moisturizer
        .sorted(Comparator.comparing(Product::getPrice))
        .collect(Collectors.toList());
      //product.print(sortedArray);
      //List<Integer> lst = sortedArray.stream().map(p -> p.getPrice()).collect(Collectors.toList()); 
      //product.print(lst);
    }
    if (prop.get("shopFor") == "S") {
      sortedArray = product.productArray
        .stream()
        // select sunscreens that contain SPF-30
        .filter(p -> p.getName().toLowerCase().contains("spf-30"))
        // select least expensive sunscreen
        .sorted(Comparator.comparing(Product::getPrice))
        .collect(Collectors.toList());
    }
    try {
      // Click to Add button for selected product
      product.clickBuyButton(sortedArray.get(0).getEl());
      // Save data about selected product
      prop.set("secondProductName", sortedArray.get(0).getName());
      prop.set("secondProductPrice", sortedArray.get(0).getPrice());
    } catch (IndexOutOfBoundsException ex) {
      ex.printStackTrace();
    }
  }

  @When("Customer clicks cart button")
  public void clickCartButton() {
    product = new ProductPage(driver);
    product.clickCartButton();
  }

  @Then("Items are displayed in cart")
  public void itemsDisplayedInCart() {
    cart = new CartPage(driver);
    // check first item
    Assert.assertEquals(cart.getItemName(cart.cartItemRows.get(0)), prop.get("firstProductName"));
    Assert.assertEquals(cart.getItemPrice(cart.cartItemRows.get(0)), prop.get("firstProductPrice"));
    // check second item
    Assert.assertEquals(cart.getItemName(cart.cartItemRows.get(1)), prop.get("secondProductName"));
    Assert.assertEquals(cart.getItemPrice(cart.cartItemRows.get(1)), prop.get("secondProductPrice"));
    // check total price
    Integer sum = Integer.parseInt(prop.get("firstProductPrice")) + 
                  Integer.parseInt(prop.get("secondProductPrice"));
    Assert.assertEquals(cart.getTotalPriceText(), "Total: Rupees "+sum.toString());
  }

  @When("Customer enters payment details")
  public void enterPaymentDetails() {
    cart = new CartPage(driver);
    cart.openPaymentForm();
    cart.enterEmail(prop.get("email"));
    cart.enterCardNumber(prop.get("cardNumber"));
    cart.enterCardExp(prop.get("cardExpDate"));
    cart.enterCardCvc(prop.get("cardCVC"));
    cart.enterZipCode(prop.get("zipCode"));
  }

  @When("Customer submits payment form")
  public void submitPaymentForm() {
    cart.submitPaymentForm();
  }

  @Then("Payment was successful")
  public void paymentSuccessful() {
    confirm = new ConfirmPage(driver);
    Assert.assertEquals(confirm.getTitle(), "PAYMENT SUCCESS");
    Assert.assertEquals(confirm.getText(), "Your payment was successful. You should receive a follow-up call from our sales team.");
  }

  @After
  public void close() {
    driver.close();
    driver.quit();
  }
  
}