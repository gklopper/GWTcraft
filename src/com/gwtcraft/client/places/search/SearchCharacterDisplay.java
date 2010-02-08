package com.gwtcraft.client.places.search;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class SearchCharacterDisplay extends Composite implements SearchCharacterPresenter.Display {

	private static SearchCharacterUiBinder uiBinder = GWT
			.create(SearchCharacterUiBinder.class);

	interface SearchCharacterUiBinder extends UiBinder<Widget, SearchCharacterDisplay> {
	}
	
	interface Style extends CssResource {
		String selected();
	}

	@UiField
	HasText realm;
	
	@UiField
	HasText name;
	
	@UiField
	HasClickHandlers characterArea;
	
	@UiField
	Style style;

	public SearchCharacterDisplay(String name, String realm) {
		initWidget(uiBinder.createAndBindUi(this));
		this.name.setText(name);
		this.realm.setText(realm);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasText getNameField() {
		return name;
	}

	@Override
	public HasText getRealmField() {
		return realm;
	}

	@Override
	public HasClickHandlers getSelectedButton() {
		return characterArea;
	}
	
	//this handler is here as it is purely a view thing
	//it contains no business logic thus it is not in the
	//presenter
	@UiHandler("characterArea")
	public void onClick(ClickEvent event) {
		((Widget)characterArea).addStyleName(style.selected());
	}
}
