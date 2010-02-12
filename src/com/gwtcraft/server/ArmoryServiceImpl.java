package com.gwtcraft.server;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import com.google.appengine.api.memcache.stdimpl.GCacheFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwtcraft.client.GwtCraftException;
import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.Item;
import com.gwtcraft.client.model.ItemDetail;
import com.gwtcraft.client.service.ArmoryService;

@SuppressWarnings( { "serial", "unchecked" })
public class ArmoryServiceImpl extends RemoteServiceServlet implements
		ArmoryService {

	public static Cache cache30min;
	public static Cache cache24hours;

	static {
		Map props30min = new HashMap();
		props30min.put(GCacheFactory.EXPIRATION_DELTA, 60 * 30);

		try {
			CacheFactory cacheFactory = CacheManager.getInstance()
					.getCacheFactory();
			cache30min = cacheFactory.createCache(props30min);
		} catch (CacheException e) {
			throw new RuntimeException("Unable to create cache", e);
		}

		Map props24hours = new HashMap();
		props24hours.put(GCacheFactory.EXPIRATION_DELTA, 60 * 60 * 24);

		try {
			CacheFactory cacheFactory = CacheManager.getInstance()
					.getCacheFactory();
			cache24hours = cacheFactory.createCache(props24hours);
		} catch (CacheException e) {
			throw new RuntimeException("Unable to create cache", e);
		}
	}

	public List<ArmoryCharacter> search(String regionCode, String searchTerm) {
		try {
			String cacheKey = CacheKeyService
					.key(regionCode + "_" + searchTerm);
			List<ArmoryCharacter> characters = (List<ArmoryCharacter>) cache30min
					.get(cacheKey);

			if (characters == null) {
				String url = "http://" + regionCode
						+ ".wowarmory.com/search.xml?searchQuery="
						+ URLEncoder.encode(searchTerm, "UTF-8");

				characters = new ArmoryParser().parseCharacterSearch(UrlService
						.open(url));

				Calendar cal = Calendar.getInstance(Locale.UK);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				cal.add(Calendar.DAY_OF_YEAR, -60);
				Date sixtyDaysAgo = cal.getTime();

				Set<ArmoryCharacter> toRemove = new HashSet<ArmoryCharacter>();

				for (ArmoryCharacter character : characters) {
					if (character.getLastLogin().before(sixtyDaysAgo)) {
						toRemove.add(character);
					} else if (character.getLevel() < 70) {
						toRemove.add(character);
					}
				}

				characters.removeAll(toRemove);

				Collections.sort(characters, new SearchRankComparator());

				cache30min.put(cacheKey, characters);
			}

			return characters;
		} catch (IOException e) {
			throw new GwtCraftException(e);
		}
	}

	@Override
	public List<Item> loadItemsFor(String regionCode, String name, String realm) {
		try {
			String cacheKey = CacheKeyService.key(regionCode + "_" + name + "_"
					+ realm);
			List<Item> items = (List<Item>) cache30min.get(cacheKey);

			if (items == null) {
				String url = String.format("http://" + regionCode
						+ ".wowarmory.com/character-sheet.xml?r=%s&n=%s",
						URLEncoder.encode(realm, "UTF-8"), URLEncoder.encode(
								name, "UTF-8"));

				items = new ArmoryParser().parseItems(UrlService.open(url));
				cache30min.put(cacheKey, items);
			}

			return items;
		} catch (IOException e) {
			throw new GwtCraftException(e);
		}
	}

	@Override
	public ItemDetail loadItem(String regionCode, Integer id) {
		String cacheKey = CacheKeyService.key("item_" + id);
		ItemDetail item = (ItemDetail) cache24hours.get(cacheKey);

		if (item == null) {
			String url = "http://" + regionCode
					+ ".wowarmory.com/item-tooltip.xml?i=" + id;
			item = new ArmoryParser().parseItem(UrlService.open(url));
			cache24hours.put(cacheKey, item);
		}

		return item;
	}

	private static class SearchRankComparator implements
			Comparator<ArmoryCharacter> {
		@Override
		public int compare(ArmoryCharacter o1, ArmoryCharacter o2) {

			// these should be sorted reverse
			return o1.getSearchRank().compareTo(o2.getSearchRank()) * -1;
		}
	}

	@Override
	public List<Integer> loadUpgradesFor(String regionCode, String playerName,
			String playerRealm, Integer itemId) {
		String url = null;

		try {
			url = String
					.format("http://" + regionCode + ".wowarmory.com/search.xml?searchType=items&pr=%s&pn=%s&pi=%s",
							URLEncoder.encode(playerRealm, "UTF-8"), URLEncoder
									.encode(playerName, "UTF-8"), String
									.valueOf(itemId));
		} catch (IOException ioe) {
			throw new GwtCraftException(ioe);
		}

		String key = CacheKeyService.key(url);

		List<Integer> items = (List<Integer>) cache30min.get(key);

		if (items == null) {

			items = new ArmoryParser().parseUpgrades(UrlService.open(url));
			cache30min.put(key, items);

		}
		return items;
	}
}
