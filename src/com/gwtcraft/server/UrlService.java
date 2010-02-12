package com.gwtcraft.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import com.gwtcraft.client.GwtCraftException;

public class UrlService {

	private static final Logger LOGGER = Logger.getLogger(UrlService.class.getName());

	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5";

	private UrlService() {}
	
	public static InputStream open(String requestUrl) {
		
		try {
			return openStream(requestUrl);
		} catch (IOException ioe) {
			
			//lots of timeouts, so pause and retry
			pause();
			LOGGER.warning("Retrying: " + requestUrl + " because " + ioe.getMessage());
			try {
				return openStream(requestUrl);
			} catch (IOException e) {
				throw new GwtCraftException("Unable to fetch from armory: " + requestUrl, e);
			}
		}
		
	}

	private static void pause() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// do nothing
		}
	}

	private static InputStream openStream(String requestUrl)
			throws IOException {
		URL url = new URL(requestUrl);
		HttpURLConnection connection = (HttpURLConnection) url
				.openConnection();
		connection.setRequestProperty("User-Agent", USER_AGENT);
		return connection.getInputStream();
	}
	
}
