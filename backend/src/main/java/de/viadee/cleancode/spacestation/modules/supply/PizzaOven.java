package de.viadee.cleancode.spacestation.modules.supply;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * TODO hier sinnvoll komentieren
 */
@Component
public class PizzaOven {
	
	SalamiStorage salamiStorage = new SalamiStorage();

	/**
	 * @param type
	 * @return
	 */
	public Pizza makePizza(PizzaType type) {
		List<PizzaTopping> pizzaToppingList;
		switch (type) {
		case BIG:
			pizzaToppingList = Arrays.asList(PizzaTopping.BROCCOLI, PizzaTopping.BROCCOLI, PizzaTopping.BROCCOLI, PizzaTopping.HOLLANDAISE);
			break;
		case CHEESY_CRUST:
			pizzaToppingList = Arrays.asList(PizzaTopping.GOUDA, PizzaTopping.TOMATO, PizzaTopping.MUSHROOM, PizzaTopping.HOLLANDAISE);
			break;
		case CLASSIC_25:
			pizzaToppingList = makeSalamiPizza();
			break;
		case VEGAN:
			pizzaToppingList = Arrays.asList(PizzaTopping.GOUDA, PizzaTopping.TOMATO, PizzaTopping.MUSHROOM, PizzaTopping.BROCCOLI, PizzaTopping.HOLLANDAISE);
			break;
		default:
			pizzaToppingList = null;
			break;
		}
		// TODO Auto-generated method stub
		return new Pizza(type, pizzaToppingList);
	}

	private List<PizzaTopping> makeSalamiPizza() {
		List<PizzaTopping> pizzaToppingList;
		pizzaToppingList = Arrays.asList(PizzaTopping.SALAMI, PizzaTopping.TOMATO, PizzaTopping.HOLLANDAISE);
		salamiStorage.pull(5);
		return pizzaToppingList;
	}

	public SalamiStorage getSalamiStorage() {
		return salamiStorage;
	}
}
