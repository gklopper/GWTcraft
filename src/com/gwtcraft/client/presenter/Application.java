package com.gwtcraft.client.presenter;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.gwtcraft.client.service.ArmoryServiceAsync;
import com.gwtcraft.client.view.search.SearchView;

public class Application implements Presenter, ValueChangeHandler<String> {

	
	
	private final ArmoryServiceAsync armoryService;
	private final HandlerManager eventBus;
	private HasWidgets container;

	public Application(ArmoryServiceAsync armoryService, HandlerManager eventBus) {
		this.armoryService = armoryService;
		this.eventBus = eventBus;
		bind();
	}
	
	private void bind() {
		History.addValueChangeHandler(this);
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
		newSearch();
	}

	public void newSearch() {
		new SearchPresenter(armoryService, eventBus, new SearchView()).go(container);
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		
		
	}

}
