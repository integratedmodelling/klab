/**
 * SDBNModel.java
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

package org.integratedmodelling.ml.legacy.riskwiz.dbn.sdbn;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.GenericBeliefNetwork;


/**
 * @author Sergey Krivov
 * DBN with spatial-temporal nodes and edges. Every SDBNEdge drawn between SaptialTemporal nodes
 * automaticaly spans to all neighbors.
 * TODO: Need to understand how to specify probability functions for such nodes
 */


public class SDBNModel extends GenericBeliefNetwork<SDBNEdge> {
	
    int numberOfSlices;
	
    int dimX;
    int dimY;
	
    private Vector<BNNode> temporalNodes;
	
    private Vector<BNNode> spatialTemporalNodes;

    /**
     * @param ef
     * @param allowMultipleEdges
     * @param allowLoops
     */
    public SDBNModel(boolean allowMultipleEdges, boolean allowLoops) {
        super(SDBNEdge.class, allowMultipleEdges, allowLoops);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param edgeClass
     */
    public SDBNModel(Class<? extends SDBNEdge> edgeClass) {
        super(edgeClass);
        // TODO Auto-generated constructor stub
    }

//    /**
//     * @param ef
//     */
//    public SDBNModel(EdgeFactory<BNNode, SDBNEdge> ef) {
//        super(ef);
//        // TODO Auto-generated constructor stub
//    }
	
    public int getNumberOfSlices() {
        return numberOfSlices;
    }

    public void setNumberOfSlices(int numberOfSlices) {
        this.numberOfSlices = numberOfSlices;
    }

    public int getDimX() {
        return dimX;
    }

    public void setDimX(int dimX) {
        this.dimX = dimX;
    }

    public int getDimY() {
        return dimY;
    }

    public void setDimY(int dimY) {
        this.dimY = dimY;
    }

    public String getEdgeComment(String sourceNodeName, String targetNodeName) {
        BNNode sourceNode = getBeliefNode(sourceNodeName);
        BNNode targetNode = getBeliefNode(targetNodeName);

        return super.getEdge(sourceNode, targetNode).getComment();
    }

    public void setEdgeComment(String sourceNodeName, String targetNodeName,
            String comment) {
        BNNode sourceNode = getBeliefNode(sourceNodeName);
        BNNode targetNode = getBeliefNode(targetNodeName);

        super.getEdge(sourceNode, targetNode).setComment(comment);
    }

    @Override
	public SDBNEdge addEdge(BNNode sourceNode, BNNode targetNode) {
        if (sourceNode.getNodeType() == BNNode.NodeType.utility) {
            throw new UnsupportedOperationException(
                    "Utility node can't have children");
        }
        SDBNEdge  edge = super.addEdge(sourceNode, targetNode);

        if (targetNode.getNodeType() == BNNode.NodeType.decision) {
            edge.setInformationEdge(true);
        } else {
            targetNode.addParentNode(sourceNode);
        }

        return edge;
    }
	
    public final BNNode addTemporalLabelNode(String nodeName, String[] stateNames,
            BNNode.NodeType nodetType) {
        BNNode node = super.addLabelNode(nodeName, stateNames, nodetType);

        temporalNodes.add(node);
        return node;
    }

    public final BNNode addTemporalDiscreteNode(String nodeName, int order,
            BNNode.NodeType nodetType) {
        BNNode node = super.addDiscreteNode(nodeName, order, nodetType);

        temporalNodes.add(node);
        return node;
    }

    public final BNNode addTemporalIntervalNode(String nodeName, double from, double to,
            int numberOfIntervals, BNNode.NodeType nodetType) {
        BNNode node = super.addIntervalNode(nodeName, from, to,
                numberOfIntervals, nodetType); 

        temporalNodes.add(node);
        return node;
    }
	
    public final BNNode addSpatialTemporalLabelNode(String nodeName, String[] stateNames,
            BNNode.NodeType nodetType) {
        BNNode node = super.addLabelNode(nodeName, stateNames, nodetType);

        spatialTemporalNodes.add(node);
        return node;
    }

    public final BNNode addSpatialTemporalDiscreteNode(String nodeName, int order,
            BNNode.NodeType nodetType) {
        BNNode node = super.addDiscreteNode(nodeName, order, nodetType);

        spatialTemporalNodes.add(node);
        return node;
    }

    public final BNNode addSpatialTemporalIntervalNode(String nodeName, double from, double to,
            int numberOfIntervals, BNNode.NodeType nodetType) {
        BNNode node = super.addIntervalNode(nodeName, from, to,
                numberOfIntervals, nodetType); 

        spatialTemporalNodes.add(node);
        return node;
    }

}
