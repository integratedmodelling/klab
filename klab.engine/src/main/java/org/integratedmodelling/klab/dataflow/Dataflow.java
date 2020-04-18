package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IContextualizable.InteractiveParameter;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Interaction;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservedArtifact;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Annotation;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.rest.DataflowReference;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.klab.utils.TypeUtils;
import org.integratedmodelling.klab.utils.Utils;

/**
 * The semantically aware implementation of {@link IDataflow}, built by the
 * k.LAB runtime as a result of a semantic resolution. Its
 * {@link #run(IScale, IMonitor)} produces {@link IObservation observations}
 * unless the dataflow is {@link #isEmpty() empty}.
 * <p>
 * Each context has a single, hierarchically organized dataflow made up of
 * sub-dataflow. The root dataflow resolves the context (object resolvers are
 * void as they do not produce observations, the object type is reserved for
 * instantiators). Each actuator that instantiates objects also has the void
 * dataflow(s) that resolve them, plus any other actuator run in the context of
 * the instantiated objects. Successive observations add dataflows to the parent
 * actuator, according to the scope of each resolution, contributing to one
 * overall dataflow which is visualized to the user as it changes.
 * <p>
 * A matching implementation may be provided to run non-semantic workflows in
 * semantically unaware computation engines, or a translator could be used to
 * provide commodity semantics to use this one so that k.LAB servers can serve
 * indirectAdapters through URNs.
 * <p>
 * A dataflow is a void actuator in k.DL. A void actuator resolves the context
 * it's run into. When objects are created, the containing actuator may contain
 * the dataflow(s) to resolve it and/or specific qualities declared "within" the
 * object.
 * 
 * @author Ferd
 *
 */
public class Dataflow extends Actuator implements IDataflow<IArtifact> {

	private String description;
	private DirectObservation context;
	private ResolutionScope resolutionScope;
	private boolean primary = true;
	IDirectObservation relationshipSource;
	IDirectObservation relationshipTarget;

	// this could simply be the "dataflow" in the parent actuator but it's clearer
	// this way.
	private Dataflow parent;

	/*
	 * if true, we observe occurrents and we may need to upgrade a generic T context
	 * to a specific one.
	 */
	boolean hasOccurrents = false;
	// if true, we have one time step and occurrents, so we should autostart
	boolean autoStartTransitions = false;

	// execution parameters for user modification if running interactively
	private List<InteractiveParameter> fields = new ArrayList<>();
	private List<Pair<IContextualizable, List<String>>> resources = new ArrayList<>();
	private List<Pair<IAnnotation, List<String>>> annotations = new ArrayList<>();
	private IMetadata metadata;
	private Collection<IObservation> configurationTargets;

	class AnnotationParameterValue {

		String annotationId;
		String parameterName;
		String value;
		Type type;

		public AnnotationParameterValue(String id, String id2, String initialValue, Type type) {
			this.annotationId = id;
			this.parameterName = id2;
			this.value = initialValue;
			this.type = type;
		}
	}

	List<AnnotationParameterValue> annotationParameters = new ArrayList<>();
	private Scale resolutionScale;
	private boolean secondary;

	/*
	 * keep the IDs of the dataflows already merged (during run()) so that we only
	 * merge each dataflow once when reusing them for multiple instances.
	 */
	Set<String> dataflowIds = new HashSet<>();

	private Dataflow(Dataflow parent) {
		this.parent = parent;
	}

	public Dataflow(ISession session, Dataflow parent) {
		this.session = session;
		this.parent = parent;
	}

	/**
	 * If the dataflow is reused more than once, this must be called before any
	 * repeated execution.
	 */
	public void reset() {
		this.resolutionScale = null;
		resetScales();
	}

	@Override
	public IArtifact run(IScale scale, IMonitor monitor) throws KlabException {
		return run(scale, null, monitor);
	}

	/**
	 * Pass an actuator to use to register ourselves into.
	 * 
	 * @param scale
	 * @param parentComputation
	 * @param monitor
	 * @return
	 * @throws KlabException
	 */
	public IArtifact run(IScale scale, Actuator parentComputation, IMonitor monitor) throws KlabException {

		reset();

		/*
		 * we need the initialization scale for the dataflow but we must create our
		 * targets with the overall scale. Problem is, occurrent actuators must create
		 * their states using their own resolution if any is specified.
		 */
		if (actuators.size() == 0) {
			if (resolutionScope.getResolvedArtifact() != null) {
				return resolutionScope.getResolvedArtifact().getArtifact();
			}
			return Observation.empty();
		}

		/*
		 * Set the .partialScale field in all actuators that represent partitions of the
		 * context to reflect the portion of the actual scale they must cover.
		 */
		definePartitions(scale);

		if (session != null && session.isInteractive()) {
			/*
			 * collect all computables with interaction switched on and wait for user
			 * response before moving on.
			 * 
			 * TODO add annotation processing for models
			 */
			this.fields = new ArrayList<>();
			this.resources = new ArrayList<>();
			for (Actuator actuator : collectActuators()) {

				// ?= in observable annotations
				if (actuator.getModel() != null) {
					for (IObservable o : CollectionUtils.join(actuator.getModel().getObservables(),
							actuator.getModel().getDependencies())) {
						List<String> parameterIds = null;
						for (IAnnotation annotation : o.getAnnotations()) {
							for (InteractiveParameter parameter : Interaction.INSTANCE
									.getInteractiveParameters(annotation, o)) {
								if (parameterIds == null) {
									parameterIds = new ArrayList<>();
								}
								fields.add(parameter);
								parameterIds.add(parameter.getId());
								annotationParameters.add(new AnnotationParameterValue(((Annotation) annotation).getId(),
										parameter.getId(), parameter.getInitialValue(), parameter.getType()));
							}
							if (parameterIds != null) {
								this.annotations.add(new Pair<>(annotation, parameterIds));
							}
						}
					}

					// interactive computations
					for (IContextualizable computable : actuator.getComputation()) {
						List<String> parameterIds = null;
						for (InteractiveParameter parameter : Interaction.INSTANCE.getInteractiveParameters(computable,
								actuator.getModel())) {
							if (parameterIds == null) {
								parameterIds = new ArrayList<>();
							}
							fields.add(parameter);
							parameterIds.add(parameter.getId());
						}
						if (parameterIds != null) {
							this.resources.add(new Pair<>(computable, parameterIds));
						}
					}
				}
			}
			if (fields.size() > 0) {
				/*
				 * Issue request, wait for answer and reset parameters in the computation.
				 * Method returns all interactive observable annotation parameters for
				 * recording.
				 */
				Collection<Triple<String, String, String>> values = Interaction.INSTANCE
						.submitParameters(this.resources, this.fields, session);

				if (values == null) {
					return null;
				}

				for (Triple<String, String, String> annotationValue : values) {
					AnnotationParameterValue aval = getAnnotationValueFor(annotationValue.getFirst(),
							annotationValue.getSecond());
					if (aval != null /* should never happen but implementation may change */) {
						aval.value = annotationValue.getThird();
					}
				}
			}
		}

		/*
		 * Initialization run, which will also schedule any further temporal actions.
		 * This is normally just one actuator. Children at the dataflow level could run
		 * in parallel, so have the runtime start futures for each child and chain the
		 * results when they come. This scenario is not possible at the moment so we
		 * spare the trouble of coding it in.
		 */
		IArtifact ret = null;
		for (IActuator actuator : actuators) {
			try {

				IArtifact data = Klab.INSTANCE.getRuntimeProvider()
						.compute(actuator, this, scale, resolutionScope/* , context */, monitor).get();
				if (ret == null) {
					ret = data;
				} else {
					((ObservedArtifact) ret).chain(data);
				}
			} catch (InterruptedException e) {
				return null;
			} catch (ExecutionException e) {
				throw new KlabContextualizationException(e);
			}
		}

		Dataflow rootDataflow = null;
		boolean added = false;
		if (parentComputation == null) {
			rootDataflow = this;
			((RuntimeScope) ((Observation) ret).getRuntimeScope()).setDataflow(this);
		} else {

			rootDataflow = (Dataflow) ((Observation) ret).getRuntimeScope().getDataflow();

			if (!rootDataflow.dataflowIds.contains(this.getName())) {

				added = true;
				rootDataflow.dataflowIds.add(this.getName());

				Actuator parent = parentComputation;

				// again, this is currently just one actuator
				for (IActuator actuator : actuators) {

					// I am the resolver
					if (actuator.getType() == Type.VOID) {
						parent.getActuators().add(actuator);
					} else if (parent.getType() != Type.VOID) {

						/*
						 * should be within an instantiator, which at this point has resolved its
						 * instances so it should have a void child with the same name
						 */
						for (IActuator pc : parent.getActuators()) {
							if (pc.getType() == Type.VOID && pc.getName().equals(parent.getName())) {
								parent = (Actuator) pc;
								break;
							}
						}
						if (parent.getType() == Type.VOID) {
							parent.getActuators().add(actuator);
						}
					}
				}
			}
		}

		if (added) {
			/*
			 * send dataflow after execution is finished. TODO add style elements or flags
			 * to make sure it's shown statically.
			 */
			session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
					IMessage.Type.DataflowCompiled, new DataflowReference(session.getMonitor().getIdentity().getId(),
							getKdlCode(), ContextualizationStrategy.getElkGraph(this))));
			System.out.println(rootDataflow.getKdlCode());
		}

		return ret;
	}

	private void definePartitions(IScale scale) {
		_definePartialScales(this, (Scale) scale);
	}

	private Scale _definePartialScales(Actuator actuator, Scale current) {

		if (actuator.getModel() != null) {

			Scale mcoverage = actuator.getModel().getCoverage(resolutionScope.getMonitor());
			if (!mcoverage.isEmpty() || actuator.isPartition()) {

				Scale coverage = mcoverage;
				if (actuator.isPartition()) {
					coverage = current.merge(mcoverage, LogicalConnector.INTERSECTION);

					// TODO MOVE BELOW (OUTSIDE THE IF) WHEN MERGING IS OK
					actuator.setMergedScale(coverage.merge(current));
				}

				/*
				 * merge in the current scale. The coverage of the current actuator defines the
				 * overall extents if any are set.
				 */

				if (actuator.isPartition()) {
					/*
					 * remove the part we handled so that the next will not cover it.
					 */
					current = current.merge(coverage, LogicalConnector.EXCLUSION);
				}
			}
		}

		for (IActuator child : actuator.getActuators()) {
			current = _definePartialScales((Actuator) child, current);
		}

		return current;
	}

	private List<Actuator> collectActuators() {
		List<Actuator> ret = new ArrayList<>();
		_collectActuators(actuators, ret);
		return ret;
	}

	private void _collectActuators(List<IActuator> actuators, List<Actuator> ret) {
		for (IActuator actuator : actuators) {
			ret.add((Actuator) actuator);
			_collectActuators(actuator.getActuators(), ret);
		}
	}

	/**
	 * If the parameters in a specified annotation have been changed by the user,
	 * return a new annotation with the new parameters.
	 * 
	 * Called by an observable's getAnnotations() when a runtime context is passed
	 * for contextualization of parameter.
	 * 
	 * @param annotation
	 * @return a new annotation or the same if parameters haven't changed.
	 */
	public IAnnotation parameterizeAnnotation(IAnnotation annotation) {
		boolean first = true;
		Annotation ret = (Annotation) annotation;
		for (AnnotationParameterValue av : getAnnotationValuesFor(((Annotation) annotation).getId())) {
			if (first) {
				ret = ret.copy();
			}
			ret.put(av.parameterName, TypeUtils.convert(av.value, Utils.getClassForType(av.type)));
		}
		return ret;
	}

	private Collection<AnnotationParameterValue> getAnnotationValuesFor(String annotationId) {
		List<AnnotationParameterValue> ret = new ArrayList<>();
		for (AnnotationParameterValue a : annotationParameters) {
			if (a.annotationId.equals(annotationId)) {
				ret.add(a);
			}
		}
		return ret;
	}

	private AnnotationParameterValue getAnnotationValueFor(String annotationId, String parameterId) {
		for (AnnotationParameterValue a : annotationParameters) {
			if (a.annotationId.equals(annotationId) && a.parameterName.equals(parameterId)) {
				return a;
			}
		}
		return null;
	}

	@Override
	protected String encode(int offset) {

		String ret = "";

		if (offset == 0) {
			ret += "@klab " + Version.CURRENT + "\n";
			ret += "@dataflow " + getName() + "\n";
			ret += "@author 'k.LAB resolver " + creationTime + "'" + "\n";
			if (getContext() != null) {
				ret += "@context " + getContext().getUrn() + "\n";
			}
			if (coverage != null && coverage.getExtentCount() > 0) {
				List<IServiceCall> scaleSpecs = ((Scale) coverage).getKimSpecification();
				if (!scaleSpecs.isEmpty()) {
					ret += "@coverage load_me_from_some_sidecar_file()";
					// TODO this can get huge and is transmitted over websockets, so can't put it here as is. Needs
					// supplemental material and a ref instead.
//					for (int i = 0; i < scaleSpecs.size(); i++) {
//						if (scaleSpecs.get(i) != null) {
//							ret += " " + scaleSpecs.get(i).getSourceCode()
//									+ ((i < scaleSpecs.size() - 1) ? (",\n" + "   ") : "");
//						}
//					}
					ret += "\n";
				}
			}
			ret += "\n";
		}

		for (IActuator actuator : actuators) {
			ret += ((Actuator) actuator).encode(offset) + "\n";
		}

		return ret;
	}

	/**
	 * Return the source code of the dataflow.
	 * 
	 * @return the source code as a string.
	 */
	@Override
	public String getKdlCode() {
		return encode(0);
	}

	@Override
	public ICoverage getCoverage() {
		return coverage;
	}

	public DirectObservation getContext() {
		return context;
	}

	public void setContext(DirectObservation context) {
		this.context = context;
	}

	public void setResolutionScope(ResolutionScope scope) {
		this.resolutionScope = scope;
	}

	public static Dataflow empty(Dataflow parent) {
		return new Dataflow(parent);
	}

	public static Dataflow empty(ResolutionScope scope, Dataflow parent) {
		Dataflow ret = new Dataflow(parent);
		ret.resolutionScope = scope;
		ret.session = scope.getSession();
		return ret;
	}

	/**
	 * Make a trivial dataflow with a single actuator that will create the passed
	 * observable target.
	 * 
	 * @param observable
	 * @param scope
	 * @return
	 */
	public static Dataflow empty(IObservable observable, String name, ResolutionScope scope, Dataflow parent) {

		Dataflow ret = new Dataflow(parent);
		ret.resolutionScope = scope;
		ret.session = scope.getSession();

		Actuator actuator = Actuator.create(ret, scope.getMode());
		actuator.setObservable((Observable) observable);
		actuator.setType(observable.getArtifactType());
		actuator.setNamespace(((ResolutionScope) scope).getResolutionNamespace());
		actuator.setName(name);
		actuator.setReferenceName(name);
		ret.getActuators().add(actuator);
		ret.setNamespace(actuator.getNamespace());

		return ret;
	}

	@Override
	public boolean isEmpty() {
		return actuators.size() == 0;
	}

	/**
	 * True if the dataflow is handling an API observation request. False if the
	 * request is to resolve an object instantiated by another dataflow.
	 * 
	 * @return
	 */
	public boolean isPrimary() {
//		return primary;
		return parent == null;
	}

//	public Dataflow setPrimary(boolean b) {
//		this.primary = b;
//		return this;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Dataflow withMetadata(IMetadata metadata) {
		this.metadata = metadata;
		return this;
	}

	public Dataflow connecting(IDirectObservation source, IDirectObservation target) {
		this.relationshipSource = source;
		this.relationshipTarget = target;
		return this;
	}

	/**
	 * Metadata may be added to the dataflow before computation to resolve states
	 * and/or add to the target observation as specified by the model.
	 * 
	 * @return
	 */
	public IMetadata getMetadata() {
		return metadata;
	}

	public IDirectObservation getRelationshipSource() {
		return relationshipSource;
	}

	public IDirectObservation getRelationshipTarget() {
		return relationshipTarget;
	}

	public Dataflow withConfigurationTargets(Collection<IObservation> targets) {
		this.configurationTargets = targets;
		return this;
	}

	public Collection<IObservation> getConfigurationTargets() {
		return this.configurationTargets;
	}

	@Override
	public IScale getResolutionScale() {
		if (this.resolutionScale == null && resolutionScope != null) {
			this.resolutionScale = resolutionScope.getScale();
			if (hasOccurrents && this.resolutionScale.getTime() != null) {
				ITime time = this.resolutionScale.getTime();
				if (time.isGeneric() || time.size() == 1) {

					if (time.getStart() == null || time.getEnd() == null) {
						throw new KlabContextualizationException(
								"cannot contextualize occurrents (processes and events) without a specified temporal extent");
					}

					// turn time into a 1-step grid (so size = 2)
					this.resolutionScale = Scale.substituteExtent(this.resolutionScale,
							((Time) time).upgradeForOccurrents());
				}

				// set the dataflow to autostart transitions if we only have one
				if (this.resolutionScale.getTime().size() >= 2) {
					autoStartTransitions = true;
				}
			}
		}
		return this.resolutionScale;
	}

	public void notifyOccurrents() {
		this.hasOccurrents = true;
	}

	public boolean isAutoStartTransitions() {
		return this.autoStartTransitions;
	}

	/**
	 * TODO/FIXME if withScope is called, it must be called before this one.
	 * 
	 * @param scale
	 * @return
	 */
	public Dataflow withScopeScale(IScale scale) {
		if (this.resolutionScope != null) {
			this.resolutionScope = this.resolutionScope.rescale(scale);
		}
		return this;
	}

	/**
	 * TODO this should create a new dataflow if we want concurrent execution of
	 * dataflows with different scopes. Also this MUST be called before
	 * withScopeScale if that is used.
	 * 
	 * @param scope
	 * @return
	 */
	public Dataflow withScope(ResolutionScope scope) {
		this.resolutionScope = scope;
		return this;
	}

	public ResolutionScope getResolutionScope() {
		return this.resolutionScope;
	}

	/*
	 * FIXME this shouldn't be necessary - use the hierarchy
	 */
	public void setSecondary(boolean b) {
		this.secondary = b;
	}

	/*
	 * FIXME this shouldn't be necessary - use the hierarchy. Also conflicts with
	 * isPrimary() in meaning.
	 */
	public boolean isSecondary() {
		return this.secondary;
	}

	public void reattributeActuators() {
		for (IActuator actuator : actuators) {
			reattributeActuator(actuator);
		}
	}

	private void reattributeActuator(IActuator actuator) {
		((Actuator) actuator).setDataflow(this);
		for (IActuator a : actuator.getActuators()) {
			reattributeActuator(a);
		}
	}

	public Dataflow withContext(IDirectObservation contextSubject) {
		this.context = (DirectObservation) contextSubject;
		return this;
	}

}
