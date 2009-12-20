package com.gwtcraft.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtcraft.client.model.CharacterSearchResult;

public interface ArmoryServiceAsync {
	void search(String input, AsyncCallback<CharacterSearchResult> callback);
}
