package com.gwtcraft.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CharacterSearchResult implements IsSerializable {
	
	List<ArmoryCharacter> characters = new ArrayList<ArmoryCharacter>();
	
	public void addCharacter(ArmoryCharacter character) {
		characters.add(character);
	}

	public List<ArmoryCharacter> getCharacters() {
		return characters;
	}
}
