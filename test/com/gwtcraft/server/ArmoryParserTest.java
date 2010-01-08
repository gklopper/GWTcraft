package com.gwtcraft.server;

import java.io.InputStream;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.Item;
import com.gwtcraft.client.model.ItemDetail;

public class ArmoryParserTest {
	@Test
	public void shouldParseSearchXml() {
		InputStream xmlStream = getClass().getResourceAsStream("searchResults.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		List<ArmoryCharacter> characters = parser.parseCharacterSearch(xmlStream);
		
		Assert.assertEquals(4, characters.size());
		
		ArmoryCharacter takbok = characters.get(3);
		Assert.assertEquals("Eonar", takbok.getRealm());
		Assert.assertEquals("Takbok", takbok.getName());
	}
	
	@Test
	public void shouldParseItemsForCharacter() {
		InputStream xmlStream = getClass().getResourceAsStream("character.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		List<Item> items = parser.parseItems(xmlStream);
		
		Assert.assertEquals(16, items.size());
		
		Item neck = items.get(1);
		
		Assert.assertEquals(1, neck.getSlot());
		Assert.assertEquals(49306, neck.getId());
		
	}
	
	@Test
	public void shouldParseItemDetail() {
		InputStream xmlStream = getClass().getResourceAsStream("item.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		ItemDetail item = parser.parseItem(xmlStream);
		
		Assert.assertEquals(47708, item.getId());
		Assert.assertEquals(47708, item.getId());
		Assert.assertEquals(47708, item.getId());
		Assert.assertEquals(47708, item.getId());
		Assert.assertEquals(47708, item.getId());
		Assert.assertEquals(47708, item.getId());
		
		
	}
}
