package org.integratedmodelling.ecology.biomass.lpjguess;

import java.util.Random;

public class TestWithState {

	float value = 0;

	public float init() {
		Random random = new Random();

		value = random.nextFloat() * 100 - 50;

		return value;
	}

	public float run() {
		value = value + 10;

		return value;
	}

}
