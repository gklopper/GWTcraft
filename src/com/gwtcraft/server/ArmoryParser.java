package com.gwtcraft.server;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.Item;
import com.gwtcraft.client.model.ItemDetail;
import com.gwtcraft.client.model.Spell;
import com.gwtcraft.client.model.Statistic;
import com.rsx.Element;
import com.rsx.impl.ReallySimpleXmlImpl;

public class ArmoryParser {
	
	private static final Logger LOGGER = Logger.getLogger(ArmoryParser.class.getName());

	private static final Map<String, String> STAT_NAMES = new HashMap<String, String>();
	static {
		STAT_NAMES.put("armor", "Armor");
		
		STAT_NAMES.put("bonusAgility", "Agi");
		STAT_NAMES.put("bonusStamina", "Stam");
		STAT_NAMES.put("bonusAttackPower", "AP");
		STAT_NAMES.put("bonusCritRating", "Crit");
		STAT_NAMES.put("bonusStrength", "Str");
		STAT_NAMES.put("bonusArmorPenetration", "Arm. Pen");
		STAT_NAMES.put("bonusDodgeRating", "Dodge");
		STAT_NAMES.put("bonusExpertiseRating", "Expertise");
		STAT_NAMES.put("bonusParryRating", "Parry");
		STAT_NAMES.put("bonusIntellect", "Int");
		STAT_NAMES.put("bonusSpirit", "Spi");
		STAT_NAMES.put("bonusSpellPower", "Spell power");
		STAT_NAMES.put("bonusDefenseSkillRating", "Def");
		STAT_NAMES.put("bonusHitRating", "Hit");
		STAT_NAMES.put("bonusHasteRating", "Haste");
		STAT_NAMES.put("bonusBlockValue", "Block");
		STAT_NAMES.put("bonusManaRegen", "MP5");
		
		STAT_NAMES.put("Yellow", "Yellow socket");
		STAT_NAMES.put("Blue", "Blue socket");
		STAT_NAMES.put("Red", "Red socket");
		STAT_NAMES.put("Meta", "Meta socket");
	}
	
	private static final Map<String, String> SOURCE_NAMES = new HashMap<String, String>();
	static {
		SOURCE_NAMES.put("sourceType.creatureDrop", "Drop");
		SOURCE_NAMES.put("sourceType.vendor", "Vendor");
		SOURCE_NAMES.put("sourceType.pvpReward", "PvP");
		SOURCE_NAMES.put("sourceType.gameObjectDrop", "Chest");
		SOURCE_NAMES.put("sourceType.createdBySpell", "Created");
		SOURCE_NAMES.put("sourceType.worldDrop", "World drop");
		SOURCE_NAMES.put("sourceType.factionReward", "Faction reward");
		SOURCE_NAMES.put("sourceType.none", "Source unknown");
		
	}

	public List<ArmoryCharacter> parseCharacterSearch(InputStream xmlStream) {
		Element page = new ReallySimpleXmlImpl().parse(xmlStream);
	
		List<Element> characters = page.element("armorySearch")
				.element("searchResults")
					.element("characters").elements("character");
		
		List<ArmoryCharacter> result = new ArrayList<ArmoryCharacter>();
		
		for (Element characterElement : characters) {
			ArmoryCharacter character = new ArmoryCharacter();
			result.add(character);
			
			character.setName(characterElement.attribute("name").toString());
			character.setRealm(characterElement.attribute("realm").toString());
			character.setSearchRank(characterElement.attribute("searchRank").toInteger());
			character.setLevel(characterElement.attribute("level").toInteger());
			
			String loginDateString = characterElement.attribute("lastLoginDate").toString();
			loginDateString = loginDateString.substring(0, 10);
			try {
				character.setLastLogin(new SimpleDateFormat("yyyy-MM-dd", Locale.UK).parse(loginDateString));
			} catch (ParseException e) {
				throw new RuntimeException("Invalid Login Date: " + character.getName() + " : " + character.getRealm() + " : " + characterElement.attribute("lastLoginDate").toString());
			}
			
		}
		return result;
	}

	public List<Item> parseItems(InputStream xmlStream) {
		Element page = new ReallySimpleXmlImpl().parse(xmlStream);

		List<Item> items = new ArrayList<Item>();
		
		Element itemsElement = page.element("characterInfo").element("items");
		
		if (itemsElement == null) {
			return items;
		}
		
		List<Element> itemElements = itemsElement.elements("item");
		
		for (Element itemElement : itemElements) {
			Item item = new Item();
			item.setId(itemElement.attribute("id").toInteger());
			item.setSlot(itemElement.attribute("slot").toInteger());
			items.add(item);
		}

		return items;
	}

	public ItemDetail parseItem(InputStream xmlStream) {
		Element itemElement = new ReallySimpleXmlImpl().parse(xmlStream).element("itemTooltips").element("itemTooltip");
		
		ItemDetail item = new ItemDetail();
		item.setId(itemElement.element("id").value().toInteger());
		item.setName(itemElement.element("name").value().toString());
		item.setIcon(itemElement.element("icon").value().toString());
		item.setItemLevel(intValueOrZero(itemElement.element("itemLevel")));
		
		//armor
		int armor = intValueOrZero(itemElement.element("armor"));
		if (armor > 0) {
			item.getStatistics().add(new Statistic(STAT_NAMES.get("armor"), armor));
		}
		
		//stats
		for (Element stat : itemElement.elements()) {
			if (stat.name().startsWith("bonus")) {
				
				final String name = STAT_NAMES.get(stat.name());
				
				if (name == null) {
					LOGGER.log(Level.INFO, "Unknown stat : " + stat.name());
				} else {
					Statistic statistic = new Statistic();
					statistic.setName(name);
					statistic.setValue(stat.value().toInteger());
					item.getStatistics().add(statistic);
				}
			}
		}
		
		//sockets
		Element socketData = itemElement.element("socketData");
		if (socketData != null) {
			for (Element socket : socketData.elements("socket")) {
				String colour = socket.attribute("color").toString();
				Statistic statistic = getOrAddNewStatistic(item, colour);
				statistic.setValue(statistic.getValue() + 1);
			}
		}
		
		//source
		Element source = itemElement.element("itemSource");
		if (source != null) {
			String sourceName = SOURCE_NAMES.get(source.attribute("value").toString());
			if (sourceName == null) {
				LOGGER.log(Level.INFO, "Unknown source : " + source.attribute("value").toString());
			} else {
				item.setSource(sourceName);
			}
			if (source.attribute("creatureName") != null) {
				item.setCreatureName(source.attribute("creatureName").toString());
			}
			if (source.attribute("areaName") != null) {
				item.setAreaName(source.attribute("areaName").toString());
			}
		}
		
		
		
		//spells
		Element spellData = itemElement.element("spellData");
		if (spellData != null) {
			for (Element spell : spellData.elements("spell")) {
				String description = spell.element("desc").value().toString();
				if (spell.element("trigger").value().toInteger().equals(0)) {
					item.getSpells().add(new Spell("Use", description));
				}
				
				if (spell.element("trigger").value().toInteger().equals(1)) {
					item.getSpells().add(new Spell("Equip", description));
				}
				
				if (spell.element("trigger").value().toInteger().equals(2)) {
					item.getSpells().add(new Spell("Chance on hit", description));
				}
			}
		} 
		
		return item;
	}

	private Statistic getOrAddNewStatistic(ItemDetail item, String statName) {
		final String name = STAT_NAMES.get(statName);
		for (Statistic stat : item.getStatistics()) {
			if (stat.getName().equals(name)) {
				return stat;
			}
		}
		Statistic stat = new Statistic();
		stat.setName(name);
		stat.setValue(0);
		item.getStatistics().add(stat);
		return stat;
	}

	private Integer intValueOrZero(Element element) {
		return element == null ? 0 : element.value().toInteger();
	}

	public List<Integer> parseUpgrades(InputStream xmlStream) {
		Element page = new ReallySimpleXmlImpl().parse(xmlStream);
		
		List<Element> items = page.element("armorySearch").element("searchResults").element("items").elements("item");
		List<Integer> itemIds = new ArrayList<Integer>();
		for (Element item : items) {
			itemIds.add(item.attribute("id").toInteger());
		}
		return itemIds;
	}
}
