package org.integratedmodelling.landcover.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.Pair;
import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimDate;
import org.integratedmodelling.kim.api.IKimExpression;
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
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.mediation.Quantity;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.data.Transition;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.code.LocatedExpression;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.IntelligentMap;
import org.integratedmodelling.klab.owl.ReasonerCache;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.landcover.model.LandcoverTransitionTable.TransitionRule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.ibm.icu.text.NumberFormat;

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

	/*
	 * keeping these from CLUE but, like CLUE, only using the default. Not sure of
	 * the wisdom in any other.
	 */
	public static enum ProbabilityCompoundingMode {
		DEMAND_WEIGHT_DOMINATED, MULTIPLY_ALL, SUM_ALL, SUITABILITY_ONLY, DEFAULT
	};

	ProbabilityCompoundingMode compoundingMode = ProbabilityCompoundingMode.DEFAULT;

	private static final PeriodFormatter periodFormat = new PeriodFormatterBuilder().appendDays()
			.appendSuffix(" day", " days").appendSeparator(" ").printZeroIfSupported().minimumPrintedDigits(2)
			.appendHours().appendSeparator(":").appendMinutes().printZeroIfSupported().minimumPrintedDigits(2)
			.appendSeparator(":").appendSeconds().minimumPrintedDigits(2).toFormatter();

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
	int maxIterations = 200;

	/*
	 * default parameters for the transition behavior
	 */
	boolean transitionsAreTransitive = true;
	boolean defaultTransitionPossible = false;

	/*
	 * if true, exceeding demand is OK, otherwise we let the algorithm rip when we
	 * have too much of any type. The algorithm will, of course, rip for nothing if
	 * there are no rules that allow LCTs in demand to change.
	 */
	boolean greedy = false;

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
	IntelligentMap<TimeDependentFactor> resistance = new IntelligentMap<>(new TimeDependentFactor());
	IntelligentMap<TimeDependentFactor> deviation = new IntelligentMap<>(new TimeDependentFactor());
	Map<IConcept, TimeDependentFactor> demand = new HashMap<>();

	MersenneTwister random = new MersenneTwister();
	ReasonerCache rcache = new ReasonerCache();

	/*
	 * default transition table allows every transition and contains no table.
	 */
	LandcoverTransitionTable transitionTable;

	static class Conversion extends Transition<IConcept> {

		transient double suitability;

		public Conversion(IConcept from, IConcept to, double suitability) {
			super(from, to);
			this.suitability = suitability;
		}

		public double getSuitability() {
			return suitability;
		}

		/*
		 * this gets recorded in a temporary state
		 */
		@Override
		public String toString() {
			return getSource().getDefinition() + "->" + getDestination().getDefinition();
		}

		public Conversion withSuitability(double probability) {
			this.suitability = probability;
			return this;
		}
	}

	private IProcess process;
	private IRuntimeScope scope;
	private IMonitor monitor;
	private double totalArea;
	private Map<IConcept, Double> distribution;
	private Map<IConcept, Double> originalDistribution;
	private Map<IConcept, Double> previousDistribution = null;
	private Set<IConcept> configuredConcepts = new HashSet<>();
	private ConversionStatistics conversionStatistics = new ConversionStatistics();

	/*
	 * buffers for state and transitions across iterations
	 */
	IStorage<IConcept> targetStorage;
	IStorage<String> transitionStorage;

	/*
	 * keep each goal met with the correspondent balance here
	 */
	Map<IConcept, Double> goalsMet = new HashMap<>();

	/*
	 * these change at each iteration
	 */
	Map<IConcept, Double> probabilityShifts = new IntelligentMap<Double>(0.0);
	Map<IConcept, Double> demandRatios = new IntelligentMap<Double>(0.0);
	Map<IConcept, Double> demandWeights = new IntelligentMap<Double>(0.0);

	/*
	 * this contains -1 if there's less area than target +/- deviation, +1 if too
	 * much.
	 */
	Map<IConcept, Integer> deviationFromTarget = new IntelligentMap<Integer>(0);
	Map<IConcept, DescriptiveStatistics> movingAverages = new HashMap<>();

	private boolean tainted;
	private double defaultDeviation = 0.05;
	private long runtimePerIteration = -1;
	private long nextents;

	public LandcoverChange(IProcess targetProcess) {
		this.process = targetProcess;
		this.transitionTable = new LandcoverTransitionTable(false, true, random);
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

		monitor.info("Running change cycle to " + scope.getScale().getTime().getEnd());

		this.probabilityShifts.clear();
		this.movingAverages.clear();
		this.goalsMet.clear();
		this.transitionTable.activate(this.landCoverAge);
		this.previousDistribution = null;

		// transition buffer for age and event detection
		this.transitionStorage = (IStorage<String>) Klab.INSTANCE.getStorageProvider()
				.createStorage(IArtifact.Type.TEXT, process.getScale().without(Dimension.Type.TIME));
		// transition state for age and event detection
		this.targetStorage = (IStorage<IConcept>) Klab.INSTANCE.getStorageProvider()
				.createStorage(IArtifact.Type.CONCEPT, process.getScale().without(Dimension.Type.TIME));

		/*
		 * compute demand ratios. These don't change with iterations, only with time.
		 */
		this.demandRatios = new HashMap<>();
		double totalDemand = getTotalDemand(scope.getScale().getTime());
		for (IConcept lu : demand.keySet()) {
			this.demandRatios.put(lu,
					totalDemand == 0 ? 0 : (getFactor(lu, demand, scope.getScale().getTime()) / totalDemand));
		}

		int iteration = 0;
		boolean isInterrupted = false;
		int nexts = 0;

		// initialize
		for (IConcept lu : demand.keySet()) {
			probabilityShifts.put(lu, 0.0);
		}

		/*
		 * TODO if all goals are met and there are still active transitions, just run
		 * one iteration with the current transition rules and do not allocate anything.
		 */
		while (iteration < maxIterations) {

			if (!transitionTable.isActive()) {
				break;
			}

			this.conversionStatistics.reset();

			long startTime = System.currentTimeMillis();
			for (ILocator locator : scope.getScale()) {

				if (monitor.isInterrupted()) {
					isInterrupted = true;
					break;
				}

				IConcept current = landCoverType.get(locator, IConcept.class);
				if (Observations.INSTANCE.isData(current)) {

					// report estimated time per iteration
					if (runtimePerIteration < 0 && this.nextents >= 10000) {
						nexts++;
						if (nexts == 5000) {
							// dividing by 8500 instead of 5000 to correct by a completely empirical factor
							// I don't really understand but seems to work.
							runtimePerIteration = (long) ((double) (System.currentTimeMillis() - startTime)
									* (((double) this.nextents) / 8500.0));
							Period period = new Period(runtimePerIteration);
							monitor.info("Estimated time per iteration: " + periodFormat.print(period));
						}
					}

					List<Conversion> conversions = getPossibleConversions(current, scope, locator, iteration);
					applyConversion(current, pickConversion(conversions), transitionStorage, targetStorage, locator);
				}

			}

			if (isInterrupted) {
				break;
			}

			DemandEvaluation eval = evaluateConvergence(scope.getScale().getTime(), iteration);

			if (eval == DemandEvaluation.NO_MORE_GOALS || eval == DemandEvaluation.MET) {
				break;
			} else if (eval == DemandEvaluation.NOT_CONVERGING) {
				monitor.warn("Not converging towards demand goals - either too little change or change "
						+ "in the wrong direction. Please review parameters and context.");
				break;
			}

			/*
			 * if we get here, we need more iterations
			 */
			demandWeights = computeDemandWeights(scope.getScale().getTime());
			shiftProbabilities();
			System.out.println(conversionStatistics.summarize(this.totalArea));

			iteration++;

		}

		if (iteration >= maxIterations) {
			this.tainted = true;
			monitor.warn(
					"   Maximum iterations reached in allocation algorithm without meeting demand. Please review parameters and context.");
		} else if (isInterrupted) {
			monitor.warn("Allocation interrupted.");
		} else if (iteration > 0) {
			if (demand.size() > 0) {
				// TODO log any surplus
				monitor.info("   " + goalsMet.size() + " of " + demand.size() + " targets achieved in " + iteration
						+ " iterations");
			}
		}

		/*
		 * recompute age for all transitions that have happened; only do that if .
		 */
		if (!tainted) {
			finalizeAllocation(targetStorage, transitionStorage, scope);
		}
	}

	/*
	 * recompute distributions and returns true if NOT converging or diverging.
	 */
	private DemandEvaluation evaluateConvergence(ITime time, int iteration) {

		this.previousDistribution = this.distribution;
		this.distribution = preprocessLandcover(false, true);

		DemandEvaluation ret = DemandEvaluation.MET;

		deviationFromTarget.clear();

		for (IConcept demanded : demand.keySet()) {

			TimeDependentFactor spec = demand.get(demanded);
			double original = getAllocatedArea(demanded, true);
			double target = spec.get(time, this.scope);
			if (!spec.isAbsolute) {
				target = original + (original * target);
			}
			double actual = getAllocatedArea(demanded, false);

			// allowed deviation
			TimeDependentFactor dfactor = this.deviation.get(demanded);
			double deviation = defaultDeviation * original;
			if (dfactor != null) {
				deviation = dfactor.isAbsolute ? dfactor.constval : (dfactor.constval * original);
			}

			// negative = there is unmet demand
			double balance = actual - target;
			Range okrange = Range.create(target - deviation, target + deviation);
			String formattedBalance = NumberFormat.getInstance().format(Quantity
					.create(Math.abs(balance), Units.INSTANCE.SQUARE_METERS).in(Units.INSTANCE.SQUARE_KILOMETERS));

			boolean demandMet = true;
			if (!okrange.contains(actual)) {
				if (balance < 0 || !greedy) {
					monitor.info("   #" + iteration + ": " + Concepts.INSTANCE.getDisplayName(demanded)
							+ (balance < 0 ? " deficit" : " surplus") + " = " + formattedBalance + " km^2");
					deviationFromTarget.put(demanded, actual > target ? 1 : -1);
					demandMet = false;
				}
			}

			if (demandMet) {
				transitionTable.deactivate(demanded);
				this.goalsMet.put(demanded, balance);
			} else {
				ret = DemandEvaluation.NOT_MET;
			}

		}

		// All goals met or no goals. This may change at the next timestep.
		if (this.goalsMet.size() == demand.size()) {
			return DemandEvaluation.NO_MORE_GOALS;
		}

		if (ret != DemandEvaluation.MET || iteration == 0) {
			printDistributions();
		}

		if (ret == DemandEvaluation.MET) {
			return ret;
		}

		/*
		 * we have demand and goals aren't met: evaluate if we're moving too slow or not
		 * at all on the goals.
		 */
		final int MOVING_AVERAGE_WINDOW_SIZE = 10;
		final double MINIMUM_MEAN_DEVIATION = 0.05;

		// check for equality first: if no change at all, stop right away
		boolean ok = false;
		for (IConcept c : deviationFromTarget.keySet()) {
			if (previousDistribution.get(c) != distribution.get(c)) {
				ok = true;
				break;
			}
			if (!ok) {
				ret = DemandEvaluation.NOT_CONVERGING;
			}
		}

		if (ret != DemandEvaluation.NOT_CONVERGING) {
			// otherwise only declare failure after a certain number of iteration with
			// little movement from the mean or increasing distance
			for (IConcept c : distribution.keySet()) {
				if (movingAverages.containsKey(c)) {
					DescriptiveStatistics stats = movingAverages.get(c);
					double previousMean = stats.getMean();
					stats.addValue(distribution.get(c));
					double newMean = stats.getMean();
					if (iteration > MOVING_AVERAGE_WINDOW_SIZE
							&& Math.abs((previousMean - newMean) / newMean) < MINIMUM_MEAN_DEVIATION) {
						ret = DemandEvaluation.NOT_CONVERGING;
					}
				} else {
					DescriptiveStatistics stats = new DescriptiveStatistics(MOVING_AVERAGE_WINDOW_SIZE);
					stats.addValue(distribution.get(c));
					movingAverages.put(c, stats);
				}
			}
		}

		return ret;
	}

	private void printDistributions() {

		Set<IConcept> allConcepts = new HashSet<>(landCoverType.getDataKey().getConcepts());
		allConcepts.addAll(distribution.keySet());
		allConcepts.addAll(originalDistribution.keySet());
		if (previousDistribution != null) {
			allConcepts.addAll(previousDistribution.keySet());
		}

		System.out.println(StringUtils.rightPad("CONCEPT", 60) + StringUtils.rightPad("ORIGINAL", 18)
				+ StringUtils.rightPad("PREVIOUS", 18) + StringUtils.rightPad("CURRENT", 18));
		for (IConcept c : allConcepts) {
			String original = StringUtils.rightPad(NumberFormat.getInstance()
					.format(originalDistribution.containsKey(c) ? (originalDistribution.get(c) / 1000000.0) : 0), 18);
			String previous = StringUtils.rightPad(NumberFormat.getInstance()
					.format(previousDistribution != null && previousDistribution.containsKey(c)
							? (previousDistribution.get(c) / 1000000.0)
							: 0),
					18);
			String current = StringUtils.rightPad(NumberFormat.getInstance()
					.format(distribution.containsKey(c) ? (distribution.get(c) / 1000000.0) : 0), 18);
			System.out.println(StringUtils.rightPad(c.toString(), 60) + original + previous + current);
		}
	}

	private void finalizeAllocation(IStorage<IConcept> targetStorage, IStorage<String> transitionStorage,
			IRuntimeScope scope) {

		long duration = scope.getScale().getTime().getEnd().getMilliseconds()
				- scope.getScale().getTime().getStart().getMilliseconds();

		for (ILocator locator : scope.getScale()) {
			ILocator slocator = locator.as(ISpace.class);
			String transition = transitionStorage.get(slocator);
			if (transition != null) {

				IConcept prima = this.landCoverType.get(locator, IConcept.class);
				IConcept dopo = targetStorage.get(slocator);

				if (!prima.equals(dopo)) {
					this.landCoverAge.set(locator, 0);
				} else {
					double age = this.landCoverAge.get(locator, Double.class);
					this.landCoverAge.set(locator, age + duration);
				}

				this.landCoverType.set(locator, targetStorage.get(slocator));
				/*
				 * TODO use the transition for event detection and statistics
				 */
			} else {
				double age = this.landCoverAge.get(locator, Double.class);
				this.landCoverAge.set(locator, age + duration);
			}
		}

		try {
			this.targetStorage.close();
			this.transitionStorage.close();
		} catch (IOException e) {
			// screw it
		}
	}

	/**
	 * Sample the distribution of possible conversions.
	 * 
	 * @param conversions
	 * @return
	 */
	private Conversion pickConversion(List<Conversion> conversions) {
		Conversion chosen = null;
		if (conversions.size() > 0) {
			List<Pair<Conversion, Double>> pmf = new ArrayList<>();
			for (Conversion conversion : conversions) {
				pmf.add(new Pair<>(conversion, conversion.getSuitability()));
			}
			EnumeratedDistribution<Conversion> distribution = new EnumeratedDistribution<>(random, pmf);
			chosen = distribution.sample();
		}
		return chosen;
	}

	/**
	 * Finalize a conversion, including the setting of frequency, iteration
	 * validation criteria, and, if requested, event detector data.
	 * 
	 * @param targetStorage
	 * @param transitionStorage
	 */
	private void applyConversion(IConcept current, Conversion candidate, IStorage<String> transitionStorage,
			IStorage<IConcept> targetStorage, ILocator locator) {
		if (candidate != null && !candidate.getSource().equals(candidate.getDestination())) {
			locator = locator.as(ISpace.class);
			targetStorage.put(candidate.getDestination(), locator);
			transitionStorage.put(candidate.toString(), locator);
			this.conversionStatistics.add(candidate, locator);
		} else {
			targetStorage.put(current, locator.as(ISpace.class));
		}
	}

	private List<Conversion> getPossibleConversions(IConcept current, IRuntimeScope scope, ILocator locator,
			int iteration) {

		List<Conversion> ret = new ArrayList<>();
		ITime time = scope.getScale().getTime();

		double resistance = getResistance(current, time, locator);

		/*
		 * CLUE just compounds the elasticity with the probabilities, with the result
		 * that if only one transition is possible, and the elasticity is < 1, that
		 * transition always happens and the elasticity is ignored. We keep that logic
		 * but also condition the entire transition computation to the elasticity.
		 */
		if (resistance < 1) {
			if (resistance > 0 && nextRandom() < resistance) {
				return ret;
			}
		}

		/*
		 * The transition table will check if a transition to the target type exists,
		 * choosing according to configuration and potentially including more generic
		 * transitions.
		 */
		for (Conversion candidate : getCandidates(current, scope, locator)) {

			for (TransitionRule transition : transitionTable.getTransitions(candidate.getSource(),
					candidate.getDestination())) {

				if (transition.isPossible(locator, scope)) {

					/*
					 * the conversion by now only contais the suitability. Add the other dimensions
					 * and shock the suitabilities in defect/surplus after the first iteration.
					 */
					double probability = compoundProbabilities(candidate, time, locator, iteration);

					/*
					 * CLUE uses an (arbitrary?) proportional shock of max +/-5% of the total
					 * probability to help prevent flip-flopping.
					 */
					probability += probabilityShifts.get(candidate.getDestination());
					probability += (nextRandom() - 0.5) * 0.05 * probability;

					if (probability > 0) {
						ret.add(candidate.withSuitability(probability));
					}
				}
			}
		}

		return ret;
	}

	/**
	 * Methods from CLUE. Return value is potentially > 1, so not really a
	 * probability.
	 * 
	 * @param conversion
	 * @param time
	 * @param locator
	 * @param currentResistance
	 * @return
	 */
	private double compoundProbabilities(Conversion conversion, ITime time,
			ILocator locator/* , double currentResistance */, int iteration) {

		double suitability = conversion.getSuitability();

		if (iteration > 0) {
			int deviation = deviationFromTarget.get(conversion.getDestination());
			if (deviation != 0) {
				/*
				 * shock the suitability according to what's needed
				 */
				double randomFactor = 0.02 + (nextRandom() * 0.05); // max 7% correction
				double correctionFactor = 1.0 + (randomFactor * deviation);
				suitability *= correctionFactor;
			}
		}

		double demandWeight = getDemandWeight(conversion.getDestination());
		double neighborhood = getNeighborhoodWeight(conversion.getDestination(), locator);
		double ret = 0;
		switch (compoundingMode) {
		case DEFAULT:
			ret = demandWeight + neighborhood + suitability;
			break;
		case DEMAND_WEIGHT_DOMINATED:
			double rescaledDemandWeight = (demandWeight + 1.0) / 2.0;
			double preProbability = (rescaledDemandWeight + suitability) / 2.0;
			if (conversion.getSource().equals(conversion.getDestination()))
				ret = preProbability + (1 - preProbability) /** currentResistance */
				;
			else
				ret = preProbability;
			break;
		case MULTIPLY_ALL:
			double elast = getResistance(conversion.getDestination(), time, locator);
			ret = demandWeight + neighborhood + suitability + elast;
			break;
		case SUITABILITY_ONLY:
			ret = suitability;
			break;
		case SUM_ALL:
			elast = getResistance(conversion.getDestination(), time, locator);
			ret = demandWeight * neighborhood * suitability * elast;
			break;
		}

		return ret;
	}

	private double getDemandWeight(IConcept current) {
		return demandWeights.get(current);
	}

	private double getResistance(IConcept concept, ITime time, ILocator locator) {
		return getFactor(concept, resistance, time, locator);
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
//			for (Attribute input : suitabilityCalculator.getResource().getInputs()) {
//				IArtifact artifact = scope.getArtifact(input.getName());
//				if (artifact == null) {
//					throw new KlabValidationException(
//							"required predictor " + input.getName() + " is not available in context");
//				}
//				if (!(artifact instanceof IState)) {
//					throw new KlabValidationException("required predictor " + input.getName() + " is not a state");
//				}
//				params.put(input.getName(), ((IState) artifact).get(locator));
//			}

			/*
			 * we want a distribution
			 */
			probabilities = suitabilityCalculator.eval(params, EnumeratedDistribution.class, monitor);

			if (probabilities != null) {
				for (Pair<IConcept, Double> pair : probabilities.getPmf()) {
					if (pair.getSecond() < probabilityThreshold) {
						continue;
					}
					ret.add(new Conversion(current, pair.getFirst(), pair.getSecond()));
				}
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
			double allocatedArea = getAllocatedArea(key, false);

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

//		System.out.println("DEMAND WEIGHTS");
//		for (IConcept dio : ret.keySet()) {
//			System.out.println("Weight for " + dio + " is now " + ret.get(dio));
//		}

		return ret;
	}

	private double getAllocatedArea(IConcept target, boolean original) {
		double ret = 0;
		Map<IConcept, Double> distribution = original ? this.originalDistribution : this.distribution;
		for (IConcept key : distribution.keySet()) {
			if (rcache.is(key, target)) {
				ret += distribution.get(key);
			}
		}
		return ret;
	}

	enum DemandEvaluation {
		MET, NOT_MET, NOT_CONVERGING, NO_MORE_GOALS
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

			if (!demandRatios.containsKey(landuse)) {
				continue;
			}

			Double shiftValue = entry.getValue();
			Double allocatedDemandRatio = 0.0;
			if (demandRatios.get(landuse) > 0.0) {
				allocatedDemandRatio = (demandRatios.get(landuse) - (getAllocatedArea(landuse, false) / totalArea))
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
			double value = spec.get(time, this.scope);
			if (!spec.isAbsolute) {
				double area = getAllocatedArea(c, true);
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

		long randomSeed = parameters.get("seed", -1L);
		this.random.setSeed(randomSeed > 0 ? randomSeed : System.currentTimeMillis());

		/*
		 * default transition behavior is that everything is possible only if no
		 * transition are specified, otherwise nothing is possible but the explicitly
		 * specified transitions.
		 */
		defaultTransitionPossible = !parameters.containsKey("transitions");
		this.greedy = parameters.get("greedy", Boolean.FALSE);

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
			target = Observables.INSTANCE.getDescribedType(target);
		} else {
			for (IConcept c : Observables.INSTANCE.getAffected(target)) {
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
		 * determine initial land distribution and total covered area. We keep the
		 * original distribution around to compare with proportional demand.
		 */
		this.distribution = this.originalDistribution = preprocessLandcover(initializeAge, false);

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
		this.resistance = readTimeDependentFactors(parameters.get("resistances"), "resistance", scope);
		this.demand = readTimeDependentFactors(parameters.get("demand"), "demand", scope);
		this.deviation = readTimeDependentFactors(parameters.get("deviations"), "deviation", scope);

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
		this.transitionTable = new LandcoverTransitionTable(transitionsAreTransitive, defaultTransitionPossible,
				random);
		if (parameters.containsKey("transitions")) {
			this.transitionTable.parse(parameters.get("transitions", IKimTable.class), scope);
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
			/*
			 * CHECK - we create a fully dynamic age state, this may be overkill.
			 */
			this.landCoverAge = this.scope.newNonsemanticState(this.landCoverType.getObservable().getName() + " age",
					IArtifact.Type.NUMBER, this.process.getScale());
			mustInitialize = true;
		}

		return mustInitialize;
	}

	private Map<IConcept, Double> preprocessLandcover(boolean initializeAge, boolean useBuffer) {

		this.totalArea = 0.0;
		this.nextents = 0;
		Map<IConcept, Double> areaDistribution = new HashMap<>();
		for (ILocator locator : this.scope.getScale()) {
			Object value = useBuffer ? this.targetStorage.get(locator.as(ISpace.class))
					: this.landCoverType.get(locator);
			if (Observations.INSTANCE.isData(value)) {
				this.nextents++;
				double area = Observations.INSTANCE.getArea(locator);
				totalArea += area;
				if (value instanceof IConcept) {
					areaDistribution.put((IConcept) value,
							areaDistribution.containsKey(value) ? (areaDistribution.get(value) + area) : area);
				}
				if (initializeAge) {
					this.landCoverAge.set(locator.as(ISpace.class), 0);
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
	private IntelligentMap<TimeDependentFactor> readTimeDependentFactors(Object object, String annotationName,
			IRuntimeScope scope) {

		IntelligentMap<TimeDependentFactor> ret = new IntelligentMap<>();

		/*
		 * start with tagged dependencies
		 */
		if (scope.getModel() != null) {
			for (IObservable dependency : this.scope.getModel().getDependencies()) {
				if (Annotations.INSTANCE.hasAnnotation(dependency, annotationName)) {
					IAnnotation annotation = Annotations.INSTANCE.getAnnotation(dependency, annotationName);
					IArtifact state = this.scope.getArtifact(dependency.getName());
					if (state instanceof IState) {
						TimeDependentFactor factor = new TimeDependentFactor();
						factor.state = (IState) state;
						factor.isAbsolute = annotationName.equals("resistance")
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
							factor.isAbsolute = annotationName.equals("resistance");

						} else if (entry.getValue() instanceof IKimQuantity) {
							Quantity q = Quantity.create(((IKimQuantity) entry.getValue()).getValue(),
									Unit.create(((IKimQuantity) entry.getValue()).getUnit()));
							factor.constval = q.in(Units.INSTANCE.SQUARE_METERS);
							factor.isAbsolute = true;

							if (factor.constval == 0) {
								throw new KlabValidationException("invalid areal specifications in " + annotationName);
							}
						} else if (entry.getValue() instanceof IKimExpression) {
							factor.expression = new LocatedExpression((IKimExpression) entry.getValue(), scope);
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
							IKimDate date = (IKimDate) time;
							factor.timepoint = new DateTime(date.getYear(), date.getMonth(), date.getDay(),
									date.getHour(), date.getMin(), date.getSec(), DateTimeZone.UTC).getMillis();
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
						} else if (value.getExpressionMatch() != null) {
							factor.expression = new LocatedExpression(value.getExpressionMatch(), scope);
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
		return getFactor(concept, source, time, null);
	}

	double getFactor(IConcept concept, Map<IConcept, TimeDependentFactor> source, ITime time, ILocator locator) {
		TimeDependentFactor factor = source.get(concept);
		if (factor == null) {
			return 0;
		}
		return locator == null ? factor.get(time, this.scope) : factor.get(time, locator, scope);
	}

	public boolean isTainted() {
		return tainted;
	}

}
