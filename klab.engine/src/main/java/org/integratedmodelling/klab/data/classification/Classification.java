package org.integratedmodelling.klab.data.classification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimClassification;
import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.Triple;

public class Classification implements IClassification {

	IConcept rootConcept;
	boolean discretization;
	List<Pair<IConcept, IClassifier>> classifiers = new ArrayList<>();
	boolean initialized = false;
	private IConcept nilClassifier;
	private List<IConcept> conceptOrder = new ArrayList<>();
	private double[] distributionBreakpoints;
	private Map<IConcept, Double> numCodes = null;
	private Map<IConcept, Integer> conceptIndexes;

	// this is for reporting to the dataflow
	private Set<IKimExpression> expressions = new HashSet<>();

	public Classification(IKimClassification classification) {

		this.discretization = classification.isDiscretization();
		for (Pair<IKimConcept, IKimClassifier> classifier : classification) {
			IConcept concept = Concepts.INSTANCE.declare(classifier.getFirst());
			IClassifier clsf = new Classifier(classifier.getSecond());
			if (classifier.getSecond().getExpressionMatch() != null) {
				this.expressions.add(classifier.getSecond().getExpressionMatch());
			}
			if (concept == null) {
				throw new KlabValidationException(
						"classification: concept declaration is illegal: " + classifier.getFirst());
			}
			classifiers.add(new Pair<>(concept, clsf));
			conceptOrder.add(concept);
		}

		initialize();
	}

	/**
	 * Use to build a number -> concept classification when we know all the concepts
	 * and we need to preserve their order.
	 * 
	 * @param rootClass
	 * @param conceptOrder
	 */
	public Classification(IConcept rootClass, List<IConcept> conceptOrder) {
		this(rootClass);
		int i = 0;
		for (IConcept concept : conceptOrder) {
			this.classifiers.add(new Pair<>(concept, Classifier.NumberMatcher(i++)));
			this.conceptOrder.add(concept);
		}
		initialize();
	}

	public Classification(IConcept rootClass) {
		this.rootConcept = rootClass;
	}

	public void initialize() {

		if (initialized)
			return;

		initialized = true;

		/*
		 * we have no guarantee that the universal classifier, if there, will be last,
		 * given that it may come from a definition where the ordering isn't guaranteed.
		 * scan the classifiers and if we have a universal classifier make sure it's the
		 * last one, to avoid problems.
		 */
		int unidx = -1;
		int iz = 0;
		for (Pair<IConcept, IClassifier> cls : this.classifiers) {
			if (cls.getSecond().isUniversal()) {
				unidx = iz;
			}
			iz++;
		}

		if (unidx >= 0 && unidx < this.classifiers.size() - 1) {
			ArrayList<Pair<IConcept, IClassifier>> nc = new ArrayList<>();
			for (iz = 0; iz < this.classifiers.size(); iz++) {
				if (iz != unidx)
					nc.add(this.classifiers.get(iz));
			}
			nc.add(this.classifiers.get(unidx));
			this.classifiers = nc;
		}

		/*
		 * check if we have a nil classifier; if we don't we don't bother classifying
		 * nulls and save some work.
		 */
		for (Pair<IConcept, IClassifier> cl : this.classifiers) {
			if (cl.getSecond().isNil()) {
				this.nilClassifier = cl.getFirst();
				break;
			}
		}

		/*
		 * remap the values to ranks and determine how to rewire the input if necessary,
		 * use classifiers instead of lexicographic order to infer the appropriate
		 * concept order
		 */
		ArrayList<Classifier> cla = new ArrayList<>();
		ArrayList<IConcept> con = new ArrayList<>();
		for (Pair<IConcept, IClassifier> op : this.classifiers) {
			cla.add((Classifier) op.getSecond());
			con.add(op.getFirst());
		}

		this.distributionBreakpoints = computeDistributionBreakpoints(cla, con);

	}

	/**
	 * This one checks if all classifiers are the discretization of a continuous
	 * distribution. If so, it ranks them in order and returns an array of
	 * breakpoints that define the continuous distribution they represent. If the
	 * classifiers are not like that, it returns null. This does not touch or rank
	 * the concepts. If the concepts have a ranking (such as the lexicographic
	 * ranking found in Metadata.rankConcepts() it is the user's responsibility that
	 * the concepts and the ranges make sense together. We do, however, enforce that
	 * continuous ranges are propertly defined if the observable is the
	 * discretization of a continuous range.
	 * 
	 * @return null if we don't encode a continuous discretization; otherwise a pair
	 *         containing the breakpoints as a double[] (n+1) and a vector of
	 *         concepts in the order defined by the intervals (size n). If the
	 *         concept list was not passed, the concept array will be filled with
	 *         nulls.
	 * @throws KlabValidationException if the observable is a continuous range
	 *                                 mapping but the classification has disjoint
	 *                                 intervals.
	 */
	double[] computeDistributionBreakpoints(Collection<Classifier> cls, List<IConcept> classes) {

		if (cls.size() < 1)
			return null;

		double[] ret = null;

		ArrayList<Triple<Double, Double, IConcept>> ranges = new ArrayList<>();

		int i = 0;
		for (Classifier c : cls) {
			if (!c.isInterval())
				return null;
			Range iv = c.getInterval();
			IConcept concept = classes == null ? null : classes.get(i++);
			double d1 = iv.isLeftInfinite() ? Double.NEGATIVE_INFINITY : iv.getLowerBound();
			double d2 = iv.isRightInfinite() ? Double.POSITIVE_INFINITY : iv.getUpperBound();
			ranges.add(new Triple<>(d1, d2, concept));
		}

		/*
		 * sort ranges so that they appear in ascending order
		 */
		Collections.sort(ranges, new Comparator<Triple<Double, Double, IConcept>>() {

			@Override
			public int compare(Triple<Double, Double, IConcept> o1, Triple<Double, Double, IConcept> o2) {

				if (Double.compare(o1.getFirst(), o2.getFirst()) == 0
						&& Double.compare(o1.getSecond(), o2.getSecond()) == 0)
					return 0;

				return o2.getFirst() >= o1.getSecond() ? -1 : 1;
			}
		});

		/*
		 * sorted vector of concepts
		 */
		IConcept[] cret = new IConcept[ranges.size()];
		for (int jc = 0; jc < ranges.size(); jc++)
			cret[jc] = ranges.get(jc).getThird();

		/*
		 * build vector from sorted array
		 */
		boolean isContinuous = true;
		ret = new double[ranges.size() + 1];
		i = 0;
		double last = 0.0;
		ret[i++] = ranges.get(0).getFirst();
		last = ranges.get(0).getSecond();
		for (int n = 1; n < ranges.size(); n++) {

			Triple<Double, Double, IConcept> pd = ranges.get(n);

			/*
			 * we don't allow ordered range mappings to have disjoint intervals
			 */
			if (Double.compare(pd.getFirst(), last) != 0) {
				isContinuous = false;
			}
			ret[i++] = pd.getFirst();
			last = pd.getSecond();
			if (n == ranges.size() - 1)
				ret[i++] = last;
		}

		/*
		 * ret != null so we are continuous and sortable. redefine the order of the
		 * concepts (whether we are continuous or not).
		 */
		conceptOrder.clear();
		for (IConcept c : cret) {
			conceptOrder.add(c);
		}

		return isContinuous ? ret : null;
	}

	@Override
	public Iterator<Pair<IConcept, IClassifier>> iterator() {
		return classifiers.iterator();
	}

	@Override
	public IConcept getConcept() {
		return rootConcept;
	}

	@Override
	public boolean isDiscretization() {
		return discretization;
	}

	@Override
	public boolean isContiguousAndFinite() {

		if (distributionBreakpoints == null)
			return false;

		return !(Double.isInfinite(distributionBreakpoints[0])
				|| Double.isInfinite(distributionBreakpoints[distributionBreakpoints.length - 1]));
	}

	@Override
	public IConcept classify(Object object, IContextualizationScope context) {

		if (object instanceof Number && Double.isNaN(((Number) object).doubleValue())) {
			object = null;
		}

		if (object == null && nilClassifier != null) {
			return nilClassifier;
		}

		for (Pair<IConcept, IClassifier> p : this.classifiers) {
			if (p.getSecond().classify(object, context)) {
				return p.getFirst();
			}
		}

		return null;
	}

	@Override
	public double undiscretize(IConcept object) {
		double ret = Double.NaN;
		if (distributionBreakpoints != null && distributionBreakpoints.length == (conceptOrder.size() + 1)) {
			for (int i = 0; i < conceptOrder.size(); i++) {
				if (object.equals(conceptOrder.get(i))) {
					ret = distributionBreakpoints[i]
							+ ((distributionBreakpoints[i + 1] - distributionBreakpoints[1]) / 2.0);
					break;
				}
			}
		}
		return ret;
	}

	@Override
	public double getNumericValue(IConcept o) {
		if (numCodes == null) {
			numCodes = new HashMap<>();
			if (distributionBreakpoints != null) {
				for (int i = 0; i < conceptOrder.size(); i++) {
					double val = distributionBreakpoints[i]
							+ ((distributionBreakpoints[i + 1] - distributionBreakpoints[i]) / 2.0);
					numCodes.put(conceptOrder.get(i), val);
				}
			} else {
				for (int i = 0; i < conceptOrder.size(); i++) {
					numCodes.put(conceptOrder.get(i), (double) (i + 1));
				}
			}
		}
		return numCodes.get(o);
	}

	public int getRank(IConcept object) {
		if (this.conceptIndexes == null) {
			conceptIndexes = new HashMap<>();
			for (int i = 0; i < conceptOrder.size(); i++) {
				conceptIndexes.put(conceptOrder.get(i), i);
			}
		}
		Integer ret = conceptIndexes.get(object);
		return ret == null ? -1 : ret;
	}

	@Override
	public int classifyToIndex(Object o, IContextualizationScope monitor) {

		if (conceptIndexes == null) {
			conceptIndexes = new HashMap<>();
			for (int i = 0; i < conceptOrder.size(); i++) {
				conceptIndexes.put(conceptOrder.get(i), i);
			}
		}
		IConcept c = classify(o, monitor);
		if (c == null) {
			return -1;
		}
		Integer ret = conceptIndexes.get(c);
		return ret == null ? -1 : ret;
	}

	public static Classification create(IConcept rootClass) {
		Classification ret = new Classification(rootClass);
		// TODO this doesn't do much interesting, should probably be hidden
		return ret;
	}

	public void addClassifier(Classifier classifier, IConcept c) {
		classifiers.add(new Pair<>(c, classifier));
		conceptOrder.add(c);
	}

	@Override
	public int reverseLookup(Object value) {
		return getRank((IConcept) value);
	}

	@Override
	public int size() {
		return classifiers.size();
	}

	@Override
	public List<String> getLabels() {
		List<String> ret = new ArrayList<>();
		for (IConcept c : conceptOrder) {
			ret.add(Concepts.INSTANCE.getDisplayName(c));
		}
		return ret;
	}

	@Override
	public boolean isOrdered() {
		for (IConcept c : conceptOrder) {
			if (!c.is(Type.ORDERING)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Pair<Integer, String>> getAllValues() {
		List<Pair<Integer, String>> ret = new ArrayList<>();
		for (IConcept c : conceptOrder) {
			ret.add(new Pair<>(getRank(c), Concepts.INSTANCE.getDisplayName(c)));
		}
		return ret;
	}

	@Override
	public Object lookup(int index) {
		return conceptOrder.get(index);
	}

	public Collection<IKimExpression> getUniqueExpressions() {
		return this.expressions;
	}

	@Override
	public List<String> getSerializedObjects() {
		List<String> ret = new ArrayList<>();
		for (IConcept c : conceptOrder) {
			ret.add(c.getDefinition());
		}
		return ret;
	}

	@Override
	public List<IConcept> getConcepts() {
		return conceptOrder;
	}

}
