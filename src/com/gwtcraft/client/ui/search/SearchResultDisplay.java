package com.gwtcraft.client.ui.search;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.event.Event;
import com.gwtcraft.client.event.EventHandler;
import com.gwtcraft.client.event.SearchCompleteEvent;
import com.gwtcraft.client.event.SearchStartEvent;
import com.gwtcraft.client.model.ArmoryCharacter;

public class SearchResultDisplay extends Composite implements EventHandler {
	
	private static SearchResultDisplayUiBinder uiBinder = GWT
			.create(SearchResultDisplayUiBinder.class);

	interface SearchResultDisplayUiBinder extends
			UiBinder<Widget, SearchResultDisplay> {
	}
	
	interface Style extends CssResource {
		String hide();
		String show();
		String loader();
	} 

	@UiField FlowPanel resultsWrapper;
	@UiField Style style;
	@UiField Image loaderImage;
	
	public SearchResultDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void handleEvent(Event event) {
		
		if (event instanceof SearchCompleteEvent) {
			loaderImage.setStyleName(style.hide());
			resultsWrapper.clear();
			List<ArmoryCharacter> characters = ((SearchCompleteEvent) event).getResult().getCharacters();
			for (ArmoryCharacter character : characters) {
				resultsWrapper.add(new SearchCharacter(character));
			}
		}
		
		if (event instanceof SearchStartEvent) {
			resultsWrapper.clear();
			loaderImage.setStyleName(style.show());
		}
	}
}
