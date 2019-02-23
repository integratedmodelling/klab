package org.integratedmodelling.mca.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.geospace.processing.MapClassifier;
import org.integratedmodelling.klab.components.geospace.processing.MapClassifier.MapClass;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.mca.MCAComponent;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;
import org.integratedmodelling.mca.api.ICriterion.Type;
import org.integratedmodelling.mca.api.IStakeholder;
import org.integratedmodelling.mca.model.Alternative;
import org.integratedmodelling.mca.model.Criterion;
import org.integratedmodelling.mca.model.Stakeholder;

/**
 * Builds the MCA objects from a k.LAB context and model and sets back MCA
 * results into the context.
 * 
 * @author ferdinando.villa
 *
 */
public class MCAContext {

	static public enum Specification {
		InlineSingle, InlineMultiple, Contextual
	}

	private List<IAlternative> alternatives = new ArrayList<>();
	private List<IStakeholder> stakeholders = new ArrayList<>();
	private List<ICriterion> criteria = new ArrayList<>();
	private Specification specification;
	private boolean computable = true;
	private MapClassifier classifier;

	private final static String DEFAULT_STAKEHOLDER = "___default_stakeholder";

	public MCAContext(IObservable concordanceObservable, IRuntimeContext context, ILocator locator, int levels) {

		IObservable stakeholderObservable = null;
		IObservable alternativeObservable = null;

		// extract from annotations
		for (IObservable observable : context.getModel().getDependencies()) {

			IAnnotation annotation = getCriterionAnnotation(observable);

			if (annotation != null) {

				if (!observable.is(IKimConcept.Type.QUALITY) && !observable.is(IKimConcept.Type.TRAIT)) {
					throw new KlabValidationException("mca: criteria must be qualities");
				}

				// build criterion
				ICriterion criterion = buildCriterion(observable, context);

				Object value = annotation.get(IKimAnnotation.DEFAULT_PARAMETER_NAME);
				if (value instanceof Number) {

					if (specification != null && specification != Specification.InlineSingle) {
						throw new KlabValidationException("cannot mix strategies in criterion annotations");
					}
					specification = Specification.InlineSingle;

					// context is stakeholder, absolute single concordance
					getOrCreate(DEFAULT_STAKEHOLDER).setWeight(criterion.getName(), ((Number) value).doubleValue());

				} else if (value instanceof Map) {

					if (specification != null && specification != Specification.InlineMultiple) {
						throw new KlabValidationException("cannot mix strategies in criterion annotations");
					}

					// build or verify stakeholders from concepts and weights in map, subjective
					// concordances
					buildStakeholders((Map<?, ?>) value, observable, context, specification == null);

					specification = Specification.InlineMultiple;

				} else {

					if (!annotation.isEmpty()) {
						throw new KlabValidationException(
								"unrecognized parameters in criterion annotation: can only be empty, number or map");
					}
					if (specification != null && specification != Specification.Contextual) {
						throw new KlabValidationException("cannot mix strategies in criterion annotations");
					}

					specification = Specification.Contextual;

				}

			} else {

				annotation = MCAComponent.getAnnotation(observable, MCAComponent.STAKEHOLDER_ANNOTATION_ID);
				if (annotation != null) {
					stakeholderObservable = observable;
				} else {
					annotation = MCAComponent.getAnnotation(observable, MCAComponent.ALTERNATIVE_ANNOTATION_ID);
					if (annotation != null) {
						alternativeObservable = observable;
					}
				}

			}
		}

		if (specification != null) {
			switch (specification) {
			case Contextual:
				// lookup stakeholders with values for criteria
				break;
			case InlineMultiple:
				// create stakeholders from specs with values for criteria
				for (IStakeholder stakeholder : this.stakeholders) {
					((Stakeholder) stakeholder).setSubject((ISubject) context
							.newObservation(stakeholder.getObservable(), stakeholder.getName(), Scale.create()));
					// TODO hostia there's more - criteria values
					for (ICriterion criterion : this.criteria) {
//						((ISubject)stakeholder.getSubject()).observe(urn, scenarios)
					}
				}
				break;
			case InlineSingle:
				// store weights for single "objective" concordance assessment
				break;
			default:
				break;

			}
		}

		// make missing observations
		if (this.stakeholders.isEmpty()) {

			if (stakeholderObservable == null) {
				throw new KlabValidationException("mca: cannot establish stakeholders. Must provide annotations for"
						+ " either implicit stakeholder definition or to identify an artifact as stakeholder.");
			}

		}

		// if needed, classify map to build alternatives or build them from an artifact
		if (alternativeObservable == null) {

			// build alternatives from the context
			buildDistributedAlternatives(context, locator, levels);

		} else {

			// get the alternatives and ensure enough of them contain values for ranking. If
			// not and the criteria
			// are distributed, give them states based on views.
			IArtifact alternativesArtifact = context.getArtifact(alternativeObservable.getLocalName());
			if (!(alternativesArtifact instanceof IObjectArtifact)) {
				throw new KlabValidationException("mca: alternatives are not suitable for ranking");
			}
			if (alternativesArtifact.isEmpty() || alternativesArtifact.groupSize() < 2) {
				context.getMonitor().warn("mca: not enough alternatives for ranking");
				computable = false;
			}

		}

	}

	private void buildDistributedAlternatives(IRuntimeContext context, ILocator locator, int levels) {

		List<IState> states = new ArrayList<>();
		for (ICriterion criterion : criteria) {
			if (criterion.getState() == null || !criterion.isDistributed()) {
				throw new KlabValidationException("mca: implicit alternatives require distributed criteria");
			}
			states.add(criterion.getState());
		}

		this.classifier = new MapClassifier(states, levels, context, locator);
		classifier.classify();

		for (MapClass clas : classifier.getClasses()) {
			alternatives.add(new Alternative(clas));
		}
	}

	public Specification getSpecification() {
		return this.specification;
	}

	public boolean isComputable() {
		return computable && this.alternatives.size() >= 2 && this.stakeholders.size() >= 1
				&& this.criteria.size() >= 2;
	}

	private Stakeholder getOrCreate(String id) {

		for (IStakeholder stakeholder : stakeholders) {
			if (stakeholder.getName().equals(id)) {
				return (Stakeholder) stakeholder;
			}
		}

		Stakeholder ret = new Stakeholder(id);
		this.stakeholders.add(ret);
		return ret;

	}

	private ICriterion buildCriterion(IObservable observable, IRuntimeContext context) {

		ICriterion ret = null;
		IArtifact crit = context.getArtifact(observable.getLocalName());
		if (crit != null) {
			if (!(crit instanceof IState)) {
				throw new KlabValidationException("mca: criteria must be qualities");
			}
			this.criteria.add(ret = new Criterion((IState) crit,
					getCriterionAnnotation(observable).getName().equals("cost") ? Type.COST : Type.BENEFIT));
		} else {
			this.criteria.add(ret = new Criterion(observable,
					getCriterionAnnotation(observable).getName().equals("cost") ? Type.COST : Type.BENEFIT));
		}

		return ret;
	}

	private void buildStakeholders(Map<?, ?> value, IObservable observable, IRuntimeContext context, boolean b) {

		for (Object o : value.keySet()) {
			if (!(o instanceof IConcept)
					|| !(((IConcept) o).is(IKimConcept.Type.SUBJECT) || ((IConcept) o).is(IKimConcept.Type.AGENT))) {
				throw new KlabValidationException("mca: stakeholder observable must be a concept specifying a subject");
			}
			if (!(value.get(o) instanceof Number)) {
				throw new KlabValidationException(
						"mca: stakeholder types must be matched to numeric weights in the criterion annotation");
			}

			IObservable oobs = Observable.promote((IConcept) o);

			Stakeholder stakeholder = getOrCreate(Concepts.INSTANCE.getDisplayName(oobs));
			stakeholder.setObservable(oobs);
			stakeholder.setWeight(observable.getLocalName(), ((Number) value.get(o)).doubleValue());
		}

	}

	private IAnnotation getCriterionAnnotation(IObservable observable) {
		for (IAnnotation annotation : ((Observable) observable).getAnnotations()) {
			if (MCAComponent.criterionAnnotations.contains(annotation.getName())) {
				return annotation;
			}
		}
		return null;
	}

	// private IAnnotation getStakeholderAnnotation(IObservable observable) {
	// for (IAnnotation annotation : ((Observable) observable).getAnnotations()) {
	// if (annotation.getName().equals("stakeholder")) {
	// return annotation;
	// }
	// }
	// return null;
	// }
	//
	// private IAnnotation getAlternativeAnnotation(IObservable observable) {
	// for (IAnnotation annotation : ((Observable) observable).getAnnotations()) {
	// if (annotation.getName().equals("alternative")) {
	// return annotation;
	// }
	// }
	// return null;
	// }

	public List<IAlternative> getAlternatives() {
		return alternatives;
	}

	public List<IStakeholder> getStakeholders() {
		return stakeholders;
	}

	public List<ICriterion> getCriteria() {
		return criteria;
	}

	public void distributeResults(Results results, IState ret) {
		if (classifier != null) {
			Map<String, Double> res = results.getConcordances(true);
			double[] cvals = new double[alternatives.size()];
			Arrays.fill(cvals, Double.NaN);
			int i = 0;
			for (IAlternative da : alternatives) {
				cvals[i++] = res.get(da.getId());
			}
			classifier.distributeResults(ret, cvals);
		} // TODO else...
	}
}
