package com.gwtcraft.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.gwtcraft.client.model.ArmoryCharacter;

public class SearchCompleteEvent extends GwtEvent<SearchCompleteEventHandler> {

	public static Type<SearchCompleteEventHandler> TYPE = new Type<SearchCompleteEventHandler>();
	private final List<ArmoryCharacter> characters;

	public SearchCompleteEvent(List<ArmoryCharacter> characters) {
		this.characters = characters;
	}
	
	@Override
	protected void dispatch(SearchCompleteEventHandler handler) {
		handler.onSearchComplete(this);
	}

	@Override
	public Type<SearchCompleteEventHandler> getAssociatedType() {
		return TYPE;
	}

	public List<ArmoryCharacter> getCharacters() {
		return characters;
	}
}
