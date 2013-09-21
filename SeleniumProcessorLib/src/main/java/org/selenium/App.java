package org.selenium;

import org.dsl.Initialise.InitDSL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class App 
{
	
	
	
    public static void main( String[] args ) throws InterruptedException
     {
    	//DriverInstance driverInstance=DriverInstance.getInstance();
    	
      // WebDriver driver = new FirefoxDriver();
    	
    	// WebDriver driver = DriverInstance.getDriver();
    
    	/* WebDriver driver=DriverInstance.getDriverInstance();
        
        driver.get("http://127.0.0.1/wordpress/wp-admin");
        driver.findElement(By.id("user_login")).sendKeys("admin");
        
        Thread.sleep(10000);
        driver.quit();*/
        
    	//InitDSL.initialise();
    	
    	//Thread.sleep(10000);
    	
    	WebDriver driver = new FirefoxDriver();
    	driver.get("http://www.gmail.com");
    	driver.findElement(By.id("Email")).sendKeys("admin");
    	driver.findElement(By.id("Passwd")).sendKeys("test");
    	driver.close();
    	
    	
    	
        
    }
}
