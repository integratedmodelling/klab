package org.integratedmodelling.landcover.clue.services;

import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;

public class ClueResolver implements IResolver<IState> {

	@Override
	public Type getType() {
		return Type.CONCEPT;
	}

	@Override
	public IState resolve(IState ret, IContextualizationScope context) throws KlabException {
		// TODO Auto-generated method stub
		return null;
	}

}
