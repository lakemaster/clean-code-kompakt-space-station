package de.viadee.cleancode.spacestation.modules.power;

import org.springframework.stereotype.Component;

import de.viadee.cleancode.spacestation.modules.DailyWorkResult;

@Component
public class PowerModuleWithEngine {

	private PowerModule powerModule;

	private boolean engineActivated = false;

	public DailyWorkResult doDailyWork() {
		DailyWorkResult result = this.powerModule.doDailyWork();
		if (result.getReturnCode().equals("0")) {
			this.doDailyEngineWork();
		}
		return result;
	}

	private void doDailyEngineWork() {
		this.startEngine();
		// ...
		this.stopEngine();
	}

	private void startEngine() {
		if (!this.engineActivated && this.powerModule.powerPackCapacity >= 1) {
			this.engineActivated = true;
			this.powerModule.powerPackCapacity--;
		}
	}

	private void stopEngine() {
		this.engineActivated = false;
	}

	public DailyWorkResult navigate(Destination destination) {
		int powerConsumption = this.calculatePowerConsumption(destination);
		return this.fly(destination, powerConsumption);
	}

	// Kein schöner Name
	private DailyWorkResult fly(Destination destination, int powerConsumption) {
		DailyWorkResult result = new DailyWorkResult();
		if (this.powerModule.powerPackCapacity < powerConsumption) {
			result.setReturnCode("-1");
			result.setErrorMessage("Destination cannot be reached - recharge power pack first");
		} else {
			this.powerModule.powerPackCapacity = this.powerModule.powerPackCapacity - powerConsumption;
			result.setReturnCode("0");
			result.setErrorMessage("Destination " + destination.toString() + " successfully reached.");
		}
		return result;
	}

	private int calculatePowerConsumption(Destination destination) {
		int powerConsumptionBase = 40;
		int powerConsumption = 0;
		switch (destination) {
			case RED_PLANET:
			case ORANGE_PLANET:
				powerConsumption = powerConsumptionBase * 2;
				break;
			case GREEN_PLANET:
				powerConsumption = powerConsumptionBase * 4;
				break;
			case YELLOW_PLANET:
				powerConsumption = powerConsumptionBase / 2;
				break;
			case BLUE_PLANET:
				powerConsumption = powerConsumptionBase * 8;
				break;
			case WHITE_PLANET:
				powerConsumption = powerConsumptionBase / 4;
				break;
			case MOON_DARK_SIDE:
				powerConsumption = 0;
				break;
			case STATIONARY_ORBIT:
			default:
				powerConsumption = powerConsumptionBase;
		}
		return powerConsumption;
	}
}
