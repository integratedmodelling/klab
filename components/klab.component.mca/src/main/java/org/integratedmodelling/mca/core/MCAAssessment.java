/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.mca.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.mca.api.ICriterion;

import com.google.common.primitives.Doubles;

/**
 * Driver class to run a whole MCA analysis from definition to results. It makes
 * using Evamix and PairwiseComparator very simple, but it is not required to
 * use it if you know your way around the analysis.
 *
 * Steps to use:
 * 
 * 1. declare your criteria and alternatives; 2. set alternative values for each
 * criterion; 3. set weights, either directly or using the pairwise method;
 * don't mix calls to these. 4. run analysis; 5. use inquiry methods to obtain
 * results.
 * 
 * Any operation in step 2 and 3 will freeze the declaration functions, which
 * will throw an exception if called after that. Operations 2 and 3 can be
 * called again after running the analysis, and will make incremental changes to
 * the existing situation so that different scenarios can be analyzed.
 * 
 * @author Ferdinando Villa
 *
 */
public class MCAAssessment {

	public static enum Method {
		EVAMIX, ELECTRE3, PROMETHEE
	}

	public class Alternative {

		String name;
		double[] values = null;
	}

	public class Criterion {

		String name;
		ICriterion.DataType dataType;
		ICriterion.Type type;
		double weight;
	}

	ArrayList<Criterion> criteria = new ArrayList<>();
	ArrayList<Alternative> alternatives = new ArrayList<>();
	AHP pairwise = null;

	// we collect weights here, unless the pairwise comparator is used.
	double weights[] = null;

	// if true, we have started setting values, so we cannot declare anything new.
	boolean frozen = false;

	// store the index in the main array of both alternatives and criteria for speed
	HashMap<String, Integer> altIndex = new HashMap<>();
	HashMap<String, Integer> critIndex = new HashMap<>();

	// results are kept here after runEvamix() is called.
	Results results = null;

	Method method = Method.EVAMIX;

	// use default method
	public MCAAssessment() {
	}

	public MCAAssessment(Method method) {
		// TODO method is unused
		this.method = method;
	}

	private int getAltIndex(String alternative) {
		return altIndex.get(alternative);
	}

	private int getCritIndex(String criterion) {
		return critIndex.get(criterion);
	}

	public void declareCriterion(String criterionName, ICriterion.DataType dataType, ICriterion.Type type) {

		if (frozen)
			throw new KlabValidationException("MCA: cannot add criteria when data input has begun");

		if (type == ICriterion.Type.COST && dataType != ICriterion.DataType.RATIO)
			throw new KlabValidationException("MCA: cost criteria can only be quantitative");

		Criterion c = new Criterion();
		c.name = criterionName;
		c.dataType = dataType;
		c.type = type;

		criteria.add(c);
		critIndex.put(criterionName, criteria.size() - 1);
	}

	public void declareAlternative(String alternativeName) {

		if (frozen)
			throw new KlabValidationException("MCA: cannot add alternatives when data input has begun");

		Alternative a = new Alternative();
		a.name = alternativeName;

		alternatives.add(a);
		altIndex.put(alternativeName, alternatives.size() - 1);
	}
	
	public void resetWeights() {
		this.weights = null;
	}

	public void invertWeights() {
		double max = Doubles.max(weights);
		for (int i = 0; i < weights.length; i++) {
			weights[i] = max - weights[i];
		}
	}
	
	public boolean setCriterionWeight(String criterionName, double criterionWeight) {

		if (!critIndex.containsKey(criterionName)) {
			return false;
		}

		frozen = true;

		if (pairwise != null) {
			throw new KlabValidationException("MCA: cannot mix pairwise weighting with direct weighting");
		}

		if (weights == null) {
			weights = new double[criteria.size()];
		}

		weights[getCritIndex(criterionName)] = criterionWeight;

		return true;
	}

	public void compareCriteria(String criterion1, String criterion2, double comparativeWeight) {

		frozen = true;

		if (weights != null) {
			throw new KlabValidationException("MCA: cannot mix pairwise weighting with direct weighting");
		}

		if (pairwise == null) {
			pairwise = new AHP(criteria.size());
		}

		pairwise.rankPair(getCritIndex(criterion1), getCritIndex(criterion2), comparativeWeight);
	}

	public void setCriterionValue(String alternativeName, String criterionName, double value) {

		Alternative alt = alternatives.get(getAltIndex(alternativeName));
		int crit = getCritIndex(criterionName);

		if (alt.values == null)
			alt.values = new double[criteria.size()];

		alt.values[crit] = value;
	}

	public Results run(IMonitor monitor) throws KlabException {
		Results ret = null;
		switch (method) {
		case EVAMIX:
			ret = runEvamix(monitor);
			break;
		default:
			break;

		}
		return ret;
	}

	public Results runEvamix(IMonitor monitor) throws KlabException {

		if (weights == null && pairwise != null) {
			weights = pairwise.getRankings();
		}
		if (weights /* still */ == null) {
			// just prevent NPEs
			weights = new double[] {};
		}

		double[][] data = new double[alternatives.size()][criteria.size()];
		boolean cost[] = new boolean[criteria.size()];
		ICriterion.DataType types[] = new ICriterion.DataType[criteria.size()];
		String cnames[] = new String[criteria.size()];

		int i = 0;
		for (Criterion c : criteria) {
			cost[i] = c.type == ICriterion.Type.COST;
			types[i] = c.dataType;
			cnames[i] = c.name;
			i++;
		}

		String anames[] = new String[alternatives.size()];

		i = 0;
		for (Alternative a : alternatives) {
			anames[i] = a.name;
			for (int j = 0; j < criteria.size(); j++) {
				data[i][j] = a.values[j];
			}
			i++;
		}

		results = Evamix.run(data, weights, types, cost, anames, cnames, monitor);

		if (!results.isEmpty() && monitor != null) {
//			monitor.getContext().getReport().write(results.dump());
		}

		return results;
	}

	public double getAlternativeRanking(String alternativeName) {

		if (results == null)
			throw new KlabValidationException("MCA: cannot report results before runEvamix() is called");

		double score = results.evamix_scores[getAltIndex(alternativeName)];

		/*
		 * TODO - what do we want to do here, just report the score or rank.
		 */
		return score;
	}

}
