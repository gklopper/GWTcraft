package com.gwtcraft.server;

import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;

import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.CharacterSearchResult;

public class ArmoryParserTest {
	@Test
	public void shouldParseSearchXml() {
		InputStream xmlStream = getClass().getResourceAsStream("searchResults.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		CharacterSearchResult search = parser.parseCharacterSearch(xmlStream);
		
		Assert.assertEquals(4, search.getCharacters().size());
		
		ArmoryCharacter takbok = search.getCharacters().get(3);
		Assert.assertEquals("Eonar", takbok.getRealm());
		Assert.assertEquals("Takbok", takbok.getName());
	}
}
