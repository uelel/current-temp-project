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

public class ConfirmPage extends BasePage {

  WebDriver driver;
  WebDriverWait wait;

  @FindBy(how = How.CSS, using = "h2")
  public WebElement title;

  @FindBy(how = How.CSS, using = "div.row p")
  public WebElement text;

  public ConfirmPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    wait = new WebDriverWait(driver, 10);
    //Initialise Elements
    PageFactory.initElements(driver, this);
  }

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