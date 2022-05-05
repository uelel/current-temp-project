package eshop.pages;

import net.thucydides.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;

import org.openqa.selenium.support.ui.ExpectedConditions;

@DefaultUrl("/")
public class HomePage extends PageObject {

  @FindBy(css = "#temperature")
  public WebElementFacade tempCont;

  @FindBy(css = "a[href='/moisturizer']")
  public WebElementFacade moistButton;

  @FindBy(css = "a[href='/sunscreen']") 
  public WebElementFacade sunButton;

  /**
  * Load text from temperature container
  * Extract and parse numerical value from text
  * @return temperature value
  */
  public Integer getTempValue() {
    waitFor(ExpectedConditions.visibilityOf(tempCont));
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
    //this.waitForPagetoLoad();
  }

  /**
  * Click button 'Buy Sunscreens'
  */
  public void clickSunButton() {
    sunButton.click();
    //this.waitForPagetoLoad();
  }

}