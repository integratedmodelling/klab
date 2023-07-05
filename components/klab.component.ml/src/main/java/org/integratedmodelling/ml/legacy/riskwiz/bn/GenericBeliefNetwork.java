/**
 * GenericBeliefNetwork.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Jul 29, 2009
 *
 * ----------------------------------------------------------------------------------
 * This file is part of riskwiz-cvars.
 * 
 * riskwiz-cvars is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * riskwiz-cvars is distributed in the hope that it will be useful,
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
 * @copyright 2009 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      Jul 29, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.bn;


import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.LabelDomain;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskDirectedGraph;


/**
 * @author Sergey Krivov
 *
 */
public class GenericBeliefNetwork<E> extends RiskDirectedGraph<BNNode, E> {
	
    /**
     * 
     */
    private static final long serialVersionUID = -4506204604462409205L;

    protected String name;

    protected String comment;

    protected BNNodeFactory bNNodeFactory;

    /**
     * @param ef
     * @param allowMultipleEdges
     * @param allowLoops
     */
    public GenericBeliefNetwork(Class<? extends E> ef,
            boolean allowMultipleEdges, boolean allowLoops) {
        super(ef, allowMultipleEdges, allowLoops);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param edgeClass
     */
    public GenericBeliefNetwork(Class<? extends E> edgeClass) {
        super(edgeClass);
        // TODO Auto-generated constructor stub
    }

//    /**
//     * @param ef
//     */
//    public GenericBeliefNetwork(EdgeFactory<BNNode, E> ef) {
//        super(ef);
//        // TODO Auto-generated constructor stub
//    }
	
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BNNode getBeliefNode(String nodeName) {
        BNNode node = null;
        Set<BNNode> nodes = super.vertexSet();

        for (BNNode nodeIt : nodes) {
            if (nodeIt.getName().equalsIgnoreCase(nodeName)) {
                node = nodeIt;
            }
        }
        return node;
    }

    public String[] getNodeNames(Set<BNNode> nodeSet) {
        BNNode[] nodes = nodeSet.toArray(new BNNode[0]);
        String[] names = new String[nodes.length];

        for (int i = 0; i < nodes.length; i++) {
            names[i] = nodes[i].getName();
        }
        return names;
    }

    public String[] getNodeNames() {
        return getNodeNames(super.vertexSet());
    }

    public int getNodeNumber() {
        return super.vertexSet().size();
    }

    public String getNodeComment(String nodeName) {
        return getBeliefNode(nodeName).getComment();
    }

    public void setNodeComment(String nodeName, String comment) {
        getBeliefNode(nodeName).setComment(comment);
    }

    public String[] getParents(String nodeName) {
        BNNode node = getBeliefNode(nodeName);

        return getNodeNames(getParents(node));

    }

    public String[] getChildren(String nodeName) {
        BNNode node = getBeliefNode(nodeName);

        return getNodeNames(getChildren(node));
    }

    public boolean hasEdgeBetween(String sourceNodeName, String targetNodeName) {
        return hasEdgeBetween(getBeliefNode(sourceNodeName),
                getBeliefNode(targetNodeName));
    }

    public void addBeliefNode(BNNode node) {
        super.addVertex(node);
    }

    public void addBeliefNode() {
        BNNode node = bNNodeFactory.createVertex();

        super.addVertex(node);
    }
	
    public void addBeliefNode(DiscreteDomain dom, BNNode.NodeType nodeType) {
        BNNode node = bNNodeFactory.createVertex(dom, nodeType);

        super.addVertex(node);
    }

    public final void addProbabilisticLabelNode(String nodeName,
            String[] stateNames) {
        addLabelNode(nodeName, stateNames, BNNode.NodeType.probabilistic);
    }

    public final void addProbabilisticDiscreteNode(String nodeName, int order) {
        addDiscreteNode(nodeName, order, BNNode.NodeType.probabilistic);
    }

    public final void addProbabilisticIntervalNode(String nodeName,
            double from, double to, int numberOfIntervals) {
        addIntervalNode(nodeName, from, to, numberOfIntervals,
                BNNode.NodeType.probabilistic);
    }

    /** ************Decision Graph API************** */

    public final BNNode  addLabelNode(String nodeName, String[] stateNames,
            BNNode.NodeType nodetType) {
        BNNode node = bNNodeFactory.createVertex(nodeName, stateNames, nodetType);

        super.addVertex(node);
        return node;
    }

    public final BNNode addDiscreteNode(String nodeName, int order,
            BNNode.NodeType nodetType) {
        BNNode node = bNNodeFactory.createVertex(nodeName, order, nodetType);

        super.addVertex(node);
        return node;
    }

    public final BNNode  addIntervalNode(String nodeName, double from, double to,
            int numberOfIntervals, BNNode.NodeType nodetType) {
        BNNode node = bNNodeFactory.createVertex(nodeName, from, to,
                numberOfIntervals, nodetType);

        super.addVertex(node);
        return node;
    }

    public BNNode addUtilityNode(java.lang.String nodeName) {
        BNNode node = bNNodeFactory.createVertex(nodeName,
                BNNode.NodeType.utility);

        super.addVertex(node);
        return node;
    }

    public final BNNode addDecisionLabelNode(String nodeName,
            String[] decisionNames) {
        return addLabelNode(nodeName, decisionNames, BNNode.NodeType.decision);
		
    }

    public final BNNode addDecisionIntervalNode(String name, double from,
            double to, int numberOfIntervals) {
        return addIntervalNode(name, from, to, numberOfIntervals,
                BNNode.NodeType.decision);
    }

    /** ************End of Decision Graph API************** */
    @Override
	public E addEdge(BNNode sourceNode, BNNode targetNode) {
        if (sourceNode.getNodeType() == BNNode.NodeType.utility) {
            throw new UnsupportedOperationException(
                    "Utility node can't have children");
        }
        E edge = super.addEdge(sourceNode, targetNode);

        targetNode.addParentNode(sourceNode);

        return edge;
    }

    public E addEdge(String sourceNodeName, String targetNodeName) {
        BNNode sourceNode = getBeliefNode(sourceNodeName);
        BNNode targetNode = getBeliefNode(targetNodeName);

        return this.addEdge(sourceNode, targetNode);
    }

    @Override
	public E removeEdge(BNNode sourceNode, BNNode targetNode) {
        if (targetNode.getNodeType() != BNNode.NodeType.decision) {
            targetNode.removeParentNode(sourceNode);
        }		
        return super.removeEdge(sourceNode, targetNode);
    }

    public E removeEdge(String sourceNodeName, String targetNodeName) {
        BNNode sourceNode = getBeliefNode(sourceNodeName);
        BNNode targetNode = getBeliefNode(targetNodeName);

        return this.removeEdge(sourceNode, targetNode);
    }

    public void removeNode(BNNode node) {
        Set<BNNode> children = super.getChildren(node);

        for (BNNode childNode : children) {
            if (childNode.getNodeType() != BNNode.NodeType.decision) {
                childNode.removeParentNode(node);
            }
        }
        super.removeVertex(node);
    }

    public void removeNode(String nodeName) {
        BNNode node = getBeliefNode(nodeName);

        this.removeNode(node);
    }

    // public CPT getConditionalProbabilityTable(String nodeName) {
    // BeliefNode node = getBeliefNode(nodeName);
    // return node.getTable();
    // }
    //
    // /*
    // * this is unsafe in this form, but let us see if checks of consystency are
    // * really needed
    // */
    // public void setConditionalProbabilityTable(String nodeName, CPT table) {
    // BeliefNode node = getBeliefNode(nodeName);
    // node.setTable(table);
    // }

	 

    public boolean isInteger(String nodeName) {
        BNNode node = getBeliefNode(nodeName);

        return isInteger(node);
    }

    public boolean isLabel(String nodeName) {
        BNNode node = getBeliefNode(nodeName);

        return isLabel(node);

    }

    public boolean isInterval(String nodeName) {
        BNNode node = getBeliefNode(nodeName);

        return isInterval(node);
    }

    public boolean isInteger(BNNode node) {
        if (!(node.getDomain() instanceof LabelDomain)
                && !(node.getDomain() instanceof IntervalDomain)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLabel(BNNode node) {
        if (node.getDomain() instanceof LabelDomain) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isInterval(BNNode node) {
        if (node.getDomain() instanceof IntervalDomain) {
            return true;
        } else {
            return false;
        }
    }

    // public int getValueNumber(String nodeName) {
    // BeliefNode node = getBeliefNode(nodeName);
    // return node.getDomain().getOrder();
    // }
    //
    // public String[] getStringValues(String nodeName) {
    // BeliefNode node = getBeliefNode(nodeName);
    // return (String[]) node.getDomain().getStates().toArray();
    // }

    public int[] getIntValues(BNNode node) {
        return null;
    }

    public float[][] getIntervals(BNNode node) {
        return null;
    }

    public String getFormula(String nodeName) {
        return null;
    }

    public boolean isFormulaProbabilistic(String nodeName) {
        return false;
    }

    public int getFormulaSampleNumber(java.lang.String nodeName) {
        return 0;
    }

    public final boolean isDecision(String nodeName) {
        return getBeliefNode(nodeName).isDecision();
    }

    public final boolean isUtility(String nodeName) {
        return getBeliefNode(nodeName).isUtility();
    }

}
