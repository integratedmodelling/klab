package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.ObjectUtils;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.rest.ModelReference;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

import com.vividsolutions.jts.geom.Geometry;

public class Prioritizer implements IPrioritizer<ModelReference> {

	ResolutionScope scope;
	ComparatorChain comparator = null;
	HashMap<ModelReference, Map<String, Double>> ranks = new HashMap<>();
	HashMap<ModelReference, double[]> idxss = new HashMap<>();

	List<String> orderedCriteria = new ArrayList<>();
	List<String> subjectiveCriteria = new ArrayList<>();

	private static IMetadata defaultStrategy = null;
	private static HashSet<String> rankingCriteria = new HashSet<>();

	static {
		rankingCriteria.add(NS.LEXICAL_SCOPE);
		rankingCriteria.add(NS.TRAIT_CONCORDANCE);
		rankingCriteria.add(NS.SEMANTIC_DISTANCE);
//		rankingCriteria.add(NS.SCALE_SPECIFICITY);
//		rankingCriteria.add(NS.SCALE_COVERAGE);
		rankingCriteria.add(NS.TIME_SPECIFICITY);
		rankingCriteria.add(NS.TIME_COVERAGE);
		rankingCriteria.add(NS.SPACE_SPECIFICITY);
		rankingCriteria.add(NS.SPACE_COVERAGE);
		rankingCriteria.add(NS.INHERENCY);
		rankingCriteria.add(NS.EVIDENCE);
		rankingCriteria.add(NS.NETWORK_REMOTENESS);
		rankingCriteria.add(NS.SCALE_COHERENCY);
		rankingCriteria.add(NS.SUBJECTIVE_CONCORDANCE);
	}

	class FieldComparator implements Comparator<Map<String, Object>> {

		String _field;

		FieldComparator(String field) {
			_field = field;
		}

		@SuppressWarnings({ "rawtypes" })
		@Override
		public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			Comparable n1 = (Comparable) o1.get(_field);
			Comparable n2 = (Comparable) o2.get(_field);

			/*
			 * comparator is icky if it gets an int and a double.
			 */
			if (n1 instanceof Number) {
				n1 = ((Number) n1).doubleValue();
			}
			if (n2 instanceof Number) {
				n2 = ((Number) n2).doubleValue();
			}
			// TODO restore the null acceptance after testing. Should not get nulls.
			return ObjectUtils.compare(n1, n2/* , false */);
		}
	}

	public Prioritizer(IResolutionScope context) {
		scope = (ResolutionScope) context;
		buildComparisonStrategy();
	}

	@Override
	public int compare(ModelReference o1, ModelReference o2) {
		return comparator.compare(getRanks(o1), getRanks(o2));
	}

	public Map<String, Double> getRanks(ModelReference md) {

		if (ranks.get(md) == null) {
			ranks.put((ModelReference) md, computeCriteria(md, scope));
		}
		return ranks.get(md);
	}

	@SuppressWarnings("unchecked")
	private void buildComparisonStrategy() {

		IMetadata strategy = scope.getResolutionNamespace() == null ? null
				: scope.getResolutionNamespace().getResolutionCriteria();
		if (strategy == null) {
			strategy = getDefaultRankingStrategy();
		}

		/*
		 * Resolution strategy contains the ordering of sequentially compared criteria
		 * and any subjective traits we want to rank. This scans the strategy and builds
		 * the comparator using the specified ordering, plus the definition of the
		 * aggregated subjective ranking function.
		 */
		ArrayList<String> criteria = new ArrayList<>();
		for (String s : strategy.keySet()) {
			if (rankingCriteria.contains(s)) {
				criteria.add(s);
			} else {
				subjectiveCriteria.add(s);
			}
		}

		final BidiMap tmp = new DualHashBidiMap();
		for (String c : criteria) {
			int ord = strategy.get(c, Integer.class);
			if (ord == 0)
				continue;
			if (tmp.get(ord) == null) {
				tmp.put(ord, c);
			} else {
				tmp.put(ord, tmp.get(ord) + "," + c);
			}
		}

		/*
		 * 2. sort
		 */
		orderedCriteria.addAll(tmp.values());
		Collections.sort(orderedCriteria, new Comparator<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				Integer r0 = (Integer) tmp.getKey(arg0);
				return r0.compareTo((Integer) tmp.getKey(arg1));
			}
		});

		/*
		 * build comparator for the desired criteria
		 */
		comparator = new ComparatorChain();

		for (String cr : orderedCriteria) {
			comparator.addComparator(new FieldComparator(cr), true);
		}
	}

	private IMetadata getDefaultRankingStrategy() {

		/*
		 * use a default ordering that may be overridden in thinklab.properties.
		 */
		if (defaultStrategy == null) {

			defaultStrategy = new Metadata();

			String dstrat = Configuration.INSTANCE.getProperties().getProperty(DEFAULT_STRATEGY_PROPERTY_NAME,
					DEFAULT_RANKING_STRATEGY);

			String[] ranking = dstrat.split("\\s+");

			for (int i = 0; i < ranking.length; i++) {
				defaultStrategy.put(ranking[i], Integer.parseInt(ranking[++i]));
			}
		}
		return defaultStrategy;
	}

	/**
	 * Compute the standard criterion identified by cr. For aggregated criteria, use
	 * 
	 * @param cr
	 * @param model
	 * @param context
	 * @return criterion value
	 */
	public double computeStandardCriterion(String cr, ModelReference model, ResolutionScope context) {

		if (cr.equals(NS.EVIDENCE)) {
			return computeEvidence(model, context);
		} else if (cr.equals(NS.SUBJECTIVE_CONCORDANCE)) {
			return computeSubjectiveConcordance(model, context, subjectiveCriteria);
		} else if (cr.equals(NS.SCALE_COVERAGE)) {
			return computeScaleCoverage(model, context);
		} else if (cr.equals(NS.SCALE_COHERENCY)) {
			return computeScaleCoherency(model, context);
		} else if (cr.equals(NS.INHERENCY)) {
			return computeInherency(model, context);
		} else if (cr.equals(NS.NETWORK_REMOTENESS)) {
			return computeNetworkRemoteness(model, context);
		} else if (cr.equals(NS.SCALE_SPECIFICITY)) {
			return computeScaleSpecificity(model, context);
		} else if (cr.equals(NS.TRAIT_CONCORDANCE)) {
			return computeTraitConcordance(model, context);
		} else if (cr.equals(NS.LEXICAL_SCOPE)) {
			return computeLexicalScope(model, context);
		} else if (cr.equals(NS.SEMANTIC_DISTANCE)) {
			return computeSemanticDistance(model, context);
		} else if (cr.equals(NS.SPACE_COHERENCY)) {
			return computeSpaceCoherency(model, context);
		} else if (cr.equals(NS.SPACE_COVERAGE)) {
			return computeSpaceCoverage(model, context);
		} else if (cr.equals(NS.SPACE_SPECIFICITY)) {
			return computeSpaceSpecificity(model, context);
		} else if (cr.equals(NS.TIME_COHERENCY)) {
			return computeTimeCoherency(model, context);
		} else if (cr.equals(NS.TIME_COVERAGE)) {
			return computeTimeCoverage(model, context);
		} else if (cr.equals(NS.TIME_SPECIFICITY)) {
			return computeTimeSpecificity(model, context);
		}

		return 0;
	}

	private double computeTimeSpecificity(ModelReference model, ResolutionScope context) {
		return computeTemporalCriteria(model, context)[1];
	}

	private double computeTimeCoverage(ModelReference model, ResolutionScope context) {
		return computeTemporalCriteria(model, context)[0];
	}

	private double computeTimeCoherency(ModelReference model, ResolutionScope context) {
		return computeTemporalCriteria(model, context)[2];
	}

	private double computeSpaceSpecificity(ModelReference model, ResolutionScope context) {
		return computeSpatialCriteria(model, context)[1];
	}

	private double computeSpaceCoverage(ModelReference model, ResolutionScope context) {
		return computeSpatialCriteria(model, context)[0];
	}

	private double computeSpaceCoherency(ModelReference model, ResolutionScope context) {
		return computeSpatialCriteria(model, context)[2];
	}

	@Override
	public Map<String, Double> computeCriteria(ModelReference model, IResolutionScope context) {

		Map<String, Double> ret = new HashMap<>();

		for (String cr : orderedCriteria) {

			if (cr.contains(",")) {
				ret.put(cr, computeCustomAggregation(cr, (ModelReference) model, context));
			} else {
				ret.put(cr, computeStandardCriterion(cr, (ModelReference) model, (ResolutionScope) context));
			}
		}

		// ret.put(IPrioritizer.OBJECT_NAME, ((ModelReference) model).getName());
		// ret.put(IPrioritizer.PROJECT_NAME, ((ModelReference) model).getProjectUrn());
		// ret.put(IPrioritizer.NAMESPACE_ID, ((ModelReference)
		// model).getNamespaceId());
		// ret.put(IPrioritizer.SERVER_ID, ((ModelReference) model).getServerId());

		ranks.put((ModelReference) model, ret);

		return ret;
	}

	/*
	 * Compute a customized aggregation of two or more standard criteria.
	 * Aggregation is equally weighted. These criteria have been given the same
	 * order in the strategy specifications.
	 */
	private double computeCustomAggregation(String def, ModelReference model, IResolutionScope context) {
		String[] ddef = def.split(",");
		ArrayList<Pair<Integer, Integer>> vals = new ArrayList<>();
		Map<String, Double> dt = getRanks(model);
		for (String cr : ddef) {
			vals.add(new Pair<>(dt.containsKey(cr) ? ((Number) (dt.get(cr))).intValue() : 50, 100));
		}
		return aggregate(vals);
	}

	/*
	 * lexical scope -> locality wrt context
	 * 
	 * 100 = in observation scenario 75 = in same namespace as context 50-26 closer
	 * to same namespace as context 25 = in same project as context 0 = non-private
	 * in other visible namespace
	 * 
	 * TODO #3 should use a hierarchy criterion: something in namespace xxxx.yyyy is
	 * satisfied preferentially by something in namespace xxxx unless criteria 1 or
	 * 2 are satisfied.
	 * 
	 * From the docs (still not implemented as such):
	 * 
	 * 1. Scenario 2. Private in same namespace 3. Public in same namespace 4.
	 * Public in parent namespace (in order of distance) 5. Project private in same
	 * project 6. Public local or cached 7. Public from the network
	 */
	public double computeLexicalScope(ModelReference model, ResolutionScope context) {

		// Scenarios always win.
		// second check should not really be necessary, as the query should only have
		// gotten
		// the relevant scenarios or none.
		if (model.isInScenario() && context.getScenarios().contains(model.getNamespaceId())) {
			return 100;
		}

		INamespace ns = Namespaces.INSTANCE.getNamespace(model.getNamespaceId());

		if (ns == null) {
			Logging.INSTANCE.warn("found model " + model.getName() + " referencing unknown namespace: ignoring");
			return 0;
		}

		IWorkspace ws = ns.getProject().getWorkspace();

		INamespace rns = context.getResolutionNamespace();
		if (rns != null && rns.getId().equals(ns.getId())) {
			return 75;
		}
		IWorkspace wsc = (rns == null || rns.getProject() == null) ? null : rns.getProject().getWorkspace();

		/*
		 * between 25 and 50 is attributed to namespace being traceable in dependency
		 * chain.
		 */
		int ret = 0;
		int nsDistance = context.getNamespaceDistance(ns);
		if (nsDistance >= 0) {
			ret = 50 - (nsDistance > 24 ? 24 : nsDistance);
		}

		/*
		 * Workspace priority exists if the model comes from the local workspace or from
		 * the same workspace as the resolution.
		 */
		boolean workspaceHasPriority = ws != null
				&& (ws.getName().equals(Resources.INSTANCE.getLocalWorkspace().getName())
						|| (wsc != null && wsc.getName().equals(ws.getName())));

		/*
		 * between 1 and 25 is attributed to project being traceable.
		 */
		int prDistance = context.getProjectDistance(ns == null ? null : ns.getProject());
		if (prDistance >= 0) {
			ret = 25 - (prDistance > 24 ? 24 : prDistance);
		}

		/*
		 * the criteria (now 0-50) above occupy 25 points on a partition decided by the
		 * workspace criterion
		 */
		if (!workspaceHasPriority) {
			ret = (int) ((double) ret / 2.0);
		}

		return ret;
	}

	/*
	 * semantic distance. This makes sure that e.g. a matching abstract model is
	 * chosen only after a concrete one is rejected.
	 */
	public double computeSemanticDistance(ModelReference model, ResolutionScope context) {

		/*
		 * list of traits in common. Don't check the trait value - assumed the same
		 * because of the search strategy.
		 */
		try {
			IConcept provided = model.getObservableConcept();
			IConcept wanted = context.getObservable() == null ? null : context.getObservable().getType();
			if (provided == null || wanted == null) {
				// TODO should not happen
				return 100;
			}

			return getSemanticDistance(wanted, provided);

		} catch (Exception e) {
		}

		return 0;
	}

	int getSemanticDistance(IConcept o1, IConcept o2) {
		// TODO
		if (o1.equals(o2)) {
			return 0;
		}
		return 50;
	}

	/*
	 * trait concordance wrt context n = # of traits shared / #. of traits possible,
	 * normalized to 100 TODO REIMPLEMENT AS APPROPRIATE - a compatibility rank
	 * 
	 * FIXME does nothing at the moment.
	 */
	public double computeTraitConcordance(ModelReference model, IResolutionScope context) {

		/*
		 * list of traits in common. Don't check the trait value - assumed the same
		 * because of the search strategy.
		 */
		try {
			IConcept c = model.getObservableConcept(); // getWantedObservable(model,
														// context);
			if (c == null) {
				// TODO issues here - just a hack, should not happen
				return 0;
			}
			Collection<IConcept> attrs = Traits.INSTANCE.separateAttributes(c).getSecond();
			Collection<IConcept> wanted = new ArrayList<>(); // ((ResolutionScope) context).getTraits();
			int common = 0;

			if (attrs.size() == 0 && wanted.size() == 0)
				return 100.0;

			for (IConcept zio : wanted) {
				if (attrs.contains(zio)) {
					common++;
				}
			}
			if (wanted.size() > 0) {
				return 100.0 * ((double) common / (double) wanted.size());
			}

		} catch (Exception e) {
		}

		return 0;
	}

	/*
	 * scale specificity -> total coverage of object wrt context (minimum of all
	 * extents?) <n> = scale / (object coverage) * 100
	 */
	public double computeScaleSpecificity(ModelReference model, ResolutionScope context) {
		return computeScaleCriteria(model, context)[1];
	}

	/*
	 * return the (possibly cached) array of coverage, specificity and resolution.
	 */
	private double[] computeScaleCriteria(ModelReference model, ResolutionScope context) {

		double specificityS = -1;
		double coverageS = -1;
		double resolutionS = -1;
		double specificityT = -1;
		double coverageT = -1;
		double resolutionT = -1;

		if (!idxss.containsKey(model)) {

			if (model.getShape() != null) {
				double[] sc = computeSpatialCriteria(model, context);
				coverageS = sc[0];
				specificityS = sc[1];
				resolutionS = sc[2];
			}

			if (context.getCoverage().getTime() != null) {
				/*
				 * TODO do the same with time and take the minimum - or should this be the
				 * separate currency value ?
				 */
				double[] tc = computeTemporalCriteria(model, context);
				coverageT = tc[0];
				specificityT = tc[1];
				resolutionT = tc[2];
			}

			idxss.put(model, new double[] { getMin(coverageS, coverageT), getMin(specificityS, specificityT),
					getMin(resolutionS, resolutionT) });
		}

		return idxss.get(model);

	}

	private double[] computeSpatialCriteria(ModelReference model, ResolutionScope context) {
		double[] ret = new double[] { -1, -1, -1 };
		if (model.getShape() != null) {
			/*
			 * compute intersection if we're spatial
			 */
			ISpace space = context.getCoverage().getSpace();
			if (space != null) {
				Geometry cspace = ((Shape) space.getShape()).getStandardizedGeometry();
				Geometry intersection = cspace.intersection(((Shape) model.getShape()).getStandardizedGeometry());
				ret[1] = 100.0
						* (intersection.getArea() / ((Shape) model.getShape()).getStandardizedGeometry().getArea());
				ret[0] = 100.0 * (intersection.getArea() / cspace.getArea());
			}
		}
		return ret;
	}

	private double[] computeTemporalCriteria(ModelReference model, ResolutionScope scope) {

		double[] ret = new double[] { -1, -1, -1 };
		ITime time = scope.getCoverage().getTime();

		Range mrange = Range.create(model.getTimeStart() == -1 ? null : model.getTimeStart(),
				model.getTimeEnd() == -1 ? null : model.getTimeEnd());
		Range crange = Range.create(time.getStart(), time.getEnd());

		/*
		 * coverage: if non-grid, 100 for covered, 75 - [0-25] for partially covered, 50
		 * - [0-25] distance if covered in infinite tail from or to a single-point
		 * beginning or end. If grid, covered.
		 */
//		if (time.size() > 1) {
//
//		} else {

		double d = mrange.exclusionOf(crange);

		if (d == 1) {
			ret[0] = 1; // very least but we don't reject
		} else if (d == 0) {
			ret[0] = 100;
		} else if (mrange.isBounded()) {
			ret[0] = 75 - (d * 25);
		} else {
			ret[0] = 50 - (d * 49);
		}

//		}

		/*
		 * specificity differs by resolution type (even if generic) and is corrected by
		 * the order of magnitude of the nearest multiplier. If the context is
		 * universal, return 50.
		 */

		if (crange.isBounded()) {

			double focalPointModel = mrange.isLeftBounded() ? mrange.getLowerBound() : mrange.getFocalPoint();
			double focalPointContext = crange.getLowerBound();

			if (time.size() > 1) {

				if (Double.isNaN(focalPointModel)) {
					ret[1] = 0;
				} else {
					if (mrange.contains(crange)) {
						ret[1] = 100;
					} else {
						ret[1] = 100 * (mrange.getWidth() / crange.getWidth());
						if (ret[1] > 100) {
							ret[1] = 100;
						}
					}
				}

			} else if (Double.isNaN(focalPointModel)) {
				ret[1] = 25;
			} else {

				double distanceFactor = (focalPointModel > focalPointContext) ? (focalPointModel - focalPointContext)
						: (focalPointContext - focalPointModel);
				distanceFactor /= (time.getResolution().getType().getMilliseconds()
						* time.getResolution().getMultiplier());

				if (distanceFactor > 50) {
					distanceFactor = 50;
				}

				ret[1] = 100 - 50 * (distanceFactor / 50);
			}

		} else {
			ret[1] = 50;
		}

		/*
		 * resolution: if non-grid, 100 for identical, 75 - ([0-25] distance factor) for
		 * overlapping. Else it's like space. Zero if grid goes with non-grid or the
		 * other way around.
		 */

		return ret;
	}

	private double getMin(double a, double b) {
		if (a < 0 && b < 0)
			return 0.0;
		if (a < 0)
			return b;
		if (b < 0)
			return a;

		return Math.min(a, b);
	}

	/*
	 * network remoteness -> whether coming from remote KBox (added by kbox
	 * implementation) 100 -> local 0 -> remote
	 */
	public static double computeNetworkRemoteness(ModelReference model, IResolutionScope context) {
		return model.getServerId() == null ? 100 : 0;
	}

	/*
	 * inherency -> level wrt observable: 100 = same thing-ness, specific inherency
	 * (model and context are inherent to same thing) 66 = same thing-ness,
	 * non-specific inherency 33 = different thing-ness, mediatable inherency
	 * 
	 * TODO level of inherency at highest level should be modulated by semantic
	 * distance between object and context, with 100 reserved for inherent to
	 * exactly same object type.
	 * 
	 */
	public double computeInherency(ModelReference model, IResolutionScope context) {
		return 0.0;
	}

	/*
	 * scale coherency -> coherency of domains adopted by context vs. the object n =
	 * # of domains shared (based on the isSpatial/isTemporal fields) normalize to
	 * 100 TODO reimplement this with the geometry
	 */
	public double computeScaleCoherency(ModelReference model, IResolutionScope context) {
		// TODO Auto-generated method stub
		return 0.0;
	}

	/*
	 * scale coverage -> of scale in context (minimum of all extents? or one per
	 * extent?) 0 = not scale-specific (outside scale will not be returned) (1, 100]
	 * = (scale ^ object context) / scale
	 */
	public double computeScaleCoverage(ModelReference model, ResolutionScope context) {
		return computeScaleCriteria(model, context)[0];
	}

	/*
	 * subjective concordance = multi-criteria ranking of user-defined metadata wrt
	 * default or namespace priorities
	 * 
	 * @returns chosen concordance metric normalized to 100
	 */
	public double computeSubjectiveConcordance(ModelReference model, IResolutionScope context,
			List<String> subjectiveCriteria) {

		if (context.getResolutionNamespace() == null) {
			// happens in non-semantic queries where the context is a data resource
			return 0;
		}

		ArrayList<Pair<Integer, Integer>> vals = new ArrayList<>();
		IMetadata nm = context.getResolutionNamespace().getResolutionCriteria();

		for (String s : subjectiveCriteria) {

			int val = (model.getMetadata() != null && model.getMetadata().containsKey(s))
					? Integer.parseInt(model.getMetadata().get(s))
					: 50;
			int wei = 100;
			if (nm != null && nm.get(s) != null) {
				wei = context.getResolutionNamespace().getResolutionCriteria().get(s, Integer.class);
			} else if (defaultStrategy.get(s) != null) {
				wei = defaultStrategy.get(s, Integer.class);
			}
			vals.add(new Pair<>(val, wei));
		}
		return aggregate(vals) * 100;
	}

	/*
	 * evidence -> resolved/unresolved 100 = resolved from data or object source 75
	 * = resolved but requires dereification 50 = computed, no dependencies 0 =
	 * unresolved
	 */
	public double computeEvidence(ModelReference model, IResolutionScope context) {

		if ((model.isHasDirectData() || model.isHasDirectObjects()) && model.getDereifyingAttribute() == null) {
			return 100.0;
		}
		if ((model.isHasDirectData() || model.isHasDirectObjects()) && model.getDereifyingAttribute() != null) {
			return 75.0;
		}
		if (model.isResolved()) {
			return 50.0;
		}
		return 0.0;
	}

	/**
	 * Implement the weighted multiplicative aggregation for subjective criteria
	 * expressed numerically with a 0-100 value. Each pair <v,w> of value and weight
	 * is interpreted as a "benefit" criterion that contributes to the overall score
	 * in a proportion that depends on its weight - the higher the weight, the
	 * higher the contribution. All scores are multiplied when still in the [0 1]
	 * range. The combination of low values is guaranteed to make the value lower.
	 * 
	 * It is meant for "benefit" criteria that correlate directly to value, and the
	 * weight of each criterion defines the amount of change it can produce in the
	 * final index - higher weight, higher importance (the criterion value only
	 * changes the (100 - w)/100 proportion of the [0 1] interval). For the index to
	 * be meaningful, only indices that refer to exactly the same criteria can be
	 * compared - the safe bet for missing values is to make them "average" by
	 * giving them value 50.
	 * 
	 * @param values collection of pairs <value, weight> for each criterion. Both
	 *               value and weight must be integers in the 0-100 range.
	 * @return aggregated criterion value
	 */
	public double aggregate(Collection<Pair<Integer, Integer>> values) {

		double ret = Double.NaN;

		for (Pair<Integer, Integer> vp : values) {

			double intProportion = 100.0 * ((double) vp.getSecond() / 100.0);
			double base = 100.0 - (double) vp.getSecond();
			double c = (base + intProportion * ((double) vp.getFirst() / 100.0)) / 100.0;
			ret = Double.isNaN(ret) ? c : ret * c;
		}

		return ret;
	}

	@Override
	public List<String> listCriteria() {
		return orderedCriteria;
	}

	public Map<String, Double> getCriteria() {
		Map<String, Double> ret = new HashMap<>();
		IMetadata strategy = scope.getResolutionNamespace() == null ? null
				: scope.getResolutionNamespace().getResolutionCriteria();
		if (strategy == null) {
			strategy = getDefaultRankingStrategy();
		}
		for (String s : orderedCriteria) {
			ret.put(s, (strategy.get(s) == null ? 50.0 : ((Number) strategy.get(s)).doubleValue()));
		}
		for (String s : subjectiveCriteria) {
			ret.put(s, (strategy.get(s) == null ? 50.0 : ((Number) strategy.get(s)).doubleValue()));
		}
		return ret;
	}

	public String asText() {
		String ret = "";
		IMetadata strategy = scope.getResolutionNamespace() == null ? null
				: scope.getResolutionNamespace().getResolutionCriteria();
		if (strategy == null) {
			strategy = getDefaultRankingStrategy();
		}
		for (String s : orderedCriteria) {
			if (!ret.isEmpty()) {
				ret += ",";
			}
			ret += s + "=" + ((strategy.get(s) == null ? 50 : strategy.get(s)));
		}
		for (String s : subjectiveCriteria) {
			if (!ret.isEmpty()) {
				ret += ",";
			}
			ret += s + "=" + ((strategy.get(s) == null ? 50 : strategy.get(s)));
		}
		return ret;
	}

	/*
	 * call to register ranks that were computed outside this object. Used for model
	 * data coming from the remote search service.
	 */
	public void registerRanks(ModelReference md) {
		ranks.put(md, md.getRanks());
	}
}
