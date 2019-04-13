package org.integratedmodelling.klab.components.runtime;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.contextualizers.CastingStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.ClassifyingStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.ConversionResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.ExpressionResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.LiteralStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.LookupStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.UrnInstantiator;
import org.integratedmodelling.klab.components.runtime.contextualizers.UrnResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.dereifiers.DensityResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.dereifiers.DistanceResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.dereifiers.PresenceResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.wrappers.ConditionalContextualizer;
import org.integratedmodelling.klab.components.runtime.observations.Event;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.Process;
import org.integratedmodelling.klab.components.runtime.observations.Relationship;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.data.storage.BooleanSingletonStorage;
import org.integratedmodelling.klab.data.storage.ConceptSingletonStorage;
import org.integratedmodelling.klab.data.storage.DoubleSingletonStorage;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.engine.runtime.AbstractTask;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStatusException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Activity;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.graph.Graphs;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;

import akka.actor.ActorSystem;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * This component provides the default dataflow execution runtime and the
 * associated services. Simply dispatches a topologically sorted computation to
 * a threadpool executor. The JGraphT-based topological order built does not
 * account for possible parallel actuators, which it should. Use Coffman–Graham
 * algorithm when practical.
 * <p>
 * The initialization dataflow will build simple objects (essentially
 * storage-only observations) when the context is not temporal. If the context
 * is temporal, it will create Akka actors for all direct observations and
 * prepare them for temporal contextualization.
 * 
 * @author Ferd
 *
 */
@Component(id = "org.integratedmodelling.runtime", version = Version.CURRENT)
public class DefaultRuntimeProvider implements IRuntimeProvider {

	private ActorSystem rootActorSystem = null;
	private ExecutorService executor = Executors.newFixedThreadPool(Configuration.INSTANCE.getDataflowThreadCount());

	@Override
	public Future<IArtifact> compute(IActuator actuator, IScale scale, IResolutionScope scope,
			IDirectObservation context, IMonitor monitor) throws KlabException {

		return executor.submit(new Callable<IArtifact>() {

			@Override
			public IArtifact call() throws Exception {

				boolean switchContext = ((Actuator) actuator).getObservable().getType().is(Type.COUNTABLE)
						&& scope.getMode() == Mode.RESOLUTION;

				IRuntimeContext runtimeContext = null;
				if (context == null) {
					// new context
					runtimeContext = createRuntimeContext(actuator, scope, scale, monitor);
				} else if (switchContext) {
					// new catalog, new scale
					runtimeContext = ((Subject) context).getRuntimeContext().createContext(scale, actuator, scope,
							monitor);
				} else {
					// instantiating or resolving states: stay in context
					runtimeContext = ((Subject) context).getRuntimeContext().createChild(scale, actuator, scope,
							monitor);
				}

				Graph<IActuator, DefaultEdge> graph = createDependencyGraph(actuator);

				/*
				 * use a tie-breaking comparator to ensure that topologically equivalent
				 * partitions are executed in reverse priority order (the highest priority last,
				 * so that when extents overlap the highest-ranking actuator has the final say).
				 */
				TopologicalOrderIterator<IActuator, DefaultEdge> sorter = new TopologicalOrderIterator<>(graph,
						new Comparator<IActuator>() {
							@Override
							public int compare(IActuator o1, IActuator o2) {
								return Integer.compare(((Actuator) o2).getPriority(), ((Actuator) o1).getPriority());
							}
						});

				while (sorter.hasNext()) {

					Actuator active = (Actuator) sorter.next();
					// create children for all actuators that are not the same object as the root
					IRuntimeContext ctx = runtimeContext;
					if (active != actuator) {
						ctx = runtimeContext.createChild(scale, active, scope, monitor);
					}
					active.compute(ctx.getTargetArtifact(), ctx);
				}

				return runtimeContext.getTargetArtifact();
			}
		});
	}

	public ActorSystem getActorSystem() {
		if (rootActorSystem == null) {
			Logging.INSTANCE.info("Creating root actor system...");
			rootActorSystem = ActorSystem
					.create(Authentication.INSTANCE.getAuthenticatedIdentity(IEngine.class).getId());
		}
		return rootActorSystem;
	}

	private Graph<IActuator, DefaultEdge> createDependencyGraph(IActuator actuator) {
		DefaultDirectedGraph<IActuator, DefaultEdge> ret = new DefaultDirectedGraph<>(DefaultEdge.class);
		insertActuator(actuator, ret, new HashMap<>());
		if (!System.getProperty("visualize", "false").equals("false") && ret.vertexSet().size() > 1) {
			Graphs.show(ret, "Actuator dependencies");
		}
		return ret;
	}

	private void insertActuator(IActuator actuator, DefaultDirectedGraph<IActuator, DefaultEdge> graph,
			Map<String, IActuator> catalog) {

		graph.addVertex(actuator);
		catalog.put(actuator.getName(), actuator);

		for (IActuator a : actuator.getActuators()) {
			if (((Actuator) a).isReference()) {
				IActuator ref = catalog.get(a.getName());
				if (ref == null) {
					throw new KlabIllegalStatusException("referenced actuator not found: " + a.getName());
				}
				graph.addEdge(ref, actuator);
			} else {
				/*
				 * containment is a dependency only if there is a computation or mediation;
				 * otherwise children are computable in parallel - which this implementation
				 * does not support.
				 */
				if (a.isComputed()) {
					graph.addVertex(a);
					graph.addEdge(a, actuator);
				}
				insertActuator(a, graph, catalog);
			}
		}
	}

	@Override
	public RuntimeContext createRuntimeContext(IActuator actuator, IResolutionScope scope, IScale scale,
			IMonitor monitor) {
		RuntimeContext ret = new RuntimeContext((Actuator) actuator, scope, scale, monitor);
		IArtifact target = ret.createTarget((Actuator) actuator, scale, scope, null);
		if (target instanceof IDirectObservation) {
			((ResolutionScope) scope).setContext((IDirectObservation) target);
		}
		return ret;
	}

	@Override
	public IServiceCall getServiceCall(IComputableResource resource, IActuator target) {
		if (resource.getServiceCall() != null) {
			if (resource.getCondition() != null) {
				return ConditionalContextualizer.getServiceCall(resource);
			}
			return resource.getServiceCall();
		} else if (resource.getUrn() != null) {
			if (resource.getComputationMode() == Mode.INSTANTIATION) {
				return UrnInstantiator.getServiceCall(resource.getUrn(), resource.getCondition(), resource.isNegated());
			} else {
				return UrnResolver.getServiceCall(resource.getUrn(), resource.getCondition(), resource.isNegated());
			}
		} else if (resource.getExpression() != null) {
			return ExpressionResolver.getServiceCall(resource);
		} else if (resource.getLiteral() != null) {
			return LiteralStateResolver.getServiceCall(resource.getLiteral(), resource.getCondition(),
					resource.isNegated());
		} else if (resource.getConversion() != null) {
			try {
				return ConversionResolver.getServiceCall(resource.getConversion());
			} catch (KlabValidationException e) {
				throw new IllegalArgumentException(e);
			}
		} else if (resource.getClassification() != null) {
			return ClassifyingStateResolver.getServiceCall(
					((ComputableResource) resource).getValidatedResource(IClassification.class),
					resource.getCondition(), resource.isNegated());
		} else if (resource.getAccordingTo() != null) {
			IClassification classification = Types.INSTANCE.createClassificationFromMetadata(
					((Actuator) target).getObservable().getType(), resource.getAccordingTo());
			return ClassifyingStateResolver.getServiceCall(classification, resource.getCondition(),
					resource.isNegated());
		} else if (resource.getLookupTable() != null) {
			return LookupStateResolver.getServiceCall(
					((ComputableResource) resource).getValidatedResource(ILookupTable.class), resource.getCondition(),
					resource.isNegated());
		}

		// temp
		throw new IllegalArgumentException("unsupported computable passed to getServiceCall()");
	}

	@Override
	public IDataArtifact distributeComputation(IStateResolver resolver, IState data, IComputationContext context,
			IScale scale) throws KlabException {

		// TODO use a distributed loop unless the resolver implements some tag interface
		// to notify
		// non-reentrant behavior
		// TODO if this is done, the next one must be local to each thread
		IArtifact self = context.get("self", IArtifact.class);
		RuntimeContext ctx = new RuntimeContext((RuntimeContext) context);
		Collection<Pair<String, IDataArtifact>> variables = ctx.getArtifacts(IDataArtifact.class);
//		for (IScale state : scale) {
//
//			if (context.getMonitor().isInterrupted()) {
//				break;
//			}
			 StreamSupport.stream(((Scale) scale).spliterator(context.getMonitor()),
			 true).forEach((state) -> {
			data.set(state, resolver.resolve(data.getObservable(),
					variables.isEmpty() ? ctx : localizeContext(ctx, state, self, variables)));
		}  );

		return data;
	}

	private IComputationContext localizeContext(RuntimeContext context, IScale state, IArtifact self,
			Collection<Pair<String, IDataArtifact>> variables) {

		/*
		 * this may not be the same layer we're producing but reflects the current value
		 * for the computation.
		 */
		IArtifact targetArtifact = self == null ? context.getTargetArtifact() : self;
		if (targetArtifact instanceof IDataArtifact) {
			// this ensures that Groovy expressions are computable
			Object value = ((IDataArtifact) targetArtifact).get(state);
			if (value == null && targetArtifact.getType() == IArtifact.Type.NUMBER) {
				value = Double.NaN;
			}
			context.set("self", value);
		}

		for (Pair<String, IDataArtifact> variable : variables) {
			// this ensures that Groovy expressions are computable
			Object value = variable.getSecond().get(state);
			if (value == null && variable.getSecond().getType() == IArtifact.Type.NUMBER) {
				value = Double.NaN;
			}
			context.set(variable.getFirst(), value);
		}

		context.setScale(state);
		return context;
	}

	static IObservation createObservation(IObservable observable, IScale scale, RuntimeContext context) {
		return createObservation(observable, scale, context, false);
	}

	@Override
	public IObservation createEmptyObservation(IObservable observable, IComputationContext context) {
		return Observation.empty(observable, context);
	}

	public static IObservation createObservation(IObservable observable, IScale scale, RuntimeContext context,
			boolean scalarStorage) {

		boolean createActors = scale.getTime() != null;
		Activity activity = null;
		
		IIdentity identity = context.getMonitor().getIdentity();
		if (identity instanceof AbstractTask) {
			activity = ((AbstractTask<?>)identity).getActivity();
		}
		
		Observation ret = null;
		if (observable.is(Type.SUBJECT) || observable.is(Type.AGENT)) {
			ret = new Subject(observable.getLocalName(), (Observable) observable, (Scale) scale, context);
		} else if (observable.is(Type.EVENT)) {
			ret = new Event(observable.getLocalName(), (Observable) observable, (Scale) scale, context);
		} else if (observable.is(Type.PROCESS)) {
			ret = new Process(observable.getLocalName(), (Observable) observable, (Scale) scale, context);
		} else if (observable.is(Type.RELATIONSHIP)) {
			throw new KlabInternalErrorException(
					"createObservation() does not create relationships: use createRelationship()");
		} else if (observable.is(Type.QUALITY) || observable.is(Type.TRAIT)) {

			IDataArtifact storage = null;

			if (scalarStorage) {
				switch (observable.getArtifactType()) {
				case CONCEPT:
					storage = new ConceptSingletonStorage(observable, (Scale) scale);
					break;
				case NUMBER:
					storage = new DoubleSingletonStorage(observable, (Scale) scale);
					break;
				case BOOLEAN:
					storage = new BooleanSingletonStorage(observable, (Scale) scale);
					break;
				default:
					throw new IllegalArgumentException("illegal observable for singleton storage: " + observable);
				}
			} else {
				storage = Klab.INSTANCE.getStorageProvider().createStorage(observable.getArtifactType(), scale,
						context);
			}

			ret = new State((Observable) observable, (Scale) scale, context, storage);

		} else if (observable.is(Type.CONFIGURATION)) {

			ret = new org.integratedmodelling.klab.components.runtime.observations.Configuration(
					observable.getLocalName(), (Observable) observable, (Scale) scale, context);
		}

		ret.setGenerator(activity);
		
		// into an Akka
		// actor and register with the actor
		

		return ret;
	}

	static IRelationship createRelationship(Observable observable, IScale scale, IDirectObservation relationshipSource,
			IDirectObservation relationshipTarget, RuntimeContext runtimeContext) {

		Activity activity = null;
		
		IIdentity identity = runtimeContext.getMonitor().getIdentity();
		if (identity instanceof AbstractTask) {
			activity = ((AbstractTask<?>)identity).getActivity();
		}
		
		IRelationship ret = new Relationship(observable.getLocalName(), (Observable) observable, (Scale) scale,
				runtimeContext);

		// the semantics of the relationship may define whether we want a directed or
		// undirected edge.
		runtimeContext.network.addEdge(ret,
				new edu.uci.ics.jung.graph.util.Pair<IDirectObservation>(relationshipSource, relationshipTarget),
				observable.is(Type.BIDIRECTIONAL) ? EdgeType.UNDIRECTED : EdgeType.DIRECTED);

		((Observation)ret).setGenerator(activity);

		// TODO if actors must be created (i.e. there are temporal transitions etc) wrap
		// into an Akka
		// actor and register with the actor

		return ret;
	}

	@Override
	public List<IComputableResource> getComputation(IObservable availableType, Mode resolutionMode,
			IObservable desiredObservation) {

		if (availableType.is(Type.COUNTABLE)) {
			if (desiredObservation.is(Type.DISTANCE)) {
				return Collections.singletonList(new ComputableResource(
						DistanceResolver.getServiceCall(availableType, desiredObservation), resolutionMode));
			} else if (desiredObservation.is(Type.PRESENCE)) {
				return Collections.singletonList(new ComputableResource(
						PresenceResolver.getServiceCall(availableType, desiredObservation), resolutionMode));
			} else if (desiredObservation.is(Type.NUMEROSITY)) {
				return Collections.singletonList(new ComputableResource(
						DensityResolver.getServiceCall(availableType, desiredObservation), resolutionMode));
			}
		}

		return null;
	}

	@Override
	public void setComputationTargetId(IComputableResource resource, String targetId) {
		if (resource.getServiceCall() != null && resource.getServiceCall().getParameters().containsKey("artifact")) {
			resource.getServiceCall().getParameters().put("artifact", targetId);
		}
	}

	@Override
	public IState createState(IObservable observable, IArtifact.Type type, IScale scale, IComputationContext context) {
		IDataArtifact storage = Klab.INSTANCE.getStorageProvider().createStorage(type, scale, context);
		return new State((Observable) observable, (Scale) scale, (RuntimeContext) context, storage);
	}

	@Override
	public void shutdown() {
		if (rootActorSystem != null) {
			rootActorSystem.terminate();
		}
	}

	@Override
	public IComputableResource getCastingResolver(IArtifact.Type sourceType, IArtifact.Type targetType) {
		/*
		 * At the moment the only admissible cast is NUMBER -> BOOLEAN, although we may
		 * want some level of text -> X (number, boolean, concept) at some point, maybe
		 * with a warning. Also if eventually we want to explicitly support all number
		 * types this will have to expand.
		 */
		if (sourceType == IArtifact.Type.NUMBER && targetType == IArtifact.Type.BOOLEAN) {
			return new ComputableResource(CastingStateResolver.getServiceCall(sourceType, targetType), Mode.RESOLUTION);
		}
		return null;
	}
}
