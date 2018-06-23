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

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IClassification;
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
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.contextualizers.ClassifyingStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.ConversionResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.ExpressionResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.LiteralStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.UrnInstantiator;
import org.integratedmodelling.klab.components.runtime.contextualizers.UrnResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.dereifiers.DensityResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.dereifiers.DistanceResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.dereifiers.PresenceResolver;
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
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStatusException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.graph.Graphs;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;

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
@Component(id = "runtime", version = Version.CURRENT)
public class DefaultRuntimeProvider implements IRuntimeProvider {

	ExecutorService executor = Executors.newFixedThreadPool(Configuration.INSTANCE.getDataflowThreadCount());

	@Override
	public Future<IArtifact> compute(IActuator actuator, IScale scale, IResolutionScope scope,
			IDirectObservation context, IMonitor monitor) throws KlabException {

		return executor.submit(new Callable<IArtifact>() {

			@Override
			public IArtifact call() throws Exception {

				IRuntimeContext runtimeContext = context == null ? createRuntimeContext(actuator, scope, scale, monitor)
						: ((Subject) context).getRuntimeContext().createChild(scale, actuator, scope, monitor);

				
				Graph<IActuator, DefaultEdge> graph = createDependencyGraph(actuator);

				/*
				 * use a tie-breaking comparator to ensure that topologically equivalent
				 * partitions are executed in reverse priority order (the highest priority last,
				 * so that when extents overlap the highest ranking actuator has the final say).
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
					throw new KlabIllegalStatusException("referenced actuator not found");
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
		IArtifact target = ret.createTarget((Actuator) actuator, scale, scope);
		if (target instanceof IDirectObservation) {
			((ResolutionScope) scope).setContext((IDirectObservation) target);
		}
		return ret;
	}

	@Override
	public IServiceCall getServiceCall(IComputableResource resource, IActuator target) {
		if (resource.getServiceCall() != null) {
			return resource.getServiceCall();
		} else if (resource.getUrn() != null) {
			return ((Actuator) target).getObservable().is(Type.COUNTABLE)
					? UrnInstantiator.getServiceCall(resource.getUrn())
					: UrnResolver.getServiceCall(resource.getUrn());
		} else if (resource.getExpression() != null) {
			return ExpressionResolver.getServiceCall(resource);
		} else if (resource.getLiteral() != null) {
			return LiteralStateResolver.getServiceCall(resource.getLiteral());
		} else if (resource.getConversion() != null) {
			try {
				return ConversionResolver.getServiceCall(resource.getConversion());
			} catch (KlabValidationException e) {
				throw new IllegalArgumentException(e);
			}
		} else if (resource.getClassification() != null) {
			return ClassifyingStateResolver.getServiceCall(((ComputableResource)resource).getValidatedResource(IClassification.class));
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
		RuntimeContext ctx = new RuntimeContext((RuntimeContext) context);
		Collection<Pair<String, IDataArtifact>> variables = ctx.getArtifacts(IDataArtifact.class);
		for (IScale state : scale) {
			data.set(state, resolver.resolve(data.getObservable(),
					variables.isEmpty() ? ctx : localizeContext(ctx, state, variables)));
		}
		return data;
	}

	private IComputationContext localizeContext(RuntimeContext context, IScale state,
			Collection<Pair<String, IDataArtifact>> variables) {

		if (context.getTargetArtifact() instanceof IDataArtifact) {
			context.set("self", ((IDataArtifact) context.getTargetArtifact()).get(state));
		}

		for (String var : context.getInputs()) {
			IArtifact artifact = context.getArtifact(var);
			if (artifact instanceof IDataArtifact) {
				context.set(var, ((IDataArtifact) artifact).get(state));
			}
		}
		context.setScale(state);
		return context;
	}

	static IObservation createObservation(Actuator actuator, RuntimeContext context) {
		return createObservation(actuator.getObservable(), context.getScale(), context,
				actuator.isStorageScalar(context.getScale()));
	}

	static IObservation createObservation(IObservable observable, IScale scale, RuntimeContext context) {
		return createObservation(observable, scale, context, false);
	}

	@Override
	public IObservation createEmptyObservation(IObservable observable, IScale scale) {
		return Observation.empty(observable, scale);
	}

	
	static IObservation createObservation(IObservable observable, IScale scale, RuntimeContext context,
			boolean scalarStorage) {

		boolean createActors = scale.getTime() != null;

		Observation ret = null;
		if (observable.is(Type.SUBJECT)) {
			ret = new Subject(observable.getLocalName(), (Observable) observable, (Scale) scale, context);
		} else if (observable.is(Type.EVENT)) {
			ret = new Event(observable.getLocalName(), (Observable) observable, (Scale) scale, context);
		} else if (observable.is(Type.PROCESS)) {
			ret = new Process(observable.getLocalName(), (Observable) observable, (Scale) scale, context);
		} else if (observable.is(Type.RELATIONSHIP)) {
			throw new IllegalArgumentException(
					"createObservation() does not create relationships: use createRelationship()");
		} else if (observable.is(Type.QUALITY)) {

			IDataArtifact storage = null;

			if (scalarStorage) {
				switch (observable.getObservationType()) {
				case CLASSIFICATION:
					storage = new ConceptSingletonStorage(observable, (Scale) scale);
					break;
				case QUANTIFICATION:
					storage = new DoubleSingletonStorage(observable, (Scale) scale);
					break;
				case VERIFICATION:
					storage = new BooleanSingletonStorage(observable, (Scale) scale);
					break;
				case INSTANTIATION:
				case SIMULATION:
				case DETECTION:
				default:
					throw new IllegalArgumentException("illegal observable for singleton storage: " + observable);
				}
			} else {
				storage = Klab.INSTANCE.getStorageProvider().createStorage(observable, scale, context);
			}

			switch (observable.getObservationType()) {
			case CLASSIFICATION:
			case QUANTIFICATION:
			case VERIFICATION:
				ret = new State((Observable) observable, (Scale) scale, context, storage);
				break;
			case INSTANTIATION:
			case SIMULATION:
			case DETECTION:
			default:
				throw new IllegalArgumentException("illegal observable for storage: " + observable);
			}
		} else if (observable.is(Type.CONFIGURATION)) {

			ret = new org.integratedmodelling.klab.components.runtime.observations.Configuration(
					observable.getLocalName(), (Observable) observable, (Scale) scale, context);
		}

		// TODO if actors must be created (i.e. there are temporal transitions etc) wrap
		// into an Akka
		// actor and register with the actor

		return ret;
	}

	static IRelationship createRelationship(Observable observable, IScale scale, ISubject relationshipSource,
			ISubject relationshipTarget, RuntimeContext runtimeContext) {

		IRelationship ret = new Relationship(observable.getLocalName(), (Observable) observable, (Scale) scale,
				runtimeContext);

		// TODO semantic of the relationship may define whether we want a directed or
		// undirected edge.
		runtimeContext.network.addEdge(ret,
				new edu.uci.ics.jung.graph.util.Pair<ISubject>(relationshipSource, relationshipTarget),
				observable.is(Type.BIDIRECTIONAL) ? EdgeType.UNDIRECTED : EdgeType.DIRECTED);

		// TODO if actors must be created (i.e. there are temporal transitions etc) wrap
		// into an Akka
		// actor and register with the actor

		return ret;
	}

	@Override
	public List<IComputableResource> getComputation(IObservable availableType, IObservable desiredObservation) {

		if (availableType.is(Type.COUNTABLE)) {
			if (desiredObservation.is(Type.DISTANCE)) {
				return Collections.singletonList(
						new ComputableResource(DistanceResolver.getServiceCall(availableType, desiredObservation)));
			} else if (desiredObservation.is(Type.PRESENCE)) {
				return Collections.singletonList(
						new ComputableResource(PresenceResolver.getServiceCall(availableType, desiredObservation)));
			} else if (desiredObservation.is(Type.NUMEROSITY)) {
				return Collections.singletonList(
						new ComputableResource(DensityResolver.getServiceCall(availableType, desiredObservation)));
			}
		}

		return null;
	}
}
