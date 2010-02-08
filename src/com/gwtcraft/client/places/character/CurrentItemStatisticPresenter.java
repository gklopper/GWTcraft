package com.gwtcraft.client.places.character;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.places.Presenter;

public class CurrentItemStatisticPresenter implements Presenter {

	private final Display display;

	public interface Display {
		Widget asWidget();
		HasText getStatistic();
		HasText getValue();
	}
	
	public CurrentItemStatisticPresenter(Display view) {
		this.display = view;
	}
	
	private void bind() {
	
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
}
