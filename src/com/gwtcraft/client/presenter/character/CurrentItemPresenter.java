package com.gwtcraft.client.presenter.character;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.event.ItemSelectedEvent;
import com.gwtcraft.client.model.ItemDetail;
import com.gwtcraft.client.model.Statistic;
import com.gwtcraft.client.presenter.Presenter;
import com.gwtcraft.client.service.ArmoryServiceAsync;
import com.gwtcraft.client.view.character.CurrentItemStatisticDisplay;

public class CurrentItemPresenter implements Presenter {

	private final HandlerManager eventBus;
	private final Display display;
	private final ArmoryServiceAsync armoryService;
	private final String characterName;
	private final String realmName;
	private final boolean selectable;
	private final Integer itemId;
	

	public interface Display {
		Widget asWidget();
		HasValue<String> getIdField();
		HasValue<String> getSlotField();
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
	
	public CurrentItemPresenter(ArmoryServiceAsync armoryService, 
								HandlerManager eventBus, 
								Display view, 
								String characterName, 
								String realmName,
								Integer itemId,
								boolean selectable) {
		this.armoryService = armoryService;
		this.eventBus = eventBus;
		this.display = view;
		this.characterName = characterName;
		this.realmName = realmName;
		this.itemId = itemId;
		this.selectable = selectable;
	}

	private void bind() {
		
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
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Armor", item.getArmor());
					new CurrentItemStatisticPresenter(view).go(display.getStatsOne());
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
				
				//TODO resistances
				
				for (String use : item.getUse()) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Use", use);
					new CurrentItemStatisticPresenter(view).go(display.getSpells());
				}
				
				for (String equip : item.getEquip()) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Equip", equip);
					new CurrentItemStatisticPresenter(view).go(display.getSpells());
				}
				
				if (selectable) {
					display.getSelectButton().addClickHandler( new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							eventBus.fireEvent(new ItemSelectedEvent(characterName, realmName, item.getId()));
						}
					});
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
