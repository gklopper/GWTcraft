package com.gwtcraft.client.presenter.character;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.ItemDetail;
import com.gwtcraft.client.presenter.Presenter;
import com.gwtcraft.client.service.ArmoryServiceAsync;
import com.gwtcraft.client.view.character.CurrentItemDisplay;
import com.gwtcraft.client.view.character.CurrentItemStatisticDisplay;

public class CurrentItemPresenter implements Presenter {

	private final HandlerManager eventBus;
	private final Display display;
	private final ArmoryServiceAsync armoryService;

	public interface Display {
		Widget asWidget();
		HasValue<String> getIdField();
		HasValue<String> getSlotField();
		HasText getName();
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
				display.getName().setText(result.getName());
				if (result.getStamina() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Stm", result.getStamina());
					new CurrentItemStatisticPresenter(armoryService, eventBus, view).go(display.getDetails());
				}
				
				if (result.getAgility() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Agi", result.getAgility());
					new CurrentItemStatisticPresenter(armoryService, eventBus, view).go(display.getDetails());
				}
				
				if (result.getArmor() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Armor", result.getArmor());
					new CurrentItemStatisticPresenter(armoryService, eventBus, view).go(display.getDetails());
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
