package de.viadee.cleancode.spacestation.modules.power;

import de.viadee.cleancode.spacestation.modules.DailyWorkResult;
import org.springframework.stereotype.Component;

@Component
public class PowerModuleWithEngine extends PowerModule {

	private boolean engineActivated = false;

	public DailyWorkResult doDailyWork() {
		DailyWorkResult result = super.doDailyWork();
		if (result.getReturnCode().equals("0")) {
			startEngine();
			// ...
			stopEnginge();
		}
		return result;
	}

	private void startEngine() {
		if (engineActivated && powerPackCapacity >= 1) {
			engineActivated = true;
			powerPackCapacity--;
		}
		return;
	}

	private void stopEnginge() {
		engineActivated = false;
	}

	public DailyWorkResult navigate(Destination destination) {
		int powerConsumptionBase = 40;
		int powerConsumption = 0;
		switch (destination) {
		case RED_PLANET:
			powerConsumption = powerConsumptionBase << 1;
			break;
		case ORANGE_PLANET:
			powerConsumption = powerConsumptionBase << 1;
			break;
		case GREEN_PLANET:
			powerConsumption = powerConsumptionBase << 2;
			break;
		case YELLOW_PLANET:
			powerConsumption = powerConsumptionBase >> 1;
			break;
		case BLUE_PLANET:
			powerConsumption = powerConsumptionBase << 3;
			break;
		case WHITE_PLANET:
			powerConsumption = powerConsumptionBase >> 2;
			break;
		case MOON_DARK_SIDE:
			powerConsumption = powerConsumptionBase % powerConsumptionBase;
			break;
		case STATIONARY_ORBIT:
		default:
			powerConsumption = powerConsumptionBase;
		}
		DailyWorkResult result = new DailyWorkResult();
		if (powerPackCapacity % powerConsumption > 0) {
			result.setReturnCode("-1");
			result.setErrorMessage("Destination cannot be reached - recharge power pack first");
		} else {
			powerPackCapacity = powerPackCapacity - powerConsumption;
			result.setReturnCode("0");
			result.setErrorMessage("Destination " + destination.toString() + " successfully reached.");
		}
		return result;
	}
}
