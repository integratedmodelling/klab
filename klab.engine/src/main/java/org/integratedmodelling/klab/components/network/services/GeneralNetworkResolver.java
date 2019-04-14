package org.integratedmodelling.klab.components.network.services;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.components.network.model.Network;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The general network resolver models generic networks using JUNG and gives access to all parameters
 * and indices that pertain to generic networks.
 * 
 * @author Ferd
 *
 */
public class GeneralNetworkResolver implements IResolver<IConfiguration> {

	Network network;
	
	@Override
	public IGeometry getGeometry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConfiguration resolve(IConfiguration ret, IComputationContext context) throws KlabException {
		// TODO Auto-generated method stub
		return null;
	}

}
