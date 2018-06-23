package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
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
import org.integratedmodelling.klab.components.runtime.observations.ObservedArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;

public class Actuator implements IActuator {

	protected String name;
	private String alias;
	private INamespace namespace;
	private Observable observable;
	protected Coverage coverage;
	private IKdlActuator.Type type;
	List<IActuator> actuators = new ArrayList<>();
	Date creationTime = new Date();
	private boolean createsObservation;
	private boolean reference;
	private boolean exported;

	// this is only for the API
	private List<IComputableResource> computedResources = new ArrayList<>();
	// we store the annotations from the model to enable probes or other
	// non-semantic options
	private List<IKimAnnotation> annotations = new ArrayList<>();

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

	// public Actuator(IMonitor monitor) {
	//// this.monitor = monitor;
	// }

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
	 *            instances are created.
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
			if (!(contextualizer instanceof IContextualizer)) {
				throw new KlabValidationException(
						"function " + service.getFirst().getName() + " does not produce a contextualizer");
			}
			computation.add(new Pair<>((IContextualizer) contextualizer, service.getSecond()));
		}

		IArtifact ret = target;

		/*
		 * run the contextualization strategy with the localized context. Each
		 * contextualizer may produce/require something else than the actuator's target
		 * (in case of explicit retargeting) or an intermediate version requiring a
		 * different type. We use the context's artifact table to keep track.
		 * 
		 * FIXME the final artifact (created before calling) may not be compatible with
		 * the intermediate ones (e.g. in classification).
		 */
		for (Pair<IContextualizer, IComputableResource> contextualizer : computation) {

			/*
			 * FIXME: this keeps reusing the same ctx, which is probably wrong as it holds
			 * parameters from all computations (although they're overwritten so it's only a
			 * problem if one uses defaults that a previous one provides with a different
			 * meaning).
			 */

			IObservable indirectTarget = contextualizer.getSecond().getTarget();

			/*
			 * Initial target will be null if the actuator is for an instantiator. We take
			 * it from the context as setupContext() may have swapped it for a rescaling
			 * mediator.
			 * 
			 * FIXME the initial target should be the empty artifact with instantiators.
			 */
			IArtifact targetArtifact = indirectTarget == null ? target : ctx.getArtifact(indirectTarget.getLocalName());

			/*
			 * run the contextualizer on its target. This may get a null and instantiate a
			 * new target artifact.
			 * 
			 * FIXME parameters are kept from previous contextualizers in ctx
			 */
			targetArtifact = runContextualizer(contextualizer.getFirst(),
					indirectTarget == null ? this.observable : indirectTarget, contextualizer.getSecond(),
					targetArtifact, ctx, ctx.getScale());

			/*
			 * if we have produced the artifact (through an instantiator), set it in the
			 * context.
			 */
			if (indirectTarget == null) {
				ret = targetArtifact;
			} else {
				ctx.setData(indirectTarget.getLocalName(), targetArtifact);
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
			 * Send the result to the session's channel, only for primary observations and
			 * with no children. TODO ensure that @probe annotations are honored: send the
			 * probed artifacts, and ensure they're not sent if not probed.
			 */
			ISession session = ctx.getMonitor().getIdentity().getParentIdentity(ISession.class);
			session.getMonitor()
					.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
							IMessage.Type.NewObservation, Observations.INSTANCE
									.createArtifactDescriptor((IObservation) ret, ctx.getContextObservation(), 0)));
		}

		/*
		 * when computation is finished, pass all annotations from the models to the
		 * context, so it can execute any post-contextualization actions.
		 */
		for (IKimAnnotation annotation : annotations) {
			ctx.processAnnotation(annotation);
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	private IArtifact runContextualizer(IContextualizer contextualizer, IObservable observable,
			IComputableResource resource, IArtifact ret, IRuntimeContext ctx, IScale scale) throws KlabException {

		if (contextualizer instanceof IStateResolver) {
			
			/*
			 * pass the distributed computation to the runtime provider for possible
			 * parallelization instead of hard-coding a loop here.
			 */
			ret = Klab.INSTANCE.getRuntimeProvider().distributeComputation((IStateResolver) contextualizer,
					(IState) ret, addParameters(ctx, resource), /*
																 * TODO CHECK THE USE OF AT() - CALLED FROM setupContext
																 * already has this applied
																 */ scale.at(ITime.INITIALIZATION));

		} else if (contextualizer instanceof IResolver) {
			ret = ((IResolver<IArtifact>) contextualizer).resolve(ret, addParameters(ctx, resource));
		} else if (contextualizer instanceof IInstantiator) {
			for (IObjectArtifact object : ((IInstantiator) contextualizer).instantiate(observable,
					addParameters(ctx, resource))) {
				if (ret == null) {
					ret = object;
				} else {
					((ObservedArtifact) ret).chain(object);
				}
			}
			if (ret == null) {
				// return an empty observation for this observable, so we know we made the
				// observation.
				ret = Klab.INSTANCE.getRuntimeProvider().createEmptyObservation(observable, ctx.getScale());
			}
		}

		return ret;
	}

	/**
	 * Set the call parameters, if any, into the context data so that they can be
	 * found by the contextualizer.
	 * 
	 * @param ctx
	 * @param second
	 * @return
	 */
	private IRuntimeContext addParameters(IRuntimeContext ctx, IComputableResource resource) {
		for (String name : resource.getParameters().keySet()) {
			ctx.set(name, resource.getParameters().get(name));
		}
		return ctx;
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
		return ofs + (reference ? "import " : "") + (isPartition() ? "partition" : getType().name().toLowerCase()) + " "
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

			// UNCOMMENT TO OUTPUT SEMANTICS - FIXME should be an annotation on top of the actuator
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

	public IKdlActuator.Type getType() {
		return type;
	}

	public void setType(IKdlActuator.Type type) {
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

	public boolean isStorageScalar(IScale scale) {
		// TODO inspect the indirectAdapters and check if we have any local
		// modifications
		return scale.size() == 1;
	}

	public boolean isStorageDynamic(IScale scale) {
		// TODO inspect the indirectAdapters and the observable semantics; check if we
		// have
		// any temporal
		// modifications
		return scale.isTemporallyDistributed();
	}

	public boolean computesRescaledState() {
		return observable.is(Type.QUALITY) && coverage != null;
	}

	public boolean isExported() {
		return exported;
	}

	public List<IKimAnnotation> getAnnotations() {
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
}
