package com.gwtcraft.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.ArmoryCharacter;
import com.gwtcraft.client.service.ArmoryServiceAsync;
import com.gwtcraft.client.view.search.SearchCharacter;
import com.gwtcraft.client.view.util.Spinner;

public class SearchPresenter implements Presenter {

	private final ArmoryServiceAsync armoryService;
	private final HandlerManager eventBus;
	private final Display display;

	public interface Display {
		HasClickHandlers getSearchButton();
		HasValue<String> getSearchField();
		HasWidgets getResultArea();
		Widget asWidget();
	}
	
	public SearchPresenter(ArmoryServiceAsync armoryService, HandlerManager eventBus, Display view) {
		this.armoryService = armoryService;
		this.eventBus = eventBus;
		this.display = view;
	}
	
	private void bind() {
		display.getSearchButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String searchTerm = display.getSearchField().getValue();
				search(searchTerm);
			}
		});		
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		display.getResultArea().clear();
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
					display.getResultArea().add(new SearchCharacter(character));
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
				display.getResultArea().add(new Label("Please try search again"));
			}
		});
	}

}
