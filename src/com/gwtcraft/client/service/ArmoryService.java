package com.gwtcraft.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwtcraft.client.model.CharacterSearchResult;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface ArmoryService extends RemoteService {
	CharacterSearchResult search(String searchTerm);
}
