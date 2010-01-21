package com.gwtcraft.client.presenter.character;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.ItemDetail;
import com.gwtcraft.client.presenter.Presenter;
import com.gwtcraft.client.service.ArmoryServiceAsync;
import com.gwtcraft.client.view.character.CurrentItemDisplay;

import java.util.Iterator;
import java.util.List;

public class ItemUpgradesPresenter implements Presenter {

	private final Display display;
	private final ArmoryServiceAsync armoryService;
	private final String name;
	private final String realm;
	private final Integer itemId;

	public interface Display {
		Widget asWidget();
		HasText getNameField();
		HasText getRealmField();
		HasWidgets getItemsWrapper();
		HasWidgets getCurrentItemWrapper();
	}
	
	public ItemUpgradesPresenter(ArmoryServiceAsync armoryService, Display view, String name, String realm, Integer itemId) {
		this.armoryService = armoryService;
		this.display = view;
		this.name = name;
		this.realm = realm;
		this.itemId = itemId;
	}
	
	private void bind() {
		
		display.getNameField().setText(name);
		display.getRealmField().setText(realm);
		
		//first load the current equipped item
		armoryService.loadItem(itemId, new AsyncCallback<ItemDetail>() {

			@Override
			public void onFailure(Throwable caught) {
				display.getCurrentItemWrapper().add(new Label("Unable to load item: " + caught.getMessage()));
			}

			@Override
			public void onSuccess(ItemDetail result) {
				CurrentItemDisplay view = new CurrentItemDisplay();
				new CurrentItemPresenter(armoryService, null, view, name, realm, result.getId(), false).go(display.getCurrentItemWrapper());
				
				armoryService.loadUpgradesFor(name, realm, itemId, new AsyncCallback<List<Integer>>() {

					@Override
					public void onFailure(Throwable caught) {
						display.getItemsWrapper().clear();
						display.getItemsWrapper().add(new Label("An error has occurred: " + caught.getMessage()));
					}

					@Override
					public void onSuccess(final List<Integer> items) {
						display.getItemsWrapper().clear();
						DeferredCommand.addCommand(new DisplayItemCommand(items));
					}
				});
				
			}
		});
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
	
	private final class DisplayItemCommand implements IncrementalCommand {
		Iterator<Integer> itemsIterator;

		private DisplayItemCommand(List<Integer> items) {
			itemsIterator = items.iterator();
		}

		public boolean execute() {
			if (itemsIterator.hasNext()) {
				final Integer itemId = itemsIterator.next();
				CurrentItemDisplay view = new CurrentItemDisplay();
				new CurrentItemPresenter(armoryService, null, view, name, realm, itemId, false).go(display.getItemsWrapper());
			}
			return itemsIterator.hasNext();
		}
	}
}
