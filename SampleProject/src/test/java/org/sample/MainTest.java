package org.sample;

import java.io.IOException;
import org.selenium.util.CreateTestNGxml;
import org.selenium.util.ReadExcel;

public class MainTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	    ReadExcel.ReadTestcaseExcel("src/main/resources/Testcases.xlsx");		
		CreateTestNGxml.testSuiteXML();
	}
}
