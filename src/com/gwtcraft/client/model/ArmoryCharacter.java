package com.gwtcraft.client.model;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ArmoryCharacter implements Serializable {
	
	private String name;
	private String realm;
	private Integer searchRank;
	
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
	public Integer getSearchRank() {
		return searchRank;
	}
	public void setSearchRank(Integer searchRank) {
		this.searchRank = searchRank;
	}

}
