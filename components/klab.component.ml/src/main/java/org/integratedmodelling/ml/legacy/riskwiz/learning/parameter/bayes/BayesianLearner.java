/**
 * BayesianLearner.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: May 16, 2008
 *
 * ----------------------------------------------------------------------------------
 * This file is part of RiskWiz.
 * 
 * RiskWiz is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * RiskWiz is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with the software; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * ----------------------------------------------------------------------------------
 * 
 * @copyright 2008 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      May 16, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.learning.parameter.bayes;


import java.io.IOException;
import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.learning.IParameterLearner;
import org.integratedmodelling.ml.legacy.riskwiz.learning.bndata.IGraphData;
import org.integratedmodelling.ml.legacy.riskwiz.learning.bndata.IGraphDataSource;
import org.integratedmodelling.ml.legacy.riskwiz.learning.bndata.IGraphTable;
import org.integratedmodelling.ml.legacy.riskwiz.learning.dtable.Dirichlet;
import org.integratedmodelling.ml.legacy.riskwiz.learning.dtable.DistTable;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularCPD;
import org.nfunk.jep.ParseException;


/**
 * @author Sergey Krivov
 * 
 */
public class BayesianLearner implements IParameterLearner {
    BeliefNetwork bnet;

    /**
     * 
     */
    public BayesianLearner() {// TODO Auto-generated constructor stub
    }
	
    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.riskwiz.learning.IParameterLearner#initialize(org.integratedmodelling.riskwiz.bn.BeliefNetwork)
     */
    @Override
	public void initialize(BeliefNetwork bn) {
        bnet = bn;
        initializeUniformDistributions();

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.riskwiz.learning.IParameterLearner#getBeliefNetwork()
     */
    @Override
	public BeliefNetwork getFinalResult() {
        Set<BNNode> nodes = bnet.vertexSet();

        for (BNNode node : nodes) {
            if (node.isProbabilistic()) {
                node.clearProperty("distribution");
            }
        }
        return bnet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.integratedmodelling.riskwiz.learning.IParameterLearner#learnFromTable(org.integratedmodelling.riskwiz.learning.bndata.IGraphTable)
     */
    @Override
	public void learnFromTable(IGraphTable gtable) {
        gtable.connect(bnet);
        Set<BNNode> nodes = bnet.vertexSet();

        for (BNNode node : nodes) {
            if (node.isProbabilistic() && (!node.isExpression())
                    && gtable.hasCompleteProjection(node)) {
                learnNodeParameters(node, gtable, gtable.getValues());
            }
        }

    }
	
    @Override
	public void learnFromDataSource(IGraphDataSource datasource) throws IOException {
        datasource.connect(bnet);
        while (datasource.hasNextValues()) {
            Vector<Vector<String>> table = datasource.getNextValues();
            Set<BNNode> nodes = bnet.vertexSet();

            for (BNNode node : nodes) {
                if (node.isProbabilistic() && (!node.isExpression())
                        && datasource.hasCompleteProjection(node)) {
                    learnNodeParameters(node, datasource, table);
                }
            }
			
            datasource.readNextValues();
        }
		
        datasource.close();
		
    }

    protected void learnNodeParameters(BNNode node, IGraphData gtable, Vector<Vector<String>> tuples) {
        DistTable dtable = (DistTable) node.getProperty("distribution");
		 
        for (Vector<String> tuple : tuples) {
            int[] cptquery = gtable.getQuery(node, tuple);

            if (isCompleteQuery(cptquery)) {
                Dirichlet dist = dtable.getValue1(cptquery);

                dist.increment(cptquery[0]);
            }
        }

        node.setFunction(dtable.createCPF());
    }

    public boolean isCompleteQuery(int[] cptquery) {

        for (int i = 0; i < cptquery.length; i++) {
            if (cptquery[i] == -1) {
                return false;
            }
        }
        return true;

    }

    public void initializeWithPriors(BeliefNetwork bn, int virtualSamples) throws ParseException {
        bnet = bn;
        initializeDistributions(virtualSamples);

    }

    private void initializeUniformDistributions() {
        Set<BNNode> nodes = bnet.vertexSet();

        for (BNNode node : nodes) {
            if (node.getFunction() instanceof TabularCPD) {
                TabularCPD function = (TabularCPD) node.getFunction();
					
                DistTable dtable = new DistTable(
                        (DiscreteDomain) function.getDomain(), 
                        function.getParentsDomains());

                dtable.setUniformDistributions();
                node.setProperty("distribution", dtable);
                node.setFunction(dtable.createCPF());
            }
        }
    }

    private void initializeDistributions(int virtualSamples) throws ParseException {
        Set<BNNode> nodes = bnet.vertexSet();

        for (BNNode node : nodes) {
            if (node.getFunction() instanceof TabularCPD) {
                TabularCPD function = (TabularCPD) node.getFunction();
                DistTable dtable = new DistTable(function, virtualSamples);

                node.setProperty("distribution", dtable);
                node.setFunction(dtable.createCPF());
            }
        }
    }

}
