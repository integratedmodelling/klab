package org.integratedmodelling.landcover.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.code.LocatedExpression;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.IntelligentMap;
import org.integratedmodelling.klab.owl.ReasonerCache;

public class LandcoverTransitionTable {

	boolean transitive = true;
	boolean canTransitionWhenUnspecified = false;

	ReasonerCache rcache = new ReasonerCache();

	class TransitionRule {

		IConcept target;
		boolean always = false;
		boolean never = false;
		Long minimumAge;
		ITimeInstant after;
		LocatedExpression selector;

		// rule gets deactivated after the demand for its target is met.
		boolean active = true;

		public TransitionRule() {
		}

		public TransitionRule(IConcept target, IKimClassifier rule, IRuntimeScope overallScope) {
			this.target = target;

			if (rule.getBooleanMatch() != null) {
				if (rule.getBooleanMatch()) {
					this.always = true;
				} else {
					this.never = true;
				}
			} else if (rule.getExpressionMatch() != null) {
				this.selector = new LocatedExpression(rule.getExpressionMatch(), overallScope, true);
			}
		}

		public boolean isPossible(ILocator locator, IRuntimeScope scope) {

			if (always) {
				return true;
			}
			if (never) {
				return false;
			}
			if (minimumAge != null) {

			}
			if (after != null) {
				ITime time = Observations.INSTANCE.getTime(locator);
				return time.getEnd().getMilliseconds() > after.getMilliseconds();
			}
			if (selector != null) {
				return selector.eval(scope, locator, Boolean.class);
			}
			return false;
		}
	}

	IntelligentMap<Map<IConcept, TransitionRule>> transitions = new IntelligentMap<>();
	public TransitionRule defaultTransitionRule;
	private Set<IConcept> concepts = new HashSet<>();

	/**
	 * Map the source concept to its admitted transitions to the target concepts,
	 * using specificity-driven transitive search for all. This way more general
	 * rules will match a transition suggested by the suitability model unless a
	 * more specific rule matches. The map may lose specificity with time through
	 * this mechanism, which is an OK consequence of the uncertainty introduced in
	 * the model.
	 * 
	 * If this behavior isn't wanted, pass false to limit the transitions to the
	 * actual classes and use the default for their children.
	 */
	public LandcoverTransitionTable(boolean transitiveTarget, boolean transitionsByDefault) {
		this.transitive = transitiveTarget;
		this.defaultTransitionRule = new TransitionRule();
		if (transitionsByDefault) {
			this.defaultTransitionRule.always = true;
		} else {
			this.defaultTransitionRule.never = true;
		}
	}

	public boolean canTransition(IConcept from, IConcept to, ILocator locator, IRuntimeScope scope) {
		TransitionRule transition = defaultTransitionRule;
		Map<IConcept, TransitionRule> possible = transitions.get(from);
		if (possible != null) {
			TransitionRule t = possible.get(to);
			if (t != null) {
				transition = t;
			}
		}
		return transition.isPossible(locator, scope);
	}

	public void parse(IKimTable table, IRuntimeScope overallScope) {

		if (table.getRowCount() < 2) {
			throw new KlabValidationException("transition table must have at least two rows");
		}

		List<IConcept> targets = new ArrayList<>();
		for (int i = 1; i < table.getColumnCount(); i++) {
			IKimClassifier cct = table.getRow(0)[i];
			if (cct.getConceptMatch() == null) {
				throw new KlabValidationException(
						"first row of transition table must contain target concepts in positions 1..n");
			}
			IConcept ct = Concepts.INSTANCE.declare(cct.getConceptMatch());
			if (ct == null) {
				throw new KlabValidationException("unknown concept in transition table, column " + (i + 1));
			}
			concepts.add(ct);
			targets.add(ct);
		}

		// if transitive, make IntelligentMaps from the rows, otherwise make
		// LinkedHashMaps.
		for (int i = 1; i < table.getRowCount(); i++) {

			IKimClassifier cct = table.getRow(i)[0];
			if (cct.getConceptMatch() == null) {
				throw new KlabValidationException(
						"first column in transition table must contain source concept in row > 0");
			}
			IConcept source = Concepts.INSTANCE.declare(cct.getConceptMatch());
			if (source == null) {
				throw new KlabValidationException("unknown concept in transition table, row" + (i + 1));
			}
			concepts.add(source);
			Map<IConcept, TransitionRule> map = new LinkedHashMap<IConcept, TransitionRule>();

			for (int c = 1; c < table.getColumnCount(); c++) {
				IKimClassifier rule = table.getRow(i)[c];
				TransitionRule transition = new TransitionRule(targets.get(c - 1), rule, overallScope);
				map.put(targets.get(c - 1), transition);
			}

			transitions.put(source, map);
		}
	}

	/**
	 * Return all concepts mentioned in the transition table, so we know all the
	 * potential targets.
	 * 
	 * @return
	 */
	public Set<IConcept> getConcepts() {
		return concepts;
	}

	public boolean isActive() {

		if (defaultTransitionRule.always) {
			return true;
		}
		
		for (IConcept c : transitions.keySet()) {
			for (IConcept cc : transitions.get(c).keySet()) {
				if (transitions.get(c).get(cc).active) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Deactivate any rule whose target is the passed concept. Return whether there are
	 * any other active rules.
	 * 
	 * @param metDemand
	 * @return
	 */
	public boolean deactivate(IConcept metDemand) {
		boolean ret = false;
		for (IConcept c : transitions.keySet()) {
			for (IConcept cc : transitions.get(c).keySet()) {
				TransitionRule rule = transitions.get(c).get(cc);
				if (rcache.is(rule.target, metDemand)) {
					rule.active = false;
				} else if (rule.active) {
					ret = true;
				}
			}
		}
		return ret;
	}

	/**
	 * Activate all rules prior to demand reevaluation.
	 * 
	 * @return
	 */
	public void activate() {
		for (IConcept c : transitions.keySet()) {
			for (IConcept cc : transitions.get(c).keySet()) {
				TransitionRule rule = transitions.get(c).get(cc);
				rule.active = true;
			}
		}
	}

	public boolean isEmpty() {
		return transitions.isEmpty();
	}

	/**
	 * Return the possible transition rules between two types, arranged so that the
	 * most appropriate comes first. If we're not in transitive mode the return list
	 * will contain one transition (either the applicable or the default), otherwise
	 * one or more, with the default transition always last.
	 * 
	 * @param current
	 * @param candidate
	 * @return all applicable transitions.
	 */
	public List<TransitionRule> getTransitions(IConcept current, IConcept candidate) {
		List<TransitionRule> ret = new ArrayList<>();
		Map<IConcept, TransitionRule> potential = transitions.get(current);
		if (potential != null) {
			TransitionRule specific = potential.get(candidate);
			if (specific != null && specific.active) {
				ret.add(specific);
			}
			for (IConcept target : potential.keySet()) {
				if (!target.equals(candidate) && (this.transitive && rcache.is(candidate, target))) {
					TransitionRule rule = potential.get(target);
					if (rule.active) {
						ret.add(rule);
					}
				}
			}
		}

		ret.add(defaultTransitionRule);

		return ret;
	}

}
