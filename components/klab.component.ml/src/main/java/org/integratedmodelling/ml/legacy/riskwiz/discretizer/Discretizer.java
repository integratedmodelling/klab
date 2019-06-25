///**
// * Discretizer.java
// * ----------------------------------------------------------------------------------
// * 
// * Copyright (C) 2008 www.integratedmodelling.org
// * Created: Aug 21, 2008
// *
// * ----------------------------------------------------------------------------------
// * This file is part of riskwiz-cvars.
// * 
// * riskwiz-cvars is free software; you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation; either version 3 of the License, or
// * (at your option) any later version.
// * 
// * riskwiz-cvars is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// * 
// * You should have received a copy of the GNU General Public License
// * along with the software; if not, write to the Free Software
// * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
// * 
// * ----------------------------------------------------------------------------------
// * 
// * @copyright 2008 www.integratedmodelling.org
// * @author    Sergey Krivov
// * @date      Aug 21, 2008
// * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
// * @link      http://www.integratedmodelling.org
// **/
//
//package org.integratedmodelling.ml.legacy.riskwiz.discretizer;
//
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//import java.util.Vector;
//
//import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
//import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
//import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
//import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;
//import org.integratedmodelling.ml.legacy.riskwiz.pfunction.ICondProbDistrib;
//import org.integratedmodelling.ml.legacy.riskwiz.pfunction.IFunction;
//import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularFunction;
//import org.integratedmodelling.ml.legacy.riskwiz.pt.CPT;
//import org.integratedmodelling.ml.legacy.riskwiz.pt.DT;
//import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
//import org.integratedmodelling.ml.legacy.riskwiz.pt.TableFactory;
//import org.integratedmodelling.ml.legacy.riskwiz.pt.UT;
//import org.nfunk.jep.ParseException;
//
//
///**
// * @author Sergey Krivov
// * 
// */
//public class Discretizer extends DomainDiscretizer {
//
//    /**
//     * 
//     */
//    public Discretizer() {// TODO Auto-generated constructor stub
//    }
//
//    public static BeliefNetwork discretizeNetwork(BeliefNetwork bn) throws Exception {
//
//        Set<BNNode> nodes = bn.vertexSet();
//
//        for (BNNode node : nodes) {
//            createDiscreteDomain(node);
//        }
//
//        for (BNNode node : nodes) {
//            node.setDiscreteCPT(discretizePFunction(node, bn));
//        }
//
//        return bn;
//
//    }
//
//    private static CPT discretizePFunction(BNNode node, BeliefNetwork bn)
//        throws Exception {
//        switch (node.getNodeType()) {
//        case probabilistic:
//            return discretizeProbabilisticCPF(node, bn);
//
//        case noisymax:
//            return discretizeProbabilisticCPF(node, bn);
//
//        case deterministic:
//            return discretizeDetF(node, bn);
//
//        case utility:
//            return discretizeUF(node, bn);
//
//        case decision:
//            return discretizeDF(node);
//
//        default:
//            return null;
//        }
//
//    }
//
//    private static UT discretizeUF(BNNode node, BeliefNetwork bn)
//        throws Exception {
//
//        IFunction cpf = node.getFunction();
//
//        if (cpf instanceof TabularFunction) {
//            return converttTabularToUT((TabularFunction) cpf);
//        } else {
//            return discretizeContUF(cpf, bn);
//        }
//    }
//
//    private static CPT discretizeProbabilisticCPF(BNNode node,
//            BeliefNetwork bn) throws ParseException {
//        IFunction function = node.getFunction();
//
//        if (function instanceof TabularFunction) {
//            return convertTabularToCPT((TabularFunction) function);
//        } else {
//            return discretizeContProbabilisticCPF(function, bn);
//        }
//    }
//
//    private static CPT discretizeDetF(BNNode node, BeliefNetwork bn)
//        throws Exception {
//
//        IFunction cpf = node.getFunction();
//
//        if (cpf instanceof TabularFunction) {
//
//            return convertTbaularDetFToCPT(node, bn);
//        } else {
//
//            return convertContDeterministicToCPT(node, bn);
//        }
//    }
//
//    private static UT discretizeContUF(IFunction cpf, BeliefNetwork bn)
//        throws Exception {
//        DiscretizationDomainMap dmap = new DiscretizationDomainMap(
//                cpf.getParentsDomains(), bn);
//        UT uT = new UT(cpf.getDomain().getName(),
//                dmap.getDiscretizedParentDomains());
//
//        int[] productStructureIterator = uT.index2addr(0);
//        boolean done = false;
//
//        while (!done) {
//            setOneOutputUTValue(productStructureIterator, uT, cpf, dmap);
//            done = uT.addOne(productStructureIterator);
//        }
//
//        return uT;
//    }
//
//    private static CPT convertTbaularDetFToCPT(BNNode node,
//            BeliefNetwork bn) {
//        PT funcTable = detFtoPT(node);
//
//        return funcTableToCPT(node.getDiscretizedDomain(), funcTable);
//		
//    }
//	
//    private static CPT convertContDeterministicToCPT(BNNode node,
//            BeliefNetwork bn) throws Exception {
//
//        PT funcTable = contDetFtoPT(node, bn);
//
//        return funcTableToCPT(node.getDiscretizedDomain(), funcTable);
//    }
//
//    private static PT detFtoPT(BNNode node) {
//        TabularFunction detf = (TabularFunction) node.getFunction();
//        PT pT = new PT(detf.getParentsDomains());
//        DiscreteDomain dom = (DiscreteDomain) node.getDomain();
//		
//        for (int i = 0; i < detf.size(); i++) {
//
//            String sstate = (String) detf.getValue(i);
//            int value = dom.findState(sstate);
//
//            pT.setValue(i, value);
//
//        }
//		
//        return pT;
//    }
//
//    private static PT contDetFtoPT(BNNode node, BeliefNetwork bn)
//        throws Exception {
//        IFunction detf = node.getFunction();
//        DiscretizationDomainMap dmap = new DiscretizationDomainMap(
//                detf.getParentsDomains(), bn);
//        PT pt = new PT(dmap.getDiscretizedParentDomains());
//
//        int[] productStructureIterator = pt.index2addr(0);
//        boolean done = false;
//
//        while (!done) {
//            setOneOutputDTValue(node, productStructureIterator, pt, detf, dmap);
//            done = pt.addOne(productStructureIterator);
//        }
//        return pt;
//    }
//
//    private static CPT funcTableToCPT(DiscreteDomain dom, PT funcTable) {
//        CPT cpt = new CPT(dom, funcTable.getDomainProduct());
//
//        cpt.setAll(0.0);
//
//        int[] structureIterator = funcTable.index2addr(0);
//        boolean done = false;
//
//        while (!done) {
//            int domIndex = (int) funcTable.getValue(structureIterator);
//
//            cpt.setValue(
//                    TableFactory.getCptReference(domIndex, structureIterator), 1);
//            done = funcTable.addOne(structureIterator);
//        }
//
//        return cpt;
//    }
//
//    private static CPT discretizeDF(BNNode node) {
//        return new DT(node.getDiscretizedDomain());
//    }
//
//    private static CPT discretizeContProbabilisticCPF(IFunction function,
//            BeliefNetwork bn) throws ParseException {
//
//        DiscretizationDomainMap dm2 = new DiscretizationDomainMap(
//                function.getParentsDomains(), bn);
//
//        return discretizeContCPF(function, dm2, bn);
//
//    }
//
//    private static CPT discretizeContCPF(IFunction function,
//            DiscretizationDomainMap dmap, BeliefNetwork bn)
//        throws ParseException {
//
//        CPT cPT = new CPT(dmap.getDiscretizedDomain(function.getDomain(), bn),
//                dmap.getDiscretizedParentDomains());
//
//        int[] productStructureIterator = cPT.index2addr(0);
//        boolean done = false;
//
//        while (!done) {
//            setOneOutputProbabilisticTableValue(bn, productStructureIterator,
//                    cPT, function, dmap);
//            done = cPT.addOne(productStructureIterator);
//        }
//        return cPT;
//
//    }
//
//    // private static void setOneOutputUTValue(int[] productStructureIterator,
//    // PT ptOut, PF pf, DiscretizationDomainMap dmap) {
//    // Node expression = pf.getValue(dmap
//    // .getProjectionFirst(productStructureIterator));
//    // XJep jep = PF.getJep();
//    // Vector<ContinuousDomain> doms = dmap.getSecondContinuousDomains();
//    // Vector<IntervalDomain> intdoms = dmap.getSecondDiscretizedDomains();
//    //
//    // int[] refs = dmap.getProjectionSecond(productStructureIterator);
//    //
//    // for (int i = 0; i < refs.length; i++) {
//    // int j = refs[i];
//    // String varname = doms.elementAt(i).getName();
//    // IntervalDomain idom = intdoms.elementAt(i);
//    // jep.addVariable(varname, idom.getAvarage(j));
//    // }
//    //
//    // try {
//    // double value = Double.parseDouble(jep.evaluate(expression)
//    // .toString());
//    // ptOut.setValue(productStructureIterator, value);
//    // } catch (NumberFormatException e) {
//    // // TODO Auto-generated catch block
//    // e.printStackTrace();
//    // } catch (ParseException e) {
//    // // TODO Auto-generated catch block
//    // e.printStackTrace();
//    // }
//    //
//    // }
//
//    private static void setOneOutputUTValue(int[] productStructureIterator,
//            PT ptOut, IFunction function, DiscretizationDomainMap dmap)
//        throws Exception {
//
//        Vector<DiscreteDomain> pdoms = dmap.getDiscretizedParentDomains();
//        List args = new LinkedList();
//
//        for (int i = 0; i < productStructureIterator.length; i++) {
//            int j = productStructureIterator[i];
//            DiscreteDomain ddom = pdoms.elementAt(i);
//
//            if (ddom instanceof IntervalDomain) {
//                IntervalDomain idom = (IntervalDomain) ddom;
//
//                Double aval = new Double(idom.getAvarage(j));
//
//                args.add(aval);
//
//            } else {
//                String sval = ddom.getState(i);
//
//                args.add(sval);
//            }
//
//        }
//
//        double value = (Double) function.getValue(args);
//
//        ptOut.setValue(productStructureIterator, value);
//
//    }
//
//    private static void setOneOutputDTValue(BNNode node,
//            int[] productStructureIterator, PT ptOut, IFunction function,
//            DiscretizationDomainMap dmap) throws Exception {
//        Vector<DiscreteDomain> pdoms = dmap.getDiscretizedParentDomains();
//        List args = new LinkedList();
//
//        for (int i = 0; i < productStructureIterator.length; i++) {
//            int j = productStructureIterator[i];
//            DiscreteDomain ddom = pdoms.elementAt(i);
//
//            if (ddom instanceof IntervalDomain) {
//                IntervalDomain idom = (IntervalDomain) ddom;
//
//                Double aval = new Double(idom.getAvarage(j));
//
//                args.add(aval);
//
//            } else {
//                String sval = ddom.getState(i);
//
//                args.add(sval);
//            }
//
//        }
//
//        double value = (Double) function.getValue(args);
//
//        if (node.getDiscretizedDomain() instanceof IntervalDomain) {
//            int index = ((IntervalDomain) node.getDiscretizedDomain()).getStateIndex(
//                    value);
//
//            // System.out.println(node.getDiscretizedDomain().getStates());
//            // System.out.println(value);
//            // System.out.println(index);
//            ptOut.setValue(productStructureIterator, index);
//        } else {
//            ptOut.setValue(productStructureIterator, value);
//        }
//
//    }
//
//    // private static void setOneOutputProbabilisticTableValue(
//    // int[] productStructureIterator, PT ptOut, PF pf,
//    // DiscretizationDomainMap dmap) {
//    // Node expression = pf.getValue(dmap
//    // .getProjectionFirst(productStructureIterator));
//    // XJep jep = PF.getJep();
//    // Vector<ContinuousDomain> doms = dmap.getSecondContinuousDomains();
//    // Vector<IntervalDomain> intdoms = dmap.getSecondDiscretizedDomains();
//    //
//    // int[] refs = dmap.getProjectionSecond(productStructureIterator);
//    // double volume = 1;
//    // for (int i = 0; i < refs.length; i++) {
//    // int j = refs[i];
//    // String varname = doms.elementAt(i).getName();
//    // IntervalDomain idom = intdoms.elementAt(i);
//    // jep.addVariable(varname, idom.getAvarage(j));
//    // volume *= idom.getWidth(j);
//    // }
//    //
//    // try {
//    // double value = Double.parseDouble(jep.evaluate(expression)
//    // .toString());
//    // ptOut.setValue(productStructureIterator, value * volume);
//    // } catch (NumberFormatException e) {
//    // // TODO Auto-generated catch block
//    // e.printStackTrace();
//    // } catch (ParseException e) {
//    // // TODO Auto-generated catch block
//    // e.printStackTrace();
//    // }
//    //
//    // }
//
//    private static void setOneOutputProbabilisticTableValue(BeliefNetwork bn,
//            int[] productStructureIterator, PT ptOut, IFunction function,
//            DiscretizationDomainMap dmap) throws ParseException {
//
//        double volume = 1;
//
//        Object val;
//
//        int k = productStructureIterator[0];
//        DiscreteDomain ddom = dmap.getDiscretizedDomain(function.getDomain(), bn);
//
//        if (ddom instanceof IntervalDomain) {
//            IntervalDomain idom = (IntervalDomain) ddom;
//
//            val = new Double(idom.getAvarage(k));
//
//            volume *= idom.getWidth(k);
//
//        } else {
//            val = ddom.getState(k);
//
//        }
//
//        Vector<DiscreteDomain> pdoms = dmap.getDiscretizedParentDomains();
//        List args = new LinkedList();
//
//        for (int i = 1; i < productStructureIterator.length; i++) {
//            int j = productStructureIterator[i];
//            DiscreteDomain dpdom = pdoms.elementAt(i);
//
//            if (dpdom instanceof IntervalDomain) {
//                IntervalDomain idom = (IntervalDomain) dpdom;
//
//                Double aval = new Double(idom.getAvarage(j));
//
//                args.add(aval);
//                volume *= idom.getWidth(j);
//
//            } else {
//                String sval = dpdom.getState(j);
//
//                args.add(sval);
//            }
//
//        }
//
//        double density = ((ICondProbDistrib) function).getProb(args, val);
//
//        ptOut.setValue(productStructureIterator, density * volume);
//
//    }
//
//    public static CPT convertTabularToCPT(TabularFunction cpf) {
//
//        CPT pT = new CPT((DiscreteDomain) cpf.getDomain(),
//                cpf.getParentsDomains());
//		 
//        for (int i = 0; i < cpf.size(); i++) {
//            double value = (Double) cpf.getValue(i);
//
//            pT.setValue(i, value);
//        }
//
//        return pT;
//
//    }
//
//    public static UT converttTabularToUT(TabularFunction cpf) {
//
//        UT uT = new UT(cpf.getDomain().getName(), cpf.getParentsDomains());
//
//        for (int i = 0; i < cpf.size(); i++) {
//            double value = (Double) cpf.getValue(i);
//
//            uT.setValue(i, value);
//        }
//
//        return uT;
//
//    }
//
//}
