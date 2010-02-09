package com.gwtcraft.client.places.current.item.spell;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.Spell;
import com.gwtcraft.client.places.Presenter;

public class CurrentItemSpellPresenter implements Presenter {

	private final Display display;
	private final Spell spell;

	public interface Display {
		Widget asWidget();
		HasText getType();
		HasText getDescription();
	}
	
	public CurrentItemSpellPresenter(Display view, Spell spell) {
		this.display = view;
		this.spell = spell;
	}
	
	private void bind() {
		display.getType().setText(spell.getType());
		display.getDescription().setText(spell.getDescription());
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}
}
