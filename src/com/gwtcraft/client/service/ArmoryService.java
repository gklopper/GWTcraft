package com.gwtcraft.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwtcraft.client.model.ArmoryCharacter;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface ArmoryService extends RemoteService {
	List<ArmoryCharacter> search(String searchTerm);
}
