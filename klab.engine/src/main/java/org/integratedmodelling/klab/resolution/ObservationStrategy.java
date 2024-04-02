package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IObservableService;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.owl.ObservableBuilder;
import org.integratedmodelling.klab.utils.Pair;

/**
 * An observation strategy implements all context-dependent inferences on
 * observables (those that are context-independent are available through the
 * configured {@link IObservableService}). It can produce all the model
 * observables that can resolve a stated one, taking into account mode of
 * resolution (for instantiation or resolution), attributes, roles and context,
 * along with the recipe on how to observe them and transform the resulting
 * artifact if necessary. When iterated, returns the original observable when
 * appropriate, then, upon request, produces all the other candidates. Each
 * candidate includes one or more observables and optional computations;
 * non-trivial candidates (with more than one observable and/or any computation)
 * produce an ad-hoc model that is resolved recursively.
 * <p>
 * The observable reasoner can also infer all the actual dependencies of a
 * model, based on the model's stated ones and on its semantics, taking into
 * account roles, abstract status, relationship endpoints etc.
 * 
 * @author ferdinando.villa
 *
 */
public class ObservationStrategy {

	/**
	 * The observation strategy incarnated by this object. Used by the dataflow
	 * compiler to properly assemble the dataflow.
	 * 
	 * @author Ferd
	 *
	 */
	public enum Strategy {
		/**
		 * Observable is directly observed; dependencies may have been added
		 */
		DIRECT,
		/**
		 * Observable has been decomposed into a legitimate observable and one or more
		 * filters provided by an attribute resolver, to be applied to it in sequence if
		 * resolving.
		 */
		FILTERING,
		/**
		 * Observable will be contextualized by resolving a set of objects to which the
		 * observable is inherent, then applying a dereifying transformation chosen by
		 * the runtime.
		 */
		DEREIFICATION,

	}

	private List<Observable> observables = new ArrayList<>();
	private Mode mode;
	private List<IContextualizable> computation = new ArrayList<>();
	private Strategy strategy = Strategy.DIRECT;
	// if this is true, the observable must be resolved again instead of proceeding
	// to model query
	private boolean resolve = false;

	public ObservationStrategy(Observable observable, Mode mode) {
		this.mode = mode;
		this.observables.add(observable);
	}

	public ObservationStrategy(Observable observable, Mode mode, Strategy strategy) {
		this.mode = mode;
		this.observables.add(observable);
		this.strategy = strategy;
	}

	/**
	 * 
	 * @param model
	 * @param scope
	 * @return
	 */
	public static List<ObservationStrategy> computeDependencies(IObservable observable, IModel model,
			IResolutionScope scope) {

		List<ObservationStrategy> ret = new ArrayList<>();
		List<IObservable> dependencies = new ArrayList<>();
		for (IObservable dep : model.getDependencies()) {
			// add all the active dependencies, expanding any generic ones.
			for (IObservable dependency : expandDependency(dep, observable, model, scope)) {
				if (((Observable) dependency).isActive()) {
					dependencies.add(dependency);
					ret.add(new ObservationStrategy((Observable) dependency,
							dependency.getDescriptionType().getResolutionMode()));
				}
			}
		}

		if (observable.is(Type.RELATIONSHIP)) {
			IConcept source = Observables.INSTANCE.getRelationshipSource(observable.getType());
			IConcept target = Observables.INSTANCE.getRelationshipTarget(observable.getType());
			if (findDependency(dependencies, source) == null) {
				ret.add(new ObservationStrategy(Observable.promote(source), Mode.INSTANTIATION));
			}
			if (!target.equals(source) && findDependency(dependencies, target) == null) {
				ret.add(new ObservationStrategy(Observable.promote(target), Mode.INSTANTIATION));
			}
		}

		if (observable.getTemporalInherent() != null) {
			if (findDependency(dependencies, observable.getTemporalInherent()) == null) {
				ret.add(new ObservationStrategy(Observable.promote(observable.getTemporalInherent()),
						Mode.INSTANTIATION));
			}
		}

		/*
		 * If we're classifying a countable with a trait and we don't have the
		 * dependency for it, add it.
		 */
		if (observable.getDescriptionType() == IActivity.Description.CLASSIFICATION) {
			IConcept dep = Observables.INSTANCE.getDescribedType(observable.getType());
			if (findDependency(dependencies, dep) == null) {
				ret.add(new ObservationStrategy(Observable.promote(dep),
						observable.getDescriptionType().getResolutionMode()));
			}
		}

		/*
		 * If we're observing change in a quality, ensure we have the initial value as a
		 * dependency unless the model is resolved, in which case any needed inputs will
		 * have to be explicit, and the model should have the quality as an output.
		 */
		if (observable.is(Type.CHANGE) && !model.isResolved()) {
			IConcept dep = Observables.INSTANCE.getDescribedType(observable.getType());
			if (findDependency(dependencies, dep) == null && ((Model) model).findOutput(dep) == null) {
				ret.add(new ObservationStrategy(Observable.promote(dep), Mode.RESOLUTION));
			}
		}

		/**
		 * Add as dependency (for initialization) any concept that is affected by the
		 * process but not created by it.
		 */
		if (observable.is(Type.PROCESS)) {
			for (int i = 1; i < model.getObservables().size(); i++) {
				IObservable output = model.getObservables().get(i);
				if (output.is(Type.QUALITY) && Observables.INSTANCE.isAffectedBy(output, observable)
						&& !Observables.INSTANCE.isCreatedBy(output, observable)) {
					ret.add(new ObservationStrategy((Observable) observable, Mode.RESOLUTION));
				}
			}
		}

		/**
		 * Add dependencies for anything mentioned in operators if needed
		 */
		for (Pair<ValueOperator, Object> operator : observable.getValueOperators()) {

			Observable dep = extractObservable(operator.getSecond(), scope.getMonitor());
			if (dep != null) {
				if (findDependency(dependencies, dep) == null) {
					ret.add(new ObservationStrategy(dep,
							dep.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION));
				}
			}
		}

		return ret;
	}

	private static Observable extractObservable(Object arg, IMonitor monitor) {
		if (arg instanceof Observable) {
			return (Observable) arg;
		} else if (arg instanceof IConcept) {
			return Observable.promote((IConcept) arg);
		} else if (arg instanceof IKimObservable) {
			return (Observable) Observables.INSTANCE.declare((IKimObservable) arg, monitor);
		} else if (arg instanceof IKimConcept) {
			return Observable.promote(Concepts.INSTANCE.declare((IKimConcept) arg));
		}
		return null;
	}

	/*
	 * TODO/FIXME - see if this is necessary vs. the other, and if the canResolve
	 * implementation is still OK. Could/should have a single implementation with
	 * ISemantic as an argument.
	 */
	private static IObservable findDependency(List<IObservable> dependencies, IObservable concept) {
		for (IObservable dependency : dependencies) {
			if (((Observable) concept).resolvesStrictly((Observable) dependency)) {
				return dependency;
			}
		}
		return null;
	}

	private static List<IObservable> expandDependency(IObservable dep, IObservable observable, IModel model,
			IResolutionScope scope) {
		List<IObservable> ret = new ArrayList<>();
		if (dep.isGeneric()) {
			IConcept type = dep.getType();
			if (type.is(Type.ROLE)) {
				for (IConcept role : scope.getRoles().keySet()) {
					if (role.is(type)) {
						for (IConcept obs : scope.getRoles().get(role)) {
							ret.add(Observable.promote(obs).withRole(type));
						}
					}
				}
			}
		} else {/*
				 * for now abstract is OK due to its role in attribute instantiator, TODO check
				 * later else if (dep.isAbstract()) { // TODO System.out.println("HOSTIAZ"); }
				 */
			ret.add(dep);
		}
		return ret;

	}

	private static IObservable findDependency(List<IObservable> dependencies, IConcept concept) {
		for (IObservable dependency : dependencies) {
			if (dependency.getType().getSemanticDistance(concept) == 0) {
				return dependency;
			}
		}
		return null;
	}

	/**
	 * The result will contain the trivial strategy + up to one alternative, which
	 * will be resolved recursively.
	 * 
	 * @param observable
	 * @param scope
	 * @return
	 */
	public static List<ObservationStrategy> computeStrategies(IObservable observable, IResolutionScope scope,
			Mode mode) {

		List<ObservationStrategy> ret = new ArrayList<>();

		Observable target = (Observable) observable;
		List<Pair<ValueOperator, Object>> operators = observable.getValueOperators();
		Strategy strategy = Strategy.DIRECT;

		/*
		 * explore inherency first. If there is explicit inherency (of), we first check
		 * for equality of inherency, i.e. X of Y within Y. In this case we just observe
		 * X. This just applies to true observables: if the inherency is for an
		 * attribute (e.g. normalized of X) we move forward.
		 * 
		 * If not, we leave the trivial strategy as is (to be resolved by a possible
		 * model) and add X within Y, which will generate X of Y in the current context.
		 */
		IConcept directInherent = Observables.INSTANCE.getDirectInherentType(observable.getType());
		IConcept inherent = observable.is(Type.OBSERVABLE) ? directInherent : null;
		if (inherent != null) {
			IConcept context = /* Observables.INSTANCE.getContextType(observable.getType()) */ observable.getContext();
			if (context != null && context.equals(inherent)) {
				observable = observable.getBuilder(scope.getMonitor()).without(ObservableRole.INHERENT)
						.buildObservable();
				ret.add(new ObservationStrategy((Observable) observable, mode));
				// next if should never be necessary as it's not legal to use direct context
				// except in an output
				/*
				 * TODO/CHECK - removed all changes as it messes up characterizations
				 */
			} else if (Observables.INSTANCE.getDirectContextType(observable.getType()) == null) {
				// ret.add(new ObservationStrategy((Observable) observable, mode));
				// observable =
				// observable.getBuilder(scope.getMonitor()).without(ObservableRole.INHERENT).within(inherent)
				// .buildObservable();
				// ObservationStrategy alternative = new ObservationStrategy((Observable)
				// observable, mode);
				// alternative.setResolve(true);
				// ret.add(alternative);
				ret.add(new ObservationStrategy(target, mode, strategy));
			}
		} else if (!operators.isEmpty()) {

			/*
			 * no as-is resolution, just use the operators. Otherwise it becomes messy to
			 * match candidates, and the utility of allowing pre-modified source models is
			 * doubtful as it encourages use of partial information as primary.
			 */

			target = (Observable) ((Observable) observable).getBuilder(scope.getMonitor()).withoutValueOperators()
					.buildObservable();
			Observable previous = ((ResolutionScope) scope).getResolvedObservable(target, mode);
			if (previous != null) {
				target = previous;
			}

			ObservationStrategy alternative = new ObservationStrategy(target, mode);

			// separate modifiers
			Set<ValueOperator> modifiers = new HashSet<>();
			List<Pair<ValueOperator, Object>> ops = new ArrayList<>();
			for (Pair<ValueOperator, Object> operator : operators) {
				if (operator.getFirst().isModifier) {
					modifiers.add(operator.getFirst());
				} else {
					ops.add(operator);
				}
			}

			for (Pair<ValueOperator, Object> operator : ops) {
				alternative.computation.add(Klab.INSTANCE.getRuntimeProvider().getOperatorResolver(target,
						operator.getFirst(), operator.getSecond(), modifiers));
			}

			ret.add(alternative);

		} else if (target.hasResolvableTraits()) {

			ret.add(new ObservationStrategy((Observable) observable, mode, strategy));

			Pair<IConcept, Observable> resolvables = target.popResolvableTrait(scope.getMonitor());

			IConcept attribute = resolvables.getFirst();
			target = resolvables.getSecond();

			/*
			 * if the observable was resolved before, use that so we don't have to translate
			 * names when we compile the dataflow.
			 */
			Observable previous = ((ResolutionScope) scope).getResolvedObservable(target, mode);
			if (previous != null) {
				target = previous;
			}

			IConcept targetAttribute = null;
			if (!attribute.is(Type.ABSTRACT) && observable.is(Type.COUNTABLE)) {
				/*
				 * find the first base observable that is abstract; give up if not found. The
				 * dataflow will discard the outputs that are not what is wanted, and tag others
				 * as irrelevant (hidden) if it's a first instantiation.
				 */
				IConcept base = Traits.INSTANCE.getBaseParentTrait(attribute);
				if (base.isAbstract()) {
					targetAttribute = attribute;
					attribute = base;
				} else {
					scope.getMonitor().debug("cannot resolve predicate " + attribute + " within " + target
							+ ": can't find an abstract base predicate for classification");
					attribute = null;
				}
			}

			if (attribute != null) {

				Observable filter = (Observable) new ObservableBuilder(attribute, scope.getMonitor())
						.of(Observables.INSTANCE.getBaseObservable(target.getType()))/* .filtering(target) */
						.withTargetPredicate(targetAttribute).withDistributedInherency(observable.is(Type.COUNTABLE))
						.buildObservable();

				ObservationStrategy alternative = new ObservationStrategy(target,
						observable.getDescriptionType().getResolutionMode());

				alternative.observables.add(filter);
				alternative.strategy = Strategy.FILTERING;

				ret.add(alternative);
			}

		} else if (hasResolvableInherency(target, scope)) {

			// direct
			ret.add(new ObservationStrategy((Observable) observable, mode, strategy));

			List<IContextualizable> computations = new ArrayList<>();
			inherent = null;
			strategy = Strategy.DIRECT;

			if (observable.is(Type.PRESENCE)) {

				inherent = Observables.INSTANCE.getDescribedType(observable.getType());
				if (inherent != null && !((ResolutionScope) scope).isBeingResolved(inherent, Mode.INSTANTIATION)) {
					computations.addAll(Klab.INSTANCE.getRuntimeProvider().getComputation(Observable.promote(inherent),
							Mode.RESOLUTION, observable));
					strategy = Strategy.DEREIFICATION;
				}

			} else if (scope.getCoverage().getSpace() != null && scope.getCoverage().getSpace().getDimensionality() >= 2
					&& observable.is(Type.DISTANCE) || observable.is(Type.NUMEROSITY)) {

				inherent = Observables.INSTANCE.getDescribedType(observable.getType());
				if (inherent != null && !((ResolutionScope) scope).isBeingResolved(inherent, Mode.INSTANTIATION)) {
					computations.addAll(Klab.INSTANCE.getRuntimeProvider().getComputation(Observable.promote(inherent),
							Mode.RESOLUTION, observable));
					strategy = Strategy.DEREIFICATION;
				}

			} else if (observable.is(Type.RATIO)) {
				// TODO won't happen for now
			}

			if (!computations.isEmpty()) {

				Observable inherentObservable = null;
				Observable previous = ((ResolutionScope) scope).getResolvedObservable(target,
						inherent.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION);
				if (previous != null) {
					inherentObservable = previous;
				} else {
					inherentObservable = Observable.promote(inherent);
				}

				ObservationStrategy alternative = new ObservationStrategy(inherentObservable, mode, strategy);

				alternative.computation.addAll(computations);

				ret.add(alternative);
			}

		} /* TODO reintegrate - the 'else' is wrong because it will exclude other basic situations handled later
		
			 * else if (target.is(Type.CHANGE)) {
			 * 
			 * 
			 * if the change is a quantifiable, we could compute it also by integrating the
			 * change rate of the same.
			 * 
			 * 
			 * } else if (target.is(Type.CHANGED)) {
			 * 
			 * 
			 * change events for a quality can be built using an internal contextualizer if
			 * the change is resolved.
			 * 
			 * 
			 * }
			 */
		/**
		 * else if (target.getType().is(Type.PREDICATE) && directInherent == null &&
		 * scope.getContext() != null) {
		 * 
		 * 
		 * target is a predicate and has no inherency: we add the inherent type of the
		 * context as it's illegal to write a naked "model predicate" characterizer to
		 * resolve the predicate independent of context.
		 * 
		 * IObservable contextualized =
		 * target.getBuilder(scope.getMonitor()).of(scope.getContext().getObservable().getType())
		 * .buildObservable(); ret.add(new ObservationStrategy((Observable)
		 * contextualized, mode, strategy));
		 * 
		 * }
		 */
		else {

			/*
			 * just add as is
			 */
			ret.add(new ObservationStrategy(target, mode, strategy));

		}

		return ret;
	}

	private static boolean hasResolvableInherency(Observable observable, IResolutionScope scope) {
		// TODO handle ratios, one day types (with classifiers)
		return observable.is(Type.PRESENCE) || scope.getCoverage().getSpace() != null
				&& scope.getCoverage().getSpace().getDimensionality() >= 2 && observable.is(Type.DISTANCE)
				|| observable.is(Type.NUMEROSITY);
	}

	public boolean isTrivial() {
		return this.observables.size() == 1 && this.computation.isEmpty();
	}

	/**
	 * The observable(s) that satisfy this request
	 */
	public List<Observable> getObservables() {
		return this.observables;
	}

	/**
	 * Any necessary computation to apply to the resulting artifact to produce the
	 * requested observation
	 */
	public List<IContextualizable> getComputation() {
		return this.computation;
	}

	/**
	 * The mode in which the observable should be observed
	 */
	public Mode getMode() {
		return this.mode;
	}

	/**
	 * Tells the model and the dataflow compiler how to handle the
	 * contextualization.
	 * 
	 * @return
	 */
	public Strategy getStrategy() {
		return strategy;
	}

	public boolean isResolve() {
		return resolve;
	}

	public void setResolve(boolean resolve) {
		this.resolve = resolve;
	}

	@Override
	public String toString() {
		return "ObservationStrategy [observables=" + observables + ", strategy=" + strategy + "]";
	}

}
