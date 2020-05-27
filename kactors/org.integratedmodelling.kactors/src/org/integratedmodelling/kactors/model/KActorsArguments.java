package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.KeyValuePair;
import org.integratedmodelling.kactors.kactors.ParameterList;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * Arguments with possible unnamed parameters, actually named
 * _p1, _p2...
 * 
 * @author Ferd
 *
 */
public class KActorsArguments extends Parameters<String> {

	public KActorsArguments(ParameterList parameters) {
		for (KeyValuePair pair : parameters.getPairs()) {
			if (pair.getName() == null) {
				putUnnamed(new KActorsValue(pair.getValue(), null));
			} else {
				put(pair.getName(), new KActorsValue(pair.getValue(), null));
			}
		}
	}
	
}
