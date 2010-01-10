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
		Assert.assertEquals(4, takbok.getSearchRank().intValue());
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
	public void shouldReturnEmptyResultsWhenCharacterNotFound() {
		InputStream xmlStream = getClass().getResourceAsStream("character-not-found.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		List<Item> items = parser.parseItems(xmlStream);
		
		Assert.assertEquals(0, items.size());
	}
	
	@Test
	public void shouldParseItemDetail() {
		InputStream xmlStream = getClass().getResourceAsStream("item.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		ItemDetail item = parser.parseItem(xmlStream);
		
		Assert.assertEquals(47708, item.getId());
		Assert.assertEquals("Duskstalker Shoulderpads", item.getName());
		Assert.assertEquals("inv_shoulder_89", item.getIcon());
		
		Assert.assertEquals(101, item.getStamina().intValue());
		Assert.assertEquals(93, item.getAgility().intValue());
		Assert.assertEquals(467, item.getArmor().intValue());
		Assert.assertEquals(135, item.getAttackPower().intValue());
		Assert.assertEquals(67, item.getCritRating().intValue());
		Assert.assertEquals(59, item.getArmorPenetration().intValue());
		
		Assert.assertEquals(1, item.getYellowSockets().intValue());
	}
	
	@Test
	public void shouldParseItemDetail2() {
		InputStream xmlStream = getClass().getResourceAsStream("item2.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		ItemDetail item = parser.parseItem(xmlStream);
		
		Assert.assertEquals(1, item.getBlueSockets().intValue());
		Assert.assertEquals(48, item.getStrength().intValue());
		Assert.assertEquals(30, item.getDodgeRating().intValue());
		Assert.assertEquals(26, item.getExpertiseRating().intValue());
		Assert.assertEquals(34, item.getParryRating().intValue());
	}
	
	@Test
	public void shouldParseItemDetail3() {
		InputStream xmlStream = getClass().getResourceAsStream("item3.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		ItemDetail item = parser.parseItem(xmlStream);
		
		Assert.assertEquals("Increases dodge rating by 512 for 20 sec.", item.getUse().get(0));
	}
	
	@Test
	public void shouldParseItemDetail4() {
		InputStream xmlStream = getClass().getResourceAsStream("item4.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		ItemDetail item = parser.parseItem(xmlStream);
		
		Assert.assertEquals("Each time a melee attack strikes you, you have a chance to gain 7056 armor for 10 sec.", item.getEquip().get(0));
	}
	
	@Test
	public void shouldParseItemDetail5() {
		InputStream xmlStream = getClass().getResourceAsStream("item5.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		ItemDetail item = parser.parseItem(xmlStream);
		
		Assert.assertEquals(70, item.getIntellect().intValue());
		Assert.assertEquals(78, item.getSpirit().intValue());
		Assert.assertEquals(1, item.getMetaSockets().intValue());
		Assert.assertEquals(1, item.getRedSockets().intValue());
		Assert.assertEquals(132, item.getSpellPower().intValue());
	}
}
