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
	List<ArmoryCharacter> search(String searchTerm);
	List<Item> loadItemsFor(String name, String realm);
	ItemDetail loadItem(Integer id);
}
