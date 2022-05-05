package eshop.pages;

import java.util.List;
import java.util.ArrayList;

import net.thucydides.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

@DefaultUrl("/cart")
public class CartPage extends PageObject {

  @FindBy(css = "h2")
  public WebElementFacade title;

  @FindBy(css = "table tbody tr")
  public List<WebElementFacade> cartItemRows;

  @FindBy(css = "#total")
  public WebElementFacade totalPrice;

  @FindBy(css = "button.stripe-button-el")
  public WebElementFacade stripeButton;

  @FindBy(css = "iframe")
  public WebElementFacade stripeIframe;

  @FindBy(css = "input#email")
  public WebElementFacade emailField;

  @FindBy(css = "input#card_number")
  public WebElementFacade cardNumberField;

  @FindBy(css = "input#cc-exp")
  public WebElementFacade cardExpField;

  @FindBy(css = "input#cc-csc")
  public WebElementFacade cardCvcField;

  @FindBy(css = "input#billing-zip")
  public WebElementFacade zipField;

  @FindBy(css = "button#submitButton")
  public WebElementFacade submitPaymentButton;

  /**
  * Get item name from given item row
  * @param itemRow
  * @return item name
  */
  public String getItemName(WebElementFacade itemRow) {
    String name = "";
    try {
      name = itemRow.then(By.cssSelector("td:nth-child(1)")).getText();
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    }
    return name;
  }

  /**
  * Get item price from given item row
  * @param itemRow
  * @return item price
  */
  public Integer getItemPrice(WebElementFacade itemRow) {
    String price = "";
    try {
      price = itemRow.then(By.cssSelector("td:nth-child(2)")).getText();
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    }
    return Integer.parseInt(price);
  }

  /**
  * Get text of total price container
  * @return text
  */
  public String getTotalPriceText() {
    return totalPrice.getText();
  }

  /**
  * Click 'Pay with card' button
  */
  public void openPaymentForm() {
    stripeButton.click();
    //this.waitForPagetoLoad();
    // Switch to stripe iframe
    getDriver().switchTo().frame(stripeIframe);
  }

  /**
  * Fill email field with given text
  */
  public void enterEmail(String text) {
    try {
      emailField.sendKeys(text);
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    }
  }

  /**
  * Fill card number field with given text
  */
  public void enterCardNumber(String text) {
    try {
      for (char ch:text.toCharArray()) {
        cardNumberField.sendKeys(String.valueOf(ch));
        try { Thread.sleep(100); } catch (InterruptedException ex) { }
      }
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    }
  }

  /**
  * Fill card expriration field with given text
  */
  public void enterCardExp(String text) {
    try {
      for (char ch:text.toCharArray()) {
        cardExpField.sendKeys(String.valueOf(ch));
        try { Thread.sleep(100); } catch (InterruptedException ex) { }
      }
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    }
  }

  /**
  * Fill card CVC field with given text
  */
  public void enterCardCvc(String text) {
    try {
      cardCvcField.sendKeys(text);
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    }
  }

  /**
  * Fill zip code field with given text
  */
  public void enterZipCode(String text) {
    try {
      zipField.sendKeys(text);
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    }
  }

  /**
  * Click on button that submits payment form
  */
  public void submitPaymentForm() {
    submitPaymentButton.click();
    waitFor(ExpectedConditions.urlContains("/confirmation"));
    getDriver().switchTo().defaultContent();
    //this.waitForPagetoLoad();
  }

}