package com.gwtcraft.client.event;

import com.gwtcraft.client.model.CharacterSearchResult;

public class SearchCompleteEvent extends Event {
	
	private CharacterSearchResult result;

	public SearchCompleteEvent(CharacterSearchResult result) {
		this.result = result;
	}

	public CharacterSearchResult getResult() {
		return result;
	}
}
