package org.integratedmodelling.klab.components.time.services;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;

public class Time implements IExpression {

	/*
	 * start    [int/negative for BC, long (ms) or string for ISO date/time] 
	 * end      [same]
	 * year     [shorthand for beg-end of given year]
	 * step     [period, int (seconds) or long(ms)]
	 * focus    [generic]
	 * realtime [boolean]
	 * 
	 * @see org.integratedmodelling.klab.api.data.general.IExpression#eval(org.
	 * integratedmodelling.kim.api.IParameters,
	 * org.integratedmodelling.klab.api.runtime.IComputationContext)
	 */
	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		// TODO Auto-generated method stub
		return null;
	}
}
