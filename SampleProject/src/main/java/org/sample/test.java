package org.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dsl.Initialise.InitDSL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class test {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new FirefoxDriver();		 
		driver.manage().window().maximize();
		driver.get("http://127.0.0.1/wordpress/wp-admin/");
		driver.findElement(By.id("user_login")).sendKeys("admin");
		Thread.sleep(3000);
		driver.findElement(By.id("user_pass")).sendKeys("test");
		driver.findElement(By.id("wp-submit")).click();
		//Actions act = new Actions(driver);
		//act.clickAndHold()(driver.findElement(By.xpath("//li[@id='wp-admin-bar-my-account']/a")));
		//driver.findElement(By.xpath("//li[@id='wp-admin-bar-my-account']/a")).click();
		//act.clickAndHold();
		
		Actions clkAndHld = new Actions(driver);
		clkAndHld.moveToElement(driver.findElement(By.xpath("//li[@id='wp-admin-bar-my-account']/a"))).build().perform();
		
		driver.findElement(By.linkText("Log Out")).click();
	}


}
