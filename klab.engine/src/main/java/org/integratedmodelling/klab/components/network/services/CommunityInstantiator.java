package org.integratedmodelling.klab.components.network.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Optional;
import java.util.stream.Collectors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaException;
import org.integratedmodelling.klab.components.network.model.Network;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;

public class CommunityInstantiator extends AbstractContextualizer implements IExpression, IInstantiator {

	
	private final static String infomap_url = "http://127.0.0.1:5000";
	
	private String networkArtifact = null;	
	
	private Network network;
	private IContextualizationScope scope;
	
	String name = null;
	Map<IDirectObservation, String> communityMap;
	
	
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
			 
			// All configurations from the context are recovered, thus this might cause trouble if there are multiple. 
			networks = new ArrayList<>(scope.getContextObservation().getChildren(IConfiguration.class));
			
		} else {
			
			
			// Adding only the configuration with the name of the passed network. This works as long as the semantics are attached to a single network vs. multiple disconnected ones.
			networks.add(scope.getContextObservation().getChildren(IConfiguration.class).stream().filter(c->c.getName()==networkArtifact).iterator().next());
			
			
//			IArtifact net = scope.getArtifact(networkArtifact); 
//			
//			if (net instanceof IObservationGroup) {
//				for (IArtifact a : net) {
//					networks.add((IObservation) a);
//				}
//			} else {
//				networks.add((IObservation) net);
//			}
		}
		

		switch (networks.size()){
		case 1:
//			if ( !(networks.get(0) instanceof IDirectObservation) ) {
//			throw new IllegalArgumentException("Network observation is not a direct observation");
//		}
			
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
		
		KlabData.Object.Builder encodedNetwork = KlabData.Object.newBuilder().setName("test-net");
		Map<String,String> edgeProperties;
		
		System.out.println("Encoding network");
		for (IRelationship edge : network.getNetwork().edgeSet()) {
			
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
				.putProperties("param1", "1.1")
				.build();

		KlabData infomapMessage = KlabData.newBuilder()
							.addObjects(encodedNetwork.build())
							.addObjects(infomapParams)
							.build();
		
		HttpResponse<InputStream> response = null;
		Map<String,String> map = null;
		
		try {
			
			File outputFile = new File("/home/dibepa/protobuf-infomap");
			FileOutputStream output = new FileOutputStream(outputFile);
			infomapMessage.writeTo(output);
			output.close();
		
			response = infomapSendRequest(outputFile);
			KlabData infomapResponse = KlabData.parseFrom(response.body());
			
			KlabData.Object communities = infomapResponse.getObjects(0);
			
			name = communities.getName();
			map = communities.getPropertiesMap();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Done. Setting community map");
		
		for (IDirectObservation node : network.getNetwork().vertexSet()) {
			String c = map.get(node.getName());
			communityMap.put(node, c);
		}
		
		System.out.println(communityMap.size());
		System.out.println(communityMap);
		
		
		if (communityMap.size()>0) {
			scope.getMonitor()
			.info("instantiating the" + communityMap.values().stream().collect(Collectors.toSet()).size() + " communities detected in " + network.getName() + ".");
		} else {
			throw new KlabException("The server returned an empty result.");
		}
		
		return instantiateCommunities(semantics);
	}
	
	private List<IObjectArtifact> instantiateCommunities(IObservable observable){
		
		int i =1 ;
		
		List<IObjectArtifact> ret = new ArrayList<>();
		
		Map<String, List<IDirectObservation>> mapInversed = 
				communityMap.keySet()
					.stream()
					.collect( Collectors.groupingBy(k -> communityMap.get(k)) );
		
		for (List<IDirectObservation> community : mapInversed.values() ) {
			
			List<IShape> locations = community.stream().map( obs -> obs.getScale().getSpace().getShape() ).collect(Collectors.toList());
			
			Optional<IShape> shape = locations.stream().reduce( (u,s) -> {return u.union(s);} );
		
			// shape.get() throws an Exception is shape is not present.
			IScale scale = Scale.substituteExtent(scope.getScale(),shape.get());
			ret.add(scope.newObservation(observable, observable.getName() + "_" + i, scale, null));
			
			i++;
		}
					
		return ret;
		
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
	
	private HttpResponse<InputStream> infomapSendRequest(File outputFile) {
		HttpRequest request = infomapBuildRequest(outputFile);
		HttpResponse<InputStream> response;
        try {
            response = HttpClient.newHttpClient().send(request, BodyHandlers.ofInputStream());
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
