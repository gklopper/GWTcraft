package com.gwtcraft.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.Item;
import com.gwtcraft.client.model.ItemDetail;

public interface ArmoryServiceAsync {
	void search(String regionCode, String input, AsyncCallback<List<ArmoryCharacter>> callback);
	void loadItemsFor(String regionCode, String name, String realm, AsyncCallback<List<Item>> callback);
	void loadItem(String regionCode, Integer id, AsyncCallback<ItemDetail> callback);
	void loadUpgradesFor(String regionCode, String name, String realm, Integer itemId, AsyncCallback<List<Integer>> callback);
}
