/**
 * TableFactory.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 19, 2008
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
 * @date      Feb 19, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pt;


import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.Setting;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DomainFactory;
import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.LabelDomain;


/**
 * @author Sergey Krivov
 *
 */
public class TableFactory {

    /**
     * 
     */
    public TableFactory() {// TODO Auto-generated constructor stub
    }
	
    public static PT createPT(Set<BNNode> clique) {
        return new PT(DomainFactory.createDirectProduct(clique));
    }
	
    public static CPT createDefaultCPT(String name) {
        LabelDomain dom = new LabelDomain(name, new String[] { "true", "false"});

        return new CPT(dom, null);
    }
	
    public static DT createDefaultDT(String name) {
        LabelDomain dom = new LabelDomain(name, new String[] { "yes", "no"});

        return new DT(dom);
    }
	
    public static CPT createCPT(String name, String[] labels) {
        LabelDomain dom = new LabelDomain(name, labels);

        return new CPT(dom, null);
    }
	
    public static DT createDT(String name, String[] labels) {
        LabelDomain dom = new LabelDomain(name, labels);

        return new DT(dom);
    }
	
    public static CPT createCPT(String name, double from, double to, int numberOfIntervals) {
        IntervalDomain dom = new IntervalDomain(name, from, to,
                numberOfIntervals);

        return new CPT(dom, null);
    }
	
    public static DT createDT(String name, double from, double to, int numberOfIntervals) {
        IntervalDomain dom = new IntervalDomain(name, from, to,
                numberOfIntervals);

        return new DT(dom);
    }
	
    public static CPT createCPT(String name, int order) {
        DiscreteDomain dom = new DiscreteDomain(name, order);

        return new CPT(dom, null);
    }
	
    public static DT createDT(String name, int order) {
        DiscreteDomain dom = new DiscreteDomain(name, order);

        return new DT(dom);
    }
	
    public static PT createObservation(DiscreteDomain domain, String value) {
        int valueIndex = domain.findState(value); 

        if (valueIndex == -1) {
            if (Setting.DEBUG) {
                System.out.print(domain.getName() + "has no value " + value);
            }
            return null;			
        }  
		
        return createObservation(domain, valueIndex); 
    }
	
    public static PT createObservation(DiscreteDomain domain, int valueIndex) {
        PT pT = new PT(DomainFactory.createDomainProduct(domain)); 
        int[] struc = pT.getStructure();
        int[] strucIterator = getProductStructureIterator(struc);
		
        for (int i = 0; i < struc[0]; i++) {
            strucIterator[0] = i;			
            pT.setValue(strucIterator, 0);
        }
        strucIterator[0] = valueIndex;			
        pT.setValue(strucIterator, 1.0);

        return pT;
    }
	
    public static int[] getProductStructureIterator(int[] struc) {
        int[] productStructureIterator = new int[struc.length];

        for (int i = 0; i < productStructureIterator.length; i++) {
            productStructureIterator[i] = 0;
        }
        return productStructureIterator;
    }
	
    public static PT createUniformDistribution(DiscreteDomain domain) {
        PT pT = new PT(DomainFactory.createDomainProduct(domain));		 
        double val = 1 / pT.size();

        pT.setAll(val);
		 	 
        return pT;
    }
	
    public static int[] getCptReference(int domIndex, int[] ref) {
        int[] cptRef = new int[ref.length + 1];

        cptRef[0] = domIndex;
        for (int i = 1; i < cptRef.length; i++) {
            cptRef[i] = ref[i - 1];
        }

        return cptRef;
    }

}
