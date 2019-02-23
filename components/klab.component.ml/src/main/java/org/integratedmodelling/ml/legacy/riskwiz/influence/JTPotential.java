/**
 * JTPotential.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: May 2, 2008
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
 * @date      May 2, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.influence;


import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DomainFactory;
import org.integratedmodelling.ml.legacy.riskwiz.pt.CPT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.DomainMap2;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FMarginalizationMap;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FastMap2;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.SubtableFastMap2;


/**
 * @author Sergey Krivov
 * 
 */
public class JTPotential {

    Vector<DiscreteDomain> domainProduct;

    private PT probabilityPotential;

    private PT utilityPotential;

    // Set<BeliefNode> nodeSet;

    public JTPotential() {// TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    public JTPotential(Set<BNNode> nodeSet) {
        createPotentials(nodeSet);
    }

    public JTPotential(PT probPotential, PT utilPotential) {
        this.domainProduct = probPotential.getDomainProduct();
        probabilityPotential = probPotential;
        utilityPotential = utilPotential;
    }

    // public void createPotentials() {
    // probabilityPotential= TableFactory.createPT(nodeSet);
    // utilityPotential= TableFactory.createPT(nodeSet);
    // }

    public void createPotentials(Set<BNNode> nodeSet) {
        // this.nodeSet=nodeSet;
        this.domainProduct = DomainFactory.createDirectProduct(nodeSet);
        probabilityPotential = new PT(this.domainProduct);
        utilityPotential = new PT(this.domainProduct);
    }

    public PT getProbabilityPotential() {
        return probabilityPotential;
    }

    public void setProbabilityPotential(PT probabilityPotential) {
        this.probabilityPotential = probabilityPotential;
    }

    public PT getUtilityPotential() {
        return utilityPotential;
    }

    public void setUtilityPotential(PT utilityPotential) {
        this.utilityPotential = utilityPotential;
    }

    public Vector<DiscreteDomain> getDomainProduct() {
        return domainProduct;
    }

    public void setVacious() {
        probabilityPotential.setAll(1);
        utilityPotential.setAll(0);
    }

    public void setVaciousProb() {
        probabilityPotential.setAll(1);
        utilityPotential = null;
    }

    public FastMap2 createSubtableFastMap(PT pt2) {
        return new FastMap2(this.domainProduct, pt2.getDomainProduct());
    }

    public DomainMap2 createSubtableDomainMap(PT pt2) {
        return new DomainMap2(this.domainProduct, pt2.getDomainProduct());
    }

    public FastMap2 createSubtableFastMap(Vector<DiscreteDomain> domainProduct) {
        return new FastMap2(this.domainProduct, domainProduct);
    }

    public FMarginalizationMap createFMarginalizationMap(
            DiscreteDomain dom) {
        return new FMarginalizationMap(this.domainProduct, dom);
    }

    public FMarginalizationMap createFMarginalizationMap(
            Vector<DiscreteDomain> doms) {
        return new FMarginalizationMap(this.domainProduct, doms);
    }

    public void multiplyBySubtable(JTPotential pt2, DomainMap2 dm2) {
        probabilityPotential.multiplyBySubtable(pt2.getProbabilityPotential(),
                dm2);
        utilityPotential.addSubtable(pt2.getUtilityPotential(), dm2);
    }

    public void multiplyByProbabilitySubtable(PT pt2, DomainMap2 dm2) {
        probabilityPotential.multiplyBySubtable(pt2, dm2);

    }

    public void addUtilitySubtable(PT pt2, DomainMap2 dm2) {
        utilityPotential.addSubtable(pt2, dm2);
    }

    public void multiplyBySubtableFast(JTPotential pt2, SubtableFastMap2 dm2) {
        probabilityPotential.multiplyBySubtableFast(
                pt2.getProbabilityPotential(), dm2);
        utilityPotential.addSubtableFast(pt2.getUtilityPotential(), dm2);
    }

    public static JTPotential marginalizeDomainsFast(JTPotential pt,
            FMarginalizationMap mmap) {
		 
        PT probPotential = new PT(mmap.getProjectionDomainProduct());

        PT.marginalizeDomainsFast(probPotential, pt.getProbabilityPotential(),
                mmap);
        PT weightedUtil = PT.multiplySimTables(pt.getUtilityPotential(),
                pt.getProbabilityPotential());
        PT utilPotentialTemp = new PT(mmap.getProjectionDomainProduct());

        PT.marginalizeDomainsFast(utilPotentialTemp, weightedUtil, mmap);
        PT utilPotential = PT.divideSimTables(utilPotentialTemp, probPotential);

        return new JTPotential(probPotential, utilPotential);
    }

    public static JTPotential maxMarginalizeDomain(JTPotential pt,
            DiscreteDomain dom, Hashtable<DiscreteDomain, CPT> policyHash) {
		
        Vector<DiscreteDomain> parentDomains = new Vector<DiscreteDomain>();

        parentDomains.addAll(pt.getDomainProduct());
        parentDomains.remove(dom);
		
        FMarginalizationMap mdmap = new FMarginalizationMap(
                pt.getDomainProduct(), parentDomains);

        // Vector <DiscreteDomain> domProduct=pt.getDomainProduct();
        // System.out.println();
        // for (DiscreteDomain domain : domProduct) {
        // System.out.print (dom.getName()+", ");
        // }
        // System.out.println();

        Vector<DiscreteDomain> projectionDomainProduct = mdmap.getProjectionDomainProduct();
        PT probPotential = new PT(projectionDomainProduct);
        PT utilPotential = new PT(projectionDomainProduct);

        System.out.println(
                "Utility Potential:\n" + pt.getUtilityPotential().toString()
                + "\n");

        CPT policy = new CPT(dom, parentDomains);

        policy.setAll(0);
		
        PT weightedUtil = PT.multiplySimTables(pt.getUtilityPotential(),
                pt.getProbabilityPotential());

        if (probPotential.isSingleValue) {
            int[] fiber = new int[] { -1 };
            int[] maxReference = weightedUtil.getMaxReference(fiber);
            double probScalar = pt.getProbabilityPotential().getValue(
                    maxReference);

            probPotential.setScalarValue(probScalar);
			
            utilPotential.setScalarValue(
                    weightedUtil.getValue(maxReference) / probScalar);
            policy.setValue(maxReference, 1);

        } else {
			
            int[] productStructureIterator = utilPotential.index2addr(0);
            boolean done = false;

            while (!done) {
                int[] fiber = mdmap.getFiber(productStructureIterator);
                int[] maxReference = weightedUtil.getMaxReference(fiber);
                double probVal = pt.getProbabilityPotential().getValue(
                        maxReference);

                probPotential.setValue(productStructureIterator, probVal);
                utilPotential.setValue(productStructureIterator, 
                        weightedUtil.getValue(maxReference) / probVal);
                policy.setValue(maxReference, 1);
                done = utilPotential.addOne(productStructureIterator);
            }
        }

        policyHash.put(dom, policy);
        System.out.println(
                dom.getName() + ":here it is\n" + policy.toString() + "\n");
        utilPotential = PT.divideSimTables(utilPotential, probPotential);
        return new JTPotential(probPotential, utilPotential);

    }

    // public static JTPotential maxMarginalizeDomains(JTPotential pt,
    // Vector<DiscreteDomain> doms) {
    // MarginalizationDomainMap mdmap = new MarginalizationDomainMap(pt
    // .getDomainProduct(), doms);
    // return maxMarginalizeDomains(pt, mdmap);
    // }

    // public static JTPotential maxMarginalizeDomain(JTPotential pt,
    // MarginalizationDomainMap mdmap, DiscreteDomain dom,
    // Hashtable<DiscreteDomain, CPT> policyHash) {
    // Vector<DiscreteDomain> projectionDomainProduct = mdmap
    // .getProjectionDomainProduct();
    // PT probPotential = new PT(projectionDomainProduct);
    // PT utilPotential = new PT(projectionDomainProduct);
    // CPT policy = new PT(pt.getDomainProduct());
    // policy.setAll(0);
    //
    // int[] productStructureIterator = utilPotential.index2addr(0);
    // boolean done = false;
    // while (!done) {
    // int[] fiber = mdmap.getFiber(productStructureIterator);
    // int[] maxReference = pt.getUtilityPotential()
    // .getMaxReference(fiber);
    // probPotential.setValue(productStructureIterator, pt
    // .getProbabilityPotential().getValue(maxReference));
    // utilPotential.setValue(productStructureIterator, pt
    // .getUtilityPotential().getValue(maxReference));
    // policy.setValue(maxReference, 1);
    // done = utilPotential.addOne(productStructureIterator);
    // }
    // policyHash.put(dom, policy);
    // utilPotential = PT.divideSimTables(utilPotential, probPotential);
    // return new JTPotential(probPotential, utilPotential);
    // }

    public static JTPotential marginalizeDomainsSequence(JTPotential pt,
            Vector<Object> maps, Hashtable<DiscreteDomain, CPT> policyHash) {

        JTPotential pt1 = pt;

        for (Object object : maps) {
            if (object instanceof FMarginalizationMap) {

                pt1 = marginalizeDomainsFast(pt1, (FMarginalizationMap) object);
            } else if (object instanceof DiscreteDomain) {

                pt1 = maxMarginalizeDomain(pt1, (DiscreteDomain) object,
                        policyHash);
            }
        }
        return pt1;
    }

}
