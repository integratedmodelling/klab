/**
 * UT.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Apr 15, 2008
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
 * @date      Apr 15, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pfunction;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;


/**
 * @author Sergey Krivov
 *
 */
public class TabularDetF extends TabularFunction {

    public TabularDetF() {}
	
    /**
     * @param domain
     * @param parentDomains
     */
    public TabularDetF(DiscreteDomain domain, Vector< DiscreteDomain> parentDomains) {
        this.domain = domain;
        if (parentDomains != null) {
            this.parentsDomains = parentDomains;
        } else {
            this.parentsDomains = new Vector< DiscreteDomain>();
        }
        resetDomainProduct();
        super.resetMultiarray();
    }

    @Override
	public void resetDomainProduct() {
		 
        super.domainProduct = new Vector< DiscreteDomain>();
        // super.discParentDomains = new Vector<DiscreteDomain>();
		
        for (DiscreteDomain dom : parentsDomains) {			 
            super.domainProduct.add(dom);
            // super.discParentDomains.add((DiscreteDomain) dom);
			 
        }
		 
    }
	 
}
