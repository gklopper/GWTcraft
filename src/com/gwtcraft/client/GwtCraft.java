package com.gwtcraft.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtcraft.client.event.EventBus;
import com.gwtcraft.client.service.ArmoryService;
import com.gwtcraft.client.service.ArmoryServiceAsync;
import com.gwtcraft.client.state.SearchState;
import com.gwtcraft.client.state.State;

public class GwtCraft implements EntryPoint {
	
	private final ArmoryServiceAsync armoryService = GWT
			.create(ArmoryService.class);

	private final EventBus eventBus = new EventBus();
	
	private static State currentState;
	
	public void onModuleLoad() {
		switchStateTo(new SearchState(armoryService, eventBus));
	}
	
	public static void switchStateTo(State state) {
		if (currentState != null) {
			currentState.takeDown();
		}	
		currentState = state;
		currentState.putUp();
	}
}
