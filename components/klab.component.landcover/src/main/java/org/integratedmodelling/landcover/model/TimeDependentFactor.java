package org.integratedmodelling.landcover.model;

import java.util.Map;
import java.util.function.Function;

import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;

/**
 * A (potentially) time-dependent quantity that may be initialized as a
 * constant, a (static or dynamic) state, or as an interpolating function based
 * on supplied <time,value> pairs.
 * 
 * @author Ferd
 *
 */
public class TimeDependentFactor {

	IState state;
	boolean isAbsolute;
	Function<Long, Double> function;
	double constval;
	
	// used only during initialization to avoid messy code
	transient long timepoint = 0;

	/**
	 * The value at the passed time.
	 * 
	 * @param time
	 * @return
	 */
	public double get(ITime time) {
		return 0;
	}

	public static <T> Map<T, TimeDependentFactor> parse(Map<?, ?> map) {
		return null;
	}

	public static <T> Map<T, TimeDependentFactor> parse(IKimTable table) {
		return null;
	}


}