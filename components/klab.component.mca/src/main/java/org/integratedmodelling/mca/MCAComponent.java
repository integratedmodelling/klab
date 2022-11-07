package org.integratedmodelling.mca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;
import org.integratedmodelling.mca.api.ICriterion.Type;
import org.integratedmodelling.mca.api.IStakeholder;
import org.integratedmodelling.mca.core.MCAAssessment;
import org.integratedmodelling.mca.core.Results;
import org.integratedmodelling.mca.model.Alternative;
import org.integratedmodelling.mca.model.Criterion;
import org.integratedmodelling.mca.model.Stakeholder;

@Component(id = "org.integratedmodelling.mca", version = Version.CURRENT)
public class MCAComponent {

	public static final String ALTERNATIVE_ANNOTATION_ID = "alternative";
	public static final String STAKEHOLDER_ANNOTATION_ID = "stakeholder";
	public static final String SCORE_METADATA_PROPERTY = "mca:score";
	public static Set<String> criterionAnnotations;

	static {
		criterionAnnotations = new HashSet<>();
		criterionAnnotations.add("cost");
		criterionAnnotations.add("benefit");
	}

	public static enum Method {
		EVAMIX, ELECTRE_I, ELECTRE_Is, ELECTRE_Iv, ELECTRE_II, ELECTRE_III, ELECTRE_IV, ELECTRE_TRI, TOPSIS
	}

	public MCAComponent() {
	}

	/**
	 * Build the value concept for a criterion that will be looked for in each
	 * stakeholder to rank the criterion's importance. It will be a "value of
	 * <i>criterion</i> for assessment of <i>finalTarget</i>".
	 * 
	 * @param criterionObservable
	 * @param targetObservable    may be null for simple object alternative ranking
	 * @return the finished value concept
	 */
	public static IObservable getCriterionValueObservable(IObservable criterionObservable,
			@Nullable IObservable targetObservable, IMonitor monitor) {

		IObservable.Builder builder = ((Observable) criterionObservable).getBuilder(monitor)
				.as(UnarySemanticOperator.VALUE);
		if (targetObservable != null) {
			builder = builder.withGoal(((Observable) targetObservable).getBuilder(monitor)
					.as(UnarySemanticOperator.ASSESSMENT).buildConcept());

		}
		return builder.buildObservable();

	}

	public static List<IAlternative> rank(List<IAlternative> alternatives, Collection<ICriterion> criteria,
			IStakeholder observer, Method method, boolean normalize, IMonitor monitor) {

		MCAAssessment assessment = new MCAAssessment();
		Results results = null;

		for (ICriterion criterion : criteria) {
			assessment.declareCriterion(criterion.getName(), criterion.getDataType(), criterion.getType());
		}
		for (IAlternative alternative : alternatives) {
			if (!alternative.isValid()) {
				continue;
			}
			assessment.declareAlternative(alternative.getId());
		}
		for (IAlternative alternative : alternatives) {
			if (!alternative.isValid()) {
				continue;
			}
			for (ICriterion criterion : criteria) {
				assessment.setCriterionValue(alternative.getId(), criterion.getName(), alternative.getValue(criterion));
			}
		}
		for (ICriterion criterion : criteria) {
			// TODO invert weights for Evamix
			assessment.setCriterionWeight(criterion.getName(), observer.getWeight(criterion));
		}

		switch (method) {
		case EVAMIX:
			results = assessment.runEvamix(monitor);
			break;
		case ELECTRE_I:
		case ELECTRE_II:
		case ELECTRE_III:
		case ELECTRE_IV:
		case ELECTRE_Is:
		case ELECTRE_Iv:
		case ELECTRE_TRI:
		case TOPSIS:
			throw new KlabUnimplementedException("Unimplemented ranking method " + method);
		default:
			break;
		}

		if (results != null) {
			Map<String, Double> concordances = results.getConcordances(normalize);
			for (IAlternative alternative : alternatives) {
				((Alternative) alternative).setScore(concordances.get(alternative.getId()));
			}
		}

		return alternatives;
	}

	/**
	 * Extract the elements of a MCA assessment from the passed artifact and put
	 * them in the passed collections. If something goes wrong or the available data
	 * do not support a MCA, warn through the context monitor and return false.
	 * 
	 * @param target
	 * @param alternatives
	 * @param criteria
	 * @param observers
	 * @param method
	 * @param context
	 * @return
	 */
	public static boolean extractAssessment(IArtifact target, List<IAlternative> alternatives,
			Collection<ICriterion> criteria, List<IStakeholder> observers, Method method,
			IContextualizationScope context) {
		// TODO Auto-generated method stub

		// check out the model for indication of the likely way to go
		boolean outputsHaveCriteria = false;
		boolean dependenciesHaveCriteria = false;
		boolean alternativesHaveCriteria = false;
		IArtifact alternativesArtifact = null;
		IArtifact stakeholdersArtifact = null;
		IConcept assessedType = null;
		List<CriterionDescriptor> criterionDescriptors = new ArrayList<>();
		Set<IConcept> stakeholderStatedObservables = new HashSet<>();

		if (target instanceof IObservation && !target.getType().isCountable()) {
			assessedType = ((IObservation) target).getObservable().getType();
		}

		for (int i = 1; i < context.getModel().getObservables().size(); i++) {
			CriterionDescriptor descriptor = getCriterionAnnotation(context.getModel().getObservables().get(i), true,
					false);
			if (descriptor != null) {
				outputsHaveCriteria = true;
				criterionDescriptors.add(descriptor);
			}
		}

		for (IObservable observable : context.getModel().getDependencies()) {
			if (getAnnotation(observable, ALTERNATIVE_ANNOTATION_ID) != null) {
				alternativesArtifact = context.getArtifact(observable.getName());
				if (alternativesArtifact == null || !(alternativesArtifact instanceof IObservationGroup)
						|| alternativesArtifact.groupSize() < 2) {
					context.getMonitor()
							.warn("MCA: alternatives must be object artifacts with at least two observations");
					return false;

				}
			} else if (getAnnotation(observable, STAKEHOLDER_ANNOTATION_ID) != null) {
				stakeholdersArtifact = context.getArtifact(observable.getName());
				if (stakeholdersArtifact == null || !(stakeholdersArtifact instanceof ObservationGroup)
						|| alternativesArtifact.groupSize() < 1) {
					context.getMonitor()
							.warn("MCA: stakeholders must be object artifacts with at least one observations");
					return false;

				}
			} else {
				CriterionDescriptor descriptor = getCriterionAnnotation(observable, false, false);
				if (descriptor != null) {
					dependenciesHaveCriteria = true;
					criterionDescriptors.add(descriptor);
				}
			}
		}

		if (!outputsHaveCriteria && !dependenciesHaveCriteria) {

			// alternatives may have criteria annotated
			if (alternativesArtifact != null) {
				for (IArtifact alternative : alternativesArtifact) {
					if (alternative instanceof IDirectObservation) {
						for (IState state : ((IDirectObservation) alternative).getStates()) {
							CriterionDescriptor crit = getCriterionAnnotation(state.getObservable(), false, true);
							if (crit != null) {
								criterionDescriptors.add(crit);
								alternativesHaveCriteria = true;
							}
						}
						alternatives.add(new Alternative((IDirectObservation) alternative));
					} else {
						context.getMonitor().error("MCA: stated alternatives must be direct observations. Exiting.");
						return false;
					}
				}
			} else {
				context.getMonitor().error("MCA: no criteria annotated in model or in alternatives. Exiting.");
				return false;
			}
		}

		if (criterionDescriptors.size() < 2) {
			context.getMonitor()
					.warn("MCA: at least two criteria need to be present to proceed with assessment. Exiting.");
			return false;
		}

		boolean globalSingleWeights = false;
		boolean globalMultipleWeights = false;

		/*
		 * build all descriptors: if 1+ criterion annotations have weights, don't try to
		 * observe the criteria in stakeholders.
		 */
		Set<String> criteriaIds = new HashSet<>();
		for (CriterionDescriptor c : criterionDescriptors) {

			if (criteriaIds.contains(c.getName())) {
				context.getMonitor().error("MCA: criterion " + c.getName() + " has multiple definitions");
				return false;
			}

			Criterion criterion = new Criterion(c.observable,
					c.annotation.getName().equals("benefit") ? Type.BENEFIT : Type.COST);

			if (!c.inAlternative && !c.input) {
				IArtifact state = context.getArtifact(c.getName());
				if (state == null || !(state instanceof IState) || !c.observable.is(IKimConcept.Type.QUANTIFIABLE)
						|| !c.observable.is(IKimConcept.Type.ORDERING)) {
					context.getMonitor()
							.error("MCA: criterion " + c.getName() + " is not a numeric or ranked observation");
					return false;
				}
			}

			if (c.annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME) != null) {
				Object value = c.annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME);
				if (value instanceof Number) {
					globalSingleWeights = true;
					c.globalWeight = ((Number) value).doubleValue();
				} else if (value instanceof Map) {
					globalMultipleWeights = true;
					for (Object o : ((Map<?, ?>) value).keySet()) {
						if (o instanceof IConcept && ((Map<?, ?>) value).get(o) instanceof Number) {
							stakeholderStatedObservables.add((IConcept) o);
							if (c.weights.containsKey(o)) {
								context.getMonitor().error("MCA: multiply defined criterion weight in " + c.getName());
								return false;
							}
							c.weights.put((IConcept) o, ((Number) ((Map<?, ?>) value).get(o)).doubleValue());
						} else {
							context.getMonitor().error("MCA: misused criterion weight map for " + c.getName()
									+ ": value must specify a map of weights associated to concepts describing stakeholders");
							return false;
						}
					}
				} else {
					context.getMonitor().error("MCA: misused criterion annotation for " + c.getName()
							+ ": value must specify a numeric weight or a map of weights associated to concepts describing stakeholders");
					return false;
				}
			}

			criteria.add(criterion);

		}

		if (globalMultipleWeights && globalSingleWeights) {
			context.getMonitor().error(
					"MCA: misused criterion annotations: only maps or single weights must be specified within an assessment");
			return false;
		}

		/*
		 * if stakeholders are defined through annotations, build them and configure
		 * them
		 */
		if (stakeholdersArtifact != null) {
			if (globalSingleWeights || globalMultipleWeights) {
				context.getMonitor().error(
						"MCA: cannot define stakeholder artifacts when implicit weights are used in criteria annotations. Exiting.");
				return false;
			}

			for (IArtifact stakeholder : stakeholdersArtifact) {
				if (stakeholder instanceof ISubject) {
					Stakeholder st = new Stakeholder((ISubject) stakeholder);
					// TODO make observations of criterion values
					for (CriterionDescriptor cd : criterionDescriptors) {
						for (IState state : ((IDirectObservation) stakeholder).getStates()) {
							if (state.getObservable().getType().is(cd.getCriterionType())) {
								// add weight
							}
						}
					}
				} else {
					context.getMonitor().error("MCA: stated stakeholders must be direct observations. Exiting.");
					return false;
				}
			}
		} else if (globalMultipleWeights) {

			boolean inconsistencyWarning = false;
			// define stakeholders based on each criterion's storage; warn about missing
			// weights or
			// uneven specs. Stakeholder are marked as not present in context so that they
			// can be
			// created.
			for (IConcept sh : stakeholderStatedObservables) {
				Stakeholder stakeholder = new Stakeholder(Observable.promote(sh));
				observers.add(stakeholder);
				for (CriterionDescriptor c : criterionDescriptors) {
					if (c.weights.containsKey(sh)) {
						stakeholder.setWeight(c.getName(), c.globalWeight);
					} else if (!inconsistencyWarning) {
						context.getMonitor().warn(
								"MCA: inconsistent weight specifications: some stakeholder types are not represented in all criteria");
						inconsistencyWarning = true;
					}
				}
			}

		} else if (globalSingleWeights) {

			// define a single objective stakeholder based on each criterion's global
			// weight; warn about
			// missing weights or
			// uneven specs
			Stakeholder stakeholder = new Stakeholder("global");
			stakeholder.setObjective(true);

			for (CriterionDescriptor c : criterionDescriptors) {
				stakeholder.setWeight(c.getName(), c.globalWeight);
			}

			observers.add(stakeholder);

		}

		/*
		 * go for the alternatives. Each should have an observation of each criterion.
		 * If not, we should have a configurable strategy, but for now assume it's
		 * unrankable and warn.
		 */
		if (alternativesArtifact == null && target instanceof ObservationGroup) {
			alternativesArtifact = target;
		}

		// can still be null, like in spatial MCA
		if (alternativesArtifact != null) {
			for (IArtifact alt : alternativesArtifact) {
				Alternative alternative = new Alternative((IDirectObservation) alt);
				for (ICriterion criterion : criteria) {
					if (criterion.isDistributed()) {
						// TODO set from a view of the distributed criteria
					} else {
						for (IState state : ((IDirectObservation) alt).getStates()) {
							if (state.getObservable().getName().equals(criterion.getName())) {
								alternative.setValue(criterion, state.aggregate(context.getScale(), Double.class));
							}
						}
					}
				}
				alternatives.add(alternative);
			}
		}
		/*
		 * Validate everthing: criteria must be represented in stakeholders,
		 * alternatives should have all criteria; fill gaps with warnings.
		 */

		return true;
	}

	public static IAnnotation getAnnotation(IObservable observable, String id) {
		for (IAnnotation annotation : ((Observable) observable).getAnnotations()) {
			if (annotation.getName().equals(id)) {
				return annotation;
			}
		}
		return null;
	}

	static class CriterionDescriptor {

		IObservable observable;
		IAnnotation annotation;
		boolean input;
		boolean inAlternative;
		double globalWeight = Double.NaN;

		// temporary storage of weight by stakeholder
		Map<IConcept, Double> weights = new HashMap<>();

		public String getName() {
			return observable.getName();
		}

		public IConcept getCriterionType() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	private static CriterionDescriptor getCriterionAnnotation(IObservable observable, boolean isInput,
			boolean isInAlternative) {
		CriterionDescriptor descriptor = null;
		for (IAnnotation annotation : ((Observable) observable).getAnnotations()) {
			if (criterionAnnotations.contains(annotation.getName())) {
				descriptor = new CriterionDescriptor();
				descriptor.annotation = annotation;
				descriptor.input = isInput;
				descriptor.inAlternative = isInAlternative;
				descriptor.observable = observable;
				return descriptor;
			}
		}
		return null;
	}

}
