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
		
		//stats
		item.setStamina(intValueOrZero(itemElement.element("bonusStamina")));
		
		return item;
	}

	private Integer intValueOrZero(Element element) {
		return element == null ? 0 : element.value().toInteger();
	}
}
