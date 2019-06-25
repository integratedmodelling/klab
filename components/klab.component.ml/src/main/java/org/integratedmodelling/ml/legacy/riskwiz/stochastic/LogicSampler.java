///**
// * LogicSampling.java
// * ----------------------------------------------------------------------------------
// * 
// * Copyright (C) 2009 www.integratedmodelling.org
// * Created: Apr 28, 2009
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
// * @copyright 2009 www.integratedmodelling.org
// * @author    Sergey Krivov
// * @date      Apr 28, 2009
// * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
// * @link      http://www.integratedmodelling.org
// **/
//
//package org.integratedmodelling.ml.legacy.riskwiz.stochastic;
//
//
//import java.util.List;
//import java.util.Vector;
//
//import org.integratedmodelling.ml.legacy.riskwiz.Util;
//import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
//import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
//import org.integratedmodelling.ml.legacy.riskwiz.discretizer.DomainDiscretizer;
//import org.integratedmodelling.ml.legacy.riskwiz.domain.DomainFactory;
//import org.integratedmodelling.ml.legacy.riskwiz.pfunction.ICondProbDistrib;
//import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularFunction;
//import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
//import org.nfunk.jep.ParseException;
//
//
///**
// * @author Sergey Krivov
// * 
// */
//public class LogicSampler extends AbstractSampler {
//
//    protected int runs;
//    protected int precisionFactor = 1000;
//    protected Vector<BNNode> orderedNodes;
//
//    // protected int[] currentSamples;
//
//    /**
//     * 
//     */
//    public LogicSampler(BeliefNetwork bn) {
//        super(bn);
//        orderedNodes = AbstractSampler.topologicalOrder(bn);
//        createOrderedParents(bn);
//        // this is temporary
//        setStatespeceSize();
//        ;
//        runs = precisionFactor * statespaceSize;
//
//    }
//
//    // public void initialize() {
//    //
//    // orderedNodes=super.topologicalOrder();
//    // //currentSamples=new int[orderedNodes.size()];
//    //
//    // }
//    //
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.integratedmodelling.riskwiz.inference.IInference#run()
//     */
//    @Override
//	public void run() {
//        try {
//            DomainDiscretizer.discretize(bn);
//            initSamplesCounters();
//
//            for (int i = 0; i < runs; i++) {
//                sampleBN();
//            }
//            updateMarginals();
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    private void sampleBN() throws ParseException {
//        Util.initRandom(true);
//        for (int i = 0; i < orderedNodes.size(); i++) {
//            BNNode node = orderedNodes.elementAt(i);
//            Object aSample = sampleNode(node);
//            int iSample = mapToDiscreteDomain(node, aSample);
//
//            if (!isConsistent(node, iSample)) {
//                // reject all BN sample
//                return;
//            } else {
//                node.setCurrentSample(aSample);
//                node.setDiscretizedSample(iSample);
//            }
//
//        }
//
//        for (int i = 0; i < orderedNodes.size(); i++) {
//            BNNode node = orderedNodes.elementAt(i);
//
//            if (!node.hasEvidence()) {
//                int iNodeSample = node.getDiscretizedSample();
//
//                node.getSamplesCounter()[iNodeSample] += 1;
//            }
//
//        }
//
//    }
//
//    private Object sampleNode(BNNode node) throws ParseException {
//        List args;
//
//        if (node.getFunction() instanceof TabularFunction) {
//            args = getDiscreteArguments(node);
//        } else {
//            args = getArguments(node);
//        }
//
//        if (node.isProbabilistic()) {
//            return ((ICondProbDistrib) node.getFunction()).sampleVal(args);
//        } else if (node.isDeterministic()) {
//            return node.getFunction().getValue(args);
//
//        } else {
//            return null;
//        }
//    }
//
//    private void updateMarginals() {
//        for (int i = 0; i < orderedNodes.size(); i++) {
//            BNNode node = orderedNodes.elementAt(i);
//
//            if (!node.hasEvidence()) {
//                PT marginal = new PT(
//                        DomainFactory.createDomainProduct(
//                                node.getDiscretizedDomain()));
//                double[] counter = node.getSamplesCounter();
//
//                for (int j = 0; j < counter.length; j++) {
//                    double val = counter[j];
//
//                    marginal.setValue(j, val);
//                }
//                marginal.normalize();
//                node.setMarginal(marginal);
//            }
//        }
//    }
//
//    public int getPrecisionFactor() {
//        return precisionFactor;
//    }
//
//    public void setPrecisionFactor(int precisionFactor) {
//        this.precisionFactor = precisionFactor;
//    }
//	
//    public boolean isConsistent(BNNode node, int aSample) {
//        if (node.hasEvidence()) {
//            PT evidence = node.getEvidence();			 
//
//            // int[] query = new int[1];
//            // query[0]=aSample;
//            // if(evidence.getValue(query)!=1.0){
//            // return false;
//            // } else {
//            // return true;
//            // }	
//			 
//            if (evidence.getValue(aSample) != 1.0) {
//                return false;
//            } else {
//                return true;
//            }	
//			
//        } else {
//            return true;
//        }
//    }
//
//}
