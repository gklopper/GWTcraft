package com.gwtcraft.client.places.util;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

public class ListBoxWithValue extends ListBox implements HasValue<String>{

	@Override
	public String getValue() {
		return super.getValue(super.getSelectedIndex());
	}

	@Override
	public void setValue(String value) {
	
		int numItems = getItemCount();
		
		for (int x = 0; x < numItems; x++) {
			if (getValue(x).equals(value)) {
				setSelectedIndex(x);
				return;
			}
			
		}
		throw new IllegalArgumentException("ListBox does not contain value: " + value);
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		throw new RuntimeException("method not implemented");
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		throw new RuntimeException("method not implemented");
	}

}
