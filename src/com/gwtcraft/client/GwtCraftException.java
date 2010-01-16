package com.gwtcraft.client;


@SuppressWarnings("serial")
public class GwtCraftException extends RuntimeException {

	public GwtCraftException(Exception e) {
		super(e);
	}
	
	public GwtCraftException(String message, Exception e) {
		super(message, e);
	}

}
