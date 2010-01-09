package com.gwtcraft.client.view.search;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.presenter.SearchCharacterPresenter.Display;

public class SearchCharacterDisplay extends Composite implements Display {

	private static SearchCharacterUiBinder uiBinder = GWT
			.create(SearchCharacterUiBinder.class);

	interface SearchCharacterUiBinder extends UiBinder<Widget, SearchCharacterDisplay> {
	}

	@UiField
	Label realm;
	
	@UiField
	Label name;
	
	@UiField
	FocusPanel characterArea;

	public SearchCharacterDisplay(String name, String realm) {
		initWidget(uiBinder.createAndBindUi(this));
		this.name.setText(name);
		this.realm.setText(realm);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasText getNameField() {
		return name;
	}

	@Override
	public HasText getRealmField() {
		return realm;
	}

	@Override
	public HasClickHandlers getSelectedButton() {
		return characterArea;
	}
}