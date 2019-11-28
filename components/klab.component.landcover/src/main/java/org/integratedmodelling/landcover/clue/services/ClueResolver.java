package org.integratedmodelling.landcover.clue.services;

import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.landcover.clue.KlabCLUEParameters;

import nl.wur.iclue.model.CLUEModel;

public class ClueResolver implements IResolver<IState> {

	CLUEModel clue = null;
	
	@Override
	public Type getType() {
		return Type.CONCEPT;
	}

	@Override
	public IState resolve(IState ret, IContextualizationScope context) throws KlabException {
		/*
		 * first call: initialization to T0
		 */
		if (clue == null) {
			this.clue = new CLUEModel(new KlabCLUEParameters(context), context.getMonitor());
		}
		return null;
	}

}
