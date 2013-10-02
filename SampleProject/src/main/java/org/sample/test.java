package org.sample;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	/*public static void main(String[] args) throws InterruptedException {
		 WebDriver driver = new FirefoxDriver();
		 driver.get("http://127.0.0.1/wordpress/wp-admin/");
		 driver.findElement(By.id("user_login")).sendKeys("admin");
		 Thread.sleep(2000);
		 driver.findElement(By.id("user_pass")).sendKeys("test");
		 driver.findElement(By.id("wp-submit")).click();	 

	}*/

/*	public static void main(String[] args) throws InterruptedException {
		//String title = "Dashboard ‹ test — WordPress";
		//String command = "VerifyEqual " + title + " with Dashboard ‹ test — WordPress";
		// System.out.println(command);
		//String commandSyntax = "Print {0}";

		String command = "Print " +" with Dashboard ‹ test — WordPress";
		// System.out.println(command);
		//String commandSyntax = "VerifyEqual {0} with {1}";
		String commandSyntax = "Print {0}";
		String[] commandArr = commandSyntax.split(" ");
		String[] userCommandArr = command.split(" ");
		int i = 0, cnt = 0;
		while (i < commandArr.length) {
			if (commandArr[i].equals(userCommandArr[cnt])) {
				System.out.println(commandArr[i] + " is neglected");
				i++;
				cnt++;
			} else if (commandArr[i].matches("\\{[0-9]{1,2}\\}")) {
				System.out.println("Regex");
				String inpVal = "";
				if ((i + 1) < commandArr.length) {
					i = i + 1;
					String hold = commandArr[i];

					while (i < commandArr.length
							&& !hold.equals(userCommandArr[cnt])) {
						inpVal = inpVal + userCommandArr[cnt];
						cnt++;
					}
				} else {
					while (cnt < userCommandArr.length) {
						inpVal = inpVal + userCommandArr[cnt];
						cnt++;
					}
					i++;
				}
				System.out.println(inpVal);
			} else {
				System.out.println("No match");
				i++;
				cnt++;
			}
		}

	}
*/

	public static void main(String[] args) throws InterruptedException {
		/*int i=2;
		System.out.println(Integer.toString(i));
		Object objInt = (Object)i;
		System.out.println(objInt);*/
		
		/*float i=2.5f;
		Object objInt = (Object)i;
		System.out.println(objInt);
*/
		
		
		boolean i=false;
		Object objInt = (Object)i;
		System.out.println(objInt.getClass().getName());

		String test = "false";
		Object objStr = (Object)test;
		System.out.println(objStr.getClass().getName());
		
		Assert.assertEquals(objInt.toString(), objStr.toString());
		
	}



}
