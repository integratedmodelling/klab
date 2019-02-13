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
import org.integratedmodelling.ml.legacy.riskwiz.domain.Domain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DomainFactory;


/**
 * @author Sergey Krivov
 *
 */
public class TabularUF extends TabularDetF {

    /**
     * @param domain
     * @param parentDomains
     */
    public TabularUF(DiscreteDomain domain, Vector<DiscreteDomain> parentDomains) {
        super(domain, parentDomains);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param domain
     * @param parentDomains
     */
    public TabularUF(String name, Vector<DiscreteDomain> parentDomains) {
        // this domain will not become part of domain product 
        this.domain = DomainFactory.createUtilityDomain(name);
        if (parentDomains != null) {
            this.parentsDomains = parentDomains;
        } else {
            this.parentsDomains = new Vector< DiscreteDomain>();
        }
        resetDomainProduct();
        resetMultiarray();
    }
	
    @Override
	public void setDomain(Domain domain) {
        throw new UnsupportedOperationException(
                "Can't set domain for Utility Table ");
    }

}
