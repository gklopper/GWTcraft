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

@SuppressWarnings("serial")
public class ArmoryServiceImpl extends RemoteServiceServlet implements
		ArmoryService {
	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5";
	
	private Cache cache15min;
	private Cache cache24hours;
	
	@SuppressWarnings("unchecked")
	public ArmoryServiceImpl() {
		Map props15min = new HashMap();
        props15min.put(GCacheFactory.EXPIRATION_DELTA, 60 * 15);

        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache15min = cacheFactory.createCache(props15min);
        } catch (CacheException e) {
            throw new RuntimeException("Unable to create cache", e);
        }
        
        Map props24hours = new HashMap();
        props24hours.put(GCacheFactory.EXPIRATION_DELTA, 60 * 60 * 24);

        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache24hours = cacheFactory.createCache(props15min);
        } catch (CacheException e) {
            throw new RuntimeException("Unable to create cache", e);
        }
	}

	@SuppressWarnings("unchecked")
	public List<ArmoryCharacter> search(String searchTerm) {
		try {
			String cacheKey = CacheKeyService.key(searchTerm);
			List<ArmoryCharacter> characters = (List<ArmoryCharacter>) cache15min.get(cacheKey);
			
			if (characters == null) {
				URL url = new URL("http://eu.wowarmory.com/search.xml?searchQuery=" + URLEncoder.encode(searchTerm, "UTF-8"));
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("User-Agent", USER_AGENT);
				characters = new ArmoryParser().parseCharacterSearch(connection.getInputStream());
				Collections.sort(characters, new SearchRankComparator());
				cache15min.put(cacheKey, characters);
			}
			
			return characters;
		} catch (IOException e) {
			throw new GwtCraftException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> loadItemsFor(String name, String realm) {
		try {
			String cacheKey = CacheKeyService.key(name + "_" + realm);
			List<Item> items = (List<Item>) cache15min.get(cacheKey);
			
			if (items == null) {
				String urlString = String.format("http://eu.wowarmory.com/character-sheet.xml?r=%s&n=%s", 
						URLEncoder.encode(realm, "UTF-8"), 
						URLEncoder.encode(name, "UTF-8"));
				URL url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("User-Agent", USER_AGENT);
				items = new ArmoryParser().parseItems(connection.getInputStream());
				cache15min.put(cacheKey, items);
			}
			
			return items;
		} catch (IOException e) {
			throw new GwtCraftException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ItemDetail loadItem(Integer id) {
		try {
			String cacheKey = CacheKeyService.key("item_" + id);
			ItemDetail item = (ItemDetail) cache24hours.get(cacheKey);
			
			if (item == null) {
				URL url = new URL("http://eu.wowarmory.com/item-tooltip.xml?i=" + id);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("User-Agent", USER_AGENT);
				item = new ArmoryParser().parseItem(connection.getInputStream());
				cache24hours.put(cacheKey, item);
			}
			
			return item;
		} catch (IOException e) {
			throw new GwtCraftException(e);
		}
	}
	
	private static class SearchRankComparator implements Comparator<ArmoryCharacter> {
		@Override
		public int compare(ArmoryCharacter o1, ArmoryCharacter o2) {
			
			//these should be sorted reverse
			return o1.getSearchRank().compareTo(o2.getSearchRank()) * -1;
		}
	}

	@Override
	public List<Integer> loadUpgradesFor(String playerName, String playerRealm, Integer itemId) {
		try {
			String requestUrl = String.format("http://eu.wowarmory.com/search.xml?searchType=items&pr=%s&pn=%s&pi=%s", playerRealm, playerName, String.valueOf(itemId));
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", USER_AGENT);
			return new ArmoryParser().parseUpgrades(connection.getInputStream());
		} catch (IOException ioe) {
			throw new GwtCraftException(ioe);
		}
	}
}
