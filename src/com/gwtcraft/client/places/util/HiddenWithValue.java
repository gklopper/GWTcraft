package com.gwtcraft.client.places.util;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Hidden;

public class HiddenWithValue extends Hidden implements HasValue<String> {

	@Override
	public void setValue(String value, boolean fireEvents) {
		throw new IllegalArgumentException();
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		throw new IllegalArgumentException();
	}
}
