package com.gwtcraft.server;

public class CacheKeyService {
	
	private static final String version = "a4_";
	
	public static String key(String key) {
		return version + key;
	}
}
