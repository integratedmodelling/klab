package org.integratedmodelling.klab.components.network.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaException;
import org.integratedmodelling.klab.components.network.model.Network;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;

public class CommunityInstantiator extends AbstractContextualizer implements IExpression, IInstantiator {

	
	private final static String infomap_url = "http://127.0.0.1:5000";
	
	private String networkArtifact = null;	
	
	private Network network;
	private IContextualizationScope scope;
	
	
	public CommunityInstantiator() {/* to instantiate as expression - do not remove (or use) */}
	
	
	public CommunityInstantiator(IParameters<String> parameters, IContextualizationScope scope) {
		
		this.scope = scope;
		this.networkArtifact = parameters.get("network", String.class);
		
	}
	
	
	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope scope)
			throws KlabException {
		
		IConcept networkConcept = semantics.getType();
		List<IObservation> networks = new ArrayList<>();
		
		if (networkArtifact == null) {
			networks.addAll(scope.getObservations(networkConcept));
		} else {
			IArtifact net = scope.getArtifact(networkArtifact); 
			if (net instanceof IObservationGroup) {
				for (IArtifact a : net) {
					networks.add((IObservation) a);
				}
			}
		}
		
		switch (networks.size()){
		case 1:
			if ( !(networks.get(0) instanceof IDirectObservation) ) {
				throw new IllegalArgumentException("Network observation is not a direct observation");
			}
			this.network = (Network) ((IDirectObservation) networks.get(0)).getOriginatingPattern();
			break;	
		case 0:
			throw new KlabException("Community instantiator finished with errors. There are no observations of " + networkConcept.getName() + " in the scope.");
		/* There cannot be multiple network objects of the same network in the scope. Disconnected networks are disconnected subgraphs of the same graph.*/
		default:
			throw new KlabException("Community instantiator finished with errors. There are multiple instances of " + networkConcept.getName() + " in the scope.");
		}
		
		/*
		 * Build the data package to be sent to the Infomap server.
		 * */
		
		KlabData.Object.Builder encodedNetwork = KlabData.Object.newBuilder().setName(networkArtifact);
		Map<String,String> edgeProperties;
		Map<String, Object> metadata;
		
		for (IRelationship edge : network.getNetwork().edgeSet()) {
			
			metadata = ((Map<String, Object>) edge.getMetadata());
			metadata.replaceAll((key, value) -> value != null ? value.toString(): null);
			
			
			edgeProperties = edge.getMetadata().entrySet()
													.stream()
													.collect(Collectors.toMap(
															e -> e.getKey(),
															e -> e.getValue() != null ? e.getValue().toString(): null
													));
			
			edgeProperties.put("source", edge.getSource().getName() );
			edgeProperties.put("target", edge.getTarget().getName() );
			
			encodedNetwork.addObjects(
						KlabData.Object.newBuilder()
						.putAllProperties(edgeProperties)
						.build()
					);
			
		}
		
		KlabData.Object infomapParams = KlabData.Object.newBuilder()
										.putProperties(networkArtifact, infomap_url)
										.build();
		
		KlabData infomapMessage = KlabData.newBuilder()
											.addObjects(encodedNetwork.build())
											.addObjects(infomapParams)
											.build();
		
		HttpResponse<String> response = null;
		File outputFile = new File(".protobuf-infomap");
		try {
			FileOutputStream output = new FileOutputStream(outputFile);
			infomapMessage.writeTo(output);
			output.close();
			response = infomapSendRequest(outputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	private HttpRequest infomapBuildRequest(File outputFile) {
		HttpRequest request = null;
		
		try {
			request = HttpRequest.newBuilder()
			        	.uri(URI.create(infomap_url))
			        	.header("Content-type", "application/protobuf")
			        	.POST(BodyPublishers.ofFile(Paths.get(outputFile.getAbsolutePath())))
			        	.build();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return request;
	}
	
	private HttpResponse<String> infomapSendRequest(File outputFile) {
		HttpRequest request = infomapBuildRequest(outputFile);
		HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException ie) {
            throw new ValhallaException(ie);
        }
		return response;
		
	}

	@Override
	public Object eval(IContextualizationScope scope, Object... additionalParameters) {
		return new CommunityInstantiator(Parameters.create(additionalParameters),scope);
	}
	
	@Override
	public Type getType() {
		return Type.OBJECT;
	}

}
