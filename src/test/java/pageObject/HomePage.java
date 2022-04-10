package pageObject;

import pageObject.BasePage;
import defs.PropertiesHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

  WebDriver driver;
  WebDriverWait wait;

  @FindBy(how = How.CSS, using = "#temperature")
  public WebElement tempCont;

  @FindBy(how = How.CSS, using = "a[href='/moisturizer']")
  public WebElement moistButton;

  @FindBy(how = How.CSS, using = "a[href='/sunscreen']") 
  public WebElement sunButton;

  public HomePage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    wait = new WebDriverWait(driver, 10);
    //Initialise Elements
    PageFactory.initElements(driver, this);
  }

  /**
  * Load text from temperature container
  * Extract and parse numerical value from text
  * @return temperature value
  */
  public Integer getTempValue() {
    try {
      String value = tempCont.getText();
      value = value.replaceAll("[^0-9]", "");
      return Integer.parseInt(value);
    } catch (NumberFormatException ex) {
      ex.printStackTrace();
      return 0;
    }
  }

  /**
  * Click button 'Buy Moisturizers'
  */
  public void clickMoistButton() {
    moistButton.click();
    this.waitForPagetoLoad();
  }

  /**
  * Click button 'Buy Sunscreens'
  */
  public void clickSunButton() {
    sunButton.click();
    this.waitForPagetoLoad();
  }

}