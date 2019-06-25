///**
// * RiskWizCarbon.java
// * ----------------------------------------------------------------------------------
// * 
// * Copyright (C) 2009 www.integratedmodelling.org
// * Created: Jan 28, 2009
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
// * @date      Jan 28, 2009
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
//import org.integratedmodelling.ml.legacy.riskwiz.io.riskwiz.RiskWizReader;
//import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTInference;
//
//
///**
// * @author Sergey Krivov
// *
// */
//public class RiskWizCarbon {
//
//    /**
//     * 
//     */
//    public RiskWizCarbon() {// TODO Auto-generated constructor stub
//    }
//	
//    public static void carbonTest() {
//        RiskWizReader gReader = new RiskWizReader();
//
//        try {
//            BeliefNetwork network = gReader.loadFromFile("examples/carbon5.rwz");
//
//            if (network == null) {
//                System.out.println("Can't load network");
//                return;
//            }
//            JTInference inference = new JTInference();
//
//            // JTCompilerDebugger deb = new JTCompilerDebugger();
//            // deb.doAll();
//            // //		
//            inference.initialize(network, new JoinTreeCompiler());
//            inference.run();
//		 
//            Set<BNNode> nodes = network.vertexSet();
//
//            for (BNNode node : nodes) {
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
//        RiskWizCarbon.carbonTest();
//
//    }
//
//}
