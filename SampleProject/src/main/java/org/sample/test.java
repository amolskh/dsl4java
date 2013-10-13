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

public class test {
	
	/*public static void main(String[] args) throws InterruptedException {
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
	}*/	
	
	public static void main(String[] args){
		String in="({SettingsSectionDisplayed} & {OpenSectionDisplayed})";
		
		
		//String in = "({item1.test} & {item2.qa})";

		Pattern p = Pattern.compile("\\{(.*?)\\}");
		Matcher m = p.matcher(in);

		while(m.find()) {
			String temp=m.group(1);
			String test ="InitDSL.getVariableValue({"+temp+"})";
			in=in.replace("{"+temp+"}", test);
		   // System.out.println(m.group(1));
					
		   // System.out.println(test);
		}	
		
		System.out.println(in);
		
		
	}
}
