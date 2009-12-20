package com.gwtcraft.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwtcraft.client.GwtCraftException;
import com.gwtcraft.client.model.CharacterSearchResult;
import com.gwtcraft.client.service.ArmoryService;

@SuppressWarnings("serial")
public class ArmoryServiceImpl extends RemoteServiceServlet implements
		ArmoryService {

	public CharacterSearchResult search(String searchTerm) {
		try {
			URL url = new URL("http://eu.wowarmory.com/search.xml?searchQuery=" + searchTerm);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5");
			return new ArmoryParser().parseCharacterSearch(connection.getInputStream());
		} catch (IOException e) {
			throw new GwtCraftException(e);
		}
	}
}
