package org.integratedmodelling.landcover.clue;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

import nl.wur.iclue.parameter.Parameters;

/**
 * CLUE parameterization built from k.LAB contextualization scope.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabCLUEParameters extends Parameters {

	public KlabCLUEParameters(IContextualizationScope context) {
		buildLanduses(context.getTargetSemantics().getType());
	}

	private void buildLanduses(IConcept type) {
		// TODO Auto-generated method stub
	}

}
