package com.gwtcraft.client.places.upgrade;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.ItemDetail;
import com.gwtcraft.client.places.Application;
import com.gwtcraft.client.places.Presenter;
import com.gwtcraft.client.places.current.item.CurrentItemDisplay;
import com.gwtcraft.client.places.current.item.CurrentItemPresenter;
import com.gwtcraft.client.places.upgrade.item.UpgradeItemDisplay;
import com.gwtcraft.client.places.upgrade.item.UpgradeItemPresenter;
import com.gwtcraft.client.service.ArmoryServiceAsync;

public class ItemUpgradesPresenter implements Presenter {

	private final Display display;
	private final ArmoryServiceAsync armoryService;
	private final String name;
	private final String realm;
	private ItemDetail currentItem;

	public interface Display {
		Widget asWidget();
		HasText getNameField();
		HasText getRealmField();
		HasWidgets getItemsWrapper();
		HasWidgets getCurrentItemWrapper();
	}
	
	public ItemUpgradesPresenter(ArmoryServiceAsync armoryService, 
								Display view, 
								String name, 
								String realm, 
								Integer currentItemId) {
		this.armoryService = armoryService;
		this.display = view;
		this.name = name;
		this.realm = realm;
		this.currentItem = new ItemDetail();
		currentItem.setId(currentItemId);
	}
	
	private void bind() {
		
		display.getNameField().setText(name);
		display.getRealmField().setText(realm);
		
		//first load the current equipped item
		armoryService.loadItem(Application.getRegionCode(), currentItem.getId(), new AsyncCallback<ItemDetail>() {

			@Override
			public void onFailure(Throwable caught) {
				display.getCurrentItemWrapper().add(new Label("Unable to load item: " + caught.getMessage()));
			}

			@Override
			public void onSuccess(ItemDetail result) {
				currentItem = result;
				CurrentItemDisplay view = new CurrentItemDisplay();
				new CurrentItemPresenter(armoryService, null, view, name, realm, currentItem.getId(), false).go(display.getCurrentItemWrapper());
				
				armoryService.loadUpgradesFor(Application.getRegionCode(), name, realm, currentItem.getId(), new AsyncCallback<List<Integer>>() {

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
				UpgradeItemDisplay view = new UpgradeItemDisplay();
				new UpgradeItemPresenter(armoryService, view, currentItem, itemId).go(display.getItemsWrapper());
			}
			return itemsIterator.hasNext();
		}
	}
}
