/**
 * GraphDataAdapter.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: May 20, 2008
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
 * @date      May 20, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.learning.bndata;


import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.Domain;


/**
 * @author Sergey Krivov
 *
 */
public abstract class GraphDataAdapter implements IGraphData {
	
    private HashMap<BNNode, int[]> mapHash;

    /**
     * 
     */
    public GraphDataAdapter() {// TODO Auto-generated constructor stub
    }
	
    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.learning.bndata.IGraphData#getScheme()
     */
    @Override
	public abstract Vector<String> getScheme();
	
    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.learning.bndata.IGraphData#initialize(org.integratedmodelling.riskwiz.bn.BeliefNetwork)
     */
    @Override
	public void connect(BeliefNetwork bn) {
        mapHash = new HashMap<BNNode, int[]>();
        Set<BNNode> nodes = bn.vertexSet();

        for (BNNode node : nodes) {
            int[] schemeMap = createSchemeMap(node);

            if (schemeMap != null) {
                mapHash.put(node, schemeMap);
            }
        }

    }
	
    @Override
	public boolean hasCompleteProjection(BNNode node) {
        return (mapHash.get(node) != null);
    }

    private int[]  createSchemeMap(BNNode node) {
        Vector<? extends Domain> domainProduct = node.getDomainProduct();
        Vector<String> scheme = this.getScheme();
        int[] map = new int[domainProduct.size()];
		
        for (int i = 0; i < domainProduct.size(); i++) {
            Domain dom = domainProduct.get(i);
            int index = scheme.indexOf(dom.getName());

            if (index == -1) {
                return null;
            } else {
                map[i] = index;
            }
			
        } 
        return map;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.learning.bndata.IGraphData#getQuery(org.integratedmodelling.riskwiz.bn.BeliefNode, java.util.Vector)
     */
    @Override
	public int[] getQuery(BNNode node, Vector<String> tupleValues) {		
        String[] projectionToNodeSpace = getNodeSpaceProjection(node,
                tupleValues);

        return valuesToQuery(node, projectionToNodeSpace);
    }
	
    public String[]   getNodeSpaceProjection(BNNode node, Vector<String> tupleValues) {
        int[] schemeMap = mapHash.get(node);

        if (schemeMap == null) {
            return null;
        }
        String[] projectionToNodeSpace = new  String[schemeMap.length];

        for (int i = 0; i < schemeMap.length; i++) {
            projectionToNodeSpace[i] = tupleValues.elementAt(schemeMap[i]);
        }
        return projectionToNodeSpace;
    }
	
    public int[] valuesToQuery(BNNode node, String[]  projectionToNodeSpace) {
        if (projectionToNodeSpace == null) {
            return null;
        }
        int[] query = new int[projectionToNodeSpace.length];
		
        Vector<DiscreteDomain> domainProduct = node.getDiscreteCPT().getDomainProduct();

        for (int i = 0; i < query.length; i++) {
            DiscreteDomain dom = domainProduct.get(i);

            query[i] = dom.findState(projectionToNodeSpace[i]);
        }
        return query;
    }

}
