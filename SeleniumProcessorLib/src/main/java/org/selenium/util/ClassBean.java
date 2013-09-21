package org.selenium.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="class")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassBean {
	public String getName1() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	String name;

}
