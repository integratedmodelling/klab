package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservable.ObservationType;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
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

	private List<Observable> observables = new ArrayList<>();
	private Mode mode;
	private List<IComputableResource> computation = new ArrayList<>();

	public ObservationStrategy(Observable observable, Mode mode) {
		this.mode = mode;
		this.observables.add(observable);
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

		for (IObservable dep : model.getDependencies()) {
			ret.add(new ObservationStrategy((Observable) dep,
					dep.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION));
		}

		if (observable.is(Type.RELATIONSHIP)) {
			IConcept source = Observables.INSTANCE.getRelationshipSource(observable.getType());
			IConcept target = Observables.INSTANCE.getRelationshipTarget(observable.getType());
			if (((Model) model).findDependency(source) == null) {
				ret.add(new ObservationStrategy(Observable.promote(source), Mode.INSTANTIATION));
			}
			if (!target.equals(source) && ((Model) model).findDependency(target) == null) {
				ret.add(new ObservationStrategy(Observable.promote(target), Mode.INSTANTIATION));
			}
		}

		/*
		 * If we're classifying a countable with a trait and we don't have the dependency for it,
		 * add it.
		 */
		if (observable.getObservationType() == ObservationType.CLASSIFICATION) {
			IConcept dep = observable.getInherentType();
			if (((Model) model).findDependency(dep) == null) {
				ret.add(new ObservationStrategy(Observable.promote(dep),
						// this is always countable, but leave it.
						dep.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION));
			}
		}
		
		/**
		 * Add dependencies for anything mentioned in operators if needed
		 */
		for (Pair<ValueOperator, Object> operator : observable.getValueOperators()) {

			if (operator.getSecond() instanceof IConcept) {
				IConcept dep = (IConcept) operator.getSecond();
				if (((Model) model).findDependency(dep) == null) {
					ret.add(new ObservationStrategy(Observable.promote(dep),
							dep.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION));
				}
			} else if (operator.getSecond() instanceof IObservable) {
				IObservable dep = (IObservable) operator.getSecond();
				if (((Model) model).findDependency(dep) == null) {
					ret.add(new ObservationStrategy((Observable) dep,
							dep.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION));
				}
			}
		}

		return ret;
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
		if (!operators.isEmpty()) {

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

			for (Pair<ValueOperator, Object> operator : operators) {
				alternative.computation.add(Klab.INSTANCE.getRuntimeProvider().getOperatorResolver(target,
						operator.getFirst(), operator.getSecond()));
			}

			ret.add(alternative);

		} else if (target.hasResolvableTraits()) {

			ret.add(new ObservationStrategy((Observable) observable, mode));

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

			Observable filter = (Observable) new ObservableBuilder(attribute)
					.of(Observables.INSTANCE.getBaseObservable(target.getType())).filtering(target).buildObservable();

			ObservationStrategy alternative = new ObservationStrategy(target, mode);

			alternative.observables.add(filter);

			ret.add(alternative);

		} else if (hasResolvableInherency(target, scope)) {

			ret.add(new ObservationStrategy((Observable) observable, mode));

			List<IComputableResource> computations = new ArrayList<>();
			IConcept inherent = null;

			if (observable.is(Type.PRESENCE)) {

				inherent = Observables.INSTANCE.getInherentType(observable.getType());
				if (inherent != null && !((ResolutionScope) scope).isBeingResolved(inherent, Mode.INSTANTIATION)) {
					computations.addAll(Klab.INSTANCE.getRuntimeProvider().getComputation(Observable.promote(inherent),
							Mode.RESOLUTION, observable));
				}

			} else if (scope.getCoverage().getSpace() != null && scope.getCoverage().getSpace().getDimensionality() >= 2
					&& observable.is(Type.DISTANCE) || observable.is(Type.NUMEROSITY)) {

				inherent = Observables.INSTANCE.getInherentType(observable.getType());
				if (inherent != null && !((ResolutionScope) scope).isBeingResolved(inherent, Mode.INSTANTIATION)) {
					computations.addAll(Klab.INSTANCE.getRuntimeProvider().getComputation(Observable.promote(inherent),
							Mode.RESOLUTION, observable));
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

				ObservationStrategy alternative = new ObservationStrategy(inherentObservable, mode);

				alternative.computation.addAll(computations);
				ret.add(alternative);
			}

		} else {

			/*
			 * just add as is
			 */
			ret.add(new ObservationStrategy((Observable) observable, mode));

		}

		return ret;
	}

	private static boolean hasResolvableInherency(Observable observable, IResolutionScope scope) {
		// TODO handle ratios
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
	public List<IComputableResource> getComputation() {
		return this.computation;
	}

	/**
	 * The mode in which the observable should be observed
	 */
	public Mode getMode() {
		return this.mode;
	}

	public String dump(String spacer) {
		// TODO Auto-generated method stub
		return null;
	}

}
