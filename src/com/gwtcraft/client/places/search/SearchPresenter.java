package com.gwtcraft.client.places.search;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.event.RegionChangedEvent;
import com.gwtcraft.client.event.SearchInitiatedEvent;
import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.places.Application;
import com.gwtcraft.client.places.Presenter;
import com.gwtcraft.client.places.util.Spinner;
import com.gwtcraft.client.service.ArmoryServiceAsync;

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
		HasChangeHandlers getRegion();
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
	
	@SuppressWarnings("unchecked")
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
		
		((HasValue<String>) display.getRegion()).setValue(Application.getRegionCode());
		
		display.getRegion().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String region = ((HasValue<String>) event.getSource()).getValue();
				eventBus.fireEvent(new RegionChangedEvent(region));
			}
		});
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
	
	private void search(final String searchTerm) {
		display.getResultArea().clear();
		
		display.getResultArea().add(new Spinner());
		
		armoryService.search(Application.getRegionCode(), searchTerm, new AsyncCallback<List<ArmoryCharacter>>() {
			
			@Override
			public void onSuccess(final List<ArmoryCharacter> characters) {
				display.getResultArea().clear();
				DeferredCommand.addCommand(new DisplayCharacterCommand(characters));
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

	private final class DisplayCharacterCommand implements IncrementalCommand {
		final Iterator<ArmoryCharacter> characterIterator;

		private DisplayCharacterCommand(List<ArmoryCharacter> characters) {
			characterIterator = characters.iterator();
		}

		@Override
		public boolean execute() {
			if (characterIterator.hasNext()) {
				ArmoryCharacter character = characterIterator.next();
				SearchCharacterDisplay characterView = new SearchCharacterDisplay(character.getName(), character.getRealm());
				new SearchCharacterPresenter(eventBus, characterView).go(display.getResultArea());
				return true;
			}
			return false;
		}
	}
}
