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
		HasWidgets getBaseStats();
		HasWidgets getOtherStats();
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
					new CurrentItemStatisticPresenter(view).go(display.getBaseStats());
				}
				
				if (result.getStamina() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Stamina", result.getStamina());
					new CurrentItemStatisticPresenter(view).go(display.getBaseStats());
				}
				
				if (result.getAgility() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Agility", result.getAgility());
					new CurrentItemStatisticPresenter(view).go(display.getBaseStats());
				}
				
				if (result.getIntellect() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Intellect", result.getIntellect());
					new CurrentItemStatisticPresenter(view).go(display.getBaseStats());
				}
				
				if (result.getSpirit() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Spirit", result.getSpirit());
					new CurrentItemStatisticPresenter(view).go(display.getBaseStats());
				}
				
				//specific stats
				if (result.getAttackPower() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Attack Power", result.getAttackPower());
					new CurrentItemStatisticPresenter(view).go(display.getOtherStats());
				}
				
				if (result.getCritRating() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Crit Rating", result.getCritRating());
					new CurrentItemStatisticPresenter(view).go(display.getOtherStats());
				}
				
				if (result.getArmorPenetration() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Armor Penetration", result.getArmorPenetration());
					new CurrentItemStatisticPresenter(view).go(display.getOtherStats());
				}
				
				if (result.getExpertiseRating() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Expertise", result.getExpertiseRating());
					new CurrentItemStatisticPresenter(view).go(display.getOtherStats());
				}
				
				//TODO hit
				
				if (result.getSpellPower() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Spell power", result.getSpellPower());
					new CurrentItemStatisticPresenter(view).go(display.getOtherStats());
				}
				
				//TODO healing ???
				
				//TODO spell penetration
				
				//TODO haste
				
				//TODO MP5
				
				if (result.getDodgeRating() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Dodge", result.getDodgeRating());
					new CurrentItemStatisticPresenter(view).go(display.getOtherStats());
				}
				
				if (result.getParryRating() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Parry", result.getParryRating());
					new CurrentItemStatisticPresenter(view).go(display.getOtherStats());
				}
				
				if (result.getStrength() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Strength", result.getStrength());
					new CurrentItemStatisticPresenter(view).go(display.getOtherStats());
				}
				
				//TODO block
				//TODO resilience
				
				if (result.getMetaSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Meta socket", result.getMetaSockets());
					new CurrentItemStatisticPresenter(view).go(display.getBaseStats());
				}
				
				if (result.getRedSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Red socket", result.getRedSockets());
					new CurrentItemStatisticPresenter(view).go(display.getBaseStats());
				}
				
				if (result.getYellowSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Yellow socket", result.getYellowSockets());
					new CurrentItemStatisticPresenter(view).go(display.getBaseStats());
				}
				
				if (result.getBlueSockets() > 0) {
					CurrentItemStatisticDisplay view = new CurrentItemStatisticDisplay("Blue socket", result.getBlueSockets());
					new CurrentItemStatisticPresenter(view).go(display.getBaseStats());
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
				display.getBaseStats().add(new Label(caught.getMessage()));
			}
		});
		
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
}
