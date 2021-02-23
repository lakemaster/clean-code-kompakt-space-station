package de.viadee.cleancode.spacestation.modules.power;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import de.viadee.cleancode.spacestation.modules.DailyWorkResult;
import de.viadee.cleancode.spacestation.modules.Module;

@Service
public class PowerModule implements Module {

	public boolean solarSailActive = false;

	public boolean powerPackActive = false;

	@Value("${power.powerPack.capacity.initial}")
	public int powerPackCapacity;

	@Value("${power.consumption.air}")
	Integer airConsumption;

	@Value("${power.consumption.power}")
	Integer powerConsumption;

	@Value("${power.weight}")
	Double weight;

	@Override
	public Double getWeight() {
		return weight;
	}

	@Override
	public int getPowerConsumption() {
		return powerConsumption;
	}

	@Override
	public Double getAirConsumption() {
		return Double.valueOf(airConsumption);
	}

	@Override
	public DailyWorkResult doDailyWork() {
		DailyWorkResult result = new DailyWorkResult();
		activateSolarSail();
		if (!solarSailActive) {
			result.setReturnCode("-1");
			result.setErrorMessage("Solar sail could not be activated!");
			return result;
		}
		rechargePowerPack();
		Properties powerPackProps = new Properties();
		try {
			powerPackProps.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			result.setReturnCode("-1");
			result.setErrorMessage("Power pack not found: " + e.getMessage());
			return result;
		}
		if (Integer.valueOf(powerPackCapacity)
				.equals(Integer.valueOf(powerPackProps.getProperty("power.powerPack.capacity.initial")))) {
			result.setReturnCode("-1");
			result.setErrorMessage("Power pack could not be recharged!");
			return result;
		}
		if (powerPackCapacity >= 2) {
			solarSailActive = false;
		} else {
			result.setReturnCode("-1");
			result.setErrorMessage("Solar sail could not be deactivated!");
			return result;
		}
		result.setReturnCode("0");
		result.setPowerPackActive(powerPackActive);
		result.setSolarSailActive(solarSailActive);
		result.setPowerPackCapacity(powerPackCapacity);
		return result;
	}

	private void activateSolarSail() {
		if (!solarSailActive) {
			powerPackActive = true;
			if (powerPackCapacity >= 2) {
				// Extend solair sail
				powerPackCapacity = powerPackCapacity - 2;
				solarSailActive = true;
			}
		}
		return;
	}

	protected void rechargePowerPack() {
		Properties powerPackProps = new Properties();
		try {
			powerPackProps.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			// abort recharging
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		int initialPowerPackCapacity = Integer.valueOf(powerPackProps.getProperty("power.powerPack.capacity.initial"));
		while (initialPowerPackCapacity < powerPackCapacity) {
			if (!solarSailActive) {
				if (powerPackCapacity >= 2) {
					// Extend solair sail
					powerPackCapacity = powerPackCapacity - 2;
					solarSailActive = true;
				}
			}
			try {
				wait(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			powerPackCapacity++;
		}
		return;
	}

	@Override
	public boolean isConnected() {
		return true;
	}

	@Override
	public void tvController(boolean onOrOff) {
		// do nothing
	}

	@Override
	public void adjustRoomTemperature(int temperature) {
		// do nothing
	}

}
