package com.gwtcraft.client.places.search;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ListBox;
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
	
	@UiField HasChangeHandlers region;
	
	public SearchView() {
	  initWidget(uiBinder.createAndBindUi(this));
	  ListBox regions = (ListBox) region;
	  regions.addItem("Americas", "www");
	  regions.addItem("Europe", "eu");
	  regions.addItem("Korea", "kr");
	  regions.addItem("China", "cn");
	  regions.addItem("Taiwan", "tw");
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
	
	@Override
	public HasChangeHandlers getRegion() {
		return region;
	}
}
