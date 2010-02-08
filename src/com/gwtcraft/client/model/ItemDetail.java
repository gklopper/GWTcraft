package com.gwtcraft.client.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ItemDetail extends Item {
	
	private String name;
	private String icon;
	private List<String> use = new ArrayList<String>();
	private List<String> equip = new ArrayList<String>();
	private List<Statistic> statistics = new ArrayList<Statistic>();
	private String source;
	private String areaName;
	private String creatureName;
	
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCreatureName() {
		return creatureName;
	}

	public void setCreatureName(String creatureName) {
		this.creatureName = creatureName;
	}
}
