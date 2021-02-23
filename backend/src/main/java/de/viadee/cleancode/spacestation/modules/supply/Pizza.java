package de.viadee.cleancode.spacestation.modules.supply;

import java.util.ArrayList;
import java.util.List;

public class Pizza {

    private PizzaType pizzaType;

    private List<PizzaTopping> pizzaToppingList;

    public Pizza(PizzaType pizzaType, List<PizzaTopping> pizzaToppingList) {
        this.pizzaType = pizzaType;
        this.pizzaToppingList = new ArrayList<>();
    }
}
