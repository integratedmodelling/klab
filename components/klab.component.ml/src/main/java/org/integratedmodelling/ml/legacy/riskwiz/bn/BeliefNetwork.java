/**
 * BeliefNetwork.java
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

/**
 * @author Sergey Krivov
 * 
 */
public class BeliefNetwork extends GenericBeliefNetwork<BNEdge> {

    private static final long serialVersionUID = -4789285633181382847L;

	/**
     * @param ef
     * @param allowMultipleEdges
     * @param allowLoops
     */
    public BeliefNetwork(boolean allowMultipleEdges, boolean allowLoops) {
        super(BNEdge.class, allowMultipleEdges, allowLoops);
        this.name = "";
    }

    /**
     * @param edgeClass
     */
    public BeliefNetwork() {
        this(false, false);
        this.name = "";
    }

//    /**
//     * @param ef
//     */
//    public BeliefNetwork(EdgeFactory<BNNode, BNEdge> ef) {
//        super(ef);
//        // TODO Auto-generated constructor stub
//    }
	
    public BeliefNetwork(String name) {
    	this(false, false);
        this.name = name;
        bNNodeFactory = new BNNodeFactory();
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
	public BNEdge addEdge(BNNode sourceNode, BNNode targetNode) {
        if (sourceNode.getNodeType() == BNNode.NodeType.utility) {
            throw new UnsupportedOperationException(
                    "Utility node can't have children");
        }
        BNEdge edge = super.addEdge(sourceNode, targetNode);

        if (targetNode.getNodeType() == BNNode.NodeType.decision) {
            edge.setInformationEdge(true);
        }  

        return edge;
    }

}
