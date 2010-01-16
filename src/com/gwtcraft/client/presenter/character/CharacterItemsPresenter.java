package com.gwtcraft.client.presenter.character;

import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.Item;
import com.gwtcraft.client.presenter.Presenter;
import com.gwtcraft.client.service.ArmoryServiceAsync;
import com.gwtcraft.client.view.character.CurrentItemDisplay;

public class CharacterItemsPresenter implements Presenter {

	private final HandlerManager eventBus;
	private final Display display;
	private final ArmoryServiceAsync armoryService;

	public interface Display {
		Widget asWidget();
		HasText getNameField();
		HasText getRealmField();
		HasWidgets getItemsWrapper();
	}
	
	public CharacterItemsPresenter(ArmoryServiceAsync armoryService, HandlerManager eventBus, Display view) {
		this.armoryService = armoryService;
		this.eventBus = eventBus;
		this.display = view;
	}
	
	private void bind() {
		armoryService.loadItemsFor(display.getNameField().getText(), display.getRealmField().getText(), new AsyncCallback<List<Item>>() {
			
			@Override
			public void onSuccess(List<Item> result) {
				display.getItemsWrapper().clear();
				for (Item item : result) {
					CurrentItemDisplay view = new CurrentItemDisplay(item.getId(), item.getSlot());
					new CurrentItemPresenter(armoryService, 
											eventBus, 
											view, 
											display.getNameField().getText(), 
											display.getRealmField().getText(),
											true).go(display.getItemsWrapper());
				}
				
				if (result.isEmpty()) {
					display.getItemsWrapper().add(new Label("No items were found for this character."));
					display.getItemsWrapper().add(new Label("This usually happens when characters have been inactive for a long time."));
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				display.getItemsWrapper().clear();
				//TODO something prettier than this
				display.getItemsWrapper().add(new Label(caught.getMessage()));
			}
		});
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
}
