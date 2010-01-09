package com.gwtcraft.client.presenter.character;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.ItemDetail;
import com.gwtcraft.client.presenter.Presenter;
import com.gwtcraft.client.service.ArmoryServiceAsync;

public class CurrentItemPresenter implements Presenter {

	private final HandlerManager eventBus;
	private final Display display;
	private final ArmoryServiceAsync armoryService;

	public interface Display {
		Widget asWidget();
		HasValue<String> getIdField();
		HasValue<String> getSlotField();
		HasWidgets getDetails();
	}
	
	public CurrentItemPresenter(ArmoryServiceAsync armoryService, HandlerManager eventBus, Display view) {
		this.armoryService = armoryService;
		this.eventBus = eventBus;
		this.display = view;
		bind();
	}
	
	private void bind() {
		Integer itemId = Integer.parseInt(display.getIdField().getValue());
		
		armoryService.loadItem(itemId, new AsyncCallback<ItemDetail>() {
			
			@Override
			public void onSuccess(ItemDetail result) {
				display.getDetails().clear();
				// TODO make me a presenter
				display.getDetails().add(new Label(result.getName()));
				if (result.getStamina() > 0) {
					display.getDetails().add(new Label("Stam: " + result.getStamina()));
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				//TODO option to retry or something
				display.getDetails().add(new Label(caught.getMessage()));
			}
		});
		
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
}
