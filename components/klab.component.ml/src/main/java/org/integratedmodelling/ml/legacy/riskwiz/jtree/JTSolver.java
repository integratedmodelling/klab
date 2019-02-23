/**
 * JTSolver.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: May 9, 2008
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
 * @date      May 9, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.jtree;


import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.influence.ISolver;
import org.integratedmodelling.ml.legacy.riskwiz.pt.CPT;


/**
 * @author Sergey Krivov
 *
 */
public class JTSolver<IJT extends  IJoinTreeDecision> implements ISolver {
	
    protected IJT jTree = null;
    protected BeliefNetwork bn = null;
    public boolean treeIsConsistent = false;

    /**
     * 
     */
    public JTSolver() {// TODO Auto-generated constructor stub
    }
	
    @Override
	public String getAlgorithmName() {
        // TODO Auto-generated method stub
        return "faeture is not implemented";
    }
	
    public void initialize(BeliefNetwork BN, IJoinTreeDecisionCompiler comp) throws Exception {
        this.bn = BN;
        jTree = (IJT) comp.execute(BN);
 
        jTree.initializeStructiure();
    }

    @Override
	public void Solve() {
        jTree.initialize();
        jTree.initializeLikelihoods();
        jTree.propagateEvidence();
		
    }

    @Override
	public CPT getPolicy(DiscreteDomain node) {
        // TODO Auto-generated method stub
        return jTree.getPolicy(node);
    }
	
}
