package com.gwtcraft.client.presenter.character;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.Statistic;
import com.gwtcraft.client.presenter.Presenter;

public class UpgradeItemStatisticPresenter implements Presenter {

	private final Display display;
	private final Statistic statistic;

	public interface Display {
		Widget asWidget();
		HasText getStatistic();
		HasText getValue();
	}
	
	public UpgradeItemStatisticPresenter(Display view, Statistic statistic) {
		this.display = view;
		this.statistic = statistic;
	}
	
	private void bind() {
	
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
}
