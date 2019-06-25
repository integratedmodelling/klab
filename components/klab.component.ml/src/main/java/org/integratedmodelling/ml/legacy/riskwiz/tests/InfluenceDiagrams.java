///**
// * InfluenceDiagrams.java
// * ----------------------------------------------------------------------------------
// * 
// * Copyright (C) 2008 www.integratedmodelling.org
// * Created: May 22, 2008
// *
// * ----------------------------------------------------------------------------------
// * This file is part of RiskWiz.
// * 
// * RiskWiz is free software; you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation; either version 3 of the License, or
// * (at your option) any later version.
// * 
// * RiskWiz is distributed in the hope that it will be useful,
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
// * @date      May 22, 2008
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
//import org.integratedmodelling.ml.legacy.riskwiz.influence.jensen.StrongJoinTreeCompiler;
//import org.integratedmodelling.ml.legacy.riskwiz.io.genie.GenieReader;
//import org.integratedmodelling.ml.legacy.riskwiz.io.xmlbif.XmlBifReader;
//import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTInferencePN;
//import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTSolver;
//import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTSolverPN;
//
//
///**
// * @author Sergey Krivov
// *
// */
//public class InfluenceDiagrams {
//	
//    public static void decisionGenie(String genieFile) {
//        // load network
//
//        GenieReader gReader = new GenieReader();
//        BeliefNetwork network;
//
//        try {
//            network = gReader.loadFromFile(genieFile);
//            if (network == null) {
//                System.out.println("Problems with loading the  network");
//                return;
//            }
//            JTSolver solver = new JTSolver();
//
//            solver.initialize(network, new StrongJoinTreeCompiler());
//		
//            // find the optimum actions
//            solver.Solve();	
//		
//            // show polices
//            Set<BNNode> nodes = network.vertexSet();
//
//            for (BNNode node : nodes) {
//                if (node.isDecision()) {
//                    System.out.println(
//                            node.getName() + ":\n"
//                            + solver.getPolicy(node.getDiscretizedDomain()).toString()
//                            + "\n");
//                }
//            }
//		
//        } catch (Exception e) {
//            System.out.println("Can't load network");
//            e.printStackTrace();
//        }
//
//    }
//	
//    public static void policyNetworkGenie(String genieFile) {
//        // load network
//
//        GenieReader gReader = new GenieReader();
//        BeliefNetwork network;
//
//        try {
//            network = gReader.loadFromFile(genieFile);
//            if (network == null) {
//                System.out.println("Problems with loading the  network");
//                return;
//            }
//            // need to use JTSolverPN to provide the policy network
//            JTSolverPN solver = new JTSolverPN();
//
//            solver.initialize(network, new StrongJoinTreeCompiler());
//		
//            // find the optimum actions
//            solver.Solve();	
//		
//            JTInferencePN inference = solver.getPolicyNetworkInference();
//		
//            inference.run();
//
//            // output inference results
//
//            Set<BNNode> nodes = network.vertexSet();
//
//            for (BNNode node : nodes) {
//                System.out.println(
//                        node.getName() + ":\n"
//                        + inference.getMarginal(node).toString() + "\n");
//            }
//		
//        } catch (Exception e) {
//            System.out.println("Can't load network");
//            e.printStackTrace();
//        }
//
//    }
//	
//    /*
//     * The following explains how to load network, solve influence diagram and output the
//     * inference results
//     */
//    public static void decision(String bifFile) {
//        // load network
//
//        XmlBifReader bifReader = new XmlBifReader();
//        BeliefNetwork network;
//
//        try {
//            network = bifReader.loadFromFile(bifFile);
//            if (network == null) {
//                System.out.println("Problems with loading the  network");
//                return;
//            }
//            JTSolver solver = new JTSolver();
//
//            solver.initialize(network, new StrongJoinTreeCompiler());
//		
//            // find the optimum actions
//            solver.Solve();	
//		
//            // show polices
//            Set<BNNode> nodes = network.vertexSet();
//
//            for (BNNode node : nodes) {
//                if (node.isDecision()) {
//                    System.out.println(
//                            node.getName() + ":\n"
//                            + solver.getPolicy(node.getDiscretizedDomain()).toString()
//                            + "\n");
//                }
//            }
//		
//        } catch (Exception e) {
//            System.out.println("Can't load network");
//            e.printStackTrace();
//        }
//
//    }
//	
//    public static void policyNetwork(String bifFile) {
//        // load network
//
//        XmlBifReader bifReader = new XmlBifReader();
//        BeliefNetwork network;
//
//        try {
//            network = bifReader.loadFromFile(bifFile);
//            if (network == null) {
//                System.out.println("Problems with loading the  network");
//                return;
//            }
//			
//            // need to use JTSolverPN to provide the policy network
//            JTSolverPN solver = new JTSolverPN();
//
//            solver.initialize(network, new StrongJoinTreeCompiler());
//		
//            // find the optimum actions
//            solver.Solve();	
//		
//            JTInferencePN inference = solver.getPolicyNetworkInference();
//		
//            inference.run();
//
//            // output inference results
//
//            Set<BNNode> nodes = network.vertexSet();
//
//            for (BNNode node : nodes) {
//                System.out.println(
//                        node.getName() + ":\n"
//                        + inference.getMarginal(node).toString() + "\n");
//            }
//		
//        } catch (Exception e) {
//            System.out.println("Can't load network");
//            e.printStackTrace();
//        }
//
//    }
//	
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        decisionGenie("examples/water.xdsl");
//
//    }
//
//}
