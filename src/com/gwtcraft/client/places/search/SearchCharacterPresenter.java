package com.gwtcraft.client.places.search;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.event.CharacterSelectedEvent;
import com.gwtcraft.client.places.Presenter;

public class SearchCharacterPresenter implements Presenter {

	private final HandlerManager eventBus;
	private final Display display;

	public interface Display {
		HasClickHandlers getSelectedButton();
		Widget asWidget();
		HasText getNameField();
		HasText getRealmField();
	}
	
	public SearchCharacterPresenter(HandlerManager eventBus, Display view) {
		this.eventBus = eventBus;
		this.display = view;
	}
	
	private void bind() {
		display.getSelectedButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String characterName = display.getNameField().getText();
				String characterRealm = display.getRealmField().getText();
				CharacterSelectedEvent characterSelectedEvent = new CharacterSelectedEvent(characterName, characterRealm);
				eventBus.fireEvent(characterSelectedEvent);
			}
		});		
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.add(display.asWidget());
	}

}
