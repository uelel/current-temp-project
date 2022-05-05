package eshop.pages;

import net.thucydides.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;

import org.openqa.selenium.NoSuchElementException;

@DefaultUrl("/confirmation")
public class ConfirmPage extends PageObject {

  @FindBy(css = "h2")
  public WebElementFacade title;

  @FindBy(css = "div.row p")
  public WebElementFacade text;

  /**
  * Get title text
  * @return text
  */
  public String getTitle() {
    return title.getText();
  }

  /**
  * Get description text
  * @return text
  */
  public String getText() {
    return text.getText();
  }

}