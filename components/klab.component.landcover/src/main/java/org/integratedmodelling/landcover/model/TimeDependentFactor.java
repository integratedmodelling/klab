package org.integratedmodelling.landcover.model;

import java.util.function.Function;

import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.code.LocatedExpression;
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
	LocatedExpression expression;

	// used only during initialization to avoid messy code
	transient long timepoint = 0;

	public TimeDependentFactor() {
	}

	public TimeDependentFactor(double d) {
		this.constval = d;
	}
	
	public TimeDependentFactor(IKimExpression expression, IRuntimeScope overallScope) {
		this.expression = new LocatedExpression(expression, overallScope);
	}

	/**
	 * The value at the passed time.
	 * 
	 * @param time
	 * @return
	 */
	public double get(ITime time, IRuntimeScope scope) {
		
		if (constval != null) {
			return constval;
		} else if (state != null) {
			return Utils.asType(state.aggregate(time), Double.class);
		} else if (function != null) {
			return function.apply(time.getStart().getMilliseconds()
					+ (time.getEnd().getMilliseconds() - time.getStart().getMilliseconds()) / 2);
		} else if (expression != null) {
			return expression.eval(scope, null, Number.class).doubleValue();
		}
		return 0;
	}
	
	/**
	 * The value at the passed time. Only admits non-located expressions.
	 * 
	 * @param time
	 * @return
	 */
	public double get(ITime time, ILocator locator, IRuntimeScope scope) {
		if (expression != null) {
			return expression.eval(scope, locator, Number.class).doubleValue();
		}
		return get(time, scope);
	}

}