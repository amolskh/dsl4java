package org.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DriverInstance {
	public static  WebDriver browser=null;

	private static DriverInstance driverInstance;
	private DriverInstance() {}

	public static WebDriver getDriverInstance() {
		if (driverInstance == null) {
			synchronized (DriverInstance.class) {
				if (driverInstance == null ) {
					driverInstance = new DriverInstance();
					browser = new FirefoxDriver();
				}
			}
		}
		return browser;
	}
}



