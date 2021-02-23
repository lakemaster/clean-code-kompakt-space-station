package de.viadee.cleancode.spacestation.modules.habitation;

import de.viadee.cleancode.spacestation.modules.DailyWorkResult;
import de.viadee.cleancode.spacestation.modules.Module;
import de.viadee.cleancode.spacestation.modules.supply.Coffee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class HabitationModule implements Module {

	@Value("${habitation.consumption.air}")
	Double airConsumption;

	@Value("${habitation.consumption.power}")
	Integer powerConsumption;

	@Value("${habitation.weight}")
	Double weight;

	@Value("${habitation.isConnected}")
	boolean isConnected;

	@Value("${habitation.isTvOn}")
	boolean isTvOn;
	
	/**
	 * Room temperature in Kelvin, adjustable
	 * 
	 * TODO show in module UI
	 */
	@Value("${habitation.temperature}")
	int temperature = 293;

	private final EspressoMachine espressoMachine = new EspressoMachine();

	private final CleaningRobot cleaningRobot = new CleaningRobot();

	@Override
	public Double getWeight() {
		return weight;
	}

	@Override
	public int getPowerConsumption() {
		return 10;
	}

	@Override
	public Double getAirConsumption() {
		return Double.valueOf(airConsumption);
	}

	@Override
	public DailyWorkResult doDailyWork() {
		// Work. Eat. Sleep. Repeat.
		int numberOfAstronauts = new Random().nextInt(5);
		DailyWorkResult workResult = new DailyWorkResult();
		for (int i = 0; i < numberOfAstronauts; i++) {
			Coffee morningCoffee = makeEspresso(false);
			if (morningCoffee == null) {
				workResult.setReturnCode("-1");
				workResult.setErrorMessage("Morning coffee could not be brewed. Work canceled.");
				return workResult;
			}
		}
		workResult.setErrorMessage("Morning coffee delivered to " + numberOfAstronauts + "astronauts!");
		adjustRoomTemperature(300);
		tvController(false);
		workResult = cleanModule(numberOfAstronauts);
		return workResult;
	}

	@Override
	public void tvController(boolean onOrOff) {
		isTvOn = onOrOff;
		if(isTvOn){
			powerConsumption++;
		}
	}

	@Override
	public void adjustRoomTemperature(int temperature) {
		this.temperature = temperature;
		powerConsumption = powerConsumption + ((temperature-this.temperature)/100);
	}

	public Coffee makeEspresso(boolean withSugar) {
		powerConsumption++;
		return espressoMachine.makeEspresso(withSugar);
	}

	public DailyWorkResult cleanModule(int numberOfAstronauts) {
		if (cleaningRobot != null) {
			powerConsumption = powerConsumption + cleaningRobot.clean(20.5, numberOfAstronauts).intValue();
			DailyWorkResult result = new DailyWorkResult();
			result.setReturnCode("1");
			result.setErrorMessage("Module successfully cleaned.");
			result.setAirconsumption(airConsumption);
			result.setPower(getPowerConsumption());
			result.setWeight(weight);
			return result;
		} else {
			return new DailyWorkResult("-1", "Cleaning unit not present.");
		}

	}

	@Override
	public boolean isConnected() {
		return isConnected;
	}

}
