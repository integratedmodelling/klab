package org.integratedmodelling.klab.resolution;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Scope;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.api.services.IObservationService;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ResolutionScope.Link;
import org.integratedmodelling.klab.rest.ModelReference;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;

/**
 * The resolver provides methods to find the observation strategy for any
 * {@link IResolvable} object. All state during resolution is held in the
 * associate {@link IResolutionScope}.
 * <p>
 * At the moment this does not correspond to any official API, although it is a
 * fundamental component. We should expose methods that return dataflows
 * directly, instead of coverages and scopes that are independently compiled
 * into dataflows. At that point this should become a IResolutionService.
 * 
 * @author ferdinando.villa
 *
 */
public class Resolver {

	private Dataflow parentDataflow;

	private Resolver(Dataflow parentDataflow) {
		this.parentDataflow = parentDataflow;
	}

	public static Resolver create(Dataflow parentDataflow) {
		return new Resolver(parentDataflow);
	}

	/**
	 * Implements the
	 * {@link IObservationService#resolve(String, ISession, String[])} method,
	 * exposed by {@link Observations}.
	 * 
	 * @param urn
	 * @param session
	 * @param scenarios
	 * @return a dataflow to compute the artifact identified by the urn.
	 * @throws KlabException
	 */
	public IDataflow<IArtifact> resolve(String urn, ISession session, String[] scenarios) throws KlabException {

		IKimObject object = Resources.INSTANCE.getModelObject(urn);
		if (!(object instanceof Observer)) {
			throw new IllegalArgumentException("URN " + urn + " does not specify an observation");
		}
		IMonitor monitor = session.getMonitor();
		String taskId = "local:task:" + session.getId() + ":" + object.getId();
		ResolutionScope scope = resolve((Observer) object, monitor, Arrays.asList(scenarios));
		if (scope.getCoverage().isRelevant()) {
			return Dataflows.INSTANCE.compile(taskId, scope, parentDataflow);
		}
		return Dataflow.empty(parentDataflow);
	}

	/**
	 * Implements the
	 * {@link IObservationService#resolve(String, ISubject, String[])} method,
	 * exposed by {@link Observations}.
	 * 
	 * @param urn
	 * @param context
	 * @param scenarios
	 * @return a dataflow, possibly empty.
	 * @throws KlabException
	 */
	public IDataflow<IArtifact> resolve(String urn, ISubject context, String[] scenarios) throws KlabException {

		IMonitor monitor = context.getMonitor();
		IResolvable resolvable = Resources.INSTANCE.getResolvableResource(urn, context.getScale());
		String taskId = "local:task:" + context.getId() + ":" + ""; // TODO encode resolvable in URN
		if (resolvable == null) {
			return Dataflow.empty(parentDataflow);
		}

		/*
		 * resolve and run
		 */
		ResolutionScope scope = resolve(resolvable,
				ResolutionScope.create((Subject) context, monitor, Arrays.asList(scenarios)));
		if (scope.getCoverage().isRelevant()) {
			return Dataflows.INSTANCE.compile(taskId, scope, parentDataflow);
		}

		return Dataflow.empty(parentDataflow);
	}

	/**
	 * Resolve the passed object in the passed parent scope, using the resolution
	 * strategy appropriate for the type.
	 * 
	 * @param resolvable
	 * @param parentScope
	 * @return the resolved scope
	 * @throws KlabException
	 */
	public ResolutionScope resolve(IResolvable resolvable, ResolutionScope parentScope) throws KlabException {

		ResolutionScope ret = null;

		if (resolvable instanceof Observable) {
			parentScope.setOriginalScope(
					((Observable) resolvable).getReferencedModel() == null ? Scope.OBSERVABLE : Scope.MODEL);
			ret = resolve((Observable) resolvable, parentScope,
					((Observable) resolvable).getDescriptionType().getResolutionMode());
		} else if (resolvable instanceof Model) {
			parentScope.setOriginalScope(Scope.MODEL);
			ret = resolve((Model) resolvable, parentScope);
		} else if (resolvable instanceof Observer) {
			parentScope.setOriginalScope(Scope.OBSERVER);
			ret = resolve((Observer) resolvable, parentScope);
		}

		if (ret != null) {

			if (ret.isOccurrent()) {

				/*
				 * visit the scope (building a list of ResolvedObservable for all qualities that
				 * may change) and resolve their change in parent scope
				 */

			}

			return ret;
		}

		return parentScope.empty();
	}

	/**
	 * Resolve a root observer to an acknowledged observation tree. This being an
	 * acknowledgement, coverage will always be 100% unless errors happen.
	 * 
	 * @param observer
	 * @param monitor
	 * @param scenarios
	 * @return the scope, with the new subject in it.
	 * @throws KlabException
	 */
	public ResolutionScope resolve(Observer observer, IMonitor monitor, Collection<String> scenarios)
			throws KlabException {

		ResolutionScope ret = ResolutionScope.create(observer, monitor, scenarios);
		if (resolve(observer.getObservable(), ret, Mode.RESOLUTION).getCoverage().isRelevant()) {
			return ret;
		}
		return ret.empty();
	}

	/**
	 * Resolve an observer in a previously existing context using passed mode and
	 * scale.
	 *
	 * @param observable
	 * @param parentScope
	 * @param mode
	 * @param scale
	 * @param model       the model that has started the resolution - usually the
	 *                    instantiator for the object being resolved.
	 * @return the merged scope
	 * @throws KlabException
	 */
	public ResolutionScope resolve(Observable observable, ResolutionScope parentScope, Mode mode, IScale scale,
			IModel model) throws KlabException {
		// TODO support model
		ResolutionScope ret = resolve(observable, parentScope.getChildScope(observable, mode, (Scale) scale, model),
				mode);
//		if (ret.getCoverage().isRelevant()) {
//			parentScope.merge(ret);
//		}
		return ret;
	}

	/**
	 * Resolve a relationship observable between two known subjects using passed
	 * scale
	 * 
	 * @param observable
	 * @param parentScope
	 * @param mode
	 * @param scale
	 * @return the merged scope
	 * @throws KlabException
	 */
	public ResolutionScope resolve(Observable observable, ResolutionScope parentScope, Subject source, Subject target,
			IScale scale, IModel upstreamModel) throws KlabException {

		ResolutionScope ret = resolve(observable,
				parentScope.getChildScope(observable, (Scale) scale, source, target, upstreamModel), Mode.RESOLUTION);
//		if (ret.getCoverage().isRelevant()) {
//			parentScope.merge(ret);
//		}
		return ret;
	}

	/**
	 * Resolve an observer in a previously existing context.
	 * 
	 * @param observer
	 * @param parentScope
	 * @return the merged scope
	 * @throws KlabException
	 */
	private ResolutionScope resolve(Observer observer, ResolutionScope parentScope) throws KlabException {

		ResolutionScope ret = resolve(observer.getObservable(), parentScope.getChildScope(observer), Mode.RESOLUTION);
		if (ret.getCoverage().isRelevant()) {
			parentScope.merge(ret);
		}
		return ret;
	}

	/**
	 * Resolve an observable in a context by accepting as many models as necessary
	 * to resolve its observation or instantiate the target observations. Final
	 * coverage is the OR of the coverage of all models found; lookup of models
	 * stops when coverage is complete.
	 * <p>
	 * TODO: if observable reference name is already known for a different
	 * observable, must change it. When that is done, remove the correspondent
	 * logics in DataflowCompiler.
	 * 
	 * @param observable
	 * @param parentScope
	 * @param mode
	 * @return the scope with any child scopes for the models and the coverage of
	 *         the resolved observable. If resolution is unsuccessful, return a
	 *         scope with no children, with empty coverage if the observable is
	 *         mandatory, or the passed scope's coverage if it's optional.
	 */
	private ResolutionScope resolve(Observable observable, ResolutionScope parentScope, Mode mode) {

		/*
		 * Check first if we need to redistribute the observable, in which case we only
		 * resolve the distribution context and we leave it to the runtime context to
		 * finish the job, as we do with the resolution of the individual instances.
		 */
		Observable deferTo = parentScope.getDeferredObservableFor(observable);

		if (deferTo != null) {

			Observable deferredObservable = observable;

			/*
			 * The observable loses the context if it's explicit and becomes inherent. (X
			 * within Y in context Z becomes X of Y). The deferred observable does not need
			 * the context so it goes back to being just X.
			 */
			if (Observables.INSTANCE.getDirectContextType(observable.getType()) != null) {

				/*
				 * this won't go through the dataflow compiler so we need to take care of units
				 * manually
				 */
				if (observable.getUnit() == null && Units.INSTANCE.needsUnits(observable)) {
					observable.setUnit(Units.INSTANCE.getDefaultUnitFor(observable));
				}

				deferredObservable = (Observable) observable.getBuilder(parentScope.getMonitor())
						.without(ObservableRole.CONTEXT).buildObservable();
				observable = (Observable) deferredObservable.getBuilder(parentScope.getMonitor()).of(deferTo.getType())
						.buildObservable();
			}

			/*
			 * Distribute the observable over the observation of its context. We don't know
			 * what the context observation will produce
			 */
			ResolutionScope ret = resolve(deferTo, parentScope, Mode.INSTANTIATION);
			if (ret.getCoverage().isRelevant()) {
				ResolutionScope deferred = ret.getChildScope(deferredObservable, mode);
				deferred.setDeferred(true);
				deferred.setCoverage(ret.getCoverage());
				ret.link(deferred);
			}

			if (Observables.INSTANCE.isOccurrent(observable.getType())) {
				parentScope.setOccurrent(true);
			}

			return ret;
		}

		ResolutionScope ret = parentScope.getChildScope(observable, mode);

		/*
		 * ensure the reference name represents unique semantics across the resolution
		 * tree
		 */
		observable = parentScope.disambiguateObservable(observable);

		/*
		 * pre-resolved artifacts contain a number, concept, boolean, expression or
		 * function. Those with a symbol aren't allowed as dependencies.
		 */
		if (((Observable) observable).isResolved()) {
			ret.setInlineValue(observable.getValue());
			parentScope.merge(ret);
			return ret;
		}

		/*
		 * detach a new coverage for partial matches, to be reset in the scope after
		 * resolution.
		 */
		Coverage coverage = new Coverage(ret.getCoverage());

		/**
		 * If we're resolving something that has been resolved before (i.e. not
		 * resolving a countable, which only happens before it is created), get the
		 * artifact as is and return it accepted for the dataflow to compile an import
		 * actuator.
		 */
		Pair<String, IArtifact> previousArtifact = null;
		boolean tryPrevious = ret.getContext() != null
				&& (!observable.is(Type.COUNTABLE) || mode == Mode.INSTANTIATION);
		if (tryPrevious) {
			/*
			 * look in the catalog. This will have accurate coverage but not necessarily
			 * every observation (those coming from attributes will be missing).
			 */
			previousArtifact = ((DirectObservation) ret.getContext()).getScope().findArtifact(observable);
			if (previousArtifact == null) {
				/*
				 * check in the context's children and attribute full coverage if there
				 */
				IObservation previous = ((DirectObservation) ret.getContext()).getObservationResolving(observable);
				if (previous != null) {
					previousArtifact = new Pair<>(previous.getObservable().getName(), previous);
				}
			}
		}

		if (previousArtifact != null) {

			ret.acceptArtifact(observable, (IObservation) previousArtifact.getSecond(), previousArtifact.getFirst());
			coverage = ret.getCoverage();

		} else if (observable.getReferencedModel() != null) {

			// observable comes complete with model, semantic or not
			ResolutionScope mscope = resolve((Model) observable.getReferencedModel(), ret);
			if (mscope.getCoverage().isRelevant() && ret.or(mscope)) {
				ret.link(mscope);
				coverage = mscope.getCoverage();
			}

		} else {

			// will be non-empty if this observable was resolved before, empty otherwise
			if (coverage.isEmpty()) {

				List<ObservationStrategy> candidates = ObservationStrategy.computeStrategies(observable, ret,
						ret.getMode());
				boolean done = false;
				int order = 0;
				for (ObservationStrategy strategy : candidates) {

					if (strategy.isResolve()) {
						// resolve again from scratch. No computations or anything.
						return resolve(strategy.getObservables().get(0), parentScope, mode);
					}

					try {

						// candidate may switch resolution mode
						double percentCovered = 0;

						/*
						 * If we have a complex candidate, just build a convenience model and resolve
						 * that instead of branching through a forest of possibilities. Otherwise search
						 * the kbox and network for candidates.
						 */
						List<IRankedModel> candidateModels = strategy.isTrivial()
								? Models.INSTANCE.resolve(strategy.getObservables().get(0),
										ret.getChildScope(strategy.getObservables().get(0), strategy.getMode()))
								: Models.INSTANCE.createDerivedModel(observable, strategy, ret);

						/*
						 * set the partition flag after we are sure we have > 1 link
						 */
						List<Link> links = new ArrayList<>();

						for (IRankedModel model : candidateModels) {

							ResolutionScope mscope = resolve((RankedModel) model, ret);

							if (mscope.getCoverage().isRelevant()) {

								Coverage newCoverage = coverage.merge(mscope.getCoverage(), LogicalConnector.UNION);
								if (!newCoverage.isRelevant()) {
									continue;
								}

								// for reporting
								boolean wasZero = percentCovered == 0;
								// percent covered by new model
								double coverageDelta = newCoverage.getCoverage() - percentCovered;
								// percent covered so far
								percentCovered += newCoverage.getCoverage();

								coverage = newCoverage;
								mscope.getMonitor()
										.debug("accepting " + model.getName() + " to resolve "
												+ NumberFormat.getPercentInstance().format(coverageDelta)
												+ (wasZero ? "" : " more") + " of " + observable);
								/*
								 * Link to dataflow and specify the order of computation and whether this is a
								 * partition of the context. The actual scale of computation for the model will
								 * be established by the dataflow compiler.
								 */
								links.add(ret.link(mscope).withOrder(order++));
							}

							if (coverage.isComplete()) {
								done = true;
								break;
							}
						}

						if (links.size() > 1) {
							for (Link link : links) {
								link.withPartition(true);
							}
						}

						if (done) {
							break;
						}

					} catch (KlabException e) {
						parentScope.getMonitor()
								.error("error during resolution of " + strategy + ": " + e.getMessage());
						return parentScope.empty();
					}
				}
			}
		}

		if (coverage.isRelevant()) {

			if (Observables.INSTANCE.isOccurrent(observable)) {
				parentScope.setOccurrent(true);
			}

			ret.setCoverage(coverage);
			parentScope.merge(ret);
			if (ret.getCoverage().getCoverage() < 0.95) {
				parentScope.getMonitor()
						.warn(observable.getType().getDefinition() + ": models could only be found to cover "
								+ NumberFormat.getPercentInstance().format(ret.getCoverage().getCoverage())
								+ " of the context");
			}
		} else if (observable.isOptional()
				|| ((observable.is(Type.SUBJECT) || observable.is(Type.PREDICATE)) && mode == Mode.RESOLUTION)) {

			if (coverage.getCoverage() > 0) {
				parentScope.getMonitor()
						.warn("models were found but the context coverage ("
								+ NumberFormat.getPercentInstance().format(ret.getCoverage().getCoverage())
								+ ") is insufficient to proceed");
			}

			/*
			 * empty strategy is OK for optional dependencies and resolved subjects. The
			 * latter are never resolved unless there has been an implicit instantiation
			 * from an instantiator, so a dataflow that creates them is generated.
			 */
			ret.acceptEmpty();
		}

		return ret;
	}

	/**
	 * Resolve a model's dependencies. Final coverage is the AND of the resolved
	 * dependencies.
	 * 
	 * @param model
	 * @param parentScope
	 * @return the merged scope, or an empty one.
	 * @throws KlabException
	 */
	private ResolutionScope resolve(Model model, ResolutionScope parentScope) throws KlabException {

		ResolutionScope ret = parentScope.getChildScope(model);

		Coverage coverage = new Coverage(ret.getCoverage());

		/*
		 * this can only happen if the model doesn't come from a kbox.
		 */
		if (coverage.getCoverage() == 0) {
			if (parentScope.getOriginalScope() == Scope.MODEL) {
				/*
				 * we have explicitly asked to resolve a model outside of the coverage, which
				 * can only happen if a model is observed directly as the kbox won't return it.
				 * In this case, warn and force the coverage to full so that resolution can
				 * continue at the modeler's risk.
				 */
				parentScope.getMonitor()
						.warn("Model " + model.getName() + " is being observed outside its coverage! Expect problems.");
				coverage.setCoverage(1.0);
			} else {
				parentScope.getMonitor().error(new KlabInternalErrorException(
						"empty model coverage before resolution of dependencies: this should never happen"));
			}
		}

		// use the reasoner to infer any missing dependency from the semantics
		List<ObservationStrategy> strategies = ObservationStrategy.computeDependencies(parentScope.getObservable(),
				model, ret);
		for (ObservationStrategy strategy : strategies) {
			// ACHTUNG TODO OBSERVABLE CAN BE MULTIPLE (probably not here though) - still,
			// should be resolving a CandidateObservable
			ResolutionScope mscope = resolve(strategy.getObservables().get(0), ret, strategy.getMode());
			coverage = coverage.merge(mscope.getCoverage(), LogicalConnector.INTERSECTION);
			if (coverage.isEmpty()) {
				parentScope.getMonitor().info("discarding first choice " + model.getId() + " due to missing dependency "
						+ strategy.getObservables().get(0).getName());
				break;
			}
		}

		ret.setCoverage(coverage);

		return ret;
	}

	/**
	 * Retrieve an appropriately configured model prioritizer for the passed scope.
	 * 
	 * @param context
	 * @return a prioritizer for this model
	 */
	public static IPrioritizer<ModelReference> getPrioritizer(ResolutionScope context) {
		return new Prioritizer(context);
	}

}
