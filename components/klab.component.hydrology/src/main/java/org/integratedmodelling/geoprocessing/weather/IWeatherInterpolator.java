package org.integratedmodelling.geoprocessing.weather;

import java.util.function.Function;

import org.integratedmodelling.klab.api.observations.IState;

public interface IWeatherInterpolator {

	/**
	 * 
	 * @param target
	 * @param weatherStation
	 * @param artifactName
	 * @param timeOffset
	 * @param transformation
	 */
	void computeState(IState target, String artifactName, long timeOffset, Function<Double, Double> transformation);

}
