package com.gwtcraft.client.presenter.character;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.ItemDetail;
import com.gwtcraft.client.model.Statistic;
import com.gwtcraft.client.presenter.Presenter;
import com.gwtcraft.client.service.ArmoryServiceAsync;
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
		HasWidgets getStatsOne();
		HasWidgets getStatsTwo();
		HasWidgets getSpells();
		HasWidgets getIconWrapper();
	}
	
	public CurrentItemPresenter(ArmoryServiceAsync armoryService, HandlerManager eventBus, Display view) {
		this.armoryService = armoryService;
		this.eventBus = eventBus;
		this.display = view;
	}
	
	private void bind() {
		Integer itemId = Integer.parseInt(display.getIdField().getValue());
		
		armoryService.loadItem(itemId, new AsyncCallback<ItemDetail>() {
			
			@Override
			public void onSuccess(ItemDetail result) {
				display.getName().setText(result.getName());
				display.getIconWrapper().clear();
				
				//TODO check T&Cs for using these images directly
				display.getIconWrapper().add(new Image("http://eu.wowarmory.com/wow-icons/_images/51x51/" + result.getIcon() + ".jpg"));
				
				// base stats
				if (result.getArmor() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Armor", result.getArmor());
					new CurrentItemStatisticPresenter(view).go(display.getStatsOne());
				}
				
				int count = 0;
				for (Statistic stat : result.getStatistics()) {
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
				
				//TODO resilience
				
				if (result.getMetaSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Meta socket", result.getMetaSockets());
					new CurrentItemStatisticPresenter(view).go(display.getStatsOne());
				}
				
				if (result.getRedSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Red socket", result.getRedSockets());
					new CurrentItemStatisticPresenter(view).go(display.getStatsOne());
				}
				
				if (result.getYellowSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Yellow socket", result.getYellowSockets());
					new CurrentItemStatisticPresenter(view).go(display.getStatsOne());
				}
				
				if (result.getBlueSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Blue socket", result.getBlueSockets());
					new CurrentItemStatisticPresenter(view).go(display.getStatsOne());
				}
				
				//TODO resistances
				
				for (String use : result.getUse()) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Use", use);
					new CurrentItemStatisticPresenter(view).go(display.getSpells());
				}
				
				for (String equip : result.getEquip()) {
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
