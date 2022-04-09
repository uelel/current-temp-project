package pageObject;

import pageObject.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage {

  WebDriver driver;
  WebDriverWait wait;

  @FindBy(how = How.CSS, using = "h2")
  private WebElement title;

  public ProductPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    wait = new WebDriverWait(driver, 10);
    //Initialise Elements
    PageFactory.initElements(driver, this);
  }

  public String getTitle() {
    return this.title.getText();
  }

}