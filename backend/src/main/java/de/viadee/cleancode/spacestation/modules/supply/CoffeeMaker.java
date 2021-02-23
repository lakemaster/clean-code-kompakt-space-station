package de.viadee.cleancode.spacestation.modules.supply;

import de.viadee.cleancode.spacestation.modules.supply.Coffee;
import de.viadee.cleancode.spacestation.modules.supply.CoffeeType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CoffeeMaker {

	@Value("4.53")
	Double groundCoffee;

	public Coffee makeCoffee(CoffeeType type, boolean withSugar) {
		if (this.groundCoffee > 0.01){
			this.groundCoffee = this.groundCoffee - 0.1;
			return new Coffee(type, withSugar? 1:0 );
		}
		throw new RuntimeException("Coffee is out!");
	}

	public Double getGroundCoffee() {
		return groundCoffee;
	}

	public void setGroundCoffee(Double groundCoffee) {
		this.groundCoffee = groundCoffee;
	}
}
