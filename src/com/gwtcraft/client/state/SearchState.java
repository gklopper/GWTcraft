package com.gwtcraft.client.state;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.event.EventBus;
import com.gwtcraft.client.service.ArmoryServiceAsync;
import com.gwtcraft.client.ui.search.SearchBox;
import com.gwtcraft.client.ui.search.SearchResultDisplay;

public class SearchState extends Composite implements State{

	private static SearchStateUiBinder uiBinder = GWT
			.create(SearchStateUiBinder.class);

	interface SearchStateUiBinder extends UiBinder<Widget, SearchState> {
	}

	@UiField
	FlowPanel wrapper;
	private EventBus eventBus;
	private SearchBox searchBox;
	private SearchResultDisplay resultDisplay;

	public SearchState(ArmoryServiceAsync armoryService, EventBus eventBus) {
		this.eventBus = eventBus;
		initWidget(uiBinder.createAndBindUi(this));
		
		searchBox = new SearchBox(eventBus, armoryService);
		resultDisplay = new SearchResultDisplay();
		wrapper.add(searchBox);
		wrapper.add(resultDisplay);
	}

	@Override
	public void putUp() {
		RootPanel.get().add(this);
		eventBus.addHandler(resultDisplay);
	}

	@Override
	public void takeDown() {
		RootPanel.get().remove(this);
		eventBus.removeHandler(resultDisplay);
	}
}
