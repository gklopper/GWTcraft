package com.gwtcraft.server;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.rsx.*;
import com.rsx.impl.ReallySimpleXmlImpl;

import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.CharacterSearchResult;
import com.gwtcraft.client.model.Item;
import com.gwtcraft.client.model.ItemDetail;

public class ArmoryParser {

	public CharacterSearchResult parseCharacterSearch(InputStream xmlStream) {
		Element page = new ReallySimpleXmlImpl().parse(xmlStream);
	
		List<Element> characters = page.element("armorySearch")
				.element("searchResults")
					.element("characters").elements("character");
		
		CharacterSearchResult result = new CharacterSearchResult();
		
		for (Element characterElement : characters) {
			ArmoryCharacter character = new ArmoryCharacter();
			result.addCharacter(character);
			
			character.setName(characterElement.attribute("name").toString());
			character.setRealm(characterElement.attribute("realm").toString());
		}
		return result;
	}

	public List<Item> parseItems(InputStream xmlStream) {
		Element page = new ReallySimpleXmlImpl().parse(xmlStream);

		List<Element> itemElements = page.element("characterInfo")
											.element("items")
											.elements("item");
		
		List<Item> items = new ArrayList<Item>();
		
		for (Element itemElement : itemElements) {
			Item item = new Item();
			item.setId(itemElement.attribute("id").toInteger());
			item.setSlot(itemElement.attribute("slot").toInteger());
			items.add(item);
		}

		return items;
	}

	public ItemDetail parseItem(InputStream xmlStream) {
		// TODO Auto-generated method stub
		return null;
	}
}
