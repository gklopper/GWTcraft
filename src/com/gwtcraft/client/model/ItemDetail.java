package com.gwtcraft.client.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ItemDetail extends Item {
	
	private String name;
	private Integer armor;
	private String icon;
	private Integer yellowSockets = 0;
	private Integer blueSockets = 0;
	private Integer redSockets = 0;
	private Integer metaSockets = 0;
	private List<String> use = new ArrayList<String>();
	private List<String> equip = new ArrayList<String>();
	private List<Statistic> statistics = new ArrayList<Statistic>(); 
	

	public List<Statistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Integer getArmor() {
		return armor;
	}

	public void setArmor(Integer armor) {
		this.armor = armor;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getYellowSockets() {
		return yellowSockets;
	}

	public void setYellowSockets(Integer yellowSockets) {
		this.yellowSockets = yellowSockets;
	}

	public Integer getBlueSockets() {
		return blueSockets;
	}

	public void setBlueSockets(Integer blueSockets) {
		this.blueSockets = blueSockets;
	}

	

	public void setUse(List<String> use) {
		this.use = use;
	}

	public List<String> getUse() {
		return use;
	}

	public void setEquip(List<String> equip) {
		this.equip = equip;
	}

	public List<String> getEquip() {
		return equip;
	}

	public Integer getMetaSockets() {
		return metaSockets;
	}

	public Integer getRedSockets() {
		return redSockets;
	}

	public void setMetaSockets(Integer metaSockets) {
		this.metaSockets = metaSockets;
	}

	public void setRedSockets(Integer redSockets) {
		this.redSockets = redSockets;
	}
}
