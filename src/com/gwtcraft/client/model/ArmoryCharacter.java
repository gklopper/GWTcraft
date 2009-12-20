package com.gwtcraft.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ArmoryCharacter implements IsSerializable{
	
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
