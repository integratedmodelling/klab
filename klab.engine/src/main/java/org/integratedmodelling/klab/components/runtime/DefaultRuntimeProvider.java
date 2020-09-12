package org.integratedmodelling.klab.components.runtime;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.concurrent.ConcurrentUtils;
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.NonReentrant;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.contextualizers.CastingStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.CategoryClassificationResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.ChangingResourceResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.ClassifyingStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.ConversionResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.DereifyingStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.ExpressionResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.LiteralStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.LookupStateResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.ObjectClassificationResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.UrnInstantiator;
import org.integratedmodelling.klab.components.runtime.contextualizers.UrnResolver;
import org.integratedmodelling.klab.components.runtime.contextualizers.ValueOperatorResolver;
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
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.engine.runtime.AbstractTask;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Activity;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;

import akka.actor.ActorSystem;

/**
 * This component provides the default dataflow execution runtime and the
 * associated services. Simply dispatches a topologically sorted computation to
 * a threadpool executor. The JGraphT-based topological order built does not
 * account for possible parallel actuators, which it should. Use Coffman–Graham
 * algorithm when practical.
 * <p>
 * The initialization dataflow will build simple objects (essentially
 * storage-only observations) when the context is not temporal. If the context
 * is temporal, it will create Akka actors for all direct observations where a
 * behavior is specified, and prepare them for temporal contextualization.
 * 
 * @author Ferd
 *
 */
@Component(id = "org.integratedmodelling.runtime", version = Version.CURRENT)
public class DefaultRuntimeProvider implements IRuntimeProvider {

	private ActorSystem rootActorSystem = null;
	private ExecutorService executor = Executors.newFixedThreadPool(Configuration.INSTANCE.getDataflowThreadCount());

	@Override
	public Future<IArtifact> compute(IDataflow<? extends IArtifact> dataflow, IScale scale, IResolutionScope scope,
			IMonitor monitor) throws KlabException {

		Callable<IArtifact> task = new Callable<IArtifact>() {

			@Override
			public IArtifact call() throws Exception {

				IDirectObservation context = scope.getContext();
				IRuntimeScope runtimeContext = null;
				Actuator initializer = (Actuator) dataflow.getActuators().get(0);
				boolean switchContext = context != null && initializer.getObservable().getType().is(Type.COUNTABLE)
						&& scope.getMode() == Mode.RESOLUTION;

				/*
				 * We get the overall scale, which we merge with the actuator's. This ensures
				 * that scale constraints at the model level are dealt with before own artifacts
				 * are created.
				 * 
				 * TODO/CHECK should use all the top-level actuators and merge from the dataflow
				 */
				IScale actuatorScale = initializer.mergeScale(scale, monitor);

				if (switchContext) {
					// new catalog, new scale, context subject is in the scope, network remains
					runtimeContext = ((Observation) context).getScope().createContext(actuatorScale, initializer,
							dataflow, scope, monitor);
				} else if (context == null) {
					// new context
					runtimeContext = createRuntimeContext(initializer, scope, actuatorScale, monitor);
				} else {
					// instantiating or resolving states: stay in context
					runtimeContext = ((Subject) context).getScope().createChild(actuatorScale, initializer, scope,
							monitor);
				}

				for (IActuator actuator : dataflow.getActuators()) {

					List<Actuator> order = ((Actuator) actuator).dependencyOrder();

					// must merge in any constraints from the model before calling this.
					IScale initializationScale = ((Scale) actuatorScale).copy().initialization();

					int i = 0;
					for (Actuator active : order) {

						IRuntimeScope ctx = runtimeContext;
						if (active != actuator) {
							ctx = runtimeContext.createChild(actuatorScale, active, scope, monitor)
									.locate(initializationScale, monitor);
						}

						/*
						 * this won't actually run the contextualizers unless the observation is a
						 * continuant.
						 */
						if (active.isComputed() || ((Actuator) active).isMerging()) {
							active.compute(ctx.getTargetArtifact(), ctx);
						}
						if (!((Actuator) active).getDataflow().isSecondary()
								&& !(monitor.getIdentity().is(IIdentity.Type.TASK)
										&& ((AbstractTask<?>) monitor.getIdentity()).isChildTask())) {
							((Actuator) active).notifyArtifacts(i == order.size() - 1, ctx);
						}

						ctx.scheduleActions(active);

						i++;
					}
				}

				/*
				 * auto-start the scheduler if transitions have been registered.
				 */
				if (((Dataflow) dataflow).isPrimary() && runtimeContext.getScheduler() != null
						&& !runtimeContext.getScheduler().isEmpty()) {
					ITaskTree<?> subtask = ((ITaskTree<?>) monitor.getIdentity())
							.createChild("Temporal contextualization");
					try {
						((AbstractTask<?>) subtask).notifyStart();
						runtimeContext.getScheduler().run(subtask.getMonitor());
						((AbstractTask<?>) subtask).notifyEnd();
					} catch (Throwable e) {
						throw ((AbstractTask<?>) subtask).notifyAbort(e);
					}
				}

				return runtimeContext.getTargetArtifact();
			}
		};

		if (Configuration.INSTANCE.synchronousDataflow()) {
			try {
				return ConcurrentUtils.constantFuture(task.call());
			} catch (Exception e) {
				throw new KlabException(e);
			}
		}

		return executor.submit(task);
	}

	@Override
	public RuntimeScope createRuntimeContext(IActuator actuator, IResolutionScope scope, IScale scale,
			IMonitor monitor) {
		RuntimeScope ret = new RuntimeScope((Actuator) actuator, scope, scale, monitor);
		// targets are created using the resolution scale, not the time-scalar scale
		// we're running in.
		IArtifact target = ret.createTarget((Actuator) actuator, scope.getScale(), scope, null);
		if (target instanceof IDirectObservation) {
			((ResolutionScope) scope).setContext((IDirectObservation) target);
		}
		return ret;
	}

	@Override
	public IServiceCall getServiceCall(IContextualizable resource, IObservable observable, ISession session) {

		IServiceCall ret = null;

		/*
		 * if (resource.isVariable()) { ret = Evaluator.getServiceCall(resource); } else
		 */
		if (resource.getServiceCall() != null) {
			if (resource.getCondition() != null) {
				ret = ConditionalContextualizer.getServiceCall(resource);
			} else {
				ret = ((KimServiceCall) resource.getServiceCall()).copy();
			}
		} else if (resource.getUrn() != null) {
			if (resource.getComputationMode() == Mode.INSTANTIATION) {
				ret = UrnInstantiator.getServiceCall(resource.getUrn(), resource.getCondition(), resource.isNegated());
			} else {
				ret = UrnResolver.getServiceCall(resource.getUrn(), resource.getCondition(), resource.isNegated());
			}
		} else if (resource.getExpression() != null) {
			ret = ExpressionResolver.getServiceCall(resource, observable);
		} else if (resource.getLiteral() != null) {
			ret = LiteralStateResolver.getServiceCall(resource.getLiteral(), resource.getCondition(),
					resource.isNegated());
		} else if (resource.getConversion() != null) {
			try {
				ret = ConversionResolver.getServiceCall(resource.getConversion());
			} catch (KlabValidationException e) {
				throw new IllegalArgumentException(e);
			}
		} else if (resource.getClassification() != null) {
			ret = ClassifyingStateResolver.getServiceCall(
					((ComputableResource) resource).getValidatedResource(IClassification.class),
					resource.getCondition(), resource.isNegated());
		} else if (resource.getAccordingTo() != null) {
			IConcept classifiable = observable.getType();
			if (classifiable.is(Type.CHANGE)) {
				/*
				 * This happens when we use a derived model to describe the change in a resolved
				 * quality. I'd like this to not be necessary, but I'm not ready to trace the
				 * consequence of this done "right".
				 */
				classifiable = Observables.INSTANCE.getDescribedType(classifiable);
			}
			IClassification classification = Types.INSTANCE.createClassificationFromMetadata(classifiable,
					resource.getAccordingTo());
			ret = ClassifyingStateResolver.getServiceCall(classification, resource.getCondition(),
					resource.isNegated());
		} else if (resource.getLookupTable() != null) {
			ret = LookupStateResolver.getServiceCall(
					((ComputableResource) resource).getValidatedResource(ILookupTable.class), resource.getCondition(),
					resource.isNegated());
		} /*
			 * else if (resource.getMergedUrns() != null) { if
			 * (resource.getComputationMode() == Mode.INSTANTIATION) { ret =
			 * MergedUrnInstantiator.getServiceCall(resource.getMergedUrns()); } else { ret
			 * = MergedUrnResolver.getServiceCall(resource.getMergedUrns()); } }
			 */else {
			throw new IllegalArgumentException("unsupported computable passed to getServiceCall()");
		}

		if (((ComputableResource) resource).getExternalParameters() != null && session != null) {
			/*
			 * add model-based parameters that are non-interactive, or all if not in
			 * interactive mode. Interactive parameters in interactive mode are already
			 * there.
			 */
			for (IAnnotation annotation : ((ComputableResource) resource).getExternalParameters()) {
				if (!session.isInteractive() || !annotation.get("interact", Boolean.FALSE)) {
					ret.getParameters().put(annotation.get("name", String.class), annotation.get("default"));
				}
			}
		}

		return ret;
	}

	@Override
	public IDataArtifact distributeComputation(IStateResolver resolver, IState data, IContextualizationScope context,
			ILocator scale) throws KlabException {

		boolean reentrant = !resolver.getClass().isAnnotationPresent(NonReentrant.class);
		if (System.getProperty("synchronous") != null
				|| (context.getModel() != null && Annotations.INSTANCE.hasAnnotation(context.getModel(), "serial"))) {
			reentrant = false;
		}
		IArtifact self = context.get("self", IArtifact.class);
		RuntimeScope ctx = new RuntimeScope((RuntimeScope) context, context.getVariables());
		Collection<Pair<String, IDataArtifact>> variables = ctx.getArtifacts(IDataArtifact.class);

//		System.err.println("DISTRIBUTING COMPUTATION FOR " + data + " AT " + scale + " WITH " + resolver);

		if (reentrant) {
			StreamSupport.stream(((Scale) scale).spliterator(context.getMonitor()), true).forEach((state) -> {
				if (!context.getMonitor().isInterrupted()) {
					data.set(state, resolver.resolve(data.getObservable(),
							variables.isEmpty() ? ctx : localizeContext(ctx, state, self, variables)));
				}
			});
		} else {
			for (ILocator state : scale) {
				if (context.getMonitor().isInterrupted()) {
					break;
				}
				data.set(state, resolver.resolve(data.getObservable(),
						variables.isEmpty() ? ctx : localizeContext(ctx, (IScale) state, self, variables)));
			}
		}

//		System.err.println("DONE " + data);
//
//		Debug.INSTANCE.summarize(data);

		return data;
	}

	private IContextualizationScope localizeContext(RuntimeScope context, IScale state, IArtifact self,
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

	@Override
	public IObservation createEmptyObservation(IObservable observable, IContextualizationScope context) {
		return Observation.empty(observable, context);
	}

	public static IObservation createObservation(IObservable observable, IScale scale, RuntimeScope context) {
		return createObservation(observable, scale, context, false);
	}

	/**
	 * 
	 * @param observable
	 * @param scale
	 * @param scope
	 * @param createArchetype if true, create an archetype. TODO support for
	 *                        non-qualities.
	 * @return
	 */
	public static IObservation createObservation(IObservable observable, IScale scale, RuntimeScope scope,
			boolean createArchetype) {

		Activity activity = null;

		IIdentity identity = scope.getMonitor().getIdentity();
		if (identity instanceof AbstractTask) {
			activity = ((AbstractTask<?>) identity).getActivity();
		}

		Observation ret = null;
		if (observable.is(Type.SUBJECT) || observable.is(Type.AGENT)) {
			ret = new Subject(observable.getName(), (Observable) observable, (Scale) scale, scope);
		} else if (observable.is(Type.EVENT)) {
			ret = new Event(observable.getName(), (Observable) observable, (Scale) scale, scope);
		} else if (observable.is(Type.PROCESS)) {
			ret = new Process(observable.getName(), (Observable) observable, (Scale) scale, scope);
		} else if (observable.is(Type.RELATIONSHIP)) {
			throw new KlabInternalErrorException(
					"createObservation() does not create relationships: use createRelationship()");
		} else if (observable.is(Type.QUALITY)) {
			if (createArchetype) {
				ret = State.newArchetype((Observable) observable, (Scale) scale, scope);
			} else {
				IStorage<?> storage = Klab.INSTANCE.getStorageProvider().createStorage(observable.getArtifactType(),
						scale, scope);
				ret = new State((Observable) observable, (Scale) scale, scope, (IDataStorage<?>) storage);
			}
		} else if (observable.is(Type.CONFIGURATION)) {

			ret = new org.integratedmodelling.klab.components.runtime.observations.Configuration(observable.getName(),
					(Observable) observable, (Scale) scale, scope);
		}

		ret.setGenerator(activity);

		return ret;
	}

	static IRelationship createRelationship(Observable observable, IScale scale, IDirectObservation relationshipSource,
			IDirectObservation relationshipTarget, RuntimeScope runtimeContext) {

		Activity activity = null;

		IIdentity identity = runtimeContext.getMonitor().getIdentity();
		if (identity instanceof AbstractTask) {
			activity = ((AbstractTask<?>) identity).getActivity();
		}

		IRelationship ret = new Relationship(observable.getName(), (Observable) observable, (Scale) scale,
				runtimeContext);
		runtimeContext.network.addEdge(relationshipSource, relationshipTarget, ret);
		((Observation) ret).setGenerator(activity);

		// TODO if actors must be created (i.e. there are temporal transitions etc) wrap
		// into an Akka
		// actor and register with the actor

		return ret;
	}

	@Override
	public List<IContextualizable> getComputation(IObservable availableType, Mode resolutionMode,
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
	public void setComputationTargetId(IContextualizable resource, String targetId) {
		if (resource.getServiceCall() != null && resource.getServiceCall().getParameters().containsKey("artifact")) {
			resource.getServiceCall().getParameters().put("artifact", targetId);
		}
	}

	@Override
	public IState createState(IObservable observable, IArtifact.Type type, IScale scale,
			IContextualizationScope context) {
		IStorage<?> storage = Klab.INSTANCE.getStorageProvider().createStorage(type, scale, context);
		return new State((Observable) observable, (Scale) scale, (RuntimeScope) context, (IDataStorage<?>) storage);
	}

	@Override
	public void shutdown() {
		if (rootActorSystem != null) {
			rootActorSystem.terminate();
		}
	}

	@Override
	public IContextualizable getCastingResolver(IArtifact.Type sourceType, IArtifact.Type targetType) {
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

	@Override
	public IContextualizable getOperatorResolver(IObservable classifiedObservable, ValueOperator operator,
			Object operand, Set<ValueOperator> modifiers) {

		if (operator == ValueOperator.BY) {

			if (!(operand instanceof IConcept)) {
				throw new IllegalArgumentException("Cannot classify an observable by anything else than a concept");
			}

			IConcept aggregator = (IConcept) operand;
			if (aggregator.is(Type.CLASS) || aggregator.is(Type.TRAIT) || aggregator.is(Type.PRESENCE)) {
				return new ComputableResource(
						CategoryClassificationResolver.getServiceCall(classifiedObservable, aggregator, modifiers),
						Mode.RESOLUTION);
			} else if (aggregator.is(Type.COUNTABLE)) {
				return new ComputableResource(
						ObjectClassificationResolver.getServiceCall(classifiedObservable, aggregator, modifiers),
						Mode.RESOLUTION);
			}
		}
		return new ComputableResource(ValueOperatorResolver.getServiceCall(classifiedObservable, operator, operand),
				Mode.RESOLUTION);
	}

	@Override
	public IContextualizable getDereifyingResolver(IConcept distributingType, IConcept inherentType,
			IArtifact.Type targetType) {
		if (targetType == IArtifact.Type.OBJECT) {
			throw new KlabUnimplementedException("de-reification of countable observations is still unimplemented");
		}
		return new ComputableResource(
				DereifyingStateResolver.getServiceCall(distributingType, inherentType, targetType), Mode.RESOLUTION);

	}

	@Override
	public IContextualizable getChangeResolver(IObservable changeObservable, IResource mergedResource) {
		if (!(mergedResource instanceof MergedResource)) {
			throw new KlabIllegalStateException("change resolver can only be used with a merged resource");
		}
		return new ComputableResource(
				ChangingResourceResolver.getServiceCall(changeObservable.getType(), (MergedResource) mergedResource),
				Mode.RESOLUTION);
	}
}
