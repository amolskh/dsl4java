package org.selenium.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DriverInstance {
	public static  WebDriver browser=null;

	private static DriverInstance driverInstance;
	private DriverInstance() {}
	
	public static void setDriverInstance(Object obj){
		driverInstance=(DriverInstance)obj;
	}

	public static WebDriver getDriverInstance() {
		if (driverInstance == null) {
			System.out.println("*******************************************Entered Null");
			synchronized (DriverInstance.class) {
				if (driverInstance == null ) {
					driverInstance = new DriverInstance();
					browser = new FirefoxDriver();
					browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					browser.manage().window().maximize();
				}
			}
		}
		return browser;
	}
}



