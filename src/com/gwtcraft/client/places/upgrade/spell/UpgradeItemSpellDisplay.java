package com.gwtcraft.client.places.upgrade.spell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class UpgradeItemSpellDisplay extends Composite implements UpgradeItemSpellPresenter.Display {

	private static CurrentItemStatisticDisplayUiBinder uiBinder = GWT
			.create(CurrentItemStatisticDisplayUiBinder.class);

	interface CurrentItemStatisticDisplayUiBinder extends
			UiBinder<Widget, UpgradeItemSpellDisplay> {
	}
	
	interface Style extends CssResource {
		String upgrade();
		String downgrade();
	}
	
	@UiField
	Style style;

	@UiField
	HasText type;

	@UiField
	HasText description;
	
	@UiField
	Widget wrapper;

	public UpgradeItemSpellDisplay() {
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
	
	public void setUpgrade(boolean isUpgrade) {
		if (isUpgrade) {
			wrapper.setStyleName(style.upgrade());
		} else {
			wrapper.setStyleName(style.downgrade());
		}
	}
}
