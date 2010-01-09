package com.gwtcraft.client.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Item implements Serializable{

	private int slot;
	private int id;
	
	public void setSlot(int slot) {
		this.slot = slot;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSlot() {
		return slot;
	}

	public int getId() {
		return id;
	}
}
