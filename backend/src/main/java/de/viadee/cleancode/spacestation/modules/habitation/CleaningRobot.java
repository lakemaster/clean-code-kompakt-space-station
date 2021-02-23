package de.viadee.cleancode.spacestation.modules.habitation;

import java.util.Random;

public class CleaningRobot {
	
	public Double clean(double roomSize, int dirtLevel) {
		Random random = new Random();
		return (roomSize * 3 + random.nextInt(4)) * dirtLevel;
	}

}
