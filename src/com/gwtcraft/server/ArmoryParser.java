package com.gwtcraft.server;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.Item;
import com.gwtcraft.client.model.ItemDetail;
import com.rsx.Element;
import com.rsx.impl.ReallySimpleXmlImpl;

public class ArmoryParser {

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
		
		//stats
		item.setStamina(intValueOrZero(itemElement.element("bonusStamina")));
		item.setIntellect(intValueOrZero(itemElement.element("bonusIntellect")));
		item.setSpirit(intValueOrZero(itemElement.element("bonusSpirit")));
		item.setSpellPower(intValueOrZero(itemElement.element("bonusSpellPower")));
		item.setAgility(intValueOrZero(itemElement.element("bonusAgility")));
		item.setArmor(intValueOrZero(itemElement.element("armor")));
		item.setAttackPower(intValueOrZero(itemElement.element("bonusAttackPower")));
		item.setCritRating(intValueOrZero(itemElement.element("bonusCritRating")));
		item.setArmorPenetration(intValueOrZero(itemElement.element("bonusArmorPenetration")));
		item.setStrength(intValueOrZero(itemElement.element("bonusStrength")));
		item.setDodgeRating(intValueOrZero(itemElement.element("bonusDodgeRating")));
		item.setExpertiseRating(intValueOrZero(itemElement.element("bonusExpertiseRating")));
		item.setParryRating(intValueOrZero(itemElement.element("bonusParryRating")));
		
		//sockets
		Element socketData = itemElement.element("socketData");
		if (socketData != null) {
			for (Element socket : socketData.elements("socket")) {
				String colour = socket.attribute("color").toString();
				if ("Yellow".equals(colour)) {
					item.setYellowSockets(item.getYellowSockets() + 1);
				}
				if ("Blue".equals(colour)) {
					item.setBlueSockets(item.getBlueSockets() + 1);
				}
				if ("Red".equals(colour)) {
					item.setRedSockets(item.getRedSockets() + 1);
				}
				if ("Meta".equals(colour)) {
					item.setMetaSockets(item.getMetaSockets() + 1);
				}
			}
		}
		
		//spells
		Element spellData = itemElement.element("spellData");
		if (spellData != null) {
			for (Element spell : spellData.elements("spell")) {
				
				if (spell.element("trigger").value().toInteger().equals(0)) {
					item.getUse().add(spell.element("desc").value().toString());
				}
				
				if (spell.element("trigger").value().toInteger().equals(1)) {
					item.getEquip().add(spell.element("desc").value().toString());
				}
			}
		} 
		
		return item;
	}

	private Integer intValueOrZero(Element element) {
		return element == null ? 0 : element.value().toInteger();
	}
}
