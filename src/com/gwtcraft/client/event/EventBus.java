package com.gwtcraft.client.event;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;

public class EventBus {
	
	private List<EventHandler> handlers = new ArrayList<EventHandler>();
	
	public void addHandler(EventHandler handler) {
		handlers.add(handler);
	}
	
	public void removeHandler(EventHandler handler) {
		handlers.remove(handler);
	}
	
	public void fireEvent(final Event event) {
		for (final EventHandler handler : handlers) {
			
			GWT.runAsync(new RunAsyncCallback() {
				
				@Override
				public void onSuccess() {
					handler.handleEvent(event);
				}
				
				@Override
				public void onFailure(Throwable reason) {
					Window.alert(reason.getMessage());
				}
			});
			handler.handleEvent(event);
		}
	}
}
