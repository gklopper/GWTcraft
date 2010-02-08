package com.gwtcraft.client.places.upgrade;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.Item;
import com.gwtcraft.client.model.ItemDetail;
import com.gwtcraft.client.model.Statistic;
import com.gwtcraft.client.places.Presenter;
import com.gwtcraft.client.places.character.CurrentItemStatisticDisplay;
import com.gwtcraft.client.places.character.CurrentItemStatisticPresenter;
import com.gwtcraft.client.service.ArmoryServiceAsync;

public class UpgradeItemPresenter implements Presenter {

	private final Display display;
	private final ArmoryServiceAsync armoryService;
	private final Item upgradeItem;
	private final ItemDetail currentItem;
	

	public interface Display {
		Widget asWidget();
		HasText getName();
		HasText getSource();
		HasText getCreature();
		HasText getArea();
		HasWidgets getStatsOne();
		HasWidgets getStatsTwo();
		HasWidgets getSpells();
		HasWidgets getIconWrapper();
		HasClickHandlers getSelectButton();
	}
	
	public UpgradeItemPresenter(ArmoryServiceAsync armoryService,
								Display view,
								ItemDetail currentItem,
								Item upgradeItem) {
		this.armoryService = armoryService;
		this.display = view;
		this.currentItem = currentItem;
		this.upgradeItem = upgradeItem;
	}

	private void bind() {
		Integer itemId = upgradeItem.getId();
		
		armoryService.loadItem(itemId, new AsyncCallback<ItemDetail>() {
			
			@Override
			public void onSuccess(final ItemDetail item) {
				display.getName().setText(item.getName());
				
				if (item.getCreatureName() == null) {
					display.getSource().setText(item.getSource());
				} else {
					display.getCreature().setText(item.getCreatureName() + " - ");
					display.getArea().setText(item.getAreaName());
				}
				
				display.getIconWrapper().clear();
				
				//TODO check T&Cs for using these images directly
				display.getIconWrapper().add(new Image("http://eu.wowarmory.com/wow-icons/_images/51x51/" + item.getIcon() + ".jpg"));
				
				
				if (item.getArmor() > 0) {
					UpgradeItemStatisticDisplay view = new UpgradeItemStatisticDisplay("Armor", item.getArmor());
					new UpgradeItemStatisticPresenter(view, null).go(display.getStatsOne());
				}
				
				int count = 0;
				for (Statistic stat : item.getStatistics()) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay(stat.getName(), stat.getValue());
					CurrentItemStatisticPresenter presenter = new CurrentItemStatisticPresenter(view);
					if (count++ < 3) {
						//3 + armor = 4
						presenter.go(display.getStatsOne());
					} else {
						presenter.go(display.getStatsTwo());
					}
				}
				
				//TODO MP5
				
				if (item.getMetaSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Meta socket", item.getMetaSockets());
					new CurrentItemStatisticPresenter(view).go(display.getStatsOne());
				}
				
				if (item.getRedSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Red socket", item.getRedSockets());
					new CurrentItemStatisticPresenter(view).go(display.getStatsOne());
				}
				
				if (item.getYellowSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Yellow socket", item.getYellowSockets());
					new CurrentItemStatisticPresenter(view).go(display.getStatsOne());
				}
				
				if (item.getBlueSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Blue socket", item.getBlueSockets());
					new CurrentItemStatisticPresenter(view).go(display.getStatsOne());
				}
				
				for (String use : item.getUse()) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Use", use);
					new CurrentItemStatisticPresenter(view).go(display.getSpells());
				}
				
				for (String equip : item.getEquip()) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Equip", equip);
					new CurrentItemStatisticPresenter(view).go(display.getSpells());
				} 
			}
			
			@Override
			public void onFailure(Throwable caught) {
				//TODO option to retry or something
				display.getStatsOne().add(new Label(caught.getMessage()));
			}
		});
		
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
}
