package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.data.table.LookupTable;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;

public class Actuator implements IActuator {

	protected String name;
	private String alias;
	private INamespace namespace;
	private Observable observable;
	protected Coverage coverage;
	private IArtifact.Type type;
	List<IActuator> actuators = new ArrayList<>();
	Date creationTime = new Date();
	private boolean createsObservation;

	// reference means that this actuator is a stand-in for another in the same
	// dataflow
	private boolean reference;
	// input means that this actuator retrieves a pre-computed artifact from the
	// context
	private boolean input;
	// export is currently unused but can be used to tag some artifacts for an
	// output port
	private boolean exported;

	// this is only for the API
	private List<IComputableResource> computedResources = new ArrayList<>();
	// we store the annotations from the model to enable probes or other
	// non-semantic options
	private List<IAnnotation> annotations = new ArrayList<>();

	public void addComputation(IComputableResource resource) {
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

	private boolean definesScale;

	// if this is non-null, coverage is also non-null and the actuator defines a
	// partition of the named target artifact, covering our coverage only.
	private String partitionedTarget;
	/*
	 * when this is a partition, the priority reflects the ranking so that the
	 * highest ranked partial can be applied last
	 */
	private int priority = 0;

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
	 * @param target
	 *            the final artifact being computed. If this actuator handles an
	 *            instantiation, the passed target is null and will be set to the
	 *            first object in the result chain, or to the empty artifact if no
	 *            instances are created. In the end the method will always return a
	 *            non-null artifact.
	 * @param runtimeContext
	 *            the runtime context
	 * @return the finalized observation data. TODO when an instantiator returns no
	 *         instances, should return an empty observation. Currently it returns
	 *         null.
	 * @throws KlabException
	 */
	public IArtifact compute(IArtifact target, IRuntimeContext runtimeContext) throws KlabException {

		/*
		 * The contextualizer chain that implements the computation is specified by
		 * service calls, so it can survive dataflow serialization/deserialization.
		 */
		List<Pair<IContextualizer, IComputableResource>> computation = new ArrayList<>();

		/*
		 * this localizes the names in the context to those understood by this actuator
		 * and applies any requested mediation to the inputs. Target may be swapped for
		 * a mediator.
		 */
		IRuntimeContext ctx = setupContext(target, runtimeContext, ITime.INITIALIZATION);

		for (Pair<IServiceCall, IComputableResource> service : computationStrategy) {

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
			IRuntimeContext context = ctx;

			if (indirectTarget != null) {
				targetId = indirectTarget.getLocalName();
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
			} else {
				context.setData(indirectTarget.getLocalName(), artifactTable.get(targetId));
			}
		}

		// FIXME the original context does not get the indirect artifacts
		if (runtimeContext.getTargetArtifact() == null) {
			((IRuntimeContext) runtimeContext).setTarget(ret);
		}

		// add any artifact, including the empty artifact, to the provenance. FIXME the
		// provenance doesn't get the indirect artifacts. This
		// needs to store the full causal chain and any indirect observations.
		ctx.getProvenance().addArtifact(ret);

		if (Klab.INSTANCE.getMessageBus() != null
				&& !ctx.getMonitor().getIdentity().getParentIdentity(ITaskTree.class).isChildTask()) {

			/*
			 * If we're not importing a previously computed result, send the artifact to the
			 * session's channel, only for primary observations and with all children. TODO
			 * ensure that @probe annotations are honored: send the probed artifacts, and
			 * ensure they're not sent if not probed.
			 */
			if (!input) {

				boolean isMain = false;
				for (IAnnotation annotation : annotations) {
					if (annotation.getName().equals("main")) {
						isMain = true;
						break;
					}
				}

				IObservation notifiable = (IObservation) (ret instanceof ObservationGroup && ret.groupSize() > 0
						? ret.iterator().next()
						: ret);
				ISession session = ctx.getMonitor().getIdentity().getParentIdentity(ISession.class);
				session.getMonitor()
						.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
								IMessage.Type.NewObservation,
								Observations.INSTANCE
										.createArtifactDescriptor(notifiable,
												ctx.getContextObservation().equals(notifiable) ? null
														: ctx.getContextObservation(),
												ITime.INITIALIZATION, -1, isMain)
										.withTaskId(ctx.getMonitor().getIdentity().getId())));
			}
		}

		/*
		 * when computation is finished, pass all annotations from the models to the
		 * context, so it can execute any post-contextualization actions.
		 */
		for (IAnnotation annotation : annotations) {
			ctx.processAnnotation(annotation);
		}

		/*
		 * when all is computed, reuse the context to render the documentation
		 * templates.
		 */
		for (IDocumentation doc : documentation) {
			for (IDocumentation.Template template : doc.get(Trigger.DEFINITION)) {
				runtimeContext.getReport().addSection(template.compile(ctx));
			}
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	private IArtifact runContextualizer(IContextualizer contextualizer, IObservable observable,
			IComputableResource resource, IArtifact ret, IRuntimeContext ctx, IScale scale) throws KlabException {

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
					(IState) ret, addParameters(ctx, self, resource), scale.at(ITime.INITIALIZATION));

		} else if (contextualizer instanceof IResolver) {
			ret = ((IResolver<IArtifact>) contextualizer).resolve(ret, addParameters(ctx, ret, resource));
		} else if (contextualizer instanceof IInstantiator) {
			for (IObjectArtifact object : ((IInstantiator) contextualizer).instantiate(observable,
					addParameters(ctx, self, resource))) {
				((Artifact) ret).chain(object);
			}
		}

		return ret;
	}

	/**
	 * Set the call parameters, if any, into the context data so that they can be
	 * found by the contextualizer.
	 * 
	 * @param ctx
	 * @param self
	 *            the current artifact which will be set as "self" in the context.
	 *            May be the target or a layer of the target.
	 * @param second
	 * @return
	 */
	private IRuntimeContext addParameters(IRuntimeContext ctx, IArtifact self, IComputableResource resource) {
		IRuntimeContext ret = ctx.copy();
		if (self != null) {
			ret.replaceTarget(self);
			ret.set("self", self);
		}
		for (String name : resource.getParameters().keySet()) {
			ret.set(name, resource.getParameters().get(name));
		}
		return ret;
	}

	private IRuntimeContext setupContext(IArtifact target, final IRuntimeContext runtimeContext, ILocator locator)
			throws KlabException {

		IRuntimeContext ret = runtimeContext.copy();
		IScale scale = ret.getScale().at(locator);

		/*
		 * if we're subsetting the scale, create the new scale as an intersection and
		 * reinterpret any existing state through it.
		 */
		if (this.coverage != null) {
			scale = scale.merge(this.coverage, LogicalConnector.INTERSECTION);
			if (target instanceof IState) {
				target = Observations.INSTANCE.getStateView((IState) target, scale, ret);
			}
		}

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

		ret.setTarget(target);
		ret.setScale(scale);

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
		return ofs + (input ? "import " : "") + (isPartition() ? "partition" : getType().name().toLowerCase()) + " "
				+ getName() + encodeBody(offset, ofs);
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

	protected String encodeBody(int offset, String ofs) {

		boolean hasBody = actuators.size() > 0 || computationStrategy.size() > 0 || mediationStrategy.size() > 0
				|| createsObservation;

		String ret = "";

		if (hasBody) {

			ret = " {\n";

			for (IActuator actuator : actuators) {
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
						+ (computationStrategy.get(i).getSecond().getTarget() == null ? ""
								: (" as " + computationStrategy.get(i).getSecond().getTarget().getLocalName()))
						+ (nout < computationStrategy.size() - 1 ? "," : "") + "\n";
				nout++;
			}

			// UNCOMMENT TO OUTPUT SEMANTICS - FIXME should be an annotation on top of the
			// actuator
			// to enable re-runs and the like
			// if (observable != null) {
			// ret += ofs + " " + "semantics " + getObservable().getDeclaration() + "\n";
			// }

			ret += ofs + "}";
		}

		if (getAlias() != null && !getAlias().equals(getName())) {
			ret += " as " + getAlias();
		}

		if (definesScale && coverage != null && !coverage.isEmpty()) {
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

	public static Actuator create() {
		return new Actuator();
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
		return type;
	}

	public void setType(IArtifact.Type type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreateObservation(boolean createObservation) {
		this.createsObservation = createObservation;
	}

	public void setReference(boolean reference) {
		this.reference = reference;
	}

	public boolean isReference() {
		return reference;
	}

	@Override
	public boolean isComputed() {
		return computationStrategy.size() > 0;
	}

	@Override
	public List<IComputableResource> getComputation() {
		return computedResources;
	}

	public void setDefinesScale(boolean definesScale) {
		this.definesScale = definesScale;
	}

	public boolean computesRescaledState() {
		return observable.is(Type.QUALITY) && coverage != null;
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

	public void notifyNewObservation(IObservation observation) {

		/*
		 * transmit all annotations so we can use those that affect the runtime
		 */
		observation.getAnnotations().addAll(annotations);

		/*
		 * add classification or lookup table as legend if our computations end with
		 * one.
		 */
		if (observation instanceof IState && computationStrategy.size() > 0) {
			IComputableResource lastResource = computationStrategy.get(computationStrategy.size() - 1).getSecond();
			if (lastResource.getClassification() != null || lastResource.getAccordingTo() != null) {
				if (observation instanceof IKeyHolder) {
					((IKeyHolder) observation).setDataKey(
							((ComputableResource) lastResource).getValidatedResource(IClassification.class));
				}
			} else if (lastResource.getLookupTable() != null) {
				if (observation instanceof IKeyHolder
						&& ((ComputableResource) lastResource).getValidatedResource(LookupTable.class).isKey()) {
					((IKeyHolder) observation)
							.setDataKey(((ComputableResource) lastResource).getValidatedResource(ILookupTable.class));
				}
			}
		}

	}

	public void setInput(boolean b) {
		this.input = b;
	}

	@Override
	public boolean isInput() {
		return input;
	}
}
