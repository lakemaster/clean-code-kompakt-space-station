package de.viadee.cleancode.spacestation.modules.power;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.viadee.cleancode.spacestation.modules.DailyWorkResult;
import de.viadee.cleancode.spacestation.modules.Module;

@Service
public class PowerModule
		implements Module {

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
		return this.weight;
	}

	@Override
	public int getPowerConsumption() {
		return this.powerConsumption;
	}

	@Override
	public Double getAirConsumption() {
		return Double.valueOf(this.airConsumption);
	}

	@Override
	public DailyWorkResult doDailyWork() {
		DailyWorkResult result = new DailyWorkResult();
		this.activateSolarSail();
		if (!this.solarSailActive) {
			return this.createErrorResult("Solar sail could not be activated!");
		}
		this.rechargePowerPack();
		Properties powerPackProps = new Properties();
		try {
			powerPackProps.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			return this.createErrorResult("Power pack not found: " + e.getMessage());
		}
		if (Integer.valueOf(this.powerPackCapacity)
				.equals(Integer.valueOf(powerPackProps.getProperty("power.powerPack.capacity.initial")))) {
			return this.createErrorResult("Power pack could not be recharged!");
		}
		if (this.powerPackCapacity >= 2) {
			this.solarSailActive = false;
		} else {
			return this.createErrorResult("Solar sail could not be deactivated!");
		}
		result.setReturnCode("0");
		result.setPowerPackActive(this.powerPackActive);
		result.setSolarSailActive(this.solarSailActive);
		result.setPowerPackCapacity(this.powerPackCapacity);
		return result;
	}

	private DailyWorkResult createErrorResult(String errorText) {
		DailyWorkResult result = new DailyWorkResult();
		result.setReturnCode("-1");
		result.setErrorMessage(errorText);
		return result;
	}

	private void activateSolarSail() {
		if (!this.solarSailActive) {
			this.powerPackActive = true;
			if (this.powerPackCapacity >= 2) {
				// Extend solair sail
				this.powerPackCapacity = this.powerPackCapacity - 2;
				this.solarSailActive = true;
			}
		}
	}

	protected void rechargePowerPack() {
		Properties powerPackProps = new Properties();
		try {
			powerPackProps.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			// abort recharging
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
		}
		int initialPowerPackCapacity = Integer.valueOf(powerPackProps.getProperty("power.powerPack.capacity.initial"));
		while (initialPowerPackCapacity < this.powerPackCapacity) {
			this.activateSolarSail();
		}
		try {
			this.wait(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.powerPackCapacity++;
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
