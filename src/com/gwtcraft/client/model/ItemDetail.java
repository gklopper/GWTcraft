package com.gwtcraft.client.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ItemDetail extends Item {
	
	private String name;
	private String icon;
	private List<Statistic> statistics = new ArrayList<Statistic>();
	private List<Spell> spells = new ArrayList<Spell>();
	private String source;
	private String areaName;
	private String creatureName;
	private Integer itemLevel;
	
	
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

	public List<Spell> getSpells() {
		return spells;
	}

	public void setSpells(List<Spell> spells) {
		this.spells = spells;
	}

	public Integer getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(Integer itemLevel) {
		this.itemLevel = itemLevel;
	}
}
