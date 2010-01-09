package com.gwtcraft.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.event.CharacterSelectedEvent;

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
