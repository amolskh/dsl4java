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
		 //WebDriver driver = new FirefoxDriver();
		
		 File file = new File("src/main/resources/chromedriver.exe");
		 System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		 WebDriver driver = new ChromeDriver();
		 driver.manage().window().maximize();
		 driver.get("http://127.0.0.1/wordpress/wp-admin/");
		 driver.findElement(By.id("user_login")).sendKeys("admin");
		 Thread.sleep(2000);
		 driver.findElement(By.id("user_pass")).sendKeys("test");
		 driver.findElement(By.id("wp-submit")).click();		 
		 driver.findElement(By.id("title")).sendKeys("testPost_Title");
		 driver.findElement(By.id("content")).sendKeys("testPost_Content");
		 driver.findElement(By.id("publish")).click();
		 Thread.sleep(6000);
		 System.out.println(driver.findElement(By.linkText("View post")).isDisplayed());
		 System.out.println(driver.findElement(By.linkText("Edit post")).isDisplayed());
		 
		 
		 
		
		 
		 driver.quit();

	}
	
	
	

}
