package com.gwtcraft.client.presenter.search;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.event.SearchInitiatedEvent;
import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.presenter.Presenter;
import com.gwtcraft.client.service.ArmoryServiceAsync;
import com.gwtcraft.client.view.search.SearchCharacterDisplay;
import com.gwtcraft.client.view.util.Spinner;

public class SearchPresenter implements Presenter {

	private final ArmoryServiceAsync armoryService;
	private final HandlerManager eventBus;
	private final Display display;
	private final String searchTerm;

	public interface Display {
		HasClickHandlers getSearchButton();
		HasValue<String> getSearchField();
		HasWidgets getResultArea();
		HasWidgets getRecentSearches();
		Widget asWidget();
	}
	
	public SearchPresenter(ArmoryServiceAsync armoryService, HandlerManager eventBus, Display view) {
		this(armoryService, eventBus, view, null);
	}
	
	public SearchPresenter(ArmoryServiceAsync armoryService, HandlerManager eventBus, Display view, String searchTerm) {
		this.armoryService = armoryService;
		this.eventBus = eventBus;
		this.display = view;
		this.searchTerm = searchTerm;
	}
	
	private void bind() {
		display.getSearchButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String searchTerm = display.getSearchField().getValue();
				eventBus.fireEvent(new SearchInitiatedEvent(searchTerm));
			}
		});
		
		if (searchTerm != null) {
			display.getSearchField().setValue(searchTerm);
			search(searchTerm);
		}
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
	
	private void search(final String searchTerm) {
		display.getResultArea().clear();
		
		display.getResultArea().add(new Spinner());
		
		armoryService.search(searchTerm, new AsyncCallback<List<ArmoryCharacter>>() {
			
			@Override
			public void onSuccess(List<ArmoryCharacter> characters) {
				display.getResultArea().clear();
				for (ArmoryCharacter character : characters) {
					SearchCharacterDisplay characterView = new SearchCharacterDisplay(character.getName(), character.getRealm());
					new SearchCharacterPresenter(eventBus, characterView).go(display.getResultArea());
				}
				
				if (characters.isEmpty()) {
					display.getResultArea().add(new Label("Search returned no results"));
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				display.getResultArea().clear();
				display.getResultArea().add(new Label("An error has occurred:" ));
				display.getResultArea().add(new Label("\"" + caught.getMessage() + "\""));
				display.getResultArea().add(new Label("Please try search again..."));
			}
		});
	}

}
