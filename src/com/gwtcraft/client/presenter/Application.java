package com.gwtcraft.client.presenter;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.gwtcraft.client.event.CharacterSelectedEvent;
import com.gwtcraft.client.event.CharacterSelectedEventHandler;
import com.gwtcraft.client.event.SearchInitiatedEvent;
import com.gwtcraft.client.event.SearchInitiatedEventHandler;
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
				History.newItem("c=" + URL.encode(event.getName() + "&r=" + event.getRealm()));
			}
		});
		
		eventBus.addHandler(SearchInitiatedEvent.TYPE, new SearchInitiatedEventHandler() {
			@Override
			public void onSearchInitiated(SearchInitiatedEvent event) {
				History.newItem("q=" + URL.encode(event.getSearchTerm()));
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
		if ("".equals(History.getToken())) {
			History.newItem("search");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		container.clear();
		
		if (token.equals("search")) {
			new SearchPresenter(armoryService, eventBus, new SearchView()).go(container);
		} else if (token.startsWith("q=")) {
			String searchTerm = URL.decode(token.replace("q=", ""));
			new SearchPresenter(armoryService, eventBus, new SearchView(), searchTerm).go(container);
		} else if (token.startsWith("c=")) {
			String combined = token.replace("c=", "");
			String[] details = combined.split("&r="); 
			showCharacter(URL.decode(details[0]), URL.decode(details[1]));
		} else {
			//default is new blank search page
			History.newItem("search");
		}
	}
}
