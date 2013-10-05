package org.selenium.driver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.dsl.Initialise.InitDSL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverInstance {
	public static  WebDriver browser=null;

	private static DriverInstance driverInstance;
	private static File file=null;

	private DriverInstance() {}

	public static void setDriverInstance(Object obj){
		driverInstance=(DriverInstance)obj;
	}

	public static WebDriver getDriverInstance() {
		String browserName = InitDSL.runTimeVars.get("browser").toString();		
		System.out.println("Executing testcase on: "+browserName);

		if (driverInstance == null) {
			synchronized (DriverInstance.class) {
				if (driverInstance == null ) {
					driverInstance = new DriverInstance();
					switch (browserName){
					case "firefox":
					{
						browser = new FirefoxDriver();
						break;
					}
					case "ie":
					{
						file = new File("src/main/resources/IEDriverServer.exe");
						System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
						browser = new InternetExplorerDriver();
						break;
					}
					case "chrome":
					{
						file = new File("src/main/resources/chromedriver.exe");
						System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
						browser = new ChromeDriver();
						break;
					}
					default:
					{
						System.out.println("Invalid browser entry");
						break;
					}
					}
					browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					browser.manage().window().maximize();
				}
			}
		}
		return browser;
	}
}



