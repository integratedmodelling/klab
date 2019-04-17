package org.integratedmodelling.klab.components.network.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.INetwork;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;
import org.integratedmodelling.klab.utils.Triple;

import com.google.common.collect.Lists;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class Network implements INetwork {

	/*
	 * export functions use the adapter interface; we redirect to the network
	 * passing this name in REST calls.
	 */
	public static final String ADAPTER_ID = "NETWORK_ADAPTER";

	DirectedSparseMultigraph<IDirectObservation, IRelationship> network = new DirectedSparseMultigraph<>();
	IConfiguration configuration;

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

		this.configuration = configuration;

		/*
		 * Build network
		 */
		for (IObservation observation : configuration.getTargetObservations()) {
			IRuntimeContext context = ((Observation) observation).getRuntimeContext();
			for (IArtifact artifact : observation) {
				if (artifact instanceof IRelationship) {
					IDirectObservation source = context.getSourceSubject((IRelationship) artifact);
					IDirectObservation target = context.getTargetSubject((IRelationship) artifact);
					network.addEdge((IRelationship) artifact, Lists.newArrayList(source, target),
							((IRelationship) artifact).getObservable().is(Type.UNIDIRECTIONAL) ? EdgeType.DIRECTED
									: EdgeType.UNDIRECTED);
				}
			}
		}

		((Artifact) configuration).addPeer(this, INetwork.class);
	}

	public void export(File file, ExportFormat format) {

	}

	@Override
	public Collection<Triple<String, String, String>> getExportCapabilities(IObservation observation) {
		List<Triple<String, String, String>> ret = new ArrayList<>();
		// TODO others, more for code-compatible formats and possibly spatial networks
		ret.add(new Triple<>("gefx", "GEFX 1.2 network export (Gephi compatible)", "gefx"));
		return ret;
	}

}
