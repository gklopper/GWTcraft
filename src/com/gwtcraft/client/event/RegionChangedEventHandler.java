package com.gwtcraft.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface RegionChangedEventHandler extends EventHandler{
	
	void onRegionChange(RegionChangedEvent event);

}
