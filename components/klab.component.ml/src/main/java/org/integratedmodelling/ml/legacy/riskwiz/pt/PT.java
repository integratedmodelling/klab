/**
 * PT.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 10, 2008
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
 * @date      Feb 10, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pt;


import java.util.HashMap;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.DomainMap2;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FMarginalizationMap;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FastMap2;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.SubtableFastMap2;
import org.integratedmodelling.ml.legacy.riskwiz.pt.util.PrintValue;
import org.integratedmodelling.ml.legacy.riskwiz.pt.util.PrintValuePTString;
import org.ojalgo.function.BinaryFunction;
import org.ojalgo.function.implementation.PrimitiveFunction;


/**
 * @author Sergey Krivov
 * 
 */
public class PT {

    protected Vector<DiscreteDomain> domainProduct;

    int[] structure = null;

    private double[] multiarray;

    int size;

    public boolean isSingleValue = false;
    private double scalarValue;

    public PT() {}

    /**
     * 
     */
    public PT(Vector<DiscreteDomain> domainProduct) {
        this.domainProduct = domainProduct;
        if (!domainProduct.isEmpty()) {
            resetMultiarray();
        } else {
            isSingleValue = true;
            // multiarray=null;
            // size=0;
            resetMultiarray();
            // System.out.println("single value\n");
        }
    }

    @Override
	public PT clone() {
        PT pT = new PT(this.domainProduct);

        for (int i = 0; i < pT.size(); i++) {
            pT.setValue(i, this.getValue(i));
        }
        return pT;
    }

    public Vector<DiscreteDomain> getDomainProduct() {
        return domainProduct;
    }

    public void setDomainProduct(Vector<DiscreteDomain> domainProduct) {
        this.domainProduct = domainProduct;
    }

    protected void resetMultiarray() {
        structure = new int[domainProduct.size()];
        int s = 1;

        for (int i = 0; i < structure.length; i++) {
            structure[i] = domainProduct.elementAt(i).getOrder();
            s *= domainProduct.elementAt(i).getOrder();
        }
        multiarray = new double[s];
        size = s;
    }

    public int[] getStructure() {
        return structure;
    }

    /*
     * ! Convert between logical to real address (absolute) [MULT-adder] [fairly
     * expensive] \param[in] addr the logical address \return the absolute/real
     */
    public int addr2index(int[] addr) {
        int index = 0;

        for (int i = 0; i < addr.length; i++) {
            index *= structure[i];
            index += addr[i];
        }
        return index;
    }

    /*
     * ! Convert between a real address and a logical address [Div-modul] [very
     * expensive] \param[in] realaddr the absolute/real address \return the
     * logical address
     */
    public int[] index2addr(int index) {
        int[] addr = new int[domainProduct.size()];
        int run = index;

        for (int i = domainProduct.size() - 1; i >= 0; i--) {
            addr[i] = run % structure[i];
            run = (run - addr[i]) / structure[i];
        }
        return addr;
    }

    // TODO check if this works
    public int step(int aDim) {

        int retVal = 1;

        for (int i = aDim - 1; i >= 0; i--) {
            retVal *= structure[i];
        }

        return retVal;
    }

    /*
     * ! Add one to an addr isomorphic to addOne(q) => index(addr2index(q)+1),
     * but FASTER \param[in,out] addr the address
     */
    public boolean addOne(int addr[]) {
        for (int k = addr.length - 1; k >= 0; k--) {
            addr[k]++;
            if (addr[k] >= structure[k]) {
                addr[k] = 0;
                if (k == 0) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    /*
     * ! Get the sum of the values for a query, q[i] = k, k >= 0 -> specific
     * value, -1 all values, per domain \param[in] query the query variable
     * \return the value or sum of values
     */
    public double getValue(int[] query) {
        for (int k = 0; k < query.length; k++) {
            if (query[k] == -1) {
                double s = 0;

                for (int j = 0; j < structure[k]; j++) {
                    query[k] = j;
                    double p = getValue(query);

                    s = s + p;
                }
                query[k] = -1;
                return s;
            }
        }
        return multiarray[addr2index(query)];

    }

    /*
     * the argument query must have exactly one element set to -1
     */

    public int[] getMaxReference1(int[] query) {

        int maxIndex = 0;
        int k = 0;

        while (query[k] != -1) {
            k++;
        }
        query[k] = 0;
        double s = multiarray[addr2index(query)];

        for (int j = 0; j < structure[k]; j++) {
            query[k] = j;
            double p = multiarray[addr2index(query)];

            if (p > s) {
                s = p;
                maxIndex = j;
            }
        }
        query[k] = maxIndex;
        return arrayCopy(query);

    }

    private int[] arrayCopy(int[] query) {
        int[] out = new int[query.length];

        for (int i = 0; i < out.length; i++) {
            out[i] = query[i];
        }
        return out;
    }

    public int[] getMaxReference(int[] query) {

        for (int k = 0; k < query.length; k++) {
            if (query[k] == -1) {
                query[k] = 0;
                int[] maxRef = getMaxReference(query);
                double s = multiarray[addr2index(maxRef)];

                for (int j = 1; j < structure[k]; j++) {
                    query[k] = j;
                    int[] ref = getMaxReference(query);
                    double p = multiarray[addr2index(ref)];

                    if (p > s) {
                        s = p;
                        maxRef = ref;

                    }
                }
                return arrayCopy(maxRef);
            }
        }
        return arrayCopy(query);

    }

    public double getValue(int index) {
        return multiarray[index];
    }

    // public double getValue(Query q) {
    // int[] query = getQueryProjectionStructure(q);
    // return getValue(query);
    // }

    public double getScalarValue() {
        return scalarValue;
    }

    public void setScalarValue(double scalarValue) {
        this.scalarValue = scalarValue;
    }

    // private int[] getQueryProjectionStructure(Query q) {
    //
    // int[] struc = q.getProjectionStructure(domainProduct);
    // return struc;
    // }

    public void setValue(int[] query, double val) {
        for (int k = 0; k < query.length; k++) {
            if (query[k] == -1) {
                for (int j = 0; j < structure[k]; j++) {
                    query[k] = j;
                    setValue(query, val);
                }
                query[k] = -1;
                return;
            }
        }
        multiarray[addr2index(query)] = val;
    }

    public void setValue(int index, double val) {
        multiarray[index] = val;
    }

    public void setValues(double[] vals) {
        if (vals.length != this.size()) {
            System.out.println("Wrong size of input array for ");
        }
        for (int i = 0; i < vals.length; i++) {
            this.setValue(i, vals[i]);
        }

    }

    public void setValues(Vector<Double> vals) {
        if (vals.size() != this.size()) {
            System.out.println("Wrong size of input array for ");
        }
        for (int i = 0; i < vals.size(); i++) {
            this.setValue(i, vals.elementAt(i));
        }

    }

    public double[] getValues() {
        double[] vals = new double[this.size()];

        for (int i = 0; i < this.size(); i++) {
            vals[i] = this.getValue(i);
        }
        return vals;
    }

    public void setAll(double val) {

        for (int i = 0; i < multiarray.length; i++) {
            multiarray[i] = val;
        }
    }

    // public void setValue(Query q, double val) {
    // int[] struc = getQueryProjectionStructure(q);
    // setValue(struc, val);
    // }

    public int size() {
        return size;
    }

    public static PT multiply(PT pt1, PT pt2) {
        DomainMap2 dm2 = new DomainMap2(pt1.getDomainProduct(),
                pt2.getDomainProduct());

        return binaryFunction(pt1, pt2, dm2, PrimitiveFunction.MULTIPLY);
    }

    public static PT divide(PT pt1, PT pt2) {
        DomainMap2 dm2 = new DomainMap2(pt1.getDomainProduct(),
                pt2.getDomainProduct());

        return binaryFunction(pt1, pt2, dm2, PrimitiveFunction.DIVIDE);
    }

    public static PT add(PT pt1, PT pt2) {
        DomainMap2 dm2 = new DomainMap2(pt1.getDomainProduct(),
                pt2.getDomainProduct());

        return binaryFunction(pt1, pt2, dm2, PrimitiveFunction.ADD);
    }

    public static PT subtract(PT pt1, PT pt2) {
        DomainMap2 dm2 = new DomainMap2(pt1.getDomainProduct(),
                pt2.getDomainProduct());

        return binaryFunction(pt1, pt2, dm2, PrimitiveFunction.SUBTRACT);
    }

    private static PT binaryFunction(PT pt1, PT pt2, DomainMap2 dm2,
            BinaryFunction<Double> func) {
        Vector<DiscreteDomain> domainProduct = dm2.getDomainProduct();
        PT pT = new PT(domainProduct);

        int[] productStructureIterator = pT.index2addr(0);
        boolean done = false;

        while (!done) {
            setOneOutputPTValue(productStructureIterator, pT, pt1, pt2, dm2,
                    func);
            done = pT.addOne(productStructureIterator);
        }

        // iterateStructure3(productStructure, productStructureIterator, 0, pt,
        // pt1, pt2, dm2, func);

        return pT;
    }

    private static void setOneOutputPTValue(int[] productStructureIterator,
            PT ptOut, PT pt1, PT pt2, DomainMap2 dm2,
            BinaryFunction<Double> func) {
        double value1 = pt1.getValue(
                dm2.getProjectionFirst(productStructureIterator));
        double value2 = pt2.getValue(
                dm2.getProjectionSecond(productStructureIterator));
        double value = func.invoke(value1, value2);

        ptOut.setValue(productStructureIterator, value);

    }

    //
    // public static PT substitution(PT pT, DiscreteDomain dom, int fixedArg) {
    // int[] fixedArgs = new int[1];
    // fixedArgs[0] = fixedArg;
    // Vector<DiscreteDomain> doms = new Vector<DiscreteDomain>();
    // doms.add(dom);
    // MarginalizationDomainMap mdmap = new MarginalizationDomainMap(pT
    // .getDomainProduct(), doms, fixedArgs);
    // return substitution(pT, mdmap, fixedArgs);
    // }
    //
    // public static PT substitution(PT pT, Vector<DiscreteDomain> doms,
    // int[] fixedArgs) {
    // MarginalizationDomainMap mdmap = new MarginalizationDomainMap(pT
    // .getDomainProduct(), doms, fixedArgs);
    // return substitution(pT, mdmap, fixedArgs);
    // }

    // public static PT substitution(PT pT, MarginalizationDomainMap mdmap,
    // int[] fixedArgs) {
    // Vector<DiscreteDomain> projectionDomainProduct = mdmap
    // .getProjectionDomainProduct();
    // PT margPt = new PT(projectionDomainProduct);
    //
    // int[] productStructureIterator = margPt.index2addr(0);
    // boolean done = false;
    // while (!done) {
    // int[] superSpaceRef = mdmap.getSuperspaceReference(
    // productStructureIterator, fixedArgs);
    // double val = pT.getValue(superSpaceRef);
    // margPt.setValue(productStructureIterator, val);
    // done = margPt.addOne(productStructureIterator);
    // }
    //
    // return margPt;
    // }

    // public static PT substitutionFunctional(PT pt, DiscreteDomain dom, PT
    // ptSubstitute) {
    // Vector<PT> ptSubstitutes= new Vector<PT>();
    // ptSubstitutes.add(ptSubstitute);
    // Vector<DiscreteDomain> doms = new Vector<DiscreteDomain>();
    // doms.add(dom);
    // MarginalizationDomainMap mdmap = new MarginalizationDomainMap(pt
    // .getDomainProduct(), doms, new int[1]);
    // return substitutionFunctional(pt, mdmap, ptSubstitutes);
    // }
    //
    // public static PT substitutionFunctional(PT pt, Vector<DiscreteDomain>
    // doms, Vector<PT> ptSubstitutes) {
    // MarginalizationDomainMap mdmap = new MarginalizationDomainMap(pt
    // .getDomainProduct(), doms, new int[ptSubstitutes.size()]);
    // return substitutionFunctional(pt, mdmap, ptSubstitutes);
    // }
    //
    // public static PT substitutionFunctional(PT pt, MarginalizationDomainMap
    // mdmap, Vector<PT> ptSubstitutes) {
    // Vector<DiscreteDomain> projectionDomainProduct = mdmap
    // .getProjectionDomainProduct();
    // PT margPt = new PT(projectionDomainProduct);
    // //create marginalisation maps for each pt in ptSubstitutes, just to
    // define projection
    // MarginalizationDomainMap[] mdmaps= new
    // MarginalizationDomainMap[ptSubstitutes.size()];
    // for (int i = 0; i < mdmaps.length; i++) {
    // mdmaps[i]= new MarginalizationDomainMap( projectionDomainProduct,
    // ptSubstitutes.elementAt(i).getDomainProduct(), true);
    // }
    // int[] args =new int[ptSubstitutes.size()];
    // int[] productStructureIterator = margPt.index2addr(0);
    // boolean done = false;
    // while (!done) {
    // //get args value for given productStructureIterator
    // for (int i = 0; i < args.length; i++) {
    // int[] argRef =mdmaps[i].getProjection(productStructureIterator);
    // args[i]=ptSubstitutes.elementAt(i).getValue(argRef);
    // }
    //
    // int[] superSpaceRef =
    // mdmap.getSuperspaceReference(productStructureIterator, args);
    // double val = pt.getValue(superSpaceRef);
    // margPt.setValue(productStructureIterator, val);
    // done = margPt.addOne(productStructureIterator);
    // }
    //
    // return margPt;
    // }

    // public static MaxMarginal maxMarginalizeDomain(PT pT, DiscreteDomain dom) {
    // MarginalizationDomainMap mdmap = new MarginalizationDomainMap(pT
    // .getDomainProduct(), dom);
    // return maxMarginalizeDomains(pT, mdmap);
    // }
    //
    // public static MaxMarginal maxMarginalizeDomains(PT pT,
    // Vector<DiscreteDomain> doms) {
    // MarginalizationDomainMap mdmap = new MarginalizationDomainMap(pT
    // .getDomainProduct(), doms);
    // return maxMarginalizeDomains(pT, mdmap);
    // }
    //
    // public static MaxMarginal maxMarginalizeDomains(PT pT,
    // MarginalizationDomainMap mdmap) {
    // Vector<DiscreteDomain> projectionDomainProduct = mdmap
    // .getProjectionDomainProduct();
    // PT margPt = new PT(projectionDomainProduct);
    // PT policy = new PT(pT.getDomainProduct());
    // policy.setAll(0);
    // int[] productStructureIterator = margPt.index2addr(0);
    // boolean done = false;
    // while (!done) {
    // int[] fiber = mdmap.getFiber(productStructureIterator);
    // int[] maxReference = pT.getMaxReference(fiber);
    // margPt
    // .setValue(productStructureIterator, pT
    // .getValue(maxReference));
    // policy.setValue(maxReference, 1);
    // done = margPt.addOne(productStructureIterator);
    // }
    // return new MaxMarginal(policy, margPt);
    // }

    // public static void marginalizeDomainsFast(PT margPt, PT pT,
    // MarginalizationFastMap mmap) {
    // // Vector<DiscreteDomain> projectionDomainProduct = mmap
    // // .getProjectionDomainProduct();
    // // PT margPt = new PT(projectionDomainProduct);
    // //		 
    //
    // margPt.setAll(0);
    //
    // int[] indexMap = mmap.getIndexMap();
    // // System.out.println("index map \n");
    // // for (int i = 0; i < indexMap.length; i++) {
    // // System.out.print(indexMap[i]+",");
    // // }
    //
    // for (int j = 0; j < indexMap.length; j++) {
    // int margIndex = indexMap[j];
    // double val = margPt.getValue(margIndex) + pT.getValue(j);
    // // System.out.println("margIndex:"+margIndex+", set Value:"+
    // // margPt.getValue(margIndex)
    // // + " + "+ pt.getValue(j)+"=" + val);
    // margPt.setValue(margIndex, val);
    // }
    //
    // }

    public static void marginalizeDomainsFast(PT margPt, PT pT,
            FMarginalizationMap mmap) {

        margPt.setAll(0);

        int[] indexMap = mmap.getIndexMap();

        for (int j = 0; j < indexMap.length; j++) {
            int margIndex = indexMap[j];
            double val = margPt.getValue(margIndex) + pT.getValue(j);

            margPt.setValue(margIndex, val);
        }

    }

    // public static MaxMarginal maxMarginalizeDomainsFast(PT pT,
    // MarginalizationFastMap mmap) {
    // Vector<DiscreteDomain> projectionDomainProduct = mmap
    // .getProjectionDomainProduct();
    // PT margPt = new PT(projectionDomainProduct);
    // margPt.setAll(-10e38);
    // PT policy = new PT(pT.getDomainProduct());
    // policy.setAll(0);
    //
    // int[] indexMap = mmap.getIndexMap();
    // int[] reverseIndexMap = new int[margPt.size()];
    //
    // for (int j = 0; j < indexMap.length; j++) {
    // int margIndex = indexMap[j];
    // double val = pT.getValue(j);
    // if (val > margPt.getValue(margIndex)) {
    // margPt.setValue(margIndex, val);
    // reverseIndexMap[margIndex] = j;
    // }
    // }
    //
    // for (int i = 0; i < reverseIndexMap.length; i++) {
    // policy.setValue(reverseIndexMap[i], 1);
    // }
    //
    // return new MaxMarginal(policy, margPt);
    // }

    public static void multiplyFast(PT pTout, PT pt1, PT pt2, FastMap2 dm2) {
        binaryFunctionFast(pTout, pt1, pt2, dm2, PrimitiveFunction.MULTIPLY);
    }

    public static void divideFast(PT pTout, PT pt1, PT pt2, FastMap2 dm2) {
        binaryFunctionFast(pTout, pt1, pt2, dm2, PrimitiveFunction.DIVIDE);
    }

    public static void addFast(PT pTout, PT pt1, PT pt2, FastMap2 dm2) {
        binaryFunctionFast(pTout, pt1, pt2, dm2, PrimitiveFunction.ADD);
    }

    public static void subtractFast(PT pTout, PT pt1, PT pt2, FastMap2 dm2) {
        binaryFunctionFast(pTout, pt1, pt2, dm2, PrimitiveFunction.SUBTRACT);
    }

    private static void binaryFunctionFast(PT pTout, PT pt1, PT pt2,
            FastMap2 dm2, BinaryFunction<Double> func) {

        int[] indexMap1 = dm2.getIndexMap1();
        int[] indexMap2 = dm2.getIndexMap2();

        for (int i = 0; i < indexMap2.length; i++) {
            double val1 = pt1.getValue(indexMap1[i]);
            double val2 = pt2.getValue(indexMap2[i]);

            pTout.setValue(i, func.invoke(val1, val2));
        }

    }

    public static PT multiplySimTables(PT pt1, PT pt2) {
        return binaryFunctionSimTables(pt1, pt2, PrimitiveFunction.MULTIPLY);
    }

    public static PT divideSimTables(PT pt1, PT pt2) {
        return binaryFunctionSimTables(pt1, pt2, PrimitiveFunction.DIVIDE);
    }

    public static PT addSimTables(PT pt1, PT pt2) {
        return binaryFunctionSimTables(pt1, pt2, PrimitiveFunction.ADD);
    }

    public static PT subtractSimTables(PT pt1, PT pt2) {
        return binaryFunctionSimTables(pt1, pt2, PrimitiveFunction.SUBTRACT);
    }

    public static PT binaryFunctionSimTables(PT pt1, PT pt2,
            BinaryFunction<Double> func) {
        Vector<DiscreteDomain> domainProduct = pt1.getDomainProduct();
        PT pT = new PT(domainProduct);

        for (int i = 0; i < pT.size(); i++) {
            double val1 = pt1.getValue(i);
            double val2 = pt2.getValue(i);

            pT.setValue(i, func.invoke(val1, val2));
        }
        return pT;

    }

    // is this needed?
    public static PT multiply(Vector<PT> pts) {
        return null;
    }

    public void multiplyBySubtable(PT pt2, DomainMap2 dm2) {
        binaryFunctionSubtable(pt2, dm2, PrimitiveFunction.MULTIPLY);
    }

    public void divideBySubtable(PT pt2, DomainMap2 dm2) {
        binaryFunctionSubtable(pt2, dm2, PrimitiveFunction.DIVIDE);
    }

    public void addSubtable(PT pt2, DomainMap2 dm2) {
        binaryFunctionSubtable(pt2, dm2, PrimitiveFunction.ADD);
    }

    public void subtructSubtable(PT pt2, DomainMap2 dm2) {
        binaryFunctionSubtable(pt2, dm2, PrimitiveFunction.SUBTRACT);
    }

    private void binaryFunctionSubtable(PT pt2, DomainMap2 dm2,
            BinaryFunction<Double> func) {
        PT pt1 = this.clone();

        int[] productStructureIterator = this.index2addr(0);
        boolean done = false;

        while (!done) {
            setOneOutputPTValue(productStructureIterator, this, pt1, pt2, dm2,
                    func);
            done = this.addOne(productStructureIterator);
        }

    }

    public void multiplyBySubtableFast(PT pt2, SubtableFastMap2 dm2) {
        binaryFunctionSubtableFast(pt2, dm2, PrimitiveFunction.MULTIPLY);
    }

    public void multiplyAndDivideBySubtableFast(PT ptMult, PT ptDiv,
            SubtableFastMap2 dm2) {
        // PT pt1 = this.clone();
        // int[] indexMap1 = dm2.getIndexMap1();
        int[] indexMap2 = dm2.getIndexMap2();

        for (int i = 0; i < indexMap2.length; i++) {
            double val1 = this.getValue(i);
            double valMult = ptMult.getValue(indexMap2[i]);
            double valDiv = ptDiv.getValue(indexMap2[i]);

            if (valDiv == 0) {
                this.setValue(i, 0);
            } else {
                this.setValue(i, val1 * valMult / valDiv);
            }

        }
    }

    public void divideBySubtableFast(PT pt2, SubtableFastMap2 dm2) {
        binaryFunctionSubtableFast(pt2, dm2, PrimitiveFunction.DIVIDE);
    }

    public void addSubtableFast(PT pt2, SubtableFastMap2 dm2) {
        binaryFunctionSubtableFast(pt2, dm2, PrimitiveFunction.ADD);
    }

    public void subtructSubtableFast(PT pt2, SubtableFastMap2 dm2) {
        binaryFunctionSubtableFast(pt2, dm2, PrimitiveFunction.SUBTRACT);
    }

    private void binaryFunctionSubtableFast(PT pt2, SubtableFastMap2 dm2,
            BinaryFunction<Double> func) {

        // PT pt1 = this.clone();
        // int[] indexMap1 = dm2.getIndexMap1();
        int[] indexMap2 = dm2.getIndexMap2();

        for (int i = 0; i < indexMap2.length; i++) {
            // System.out.print(" indexMap1["+i+"]="+indexMap1[i]
            // +" indexMap2["+i+"]="+indexMap2[i]);
            double val1 = this.getValue(i);
            double val2 = pt2.getValue(indexMap2[i]);

            this.setValue(i, func.invoke(val1, val2));

        }
        // System.out.println();

    }

    public SubtableFastMap2 createSubtableFastMap(PT pt2) {
        return new SubtableFastMap2(this.domainProduct, pt2.domainProduct);
    }

    public DomainMap2 createSubtableDomainMap(PT pt2) {
        return new DomainMap2(this.domainProduct, pt2.domainProduct);
    }

    public FastMap2 createSubtableFastMap(Vector<DiscreteDomain> domainProduct) {
        return new FastMap2(this.domainProduct, domainProduct);
    }

    public DomainMap2 createSubtableDomainMap(
            Vector<DiscreteDomain> domainProduct) {
        return new DomainMap2(this.domainProduct, domainProduct);
    }

    // public MarginalizationFastMap createMarginalizationFastMap(
    // DiscreteDomain dom) {
    // return new MarginalizationFastMap(this.domainProduct, dom, true);
    // }
    //
    // public MarginalizationDomainMap createMarginalizationDomainMap(
    // DiscreteDomain dom) {
    // return new MarginalizationDomainMap(this.domainProduct, dom, true);
    // }

    // this has to be overwritten in CPT
    public void normalize() {
        double Sum = sum();

        for (int i = 0; i < multiarray.length; i++) {
            double val = multiarray[i];

            multiarray[i] = val / Sum;
        }

    }

    public double sum() {
        double Sum = 0;

        for (int i = 0; i < multiarray.length; i++) {
            Sum += multiarray[i];
        }
        return Sum;

    }

    @Override
	public String toString() {
        PrintValuePTString printer = new PrintValuePTString();

        printValues(printer);
        return printer.getOutput();
    }

    public void printValues(PrintValue printer) {

        int[] productStructureIterator = this.index2addr(0);
        boolean done = false;

        while (!done) {
            printer.print(this.getDomainProduct(), productStructureIterator,
                    this.getValue(productStructureIterator));

            done = this.addOne(productStructureIterator);
        }

    }

    public HashMap<String, Double> getDomainValuePairs() {

        HashMap<String, Double> ret = new HashMap<String, Double>();
        int[] productStructureIterator = this.index2addr(0);
        boolean done = false;

        while (!done) {
            for (int i = 0; i < productStructureIterator.length; i++) {
                DiscreteDomain dom = domainProduct.elementAt(i);

                ret.put(dom.getState(productStructureIterator[i]),
                        this.getValue(productStructureIterator));
            }
            done = this.addOne(productStructureIterator);
        }
        return ret;
    }

}
