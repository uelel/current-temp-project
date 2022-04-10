package defs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PropertiesHandler {

  //Create a object for class properties 
  static Properties prop = new Properties();
  //defining the project path
  static String projectPath = System.getProperty("user.dir");

  public static void loadFromFile(String path) {
    try {
      InputStream input = new FileInputStream(projectPath+path);
      prop.load(input);
    } catch (Exception exp) {
      exp.printStackTrace();
    }
  }

  public static String get(String name) {
    String value = "";
    try {
      value = prop.getProperty(name);
    } catch (Exception exp) {
      exp.printStackTrace();
    }
    return value;
  }

  public static void set(String name, Object value) {
    try {
      //create a object for class OuputStream
      //OutputStream output = new FileOutputStream(projectPath + "/src/test/java/test/config.properties");
      //Load properties file and set firefox
      prop.setProperty(name, value.toString());
    } catch (Exception exp) {
      exp.printStackTrace();
    }
  }
}