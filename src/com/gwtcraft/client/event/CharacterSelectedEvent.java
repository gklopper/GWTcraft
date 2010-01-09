package com.gwtcraft.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class CharacterSelectedEvent extends GwtEvent<CharacterSelectedEventHandler> {

	public static Type<CharacterSelectedEventHandler> TYPE = new Type<CharacterSelectedEventHandler>();
	private final String name;
	private final String realm;

	public CharacterSelectedEvent(String name, String realm) {
		this.name = name;
		this.realm = realm;
	}
	
	@Override
	protected void dispatch(CharacterSelectedEventHandler handler) {
		handler.onCharacterSelected(this);
	}

	@Override
	public Type<CharacterSelectedEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getName() {
		return name;
	}

	public String getRealm() {
		return realm;
	}
	
}
