package org.integratedmodelling.landcover.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimDate;
import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.mediation.Quantity;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.data.resources.ResourceCalculator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.IntelligentMap;
import org.integratedmodelling.klab.utils.Range;

/**
 * Demand-driven allocation model for conceptual. Too much of CLUE's original
 * code isn't worth keeping, but we use the generalized CLUE concepts and
 * reimplement its central allocation algorithm wrt. Verveji et al. 2018.
 * <p>
 * Key improvements over CLUE, apart from running within a semantic and
 * integrating platform, are:
 * <ul>
 * <li>any spatial extent is supported (including vector);</li>
 * <li>all configuration variables (demand, elasticity and deviation) can now be
 * dynamic and optionally provided by externally linked models</li>
 * <li>the land cover configuration rules are matched using the reasoner so that
 * a single, hierarchial land cover classification can be supported at any level
 * of detail;</li>
 * <li>transition rules can use, along with dates and ages, arbitrary
 * expressions that include access to the neighborhood in grids, so that CA-like
 * rules can easily be defined.</li>
 * </ul>
 * <p>
 * TODO generalize semantics beyond landcover, as the concepts here are way more
 * general than LULC alone. Do this after closing the deliverable to avoid
 * misunderstandings.
 * 
 * @author Ferd
 *
 */
public class LandcoverChange {

	IState landCoverType;
	IState landCoverAge;

	/*
	 * these two are crucial to the allocation algorithm and are, strangely enough,
	 * immutable constants in CLUE.
	 */
	double maxShift = 0.05;
	double shiftStep = 0.001;

	/*
	 * one of these supplies the probability distribution, either for the concept
	 * itself or for each possible transition.
	 */
	IResourceCalculator<?> suitabilityCalculator = null;
	IResourceCalculator<?> transitionCalculator = null;

	/*
	 * all factors, including elasticities and allowed deviation, can be made
	 * time-dependent (unlike in CLUE where only demand is) and linked to external
	 * models. Using intelligent maps enables transitive attribution of all
	 * parameters through the class hierarchy and specialization of behavior for
	 * more specific types.
	 */
	IntelligentMap<TimeDependentFactor> elasticity = new IntelligentMap<>();
	IntelligentMap<TimeDependentFactor> deviation = new IntelligentMap<>();
	IntelligentMap<TimeDependentFactor> demand = new IntelligentMap<>();
	LandcoverTransitionTable transitionTable = new LandcoverTransitionTable();

	private IProcess process;
	private IRuntimeScope scope;
	private IMonitor monitor;
	private double totalArea;
	private boolean firstRun = true;

	public LandcoverChange(IProcess targetProcess) {
		this.process = targetProcess;
	}

	/**
	 * The scope that gets here is assumed to select the initialized state at
	 * T=initialization. So call from within resolve(), not at contextualizer
	 * construction.
	 * 
	 * @param parameters
	 * @param scope
	 */
	public void configure(IParameters<String> parameters, IRuntimeScope scope) {

		this.scope = scope;
		this.monitor = scope.getMonitor();

		/*
		 * just in case
		 */
		this.maxShift = parameters.get("shift", maxShift);
		this.shiftStep = parameters.get("step", shiftStep);

		/*
		 * recover or create the states
		 */
		IConcept target = scope.getTargetSemantics().getType();
		if (target.is(Type.CHANGE)) {
			target = Observables.INSTANCE.getInherentType(target);
		} else {
			for (IConcept c : Observables.INSTANCE.getAffectedQualities(target)) {
				if (scope.getArtifact(target, IState.class) != null) {
					target = c;
					break;
				}
			}
		}

		this.landCoverType = scope.getArtifact(target, IState.class);
		if (this.landCoverType == null) {
			throw new KlabResourceNotFoundException("Cannot find target artifact " + target);
		}

		boolean initializeAge = false;
		// TODO find or create age state

		/*
		 * determine initial land distribution and total covered area
		 */
		preprocessLandcover(initializeAge);

		/*
		 * suitability parameters
		 */
		if (parameters.containsKey("suitability")) {
			IResource suitability = Resources.INSTANCE.resolveResource(parameters.get("suitability").toString(),
					scope.getModel() == null ? null : scope.getModel().getNamespace().getProject());
			this.suitabilityCalculator = ResourceCalculator.create(suitability, Object.class);
		} else if (parameters.containsKey("change")) {
			IResource suitability = Resources.INSTANCE.resolveResource(parameters.get("change").toString(),
					scope.getModel() == null ? null : scope.getModel().getNamespace().getProject());
			this.transitionCalculator = ResourceCalculator.create(suitability, Object.class);
		}

		/*
		 * these will properly use annotations as well as configuration specs
		 */
		this.elasticity = readTimeDependentFactors(parameters.get("elasticities"), "elasticity");
		this.demand = readTimeDependentFactors(parameters.get("demand"), "demand");
		this.deviation = readTimeDependentFactors(parameters.get("deviations"), "deviation");

		/*
		 * transition table. Default will just say yes to all transitions.
		 */
		if (parameters.containsKey("transitions")) {
			this.transitionTable.parse(parameters.get("transitions", IKimTable.class));
		}
	}

	private void preprocessLandcover(boolean initializeAge) {
		this.totalArea = 0.0;
		Map<IConcept, Double> areaDistribution = new HashMap<>();
		for (ILocator locator : this.scope.getScale()) {
			Object value = this.landCoverType.get(locator);
			if (Observations.INSTANCE.isData(value)) {
				double area = Observations.INSTANCE.getArea(locator);
				totalArea += area;
				if (value instanceof IConcept) {
					areaDistribution.put((IConcept) value,
							areaDistribution.containsKey(value) ? (areaDistribution.get(value) + area) : area);
				}
				if (initializeAge) {
					this.landCoverAge.set(locator, 0);
				}
			}
		}
	}

	/**
	 * Run an allocation cycle for the passed time extent.
	 * 
	 * @param extent
	 */
	public void run(ITime extent) {

		if (!firstRun) {
			// TODO process landcover again to update area distributions for this step
		}

		firstRun = false;
	}

	/**
	 * Read a map of correspondence of concepts to time-dependent parameters, also
	 * using any annotations in the dependencies of the model.
	 * 
	 * @param object
	 * @param annotationName
	 * @return
	 */
	private IntelligentMap<TimeDependentFactor> readTimeDependentFactors(Object object, String annotationName) {

		IntelligentMap<TimeDependentFactor> ret = new IntelligentMap<>();

		/*
		 * start with tagged dependencies
		 */
		if (this.scope.getModel() != null) {
			for (IObservable dependency : this.scope.getModel().getDependencies()) {
				if (Annotations.INSTANCE.hasAnnotation(dependency, annotationName)) {
					IAnnotation annotation = Annotations.INSTANCE.getAnnotation(dependency, annotationName);
					IArtifact state = this.scope.getArtifact(dependency.getName());
					if (state instanceof IState) {
						TimeDependentFactor factor = new TimeDependentFactor();
						factor.state = (IState) state;
						for (Object o : annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME, List.class)) {
							if (o instanceof IKimConcept) {
								IConcept concept = Concepts.INSTANCE.declare((IKimConcept) o);
								if (concept != null) {
									ret.put(concept, factor);
								}
							}
						}
					}
				}
			}
		}

		// store all pairs <time, areavalue> pertaining to each concept, using 0 for
		// unspecified time. We merge and validate specs later
		Map<IConcept, List<TimeDependentFactor>> specs = new HashMap<>();
		Range zeroone = Range.create(0, 1, false);

		if (object instanceof Map) {

			for (Map.Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
				if (entry.getKey() instanceof IKimConcept) {
					IConcept concept = Concepts.INSTANCE.declare((IKimConcept) entry.getKey());
					if (concept != null) {

						TimeDependentFactor factor = new TimeDependentFactor();

						if (entry.getValue() instanceof Number) {
							if (!zeroone.contains(((Number) entry.getValue()).doubleValue())) {
								throw new KlabValidationException("numeric areal proportions must be between 0 and 1");
							}

							factor.constval = ((Number) entry.getValue()).doubleValue();
							factor.isAbsolute = false;

						} else if (entry.getValue() instanceof IKimQuantity) {
							Quantity q = Quantity.create(((IKimQuantity) entry.getValue()).getValue(),
									Unit.create(((IKimQuantity) entry.getValue()).getUnit()));
							factor.constval = q.in(Units.INSTANCE.SQUARE_METERS);
							factor.isAbsolute = true;
						}

						if (factor.constval == 0) {
							throw new KlabValidationException("invalid areal specifications in " + annotationName);
						}

						if (specs.get(concept) == null) {
							List<TimeDependentFactor> list = new ArrayList<>();
							list.add(factor);
							specs.put(concept, list);
						} else {
							specs.get(concept).add(factor);
						}

					}
				}
			}

		} else if (object instanceof IKimTable) {

			for (IKimClassifier[] row : ((IKimTable) object).getRows()) {

				if (row[0] instanceof IKimConcept) {
					IConcept concept = Concepts.INSTANCE.declare((IKimConcept) row[0]);
					if (concept != null) {

						TimeDependentFactor factor = new TimeDependentFactor();

						IKimClassifier time = row[1];
						IKimClassifier value = row[2];

						if (time instanceof IKimDate) {
							factor.timepoint = ((IKimDate) time).getDate().getTime();
						}

						if (value.getNumberMatch() != null) {
							if (!zeroone.contains(value.getNumberMatch())) {
								throw new KlabValidationException("numeric areal proportions must be between 0 and 1");
							}

							factor.constval = ((Number) value).doubleValue();
							factor.isAbsolute = false;

						} else if (value.getQuantityMatch() != null) {
							Quantity q = Quantity.create(((IKimQuantity) value).getValue(),
									Unit.create(((IKimQuantity) value).getUnit()));
							factor.constval = q.in(Units.INSTANCE.SQUARE_METERS);
							factor.isAbsolute = true;
						}

						if (factor.constval == 0) {
							throw new KlabValidationException("invalid areal specifications in " + annotationName);
						}

						if (specs.get(concept) == null) {
							List<TimeDependentFactor> list = new ArrayList<>();
							list.add(factor);
							specs.put(concept, list);
						} else {
							specs.get(concept).add(factor);
						}

					}
				}

			}

		}

		return mergeAndValidate(specs, ret, annotationName);
	}

	private IntelligentMap<TimeDependentFactor> mergeAndValidate(Map<IConcept, List<TimeDependentFactor>> specs,
			IntelligentMap<TimeDependentFactor> ret, String desc) {

		for (IConcept concept : specs.keySet()) {
			List<TimeDependentFactor> list = specs.get(concept);
			if (ret.containsKey(concept)) {
				throw new KlabValidationException(
						"duplicate specifications for " + desc + ": both states and configuration specified");
			}

			if (list.size() == 1) {
				ret.put(concept, list.get(0));
			} else {
				// absolute or not must be compatible; specs are in order of definition so first
				// and last time can be set to overall scale. Line up T and demand, then use
				// linear
				// interpolation for 2 points, spline for 3 or more.
			}
		}

		return ret;
	}

	double getFactor(IntelligentMap<TimeDependentFactor> source, ITime time) {
		return 0;
	}

}
