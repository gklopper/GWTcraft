package com.gwtcraft.client.model;

@SuppressWarnings("serial")
public class ItemDetail extends Item {
	
	private String name;
	private Integer stamina;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Integer getStamina() {
		return stamina;
	}

	public void setStamina(Integer stamina) {
		this.stamina = stamina;
	}
}
