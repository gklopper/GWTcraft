package com.gwtcraft.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SearchInitiatedEventHandler extends EventHandler{
	
	void onSearchInitiated(SearchInitiatedEvent event);

}
