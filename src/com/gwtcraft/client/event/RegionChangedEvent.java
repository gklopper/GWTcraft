package com.gwtcraft.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RegionChangedEvent extends GwtEvent<RegionChangedEventHandler> {

	public static Type<RegionChangedEventHandler> TYPE = new Type<RegionChangedEventHandler>();
	private final String regionCode;

	public RegionChangedEvent(String regionCode) {
		this.regionCode = regionCode;
	}
	
	@Override
	public Type<RegionChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RegionChangedEventHandler handler) {
		handler.onRegionChange(this);
	}

	public String getRegionCode() {
		return regionCode;
	}
}
