package org.sample;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		 WebDriver driver = new FirefoxDriver();
		 driver.get("http://127.0.0.1/wordpress/wp-admin/");
		 driver.findElement(By.id("user_login")).sendKeys("admin");
		 Thread.sleep(2000);
		 driver.findElement(By.id("user_pass")).sendKeys("test");
		 driver.findElement(By.id("wp-submit")).click();
		 
		 
		 

	}
    
     
   
     
	
	
}
