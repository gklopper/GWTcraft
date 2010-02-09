package com.gwtcraft.client.places.character.spell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.places.character.spell.CurrentItemSpellPresenter.Display;

public class CurrentItemSpellDisplay extends Composite implements Display {

	private static CurrentItemStatisticDisplayUiBinder uiBinder = GWT
			.create(CurrentItemStatisticDisplayUiBinder.class);

	interface CurrentItemStatisticDisplayUiBinder extends
			UiBinder<Widget, CurrentItemSpellDisplay> {
	}

	@UiField
	HasText type;

	@UiField
	HasText description;

	public CurrentItemSpellDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasText getType() {
		return type;
	}

	@Override
	public HasText getDescription() {
		return description;
	}
}
