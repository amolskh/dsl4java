package org.selenium.methods;

import org.dsl.Initialise.InitDSL;
import org.dsl.annotation.DSL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.driver.DriverInstance;
import org.testng.Assert;

public class SeleniumUtilities {
	static WebDriver driver=DriverInstance.getDriverInstance();
	WebDriverWait wait = new WebDriverWait(driver,20); 
		
	@DSL(commName = "LoadUrl", commRegex = {".*"}, commSyntax = "LoadUrl {0}")
	public void loadUrl(String url) {
		driver.get(url);		
	}

	@DSL(commName = "CloseBrowser", commRegex = {}, commSyntax = "CloseBrowser")
	public void close() {
		driver.close();		
	}

	@DSL(commName = "QuitBrowser", commRegex = {}, commSyntax = "QuitBrowser")
	public void quit() {
		driver.quit();	
	}

	@DSL(commName = "Type",commRegex = {"[a-zA-z0-9{}%:/.-]{1,}","[a-zA-z]{1,}","[a-zA-z0-9{}%:/.-_]{1,}"}, commSyntax = "Type {0} in element with {1} = {2}")
	public void type(String text,String attribute,String value) throws InterruptedException {
		Thread.sleep(1000);
		WebElement element=null;
		switch(attribute){
		case "id":
			element=driver.findElement(By.id(value));
			break;
		case "className":
			element=driver.findElement(By.className(value));
			break;
		case "cssSelector":
			element=driver.findElement(By.cssSelector(value));
			break;
		case "linkText":
			element=driver.findElement(By.linkText(value));
			break;
		case "name":
			element=driver.findElement(By.name(value));
			break;
		case "partialLinkText":
			element=driver.findElement(By.partialLinkText(value));
			break;
		case "tagName":
			element=driver.findElement(By.tagName(value));
			break;
		case "xpath":
			element=driver.findElement(By.xpath(value));
			break;
		default:
			System.out.println("Attribute not valid");
		}  
		
		if(element!= null){
			element.clear();
			element.sendKeys(text);
		}
	}

	
	@DSL(commName = "Click",commRegex = {"[a-zA-z]{1,}",".*"}, commSyntax = "Click element with {0} = {1}")
	public void click(String attribute,String value) {
		WebElement element=null;
		switch(attribute){
		case "id":
			element=driver.findElement(By.id(value));
			break;
		case "className":
			element=driver.findElement(By.className(value));
			break;
		case "cssSelector":
			element=driver.findElement(By.cssSelector(value));
			break;
		case "linkText":
			element=driver.findElement(By.linkText(value));
			break;
		case "name":
			element=driver.findElement(By.name(value));
			break;
		case "partialLinkText":
			element=driver.findElement(By.partialLinkText(value));
			break;
		case "tagName":
			element=driver.findElement(By.tagName(value));
			break;
		case "xpath":
			element=driver.findElement(By.xpath(value));
			break;
		default:
			System.out.println("Attribute not valid");
		} 
		if(element!= null){
			element.click();
		}    	
	}

	@DSL(commName = "VerifyEqual",commRegex = {".*",".*"}, commSyntax = "{0} VerifyEqual {1}")
	public void verifyEqual(Object obj, Object objectExp){
		Object objectActual = (Object)InitDSL.runTimeVars.get(obj.toString());		
		Object objectExpected = (Object)objectExp;
		
		System.out.println("&&&&&&&&&&act"+objectActual.toString());
		System.out.println("&&&&&&&&&&exp"+objectExpected.toString());
		Assert.assertEquals(objectActual.toString(),objectExpected.toString());
		System.out.println("*********************After Assert********************");
	}
	
	@DSL(commName = "GetPageTitle",commRegex = {}, commSyntax = "GetPageTitle")
	public String getPageTitle(){
		String pageTitle = driver.getTitle();
		return pageTitle.trim();
	}
	
	@DSL(commName = "MaximizeBrowserWindow",commRegex = {}, commSyntax = "MaximizeBrowserWindow")
	public void maximizeBrowserWindow(){
		driver.manage().window().maximize();
	}
			
	@DSL(commName = "Sleep",commRegex = {"[0-9]{1,}"}, commSyntax = "Sleep {0}")
	public void sleep(int timeInSeconds) throws InterruptedException{
		Thread.sleep(1000*timeInSeconds);
	}
	
	
}
