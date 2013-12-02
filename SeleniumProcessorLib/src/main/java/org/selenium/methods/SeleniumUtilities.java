package org.selenium.methods;

import java.util.List;
import java.util.Set;
import org.dsl.Initialise.InitDSL;
import org.dsl.annotation.DSL;
import org.dsl.exception.DSLExecFailException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.selenium.driver.DriverInstance;
import org.testng.Assert;

public class SeleniumUtilities {
	static WebDriver driver=null;
		
	public WebElement getElement(String attribute,String value){
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
		return element;
	}
	
	
	
	public List<WebElement> getElements(String attribute,String value){
		List<WebElement> elements=null;
		switch(attribute){
		case "id":
			elements=driver.findElements(By.id(value));
			break;
		case "className":
			elements=driver.findElements(By.className(value));
			break;
		case "cssSelector":
			elements=driver.findElements(By.cssSelector(value));
			break;
		case "linkText":
			elements=driver.findElements(By.linkText(value));
			break;
		case "name":
			elements=driver.findElements(By.name(value));
			break;
		case "partialLinkText":
			elements=driver.findElements(By.partialLinkText(value));
			break;
		case "tagName":
			elements=driver.findElements(By.tagName(value));
			break;
		case "xpath":
			elements=driver.findElements(By.xpath(value));
			break;
		default:
			System.out.println("Attribute not valid");
		} 
		return elements;
	}
	

	@DSL(commName = "LoadUrl", commRegex = {".*"}, commSyntax = "Load {0}")
	public void loadUrl(String url) {
		driver.get(InitDSL.getVariableValue(url).toString());			
	}

	@DSL(commName = "CloseBrowser", commRegex = {}, commSyntax = "Close browser")
	public void close() {
		driver.close();		
	}

	@DSL(commName = "QuitBrowser", commRegex = {}, commSyntax = "Quit browser")
	public void quit() {
		driver.quit();	
	}

	@DSL(commName = "Type",commRegex = {".*",".*","[a-zA-z]{1,}",".*"}, commSyntax = "Type {0} in {1} with {2} = {3}")
	public void type(String text,String elementType, String attribute,String value) throws InterruptedException {
		Thread.sleep(1000);
		WebElement element=getElement(attribute,value);
		if(element!= null){
			element.clear();
			element.sendKeys(text);
		}
	}

	@DSL(commName = "Click",commRegex = {".*","[a-zA-z]{1,}",".*"}, commSyntax = "Click {0} with {1} = {2}")
	public void click(String elementType,String attribute,String value) {
		WebElement element=getElement(attribute,value);
		if(element!= null){
			element.click();
		}    	
	}

	@DSL(commName = "VerifyEqual",commRegex = {".*",".*"}, commSyntax = "{0} VerifyEqual {1}")
	public void verifyEqual(Object obj, Object objectExp) throws DSLExecFailException{
		Object objectActual =InitDSL.getVariableValue(obj.toString());
		Object objectExpected = (Object)objectExp;

		System.out.println("Actual Value = "+objectActual.toString());
		System.out.println("Expected Value = "+objectExpected.toString());

		Assert.assertEquals(objectActual.toString(),objectExpected.toString());	

	}

	@DSL(commName = "GetPageTitle",commRegex = {}, commSyntax = "Get page title")
	public String getPageTitle(){
		String pageTitle = driver.getTitle();
		return pageTitle.trim();
	}

	@DSL(commName = "GetCurrentPageUrl",commRegex = {}, commSyntax = "Get current page url")
	public String getCurrentPageUrl(){
		String currentPageUrl = driver.getCurrentUrl();
		return currentPageUrl;
	}

	@DSL(commName = "MaximizeBrowserWindow",commRegex = {}, commSyntax = "Maximize browser window")
	public void maximizeBrowserWindow(){
		driver.manage().window().maximize();
	}

	@DSL(commName = "Sleep",commRegex = {"[0-9]{1,}"}, commSyntax = "Sleep {0}")
	public void sleep(int timeInSeconds) throws InterruptedException{
		Thread.sleep(1000*timeInSeconds);
	}	

	@DSL(commName = "InvokeBrowser",commRegex = {}, commSyntax = "Invoke browser")
	public static WebDriver invokeBrowser(){
		DriverInstance.setDriverInstance(null);
		WebDriver driverInstance=DriverInstance.getDriverInstance();
		driver=driverInstance;	
		return driver;
	}

	@DSL(commName = "Select",commRegex = {".*",".*","[a-zA-z]{1,}",".*"}, commSyntax = "Select {0} from {1} with {2} = {3}")
	public void select(String textToBeSelected, String elementType, String attribute,String value) {
		WebElement element=getElement(attribute,value);
		Select select= null;
		if(element!= null){
			select = new Select(element);
			select.selectByVisibleText(textToBeSelected);
		}    	
	}

	@DSL(commName = "GetElementText",commRegex = {".*","[a-zA-z]{1,}",".*"}, commSyntax = "Get text of {0} with {1} = {2}")
	public String getElementText(String elementType, String attribute,String value) throws InterruptedException {
		WebElement element=getElement(attribute,value);
		String text=null;
		if(element!= null){
			text=element.getText().trim();			
		}
		return text;
	}

	@DSL(commName = "GetElementTagName",commRegex = {".*","[a-zA-z]{1,}",".*"}, commSyntax = "Get tagname of {0} with {1} = {2}")
	public String getElementTagName(String elementType, String attribute,String value) throws InterruptedException {
		WebElement element=getElement(attribute,value);
		String tagName=null;
		if(element!= null){
			tagName=element.getTagName();			
		}
		return tagName;
	}

	@DSL(commName = "GetElementAttributeValue",commRegex = {".*",".*","[a-zA-z]{1,}",".*"}, commSyntax = "Get value of attribute: {0} of {1} with {2} = {3}")
	public String getElementAttributeValue(String attributeValue,String elementType, String attribute,String value) throws InterruptedException {
		WebElement element=getElement(attribute,value);
		String attribVal=null;
		if(element!= null){
			attribVal=element.getAttribute(attributeValue);
		}
		return attribVal;
	}

	@DSL(commName = "IsElementDisplayed",commRegex = {".*","[a-zA-z]{1,}",".*"}, commSyntax = "Is {0} with {1} = {2} displayed")
	public boolean isElementDisplayed(String elementType,String attribute,String value) {
		WebElement element=getElement(attribute,value);
		boolean flag=false;
		if(element!= null){
			flag=element.isDisplayed();
		}    
		return flag;
	}

	@DSL(commName = "IsElementEnabled",commRegex = {".*","[a-zA-z]{1,}",".*"}, commSyntax = "Is {0} with {1} = {2} enabled")
	public boolean isElementEnabled(String elementType,String attribute,String value) {
		WebElement element=getElement(attribute,value);
		boolean flag=false;
		if(element!= null){
			flag=element.isEnabled();
		}    
		return flag;
	}

	@DSL(commName = "IsElementSelected",commRegex = {".*","[a-zA-z]{1,}",".*"}, commSyntax = "Is {0} with {1} = {2} selected")
	public boolean isElementSelected(String elementType,String attribute,String value) {
		WebElement element=getElement(attribute,value);
		boolean flag=false;
		if(element!= null){
			flag=element.isSelected();
		}    
		return flag;
	}

	@DSL(commName = "ClearElement",commRegex = {".*","[a-zA-z]{1,}",".*"}, commSyntax = "Clear {0} with {1} = {2}")
	public void clearElement(String elementType,String attribute,String value) {
		WebElement element=getElement(attribute,value);
		if(element!= null){
			element.clear();
		}    	
	}

	@DSL(commName = "SwitchToWindowWithTitle",commRegex = {".*","[a-zA-z]{1,}",".*"}, commSyntax = "Switch to window with title: {0}")
	public String switchToWindowWithTitle(String pageTitle) {
		String parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
		WebDriver popup = null;
		Set<String> windowHandles = driver.getWindowHandles();
		while(windowHandles.iterator().hasNext()) { 
			String windowHandle = windowHandles.iterator().next(); 
			popup = driver.switchTo().window(windowHandle);
			if (popup.getTitle().equals(pageTitle)) {
				break;
			}
		}
		return parentWindowHandle;
	}
		
	@DSL(commName = "SwitchToDefaultFrame",commRegex = {}, commSyntax = "Switch to default frame")
	public void switchToDefaultFrame() {
		driver.switchTo().defaultContent();
	}
	
	@DSL(commName = "SwitchToFrame",commRegex = {".*","[a-zA-z]{1,}",".*"}, commSyntax = "Switch to {0} with {1} = {2}")
	public void switchToFrame(String elementType,String attribute,String value) {
		WebElement element=getElement(attribute,value);
		if(element!= null){
			driver.switchTo().defaultContent();
			driver.switchTo().frame(element);
		}  
	}
	
	@DSL(commName = "HoverOver",commRegex = {".*","[a-zA-z]{1,}",".*"}, commSyntax = "HoverOver {0} with {1} = {2}")
	public void hoverOver(String elementType,String attribute,String value) {
		WebElement element=getElement(attribute,value);
		if(element!= null){			
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();			
		}  
	}
	
	@DSL(commName = "GetTotalNumberOfSimilarElements",commRegex = {".*","[a-zA-z]{1,}",".*"}, commSyntax = "Get total number of {0} with {1} = {2}")
	public int getTotalNumberOfSimilarElements(String elementType, String attribute,String value){
		List<WebElement> elements=getElements(attribute,value);
		int listSize=0;
		if(!elements.isEmpty()){
			listSize=elements.size();		
		}
		return listSize;
	}
}
