package com.gwtcraft.client.view.character;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.presenter.character.CurrentItemPresenter.Display;

public class CurrentItemDisplay extends Composite implements Display{

	private static CurrentItemDisplayUiBinder uiBinder = GWT
			.create(CurrentItemDisplayUiBinder.class);

	interface CurrentItemDisplayUiBinder extends UiBinder<Widget, CurrentItemDisplay> {
	}
	
	@UiField
	HasClickHandlers selectButton;

	@UiField
	HasValue<String> slotId;
	
	@UiField
	HasValue<String> itemId;
	
	@UiField
	HasWidgets statsOne;
	
	@UiField
	HasWidgets statsTwo;
	
	@UiField
	HasWidgets spells;
	
	@UiField
	HasWidgets iconWrapper;
	
	@UiField
	HasText name;

	public CurrentItemDisplay(Integer itemId, Integer slotId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.slotId.setValue(String.valueOf(slotId));
		this.itemId.setValue(String.valueOf(itemId));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasValue<String> getIdField() {
		return itemId;
	}

	@Override
	public HasValue<String> getSlotField() {
		return slotId;
	}

	@Override
	public HasText getName() {
		return name;
	}

	@Override
	public HasWidgets getIconWrapper() {
		return iconWrapper;
	}

	@Override
	public HasWidgets getStatsOne() {
		return statsOne;
	}

	@Override
	public HasWidgets getStatsTwo() {
		return statsTwo;
	}

	@Override
	public HasWidgets getSpells() {
		return spells;
	}

	@Override
	public HasClickHandlers getSelectButton() {
		return selectButton;
	}
}
