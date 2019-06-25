///**
// * NoisyText.java
// * ----------------------------------------------------------------------------------
// * 
// * Copyright (C) 2009 www.integratedmodelling.org
// * Created: Mar 4, 2009
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
// * @date      Mar 4, 2009
// * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
// * @link      http://www.integratedmodelling.org
// **/
//
//package org.integratedmodelling.ml.legacy.riskwiz.tests;
//
//
//import java.util.Set;
//
//import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
//import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
//import org.integratedmodelling.ml.legacy.riskwiz.inference.ls.JoinTreeCompiler;
//import org.integratedmodelling.ml.legacy.riskwiz.io.genie.GenieReader;
//import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTInference;
//
//
///**
// * @author Sergey Krivov
// *
// */
//
///*
// *  
// aestheticService_AestheticProximityUse.xdsl
// aestheticService_AestheticViewshedUse.xdsl
// aestheticService_ProximityToBeauty.xdsl
// aestheticService_SensoryEnjoyment.xdsl
// carbonService_AllPeopleEverywhere.xdsl
// carbonService_ClimateStability.xdsl
// */
//public class NoisyTest {
//	
//    public static void test(String file) {
//        GenieReader gReader = new GenieReader();
//
//        try {
//            BeliefNetwork network = gReader.loadFromFile(file);
//
//            if (network == null) {
//                System.out.println("Can't load network");
//                return;
//            }
//			
//            // BeliefNode carbon= network.getBeliefNode("ClimateStability");
//            // System.out.println(carbon.getCPF().toString());
//			
//            JTInference inference = new JTInference();
//
//            // JTCompilerDebugger deb = new JTCompilerDebugger();
//            // deb.doAll();
//			
//            inference.initialize(network, new JoinTreeCompiler());
//            inference.run();
//			 
//            Set<BNNode> nodes = network.vertexSet();
//
//            for (BNNode node : nodes) {
//				 
//                System.out.println(
//                        node.getName() + ":\n" + node.getMarginal().toString()
//                        + "\n");
//            }
//            //
//            // PT de1 = TableFactory.createObservation(node1.getDomain(), "false");
//            // // normally this is better to use for seting evidence, there is easy
//            // // way with observations
//            // inference.setEvidence("Cloudy", de1);
//            // inference.run();
//            // for (BeliefNode node : nodes) {
//            // System.out.println(node.getName() + ":\n"
//            // + node.getMarginal().toString() + "\n");
//            // }
//			 
//            // inference.retractEvidence("AnnualPrecipitation");
//            // inference.run();
//            // System.out
//            // .println("IInference Results after clearing the evidence");
//            // for (BeliefNode node : nodes) {
//            // System.out.println(node.getName() + ":\n"
//            // + node.getMarginal().toString() + "\n");
//            // }
//        } catch (Exception e) {
//            System.out.println("Can't run network");
//            e.printStackTrace();
//        }
//    }
//	
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        NoisyTest.test("examples/aries/aestheticService_ViewSink.xdsl");
//
//    }
//
//}
