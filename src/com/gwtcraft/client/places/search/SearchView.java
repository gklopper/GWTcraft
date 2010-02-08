package com.gwtcraft.client.places.search;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class SearchView extends Composite implements SearchPresenter.Display {

	private static SearchBoxUiBinder uiBinder = GWT
			.create(SearchBoxUiBinder.class);

	interface SearchBoxUiBinder extends UiBinder<Widget, SearchView> {
	}

	@UiField
	HasClickHandlers search;
	
	@UiField
	HasValue<String> searchText;
	
	@UiField
	HasWidgets resultsWrapper;
	
	@UiField
	HasWidgets recentSearches;
	
	public SearchView() {
	  initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public Widget asWidget() {
		return this;
	}


	@Override
	public HasWidgets getResultArea() {
		return resultsWrapper;
	}


	@Override
	public HasClickHandlers getSearchButton() {
		return search;
	}


	@Override
	public HasValue<String> getSearchField() {
		return searchText;
	}


	@Override
	public HasWidgets getRecentSearches() {
		return recentSearches;
	}
}
