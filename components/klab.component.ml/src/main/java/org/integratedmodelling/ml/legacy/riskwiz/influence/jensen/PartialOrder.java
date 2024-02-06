/**
 * PartialOrder.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Apr 23, 2008
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
 * @date      Apr 23, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.influence.jensen;


import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNEdge;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.graph.algorithm.Algorithm;
import org.jgrapht.Graph;
import org.jgrapht.traverse.TopologicalOrderIterator;


/**
 * @author Sergey Krivov
 *
 */
public class PartialOrder extends Algorithm<BNNode, BNEdge> {
    private Vector<Set<BNNode>> oredredSets;
    private Vector< BNNode> oredredDecisionNodes;

    /**
     * 
     */
    public PartialOrder() {
        super(BNEdge.class);
		 
    }
	
    public void execute(BeliefNetwork bn) {
        oredredSets = new Vector<Set<BNNode>>();
        oredredDecisionNodes = new Vector< BNNode>();
		 
        for (TopologicalOrderIterator it = new TopologicalOrderIterator((Graph) bn); it.hasNext();) {
            BNNode node = (BNNode) it.next();

            if (node.isDecision()) {
                Set<BNNode> parents = bn.getParents(node);

                oredredDecisionNodes.add(node);
                oredredSets.add(parents);				 
            }
        }
		 
        oredredSets.add(getLastNodeSet(bn, oredredSets));
		 
    }

    public Vector<BNNode> getOredredDecisionNodes() {
        return oredredDecisionNodes;
    }

    public Vector<Set<BNNode>> getOredredSets() {
        return oredredSets;
    }
	
    private Set<BNNode> getLastNodeSet(BeliefNetwork bn, Vector<Set<BNNode>> odSets) {
        Set<BNNode> allNodes = bn.vertexSet();
        Set<BNNode> lastSet = new HashSet<BNNode>();

        for (BNNode node : allNodes) {
            if (!node.isDecision() && !node.isUtility() && !isIn(node, odSets)) {
                lastSet.add(node);
            }
        }
		
        return lastSet;
    }
	
    private boolean isIn(BNNode node, Vector<Set<BNNode>> odSets) {
        for (Set<BNNode> set : odSets) {
            for (BNNode node2 : set) {
                if (node2 == node) {
                    return true;
                }
            }
        }
		
        return false;
    }
}
