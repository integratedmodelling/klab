package org.integratedmodelling.landcover.model;

import java.util.function.Function;

import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.utils.Utils;

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
	Double constval;

	// used only during initialization to avoid messy code
	transient long timepoint = 0;

	public TimeDependentFactor() {
	}

	public TimeDependentFactor(double d) {
		this.constval = d;
	}

	/**
	 * The value at the passed time.
	 * 
	 * @param time
	 * @return
	 */
	public double get(ITime time) {
		
		if (constval != null) {
			return constval;
		} else if (state != null) {
			return Utils.asType(state.aggregate(time), Double.class);
		} else if (function != null) {
			return function.apply(time.getStart().getMilliseconds()
					+ (time.getEnd().getMilliseconds() - time.getStart().getMilliseconds()) / 2);
		}
		return 0;
	}

}