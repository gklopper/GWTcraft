package com.gwtcraft.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SearchInitiatedEvent extends GwtEvent<SearchInitiatedEventHandler> {

	public static Type<SearchInitiatedEventHandler> TYPE = new Type<SearchInitiatedEventHandler>();
	private final String searchTerm;

	public SearchInitiatedEvent(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	
	@Override
	protected void dispatch(SearchInitiatedEventHandler handler) {
		handler.onSearchInitiated(this);
	}

	@Override
	public Type<SearchInitiatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getSearchTerm() {
		return searchTerm;
	}
}
