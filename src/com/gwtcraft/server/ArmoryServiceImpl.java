package com.gwtcraft.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
import com.gwtcraft.client.service.ArmoryService;

@SuppressWarnings("serial")
public class ArmoryServiceImpl extends RemoteServiceServlet implements
		ArmoryService {
	
	Cache cache;
	
	@SuppressWarnings("unchecked")
	public ArmoryServiceImpl() {
		Map props = new HashMap();
        props.put(GCacheFactory.EXPIRATION_DELTA, 60 * 15);

        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(props);
        } catch (CacheException e) {
            throw new RuntimeException("Unable to create cache", e);
        }
	}

	@SuppressWarnings("unchecked")
	public List<ArmoryCharacter> search(String searchTerm) {
		try {
			String cacheKey = CacheKeyService.key(searchTerm);
			List<ArmoryCharacter> characters = (List<ArmoryCharacter>) cache.get(cacheKey);
			
			if (characters == null) {
				URL url = new URL("http://eu.wowarmory.com/search.xml?searchQuery=" + searchTerm);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5");
				characters = new ArmoryParser().parseCharacterSearch(connection.getInputStream());
				cache.put(cacheKey, characters);
			}
			
			return characters;
		} catch (IOException e) {
			throw new GwtCraftException(e);
		}
	}
}
