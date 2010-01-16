package com.gwtcraft.client.view.character;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.presenter.character.ItemUpgradesPresenter.Display;

public class ItemUpgradesDisplay extends Composite implements Display {

	private static CharacterItemsDisplayUiBinder uiBinder = GWT.create(CharacterItemsDisplayUiBinder.class);

	interface CharacterItemsDisplayUiBinder extends
			UiBinder<Widget, ItemUpgradesDisplay> {
	}

	@UiField
	HasWidgets itemsWrapper;
	
	@UiField
	HasWidgets currentItemWrapper;
	
	@UiField
	HasText name;
	
	@UiField
	HasText realm;

	public ItemUpgradesDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
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

	@Override
	public HasWidgets getCurrentItemWrapper() {
		return currentItemWrapper;
	}
}
