package com.gwtcraft.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.ItemDetail;

public class ItemSelectedEvent extends GwtEvent<ItemSelectedEventHandler> {

	public static Type<ItemSelectedEventHandler> TYPE = new Type<ItemSelectedEventHandler>();
	private final String characterName;
	private final String characterRealm;
	private final Integer itemId;
	

	public ItemSelectedEvent(String characterName, String characterRealm, Integer itemId) {
		this.characterName = characterName;
		this.characterRealm = characterRealm;
		this.itemId = itemId;
	}
	
	@Override
	protected void dispatch(ItemSelectedEventHandler handler) {
		handler.onItemSelected(this);
	}

	@Override
	public Type<ItemSelectedEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getCharacterName() {
		return characterName;
	}

	public String getCharacterRealm() {
		return characterRealm;
	}

	public Integer getItemId() {
		return itemId;
	}
}
