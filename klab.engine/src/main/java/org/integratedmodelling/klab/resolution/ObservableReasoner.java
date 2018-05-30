package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.services.IObservableService;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Observable;

/**
 * An observable reasoner implements all context-dependent inferences on
 * observables (those that are context-independent are available through the
 * configured {@link IObservableService}). It can produce all the model
 * observables that can resolve a stated one, taking into account mode of
 * resolution (for instantiation or resolution) and context. When iterated,
 * returns the original observable when appropriate, then, upon request it
 * produces all the others lazily.
 * 
 * It can also be used to infer all the actual dependencies of a model, based on
 * the model's stated ones and on its semantics, taking into account roles,
 * abstract status, relationship endpoints etc.
 * 
 * @author ferdinando.villa
 *
 */
public class ObservableReasoner implements Iterable<Observable> {

	private ResolutionScope scope;
	private Mode mode;
	private List<Observable> alternatives = new ArrayList<>();
	private boolean alternativesComputed = false;

	// the two below are only used when inferring the actual dependencies in a model
	private Model model;
	private Observable observable;

	class It implements Iterator<Observable> {

		int idx = 0;

		@Override
		public boolean hasNext() {
			return getObservables().size() > idx;
		}

		@Override
		public Observable next() {
			Observable ret = alternatives.get(idx);
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
	public ObservableReasoner(Model model, Observable observable) {
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
	public ObservableReasoner(Observable original, Mode mode) {
		this.alternatives.add(original);
		this.mode = mode;
	}

	/**
	 * Use to compute all the alternative observables that can resolve an original
	 * one.
	 * 
	 * @param original
	 * @param scope
	 */
	public ObservableReasoner(Observable original, ResolutionScope scope) {
		this.alternatives.add(original);
		this.scope = scope;
		this.mode = scope.getMode();
	}

	@Override
	public Iterator<Observable> iterator() {
		return new It();
	}

	/**
	 * Compute all possible alternatives for the original observable, or the entire
	 * set of appropriate dependencies for the passed model in the context.
	 * 
	 * @return all alternatives, including the original when it can be observed
	 *         directly.
	 */
	public List<Observable> getObservables() {
		if (!this.alternativesComputed) {
			if (this.model != null) {
				alternatives.addAll(inferModelDependencies(this.model));
			} else {
				alternatives.addAll(inferAlternativeObservables(this.alternatives.get(0)));
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
	public List<Observable> getAlternatives() {
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

	private List<Observable> inferAlternativeObservables(Observable observable) {
		List<Observable> ret = new ArrayList<>();
		if (observable.is(Type.PRESENCE)) {
			IConcept inherent = Observables.INSTANCE.getInherentType(observable.getType());
			if (inherent != null) {
				// TODO observable should contain a list of computations to adapt to the original after it's observed
				ret.add(Observable.promote(inherent));
			}
		} else if (scope.getCoverage().getSpace() != null && scope.getCoverage().getSpace().getDimensionality() >= 2
				&& observable.is(Type.DISTANCE) || observable.is(Type.NUMEROSITY)) {
			IConcept inherent = Observables.INSTANCE.getInherentType(observable.getType());
			if (inherent != null) {
				// TODO observable should contain a list of computations to adapt to the original after it's observed
				ret.add(Observable.promote(inherent));
			}
		} else if (observable.is(Type.RATIO)) {
			// TODO
		}
		return ret;
	}

	/*
	 * -----------------------------------------------------------------------------
	 * Computation of full dependency scope for model
	 * -----------------------------------------------------------------------------
	 */

	private List<Observable> inferModelDependencies(Model model) {

		List<Observable> ret = selectApplicableDependencies(model.getDependencies(), scope);

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

	private List<Observable> selectApplicableDependencies(List<IObservable> dependencies, IResolutionScope context) {
		List<Observable> ret = new ArrayList<>();
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
			ret.add((Observable) dep);
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
