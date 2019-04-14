package org.integratedmodelling.klab.components.network.model;

import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.INetwork;
import org.integratedmodelling.klab.api.observations.IRelationship;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;

public class Network implements INetwork {

	DirectedSparseMultigraph<IDirectObservation, IRelationship> network = new DirectedSparseMultigraph<>();

	/**
	 * True if the passed configuration is a network, based on the presence of
	 * relationships.
	 * 
	 * @param configuration
	 * @return
	 */
	public static boolean isNetwork(IConfiguration configuration) {
		return false;
	}

	public Network(IConfiguration configuration) {

	}

}
