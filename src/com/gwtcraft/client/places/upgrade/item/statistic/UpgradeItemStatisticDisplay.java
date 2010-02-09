package com.gwtcraft.client.places.upgrade.item.statistic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.places.upgrade.item.statistic.UpgradeItemStatisticPresenter.Display;

public class UpgradeItemStatisticDisplay extends Composite implements Display {

	private static CurrentItemStatisticDisplayUiBinder uiBinder = GWT
			.create(CurrentItemStatisticDisplayUiBinder.class);

	interface CurrentItemStatisticDisplayUiBinder extends
			UiBinder<Widget, UpgradeItemStatisticDisplay> {
	}
	
	interface Style extends CssResource {
		String upgrade();
		String downgrade();
	}
	
	@UiField
	Style style;

	@UiField
	HasText statistic;

	@UiField
	HasText value;
	
	@UiField
	Widget wrapper;

	public UpgradeItemStatisticDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasText getStatistic() {
		return statistic;
	}

	@Override
	public HasText getValue() {
		return value;
	}
	
	public void setUpgrade(boolean isUpgrade) {
		if (isUpgrade) {
			wrapper.setStyleName(style.upgrade());
		} else {
			wrapper.setStyleName(style.downgrade());
		}
	}
}
