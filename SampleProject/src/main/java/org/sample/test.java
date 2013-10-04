package org.sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.dsl.Initialise.InitDSL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class test {

	
	/*public static void main(String[] args) throws InterruptedException {
		 WebDriver driver = new FirefoxDriver();
		 driver.manage().window().maximize();
		 driver.get("http://127.0.0.1/wordpress/wp-admin/");
		 driver.findElement(By.id("user_login")).sendKeys("admin");
		 Thread.sleep(2000);
		 driver.findElement(By.id("user_pass")).sendKeys("test");
		 System.out.println(driver.findElement(By.id("wp-submit")).getAttribute("test"));
		 System.out.println(driver.findElement(By.id("wp-submit")).isDisplayed());
         System.out.println(driver.findElement(By.id("rememberme")).isEnabled());
         System.out.println(driver.findElement(By.id("rememberme")).isSelected());
         driver.findElement(By.id("rememberme")).click();
         System.out.println(driver.findElement(By.id("rememberme")).isSelected());
		 driver.findElement(By.id("wp-submit")).click();		 
		 driver.findElement(By.id("title")).sendKeys("testPost_Title");
		 driver.findElement(By.id("content")).sendKeys("testPost_Content");
		 driver.findElement(By.id("publish")).click();
		 
		 
		 
		 driver.findElement(By.xpath("//li[@id='menu-posts']/a/div[3]")).click();		 
		 Select select = new Select(driver.findElement(By.xpath("//form[@id='posts-filter']/div[1]/div[1]/select")));		 
		 select.selectByVisibleText("Edit");
		 
		 driver.quit();

	}*/
	
	static Properties prop=null;
	
	public static void main(String[] args) throws InterruptedException {
		try
		{
			getConfig();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		String packageList = prop.getProperty("browser");
		System.out.println(packageList);

		 
    	
	}
	
	private static void getConfig() throws IOException {
		prop = new Properties();
		InputStream in = InitDSL.class.getClassLoader().getResourceAsStream("config.properties");
		prop.load(in);
	}

}
