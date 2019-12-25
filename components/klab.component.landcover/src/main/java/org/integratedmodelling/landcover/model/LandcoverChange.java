package org.integratedmodelling.landcover.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;
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
import org.integratedmodelling.klab.api.data.IResource.Attribute;
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
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.IntelligentMap;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.landcover.model.LandcoverTransitionTable.TransitionRule;

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
 * of detail (demand specifications must use disjoint concepts);</li>
 * <li>transition rules can use, along with dates and ages, arbitrary
 * expressions that include access to the neighborhood in grids, so that CA-like
 * rules can easily be defined.</li>
 * <li>landcover age can be imported from another model or dataset, and exported
 * as an output of the model. If a dependency is tagged as age, the model
 * outputs the 'change in ' its observable.</li>.
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

	enum AllocationStrategy {
		/**
		 * Pick the target landcover stochastically using the suitability distribution,
		 * ignoring the probability threshold, and only convert if the transition table
		 * allows it. This is the CLUE default and ours.
		 */
		STOCHASTIC,
		/**
		 * Choose the most likely conversion; if the associated probability is below the
		 * threshold or the transition rules prevent it, do not convert.
		 */
		CONSERVATIVE_DETERMINISTIC,
		/**
		 * Choose the most likely conversion first, then if none is possible due to
		 * transition rules, go to the next most likely until a conversion is found or
		 * the probability threshold is crossed. This will produce more conversions than
		 * the conservative and can model highly dynamic situations.
		 */
		GREEDY_DETERMINISTIC
	};

	AllocationStrategy allocationStrategy = AllocationStrategy.STOCHASTIC;
	int randomSeed = 123894;

	/**
	 * No conversion in deterministic modes will take place if the probability
	 * associated is less than this threshold.
	 */
	double probabilityThreshold = .01;

	IState landCoverType;
	IState landCoverAge;

	/*
	 * these two are crucial to the allocation algorithm and are, strangely enough,
	 * immutable constants in CLUE.
	 */
	double maxShift = 0.05;
	double shiftStep = 0.001;

	/*
	 * default max iterations. CLUE default is 2000, seems high.
	 */
	int maxIterations = 2000;

	/*
	 * default parameters for the transition behavior
	 */
	boolean transitionsAreTransitive = true;
	boolean defaultTransitionPossible = false;

	/*
	 * one of these supplies the probability distribution, either for the concept
	 * itself or for each possible transition.
	 */
	IResourceCalculator suitabilityCalculator = null;
	IResourceCalculator transitionCalculator = null;

	/*
	 * all factors, including elasticities and allowed deviation, can be made
	 * time-dependent (unlike in CLUE where only demand is) and linked to external
	 * models. Using intelligent maps enables transitive attribution of all
	 * parameters through the class hierarchy and specialization of behavior for
	 * more specific types.
	 */
	IntelligentMap<TimeDependentFactor> elasticity = new IntelligentMap<>(new TimeDependentFactor(0.0));
	IntelligentMap<TimeDependentFactor> deviation = new IntelligentMap<>(new TimeDependentFactor(0.05));
	Map<IConcept, TimeDependentFactor> demand = new HashMap<>();

	/*
	 * default transition table allows every transition and contains no table.
	 */
	LandcoverTransitionTable transitionTable = new LandcoverTransitionTable(false, true);

	class Conversion {
		IConcept from;
		IConcept to;
		double probability;
	}

	private IProcess process;
	private IRuntimeScope scope;
	private IMonitor monitor;
	private double totalArea;
	private double areaFactor;
	private long extentFactor;
	private boolean firstRun = true;
	private Map<IConcept, Double> distribution;
	private Set<IConcept> configuredConcepts = new HashSet<>();
	
	public LandcoverChange(IProcess targetProcess) {
		this.process = targetProcess;
	}

	/*
	 * * ---- Main algorithm & components ---- *
	 */

	/**
	 * Run an allocation cycle for the passed scope, which must be tuned to the
	 * desired time extent and space. Our current distributions are tuned to the
	 * previous state, initialization when this is called the first time.
	 * 
	 * @param extent
	 */
	public void run(IRuntimeScope scope) {

		int iterations = 0;
		while (!demandMet(scope.getScale().getTime()) && iterations < maxIterations) {

			Map<IConcept, Double> demandWeights = computeDemandWeights(scope.getScale().getTime());

			for (ILocator locator : scope.getScale()) {

				IConcept current = landCoverType.get(locator, IConcept.class);
				double elasticity = getElasticity(current, locator);

				if (Observations.INSTANCE.isData(current) && elasticity < 1) {

					List<Conversion> conversions = getPossibleConversions(current, demandWeights.get(current),
							elasticity, getNeighborhoodWeight(locator), scope, locator);

					Conversion chosen = null;
					if (conversions.size() == 1) {
						chosen = conversions.get(0);
					} else if (conversions.size() > 1) {
						chosen = pickConversion(conversions);
					}

					if (chosen != null) {
						applyConversion(chosen, locator);
					}
				}

			}
			iterations++;
		}

		if (iterations >= maxIterations) {
			// hostia.
		}

	}

	/**
	 * Called only if >1 conversions are possible. If in stochastic mode, pick one
	 * according to probability, otherwise pick the most likely.
	 * 
	 * @param conversions
	 * @return
	 */
	private Conversion pickConversion(List<Conversion> conversions) {
		// TODO Auto-generated method stub
		return conversions.get(0);
	}

	/**
	 * Finalize a conversion, including the setting of age, frequency, iteration
	 * validation criteria, and, if requested, event detector data.
	 */
	private void applyConversion(Conversion candidate, ILocator locator) {
		// TODO Auto-generated method stub

	}

	private List<Conversion> getPossibleConversions(IConcept current, double demandWeight, double elasticity,
			double neighborhoodWeight, IRuntimeScope scope, ILocator locator) {

		List<Conversion> ret = new ArrayList<>();

		/*
		 * The transition table will check if a transition to the target type exists,
		 * choosing according to configuration and potentially including more generic
		 * transitions.
		 */
		for (IConcept candidate : getCandidates(current, demandWeight, elasticity, neighborhoodWeight, scope,
				locator)) {
			for (TransitionRule transition : transitionTable.getTransitions(current, candidate)) {
				if (transition.isPossible(locator, scope)) {
					// add conversion
				}
			}
		}

		return ret;
	}

	/*
	 * Use the suitability model according to configuration: either stochastic
	 * (choose accordingly to distribution and see if it's possible, failing if
	 * not), or deterministic (conservative = pick the most likely transition and
	 * fail if impossible | greedy = use the first possible in order of
	 * probability).
	 * 
	 * TODO the probability should be part of the input.
	 */
	@SuppressWarnings("unchecked")
	private List<IConcept> getCandidates(IConcept current, double demandWeight, double elasticity,
			double neighborhoodWeight, IRuntimeScope scope, ILocator locator) {

		List<IConcept> ret = new ArrayList<>();
		EnumeratedDistribution<IConcept> probabilities = null;

		if (suitabilityCalculator != null) {

			IParameters<String> params = Parameters.create();
			for (Attribute input : suitabilityCalculator.getResource().getInputs()) {
				IArtifact artifact = scope.getArtifact(input.getName());
				if (artifact == null) {
					throw new KlabValidationException(
							"required predictor " + input.getName() + " is not available in context");
				}
				if (!(artifact instanceof IState)) {
					throw new KlabValidationException("required predictor " + input.getName() + " is not a state");
				}
				params.put(input.getName(), ((IState) artifact).get(locator));
			}

			/*
			 * we want a distribution
			 */
			probabilities = suitabilityCalculator.eval(params, EnumeratedDistribution.class, monitor);

			switch (allocationStrategy) {
			case CONSERVATIVE_DETERMINISTIC:
				List<Pair<IConcept, Double>> list = probabilities.getPmf();
				list.sort(new Comparator<Pair<IConcept, Double>>() {
					@Override
					public int compare(Pair<IConcept, Double> o1, Pair<IConcept, Double> o2) {
						// sort by descending probability
						return Double.compare(o2.getSecond(), o1.getSecond());
					}
				});

				// get the most likely unless not likely enough
				if (list.get(0).getSecond() >= probabilityThreshold) {
					ret.add(list.get(0).getFirst());
				}
				break;
			case GREEDY_DETERMINISTIC:
				list = probabilities.getPmf();
				list.sort(new Comparator<Pair<IConcept, Double>>() {
					@Override
					public int compare(Pair<IConcept, Double> o1, Pair<IConcept, Double> o2) {
						// sort by descending probability
						return Double.compare(o2.getSecond(), o1.getSecond());
					}
				});

				// get all likely enough in order
				for (Pair<IConcept, Double> pair : list) {
					if (pair.getSecond() < probabilityThreshold) {
						break;
					}
					ret.add(pair.getFirst());
				}
				break;
			case STOCHASTIC:
				// sample the distribution, as we should.
				ret.add(probabilities.sample());
				break;
			}

		} else if (transitionCalculator != null) {
			throw new KlabUnimplementedException(
					"predicting transitions is still unimplemented: please use standard suitability analysis");
		} else {
			// add all concepts we know (equal probability if we have to)
			ret.addAll(this.distribution.keySet());
		}

		return ret;
	}

	private int getElasticity(IConcept current, ILocator locator) {
		// TODO Auto-generated method stub
		return 0;
	}

	private double getNeighborhoodWeight(ILocator locator) {
		// TODO unimplemented in CLUE. Comment is: "change weight based on surrounding
		// regions (not cells!) which are updated
		// during iterations (landuse changes)". Supposed to be 0-1 like all other
		// weights. Not very clear. Check paper.
		return 0;
	}

	/**
	 * Sigmoid weighting for demand. CLUE algorithm. Produces an IntelligentMap for
	 * matching hierarchically.
	 * 
	 * @param time
	 * @return
	 */
	private Map<IConcept, Double> computeDemandWeights(ITime time) {

		double steepnessConstant = 0.1; // the smaller -> the steeper the sigmoid

		Map<IConcept, Double> ret = new IntelligentMap<Double>(0.0);
		Map<IConcept, Double> req = getRequiredAreas(time);
		for (IConcept key : demand.keySet()) {

			double result;
			double demanded = req.get(key);
			double allocatedArea = getAllocatedArea(key);

			if (allocatedArea < demanded) {
				double hulp1 = Math.log(steepnessConstant / (1 + steepnessConstant)) / (double) demanded;
				result = (1 + steepnessConstant) * (1 - Math.exp(hulp1 * (demanded - allocatedArea)));
			} else if (allocatedArea == demanded) {
				result = 0.0;
			} else {
				double hulp2 = Math.log(steepnessConstant / (1 + steepnessConstant)) / (double) (totalArea - demanded);
				result = -1 * (1 + steepnessConstant) * (1 - Math.exp(hulp2 * (allocatedArea - demanded)));
			}
			ret.put(key, result);
		}
		return ret;
	}

	private double getAllocatedArea(IConcept target) {
		double ret = 0;
		for (IConcept key : distribution.keySet()) {
			if (key.is(target)) {
				ret += distribution.get(key);
			}
		}
		return ret;
	}

	/**
	 * Check if demand is met, tabulating all distributions and checking them
	 * against demand at the time.
	 * 
	 * @return
	 */
	private boolean demandMet(ITime time) {

		/*
		 * compute distributions, which will be used at each iteration
		 */
		this.distribution = preprocessLandcover(false);
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Demanded area per <i>requested</i> category at passed time, in square meters.
	 */
	IntelligentMap<Double> getRequiredAreas(ITime time) {
		IntelligentMap<Double> ret = new IntelligentMap<>(0.0);
		for (IConcept c : demand.keySet()) {
			TimeDependentFactor spec = demand.get(c);
			double value = spec.get(time);
			if (!spec.isAbsolute) {
				double area = getAllocatedArea(c);
				value = area + (area * value);
			}
			ret.put(c, value);
		}
		return ret;
	}

	/*
	 * * ---- Configuration and setup ---- *
	 */

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

		boolean initializeAge = findOrCreateAgeState();

		/*
		 * determine initial land distribution and total covered area
		 */
		this.distribution = preprocessLandcover(initializeAge);

		/*
		 * suitability parameters
		 */
		if (parameters.containsKey("suitability")) {
			IResource suitability = Resources.INSTANCE.resolveResource(parameters.get("suitability").toString(),
					scope.getModel() == null ? null : scope.getModel().getNamespace().getProject());
			if (suitability == null) {
				throw new KlabValidationException("resource " + parameters.get("suitability") + " cannot be resolved");
			}
			this.suitabilityCalculator = Resources.INSTANCE.getCalculator(suitability);
			if (this.suitabilityCalculator == null) {
				throw new KlabValidationException(
						"resource " + suitability.getUrn() + " cannot be used to compute probabilities");
			}
		} else if (parameters.containsKey("change")) {
			IResource suitability = Resources.INSTANCE.resolveResource(parameters.get("change").toString(),
					scope.getModel() == null ? null : scope.getModel().getNamespace().getProject());
			if (suitability == null) {
				throw new KlabValidationException("resource " + parameters.get("change") + " cannot be resolved");
			}
			this.transitionCalculator = Resources.INSTANCE.getCalculator(suitability);
			if (this.transitionCalculator == null) {
				throw new KlabValidationException(
						"resource " + suitability.getUrn() + " cannot be used to compute probabilities");
			}
		}

		/*
		 * these will properly use annotations as well as configuration specs
		 */
		this.elasticity = readTimeDependentFactors(parameters.get("elasticities"), "elasticity");
		this.demand = readTimeDependentFactors(parameters.get("demand"), "demand");
		this.deviation = readTimeDependentFactors(parameters.get("deviations"), "deviation");

		/*
		 * TODO read transition behavior from parameters if any
		 */
		this.transitionTable = new LandcoverTransitionTable(transitionsAreTransitive, defaultTransitionPossible);
		if (parameters.containsKey("transitions")) {
			this.transitionTable.parse(parameters.get("transitions", IKimTable.class));
			configuredConcepts.addAll(this.transitionTable.getConcepts());
		}
		
	}

	private boolean findOrCreateAgeState() {

		IArtifact prospective = null;
		boolean mustInitialize = false;
		if (this.scope.getModel() != null) {
			for (IObservable o : this.scope.getModel().getObservables()) {
				if (Annotations.INSTANCE.hasAnnotation(o, "age")) {
					prospective = this.scope.getArtifact(o.getName());
					mustInitialize = true;
					break;
				}
			}
			if (prospective == null) {
				for (IObservable o : this.scope.getModel().getDependencies()) {
					if (Annotations.INSTANCE.hasAnnotation(o, "age")) {
						prospective = this.scope.getArtifact(o.getName());
						break;
					}
				}
			}

			if (prospective != null) {

				if (!(prospective instanceof IState) || prospective.getType() != IArtifact.Type.NUMBER) {
					throw new KlabValidationException("observable tagged with 'age' must be a numeric state");
				}

				this.landCoverAge = (IState) prospective;
			}
		}

		if (this.landCoverAge == null) {
			this.landCoverAge = this.scope.newNonsemanticState(this.landCoverType.getObservable().getName() + " age",
					IArtifact.Type.NUMBER, this.process.getScale().initialization());
			mustInitialize = true;
		}

		return mustInitialize;
	}

	private Map<IConcept, Double> preprocessLandcover(boolean initializeAge) {
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
		/*
		 * add all concepts mentioned that are not in the current state with area 0 so they 
		 * become game for transitions 
		 */
		for (IConcept c : configuredConcepts) {
			if (!areaDistribution.containsKey(c)) {
				areaDistribution.put(c, 0.0);
			}
		}
		return areaDistribution;
	}

	/**
	 * Read a map of correspondence of concepts to time-dependent parameters, also
	 * using any annotations in the dependencies of the model. Long and messy due to
	 * the variety of situations it must accommodate. We use it for all configurable
	 * parameters:
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
									configuredConcepts.add(concept);
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
		// range can be negative if we're specifying demand.
		Range zeroone = Range.create(annotationName.equals("demand") ? -1 : 0, 1, false);

		if (object instanceof Map) {

			for (Map.Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
				if (entry.getKey() instanceof IKimConcept) {
					IConcept concept = Concepts.INSTANCE.declare((IKimConcept) entry.getKey());
					if (concept != null) {

						configuredConcepts.add(concept);
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

						configuredConcepts.add(concept);
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
				// piecewise linear interpolation for 2 points, or maybe spline for 3 or more.
			}
		}

		/*
		 * TODO! Further validation depending on what the target is. ALL DEMAND CONCEPTS
		 * MUST BE DISJOINT or the algorithm will produce crap!
		 */

		return ret;
	}

	double getFactor(IntelligentMap<TimeDependentFactor> source, ITime time) {
		return 0;
	}

}
