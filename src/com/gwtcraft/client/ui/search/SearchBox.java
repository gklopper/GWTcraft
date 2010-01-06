package com.gwtcraft.client.ui.search;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.event.EventBus;
import com.gwtcraft.client.event.SearchCompleteEvent;
import com.gwtcraft.client.event.SearchStartEvent;
import com.gwtcraft.client.model.CharacterSearchResult;
import com.gwtcraft.client.service.ArmoryServiceAsync;

public class SearchBox extends Composite {

	private static SearchBoxUiBinder uiBinder = GWT
			.create(SearchBoxUiBinder.class);

	interface SearchBoxUiBinder extends UiBinder<Widget, SearchBox> {
	}

	@UiField
	Image search;
	@UiField
	TextBox searchText;
	
	private EventBus eventBus;
	private ArmoryServiceAsync armoryService;
	
	public SearchBox(EventBus eventBus, ArmoryServiceAsync armoryService) {
	  this.eventBus = eventBus;	
	  this.armoryService = armoryService;
	  initWidget(uiBinder.createAndBindUi(this));
	}


	@UiHandler("search")
	void onClick(ClickEvent e) {
		String searchString = searchText.getText();
		
		armoryService.search(searchString, new AsyncCallback<CharacterSearchResult>() {
			
			@Override
			public void onSuccess(CharacterSearchResult result) {
				eventBus.fireEvent(new SearchCompleteEvent(result));
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				
			}
		});
		
		eventBus.fireEvent(new SearchStartEvent());
		
	}

}
