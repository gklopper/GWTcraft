package com.gwtcraft.client.places.upgrade.statistic.copy;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.Statistic;
import com.gwtcraft.client.places.Presenter;

public class UpgradeItemStatisticPresenter implements Presenter {

	private final Display display;
	private final Statistic statistic;

	public interface Display {
		Widget asWidget();
		HasText getStatistic();
		HasText getValue();
		void setUpgrade(boolean isUpgrade);
	}
	
	public UpgradeItemStatisticPresenter(Display view, Statistic statistic) {
		this.display = view;
		this.statistic = statistic;
	}
	
	private void bind() {
		display.getStatistic().setText(statistic.getName());
		display.getValue().setText(statistic.getValue().toString());
		if (statistic.getValue() > 0) {
			display.setUpgrade(true);
		} else {
			display.setUpgrade(false);
		}
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
}
