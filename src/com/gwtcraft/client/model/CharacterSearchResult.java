package com.gwtcraft.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CharacterSearchResult implements Serializable {
	
	List<ArmoryCharacter> characters = new ArrayList<ArmoryCharacter>();
	
	public void addCharacter(ArmoryCharacter character) {
		characters.add(character);
	}

	public List<ArmoryCharacter> getCharacters() {
		return characters;
	}
}
