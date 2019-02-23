/**
 * Query.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 7, 2008
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
 * @date      Feb 7, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pt;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;


/**
 * @author Sergey Krivov
 *
 */
public class Query {
	
    protected Vector<DiscreteDomain> domains;
    protected Vector<Double> structure;
	
    public Query() {
        this.domains = new Vector<DiscreteDomain>();		
        this.structure = new Vector<Double>();
    }

    /**
     * 
     */
    public Query(Vector<DiscreteDomain> domains, Vector<Double> values) {
        this.domains = domains;
        this.structure = values;
		
    }
	
    public Query(DiscreteDomain[] domains, Double[] values) {
        this.domains = new Vector<DiscreteDomain>();		
        this.structure = new Vector<Double>();
		
        for (int i = 0; i < domains.length; i++) {
            this.domains.add(domains[i]);
        }
        
        for (int i = 0; i < values.length; i++) {
            this.structure.add(values[i]);
        }
		
    }
	
    public Query(DiscreteDomain dom, DiscreteDomain[] parentDomains, Double val, Double[] values) {
        this.domains = new Vector<DiscreteDomain>();		
        this.structure = new Vector<Double>();
        this.domains.add(dom);
        this.structure.add(val);
        for (int i = 0; i < parentDomains.length; i++) {
            this.domains.add(parentDomains[i]);
        }
        
        for (int i = 0; i < values.length; i++) {
            this.structure.add(values[i]);
        }
		
    }
	
    public Query getPprojection(Vector<DiscreteDomain> targetDomains) {
        Query projectedQuery = new Query();
		
        return projectedQuery;
    }
	
    public int[] getProjectionStructure(Vector<DiscreteDomain> targetDomains) {
		 
        return null;
    }

}
