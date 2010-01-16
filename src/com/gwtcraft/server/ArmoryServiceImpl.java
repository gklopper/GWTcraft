package com.gwtcraft.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

@SuppressWarnings({ "serial", "unchecked" })
public class ArmoryServiceImpl extends RemoteServiceServlet implements
		ArmoryService {

	private static final Logger LOGGER = Logger
			.getLogger(ArmoryServiceImpl.class.getName());

	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5";

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

	public List<ArmoryCharacter> search(String searchTerm) {
		try {
			String cacheKey = CacheKeyService.key(searchTerm);
			List<ArmoryCharacter> characters = (List<ArmoryCharacter>) cache30min
					.get(cacheKey);

			if (characters == null) {
				URL url = new URL(
						"http://eu.wowarmory.com/search.xml?searchQuery="
								+ URLEncoder.encode(searchTerm, "UTF-8"));
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestProperty("User-Agent", USER_AGENT);
				characters = new ArmoryParser().parseCharacterSearch(connection
						.getInputStream());
				Collections.sort(characters, new SearchRankComparator());
				cache30min.put(cacheKey, characters);
			}

			return characters;
		} catch (IOException e) {
			throw new GwtCraftException(e);
		}
	}

	@Override
	public List<Item> loadItemsFor(String name, String realm) {
		try {
			String cacheKey = CacheKeyService.key(name + "_" + realm);
			List<Item> items = (List<Item>) cache30min.get(cacheKey);

			if (items == null) {
				String urlString = String
						.format(
								"http://eu.wowarmory.com/character-sheet.xml?r=%s&n=%s",
								URLEncoder.encode(realm, "UTF-8"), URLEncoder
										.encode(name, "UTF-8"));
				URL url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestProperty("User-Agent", USER_AGENT);
				items = new ArmoryParser().parseItems(connection
						.getInputStream());
				cache30min.put(cacheKey, items);
			}

			return items;
		} catch (IOException e) {
			throw new GwtCraftException(e);
		}
	}

	@Override
	public ItemDetail loadItem(Integer id) {
		try {
			String cacheKey = CacheKeyService.key("item_" + id);
			ItemDetail item = (ItemDetail) cache24hours.get(cacheKey);

			if (item == null) {
				URL url = new URL("http://eu.wowarmory.com/item-tooltip.xml?i="
						+ id);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestProperty("User-Agent", USER_AGENT);
				item = new ArmoryParser()
						.parseItem(connection.getInputStream());
				cache24hours.put(cacheKey, item);
			}

			return item;
		} catch (IOException e) {
			throw new GwtCraftException(e);
		}
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
	public List<Integer> loadUpgradesFor(String playerName, String playerRealm,
			Integer itemId) {
		String requestUrl = null;
		try {
			requestUrl = String
					.format(
							"http://eu.wowarmory.com/search.xml?searchType=items&pr=%s&pn=%s&pi=%s",
							URLEncoder.encode(playerRealm, "UTF-8"),
							URLEncoder.encode(playerName, "UTF-8"),
							String.valueOf(itemId));
			String key = CacheKeyService.key(requestUrl);

			List<Integer> items = (List<Integer>) cache30min.get(key);

			if (items == null) {

				URL url = new URL(requestUrl);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestProperty("User-Agent", USER_AGENT);
				items = new ArmoryParser().parseUpgrades(connection
						.getInputStream());
				cache30min.put(key, items);

			}

			return items;
		} catch (IOException ioe) {
			LOGGER.log(Level.SEVERE, "Unable to load: " + requestUrl, ioe);
			throw new GwtCraftException("Error retreiving data" + requestUrl,
					ioe);
		}
	}
}
