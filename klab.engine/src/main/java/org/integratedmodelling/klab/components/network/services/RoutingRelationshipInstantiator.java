package org.integratedmodelling.klab.components.network.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.routing.Valhalla;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaException;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaOutputDeserializer;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.Graph;



public class RoutingRelationshipInstantiator extends AbstractContextualizer implements IExpression, IInstantiator {

	private String sourceArtifact = null;
    private String targetArtifact = null;
    private Double timeThreshold = null;
    private Double distanceThreshold = null; 

    // Diego: Not sure what is the role of this.
    Descriptor selectorDescriptor = null;

    
    static enum TransportType{
    	Auto("auto"),
    	Pedestrian("pedestrian"),
    	Bicycle("bicycle"),
    	Bus("bus"),
    	Truck("truck"),
    	Taxi("taxi"),
    	MotorScooter("motor_scooter"),
    	Multimodal("multimodel");
    	
    	private String type; 
    	
    	TransportType(String type) {
    		this.type = type;
    	}
    	
    	final public String getType() {
            return this.type;
        }
    }
    
    static enum Server{
    	Local(true),
    	Remote(false);
    	
    	private boolean type; 
    	
    	Server(boolean type) {
    		this.type = type;
    	}
    	
    	final public boolean getType() {
            return this.type;
        }
    }
    
    static enum GeometryCollapser {
    	Centroid("centroid");
    	private String type;
    	GeometryCollapser(String type) {
    		this.type = type;
    	}
    	final public String getType() {
            return this.type;
        }
    }
    
    private TransportType transportType = TransportType.Auto;
    private GeometryCollapser geometryCollapser = GeometryCollapser.Centroid;
    private Server server = Server.Local;
    private IContextualizationScope scope;
    private Valhalla valhalla; 
    private Graph<IObjectArtifact, DefaultEdge> graph;
    private Map<Pair<IDirectObservation,IDirectObservation>,IShape> trajectories; 

    
    static enum CostingOptions{
    	/* Empty for the time being, it is maybe too much unneeded detail. To be developed as needed. */
    }
	
	public RoutingRelationshipInstantiator() {/* to instantiate as expression - do not remove (or use) */}
	
	public RoutingRelationshipInstantiator(IParameters<String> parameters, IContextualizationScope scope) {

		this.scope = scope;
        this.sourceArtifact = parameters.get("source", String.class);
        this.targetArtifact = parameters.get("target", String.class);
        this.timeThreshold = parameters.get("time_limit", Double.class);
        this.distanceThreshold = parameters.get("distance_limit", Double.class);
        this.distanceThreshold = parameters.get("distance_limit", Double.class);
         
        if (parameters.containsKey("transport")) {
        	this.transportType = TransportType.valueOf(Utils.removePrefix(parameters.get("transport", String.class)));
        }
        if (parameters.containsKey("collapse_geometry")) {
        	this.geometryCollapser = GeometryCollapser.valueOf(Utils.removePrefix(parameters.get("collapse_geometry", String.class)));
        }
        if (parameters.containsKey("server")) {
        	this.server = Server.valueOf(Utils.removePrefix(parameters.get("server", String.class)));
        }
        
        this.valhalla = new Valhalla();

	}
	
	/*
	 * This is an adapted copy of the instantiate method of the configurable relationship instantiator.
	 * */
	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context) throws KlabException {
		
		
        IConcept sourceConcept = Observables.INSTANCE.getRelationshipSource(semantics.getType());
        IConcept targetConcept = Observables.INSTANCE.getRelationshipTarget(semantics.getType());

        
        /*
         * recover artifacts according to parameterization or lack thereof. Source and target
         * artifacts may be the same artifact.
         */
        List<IObservation> sources = new ArrayList<>();
        if (sourceArtifact == null) {
            sources.addAll(context.getObservations(sourceConcept));
        } else {
            IArtifact src = context.getArtifact(sourceArtifact);
            if (src instanceof IObservationGroup) {
                for (IArtifact a : src) {
                    sources.add((IObservation) a);
                }
            }
        }

        List<IObservation> targets = new ArrayList<>();
        if (targetArtifact == null) {
            targets.addAll(context.getObservations(targetConcept));
        } else {
            IArtifact src = context.getArtifact(targetArtifact);
            if (src instanceof IObservationGroup) {
                for (IArtifact a : src) {
                    targets.add((IObservation) a);
                }
            }
        }
        

        // all artifacts must be non-null and objects
        for (List<?> co : new List[]{sources, targets}) {
            for (Object o : co) {
                if (!(o instanceof IObjectArtifact)) {
                    throw new IllegalArgumentException(
                            "klab.networks.routing: at least one source or target artifact does not exist or is not an object artifact");
                }
            }
        }

        ILanguageExpression selector = null;
        Parameters<String> parameters = new Parameters<>();
        if (selectorDescriptor != null) {
            selector = selectorDescriptor.compile();
        }

        // TODO these are the simple methods - enable others separately
        Collection<IObservation> allSources = CollectionUtils.joinObservations(sources);
        Collection<IObservation> allTargets = CollectionUtils.joinObservations(targets);
        

        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        trajectories = new HashMap<>();

        Set<IObservation> connected = new HashSet<>();

        int nullTrajectories = 0;
        
        for (IObservation source : allSources) {

        	
            if (context.getMonitor().isInterrupted()) {
                break;
            }
            
            if (connected.contains(source)) {
                continue;
            }

            for (IArtifact target : allTargets) {
            	
                if (context.getMonitor().isInterrupted()) {
                    break;
                }
                
                // A direct connection of an instance to itself in the context of routing makes no sense and is thus avoided. 
                // Note that closed paths are nevertheless possible. 
                if (source.equals(target)) {
                    continue;
                }

                if (!(source instanceof IDirectObservation)) {
                    throw new IllegalArgumentException("source observations are not direct observations");
                }

                if (!(target instanceof IDirectObservation)) {
                    throw new IllegalArgumentException("target observations are not direct observations");
                }

                // Diego: I don't get the purpose of this. 
                if (selector != null) {

                    Object o = selector.eval(context, parameters, "source", source, "target", target);

                    if (o == null) {
                        o = Boolean.FALSE;
                    }
                    if (!(o instanceof Boolean)) {
                        throw new KlabValidationException(
                                "relationship instantiator: selector expression must return true/false");
                    }

                    if (!(Boolean) o) {
                        continue;
                    }
                }
                
                // Find the optimal route between target and location. 
                
                // Handle non-0 dimension objects: collapse higher dimension geometries to their centroid. This is the easiest. 
                // More complex versions could involve generating N random points within the geometry, or for polygons random points
                // along its border. 
                    
                String valhallaInput = Valhalla.buildValhallaJsonInput((IDirectObservation) source, (IDirectObservation) target, transportType.getType(), geometryCollapser.getType());                
                ValhallaOutputDeserializer.OptimizedRoute route;
                IShape trajectory; 
                Map<String,Double> stats;
				try {
					route = valhalla.optimized_route(valhallaInput);
					trajectory = route.getPath().transform(scope.getScale().getSpace().getProjection());
	                stats = route.getSummaryStatistics();
	                
				} catch (ValhallaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					route = null;
					stats = null;
					trajectory = null;
				}
                

                if (
                		(timeThreshold == null || (stats.get("time") < timeThreshold)) && 
                		(distanceThreshold == null || (stats.get("length") < distanceThreshold))
                	
                	) 
                {
                	
                	if (trajectory != null) {
                		connect((IDirectObservation) source, (IDirectObservation) target, trajectory);
                		connected.add((IObservation) target);
                		trajectories.put(new Pair<IDirectObservation,IDirectObservation>((IDirectObservation)source,(IDirectObservation)target),trajectory);
                	}
                	else {
                		
                		nullTrajectories += 1; 
                		
                	}
                } 	
                  
            }
        }
        
        if (nullTrajectories > 0) {
        	
        	context.getMonitor()
            .info("creating " + graph.edgeSet().size() + " " + Concepts.INSTANCE.getDisplayName(semantics.getType())
                    + " routing relationships. \n" + nullTrajectories + " relationships could not be created because a route "
                    		+ "was not found either due to a lack of data or because travel characteristics exceed the allowed limits.");
        	
        } else {
	        context.getMonitor()
	                .info("creating " + graph.edgeSet().size() + " " + Concepts.INSTANCE.getDisplayName(semantics.getType())
	                        + " routing relationships.");
        }
        
        return instantiateRelationships(semantics);
	}

	private List<IObjectArtifact> instantiateRelationships(IObservable observable) {
		
        int i = 1;
        List<IObjectArtifact> ret = new ArrayList<>();
        // build from graph
        for (DefaultEdge edge : graph.edgeSet()) {
            IDirectObservation source = (IDirectObservation) graph.getEdgeSource(edge);
            IDirectObservation target = (IDirectObservation) graph.getEdgeTarget(edge);
            IScale scale = Scale.substituteExtent(scope.getScale(), trajectories.get(new Pair<IDirectObservation,IDirectObservation>((IDirectObservation)source,(IDirectObservation)target)));
            ret.add(scope.newRelationship(observable, observable.getName() + "_" + i, scale, source, target, null));
            i++;
        }
        return ret;
    }
	
	private void connect(IDirectObservation source, IDirectObservation target, ISpace spatialConnection) {

        // add to graph for bookkeeping unless we don't need it
        graph.addVertex(source);
        graph.addVertex(target);
        graph.addEdge(source, target, new SpatialEdge(null, spatialConnection == null ? null : spatialConnection.getShape()));
    }
	
	class SpatialEdge extends DefaultEdge {

        private static final long serialVersionUID = -6448417928592670704L;

        IShape sourceShape;
        IShape targetShape;

        SpatialEdge() {
        }

        SpatialEdge(IShape s, IShape t) {
            this.sourceShape = s;
            this.targetShape = t;
        }

    }
	
	
	@Override
	public Object eval(IContextualizationScope context, Object...parameters) throws KlabException {
		return new RoutingRelationshipInstantiator(Parameters.create(parameters), context);
	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

}
