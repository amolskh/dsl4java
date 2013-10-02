package org.sample;

import org.dsl.Initialise.InitDSL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
    	//InitDSL.initialise();
       
    	WebDriver driver = new FirefoxDriver();
    	driver.get("http://en.wikipedia.org/wiki/Flag");
    	driver.findElement(By.linkText("View history")).click();
    	Thread.sleep(10000);
    }
}
