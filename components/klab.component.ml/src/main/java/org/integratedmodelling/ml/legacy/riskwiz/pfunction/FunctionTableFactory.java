/**
 * FunctionTableFactory.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Sep 19, 2008
 *
 * ----------------------------------------------------------------------------------
 * This file is part of RiskWiz.
 * 
 * RiskWiz is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your oPFion) any later version.
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
 * @date      Sep 19, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pfunction;


import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.LabelDomain;
 

/**
 * @author Sergey Krivov
 *
 */
public class FunctionTableFactory {

    /**
     * 
     */
    public FunctionTableFactory() {// TODO Auto-generated constructor stub
    }
	
    // //TODO just stub
    // public static PF createPF(Set<BeliefNode> clique){
    // return new PF(DomainFactory.createDirectProduct(clique), null);
    // }
	
    public static TabularCPD createDefaultCPF(String name) {
        LabelDomain dom = new LabelDomain(name, new String[] { "true", "false"});

        return new TabularCPD(dom, null);
    }
	
    public static TabularDetF createDefaultDetF(String name) {
        LabelDomain dom = new LabelDomain(name, new String[] { "true", "false"});

        return new TabularDetF(dom, null);
    }
	
    public static TabularDF createDefaultDF(String name) {
        LabelDomain dom = new LabelDomain(name, new String[] { "yes", "no"});

        return new TabularDF(dom);
    }
	
    public static TabularCPD createCPF(String name, String[] labels) {
        LabelDomain dom = new LabelDomain(name, labels);

        return new TabularCPD(dom, null);
    }
	
    public static TabularDF createDF(String name, String[] labels) {
        LabelDomain dom = new LabelDomain(name, labels);

        return new TabularDF(dom);
    }
	
    public static TabularCPD createCPF(String name, double from, double to, int numberOfIntervals) {
        IntervalDomain dom = new IntervalDomain(name, from, to,
                numberOfIntervals);

        return new TabularCPD(dom, null);
    }
	
    public static TabularDF createDF(String name, double from, double to, int numberOfIntervals) {
        IntervalDomain dom = new IntervalDomain(name, from, to,
                numberOfIntervals);

        return new TabularDF(dom);
    }
	
    public static TabularCPD createCPF(String name, int order) {
        DiscreteDomain dom = new DiscreteDomain(name, order);

        return new TabularCPD(dom, null);
    }
	
    public static TabularDF createDF(String name, int order) {
        DiscreteDomain dom = new DiscreteDomain(name, order);

        return new TabularDF(dom);
    }
	
    // public static  PF createObservation(DiscreteDomain domain, String value){
    // int valueIndex= domain.findState(value) ; 
    // if(valueIndex == -1) {
    // if(Setting.DEBUG) {
    // System.out.print(domain.getName() + "has no value " +value);
    // }
    // return null;			
    // }  
    //
    // return createObservation( domain,   valueIndex); 
    // }
	
    // public static  PF createObservation(DiscreteDomain domain, int valueIndex){
    // PF PF= new PF(DomainFactory.createDomainProduct(domain), null); 
    // int[] struc = PF.getStructure();
    // int[] strucIterator = getProductStructureIterator(struc);
    //
    // for (int i = 0; i < struc[0]; i++) {
    // strucIterator[0] = i;			
    // PF.setValue(strucIterator, 0);
    // }
    // strucIterator[0] = valueIndex;			
    // PF.setValue(strucIterator, 1);
    //
    // return PF;
    // }
	
    public static int[] getProductStructureIterator(int[] struc) {
        int[] productStructureIterator = new int[struc.length];

        for (int i = 0; i < productStructureIterator.length; i++) {
            productStructureIterator[i] = 0;
        }
        return productStructureIterator;
    }
	
    // public static  PF createUniformDistribution(DiscreteDomain domain){
    // PF PF= new PF(DomainFactory.createDomainProduct(domain), null);		 
    // double val=1/PF.size();
    // PF.setAll(val);
    //
    // return PF;
    // }

}
