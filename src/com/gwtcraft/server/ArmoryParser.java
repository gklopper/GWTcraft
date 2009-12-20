package com.gwtcraft.server;

import java.io.InputStream;
import java.util.List;

import com.rsx.*;
import com.rsx.impl.ReallySimpleXmlImpl;

import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.CharacterSearchResult;

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
}
