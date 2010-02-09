package com.gwtcraft.server;

public class CacheKeyService {
	
	private static final String version = "_e1_";
	
	public static String key(String key, String regionCode) {
		if (key == null) {
			throw new IllegalArgumentException("Null key not allowed");
		}
		return regionCode + version + key;
	}
}
