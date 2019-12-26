package org.integratedmodelling.landcover.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.IStorage;
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

	public static enum ProbabilityCompoundingMode {
		DEMAND_WEIGHT_DOMINATED, MULTIPLY_ALL, SUM_ALL, SUITABILITY_ONLY, DEFAULT
	};

	ProbabilityCompoundingMode compoundingMode = ProbabilityCompoundingMode.DEFAULT;

	/*
	 * if set in configuration, reseed with that number at construction.
	 */
	long randomSeed = -1;

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

	Random random = new Random();

	/*
	 * default transition table allows every transition and contains no table.
	 */
	LandcoverTransitionTable transitionTable = new LandcoverTransitionTable(false, true);

	class Conversion {

		IConcept from;
		IConcept to;
		double probability;

		public Conversion(IConcept from, IConcept to, double probability) {
			this.from = from;
			this.to = to;
			this.probability = probability;
		}

		public Conversion withProbability(double probability) {
			this.probability = probability;
			return this;
		}

		/*
		 * this gets recorded in a temporary state
		 */
		@Override
		public String toString() {
			return from.getDefinition() + "|" + to.getDefinition() + "|" + probability;
		}
	}

	private IProcess process;
	private IRuntimeScope scope;
	private IMonitor monitor;
	private double totalArea;
	private Map<IConcept, Double> distribution;
	private Set<IConcept> configuredConcepts = new HashSet<>();

	/*
	 * these change at each iteration
	 */
	Map<IConcept, Double> probabilityShifts = new IntelligentMap<Double>();
	Map<IConcept, Double> demandRatios = new IntelligentMap<Double>();
	Map<IConcept, Double> demandWeights = new IntelligentMap<Double>();

	public LandcoverChange(IProcess targetProcess) {
		this.process = targetProcess;
		this.random.setSeed(randomSeed > 0 ? randomSeed : System.currentTimeMillis());
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
	@SuppressWarnings("unchecked")
	public void run(IRuntimeScope scope) {

		// this persists across a timestep
		probabilityShifts.clear();

		// transition state for age and event detection
		IStorage<String> transitionStorage = (IStorage<String>) Klab.INSTANCE.getStorageProvider()
				.createStorage(IArtifact.Type.TEXT, process.getScale().without(Dimension.Type.TIME), scope);
		// transition state for age and event detection
		IStorage<IConcept> targetStorage = (IStorage<IConcept>) Klab.INSTANCE.getStorageProvider()
				.createStorage(IArtifact.Type.CONCEPT, process.getScale().without(Dimension.Type.TIME), scope);

		int iterations = 0;
		while (!demandMet(scope.getScale().getTime()) && iterations < maxIterations) {

			for (ILocator locator : scope.getScale()) {

				IConcept current = landCoverType.get(locator, IConcept.class);

				if (Observations.INSTANCE.isData(current)) {

					List<Conversion> conversions = getPossibleConversions(current,
							/* demandWeights.get(current), */ scope, locator);

					Conversion chosen = null;
					if (conversions.size() == 1) {
						chosen = conversions.get(0);
					} else if (conversions.size() > 1) {
						chosen = pickConversion(conversions);
					}

					applyConversion(chosen, transitionStorage, targetStorage, locator);
				}
			}
			iterations++;
		}

		if (iterations >= maxIterations) {
			monitor.warn(
					"maximum iterations reached in allocation algorithm without meeting demand. Please review parameters and context.");
		}

		/*
		 * TODO recompute age for all transitions that have happened.
		 */
		finalizeAllocation(targetStorage, transitionStorage, scope);

	}

	private void finalizeAllocation(IStorage<IConcept> targetStorage, IStorage<String> transitionStorage,
			IRuntimeScope scope) {

		long duration = scope.getScale().getTime().getEnd().getMilliseconds()
				- scope.getScale().getTime().getStart().getMilliseconds();
		
		for (ILocator locator : scope.getScale()) {
			String transition = transitionStorage.get(locator);
			if (transition != null) {
				this.landCoverAge.set(locator, 0);
				this.landCoverType.set(locator, targetStorage.get(locator));
				/*
				 * TODO use the transition for event detection and statistics
				 */
			} else {
				double age = this.landCoverAge.get(locator, Double.class);
				this.landCoverAge.set(locator, age + duration);
			}
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
		Conversion chosen = null;
		if (conversions.size() > 0) {
			chosen = conversions.get(0);
			for (int i = 1; i < conversions.size(); i++) {
				if (chosen.probability < conversions.get(i).probability) {
					chosen = conversions.get(i);
				}
			}
		}
		return chosen;
	}

	/**
	 * Finalize a conversion, including the setting of age, frequency, iteration
	 * validation criteria, and, if requested, event detector data.
	 * 
	 * @param targetStorage
	 * @param transitionStorage
	 */
	private void applyConversion(Conversion candidate, IStorage<String> transitionStorage,
			IStorage<IConcept> targetStorage, ILocator locator) {

		if (!candidate.from.equals(candidate.to)) {
			targetStorage.put(candidate.to, locator);
			transitionStorage.put(candidate.toString(), locator);
		}
		// TODO
	}

	private List<Conversion> getPossibleConversions(IConcept current, IRuntimeScope scope, ILocator locator) {

		List<Conversion> ret = new ArrayList<>();
		ITime time = scope.getScale().getTime();

		double elasticity = getElasticity(current, time);

		if (elasticity >= 1) {
			return ret;
		}

		/*
		 * The transition table will check if a transition to the target type exists,
		 * choosing according to configuration and potentially including more generic
		 * transitions.
		 */
		for (Conversion candidate : getCandidates(current, scope, locator)) {

			for (TransitionRule transition : transitionTable.getTransitions(candidate.from, candidate.to)) {

				if (transition.isPossible(locator, scope)) {

					/*
					 * the conversion by now only contais the suitability. Add the other dimensions.
					 */
					double probability = compoundProbabilities(candidate, time, locator, elasticity);
					/*
					 * CLUE uses an (arbitrary?) further shock of +/-5% of the total probability to
					 * prevent flip-flopping.
					 */
					probability += probabilityShifts.get(candidate.to);
					probability += (nextRandom() - 0.5) * 0.05 * probability;

					if (probability > 0) {
						ret.add(candidate.withProbability(probability));
					}
				}
			}
		}

		return ret;
	}

	/**
	 * Methods from CLUE
	 * 
	 * @param conversion
	 * @param time
	 * @param locator
	 * @param currentElasticity
	 * @return
	 */
	private double compoundProbabilities(Conversion conversion, ITime time, ILocator locator,
			double currentElasticity) {

		double suitability = conversion.probability;
		double demandWeight = getDemandWeight(conversion.to);
		double neighborhood = getNeighborhoodWeight(conversion.to, locator);
		double ret = 0;
		switch (compoundingMode) {
		case DEFAULT:
			ret = demandWeight + neighborhood + suitability
					+ (conversion.from.equals(conversion.to) ? currentElasticity : 0);
			break;
		case DEMAND_WEIGHT_DOMINATED:
			double rescaledDemandWeight = (demandWeight + 1.0) / 2.0;
			double preProbability = (rescaledDemandWeight + suitability) / 2.0;
			if (conversion.from.equals(conversion.to))
				ret = preProbability + (1 - preProbability) * currentElasticity;
			else
				ret = preProbability;
			break;
		case MULTIPLY_ALL:
			double elast = getElasticity(conversion.to, time);
			ret = demandWeight + neighborhood + suitability + elast;
			break;
		case SUITABILITY_ONLY:
			ret = suitability;
			break;
		case SUM_ALL:
			elast = getElasticity(conversion.to, time);
			ret = demandWeight * neighborhood * suitability * elast;
			break;
		}

		// there is at least one situation in which ret > 1
		return ret > 1 ? 1 : ret;
	}

	private double getDemandWeight(IConcept current) {
		return demandWeights.get(current);
	}

	private double getElasticity(IConcept concept, ITime time) {
		return getFactor(concept, elasticity, time);
	}

	private double nextRandom() {
		return random.nextDouble();
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
	private List<Conversion> getCandidates(IConcept current, IRuntimeScope scope, ILocator locator) {

		List<Conversion> ret = new ArrayList<>();
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

			for (Pair<IConcept, Double> pair : probabilities.getPmf()) {
				if (pair.getSecond() < probabilityThreshold) {
					break;
				}
				ret.add(new Conversion(current, pair.getFirst(), pair.getSecond()));
			}

		} else if (transitionCalculator != null) {
			throw new KlabUnimplementedException(
					"predicting transitions is still unimplemented: please use standard suitability analysis");
		} else {
			// add all concepts we know (with equal probability, including current)
			for (IConcept c : distribution.keySet()) {
				ret.add(new Conversion(current, c, 1.0 / distribution.size()));
			}
		}

		return ret;
	}

	private double getNeighborhoodWeight(IConcept current, ILocator locator) {
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

		demandRatios.clear();
		demandWeights = computeDemandWeights(scope.getScale().getTime());

		/*
		 * compute areal distribution of each landcover type, including 0 for any
		 * landcover mentioned in the configuration but not yet represented in the data.
		 */
		this.distribution = preprocessLandcover(false);

		if (probabilityShifts.isEmpty()) {
			// first run: initialize to 0
			for (IConcept lu : demand.keySet()) {
				probabilityShifts.put(lu, 0.0);
			}
		}

		/*
		 * compute demand ratio
		 */
		this.demandRatios = new HashMap<>();
		double totalDemand = getTotalDemand(time);
		for (IConcept lu : demand.keySet()) {
			this.demandRatios.put(lu, totalDemand == 0 ? 0 : (getFactor(lu, demand, time) / totalDemand));
		}

		shiftProbabilities();

		// TODO Auto-generated method stub
		return false;
	}

	private double getTotalDemand(ITime time) {
		double ret = 0;
		for (IConcept lu : demand.keySet()) {
			ret += getFactor(lu, demand, time);
		}
		return ret;
	}

	/*
	 * CLUE algorithm
	 */
	private void shiftProbabilities() {

		for (Map.Entry<IConcept, Double> entry : this.probabilityShifts.entrySet()) {

			IConcept landuse = entry.getKey();

			if (!demandRatios.containsKey(landuse))
				continue;

			Double shiftValue = entry.getValue();
			Double allocatedDemandRatio = 0.0;
			if (demandRatios.get(landuse) > 0.0) {
				allocatedDemandRatio = (demandRatios.get(landuse) - (distribution.get(landuse) / totalArea))
						/ demandRatios.get(landuse);
			}

			double stepSize = Math.min(allocatedDemandRatio * 100 * shiftStep, maxShift);
			probabilityShifts.put(landuse, shiftValue + stepSize);
		}
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
		 * default transition behavior is that everything is possible only if no
		 * transition are specified, otherwise nothing is possible but the explicitly
		 * specified transitions.
		 */
		defaultTransitionPossible = !parameters.containsKey("transitions");

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
		 * this would really screw things up
		 */
		if (!Concepts.INSTANCE.isTransitivelyIndependent(this.demand.keySet())) {
			throw new KlabValidationException("demand concepts must be independent from each other");
		}

		/*
		 * read transition behavior from parameters if any, otherwise default to all
		 * transitions possible
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
		 * add all concepts mentioned that are not in the current state with area 0 so
		 * they become game for transitions
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
						factor.isAbsolute = annotationName.equals("elasticities")
								|| !((IState) state).getObservable().is(Type.PROPORTION);
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
							factor.isAbsolute = annotationName.equals("elasticities");

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

		return ret;
	}

	double getFactor(IConcept concept, Map<IConcept, TimeDependentFactor> source, ITime time) {

		TimeDependentFactor factor = source.get(concept);
		if (factor == null) {
			return 0;
		}
		double ret = factor.get(time);
		if (!factor.isAbsolute) {
			ret *= totalArea;
		}
		return ret;
	}

}
