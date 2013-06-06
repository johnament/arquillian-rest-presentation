package com.tad.arquillian.rest.greeter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GreeterObject {
	private String value;
	private int size;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
