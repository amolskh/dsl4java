package org.selenium.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="suite")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuiteBean {
	@XmlAttribute
	String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParallel() {
		return parallel;
	}

	public void setParallel(String parallel) {
		this.parallel = parallel;
	}

	public List<TestBean> getTestList() {
		return testList;
	}

	public void setTestList(List<TestBean> testList) {
		this.testList = testList;
	}

	@XmlAttribute
	String parallel;
	
	@XmlElement(name ="test", type = TestBean.class)
	List<TestBean> testList=  new ArrayList<TestBean>();

}
