package com.gwtcraft.client.places.upgrade.item;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.ItemDetail;
import com.gwtcraft.client.model.Spell;
import com.gwtcraft.client.model.Statistic;
import com.gwtcraft.client.places.Presenter;
import com.gwtcraft.client.places.upgrade.spell.UpgradeItemSpellDisplay;
import com.gwtcraft.client.places.upgrade.spell.UpgradeItemSpellPresenter;
import com.gwtcraft.client.places.upgrade.statistic.UpgradeItemStatisticDisplay;
import com.gwtcraft.client.places.upgrade.statistic.UpgradeItemStatisticPresenter;
import com.gwtcraft.client.service.ArmoryServiceAsync;

public class UpgradeItemPresenter implements Presenter {

	private final Display display;
	private final ArmoryServiceAsync armoryService;
	private ItemDetail upgradeItem;
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
	}
	
	public UpgradeItemPresenter(ArmoryServiceAsync armoryService,
								Display view,
								ItemDetail currentItem,
								Integer upgradeItemId) {
		this.armoryService = armoryService;
		this.display = view;
		this.currentItem = currentItem;
		this.upgradeItem = new ItemDetail();
		upgradeItem.setId(upgradeItemId);
	}

	private void bind() {
		Integer itemId = upgradeItem.getId();
		
		armoryService.loadItem(itemId, new AsyncCallback<ItemDetail>() {
			
			@Override
			public void onSuccess(final ItemDetail result) {
				upgradeItem = result;
				final List<Statistic> upgrades = new ArrayList<Statistic>(upgradeItem.getStatistics());

				//work out upgrades and downgrades
				for (Statistic currentStat : currentItem.getStatistics()) {
					Statistic upgradeStat = getUpgradeStatistic(currentStat, upgrades);
					if (upgradeStat == null) {
						//we are losing this stat so it is a downgrade
						Statistic stat = new Statistic(currentStat.getName(), currentStat.getValue() * -1);
						upgrades.add(stat);
					} else {
						int difference = upgradeStat.getValue() - currentStat.getValue();
						Statistic stat = new Statistic(currentStat.getName(), difference);
						int index = upgrades.indexOf(upgradeStat);
						
						if (difference == 0) {
							//do not display the stat if it is unchanged
						    upgrades.remove(index);	
						} else {
							upgrades.set(index, stat);
						}
					}
				}
				
				display.getName().setText(upgradeItem.getName());
				
				if (upgradeItem.getCreatureName() == null) {
					display.getSource().setText(upgradeItem.getSource());
				} else {
					display.getCreature().setText(upgradeItem.getCreatureName() + " - ");
					display.getArea().setText(upgradeItem.getAreaName());
				}
				
				display.getIconWrapper().clear();
				
				//TODO check T&Cs for using these images directly
				display.getIconWrapper().add(new Image("http://eu.wowarmory.com/wow-icons/_images/51x51/" + upgradeItem.getIcon() + ".jpg"));
								
				int count = 0;
				for (Statistic stat : upgrades) {
					UpgradeItemStatisticDisplay view = new UpgradeItemStatisticDisplay();
					UpgradeItemStatisticPresenter presenter = new UpgradeItemStatisticPresenter(view, stat);
					if (count++ < 4) {
						presenter.go(display.getStatsOne());
					} else {
						presenter.go(display.getStatsTwo());
					}
				}
				
				for (Spell spell : currentItem.getSpells()) {
					UpgradeItemSpellDisplay view = new UpgradeItemSpellDisplay();
					new UpgradeItemSpellPresenter(view, spell, false).go(display.getSpells());
				}
				
				for (Spell spell : upgradeItem.getSpells()) {
					UpgradeItemSpellDisplay view = new UpgradeItemSpellDisplay();
					new UpgradeItemSpellPresenter(view, spell, true).go(display.getSpells());
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				//TODO option to retry or something
				display.getStatsOne().add(new Label(caught.getMessage()));
			}
		});
		
	}

	private Statistic getUpgradeStatistic(Statistic currentStat, List<Statistic> upgradeStatistics) {
		String statName = currentStat.getName();
		for (Statistic stat : upgradeStatistics) {
			if (stat.getName().equals(statName)) {
				return stat;
			}
		}
		return null;
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
}
