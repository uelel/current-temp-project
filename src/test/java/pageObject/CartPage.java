package pageObject;

import java.util.List;
import java.util.ArrayList;
import pageObject.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

  WebDriver driver;
  WebDriverWait wait;

  @FindBy(how = How.CSS, using = "h2")
  public WebElement title;

  @FindBys({@FindBy(how = How.CSS, using = "table tbody tr")})
  public List<WebElement> cartItemRows;

  @FindBy(how = How.CSS, using = "#total")
  public WebElement totalPrice;

  @FindBy(how = How.CSS, using = "button.stripe-button-el")
  public WebElement stripeButton;

  @FindBy(how = How.CSS, using = "iframe")
  public WebElement stripeIframe;

  @FindBy(how = How.CSS, using = "input#email")
  public WebElement emailField;

  @FindBy(how = How.CSS, using = "input#card_number")
  public WebElement cardNumberField;

  @FindBy(how = How.CSS, using = "input#cc-exp")
  public WebElement cardExpField;

  @FindBy(how = How.CSS, using = "input#cc-csc")
  public WebElement cardCvcField;

  @FindBy(how = How.CSS, using = "input#billing-zip")
  public WebElement zipField;

  @FindBy(how = How.CSS, using = "button#submitButton")
  public WebElement submitPaymentButton;

  public CartPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    wait = new WebDriverWait(driver, 10);
    //Initialise Elements
    PageFactory.initElements(driver, this);
  }

  /**
  * Get item name from given item row
  * @param itemRow
  * @return item name
  */
  public String getItemName(WebElement itemRow) {
    String name = "";
    try {
      name = itemRow.findElement(By.cssSelector("td:nth-child(1)")).getText();
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
  public String getItemPrice(WebElement itemRow) {
    String price = "";
    try {
      price = itemRow.findElement(By.cssSelector("td:nth-child(2)")).getText();
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    }
    return price;
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
    this.waitForPagetoLoad();
    // Switch to stripe iframe
    driver.switchTo().frame(stripeIframe);
  }

  /**
  * Fill email field with given text
  */
  public void enterEmail(String text) {
    try {
      emailField.sendKeys(text);
    } catch (NoSuchElementException ex) {
      this.print(ex);
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
    wait.until(ExpectedConditions.urlContains("/confirmation"));
    this.waitForPagetoLoad();
  }

}