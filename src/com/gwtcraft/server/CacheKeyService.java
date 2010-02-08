package com.gwtcraft.server;

public class CacheKeyService {
	
	private static final String version = "d1_";
	
	public static String key(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Null key not allowed");
		}
		return version + key;
	}
}
