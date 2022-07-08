package org.integratedmodelling.geoprocessing.weather;

import java.util.function.Function;

import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;

@Deprecated
public interface IWeatherInterpolator {

	/**
	 * 
	 * @param target
	 * @param weatherStation
	 * @param artifactName
	 * @param timeOffset
	 * @param transformation
	 */
	void computeState(IState target, String artifactName, long timeOffset, IScale scale,
			Function<Double, Double> transformation);

}
