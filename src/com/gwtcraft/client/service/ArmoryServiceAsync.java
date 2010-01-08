package com.gwtcraft.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtcraft.client.model.ArmoryCharacter;

public interface ArmoryServiceAsync {
	void search(String input, AsyncCallback<List<ArmoryCharacter>> callback);
}
