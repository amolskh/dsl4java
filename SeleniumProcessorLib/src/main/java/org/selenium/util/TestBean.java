package org.selenium.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="test")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestBean {
	@XmlAttribute
	String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ParameterBean getParamObj() {
		return paramObj;
	}

	public void setParamObj(ParameterBean paramObj) {
		this.paramObj = paramObj;
	}

	public ClassesBean getClassesObj() {
		return classesObj;
	}

	public void setClassesObj(ClassesBean classesObj) {
		this.classesObj = classesObj;
	}

	@XmlElement(name ="parameter", type = ParameterBean.class)
	ParameterBean paramObj;
	
	@XmlElement(name ="classes", type = ClassesBean.class)
	ClassesBean classesObj;
	
	




}
