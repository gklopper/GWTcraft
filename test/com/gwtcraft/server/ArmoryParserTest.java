package com.gwtcraft.server;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import junit.framework.Assert;

import org.junit.Test;

import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.Item;
import com.gwtcraft.client.model.ItemDetail;
import com.gwtcraft.client.model.Spell;
import com.gwtcraft.client.model.Statistic;

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
		Assert.assertEquals(80, takbok.getLevel().intValue());
		
		//2009-12-20 08:32:09.0
		
		Calendar cal = Calendar.getInstance(Locale.UK);
		cal.clear();
		cal.set(Calendar.YEAR, 2009);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, 20);
		Assert.assertEquals(cal.getTime(), takbok.getLastLogin());
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
		
		Statistic expectedArmor = new Statistic("Armor", 467);
		Assert.assertTrue(item.getStatistics().contains(expectedArmor));
		
		Statistic expectedSocket = new Statistic("Yellow socket", 1);
		Assert.assertTrue(item.getStatistics().contains(expectedSocket));
		Assert.assertEquals("Vendor", item.getSource());
		Assert.assertEquals(245, item.getItemLevel().intValue());
		
	}
	
	@Test
	public void shouldParseItemDetail2() {
		InputStream xmlStream = getClass().getResourceAsStream("item2.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		ItemDetail item = parser.parseItem(xmlStream);
		
		Statistic expectedSocket = new Statistic("Blue socket", 1);
		Assert.assertTrue(item.getStatistics().contains(expectedSocket));
	}
	
	@Test
	public void shouldParseItemDetail3() {
		InputStream xmlStream = getClass().getResourceAsStream("item3.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		ItemDetail item = parser.parseItem(xmlStream);
		
		Spell expectedUse = new Spell("Use", "Increases dodge rating by 512 for 20 sec.");
		Assert.assertTrue(item.getSpells().contains(expectedUse));
	}
	
	@Test
	public void shouldParseItemDetail4() {
		InputStream xmlStream = getClass().getResourceAsStream("item4.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		ItemDetail item = parser.parseItem(xmlStream);
		
		Spell expectedEquip = new Spell("Equip", "Each time a melee attack strikes you, you have a chance to gain 7056 armor for 10 sec.");
		Assert.assertTrue(item.getSpells().contains(expectedEquip));
	}
	
	@Test
	public void shouldParseItemDetail5() {
		InputStream xmlStream = getClass().getResourceAsStream("item5.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		ItemDetail item = parser.parseItem(xmlStream);
		
		Statistic expectedMetaSocket = new Statistic("Meta socket", 1);
		Assert.assertTrue(item.getStatistics().contains(expectedMetaSocket));
		Statistic expectedRedSocket = new Statistic("Red socket", 1);
		Assert.assertTrue(item.getStatistics().contains(expectedRedSocket));
		Assert.assertEquals("Onyxia", item.getCreatureName());
		Assert.assertEquals("Onyxia's Lair", item.getAreaName());
	}
	
	@Test
	public void shouldParseItemUpgrades() {
		InputStream xmlStream = getClass().getResourceAsStream("upgrade-items.xml");
		Assert.assertNotNull(xmlStream);
		
		ArmoryParser parser = new ArmoryParser();
		List<Integer> items = parser.parseUpgrades(xmlStream);
		
		Assert.assertEquals(34, items.size());
		Assert.assertEquals(51143, items.get(2).intValue());
	}
}
