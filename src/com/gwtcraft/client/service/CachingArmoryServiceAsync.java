package com.gwtcraft.client.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.Item;
import com.gwtcraft.client.model.ItemDetail;

public class CachingArmoryServiceAsync implements ArmoryServiceAsync {
	
	private final ArmoryServiceAsync delegate;
	
	//TODO think about whether a map is a good cache in these circumstances
	//what other caches do you get for Javascript?
	private final Map<Integer, ItemDetail> itemCache = new HashMap<Integer, ItemDetail>();
	
	public CachingArmoryServiceAsync(ArmoryServiceAsync delegate) {
		this.delegate = delegate;
	}

	public void loadItem(final Integer id, final AsyncCallback<ItemDetail> callback) {
		
		ItemDetail item = itemCache.get(id);
		
		if (item == null) {
			delegate.loadItem(id, new AsyncCallback<ItemDetail>() {
				@Override
				public void onFailure(Throwable caught) {
					callback.onFailure(caught);	
				}

				@Override
				public void onSuccess(ItemDetail result) {
					itemCache.put(id, result);
					callback.onSuccess(result);
				}
			});
		} else {
			callback.onSuccess(item);
		}
	}

	public void loadItemsFor(String name, String realm, AsyncCallback<List<Item>> callback) {
		delegate.loadItemsFor(name, realm, callback);
	}

	public void search(String input, AsyncCallback<List<ArmoryCharacter>> callback) {
		delegate.search(input, callback);
	}
}
