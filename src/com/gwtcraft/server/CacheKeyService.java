package com.gwtcraft.server;

public class CacheKeyService {
	
	private static final String version = "b1_";
	
	public static String key(String key) {
		return version + key;
	}
}
