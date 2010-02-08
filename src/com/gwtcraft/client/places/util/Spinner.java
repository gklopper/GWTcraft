package com.gwtcraft.client.places.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Spinner extends Composite {

	private static SpinnerUiBinder uiBinder = GWT.create(SpinnerUiBinder.class);

	interface SpinnerUiBinder extends UiBinder<Widget, Spinner> {
	}
	
	public Spinner() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
