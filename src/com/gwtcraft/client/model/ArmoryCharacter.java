package com.gwtcraft.client.model;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ArmoryCharacter implements Serializable {
	
	private String name;
	private String realm;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealm() {
		return realm;
	}
	public void setRealm(String realm) {
		this.realm = realm;
	}

}
