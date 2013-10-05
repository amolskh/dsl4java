package org.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.dsl.Initialise.InitDSL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class test {

	
	public static void main(String[] args) throws InterruptedException {
		 WebDriver driver = new FirefoxDriver();		 
		 driver.manage().window().maximize();
		 driver.get("http://127.0.0.1/wordpress/wp-admin/");
		 driver.findElement(By.id("user_login")).sendKeys("admin");
		 Thread.sleep(2000);
		 driver.findElement(By.id("user_pass")).sendKeys("test");
		 driver.findElement(By.id("wp-submit")).click();
		  System.out.println(driver.findElement(By.id("adv-settings")).isDisplayed());
		 if(!driver.findElement(By.id("adv-settings")).isDisplayed()){
				driver.findElement(By.id("show-settings-link")).click();
		 }
	     System.out.println(driver.findElement(By.id("adv-settings")).isDisplayed());
		 
		 
		 
		
		 
		 driver.quit();

	}
	
	
	

}
