/**
 * NewDiscretizationDomainMap.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: May 22, 2009
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
 * @date      May 22, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.discretizer;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.ContinuousDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.Domain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;


/**
 * @author Sergey Krivov
 *
 */
public class DiscretizationDomainMap {
	
    protected Vector< DiscreteDomain> discretizedParentDomains;

    protected DiscreteDomain domain;

    /**
     * 
     */
    public DiscretizationDomainMap() {// TODO Auto-generated constructor stub
    }
	
    public DiscretizationDomainMap(Vector<? extends  Domain> contParentDomains,
            BeliefNetwork bn) {

        initState();
        initParentDomains(contParentDomains, bn);
		 
    }

    private void initState() {		 
        this.discretizedParentDomains = new Vector<DiscreteDomain>();
		 
    }

    private void initParentDomains(Vector<? extends Domain> parentDomains,
            BeliefNetwork bn) {
        for (Domain dom : parentDomains) {

            if (dom  instanceof ContinuousDomain) {
				 
                IntervalDomain idom = getDiscretizedDomain(
                        dom, bn);

                this.discretizedParentDomains.add(idom);
				 
            } else {
                this.discretizedParentDomains.add((DiscreteDomain) dom);
				 
            }
        }

    }
	
    public IntervalDomain getDiscretizedDomain(Domain dom,
            BeliefNetwork bn) {
		
        BNNode node = bn.getBeliefNode(dom.getName());

        return (IntervalDomain) node.getDiscretizedDomain();

    }
	
    public Vector<DiscreteDomain> getDiscretizedParentDomains() {
        return discretizedParentDomains;
    }

}
