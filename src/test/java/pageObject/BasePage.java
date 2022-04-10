package pageObject;

import java.net.URL;
import java.lang.Exception;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;

public class BasePage {

 	WebDriver driver;
  WebDriverWait wait;

  public BasePage (WebDriver driver) {
  	this.driver = driver;
    wait = new WebDriverWait(driver, 10);
  }

  public void print(Object text) {
    System.out.println(text);
  }

  public void navigateTo(String url) {
    driver.get(url);
  }

  /**
  * Wait until visilibity of given web element
  * @param el
  */
  public void waitUntilVisibility(WebElement el) {
    wait.until(ExpectedConditions.visibilityOf(el));
  }

  /**
  * Wait for document ready state
  */
  public void waitForPagetoLoad() {
    long millis = 500;
    try { Thread.sleep(500); } catch (InterruptedException ex) { }
    do {
      try { Thread.sleep(500); } catch (InterruptedException ex) { }
      millis = millis + 500;
    } while (!(((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
            .equals("complete")) && millis <= 10000);
    //Reporter.log("Web page loading is complete in " + (millis / 1000) + "  Seconds");
  }

}