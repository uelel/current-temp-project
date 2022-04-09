package pageObject;

import java.net.URL;
import java.lang.Exception;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

 	WebDriver driver;

  public BasePage (WebDriver driver) {
  	this.driver = driver;
  }

  public void print(Object text) {
    System.out.println(text);
  }

}