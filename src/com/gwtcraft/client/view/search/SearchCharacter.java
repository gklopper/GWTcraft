package com.gwtcraft.client.view.search;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtcraft.client.model.ArmoryCharacter;

public class SearchCharacter extends Composite {

	private static SearchCharacterUiBinder uiBinder = GWT
			.create(SearchCharacterUiBinder.class);

	interface SearchCharacterUiBinder extends UiBinder<Widget, SearchCharacter> {
	}

	@UiField
	Label realm;
	
	@UiField
	Label name;

	public SearchCharacter(ArmoryCharacter character) {
		initWidget(uiBinder.createAndBindUi(this));
		name.setText(character.getName());
		realm.setText(character.getRealm());
	}
}
