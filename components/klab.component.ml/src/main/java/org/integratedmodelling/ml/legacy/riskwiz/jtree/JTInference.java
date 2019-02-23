/**
 * JTIference.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 19, 2008
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
 * @date      Feb 19, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.jtree;


import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.debugger.IJTCompilerDebugger;
import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;
import org.integratedmodelling.ml.legacy.riskwiz.inference.IInference;
import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.TableFactory;


/**
 * @author Sergey Krivov
 *
 */
public class JTInference<IJT extends IJoinTree > implements IInference {
	
    protected IJT jTree = null;
    protected BeliefNetwork bn = null;
    public boolean treeIsConsistent = false;
    public boolean retraction = true;
	
    protected BNNode updatedNode = null;

    /**
     * 
     */
    public JTInference() {}

    public String getAlgorithmName() {		
        return "faeture is not implemented";
    }

    public void initialize(BeliefNetwork BN, IJoinTreeCompiler comp) throws Exception {
        this.bn = BN;
        jTree = (IJT) comp.execute(BN);
        jTree.initializeStructiure();
    }
	
    public void initialize(BeliefNetwork BN, IJoinTreeCompiler comp, IJTCompilerDebugger deb) throws Exception {
        this.bn = BN;
        jTree = (IJT) comp.execute(BN, deb);
        jTree.initializeStructiure();
    }
	
    public void initialize(IJT jt) {
        this.bn = jt.getBeliefNetwork();
        jTree = jt;
        jTree.initializeStructiure();
    }

    @Override
	public void run() {
        if (treeIsConsistent) {
            return;
        }
		
        if (retraction || updatedNode == null) {
            globalRetraction();
        } else {
            globalUpdate(updatedNode);
        }
		
    }
	
    public void globalRetraction() {
        jTree.initialize();
        jTree.initializeLikelihoods();
        jTree.propagateEvidence();
        jTree.setNodeConditionalMarginals();
        treeIsConsistent = true;
    }
	
    public void globalUpdate(BNNode node) {
        jTree.propagateEvidence(node);
        jTree.setNodeConditionalMarginals();
        treeIsConsistent = true;
    }

    @Override
	public PT getEvidence(String nodeName) {
        BNNode node = bn.getBeliefNode(nodeName);
		 
        return node.getEvidence();
		  
    }
	
    public PT getBelief(String nodeName) {
        BNNode node = bn.getBeliefNode(nodeName);

        return getBelief(node);
		  
    }
	
    public PT getBelief(BNNode node) {
		 
        if (node.hasEvidence()) {
            return node.getEvidence();
        }  
        return node.getMarginal();
		  
    }

    @Override
	public PT getMarginal(String nodeName) {
        BNNode node = bn.getBeliefNode(nodeName);

        return node.getMarginal();
    }
	
    @Override
	public PT getMarginal(BNNode node) {		 
        return node.getMarginal();
    }
	
    @Override
	public void setEvidence(String nodeName, PT mpt) {
        BNNode node = bn.getBeliefNode(nodeName);

        if (node != null) {
            setEvidence(node, mpt);
        }
    }
	
    @Override
	public void setEvidence(BNNode node, PT mpt) {
		 
        if (node.hasEvidence() || updatedNode != null) {
            retraction = true;
            updatedNode = null;
        } else {
            updatedNode = node;
        }
        node.setEvidence(mpt);		
        treeIsConsistent = false;
    }
	
    // observation is a kind of evidence

    @Override
	public void setObservation(String nodeName, int valueIndex) {
        BNNode node = bn.getBeliefNode(nodeName);

        if (node != null) {
            setObservation(node, valueIndex);
        }
		
    }
	
    @Override
	public void setObservation(String nodeName, double value) {
        BNNode node = bn.getBeliefNode(nodeName);

        if (node != null) {
            setObservation(node, value);
        }
		
    }
	
    @Override
	public void setObservation(BNNode node, int valueIndex) {		 
        setEvidence(node,
                TableFactory.createObservation(node.getDiscretizedDomain(),
                valueIndex));		
    }
	
    @Override
	public void setObservation(BNNode node, double  value) {
        if (node.getDomType() == BNNode.DomainType.continuous) {
            int valueIndex = ((IntervalDomain) node.getDiscretizedDomain()).getStateIndex(
                    value);

            if (valueIndex > -1) {
                setEvidence(node,
                        TableFactory.createObservation(
                        node.getDiscretizedDomain(), valueIndex));
            } else {
                ; // throw exception
            }
        } else {
            ; // throw exception
        }
    }

    @Override
	public void setObservation(String nodeName, String value) {
        BNNode node = bn.getBeliefNode(nodeName);

        if (node != null) {
            setObservation(node, value);
        }
    }
	
    @Override
	public void setObservation(BNNode node, String value) {		 
        setEvidence(node,
                TableFactory.createObservation(node.getDiscretizedDomain(),
                value));		
    }
	
    @Override
	public void retractEvidence(String nodeName) {
        BNNode node = bn.getBeliefNode(nodeName);

        if (node != null) {
            retractEvidence(node);
        }
    }
	
    @Override
	public void retractEvidence(BNNode node) {
		 
        node.setEvidence(null);
        retraction = true;
        treeIsConsistent = false;
    }

    public IJT getJoinTree() {
        return jTree;
    }

}
