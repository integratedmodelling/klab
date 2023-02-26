package org.integratedmodelling.klab.api.lang.kdl;

import org.integratedmodelling.klab.api.lang.KServiceCall;

public interface KKdlContextualizer {

	/**
	 * Service call implementing the contextualizer.
	 * 
	 * @return
	 */
	KServiceCall getServiceCall();

	/**
	 * Mediation target, if any, corresponding to mediationTarget >>
	 * contextualizer()
	 * 
	 * @return
	 */
	String getMediationTarget();

	/**
	 * Destination variable for intermediate local values, if any, corresponding to
	 * variable <- contextualizer()
	 * 
	 * @return
	 */
	String getVariable();

	/**
	 * Contextualization target, if any, corresponding to contextualizer() >> target
	 * 
	 * @return
	 */
	String getTarget();
}
