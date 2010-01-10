package com.gwtcraft.client.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ItemDetail extends Item {
	
	private String name;
	private Integer stamina;
	private Integer agility;
	private Integer armor;
	private Integer attackPower;
	private Integer critRating;
	private Integer armorPenetration;
	private String icon;
	private Integer yellowSockets = 0;
	private Integer blueSockets = 0;
	private Integer strength;
	private Integer dodgeRating;
	private Integer expertiseRating;
	private Integer parryRating;
	private List<String> use = new ArrayList<String>();
	private List<String> equip = new ArrayList<String>();
	private Integer intellect;
	private Integer spirit;
	private Integer metaSockets = 0;
	private Integer spellPower;
	private Integer redSockets = 0;

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

	public Integer getAgility() {
		return agility;
	}

	public void setAgility(Integer agility) {
		this.agility = agility;
	}

	public Integer getArmor() {
		return armor;
	}

	public void setArmor(Integer armor) {
		this.armor = armor;
	}

	public Integer getAttackPower() {
		return attackPower;
	}

	public Integer getCritRating() {
		return critRating;
	}

	public Integer getArmorPenetration() {
		return armorPenetration;
	}

	public void setAttackPower(Integer attackPower) {
		this.attackPower = attackPower;
	}

	public void setCritRating(Integer critRating) {
		this.critRating = critRating;
	}

	public void setArmorPenetration(Integer armorPenetration) {
		this.armorPenetration = armorPenetration;
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

	public Integer getStrength() {
		return strength;
	}

	public Integer getDodgeRating() {
		return dodgeRating;
	}

	public Integer getExpertiseRating() {
		return expertiseRating;
	}

	public Integer getParryRating() {
		return parryRating;
	}

	public void setStrength(Integer strength) {
		this.strength = strength;
	}

	public void setDodgeRating(Integer dodgeRating) {
		this.dodgeRating = dodgeRating;
	}

	public void setExpertiseRating(Integer expertiseRating) {
		this.expertiseRating = expertiseRating;
	}

	public void setParryRating(Integer parryRating) {
		this.parryRating = parryRating;
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

	public Integer getIntellect() {
		return intellect;
	}

	public Integer getSpirit() {
		return spirit;
	}

	public Integer getMetaSockets() {
		return metaSockets;
	}

	public Integer getSpellPower() {
		return spellPower;
	}

	public Integer getRedSockets() {
		return redSockets;
	}

	public void setIntellect(Integer intellect) {
		this.intellect = intellect;
	}

	public void setSpirit(Integer spirit) {
		this.spirit = spirit;
	}

	public void setMetaSockets(Integer metaSockets) {
		this.metaSockets = metaSockets;
	}

	public void setSpellPower(Integer spellPower) {
		this.spellPower = spellPower;
	}

	public void setRedSockets(Integer redSockets) {
		this.redSockets = redSockets;
	}
}
