package org.integratedmodelling.klab.components.network.model;

import java.io.File;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.INetwork;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;

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
		boolean ret = true;
		for (IObservation obs : configuration.getTargetObservations()) {
			if (obs.getObservable().is(Type.RELATIONSHIP)) {
				ret = false;
				break;
			}
		}
		return ret;
	}

	public Network(IConfiguration configuration) {
		((Artifact)configuration).addPeer(this, INetwork.class);
	}
	
	public void export(File file, ExportFormat format) {
		
	}

}
