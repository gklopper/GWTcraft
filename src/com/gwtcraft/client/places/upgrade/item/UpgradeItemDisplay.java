package com.gwtcraft.client.places.upgrade.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.places.upgrade.item.UpgradeItemPresenter.Display;

public class UpgradeItemDisplay extends Composite implements Display{

	private static UpgradeItemDisplayUiBinder uiBinder = GWT.create(UpgradeItemDisplayUiBinder.class);

	interface UpgradeItemDisplayUiBinder extends UiBinder<Widget, UpgradeItemDisplay> {
	}
	
	interface Style extends CssResource{
		String currentItem();
	}
	
	@UiField
	Style style;
	
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
	
	@UiField
	HasText source;
	
	@UiField
	HasText creature;
	
	@UiField
	HasText area;
	
	@UiField
	HasText itemLevel;

	public UpgradeItemDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
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
	public HasText getArea() {
		return area;
	}

	@Override
	public HasText getCreature() {
		return creature;
	}

	@Override
	public HasText getSource() {
		return source;
	}
	
	@Override
	public HasText getItemLevel() {
		return itemLevel;
	}
	
	@Override
	public void isCurrentItem(boolean isCurrentItem) {
		if (isCurrentItem) {
			setStyleName(style.currentItem());
		}
	}
}
