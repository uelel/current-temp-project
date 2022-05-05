package eshop.pages;

import java.util.List;
import java.util.ArrayList;

import net.thucydides.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

//@DefaultUrl("")
public class ProductPage extends PageObject {

  /**
  * Define custom Product class that contains important product attributes
  * @property element - web element object of given product
  * @property name - product name
  * @property price - product price
  */
  public class Product {

    WebElementFacade element;
    String name;
    Integer price;

    public Product (WebElementFacade el, 
                    String name,
                    Integer price) {
      this.element = el;
      this.name = name;
      this.price = price;
    }

    public WebElementFacade getEl() {
      return element;
    }
    public String getName() {
      return name;
    }
    public Integer getPrice() {
      return price;
    }

    public void setEl(WebElementFacade el) {
      this.element = el;
    }
    public void setName(String name) {
      this.name = name;
    }
    public void setPrice(Integer price) {
      this.price = price;
    }

  }
  
  public List<Product> productArray = new ArrayList<>();

  @FindBy(css = "h2")
  public WebElementFacade title;

  @FindBy(css = ".container .top-space-50 div")
  public List<WebElementFacade> productContArray;

  @FindBy(css = "button[onclick='goToCart()']")
  public WebElementFacade cartButton;

  /**
  * Load all products on page
  * Save details into array of Product objects
  */
  public void loadProducts() {
    for (WebElementFacade cont:productContArray) {
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
    return title.getText();
  }

  /**
  * Get product name from given product container
  * @param productCont
  * @return product name
  */
  public String getProductName(WebElementFacade productCont) {
    String name = "";
    try {
      name = productCont.then(By.cssSelector("p.font-weight-bold")).getText();
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
  public Integer getProductPrice(WebElementFacade productCont) {
    String priceText = "";
    Integer price = 0;
    try {
      priceText = productCont.then(By.cssSelector("p:not([class])")).getText();
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
  * Click on Buy button for given product
  * @param productCont - container of given product
  */
  public void clickBuyButton(WebElementFacade productCont) {
    try {
      productCont.then("button").click();
    } catch (NoSuchElementException ex) {
      ex.printStackTrace();
    }
  }

  /**
  * Click cart button
  */
  public void clickCartButton() {
    cartButton.click();
    //this.waitForPagetoLoad();
  }

}