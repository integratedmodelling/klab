package org.integratedmodelling.klab.components.network.services;

import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.network.model.Network;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The general network resolver models generic networks using JUNG and gives access to all parameters
 * and indices that pertain to generic networks.
 * 
 * @author Ferd
 *
 */
public class GeneralNetworkResolver extends AbstractContextualizer implements IResolver<IConfiguration> {

	Network network;
	
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConfiguration resolve(IConfiguration ret, IContextualizationScope context) throws KlabException {
		// TODO Auto-generated method stub
		return null;
	}

}
