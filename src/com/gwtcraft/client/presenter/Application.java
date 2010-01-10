package com.gwtcraft.client.presenter;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.gwtcraft.client.event.CharacterSelectedEvent;
import com.gwtcraft.client.event.CharacterSelectedEventHandler;
import com.gwtcraft.client.presenter.character.CharacterItemsPresenter;
import com.gwtcraft.client.presenter.search.SearchPresenter;
import com.gwtcraft.client.service.ArmoryServiceAsync;
import com.gwtcraft.client.view.character.CharacterItemsDisplay;
import com.gwtcraft.client.view.search.SearchView;

public class Application implements Presenter, ValueChangeHandler<String> {

	
	
	private final ArmoryServiceAsync armoryService;
	private final HandlerManager eventBus;
	private HasWidgets container;

	public Application(ArmoryServiceAsync armoryService, HandlerManager eventBus) {
		this.armoryService = armoryService;
		this.eventBus = eventBus;
		addHandlers();
	}
	
	private void addHandlers() {
		History.addValueChangeHandler(this);
		eventBus.addHandler(CharacterSelectedEvent.TYPE, new CharacterSelectedEventHandler() {
			@Override
			public void onCharacterSelected(CharacterSelectedEvent event) {
				showCharacter(event.getName(), event.getRealm());
			}
		});
	}

	private void showCharacter(String name, String realm) {
		container.clear();
		CharacterItemsDisplay view = new CharacterItemsDisplay(name, realm);
		new CharacterItemsPresenter(armoryService, eventBus, view).go(container);
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
