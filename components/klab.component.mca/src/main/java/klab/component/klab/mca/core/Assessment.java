package klab.component.klab.mca.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.processing.MapClassifier;
import org.integratedmodelling.klab.components.geospace.processing.MapClassifier.MapClass;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;

import com.sun.xml.bind.marshaller.Messages;

import klab.component.klab.mca.AHP;
import klab.component.klab.mca.MCA;
import klab.component.klab.mca.MCA.CriterionDataType;
import klab.component.klab.mca.MCA.CriterionType;
import klab.component.klab.mca.Results;
import klab.component.klab.mca.api.IAlternative;
import klab.component.klab.mca.api.IStakeholder;

public class Assessment {

	List<Stakeholder> stakeholders = new ArrayList<>();
	List<Alternative> alternatives = new ArrayList<>();

	private ISubject context;
	// private IActiveProcess process;
	private IScale scale;
	private IMonitor monitor;
	// private IResolutionScope resolutionContext;
	private boolean isError;
	private MCA.Method method = MCA.Method.EVAMIX;
	private boolean isGlobal;
	private boolean contextIsStakeholder;
	private boolean contextIsAlternative;

	// TODO set with parameter
	private int maxBinsPerState = 6;
	public boolean isDistributed;
	private IObservable concordance;
	private IRuntimeContext resolutionContext;

	/**
	 * Stakeholders are subjects that have a Concordance state per alternative.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public class Stakeholder implements IStakeholder {

		ISubject subject;
		List<IObservable> values = new ArrayList<>();
		ICurrency currency;
		List<IConcept> neededKnowledge;
		/*
		 * the subset of all alternatives that this stakeholder is valuing. They must
		 * have criteria for all the values.
		 */
		List<IAlternative> alternatives = null;

		/*
		 * weights are private because we may need to recompute them if values are
		 * distributed, so use getWeights() to retrieve them, which will behave
		 * accordingly.
		 */
		private Map<IConcept, Double> weights;

		// we keep the states here and only their observer in values, to avoid
		// lots of
		// horrible casts.
		Map<IObservable, IState> vstates = new HashMap<>();

		@Override
		public String toString() {
			return "STK/" + subject.getName() + "/" + values.size() + "/" + (isPairwiseValuator ? "pw" : "abs");
		}

		boolean isPairwiseValuator = false;
		boolean isConsistent = true;
		boolean isNeutral = true;
		boolean hasDistributedValues = false;

		Stakeholder(ISubject obs) {

			this.subject = obs;

			for (IState s : obs.getStates()) {
				if (s.getObservable().is(IKimConcept.Type.VALUE)) {

					/*
					 * values don't value themselves
					 */
					if (s.getObservable().getType().equals(concordance.getType())) {
						continue;
					}

					ICurrency curr = s.getObservable().getCurrency();
					if (this.currency == null) {
						this.currency = curr;
					} else if (!this.currency.isCompatible(curr)) {
						monitor.warn("currency for " + s.getObservable().getType() + " in stakeholder "
								+ subject.getName() + " is incompatible with others: value will be ignored");
						continue;
					}
					if (!s.isConstant() && Observations.INSTANCE.isDistributedOutside(s, Dimension.Type.TIME)) {
						hasDistributedValues = true;
					}
					values.add(s.getObservable());
					vstates.put(s.getObservable(), s);
				}
			}

			int nPair = 0;
			int nStraight = 0;
			for (IObservable vo : values) {
				if (vo.getComparisonType() != null /* pairwise */) {
					nPair++;
				} else {
					nStraight++;
				}
			}

			isConsistent = !(nStraight != 0 && nPair != 0);
			isNeutral = nStraight == 0 && nPair == 0;
			isPairwiseValuator = nPair == values.size() && nPair > 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.integratedmodelling.mca.IStakeholder#getAlternatives(org.
		 * integratedmodelling.api.modelling.scheduling.ITransition)
		 */
		@Override
		public List<IAlternative> getAlternatives(ILocator transition) {
			return initializeAlternatives(transition);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.integratedmodelling.mca.IStakeholder#rankAlternatives(org.
		 * integratedmodelling.api.modelling.scheduling.ITransition)
		 */
		@Override
		public void rankAlternatives(ILocator transition) throws KlabException {

			MCA mca = new MCA(method);
			List<IConcept> criteria = getAllNeededKnowledge();
			/*
			 * declare criteria
			 */
			for (IKnowledge k : criteria) {
				/*
				 * find criterion in the first alternative that has it.
				 */
				Criterion criterion = findCriterion(k);
				if (criterion != null) {
					mca.declareCriterion(k.toString(), getCriterionDataType(criterion.state), criterion.type);
				}
			}

			/*
			 * declare alternatives
			 */
			for (IAlternative a : getAlternatives(transition)) {
				mca.declareAlternative(a.getId());
			}

			/*
			 * Only perform the assessment spatially if we have distributed everything.
			 * Otherwise we want just scalars, and if the assessment is spatial it will be
			 * the alternatives that take care of spatial distribution.
			 * 
			 * FIXME: this should just reset the weights for each point, but not redo the
			 * whole thing. Unfortunately the weights are distributed differently than the
			 * criteria. For now, just disable.
			 */
			// for (int offset : (hasDistributedValues ?
			// subject.getScale().getIndex(transition)
			// : Collections.singletonList(0))) {

			// double cnc = Double.NaN;
			// if (!hasDistributedValues /* ||
			// subject.getScale().isCovered(offset)*/) {

			weights = getWeights(transition);

			for (IConcept k : criteria) {
				if (mca.setCriterionWeight(k.toString(), weights.get(k))) {
					for (IAlternative a : getAlternatives(transition)) {
						mca.setCriterionValue(a.getId(), k.toString(), a.getValueOf(k, transition, subject));
					}
				}
			}

			Results results = mca.run(monitor);

			if (results != null && !results.isEmpty()) {

				/**
				 * distribute ranking values into states.
				 */
				for (IAlternative a : this.alternatives) {
					((Alternative) a).setResults(getPOVObservable(concordance), results);
				}

				// results.dump();
			}

			/*
			 * set result of assessment into states per alternative
			 */

			/*
			 * TODO see if we need to confer a relative ranking (if there are multiple
			 * alternative; should depend on parameters)
			 */

			// }

		}

		private IObservable getPOVObservable(IObservable concordance) {
			Observable ret = new Observable((Observable) concordance);
			ret.setObserver(this.subject);
			return ret;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.integratedmodelling.mca.IStakeholder#canValue(org.
		 * integratedmodelling.mca. Assessment.Alternative)
		 */
		@Override
		public boolean canValue(IAlternative alternative) {

			int n = 0;
			for (IConcept k : getAllNeededKnowledge()) {
				if (alternative.hasCriterion(k)) {
					n++;
				}
			}

			return n >= 2;
		}

		private List<IConcept> getAllNeededKnowledge() {

			if (neededKnowledge == null) {
				Set<IConcept> ret = new HashSet<>();
				for (IObservable value : values) {
					ret.add(Observables.INSTANCE.getInherentType(value.getType()));
					if (Observables.INSTANCE.getComparisonType(value.getType()) != null) {
						ret.add(Observables.INSTANCE.getComparisonType(value.getType()));
					}
				}
				neededKnowledge = new ArrayList<>(ret);
			}
			return neededKnowledge;
		}

		public Map<IConcept, Double> getWeights(ILocator locator) {

			if (weights == null || hasDistributedValues) {

				weights = new HashMap<>();
				List<IConcept> criteria = getAllNeededKnowledge();
				/*
				 * TODO compute weights (with currency conversion)
				 */
				double[] wv = new double[criteria.size()];

				if (isPairwiseValuator) {

					AHP ahp = new AHP(criteria.size());
					for (int i = 0; i < criteria.size(); i++) {
						for (int j = 0; j < criteria.size(); j++) {
							ahp.rankPair(i, j, getValueFor(criteria.get(i), criteria.get(j), locator));
						}
					}
					wv = ahp.getRankings();

				} else {

					Arrays.fill(wv, 1.0);
					for (int i = 0; i < criteria.size(); i++) {
						wv[i] = getValueFor(criteria.get(i), locator);
					}
				}

				for (int i = 0; i < criteria.size(); i++) {
					weights.put(criteria.get(i), wv[i]);
				}
			}

			return weights;
		}

		public double getCriterionValue(Criterion criterion, int offset) {

			if (criterion.context.getName().equals(subject.getName())) {
				// just get the value. According to the crit type, we may need
				// to change
				// something.
			} else {
				/*
				 * create view for criterion if missing and store it
				 */
				/*
				 * use view to retrieve value
				 */
			}

			return 0;
		}

		private double getValueFor(IConcept criterion, ILocator locator) {
			double ret = Double.NaN;
			for (IObservable ob : values) {
				if (criterion.is(Observables.INSTANCE.getInherentType(ob.getType()))) {
					ret = ob.getCurrency().convert(vstates.get(ob).get(locator, Double.class), currency).doubleValue();
				}
			}
			return ret;
		}

		private double getValueFor(IConcept a1, IConcept a2, ILocator locator) {

			double ret = Double.NaN;
			for (IObservable ob : values) {
				if (a1.is(Observables.INSTANCE.getInherentType(ob.getType()))
						&& a2.is(Observables.INSTANCE.getComparisonType(ob.getType()))) {
					ret = ob.getCurrency().convert(vstates.get(ob).get(locator, Double.class), currency).doubleValue();
					break;
				} else if (a1.is(Observables.INSTANCE.getComparisonType(ob.getType()))
						&& a2.is(Observables.INSTANCE.getInherentType(ob.getType()))) {
					ret = 1.0 / ob.getCurrency().convert(vstates.get(ob).get(locator, Double.class), currency)
							.doubleValue();
					break;
				}
			}
			return ret;
		}

		List<IAlternative> initializeAlternatives(ILocator transition) {
			if (this.alternatives == null) {
				this.alternatives = new ArrayList<>();
				for (Alternative a : Assessment.this.alternatives) {
					if (canValue(a)) {
						this.alternatives.add(a);
					} else {
						monitor.warn("stakeholder " + subject.getName() + " cannot evaluate alternative "
								+ a.subject.getName() + " for lack of criteria");
					}
				}
			}

			if (this.alternatives.size() == 0) {
				monitor.warn("stakeholder " + subject.getName()
						+ " cannot evaluate any alternatives and will be removed from the assessment.");
				isConsistent = false;
				return alternatives;
			}

			if (alternatives.size() == 1 && alternatives.get(0).isDistributed()) {
				return ((Alternative) alternatives.get(0)).computeDistribution(transition);
			}

			return alternatives;
		}

		@Override
		public IDirectObservation getSubject() {
			return subject;
		}
	}

	/**
	 * Alternatives are what we are ranking. Each alternative gets a Concordance
	 * state for each stakeholder, used as the basis for ranking.
	 * 
	 * There is always at least one alternative (the context if no subjects have
	 * this role). Alternatives hold values of criteria for their context; these
	 * value may come from their own state, or from their view of the context's
	 * states.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public class Alternative implements IAlternative {

		IDirectObservation subject;
		Map<IConcept, Criterion> criteria = new HashMap<>();
		List<IConcept> critOrder = new ArrayList<>();
		MapClassifier classifier = null;
		List<IAlternative> distributedAlternatives = null;
		Map<String, DistributedAlternative> altCatalog;

		IState output = null;

		// /**
		// * Used to create the state.
		// *
		// * @return true if dynamic
		// */
		// public boolean isDynamic() {
		// for (Criterion c : criteria.values()) {
		// if (c.state.getStorage().isDynamic()) {
		// return true;
		// }
		// }
		// return false;
		// }

		/*
		 * an alternative generates an array of these when it's distributed. These rank
		 * properly in an MCA, after which we redistribute the rankings to the
		 * distributed states that created them.
		 */
		class DistributedAlternative implements IAlternative {

			private MapClass mapClass;

			DistributedAlternative(MapClass mc) {
				this.mapClass = mc;
			}

			@Override
			public boolean isDistributed() {
				return false;
			}

			@Override
			public double getValueOf(IConcept k, ILocator offset, IDirectObservation offsetContext) {
				return mapClass.getValueOf(k);
			}

			@Override
			public boolean hasCriterion(IConcept observable) {
				return Alternative.this.hasCriterion(observable);
			}

			@Override
			public IDirectObservation getSubject() {
				return subject;
			}

			@Override
			public String getId() {
				return subject.getName() + "_" + mapClass.getIndex();
			}

		}

		@Override
		public String toString() {
			return "ALT/" + subject.getName() + "/" + criteria.size();
		}

		public void setResults(IObservable observable, Results results) throws KlabException {

			Map<String, Double> res = results.getConcordances(true);

			if (classifier != null) {
//				double[] cvals = new double[distributedAlternatives.size()];
//				for (IAlternative da : distributedAlternatives) {
//					cvals[((DistributedAlternative) da).mapClass.getIndex()] = res.get(da.getId());
//				}
//				classifier.distributeResults(getState(observable), cvals);
			} else {
//				((Subject) subject).createState(observable, res.get(getId()));
			}
		}

//		private IState getState(IObservable observable) throws KlabException {
//			return subject.createState(observable);
//		}

		public List<IAlternative> computeDistribution(ILocator transition) {

			/*
			 * TODO recompute also if transition is new
			 */
			if (classifier == null) {

				distributedAlternatives = new ArrayList<>();
				altCatalog = new HashMap<>();
				List<IState> states = new ArrayList<>();
				for (IKnowledge c : critOrder) {
					states.add(criteria.get(c).state);
				}
				classifier = new MapClassifier(states, maxBinsPerState, resolutionContext, transition);
				monitor.info("computing distribution of states for " + subject.getName());
				int nclasses = classifier.classify();
				monitor.info("total number of spatial alternatives is " + nclasses);
				for (MapClass mc : classifier.getClasses()) {
					DistributedAlternative da = new DistributedAlternative(mc);
					distributedAlternatives.add(da);
					altCatalog.put(subject.getName() + "" + mc.getIndex(), da);
				}
			}

			/*
			 * tag the whole assessment as distributed, as this will only be called in that
			 * case.
			 */
			isDistributed = true;

			return distributedAlternatives;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.integratedmodelling.mca.IAlternative#isDistributed()
		 */
		@Override
		public boolean isDistributed() {
			for (Criterion c : criteria.values()) {
				if (!c.isDistributed()) {
					return false;
				}
			}
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.integratedmodelling.mca.IAlternative#getValueOf(org.
		 * integratedmodelling.api .knowledge.IKnowledge, int,
		 * org.integratedmodelling.api.modelling.IDirectObservation)
		 */
		@Override
		public double getValueOf(IConcept k, ILocator offset, IDirectObservation offsetContext) {

			Criterion cr = criteria.get(k);
			if (cr == null) {
				return Double.NaN;
			}

			if (offsetContext.getName().equals(cr.context.getName())) {
				return cr.state.get(offset, Double.class);
			} else {
				/*
				 * TODO
				 */
			}
			return Double.NaN;
		}

		Alternative(IDirectObservation subject) {
			this.subject = subject;

			for (IState s : subject.getStates()) {
//				if (process.getRolesFor(s).contains(MCAComponent.NS.COST_CRITERION_ROLE)) {
//					criteria.put(s.getObservable().getType(), new Criterion(s, false, subject));
//					critOrder.add(s.getObservable().getType());
//				} else if (process.getRolesFor(s).contains(MCAComponent.NS.BENEFIT_CRITERION_ROLE)) {
//					criteria.put(s.getObservable().getType(), new Criterion(s, true, subject));
//					critOrder.add(s.getObservable().getType());
//				}
			}

			/**
			 * Lookup criteria in the context (which will need to be mediated) unless the
			 * subject IS the context.
			 */
			if (!subject.getName().equals(context.getName())) {

				for (IState s : context.getStates()) {
//					if (process.getRolesFor(s).contains(MCAComponent.NS.COST_CRITERION_ROLE)) {
//						criteria.put(s.getObservable().getType(), new Criterion(s, false, context));
//						critOrder.add(s.getObservable().getType());
//					} else if (process.getRolesFor(s).contains(MCAComponent.NS.BENEFIT_CRITERION_ROLE)) {
//						criteria.put(s.getObservable().getType(), new Criterion(s, true, context));
//						critOrder.add(s.getObservable().getType());
//					}
				}
			}

			if (criteria.size() == 0) {
				monitor.info("no criteria found: concordance analysis is empty");
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.integratedmodelling.mca.IAlternative#hasCriterion(org.
		 * integratedmodelling. api.knowledge.IKnowledge)
		 */
		@Override
		public boolean hasCriterion(IConcept observable) {
			return getCriterion(observable) != null;
		}

		public Criterion getCriterion(IConcept observable) {
			return criteria.get(observable);
		}

		@Override
		public IDirectObservation getSubject() {
			return subject;
		}

		@Override
		public String getId() {
			return subject.getName();
		}

	}

	public class Criterion {

		private IState state;
		private CriterionType type;
		private IDirectObservation context;

		@Override
		public String toString() {
			return "CRT/" + state + "/" + type + "/" + context.getName();
		}

		public boolean isDistributed() {
			return !state.isConstant() && Observations.INSTANCE.isDistributedOutside(state, Dimension.Type.TIME);
		}

		Criterion(IState state, boolean isBenefit, IDirectObservation context) {
			this.state = state;
			this.type = isBenefit ? CriterionType.BENEFIT : CriterionType.COST;
			this.context = context;
		}
	}

	/**
	 * The constructor creates all the recognized stakeholders and alternatives. If
	 * no explicit stakeholders and/or alternatives are set, the context is used for
	 * either as long as there are criteria (even values are optional). If the
	 * assessment can proceed, isError() will return false after initialization.
	 * 
	 * At compute(), each stakeholder will rank all the alternatives it can value,
	 * and the result will be set into a quality of the alternative contextualized
	 * to the stakeholder. An observer for the quality (a ranking, value or
	 * ordering) must be passed. According to the context, the quality may be
	 * spatial, and the spatial value of both criteria and values will be honored.
	 * 
	 * @param process
	 * @param context
	 * @param resolutionContext
	 * @param monitor
	 */
	public Assessment(ISubject context, IObservable output, IRuntimeContext resolutionContext) {

		this.context = context;
		// this.process = process;
		this.scale = context.getScale();
		this.monitor = resolutionContext.getMonitor();
		this.resolutionContext = resolutionContext;
		this.concordance = output;

		initialize();
	}

	public Criterion findCriterion(IKnowledge k) {
		for (Alternative a : alternatives) {
			Criterion c = a.criteria.get(k);
			if (c != null) {
				return c;
			}
		}
		return null;
	}

	public Map<String, IObservation> compute(ILocator transition) throws KlabException {

		for (Stakeholder stakeholder : stakeholders) {

			if (stakeholder.isNeutral) {
				monitor.info("stakeholder " + stakeholder.subject.getName()
						+ " is neutral: concordance will use equal weights");
			}

			stakeholder.rankAlternatives(transition);
		}

		return null;

	}

	void initialize() {

		this.contextIsStakeholder = false;
		this.contextIsAlternative = false;

		if (context instanceof ISubject) {
			for (ISubject s : ((ISubject) context).getSubjects()) {
//				if (process.getRolesFor(s).contains(MCAComponent.NS.STAKEHOLDER_ROLE)) {
//					Stakeholder st = new Stakeholder(s);
//					if (!st.isConsistent) {
//						monitor.warn("stakeholder " + st.subject.getName()
//								+ " mixes pairwise values with absolute ones: ignored");
//					} else {
//						stakeholders.add(st);
//					}
//				}
			}
		}

		if (stakeholders.size() == 0) {
			Stakeholder st = new Stakeholder(context);
			if (!st.isConsistent) {
				monitor.error("context mixes pairwise values with absolute ones: cannot proceed");
				isError = true;
				return;
			} else if (st.hasDistributedValues) {
				monitor.error("distributed values are not supported currently: cannot proceed");
				isError = true;
				return;
			} else {
				monitor.info("mca: no stakeholders found: values will be assessed from the context");
				stakeholders.add(st);
				contextIsStakeholder = true;
			}
		} else {
			monitor.info(stakeholders.size() + " stakeholders found");
		}

		if (context instanceof ISubject) {
			for (ISubject s : ((ISubject) context).getSubjects()) {
//				if (process.getRolesFor(s).contains(MCAComponent.NS.ALTERNATIVE_ROLE)) {
//					Alternative a = new Alternative(s);
//					if (a.criteria.size() >= 2) {
//						alternatives.add(a);
//					} else {
//						monitor.warn(s.getName() + " has role of alternative but less than two criteria: ignored");
//					}
//				}
			}
		}

		if (alternatives.size() == 0) {
			monitor.info("mca: no alternatives found: values will be assessed in the context");
			Alternative a = new Alternative(context);
			if (!a.isDistributed()) {
				monitor.error(
						"using the context as an alternative is only meaningful when all criteria are distributed in space.");
				isError = true;
				return;
			} else if (a.criteria.size() >= 2) {
				alternatives.add(a);
				contextIsAlternative = true;
			} else {
				monitor.error("the context has less than two criteria so it cannot be used as an alternative");
				isError = true;
				return;
			}
		} else {
			monitor.info(alternatives.size() + " alternatives found");
		}

	}

	public static CriterionDataType getCriterionDataType(IState state) throws KlabValidationException {

		CriterionDataType ret = null;

		if (state.getType() == IArtifact.Type.NUMBER) {
			// TODO CHECK discretization
			ret = CriterionDataType.RATIO;
		} else if (state.getType() == IArtifact.Type.BOOLEAN) {
			ret = CriterionDataType.BINARY;
		} else if (state.getType() == IArtifact.Type.CONCEPT && state.getObservable().is(Type.ORDERING)) {
			ret = CriterionDataType.ORDINAL;
		}

		if (ret == null) {
			throw new KlabValidationException(
					"non-numeric and non-ordinal states cannot be used as criteria: " + state);
		}
		return ret;
	}

	public boolean isError() {
		return isError;
	}

	public void setDiscretizationLevel(int maxBins) {
		this.maxBinsPerState = maxBins;
	}

}
