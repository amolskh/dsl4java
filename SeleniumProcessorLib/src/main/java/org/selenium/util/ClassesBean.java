package org.selenium.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="classes")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassesBean {
	@XmlElement(name ="class", type = ClassBean.class)
	ClassBean classObj;

	public ClassBean getClassObj() {
		return classObj;
	}

	public void setClassObj(ClassBean classObj) {
		this.classObj = classObj;
	}
	


}
