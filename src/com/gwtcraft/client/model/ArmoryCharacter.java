package com.gwtcraft.client.model;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class ArmoryCharacter implements Serializable {
	
	private String name;
	private String realm;
	private Integer searchRank;
	private Date lastLogin;
	private Integer level;
	
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
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getLevel() {
		return level;
	}

}
