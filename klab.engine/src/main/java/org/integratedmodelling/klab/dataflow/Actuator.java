package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.model.contextualization.IPredicateClassifier;
import org.integratedmodelling.klab.api.model.contextualization.IPredicateResolver;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.components.runtime.observations.ObservedArtifact;
import org.integratedmodelling.klab.components.runtime.observations.StateLayer;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.data.table.LookupTable;
import org.integratedmodelling.klab.documentation.Report;
import org.integratedmodelling.klab.engine.runtime.SimpleRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.rest.DataflowState;
import org.integratedmodelling.klab.rest.DataflowState.Status;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;

public class Actuator implements IActuator {

	// these are part of graphs so they should behave wrt. equality. Adding an ID
	// for comparison just to ensure that future changes upstream do not affect the
	// logics.
	private String _actuatorId = NameGenerator.shortUUID();

	protected String name;
	private String alias;
	private INamespace namespace;
	private Observable observable;

	/**
	 * Status is 0 = waiting, 1 = computing, 2 = computed, 3 = interrupted. Used
	 * within documentation templates to figure out what can be said. When the
	 * computation starts and ends, the timestamps are updated.
	 */
	private AtomicInteger status = new AtomicInteger(0);
	private AtomicLong startComputation = new AtomicLong(0);
	private AtomicLong endComputation = new AtomicLong(0);

	/*
	 * The coverage is generic and is set to the coverage of the generating models.
	 * The dataflow will set the actual scale of computation into partialScale just
	 * before calling compute().
	 */
	protected Coverage coverage;
	private IArtifact.Type type;
	private Dataflow dataflow;
	List<IActuator> actuators = new ArrayList<>();
	Date creationTime = new Date();
	// private boolean createsObservation;

	// reference means that this actuator is a stand-in for another in the same
	// dataflow
	private boolean reference;
	// input means that this actuator retrieves a pre-computed artifact from the
	// context
	private boolean input;
	// export is currently unused but can be used to tag some artifacts for an
	// output port
	private boolean exported;

	protected ISession session;

	// this is only for the API
	private List<IComputableResource> computedResources = new ArrayList<>();
	// we store the annotations from the model to enable probes or other
	// non-semantic options
	private List<IAnnotation> annotations = new ArrayList<>();

	// this is for documentation templates, not saved
	private transient IRuntimeScope currentContext;

	/*
	 * this gets a copy of the original model resource, so we can do things to it.
	 */
	public void addComputation(IComputableResource resource) {
		((ComputableResource) resource).setOriginalObservable(this.observable);
		computedResources.add(resource);
		IServiceCall serviceCall = Klab.INSTANCE.getRuntimeProvider().getServiceCall(resource, this);
		computationStrategy.add(new Pair<>(serviceCall, resource));
	}

	public void addMediation(IComputableResource resource, Actuator target) {
		((ComputableResource) resource).setTargetId(target.getAlias() == null ? target.getName() : target.getAlias());
		((ComputableResource) resource).setMediation(true);
		computedResources.add(resource);
		IServiceCall serviceCall = Klab.INSTANCE.getRuntimeProvider().getServiceCall(resource, target);
		mediationStrategy.add(new Pair<>(serviceCall, resource));
	}

	public void addDocumentation(IDocumentation documentation) {
		this.documentation.add(documentation);
	}

	/**
	 * the specs from which the contextualizers are built: first the computation,
	 * then the mediation. We keep them separated because the compiler needs to
	 * rearrange mediators and references as needed. Then both get executed to
	 * produce the final list of contextualizers.
	 * 
	 * Each list contains a service call and its local target name, null for the
	 * main observable.
	 */
	private List<Pair<IServiceCall, IComputableResource>> computationStrategy = new ArrayList<>();
	private List<Pair<IServiceCall, IComputableResource>> mediationStrategy = new ArrayList<>();

	/**
	 * Documentation extracted from the models and other objects used and compiled
	 * at end of computation
	 */
	private List<IDocumentation> documentation = new ArrayList<>();

	/*
	 * keep all computed observations here for notifyArtifact() to send on the
	 * message bus
	 */
	private List<IObservation> products = new ArrayList<>();

	// if this is non-null, coverage is also non-null and the actuator defines a
	// partition of the named target artifact, covering our coverage only.
	private String partitionedTarget;

	/*
	 * when this is a partition, the priority reflects the ranking so that the
	 * highest ranked partial can be applied last
	 */
	private int priority = 0;
	private Mode mode;
	private Model model;

	/*
	 * the scale of computation for partials. This is set by the dataflow when the
	 * actual context is known. FIXME this is kind of dirty: the dataflow will set
	 * it into the actuator, so each actuator tree should be used only once.
	 */
	private Scale mergedScale;

	/*
	 * Name of the corresponding observable in the generating model, if that's
	 * unambiguous; otherwise name of the observable. Users should be able to choose
	 * whether to generate their artifacts using this or (as a default) the
	 * observable's name, e.g. the dependency name.
	 */
	private String referenceName;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<IActuator> getActuators() {
		return actuators;
	}

	@Override
	public List<IActuator> getInputs() {
		List<IActuator> ret = new ArrayList<>();
		for (IActuator actuator : actuators) {
			if (((Actuator) actuator).isReference()) {
				ret.add(actuator);
			}
		}
		return ret;
	}

	@Override
	public List<IActuator> getOutputs() {
		List<IActuator> ret = new ArrayList<>();
		for (IActuator actuator : actuators) {
			if (((Actuator) actuator).exported) {
				ret.add(actuator);
			}
		}
		return ret;
	}

	/**
	 * Compute the actuator.
	 * 
	 * @param target         the final artifact being computed. If this actuator
	 *                       handles an instantiation, the passed target is null and
	 *                       will be set to the first object in the result chain, or
	 *                       to the empty artifact if no instances are created. In
	 *                       the end the method will always return a non-null
	 *                       artifact.
	 * @param runtimeContext the runtime context
	 * @return the finalized observation data. TODO when an instantiator returns no
	 *         instances, should return an empty observation. Currently it returns
	 *         null.
	 * @throws KlabException
	 */
	public IArtifact compute(IArtifact target, IRuntimeScope runtimeContext) throws KlabException {

		
		this.currentContext = runtimeContext;
		this.status.set(1);
		this.startComputation.set(System.currentTimeMillis());

		/*
		 * The contextualizer chain that implements the computation is specified by
		 * service calls, so it can survive dataflow serialization/deserialization.
		 */
		List<Pair<IContextualizer, IComputableResource>> computation = new ArrayList<>();
		Set<IArtifact> artifacts = new HashSet<>();

		/*
		 * this localizes the names in the context to those understood by this actuator
		 * and applies any requested mediation to the inputs. Target may be swapped for
		 * a mediator.
		 */
		IRuntimeScope ctx = setupContext(target, runtimeContext);

		for (Pair<IServiceCall, IComputableResource> service : computationStrategy) {

			if (runtimeContext.getMonitor().isInterrupted()) {
				return Observation.empty(getObservable(), runtimeContext);
			}

			IServiceCall function = service.getFirst();

			if (((ComputableResource) service.getSecond()).getModifiedParameters() != null) {
				function.getParameters().putAll(((ComputableResource) service.getSecond()).getModifiedParameters());
			}

			Object contextualizer = Extensions.INSTANCE.callFunction(service.getFirst(), ctx);
			if (contextualizer == null) {
				// this happens when a condition isn't met, so it's legal.
				continue;
			}
			if (!(contextualizer instanceof IContextualizer)) {
				// this isn't
				throw new KlabValidationException(
						"function " + service.getFirst().getName() + " does not produce a contextualizer");
			}
			computation.add(new Pair<>((IContextualizer) contextualizer, service.getSecond()));
		}

		IArtifact ret = target;

		/*
		 * Keep the latest layer for all artifacts involved here, indexed by name (use
		 * self_ for the actuator's target).
		 */
		Map<String, IArtifact> artifactTable = new HashMap<>();
		artifactTable.put("self_", target);

		/*
		 * run the contextualization strategy with the localized context. Each
		 * contextualizer may produce/require something else than the actuator's target
		 * (in case of explicit retargeting) or an intermediate version requiring a
		 * different type. We use the context's artifact table to keep track.
		 */
		for (Pair<IContextualizer, IComputableResource> contextualizer : computation) {

			/*
			 * FIXME: this keeps reusing the same ctx, which is probably wrong as it holds
			 * parameters from all computations (although they're overwritten so it's only a
			 * problem if one uses defaults that a previous one provides with a different
			 * meaning).
			 */
			IObservable indirectTarget = contextualizer.getSecond().getTarget();
			String targetId = "self_";
			IRuntimeScope context = ctx;

			if (indirectTarget != null) {
				targetId = indirectTarget.getName();
				/*
				 * TODO check if we should do this even for the normal target, so we don't carry
				 * previous parameters around.
				 */
				context = context.createChild(indirectTarget);
			}

			if (!artifactTable.containsKey(targetId)) {
				artifactTable.put(targetId, context.getArtifact(targetId));
			}

			/*
			 * run the contextualizer on its target. This may get a null and instantiate a
			 * new target artifact.
			 */
			artifactTable.put(targetId,
					runContextualizer(contextualizer.getFirst(),
							indirectTarget == null ? this.observable : indirectTarget, contextualizer.getSecond(),
							artifactTable.get(targetId), context, context.getScale()));

			/*
			 * if we have produced the artifact (through an instantiator), set it in the
			 * context.
			 */
			if (indirectTarget == null) {
				ret = artifactTable.get(targetId);
			} else if (!runtimeContext.getMonitor().isInterrupted()) {
				context.setData(indirectTarget.getName(), artifactTable.get(targetId));
			}

			if (model != null && !input && !artifacts.contains(ret)) {
				artifacts.add(ret);
				if (ret instanceof IObservation && !(ret instanceof StateLayer)) {
					// ACH creates problems later
					int i = 0;
					int toRemove = -1;
					for (IObservation o : this.products) {
						if (o.getObservable().getName().equals(((IObservation) ret).getObservable().getName())) {
							// added before: can only happen if this computation transformed it, so remove
							// it.
							toRemove = i;
							break;
						}
						i++;
					}
					if (toRemove >= 0) {
						this.products.remove(toRemove);
					}
					this.products.add((IObservation) ret);
				}
			}

			/*
			 * include the computed resource in the report
			 */
			((Report) context.getReport()).include(contextualizer.getSecond());
		}

		if (runtimeContext.getMonitor().isInterrupted()) {
			this.status.set(3);
			this.currentContext = null;
			this.endComputation.set(System.currentTimeMillis());
			return ret;
		}

		if (!runtimeContext.getTargetArtifact().equals(ret)) {
			/*
			 * Computation has changed the artifact: reset into catalog unless it's a proxy
			 * artifact.
			 */
			if (!isProxy(target)) {
				runtimeContext.setData(((IObservation) target).getObservable().getName(), ret);
			}
		}

		// FIXME the original context does not get the indirect artifacts
		if (runtimeContext.getTargetArtifact() == null || !runtimeContext.getTargetArtifact().equals(ret)) {
			((IRuntimeScope) runtimeContext).setTarget(ret);
		}

		// add any artifact, including the empty artifact, to the provenance. FIXME the
		// provenance doesn't get the indirect artifacts. This
		// needs to store the full causal chain and any indirect observations.
		ctx.getProvenance().addArtifact(ret);

		/*
		 * If we're not importing a previously computed result, put outputs in product
		 * list and create configurations if any.
		 */
		if (!input) {

			// check out any that we escaped (built directly by actuators using the context)
			List<IObservation> secondary = new ArrayList<>();
			if (model != null) {
				for (int i = 0; i < model.getObservables().size(); i++) {
					IArtifact artifact = ctx.getArtifact(model.getObservables().get(i).getName());
					if (!artifacts.contains(artifact) && artifact instanceof IObservation) {
						secondary.add((IObservation) artifact);
					}
				}
			}

			// consolidate the lists, secondary first.
			if (!secondary.isEmpty()) {
				List<IObservation> primary = new ArrayList<>(this.products);
				this.products.clear();
				this.products.addAll(secondary);
				this.products.addAll(primary);
			}

			IConfiguration configuration = null;
			if (!ret.isEmpty() && (mode == Mode.INSTANTIATION || ret instanceof IState)) {
				/*
				 * check for configuration triggered, only if we just resolved a state or
				 * instantiated 1+ objects
				 */
				Pair<IConcept, Set<IObservation>> confdesc = Observables.INSTANCE
						.detectConfigurations((IObservation) ret, ctx.getContextObservation());

				if (confdesc != null) {

					ctx.getMonitor().info("emergent configuration "
							+ Concepts.INSTANCE.getDisplayName(confdesc.getFirst()) + " detected");

					configuration = ctx.newConfiguration(confdesc.getFirst(), confdesc.getSecond(),
							/* TODO metadata */ new Metadata());

					if (configuration != null) {
						this.products.add(configuration);
					}
				}
			}
		}

		/*
		 * when computation is finished, pass all annotations from the models to the
		 * context, so it can execute any post-contextualization actions.
		 */
		for (IAnnotation annotation : annotations) {
			ctx.processAnnotation(annotation);
		}

		this.currentContext = null;
		this.endComputation.set(System.currentTimeMillis());
		this.status.set(2);

		return ret;
	}

	private boolean isProxy(IArtifact target) {
		return target instanceof RescalingState || target instanceof StateLayer;
	}

	@SuppressWarnings("unchecked")
	private IArtifact runContextualizer(IContextualizer contextualizer, IObservable observable,
			IComputableResource resource, IArtifact ret, IRuntimeScope ctx, IScale scale) throws KlabException {

		if (ctx.getMonitor().isInterrupted()) {
			return Observation.empty(getObservable(), ctx);
		}

		ISession session = ctx.getMonitor().getIdentity().getParentIdentity(ISession.class);
		DataflowState state = new DataflowState();
		state.setNodeId(ctx.getContextualizationStrategy().getComputationToNodeIdTable().get(resource.getDataflowId()));
		state.setStatus(Status.STARTED);
		state.setMonitorable(false); // for now
		session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
				IMessage.Type.DataflowStateChanged, state));

		/*
		 * This is what we get as the original content of self, which may be null or an
		 * empty state, or contain the result of the previous computation, including
		 * those that create state layers of a different type. This is the one to use as
		 * INPUT for computations involving self.
		 */
		IArtifact self = ret;

		if (ret instanceof IState) {
			/*
			 * Establish the container for the output: switch the storage in the state to
			 * the type needed in the compute chain, creating a layer if necessary. This is
			 * the layer to WRITE INTO.
			 */
			ret = ((IState) ret).as(contextualizer.getType());
		}

		if (contextualizer instanceof IStateResolver) {

			/*
			 * pass the distributed computation to the runtime provider for possible
			 * parallelization instead of hard-coding a loop here.
			 * 
			 * TODO CHECK THE USE OF AT() - CALLED FROM setupContext already has this
			 * applied
			 */
			ret = Klab.INSTANCE.getRuntimeProvider().distributeComputation((IStateResolver) contextualizer,
					(IState) ret, addParameters(ctx, self, resource), scale/* .at(Time.INITIALIZATION) */);

		} else if (contextualizer instanceof IResolver) {
			ret = ((IResolver<IArtifact>) contextualizer).resolve(ret, addParameters(ctx, ret, resource));
		} else if (contextualizer instanceof IInstantiator) {

			List<IObjectArtifact> objects = ((IInstantiator) contextualizer).instantiate(observable,
					addParameters(ctx, self, resource));

			/*
			 * Instantiators that act as sorters or filters will return a legitimate null,
			 * meaning "I've done my job, just ignore my output".
			 */
			if (objects != null) {

				for (IObjectArtifact object : objects) {

					/*
					 * if artifact has been filtered out, remove from structure (if there) and
					 * continue
					 */
					if (object instanceof ObservedArtifact && ((ObservedArtifact) object).isMarkedForDeletion()) {
						ctx.removeArtifact(object);
						continue;
					}

					((Artifact) ret).chain(object);
				}
				if (ret.groupSize() == 0) {
					// manually add the empty artifact to the structure; this is not done when a
					// group is created.
					ctx.link(ctx.getContextObservation(), ret);
				}
			}
		} else if (contextualizer instanceof IPredicateClassifier) {

			/*
			 * these are filters, so ret must be filled in already
			 */
			IConcept abstractPredicate = Observables.INSTANCE
					.getBaseObservable(((ComputableResource) resource).getOriginalObservable().getType());
			IConcept targetPredicate = ((Observable) ((ComputableResource) resource).getOriginalObservable())
					.getTargetPredicate();

			boolean ok = ((IPredicateClassifier<?>) contextualizer).initialize((IObjectArtifact) ret, abstractPredicate,
					targetPredicate, ctx);

			if (ok) {

				for (IArtifact target : ret) {

					@SuppressWarnings("rawtypes")
					IConcept c = ((IPredicateClassifier) contextualizer).classify(abstractPredicate,
							(IDirectObservation) target, ctx);
					if (c != null) {
						// attribute and resolve
						ctx.newPredicate((IDirectObservation) target, c);
					}
				}
			}

			/*
			 * Tell the observation group to revise its situation and enqueue any
			 * modification messages.
			 */
			((Observation) ret).evaluateChanges();

			/*
			 * ensure we return a view if that's necessary
			 */
			ret = ctx.getObservationGroupView((Observable) observable, (IObservation) ret);

		} else if (contextualizer instanceof IPredicateResolver) {

			/*
			 * This is called from a dataflow meant to resolve the attribute, so ret is the
			 * observation being characterized and the attribute is there because
			 * createTarget() has added it.
			 */
			IConcept predicate = Observables.INSTANCE.getBaseObservable(observable.getType());
			if (!((IPredicateResolver<IDirectObservation>) contextualizer).resolve(predicate, (IDirectObservation) ret,
					ctx)) {
				// strip the attribute that the classifier added
				((DirectObservation) ret).removePredicate(predicate);
			}
			((Observation) ret).evaluateChanges();
		}

		/**
		 * Insert any text part that the contextualizer makes available for the
		 * documentation.
		 */
		if (contextualizer instanceof IDocumentationProvider) {
			for (IDocumentationProvider.Item item : ((IDocumentationProvider) contextualizer).getDocumentation()) {
				((Report) ctx.getReport()).addTaggedText(item);
			}
		}

		state.setStatus(Status.FINISHED);
		session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
				IMessage.Type.DataflowStateChanged, state));

		return ret;
	}

	/**
	 * Set the call parameters, if any, into the context data so that they can be
	 * found by the contextualizer.
	 * 
	 * @param ctx
	 * @param self   the current artifact which will be set as "self" in the
	 *               context. May be the target or a layer of the target.
	 * @param second
	 * @return
	 */
	private IRuntimeScope addParameters(IRuntimeScope ctx, IArtifact self, IComputableResource resource) {
		IRuntimeScope ret = ctx.copy();
		if (self != null) {
			ret.replaceTarget(self);
			ret.set("self", self);
		}
		ret.setModel(model);
		for (String name : resource.getParameters().keySet()) {
			ret.set(name, resource.getParameters().get(name));
		}
		return ret;
	}

	private IRuntimeScope setupContext(IArtifact target, final IRuntimeScope runtimeContext) throws KlabException {

		IRuntimeScope ret = runtimeContext.copy();

		// compile mediators
		List<Pair<IContextualizer, IComputableResource>> mediation = new ArrayList<>();
		for (Pair<IServiceCall, IComputableResource> service : mediationStrategy) {
			Object contextualizer = Extensions.INSTANCE.callFunction(service.getFirst(), runtimeContext);
			if (!(contextualizer instanceof IContextualizer)) {
				throw new KlabValidationException(
						"function " + service.getFirst().getName() + " does not produce a contextualizer");
			}
			mediation.add(new Pair<>((IContextualizer) contextualizer, service.getSecond()));
		}

		for (IActuator input : getActuators()) {
			if (ret.getArtifact(input.getName()) != null) {
				// no effect if not aliased
				ret.rename(input.getName(), input.getAlias());
				/*
				 * scan mediations and apply them as needed
				 */
				for (Pair<IContextualizer, IComputableResource> mediator : mediation) {

					String targetArtifactId = mediator.getSecond().getMediationTargetId() == null ? null
							: mediator.getSecond().getMediationTargetId();

					if (targetArtifactId.equals(input.getAlias())) {
						IArtifact artifact = ret.getArtifact(targetArtifactId);
						/*
						 * TODO (I think): if we have own coverage, must reinterpret the artifact
						 * through the new scale.
						 */
						IArtifact mediated = runContextualizer(mediator.getFirst(), this.observable,
								mediator.getSecond(), artifact, ret, ret.getScale());
						ret.setData(targetArtifactId, mediated);
					}
				}
			}
		}

		return ret;
	}

	public String toString() {
		return "<" + getName() + ((getAlias() != null && !getAlias().equals(getName())) ? " as " + getAlias() : "")
				+ " [" + (computationStrategy.size() + mediationStrategy.size()) + "]>";
	}

	/**
	 * Reconstruct or return the source code for this actuator.
	 * 
	 * @param offset
	 * @return
	 */
	protected String encode(int offset) {
		String ofs = StringUtils.repeat(" ", offset);
		String ret = "";
		if (!isPartition()) {
			ret = ofs + "@semantics('" + getObservable().getDeclaration() + "')\n";
		}
		return ret + ofs + (input ? "import " : "") + (isPartition() ? "partition" : getType().name().toLowerCase())
				+ " " + getName() + encodeBody(offset, ofs);
	}

	public boolean isPartition() {
		return partitionedTarget != null;
	}

	public void setPartitionedTarget(String targetId) {
		this.partitionedTarget = targetId;
	}

	public String getPartitionedTarget() {
		return this.partitionedTarget;
	}

	public List<Pair<IServiceCall, IComputableResource>> getMediationStrategy() {
		return mediationStrategy;
	}

	public List<Pair<IServiceCall, IComputableResource>> getComputationStrategy() {
		return computationStrategy;
	}

	protected String encodeBody(int offset, String ofs) {

		boolean hasBody = actuators.size() > 0 || computationStrategy.size() > 0 || mediationStrategy.size() > 0
				|| mode == Mode.RESOLUTION;

		String ret = "";

		if (hasBody) {

			ret = " {\n";

			for (IActuator actuator : getSortedChildren(this)) {
				ret += ((Actuator) actuator).encode(offset + 3) + "\n";
			}

			int cout = mediationStrategy.size() + computationStrategy.size();
			int nout = 0;
			for (int i = 0; i < mediationStrategy.size(); i++) {
				ret += (nout == 0 ? (ofs + "   compute" + (cout < 2 ? " " : ("\n" + ofs + "     "))) : ofs + "     ")
						+ (mediationStrategy.get(i).getSecond().getMediationTargetId() == null ? ""
								: (mediationStrategy.get(i).getSecond().getMediationTargetId() + " >> "))
						+ mediationStrategy.get(i).getFirst().getSourceCode()
						+ (nout < mediationStrategy.size() - 1 || computationStrategy.size() > 0 ? "," : "") + "\n";
				nout++;
			}
			for (int i = 0; i < computationStrategy.size(); i++) {
				ret += (nout == 0 ? (ofs + "   compute" + (cout < 2 ? " " : ("\n" + ofs + "     "))) : ofs + "     ")
						+ computationStrategy.get(i).getFirst().getSourceCode()
						+ ((computationStrategy.get(i).getSecond().getTarget() == null
								|| computationStrategy.get(i).getSecond().getTarget().equals(observable)) ? ""
										: (" as " + computationStrategy.get(i).getSecond().getTarget().getName()))
						+ (nout < computationStrategy.size() - 1 ? "," : "") + "\n";
				nout++;
			}

			ret += ofs + "}";
		}

		if (getAlias() != null && !getAlias().equals(getName())) {
			ret += " as " + getAlias();
		}

		if (coverage != null && !coverage.isEmpty()) {
			List<IServiceCall> scaleSpecs = ((Scale) coverage).getKimSpecification();
			if (!scaleSpecs.isEmpty()) {
				ret += " over";
				for (int i = 0; i < scaleSpecs.size(); i++) {
					ret += " " + scaleSpecs.get(i).getSourceCode()
							+ ((i < scaleSpecs.size() - 1) ? (",\n" + ofs + "      ") : "");
				}
			}
		}

		return ret;
	}

	public static Actuator create(Dataflow dataflow, IResolutionScope.Mode mode) {
		Actuator ret = new Actuator();
		ret.mode = mode;
		ret.dataflow = dataflow;
		ret.session = dataflow.getSession();
		return ret;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Observable getObservable() {
		return observable;
	}

	public void setObservable(Observable observable) {
		this.observable = observable;
	}

	public INamespace getNamespace() {
		return namespace;
	}

	public void setNamespace(INamespace namespace) {
		this.namespace = namespace;
	}

	public IArtifact.Type getType() {
		return (mode == IResolutionScope.Mode.RESOLUTION && type == IArtifact.Type.OBJECT) ? IArtifact.Type.VOID : type;
	}

	public void setType(IArtifact.Type type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReference(boolean reference) {
		this.reference = reference;
	}

	public boolean isReference() {
		return reference;
	}

	public ISession getSession() {
		return this.session;
	}

	@Override
	public boolean isComputed() {
		return computationStrategy.size() > 0;
	}

	public boolean isMerging() {
		for (IActuator child : getActuators()) {
			if (((Actuator) child).isPartition()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<IComputableResource> getComputation() {
		return computedResources;
	}

	public boolean isExported() {
		return exported;
	}

	public List<IAnnotation> getAnnotations() {
		return annotations;
	}

	// coverage in an actuator is only set when it covers a sub-scale compared to
	// that of resolution.
	// The same field is used in a dataflow to define the overall coverage.
	public void setCoverage(Coverage coverage) {
		this.coverage = coverage;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	/*
	 * Called after target was created. Not meant for listeners but to complete the
	 * observation with its model-dependent information before the model goes away.
	 * 
	 * @param observation
	 */
	public void notifyNewObservation(IObservation observation) {

		/*
		 * transmit all annotations so we can use those that affect the runtime
		 */
		observation.getAnnotations().addAll(annotations);

		/*
		 * Assess if the computation implies having a datakey and if so, transmit it to
		 * the state.
		 */
		if (observation instanceof IState && computationStrategy.size() > 0) {
			IDataKey dataKey = findDataKey();
			if (dataKey != null && observation instanceof IKeyHolder) {
				((IKeyHolder) observation).setDataKey(dataKey);
			}
		}

	}

	/*
	 * Observations have a datakey if their last resource applied to the main
	 * observable is a lookup table producing concepts or a classification. They
	 * also have it if the main observable is a transformation of a dependency that
	 * does.
	 */
	private IDataKey findDataKey() {

		if (computationStrategy.size() > 0) {
			IComputableResource lastResource = computationStrategy.get(computationStrategy.size() - 1).getSecond();
			if (lastResource.getClassification() != null || lastResource.getAccordingTo() != null) {
				return ((ComputableResource) lastResource).getValidatedResource(IClassification.class);
			} else if (lastResource.getLookupTable() != null) {
				if (((ComputableResource) lastResource).getValidatedResource(LookupTable.class).isKey()) {
					return ((ComputableResource) lastResource).getValidatedResource(ILookupTable.class);
				}
			}
		}

		IDataKey ret = null;
//		for (IActuator actuator : actuators) {
//			if (((Actuator) actuator).observable.getType().equals(observable.getType())) {
//				ret = ((Actuator) actuator).findDataKey();
//				break;
//			}
//		}
		return ret;

	}

	public void setInput(boolean b) {
		this.input = b;
	}

	@Override
	public boolean isInput() {
		return input;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_actuatorId == null) ? 0 : _actuatorId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actuator other = (Actuator) obj;
		if (_actuatorId == null) {
			if (other._actuatorId != null)
				return false;
		} else if (!_actuatorId.equals(other._actuatorId))
			return false;
		return true;
	}

	public String getId() {
		return _actuatorId;
	}

	public String getDataflowId() {
		return getId();
	}

	public void setDataflowId(String dataflowId) {
		_actuatorId = dataflowId;
	}

	public IResolutionScope.Mode getMode() {
		return this.mode;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Model getModel() {
		return this.model;
	}

	public Dataflow getDataflow() {
		return this.dataflow;
	}

	public void setMergedScale(Scale scale) {
		this.mergedScale = scale;
	}

	public Scale getScale() {
		return mergedScale;
	}

	/**
	 * Find the actuator with the given name. Call it on the dataflow for the full
	 * experience.
	 * 
	 * @param name
	 * @return
	 */
	public Actuator getActuator(String name) {
		if (this.name.equals(name)) {
			return this;
		}
		for (IActuator actuator : getActuators()) {
			Actuator ret = ((Actuator) actuator).getActuator(name);
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}

	public List<Actuator> dependencyOrder() {
		List<Actuator> ret = new ArrayList<>();
		_dependencyOrder(this, ret, new HashSet<Actuator>(), buildCatalog());
		return ret;
	}

	/*
	 * build a catalog of all the non-reference actuators descending from this one
	 */
	private Map<String, Actuator> buildCatalog() {
		Map<String, Actuator> catalog = new HashMap<>();
		_buildCatalog(this, catalog);
		return catalog;
	}

	private void _buildCatalog(Actuator actuator, Map<String, Actuator> catalog) {

		if (!actuator.isReference()) {
			catalog.put(actuator.getName(), actuator);
		}
		for (IActuator child : actuator.getActuators()) {
			_buildCatalog((Actuator) child, catalog);
		}
	}

	private void _dependencyOrder(Actuator actuator, List<Actuator> ret, Set<Actuator> added,
			Map<String, Actuator> catalog) {

		if (actuator.isReference()) {
			actuator = catalog.get(actuator.getName());
			if (actuator == null) {
				return;
			}
		}

		boolean add = !added.contains(actuator);

		for (IActuator child : getSortedChildren(actuator)) {
			_dependencyOrder((Actuator) child, ret, added, catalog);
		}

		if (add && !added.contains(actuator)) {
			ret.add(actuator);
			added.add(actuator);
		}
	}

	/*
	 * Return our children in the original order; if they're partitions, sort them
	 * by increasing priority (the opposite of their natural order) so that the
	 * highest-priority computes last, just in case overlaps happen.
	 * 
	 * Note: All partitions of the same observable must go after the dependencies
	 * 
	 * @param actuator
	 * 
	 * @return
	 */
	private List<IActuator> getSortedChildren(Actuator actuator) {
		List<IActuator> ret = new ArrayList<>();
		List<IActuator> partitions = new ArrayList<>();
		for (IActuator act : actuator.getActuators()) {
			if (((Actuator) act).observable.equals(actuator.observable)) {
				partitions.add(act);
			} else {
				ret.add(act);
			}
		}
		if (partitions.size() > 1) {
			partitions.sort(new Comparator<IActuator>() {

				@Override
				public int compare(IActuator o1, IActuator o2) {
					int o1priority = ((Actuator) o1).priority;
					int o2priority = ((Actuator) o2).priority;
					return Integer.compare(o2priority, o1priority);
				}
			});
		}

		ret.addAll(partitions);

		return ret;
	}

	/*
	 * Notify all computed artifacts on the message bus. Compute() may not have been
	 * called if there is no computation, so if we have no artifacts check first if
	 * we have a product with the same name (happens with countables when no
	 * resolver was found).
	 * 
	 * TODO ensure that @probe annotations are honored.
	 * 
	 * @param isMainObservable
	 */
	public void notifyArtifacts(boolean isMainObservable, IRuntimeScope context) {

		this.currentContext = context;

		if (Klab.INSTANCE.getMessageBus() == null || isPartition()
				|| context.getMonitor().getIdentity().getParentIdentity(ITaskTree.class).isChildTask()) {
			return;
		}

		String taskId = context.getMonitor().getIdentity().getId();
		ISession session = context.getMonitor().getIdentity().getParentIdentity(ISession.class);

		if (this.products.isEmpty()) {
			if (context.getArtifact(this.name) != null) {
				this.products.add((IObservation) context.getArtifact(this.name));
			}
		}

		boolean isMain = false;
		for (IAnnotation annotation : annotations) {
			if (annotation.getName().equals("main")) {
				isMain = true;
				break;
			}
		}

		for (IObservation product : products) {

			boolean isNew = true;
			if (product instanceof ObservationGroup) {
				isNew = ((ObservationGroup) product).isNew();
			}

			if (isNew && context.getNotifiedObservations().contains(product.getId())) {
				continue;
			}

			context.getNotifiedObservations().add(product.getId());

			// parent is always getContext() because these notifications aren't sent beyond
			// level 0

			if (isNew) {
				IObservationReference observation = Observations.INSTANCE.createArtifactDescriptor(product,
						product.getContext(), context.getScale().initialization(), 0, isMainObservable || isMain).withTaskId(taskId);

				session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
						IMessage.Type.NewObservation, observation));

				((Report) context.getReport()).include(observation);
			} else {

				// TODO notify a change in an observation group, if any happened

			}

			if (product instanceof ObservationGroup) {
				((ObservationGroup) product).setNew(false);
			}
		}

		/*
		 * when all is computed, reuse the context to render the documentation
		 * templates.
		 */
		for (IDocumentation doc : documentation) {
			for (IDocumentation.Template template : doc.get(Trigger.DEFINITION)) {
				((Report) context.getReport()).include(template, context);
			}
		}

		this.currentContext = null;
	}

	public IContextualizationScope getCurrentContext() {
		if (currentContext == null) {
			return new SimpleRuntimeScope(this);
		}
		return currentContext;
	}

	public boolean isFilter() {
		return observable.getDescription() == IActivity.Description.CHARACTERIZATION
				|| observable.getDescription() == IActivity.Description.CLASSIFICATION;
	}

	/**
	 * Set things up to use the filter model compiled into the passed actuator. The
	 * actuator sorting strategy ensures filters are called last.
	 * 
	 * @param filter
	 * @param existingActuators
	 */
	public void adoptFilter(Actuator filter, Map<String, Actuator> existingActuators) {

		/*
		 * the observable is in the primary actuator, reference or not
		 */
		IObservable filtered = filter.observable.getFilteredObservable();

		/*
		 * match it to one of our dependencies. If not there, we have a screwup.
		 */
		for (IActuator actuator : actuators) {
			if (((Actuator) actuator).observable.canResolve((Observable) filtered)) {
				filtered = ((Actuator) actuator).observable;
				break;
			}
		}

		if (filter.isReference()) {
			// switch to original actuator
			filter = existingActuators.get(filter.getReferenceName());
		}

		if (filter == null || !existingActuators.containsKey(filter.getReferenceName())) {
			// should never happen - remove when we're certain that it doesn't indeed
			throw new KlabInternalErrorException("UNRESOLVED FILTER REFERENCE!");
		}

		/*
		 * adopt any dependencies from the filter; if the dependency exists in the
		 * passed catalog and we don't already have it, compile in a reference to it,
		 * otherwise put it in here.
		 */
		for (IActuator dependency : filter.actuators) {
			if (hasDependency(dependency)) {
				continue;
			}
			if (!((Actuator) dependency).isReference() && existingActuators.containsKey(dependency.getName())
					&& !haveActuatorNamed(dependency.getName())) {
				dependency = ((Actuator) dependency).getReference();
			}
			this.actuators.add(dependency);
		}

		// compile in all mediations as they are
		for (Pair<IServiceCall, IComputableResource> mediator : filter.mediationStrategy) {
			this.mediationStrategy.add(mediator);
		}

		/*
		 * compile in all filter computations, making a copy and ensuring the target is
		 * our filtered observable. These can only be filters by virtue of validation.
		 */
		for (Pair<IServiceCall, IComputableResource> computation : filter.computationStrategy) {
			this.computationStrategy.add(new Pair<>(setFilteredArgument(computation.getFirst(), filtered.getName()),
					setFilteredArgument(computation.getSecond(), filtered.getName())));
		}

	}

	private boolean hasDependency(IActuator dependency) {
		for (IActuator actuator : actuators) {
			if (((Actuator) actuator).getObservable().canResolve(((Actuator) dependency).observable)) {
				return true;
			}
		}
		return false;
	}

	private KimServiceCall setFilteredArgument(IServiceCall function, String filteredArgument) {

		IPrototype p = Extensions.INSTANCE.getPrototype(function.getName());
		if (p != null && p.isFilter()) {
			String artifactArg = null;
			for (Argument argument : p.listImports()) {
				artifactArg = argument.getName();
				break; // yes, break
			}
			if (artifactArg != null) {
				function = ((KimServiceCall) function).copy();
				function.getParameters().put(artifactArg, filteredArgument);
			}
		}
		return (KimServiceCall) function;
	}

	private IComputableResource setFilteredArgument(IComputableResource resource, String filteredArgument) {
		if (resource.getServiceCall() != null) {
			resource = ((ComputableResource) resource).copy();
			((ComputableResource) resource)
					.setServiceCall(setFilteredArgument(resource.getServiceCall(), filteredArgument));
		}
		return resource;
	}

	public Actuator getReference() {
		Actuator ret = new Actuator();
		ret.name = this.name;
		ret.alias = this.alias;
		ret.referenceName = this.referenceName;
		ret.reference = true;
		ret.type = this.type;
		ret.observable = this.observable;
		ret.namespace = this.namespace;
		ret.session = this.session;
		ret.mode = this.mode;
		// ret.partitionedTarget = this.partitionedTarget;
		return ret;
	}

	private boolean haveActuatorNamed(String name) {
		for (IActuator actuator : actuators) {
			if (actuator.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The name of the observable of the model that generated this. If it's a
	 * multi-model partitioning actuator, keep the name of the observable.
	 * 
	 * @return
	 */
	public String getReferenceName() {
		return this.referenceName;
	}

	public void setReferenceName(String name) {
		this.referenceName = name;
	}

	public Actuator withAlias(String alias) {
		this.alias = alias;
		return this;
	}
}
