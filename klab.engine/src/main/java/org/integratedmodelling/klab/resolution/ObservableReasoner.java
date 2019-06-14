package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.api.services.IObservableService;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.engine.runtime.code.Transformation;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ObservableReasoner.CandidateObservable;

/**
 * An observable reasoner implements all context-dependent inferences on
 * observables (those that are context-independent are available through the
 * configured {@link IObservableService}). It can produce all the model
 * observables that can resolve a stated one, taking into account mode of
 * resolution (for instantiation or resolution) and context, along with the
 * recipe on how to observe them and transform the resulting artifact if
 * necessary. When iterated, returns the original observable when appropriate,
 * then, upon request, produces all the other candidates.
 * <p>
 * It can also be used to infer all the actual dependencies of a model, based on
 * the model's stated ones and on its semantics, taking into account roles,
 * abstract status, relationship endpoints etc.
 * 
 * @author ferdinando.villa
 *
 */
public class ObservableReasoner implements Iterable<CandidateObservable> {

	private ResolutionScope scope;
	private Mode mode;
	private List<CandidateObservable> alternatives = new ArrayList<>();
	private boolean alternativesComputed = false;

	/**
	 * Each candidate observable comes with an observable, a mode of observation and
	 * a set of indirectAdapters to adapt it to the target observation.
	 * 
	 * TODO each candidate observable should be one or more observables, resolved in
	 * AND.
	 */
	public static class CandidateObservable {

		/**
		 * The observable(s) that satisfy this request
		 */
		public List<Observable> observables = new ArrayList<>();

		/**
		 * The mode in which the observable should be observed
		 */
		public Mode mode;

		/**
		 * If true, this is part of a group of contiguous observables representing a set
		 * of alternative observables in OR. We will resolve all the observables,
		 * failing only if none is resolved, and contextualize the resulting
		 * contextualizers lazily until fulfillment of the observation.
		 */
		public boolean inGroup;

		/**
		 * Any necessary computation to apply to the resulting artifact to produce the
		 * requested observation
		 */
		public List<IComputableResource> computation;

		CandidateObservable(Observable original, Mode mode) {
			this.observables.add(original);
			this.mode = mode;
		}

		CandidateObservable(Observable original, Mode mode, List<IComputableResource> computables) {
			this.observables.add(original);
			this.mode = mode;
			this.computation = computables;
		}

		CandidateObservable(Observable original, Mode mode, boolean inGroup) {
			this.observables.add(original);
			this.mode = mode;
			this.inGroup = inGroup;
		}

//		/**
//		 * Inform all the computables that this model is the one that provides the
//		 * observable to transform, so that the IDs referenced for the observable can be
//		 * established.
//		 * 
//		 * @param model
//		 */
//		public void accept(IRankedModel model) {
//			// TODO this is ugly - revise
//			if (this.computation != null) {
//				for (IComputableResource computable : computation) {
//					Klab.INSTANCE.getRuntimeProvider().setComputationTargetId(computable,
//							model.getObservables().get(0).getLocalName());
//				}
//			}
//		}

	}

	// the two below are only used when inferring the actual dependencies in a model
	private Model model;
	private Observable observable;

	class It implements Iterator<CandidateObservable> {

		int idx = 0;

		@Override
		public boolean hasNext() {
			return getObservables().size() > idx;
		}

		@Override
		public CandidateObservable next() {
			CandidateObservable ret = alternatives.get(idx);
			idx++;
			return ret;
		}
	}

	/**
	 * Use this to compute the model's dependencies. Also pass the actual observable
	 * being resolved, which may not be the same as the model's observable (due to
	 * possible abstract status of the model).
	 * 
	 * @param model
	 * @param observable
	 */
	public ObservableReasoner(Model model, Observable observable, ResolutionScope scope) {
		this.model = model;
		this.observable = observable;
	}

	/**
	 * Same as {@link #ObservableReasoner(Observable, ResolutionScope)} when the
	 * scope is not available.
	 * 
	 * @param original
	 * @param mode
	 */
	public ObservableReasoner(Observable original, Mode mode, ResolutionScope scope) {
		this.alternatives.add(new CandidateObservable(original, mode));
		this.mode = mode;
		this.scope = scope;
	}

	/**
	 * Use to compute all the alternative observables that can resolve an original
	 * one.
	 * 
	 * @param original
	 * @param scope
	 */
	public ObservableReasoner(Observable original, ResolutionScope scope) {
		this.alternatives.add(new CandidateObservable(original, scope.getMode()));
		this.scope = scope;
		this.mode = scope.getMode();
	}

	@Override
	public Iterator<CandidateObservable> iterator() {
		return new It();
	}

	/**
	 * Compute all possible alternatives for the original observable, or the entire
	 * set of appropriate dependencies for the passed model in the context.
	 * 
	 * @return all alternatives, including the original when it can be observed
	 *         directly.
	 */
	public List<CandidateObservable> getObservables() {
		if (!this.alternativesComputed) {
			if (this.model != null) {
				alternatives.addAll(inferModelDependencies(this.model));
			} else {
				for (Observable oo : this.alternatives.get(0).observables) {
					alternatives.addAll(inferAlternativeObservables(oo));
				}
			}
			this.alternativesComputed = true;
		}
		return this.alternatives;
	}

	/**
	 * Return all the alternatives except the first, which is expected to be the
	 * original observable.
	 * 
	 * @throw {@link IllegalStateException} when called in a context where
	 *        alternatives do not apply (model dependency resolution).
	 * @return a list of alternative observables, possibly empty.
	 */
	public List<CandidateObservable> getAlternatives() {
		if (model != null) {
			throw new IllegalStateException(
					"ObservableReasoner: getAlternatives() called when alternatives are not computed.");
		}
		return getObservables().subList(1, alternatives.size());
	}

	/*
	 * -----------------------------------------------------------------------------
	 * Computation of alternative observables for the passed one
	 * -----------------------------------------------------------------------------
	 */

	private List<CandidateObservable> inferAlternativeObservables(Observable observable) {

		List<CandidateObservable> ret = new ArrayList<>();

		/*
		 * if we get here, we already know that the original observable isn't available.
		 * First strip any data reduction traits that we know how to handle and save
		 * them.
		 */
		IObservable.Builder builder = observable.getBuilder(scope.getMonitor())
				.withoutAny(Concepts.c(NS.CORE_OBSERVATION_TRANSFORMATION));

		if (builder.getRemoved().size() > 0) {
			boolean ok = true;
			/*
			 * TODO Simple strategy assuming that transformations are not contextual. Should
			 * become smarter to include other attributes, and be called lazily (currently
			 * it's not).
			 */
			List<IModel> transformers = new ArrayList<>();
			for (IConcept trait : builder.getRemoved()) {
				IModel transformer = Models.INSTANCE.resolve(trait, this.scope);
				if (transformer == null) {
					ok = false;
					break;
				}
				transformers.add(transformer);
			}

			if (ok) {
				List<IComputableResource> transformations = new ArrayList<>();
				IConcept concept = builder.buildConcept();
				Observable newobs = Observable.promote(concept);
				for (IModel model : transformers) {
					for (IComputableResource computation : model.getComputation(ITime.INITIALIZATION)) {
						transformations.add(new Transformation(computation, newobs));
					}
				}
				ret.add(new CandidateObservable(newobs, Mode.RESOLUTION, transformations));
			}

		}

		if (observable.is(Type.PRESENCE)) {

			IConcept inherent = Observables.INSTANCE.getInherentType(observable.getType());

			if (inherent != null && !scope.isBeingResolved(inherent, Mode.INSTANTIATION)) {
				List<IComputableResource> dereificator = Klab.INSTANCE.getRuntimeProvider()
						.getComputation(Observable.promote(inherent), Mode.RESOLUTION, observable);
				if (dereificator != null) {
					ret.add(new CandidateObservable(Observable.promote(inherent), Mode.INSTANTIATION, dereificator));
				}
			}

		} else if (scope.getCoverage().getSpace() != null && scope.getCoverage().getSpace().getDimensionality() >= 2
				&& observable.is(Type.DISTANCE) || observable.is(Type.NUMEROSITY)) {

			IConcept inherent = Observables.INSTANCE.getInherentType(observable.getType());

			if (inherent != null && !scope.isBeingResolved(inherent, Mode.INSTANTIATION)) {
				List<IComputableResource> dereificator = Klab.INSTANCE.getRuntimeProvider()
						.getComputation(Observable.promote(inherent), Mode.RESOLUTION, observable);
				if (dereificator != null) {
					ret.add(new CandidateObservable(Observable.promote(inherent), Mode.INSTANTIATION, dereificator));
				}
			}

		} else if (observable.is(Type.RATIO)) {
			// TODO
		}

		if (observable.getAggregator() != null) {
			// we also need the aggregator observation and a distributing operator
		}

		return ret;
	}

	/*
	 * -----------------------------------------------------------------------------
	 * Computation of full dependency scope for model
	 * -----------------------------------------------------------------------------
	 */

	private List<CandidateObservable> inferModelDependencies(Model model) {

		List<CandidateObservable> ret = selectApplicableDependencies(model.getDependencies(), scope);

		if (observable.getAggregator() != null) {
			// we need the aggregator observation + the adapter after the state is computed
		}

		if (observable.is(Type.RELATIONSHIP)) {
			IConcept source = Observables.INSTANCE.getRelationshipSource(observable.getType());
			IConcept target = Observables.INSTANCE.getRelationshipTarget(observable.getType());
			if (model.findDependency(source) == null) {
				ret.add(new CandidateObservable(Observable.promote(source),
						source.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION));
			}
			if (!target.equals(source) && model.findDependency(target) == null) {
				ret.add(new CandidateObservable(Observable.promote(target),
						target.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION));
			}
		}
		
		//
		// /**
		// * original minus those that apply to a scale we don't have.
		// */
		// List<IDependency> ret = selectApplicableDependencies(dependencies, context);
		//
		// for (Pair<IConcept, Boolean> c : getInferredDependencies(monitor, context)) {
		// // if (!haveDep(ret, c)) {
		// ObservableSemantics obs = new ObservableSemantics(c.getFirst());
		// if (NS.isCountable(c.getFirst())) {
		// obs.setInstantiator(true);
		// }
		// ret.add(new KIMDependency(obs, obs.getFormalName(), c.getSecond(), context));
		// // }
		// }
		//
		// /**
		// * Add whatever is specified by the semantics and is not already in the stated
		// * dependencies. Only for direct observations - indirect are "data" and they
		// * naturally can't have anything playing a role in their semantics, only
		// models
		// * can define that.
		// */
		// if (NS.isDirect(getObservable())) {
		//
		// for (IConcept c : Roles.getRoles(this.getObservable(), context)) {
		// if (!haveDep(ret, c)) {
		// String fname = CamelCase.toLowerCase(c.getLocalName(), '-');
		// // TODO depends on the restriction and the property!
		// boolean isOptional = true;
		// IDependency ndep = new KIMDependency(new ObservableSemantics(c, NS
		// .getObservationTypeFor(c), fname), fname, null, isOptional, null, context);
		// ret.add(ndep);
		// }
		// }
		//
		// for (Triple<IConcept, IConcept, IProperty> sd :
		// NS.getSemanticDependencies(getObservable()))
		// {
		// IConcept dep = sd.getFirst();
		// String fname = CamelCase.toLowerCase(dep.getLocalName(), '-');
		// if (!haveDep(ret, dep)) {
		// // TODO depends on the restriction and the property!
		// boolean isOptional = true;
		// IDependency ndep = new KIMDependency(new ObservableSemantics(dep, NS
		// .getObservationTypeFor(dep), fname), fname, sd
		// .getThird(), isOptional, null, context);
		// ret.add(ndep);
		// }
		// }
		// }
		//
		return ret;
	}

	private void getInferredDependencies(Model model) {

		// // TODO Auto-generated method stub
		// List<Pair<IConcept, Boolean>> ret = new ArrayList<>();
		//
		/*
		 * roles are contextual to the ACTUAL observable being resolved, which may not
		 * be the same as the model's (as abstract models can be picked to resolve it).
		 */
		// Collection<IConcept> roles =
		// Roles.getRoles(context.getObservableBeingResolved().getType());
		//
		/*
		 * add source and destination if it's a relationship and we're instantiating
		 */
		// if (NS.isRelationship(context.getObservableBeingResolved()) && this
		// instanceof IModel
		// && ((IModel) this).isInstantiator()) {
		// for (IConcept role : roles) {
		// IConcept source = Observables.getApplicableSource(role);
		// IConcept destination = Observables.getApplicableDestination(role);
		// if (source != null) {
		// addRoleImpliedDependency(role, source, ret);
		// }
		// if (destination != null) {
		// addRoleImpliedDependency(role, destination, ret);
		// }
		// }
		// }
		//
		/*
		 * Otherwise, dependencies are obviously added only to explanation models
		 */
		// if (!(this instanceof IModel && ((IModel) this).isInstantiator())) {
		// for (IConcept role : roles) {
		// for (IConcept impliedRole : Roles.getImpliedObservableRoles(role)) {
		// addRoleImpliedDependency(role, impliedRole, ret);
		// }
		// }
		// }

	}

	// private void addRoleImpliedDependency(IConcept implyingRole, IConcept
	// impliedRole,
	// List<Pair<IConcept, Boolean>> ret) throws KlabException {
	//
	// /*
	// * Concrete ones generate a dependency on their applicable observable with the
	// role.
	// */
	// if (!impliedRole.isAbstract() && !includesRole(impliedRole)) {
	//
	// if (OWL.isRestrictionDenied(getObservable().getType(), impliedRole)) {
	// return;
	// }
	//
	// IConcept contextType = Observables.getContextType(getObservable().getType());
	// IConcept toobs = Roles.getObservableWithRole(impliedRole, contextType);
	//
	// /*
	// * TODO check - only adding the dep if it's DIRECT or a non-abstract
	// non-countable. Definitely
	// * shouldn't add an abstract state dep, but who knows.
	// */
	// if (toobs != null && (NS.isDirect(toobs) || !toobs.isAbstract())) {
	// boolean optional = OWL.isRestrictionOptional(implyingRole, impliedRole);
	// ret.add(new Pair<>(toobs, optional));
	// }
	// }
	// }
	//
	// protected boolean includesRole(IConcept observableRole) {
	//
	// for (IDependency d : getDependencies()) {
	// if ((((KIMDependency) d).interpretAs != null
	// && ((KIMDependency) d).interpretAs.is(observableRole))
	// || NS.contains(observableRole, Roles.getRoles(d))) {
	// return true;
	// }
	// }
	//
	// for (IObservableSemantics obs : this instanceof IModel ? ((IModel)
	// this).getObservables()
	// : Collections.singletonList(getObservable())) {
	// /*
	// * an abstract observable role fulfills the requested role if the requested
	// role specializes
	// * the provided (adding it means that the model will check the concrete role
	// and provide it as
	// * requested). For a concrete one the model must have a role that specializes
	// the requested.
	// */
	// if (obs.getType().isAbstract()) {
	// for (IConcept abrole : Roles.getRoles(obs.getType())) {
	// if (observableRole.is(abrole)) {
	// return true;
	// }
	// }
	// } else if (NS.contains(observableRole, Roles.getRoles(obs.getType()))) {
	// return true;
	// }
	// }
	//
	// return false;
	// }

	private List<CandidateObservable> selectApplicableDependencies(List<IObservable> dependencies,
			IResolutionScope context) {
		List<CandidateObservable> ret = new ArrayList<>();
		for (IObservable dep : dependencies) {
			// if (!((KIMDependency) dep).resolutionContext.isEmpty()) {
			// boolean ok = true;
			// for (IConcept ext : ((KIMDependency) dep).resolutionContext) {
			// if (context.getScale().getExtent(ext) == null) {
			// ok = false;
			// break;
			// }
			// }
			// if (!ok) {
			// continue;
			// }
			// }
			ret.add(new CandidateObservable((Observable) dep,
					dep.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION));
		}
		return ret;
	}
	//
	// private boolean haveDep(List<IDependency> ret, IKnowledge c) {
	// for (IDependency d : ret) {
	// if (d.getType().is(c)) {
	// return true;
	// }
	// }
	// return false;
	// }

}
