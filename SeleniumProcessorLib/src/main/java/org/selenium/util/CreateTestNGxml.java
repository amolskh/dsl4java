package org.selenium.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class CreateTestNGxml {
	static List<TestBean> tests=  new ArrayList<TestBean>();

	public void createTestList(String TC_Num){
		ClassBean cls = new ClassBean();
		cls.setName("org.sample.ParameterTest");	

		ClassesBean classes = new ClassesBean();
		classes.setClassObj(cls);

		ParameterBean param = new ParameterBean();
		param.setName("tc_Name");
		param.setValue(TC_Num);	

		TestBean test=new TestBean();
		test.setName(TC_Num);
		test.setParamObj(param);
		test.setClassesObj(classes);
		tests.add(test);		
	}

	public static  void testSuiteXML(){
		SuiteBean suite = new SuiteBean();
		suite.setName("Suite");
		suite.setParallel("false");
		suite.setTestList(tests);

		try {
			File file = new File("src/test/java/testSuite.xml");
			marshal(suite, file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}		
	}

	// Export
	public static void marshal(SuiteBean suite, File selectedFile)throws IOException, JAXBException {
		BufferedWriter  writer = new BufferedWriter(new FileWriter(selectedFile));
		JAXBContext context = JAXBContext.newInstance(SuiteBean.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(suite, writer);
		m.marshal(suite, System.out);
		writer.close();
	}
}
