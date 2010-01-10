package com.gwtcraft.client.view.character;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.presenter.character.CharacterItemsPresenter.Display;

public class CharacterItemsDisplay extends Composite implements Display {

	private static CharacterItemsDisplayUiBinder uiBinder = GWT
			.create(CharacterItemsDisplayUiBinder.class);

	interface CharacterItemsDisplayUiBinder extends
			UiBinder<Widget, CharacterItemsDisplay> {
	}

	@UiField
	HasWidgets itemsWrapper;
	
	@UiField
	HasText name;
	
	@UiField
	HasText realm;

	public CharacterItemsDisplay(String name, String realm) {
		initWidget(uiBinder.createAndBindUi(this));
		this.name.setText(name);
		this.realm.setText(realm);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasWidgets getItemsWrapper() {
		return itemsWrapper;
	}

	@Override
	public HasText getNameField() {
		return name;
	}

	@Override
	public HasText getRealmField() {
		return realm;
	}
}
