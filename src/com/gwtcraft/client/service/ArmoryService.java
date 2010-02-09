package com.gwtcraft.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.model.Item;
import com.gwtcraft.client.model.ItemDetail;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface ArmoryService extends RemoteService {
	List<ArmoryCharacter> search(String regionCode, String searchTerm);
	List<Item> loadItemsFor(String regionCode, String name, String realm);
	ItemDetail loadItem(String regionCode, Integer id);
	List<Integer> loadUpgradesFor(String regionCode, String name, String realm, Integer itemId);
}
