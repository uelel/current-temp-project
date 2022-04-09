package pageObject;

import pageObject.BasePage;
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
  private WebElement tempCont;

  @FindBy(how = How.CSS, using = "a[href='/moisturizer']")
  private WebElement moistButton;

  @FindBy(how = How.CSS, using = "a[href='/sunscreen']") 
  private WebElement sunButton;

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
  * Load homepage repeatedly until displayed temperature 
  * is lower that 19 degrees
  */
  public void loadPageWithTempLower19() {
    Integer temp;
    driver.get("https://weathershopper.pythonanywhere.com");
    wait.until(ExpectedConditions.visibilityOf(tempCont));
    temp = this.getTempValue();
    while (temp >= 19) {
      driver.get("https://weathershopper.pythonanywhere.com");
      wait.until(ExpectedConditions.visibilityOf(tempCont));
      temp = this.getTempValue();
    }
  }

  /**
  * Load homepage repeatedly until displayed temperature 
  * is higher than 34 degrees
  */
  public void loadPageWithTempHigher34() {
    Integer temp;
    driver.get("https://weathershopper.pythonanywhere.com");
    wait.until(ExpectedConditions.visibilityOf(tempCont));
    temp = this.getTempValue();
    while (temp <= 34) {
      driver.get("https://weathershopper.pythonanywhere.com");
      wait.until(ExpectedConditions.visibilityOf(tempCont));
      temp = this.getTempValue();
    }
  }

  /**
  * Click button 'Buy Moisturizers'
  */
  public void clickMoistButton() {
    moistButton.click();
  }

  /**
  * Click button 'Buy Sunscreens'
  */
  public void clickSunButton() {
    sunButton.click();
  }

}