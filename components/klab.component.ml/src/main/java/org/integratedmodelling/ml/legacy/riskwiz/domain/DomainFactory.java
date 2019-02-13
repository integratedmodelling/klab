/**
 * DomainFactory.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 27, 2008
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
 * @date      Feb 27, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.domain;


import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;


/**
 * @author Sergey Krivov
 *
 */
public class DomainFactory {
	
    // public static Vector<DiscreteDomain> createDirectProduct( Vector<Vector<DiscreteDomain > >  domains )  {
    // Vector<DiscreteDomain> domainProduct = new Vector<DiscreteDomain>();
    //
    // for (Vector<DiscreteDomain> vector : domains) {
    // for (DiscreteDomain domain : vector) {
    // if(!domainProduct.contains(domain)){
    // domainProduct.add(domain);
    // }
    // }
    // }		
    // return domainProduct;
    // }
	
    // this is useful for 1-dimensional probability tables
    public static Vector<DiscreteDomain> createDomainProduct(DiscreteDomain domain) {
        Vector<DiscreteDomain> domainProduct = new Vector<DiscreteDomain>();

        domainProduct.add(domain);
        return domainProduct;
    }
	
    public static LabelDomain createUtilityDomain(String name) {
		 
        return new LabelDomain(name, new String[] { "values" });
    }
	
    public static Vector<DiscreteDomain> createDirectProduct(Set<BNNode> clique) {
        Vector<DiscreteDomain >    directProduct = new  Vector<DiscreteDomain >();

        for (BNNode node : clique) {
            directProduct.add(node.getDiscretizedDomain());
        }
			  
        return directProduct;
    }

}

