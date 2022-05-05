package eshop.steps;

import eshop.pages.HomePage;
import eshop.pages.ProductPage;
import eshop.pages.ProductPage.Product;
import eshop.pages.CartPage;
import eshop.pages.ConfirmPage;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import net.serenitybdd.core.Serenity;
import org.assertj.core.api.SoftAssertions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class NewOrderDefinitions {

	HomePage home;
  ProductPage product;
  CartPage cart;
  ConfirmPage confirm;
  SoftAssertions softAssert = new SoftAssertions();

	@Given("Customer navigates to home page")
  public void navigateToHomepage() {
    home.open();
  }

  @When("Customer clicks correct button based on temperature")
  public void clickCorrectButtonBasedOnTemp() {
    // Ensure that temperature is <19 or >34 degrees
    Integer temp;
    temp = home.getTempValue();
    while ((19 <= temp) && (temp <= 34)) {
      home.open();
      temp = home.getTempValue();
    }
    // Click on button 'Buy moisturizers' in case
    // temperature < 19 degrees
    if (temp < 19) {
      Serenity.setSessionVariable("shopFor").to("M");
      home.clickMoistButton();
    }
    // Click on button 'Buy sunscreens' in case
    // temperature > 34 degrees
    if (temp > 34) {
      Serenity.setSessionVariable("shopFor").to("S");
      home.clickSunButton();
    }
  }

  @Then("Correct category page is displayed")
  public void MoistPageDisplayed() {
    if (Serenity.sessionVariableCalled("shopFor") == "M") {
      softAssert.assertThat(product.getTitle()).isEqualTo("Moisturizers");
    }
    if (Serenity.sessionVariableCalled("shopFor") == "S") {
      softAssert.assertThat(product.getTitle()).isEqualTo("Sunscreens");
    }
  }

  @When("Customer adds first item into cart")
  public void addFirstItemToCart() {
    product.loadProducts();
    List<Product> sortedArray = new ArrayList<>();
    if (Serenity.sessionVariableCalled("shopFor") == "M") {
      sortedArray = product.productArray
        .stream()
        // select moisturizers that contain Aloe
        .filter(p -> p.getName().toLowerCase().contains("aloe"))
        // select least expensive moisturizer
        .sorted(Comparator.comparing(Product::getPrice))
        .collect(Collectors.toList());
    }
    if (Serenity.sessionVariableCalled("shopFor") == "S") {
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
      Serenity.setSessionVariable("firstProductName").to(sortedArray.get(0).getName());
      Serenity.setSessionVariable("firstProductPrice").to(sortedArray.get(0).getPrice());
    } catch (IndexOutOfBoundsException ex) {
      ex.printStackTrace();
    }
  }

  @When("Customer adds second item into cart")
  public void addSecondItemToCart() {
    List<Product> sortedArray = new ArrayList<>();
    if (Serenity.sessionVariableCalled("shopFor") == "M") {
      sortedArray = product.productArray
        .stream()
        // select moisturizers that contain almond
        .filter(p -> p.getName().toLowerCase().contains("almond")) 
        // select least expensive moisturizer
        .sorted(Comparator.comparing(Product::getPrice))
        .collect(Collectors.toList());
    }
    if (Serenity.sessionVariableCalled("shopFor") == "S") {
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
      Serenity.setSessionVariable("secondProductName").to(sortedArray.get(0).getName());
      Serenity.setSessionVariable("secondProductPrice").to(sortedArray.get(0).getPrice());
    } catch (IndexOutOfBoundsException ex) {
      ex.printStackTrace();
    }
  }

  @When("Customer clicks cart button")
  public void clickCartButton() {
    product.clickCartButton();
  }

  @Then("Items are displayed in cart")
  public void itemsDisplayedInCart() {
    // check first item
    softAssert.assertThat(cart.getItemName(cart.cartItemRows.get(0)))
              .isEqualTo(Serenity.sessionVariableCalled("firstProductName"));
    softAssert.assertThat(cart.getItemPrice(cart.cartItemRows.get(0)))
              .isEqualTo(Serenity.sessionVariableCalled("firstProductPrice"));
    // check second item
    softAssert.assertThat(cart.getItemName(cart.cartItemRows.get(1)))
              .isEqualTo(Serenity.sessionVariableCalled("secondProductName"));
    softAssert.assertThat(cart.getItemPrice(cart.cartItemRows.get(1)))
              .isEqualTo(Serenity.sessionVariableCalled("secondProductPrice"));
    // check total price
    Integer sum = (Integer)Serenity.sessionVariableCalled("firstProductPrice") + 
                  (Integer)Serenity.sessionVariableCalled("secondProductPrice");
    softAssert.assertThat(cart.getTotalPriceText())
              .isEqualTo("Total: Rupees "+sum.toString());
  }

  @When("Customer enters payment details")
  public void enterPaymentDetails() {
    cart.openPaymentForm();
    cart.enterEmail("xy@email.cz");
    cart.enterCardNumber("4242424242424242");
    cart.enterCardExp("1224");
    cart.enterCardCvc("123");
    cart.enterZipCode("12345");
  }

  @When("Customer submits payment form")
  public void submitPaymentForm() {
    cart.submitPaymentForm();
  }

  @Then("Payment was successful")
  public void paymentSuccessful() {
    softAssert.assertThat(confirm.getTitle())
              .isEqualTo("PAYMENT SUCCESS");
    softAssert.assertThat(confirm.getText())
              .isEqualTo("Your payment was successful. You should receive a follow-up call from our sales team.");
  }
  
}