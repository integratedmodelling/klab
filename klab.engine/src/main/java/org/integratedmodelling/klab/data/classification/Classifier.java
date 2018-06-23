package org.integratedmodelling.klab.data.classification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.runtime.code.Expression;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;

public class Classifier implements IClassifier {

	private Classifier() {
		// TODO Auto-generated constructor stub
	}

	Classifier(IKimClassifier statement) {

		this.sourceCode = statement.getSourceCode().trim();
		
		this.numberMatch = statement.getNumberMatch();
		this.anythingMatch = statement.isCatchAnything();
		this.catchAll = statement.isCatchAll();
		this.intervalMatch = statement.getIntervalMatch();
		if (statement.getConceptMatch() != null) {
			this.conceptMatch = Concepts.INSTANCE.declare(statement.getConceptMatch());
		}
		this.stringMatch = statement.getStringMatch();
		this.booleanMatch = statement.getBooleanMatch() == null ? null : (statement.getBooleanMatch() ? 1 : 0);
		this.negated = statement.isNegated();
		if (statement.getConceptMatches() != null) {
			this.conceptMatches = new ArrayList<>();
			for (IKimConcept cstatement : statement.getConceptMatches()) {
				// TODO support collections
				this.conceptMatches.add(Collections.singletonList(Concepts.INSTANCE.declare(cstatement)));
			}
		}
		if (statement.getClassifierMatches() != null) {
			this.classifierMatches = new ArrayList<>();
			for (IKimClassifier cstatement : statement.getClassifierMatches()) {
				this.classifierMatches.add(new Classifier(cstatement));
			}
		}
		// this.expressionMatch = statement.getExpressionMatch();
	}

	private ArrayList<Classifier> classifierMatches = null;
	private Double numberMatch = null;
	private Range intervalMatch = null;
	private IConcept conceptMatch = null;
	private String stringMatch = null;
	private Integer booleanMatch = null;
	private boolean negated = false;
	private IExpression expressionMatch = null;
	private boolean anythingMatch = false;
	private String sourceCode;

	// each sublist is in AND, each concept in each list is in OR
	protected List<List<IConcept>> conceptMatches = null;
	protected HashMap<String, Boolean> _reasonCache;

	// if not null, we're a classifier for a particularly inherited trait, which
	// enables
	// different optimizations.
	IConcept trait = null;

	/*
	 * if true, this is an :otherwise classifier, that needs to be known by the
	 * classification
	 */
	protected boolean catchAll = false;

	/*
	 * if true, this is a classifier specifically meant to reclassify nil/null;
	 * normally, nil does not reclassify unless there is one of these in a
	 * classification.
	 */
	protected boolean nullMatch = false;

	private Classifier(Object o) {

		if (o instanceof Number) {
			numberMatch = ((Number) o).doubleValue();
		} else if (o instanceof String) {
			stringMatch = (String) o;
		} else if (o instanceof IConcept) {
			conceptMatch = (IConcept) o;
		} else if (o instanceof Range) {
			intervalMatch = (Range) o;
		} else if (o == null) {
			nullMatch = true;
		} else {
			throw new KlabValidationException("cannot create classifier to match unsupported object type: " + o);
		}
	}

	@Override
	public boolean isUniversal() {
		return catchAll;
	}

	// called by the classification
	void reset() {
		_reasonCache = null;
	}

	@Override
	public boolean classify(Object o, IMonitor monitor) {

		if (anythingMatch) {
			return true;
		}

		if (catchAll && o != null && !(o instanceof Double && Double.isNaN((Double) o))) {
			return true;
		}

		if (o == null)
			return negated ? !nullMatch : nullMatch;

		if (numberMatch != null) {

			Number n = asNumber(o);
			if (n == null) {
				return false;
			}
			return negated ? !NumberUtils.equal(numberMatch, asNumber(o)) : NumberUtils.equal(numberMatch, asNumber(o));

		} else if (booleanMatch != null) {

			return negated ? asBoolean(o) != (booleanMatch > 0) : asBoolean(o) == (booleanMatch > 0);

		} else if (classifierMatches != null) {

			for (Classifier cl : classifierMatches) {
				if (cl.classify(o, monitor))
					return true;
			}

		} else if (intervalMatch != null) {

			Double d = asNumber(o);
			if (d != null)
				return negated ? !intervalMatch.contains(d) : intervalMatch.contains(d);

		} else if (conceptMatch != null) {

			return negated ? !is(asConcept(o), conceptMatch) : is(asConcept(o), conceptMatch);

		} else if (stringMatch != null) {

			return negated ? !stringMatch.equals(o.toString()) : stringMatch.equals(o.toString());

		} else if (expressionMatch != null) {

			try {
				/*
				 * TODO find an elegant way to communicate external parameter maps, and set
				 * :self = o in it.
				 */
				Parameters parms = new Parameters();
				parms.put("self", o);
				// FIXME pass a proper monitor
				return negated
						? !(Boolean) expressionMatch.eval(parms,
								/* TODO pass a context */ Expression.emptyContext(monitor))
						: (Boolean) expressionMatch.eval(parms,
								/* TODO pass a context */ Expression.emptyContext(monitor));

			} catch (Exception e) {
				throw new KlabValidationException(e);
			}
		} else if (conceptMatches != null) {

			IConcept cc = asConcept(o);
			for (List<IConcept> or : conceptMatches) {
				boolean oneOk = false;
				for (IConcept oc : or) {
					if (negated ? !is(cc, conceptMatch) : is(cc, conceptMatch)) {
						oneOk = true;
						break;
					}
					if (!oneOk) {
						return false;
					}
				}
			}
			return true;
		}

		return false;
	}

	/**
	 * Calls is() on the concepts, but caches the result so that the same invocation
	 * will be cheap. We're may call this hundreds of thousands of times, and it's
	 * safe to assume that the ontology won't change between invocations.
	 * 
	 * @param asConcept
	 * @param conceptMatch2
	 * @return
	 */
	private boolean is(IConcept c1, IConcept c2) {

		String key = c1 + "#" + c2;
		Boolean ret = null;
		if (_reasonCache != null)
			ret = _reasonCache.get(key);
		if (ret == null) {
			if (_reasonCache == null) {
				_reasonCache = new HashMap<String, Boolean>();
			}
			ret = c1.is(c2);
			_reasonCache.put(key, ret);
		}
		return ret;
	}

	private IConcept asConcept(Object o) {

		if (o instanceof IConcept)
			return (IConcept) o;

		/*
		 * TODO no provision for parsing from string
		 */

		return null;
	}

	private Double asNumber(Object o) {

		Double ret = null;
		if (o instanceof Number) {
			ret = ((Number) o).doubleValue();
		}
		return ret;
	}

	private boolean asBoolean(Object o) {

		Boolean ret = false;
		if (o instanceof Boolean) {
			ret = ((Boolean) o);
		}
		return ret;
	}

	public void addClassifier(Classifier c) {
		if (classifierMatches == null)
			classifierMatches = new ArrayList<Classifier>();
		classifierMatches.add(c);
	}

	public void setConcept(IConcept c) {
		conceptMatch = c;
	}

	public void setInterval(Range interval) {
		this.intervalMatch = interval;
	}

	public void setNumber(Object classifier) {
		numberMatch = asNumber(classifier);
	}

	@Override
	public String toString() {
		String ret = null;
		if (classifierMatches != null) {
			ret = "mul:";
			for (Classifier c : classifierMatches) {
				ret += "[" + c + "]";
			}
		} else if (numberMatch != null) {
			ret = "num:" + numberMatch;
		} else if (intervalMatch != null) {
			ret = "int:" + intervalMatch;
		} else if (conceptMatch != null) {
			ret = "con:" + conceptMatch;
		} else if (stringMatch != null) {
			ret = "str:" + stringMatch;
		} else if (catchAll) {
			ret = "tru:true";
		} else if (nullMatch) {
			ret = "nil:true";
		}
		return ret;
	}

	public void setCatchAll() {
		this.catchAll = true;
	}

	public void setString(String classifier) {
		this.stringMatch = classifier;
	}

	public void setNil() {
		this.nullMatch = true;
	}

	@Override
	public boolean isInterval() {
		return intervalMatch != null;
	}

	public Range getInterval() {
		return intervalMatch;
	}

	@Override
	public boolean isNil() {
		return this.nullMatch;
	}

	public void setExpression(IExpression e) {
		this.expressionMatch = e;
	}

	public static Classifier NumberMatcher(Number n) {
		Classifier ret = new Classifier();
		ret.numberMatch = n.doubleValue();
		return ret;
	}

	public static Classifier BooleanMatcher(boolean n) {
		Classifier ret = new Classifier();
		ret.booleanMatch = n ? 1 : 0;
		return ret;
	}

	public static Classifier RangeMatcher(Range interval) {
		Classifier ret = new Classifier();
		ret.intervalMatch = interval;
		return ret;
	}

	public static Classifier ConceptMatcher(IConcept concept) {
		Classifier ret = new Classifier();
		ret.conceptMatch = concept;
		return ret;
	}

	public static Classifier Multiple(Classifier... classifiers) {
		Classifier ret = new Classifier();
		for (Classifier c : classifiers)
			ret.addClassifier(c);
		return ret;
	}

	public static Classifier StringMatcher(String string) {
		Classifier ret = new Classifier();
		ret.stringMatch = string;
		return ret;
	}

	public static Classifier Universal() {
		Classifier ret = new Classifier();
		ret.catchAll = true;
		return ret;
	}

	public static Classifier NullMatcher() {
		Classifier ret = new Classifier();
		ret.nullMatch = true;
		return ret;
	}

	public void negate() {
		negated = true;
	}

	/**
	 * Create a classifier that will match any of a set of literals. Admitted
	 * literals are numbers, strings, concepts, null and nested lists, which are all
	 * in OR.
	 * 
	 * @param set
	 * @return multiple classifier
	 */
	public static IClassifier Multiple(List<?> set) {

		Classifier ret = new Classifier();
		for (Object o : set) {
			if (o instanceof Number) {
				ret.addClassifier(NumberMatcher((Number) o));
			} else if (o instanceof String) {
				ret.addClassifier(StringMatcher((String) o));
			} else if (o instanceof IConcept) {
				ret.addClassifier(ConceptMatcher((IConcept) o));
			} else if (o == null) {
				ret.addClassifier(NullMatcher());
			} else if (o instanceof List) {
				ret.addClassifier((Classifier) Multiple((List<?>) o));
			}
		}
		return ret;
	}

	public String dumpCode() {
		/*
		 * TODO provisional
		 */
		return toString();
	}

	@Override
	public Object asValue() {

		if (numberMatch != null) {
			return numberMatch;
		} else if (booleanMatch != null) {
			return booleanMatch;
		} else if (intervalMatch != null) {

			return intervalMatch.getLowerBound()
					+ (new Random().nextDouble() * (intervalMatch.getUpperBound() - intervalMatch.getLowerBound()));

		} else if (conceptMatch != null) {

			return conceptMatch;

		} else if (stringMatch != null) {

			return stringMatch;

		} else if (expressionMatch != null) {

			return expressionMatch;
		}
		return null;
	}

	public static Classifier create(Object o) {
		return new Classifier(o);
	}

	@Override
	public String getSourceCode() {
		return sourceCode;
	}
}
