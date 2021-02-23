package de.viadee.cleancode.spacestation.modules.supply;

public class Coffee {
	
	private CoffeeType type;
	
	private int sugar;
	
	public Coffee (CoffeeType type, int numberOfSugarCubes) {
		this.type = type;
		sugar = numberOfSugarCubes;
	}

	public CoffeeType getType() {
		return type;
	}

	public int getSugar() {
		return sugar;
	}

}
