package com.gwtcraft.client.places.upgrade;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.places.upgrade.UpgradeItemStatisticPresenter.Display;

public class UpgradeItemStatisticDisplay extends Composite implements Display {

	private static CurrentItemStatisticDisplayUiBinder uiBinder = GWT
			.create(CurrentItemStatisticDisplayUiBinder.class);

	interface CurrentItemStatisticDisplayUiBinder extends
			UiBinder<Widget, UpgradeItemStatisticDisplay> {
	}

	@UiField
	HasText statistic;

	@UiField
	HasText value;

	public UpgradeItemStatisticDisplay(String statistic, Integer value) {
		this(statistic, String.valueOf(value));
	}
	
	public UpgradeItemStatisticDisplay(String statistic, String value) {
		initWidget(uiBinder.createAndBindUi(this));
		this.statistic.setText(statistic);
		this.value.setText(String.valueOf(value));
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
}
