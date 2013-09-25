package org.sample;


import java.io.IOException;
import org.dsl.Initialise.InitDSL;
import org.dsl.exception.DSLExecFailException;
import org.selenium.util.ReadExcel;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParameterTest {
	
	@BeforeSuite()
	public void createExcelHash() throws IOException{
		ReadExcel.ReadTestcaseExcel("src/main/resources/Testcases.xlsx");		
	}

	@Test
	@Parameters(value="tc_Name")	
	public void testExecMethod(String tc_Name) throws IOException, DSLExecFailException{
		InitDSL.initialise(ReadExcel.testcaseMapping.get(tc_Name));	
	}
}
