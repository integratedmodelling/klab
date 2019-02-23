/**
 * NoisyTable.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Mar 4, 2009
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
 * @date      Mar 4, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pfunction;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.Domain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DomainFactory;
import org.integratedmodelling.ml.legacy.riskwiz.pt.CPT;


/**
 * @author Sergey Krivov
 * 
 */
public class NoisyT {

    private Vector<CPT> parentEffects;

    private double[] leack;
    private int size;

    private DiscreteDomain dom;
    private Vector< DiscreteDomain> parentDomains;

    /**
     * 
     */
    public NoisyT(DiscreteDomain dom, Vector< DiscreteDomain> pdoms) {
        this.dom = dom;
        if (parentDomains != null) {
            this.parentDomains = pdoms;
        } else {
            this.parentDomains = new Vector<DiscreteDomain>();
        }
		 
        resetNoisyTable();

    }

    public NoisyT(IFunction cPF) {}

    public TabularCPD toCPF() {
        Vector<DiscreteDomain> pdoms = new Vector<DiscreteDomain>();

        pdoms.addAll(parentDomains);
        TabularCPD cpf = new TabularCPD(this.dom, pdoms);
        Vector<CPT> parentDists = new Vector<CPT>();

        for (CPT cpt : parentEffects) {
            parentDists.add(toDistribution(cpt));
        }

        // iterate over cpf, using iteration on condition and value of domain
        NoisyMap map = getNoisyMap();
        int[] conditionsIterator = map.index2addr(0);
        boolean done = false;

        while (!done) {

            double previousValue = 0;

            for (int domIndex = dom.getOrder() - 1; domIndex
                    >= 0; domIndex--) {
                // get probabilities for parents effects when act independently
                // and multiply them
                double probDistValue = 1;

                for (int i = 0; i < parentDists.size(); i++) {
                    CPT dist = parentDists.get(i);
                    int[] addres = new int[2];

                    addres[0] = domIndex;
                    addres[1] = conditionsIterator[i];
                    probDistValue *= dist.getValue(addres);
                }

                // set the probability density value
                cpf.setValue(map.getCPTAddress(domIndex, conditionsIterator),
                        probDistValue - previousValue);
                // save the curent probability distribution value to calculate
                // next density value
                previousValue = probDistValue;
            }

            done = map.addOne(conditionsIterator);
        }

        int[] leackCond = map.getLast();

        for (int domIndex = 0; domIndex < dom.getOrder(); domIndex++) {
            cpf.setValue(map.getCPTAddress(domIndex, leackCond), leack[domIndex]);
        }

        return cpf;
    }

    private void resetNoisyTable() {
        parentEffects = new Vector<CPT>();
        leack = new double[dom.getOrder()];

        for (Domain pDomain : parentDomains) {
            CPT c = new CPT(dom,
                    DomainFactory.createDomainProduct((DiscreteDomain) pDomain));

            parentEffects.add(c);
			 
        }

        size = 0;
        for (CPT pe : parentEffects) {
            size += pe.size();
        }
        size += leack.length;
		
    }

    public void setValues(double[] vals) {
        int index = 0;
        int domainOrder = dom.getOrder();

        for (CPT pe : parentEffects) {
            int arraySize = pe.size();

            for (int i = 0; i < arraySize; i++) {
                int z = i % domainOrder;
                int z2 = i / domainOrder;
                int z3 = arraySize / domainOrder;

                pe.setValue(z * z3 + z2, vals[index]);
                index++;
            }

        }

        for (int i = 0; i < leack.length; i++) {
            leack[i] = vals[index];
            index++;
        }

    }

    public void setValues(Vector<Double> vals) {
        int index = 0;
        int domainOrder = dom.getOrder();

        for (CPT pe : parentEffects) {
            int arraySize = pe.size();

            for (int i = 0; i < arraySize; i++) {
                int z = i % domainOrder;
                int z2 = i / domainOrder;
                int z3 = arraySize / domainOrder;

                pe.setValue(z * z3 + z2, vals.elementAt(index));
                index++;
            }

        }

        for (int i = 0; i < leack.length; i++) {
            leack[i] = vals.elementAt(index);
            index++;
        }

    }

    // public void setValues(Vector<Double> vals) {
    // int index = 0;
    // int domainOrder = dom.getOrder();
    //
    // for (CPT pe : parentEffects) {
    // System.out.println("parenteffect:"+ pe.toString());
    // DiscreteDomain parentDom = pe.getParentsDomains().firstElement();
    // int parentOrder = parentDom.getOrder();
    //
    // for (int parentIndex = 0; parentIndex < parentOrder; parentIndex++) {
    // for (int domIndex = 0; domIndex < domainOrder; domIndex++) {
    // System.out.println("parentdom: "+parentDom.getName()+" indexes"+
    // domIndex+" "+parentIndex);
    // int[] addres = new int[2];
    // addres[0] = domIndex;
    // addres[1] = parentIndex;
    //
    // pe.setValue(addres, vals.elementAt(index));
    // index++;
    // }
    // }
    //
    // }
    //
    // for (int i = 0; i < leack.length; i++) {
    // leack[i] = vals.elementAt(index);
    // index++;
    // }
    //
    // }

    public double[] getValues() {
        double[] vals = new double[size];
        int tab = dom.getOrder();
        int index = 0;

        for (CPT pe : parentEffects) {
            int max = pe.size();

            for (int j = 0; j < max / tab; j++) {
                for (int k = 0; k < tab; k++) {

                    vals[index] = pe.getValue(k * max / tab + j);
                    index++;

                }
            }

        }

        for (int i = 0; i < leack.length; i++) {
            vals[index] = leack[i];
            index++;
        }

        return vals;
    }

    public String getValuesString(String separator) {
        String vals = "";
        int tab = dom.getOrder();
        int index = 0;

        for (CPT pe : parentEffects) {
            int max = pe.size();

            for (int j = 0; j < max / tab; j++) {
                for (int k = 0; k < tab; k++) {

                    vals += pe.getValue(k * max / tab + j);
                    vals += separator;
                    index++;

                }
            }

        }

        for (int i = 0; i < leack.length; i++) {
            vals += leack[i];
            vals += separator;
            index++;
        }

        return vals;
    }

    public int size() {
        return size;
    }

    private CPT toDistribution(CPT cpt) {
        DiscreteDomain dom = cpt.getDomain();
        DiscreteDomain parentDom = cpt.getParentsDomains().firstElement();
        CPT distribution = new CPT(dom, cpt.getParentsDomains());

        for (int parentIndex = 0; parentIndex < parentDom.getOrder(); parentIndex++) {
            double sum = 0;

            for (int domIndex = dom.getOrder() - 1; domIndex >= 0; domIndex--) {
                int[] addres = new int[2];

                addres[0] = domIndex;
                addres[1] = parentIndex;
                sum += cpt.getValue(addres);
                distribution.setValue(addres, sum);
            }
        }

        return distribution;
    }

    private NoisyMap getNoisyMap() {
        return new NoisyMap(parentDomains);

    }

    public DiscreteDomain getDomain() {
        return  dom;
    }

    public void setDomain(DiscreteDomain dom) {
        this.dom = dom;
        resetNoisyTable();
    }

    public void addedParentDomain(DiscreteDomain dom) {
        this.parentDomains.add(dom);
        resetNoisyTable();
    }

    public void removedParentDomain(DiscreteDomain dom) {
        this.parentDomains.remove(dom);
        resetNoisyTable();
    }

}
