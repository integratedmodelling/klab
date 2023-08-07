/**
 * DBNModel.java
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

package org.integratedmodelling.ml.legacy.riskwiz.dbn;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.GenericBeliefNetwork;


/**
 * @author Sergey Krivov
 * 
 */
public class DBNModel extends GenericBeliefNetwork<DBNEdge> {
	
    private static final long serialVersionUID = 2985991876480120930L;

	int numberOfSlices;
	
    private Vector<BNNode> temporalNodes;

    /**
     * @param edgeClass
     */
    public DBNModel(Class<? extends DBNEdge> edgeClass) {
        super(edgeClass);
        temporalNodes = new Vector<BNNode>();
    }

    /**
     * @param ef
     * @param allowMultipleEdges
     * @param allowLoops
     */
    public DBNModel(boolean allowMultipleEdges, boolean allowLoops) {
        super(DBNEdge.class, allowMultipleEdges, allowLoops);
        temporalNodes = new Vector<BNNode>();
    }

    /**
     * @param ef
     */
    public DBNModel() {
        super(DBNEdge.class);
        temporalNodes = new Vector<BNNode>();
    }
	
    public int getNumberOfSlices() {
        return numberOfSlices;
    }

    public void setNumberOfSlices(int numberOfSlices) {
        this.numberOfSlices = numberOfSlices;
    }
	
    public DBNGroundNetwork unflod() {
        return null;
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
	public DBNEdge addEdge(BNNode sourceNode, BNNode targetNode) {
        if (sourceNode.getNodeType() == BNNode.NodeType.utility) {
            throw new UnsupportedOperationException(
                    "Utility node can't have children");
        }
        DBNEdge edge = super.addEdge(sourceNode, targetNode);

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

}
