package com.gwtcraft.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtcraft.client.presenter.Application;
import com.gwtcraft.client.service.ArmoryService;
import com.gwtcraft.client.service.ArmoryServiceAsync;


public class GwtCraft implements EntryPoint {
	
	public void onModuleLoad() {
		ArmoryServiceAsync armoryService = GWT.create(ArmoryService.class);
		
		HandlerManager eventBus = new HandlerManager(null);
		
		Application app = new Application(armoryService, eventBus);
		app.go(RootPanel.get());
	}
}
