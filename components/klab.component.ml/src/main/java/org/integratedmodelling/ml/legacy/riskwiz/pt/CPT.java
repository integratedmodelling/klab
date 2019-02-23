/**
 * CPT.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 6, 2008
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
 * @date      Feb 6, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pt;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.pt.util.PrintValueCPTString;


/**
 * @author Sergey Krivov
 * 
 */
public class CPT extends PT {

    protected DiscreteDomain domain;

    protected Vector<DiscreteDomain> parentsDomains;
	
    public CPT() {}

    /**
     * 
     */
    public CPT(DiscreteDomain domain, Vector<DiscreteDomain> parentDomains) {
        this.domain = domain;
        if (parentDomains != null) {
            this.parentsDomains = parentDomains;
        } else {
            this.parentsDomains = new Vector<DiscreteDomain>();
        }
        resetDomainProduct();
        resetMultiarray();
    }

    public DiscreteDomain getDomain() {
        return domain;
    }

    public void setDomain(DiscreteDomain domain) {
        this.domain = domain;
        resetDomainProduct();
        resetMultiarray();
    }

    public Vector<DiscreteDomain> getParentsDomains() {
        return parentsDomains;
    }

    public void setParentsDomains(Vector<DiscreteDomain> parentsDomains) {
        this.parentsDomains = parentsDomains;
        resetDomainProduct();
        super.resetMultiarray();
    }

    public void addParentDomain(DiscreteDomain dom) {
        this.parentsDomains.add(dom);
        resetDomainProduct();
        super.resetMultiarray();
    }

    public void removeParentDomain(DiscreteDomain dom) {
        this.parentsDomains.remove(dom);
        resetDomainProduct();
        super.resetMultiarray();
    }

    /*
     * TODO order can change
     */

    public void resetDomainProduct() {
        domainProduct = new Vector<DiscreteDomain>();
        domainProduct.add(domain);
        domainProduct.addAll(parentsDomains);
    }

    // public double querySuperSet(Query q){
    // return 0;
    // }
    //
    // public double querySubSet(Query q){
    // return 0;
    // }
    //

    public CPT normalizeCPT() {
        return null;
    }

    @Override
	public String toString() {
        PrintValueCPTString printer = new PrintValueCPTString();
        int max = this.size();
        int tab = this.getDomain().getOrder();

        for (int j = 0; j < max / tab; j++) {
            for (int k = 0; k < tab; k++) {
                int index = k * max / tab + j;

                printer.print(this.getDomainProduct(), index2addr(index),
                        this.getValue(index));
				 
            }
        }
        return printer.getOutput();
    }
	
}
