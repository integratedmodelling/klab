package org.integratedmodelling.kdl.api;

import org.integratedmodelling.kim.api.IServiceCall;

public interface IKdlContextualizer {

	/**
	 * Service call implementing the contextualizer.
	 * 
	 * @return
	 */
	IServiceCall getServiceCall();

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
