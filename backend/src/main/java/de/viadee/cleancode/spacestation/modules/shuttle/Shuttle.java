package de.viadee.cleancode.spacestation.modules.shuttle;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import de.viadee.cleancode.spacestation.modules.DailyWorkResult;
import de.viadee.cleancode.spacestation.modules.Module;
import de.viadee.cleancode.spacestation.modules.power.Destination;
import de.viadee.cleancode.spacestation.modules.power.PowerModule;
import de.viadee.cleancode.spacestation.modules.supply.Coffee;
import de.viadee.cleancode.spacestation.modules.supply.CoffeeType;
import de.viadee.cleancode.spacestation.modules.supply.PizzaType;
import de.viadee.cleancode.spacestation.modules.supply.SupplyModule;
import org.springframework.stereotype.Component;

@Component
public class Shuttle implements Module {

	boolean powerPackActive = false;
	
	@Autowired
	private PowerModule powerModule;
	
	@Autowired
	private SupplyModule supplyModule;

	@Value("${shuttle.powerPack.capacity.initial}")
	int powerPackCapacity;

	@Value("${shuttle.consumption.air}")
	Integer airConsumption;

	@Value("${shuttle.consumption.power}")
	Integer powerConsumption;

	@Value("${shuttle.weight}")
	Double weight;

	@Value("${shuttle.isConnected}")
	boolean isConnected;

	private ShuttleType type;

	private static final SecureRandom random = new SecureRandom();


	@Override
	public Double getWeight() {
		return weight;
	}

	@Override
	public int getPowerConsumption() {
		switch (type) {
		case SOJUS:
			powerConsumption = 8;
			break;
		case ORION:
			powerConsumption = 6;
			break;
		case SPACE_X:
			powerConsumption = 2;
			break;
		default:
			throw new RuntimeException("Unknown type of shuttle!");
		}
		return powerConsumption;
	}
	
	public int getNumberOfAstronauts() {
		int numberOfAstronauts = 0;
		switch (type) {
		case SOJUS:
			numberOfAstronauts= 4;
			break;
		case ORION:
			numberOfAstronauts= 6;
			break;
		case SPACE_X:
			numberOfAstronauts= 8;
			break;
		default:
			numberOfAstronauts= 2;
		}
		return numberOfAstronauts;
	}

	@Override
	public Double getAirConsumption() {
		return Double.valueOf(airConsumption);
	}

	@Override
	public boolean isConnected() {
		return isConnected;
	}

	@Override
	public DailyWorkResult doDailyWork() {
		DailyWorkResult result = new DailyWorkResult();
		result = getSupplies();
		if (result.getReturnCode().equals("-1")) {
			return result;
		}
		recharge();
		return navigate(randomEnum(Destination.class));
	}
	
	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    };
	
	
	public void recharge() {
		if (isConnected) {
			if (powerModule.solarSailActive) {
				powerPackCapacity = getPowerPackCapacityInitial();
			} else {
				int powerModulePowerCapacity = powerModule.powerPackActive ? powerModule.powerPackCapacity - powerModule.getPowerConsumption() : 0;
				while (powerModulePowerCapacity > 0  && powerPackCapacity < getPowerPackCapacityInitial() ) {
					powerPackCapacity++;
					powerModule.powerPackCapacity--;
				}
			}
		}
	}
	
	private int getPowerPackCapacityInitial() {
		Properties powerPackProps = new Properties();
		try {
			powerPackProps.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			return 0;
		}
		return (int) powerPackProps.get("shuttle.powerPack.capacity.initial");
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
		if (powerConsumption != 0 && !(powerPackCapacity % powerConsumption <= 0)) {
			result.setReturnCode("-1");
			result.setErrorMessage("Destination cannot be reached - recharge power pack first");
		} else {
			powerPackCapacity = powerPackCapacity - powerConsumption;
			result.setReturnCode("0");
			result.setErrorMessage("Destination " + destination.toString() + " successfully reached.");
		}
		return result;
	}
	
	private DailyWorkResult getSupplies() {
		DailyWorkResult workResult = new DailyWorkResult();
		for (int i = 0; i < getNumberOfAstronauts(); i++) {
			try {
				Coffee coffee = supplyModule.makeCoffee(CoffeeType.BLACK, false);
				if (coffee == null) {
					workResult.setReturnCode("-1");
					workResult.setErrorMessage("Coffee could not be brewed. Work canceled.");
				} else {
					workResult.setReturnCode("0");
				}
			} catch (Exception e) {
				workResult.setReturnCode("-1");
				workResult.setErrorMessage("Coffee could not be brewed. Work canceled.");
			}
			
			if (!supplyModule.hasSalamiSupply()) supplyModule.getPizzaOven().refillSalamiStorage(100);
			
			//TODO Pizzamenge auf Basis der Astronauten einstellen, erstmal jede Sorte machen
			supplyModule.getPizzaOven().makePizza(PizzaType.CHEESY_CRUST);
			supplyModule.getPizzaOven().makePizza(PizzaType.BIG);
			supplyModule.getPizzaOven().makePizza(PizzaType.CLASSIC_25);
			supplyModule.getPizzaOven().makePizza(PizzaType.VEGAN);

			return workResult;
		}
		workResult.setReturnCode("1");
		return workResult;
	}

	public void setType(ShuttleType type) {
		this.type = type;
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
