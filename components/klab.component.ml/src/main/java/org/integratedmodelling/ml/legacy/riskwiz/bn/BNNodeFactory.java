/**
 * BeliefNodeFactory.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 26, 2008
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
 * @date      Feb 26, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.bn;


import java.util.function.Supplier;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;


/**
 * @author Sergey Krivov
 *
 */
public class BNNodeFactory implements  Supplier<BNNode> {

    /**
     * 
     */
    int nodeCount = 0;
    public BNNodeFactory() {
        nodeCount = 0;
    }

//    @Override
	public BNNode createVertex() {
        nodeCount++;
        return new BNNode("Node" + nodeCount);
    }
	
    public BNNode createVertex(String name, BNNode.NodeType nodeType) {
        nodeCount++;
        return new BNNode(name, nodeType);
    }
	
    public BNNode createVertex(DiscreteDomain dom, BNNode.NodeType nodeType) {
        nodeCount++;
        return new BNNode(dom, nodeType);
    }
	
    public BNNode createVertex(String name, int order, BNNode.NodeType nodeType) {
        nodeCount++;
        return new BNNode(name, order, nodeType);
    }
	
    public BNNode createVertex(String name, String[] labels, BNNode.NodeType nodeType) {
        nodeCount++;
        return new BNNode(name, labels, nodeType);
    }
	
    public BNNode createVertex(String name, double from, double to, int numberOfIntervals, BNNode.NodeType nodeType) {
        nodeCount++;
        return new BNNode(name, from, to, numberOfIntervals, nodeType);
    }

	@Override
	public BNNode get() {
		return createVertex();
	}

}
