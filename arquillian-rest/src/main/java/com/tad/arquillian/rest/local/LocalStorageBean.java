package com.tad.arquillian.rest.local;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class LocalStorageBean {
	private List<String> entries;
	
	@PostConstruct
	public void init() {
		this.entries = new ArrayList<>();
	}
	
	public void addMessage(String message) {
		this.entries.add(message);
	}
	
	public List<String> getEntries() {
		return Collections.unmodifiableList(this.entries);
	}
}
