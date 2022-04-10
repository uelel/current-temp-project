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

public class ProductPage extends BasePage {

  /**
  * Define custom Product class that contains important product attributes
  * @property element - web element object of given product
  * @property name - product name
  * @property price - product price
  */
  public class Product {

    WebElement element;
    String name;
    Integer price;

    public Product (WebElement el, 
                    String name,
                    Integer price) {
      this.element = el;
      this.name = name;
      this.price = price;
    }

    public WebElement getEl() {
      return element;
    }
    public String getName() {
      return name;
    }
    public Integer getPrice() {
      return price;
    }

    public void setEl(WebElement el) {
      this.element = el;
    }
    public void setName(String name) {
      this.name = name;
    }
    public void setPrice(Integer price) {
      this.price = price;
    }

  }

  WebDriver driver;
  WebDriverWait wait;
  
  public List<Product> productArray = new ArrayList<>();

  @FindBy(how = How.CSS, using = "h2")
  public WebElement title;

  @FindBys({@FindBy(how = How.CSS, using = ".container .top-space-50 div")})
  public List<WebElement> productContArray;

  @FindBy(how = How.CSS, using = "button[onclick='goToCart()']")
  public WebElement cartButton;

  public ProductPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    wait = new WebDriverWait(driver, 10);
    //Initialise Elements
    PageFactory.initElements(driver, this);
    // Fill productArray with Product objects
    for (WebElement cont:productContArray) {
      productArray.add(new Product(
        cont,
        this.getProductName(cont),
        this.getProductPrice(cont)
      ));
    }
  }

  /**
  * Get title text
  */
  public String getTitle() {
    return this.title.getText();
  }

  /**
  * Get product name from given product container
  * @param productCont
  * @return product name
  */
  public String getProductName(WebElement productCont) {
    String name = "";
    try {
      name = productCont.findElement(By.cssSelector("p.font-weight-bold")).getText();
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    }
    return name;
  }

  /**
  * Get product price from given product container
  * @param productCont
  * @return product price
  */
  public Integer getProductPrice(WebElement productCont) {
    String priceText = "";
    Integer price = 0;
    try {
      priceText = productCont.findElement(By.cssSelector("p:not([class])")).getText();
      priceText = priceText.replaceAll("[^0-9]", "");
      price = Integer.parseInt(priceText);
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    } catch (NumberFormatException ex) {
      ex.printStackTrace();
    }
    return price;
  }

  /**
  * Click of Buy button for given product
  * @param productCont - container of given product
  */
  public void clickBuyButton(WebElement productCont) {
    try {
      productCont.findElement(By.cssSelector("button")).click();
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    }
  }

  /**
  * Click cart button
  */
  public void clickCartButton() {
    cartButton.click();
    this.waitForPagetoLoad();
  }

}