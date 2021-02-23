package de.viadee.cleancode.spacestation.modules.supply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.viadee.cleancode.spacestation.modules.DailyWorkResult;
import de.viadee.cleancode.spacestation.modules.Module;

@Component
public class SupplyModule implements Module {

    @Value("${supply.consumption.air}")
    private int airconsump;

    @Value("${supply.consumption.power}")
    private int consumptionpower;

    @Value("${supply.isConnected}")
    boolean isConnected;

    @Value("${supply.temperature}")
    int temperature = 293;

    @Autowired
    CoffeeMaker coffeeMaker;
    
    @Autowired
    private PizzaOven pizzaOven;

    @Override
    public Double getWeight() {
        return null;
    }

    @Override
    public int getPowerConsumption() {
        return 0;
    }

    @Override
    public Double getAirConsumption() {
        return null;
    }

    @Override
    public DailyWorkResult doDailyWork() {
        DailyWorkResult result = new DailyWorkResult();
        if (coffeeMaker.getGroundCoffee() < 10) {
            result.setErrorMessage("Emergency!! Coffee is almost out!");
            result.setReturnCode("-1");
        }
        result.setWeight(this.weight);
        result.setPower(this.getPowerConsumption());
        result.setAirconsumption(this.getAirConsumption());
        result.setReturnCode("0");
        return result;
    }

    @Override
    public void tvController(boolean onOrOff) {
        // do nothing
    }

    @Override
    public void adjustRoomTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Value("${supply.weight}")
    private Double weight;

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    public Coffee makeCoffee(CoffeeType type, boolean withSugar) {
        consumptionpower++;
        return coffeeMaker.makeCoffee(type, withSugar);
    }

    public PizzaOven getPizzaOven() {
        return pizzaOven;
    }

	public boolean hasSalamiSupply() {
		return pizzaOven.salamiStorage > 0;
	}
}
