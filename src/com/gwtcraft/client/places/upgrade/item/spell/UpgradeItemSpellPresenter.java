package com.gwtcraft.client.places.upgrade.item.spell;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.Spell;
import com.gwtcraft.client.places.Presenter;

public class UpgradeItemSpellPresenter implements Presenter {

	private final Display display;
	private final Spell spell;
	private final boolean isUpgrade;

	public interface Display {
		Widget asWidget();
		HasText getType();
		HasText getDescription();
		void setUpgrade(boolean isUpgrade);
	}
	
	public UpgradeItemSpellPresenter(Display view, Spell spell, boolean isUpgrade) {
		this.display = view;
		this.spell = spell;
		this.isUpgrade = isUpgrade;
	}
	
	private void bind() {
		display.getType().setText(spell.getType());
		display.getDescription().setText(spell.getDescription());
		display.setUpgrade(isUpgrade);
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
}
