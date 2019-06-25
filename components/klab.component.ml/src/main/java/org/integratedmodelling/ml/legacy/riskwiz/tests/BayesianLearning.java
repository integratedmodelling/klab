///**
// * BayesianLearning.java
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
//import org.integratedmodelling.ml.legacy.riskwiz.inference.ls.JoinTreeCompiler;
//import org.integratedmodelling.ml.legacy.riskwiz.io.xmlbif.XmlBifReader;
//import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTInference;
//import org.integratedmodelling.ml.legacy.riskwiz.learning.bndata.GraphDataFile;
//import org.integratedmodelling.ml.legacy.riskwiz.learning.parameter.bayes.BayesianLearner;
//
//
///**
// * @author Sergey Krivov
// *
// */
//public class BayesianLearning {
//	
//    /*
//     * The following explains how to load network, run inference and output the
//     * inference results, than learn from data
//     */
//    public static void learn(String networkFile, String dataFile) {
//		
//        XmlBifReader bifReader = new XmlBifReader();
//        BeliefNetwork network;
//
//        try {
//            // load network and show the marginals
//            network = bifReader.loadFromFile(networkFile);
//            if (network == null) {
//                System.out.println("Problems with loading the  network");
//                return;
//            }
//
//            Set<BNNode> nodes = network.vertexSet();
//
//            System.out.println("CPTs of original network\n");
//			
//            for (BNNode node : nodes) {
//                System.out.println(
//                        node.getName() + ":\n" + node.getFunction().toString()
//                        + "\n");
//            }
//		
//            // run inference
//            JTInference inference = new JTInference();
//
//            inference.initialize(network, new JoinTreeCompiler());
//            inference.run();
//
//            // output inference results
//            System.out.println("Marginals of original network\n");
//            for (BNNode node : nodes) {
//                System.out.println(
//                        node.getName() + ":\n" + node.getMarginal().toString()
//                        + "\n");
//            }
//			
//            // learning starts here
//			
//            // create a new learner
//            BayesianLearner learner = new BayesianLearner();
//
//            // you need to initialize the link between the learner and network
//            // this initialization will clear existing
//            // probability tables from the network and set up uniform Dirichlet priors
//            learner.initialize(network);
//			
//            // System.out.println("CPTs Before Learning \n");
//            //
//            // for (BeliefNode node : nodes) {
//            // System.out.println(node.getName() + ":\n"
//            // + node.getTable().toString() + "\n");
//            // }
//			
//            // create a new data source for the learner
//			
//            GraphDataFile graphData = new GraphDataFile();
//			
//            // now, populate the data source, in this case from file
//            graphData.readArff(dataFile);
//			
//            // you need to connect it too, which will help 
//            // the instance IGraphData to understand how to 
//            // format dta so that they fit the network
//            // graphData.connect(network);
//			
//            // finally, learn!
//            learner.learnFromTable(graphData);
//			
//            inference = new JTInference();
//            inference.initialize(network, new JoinTreeCompiler());
//            inference.run();
//            // now, show the probabilities again
//            // nodes = network.vertexSet();
//            System.out.println("CPTs after learning\n");
//            nodes = network.vertexSet(); 
//            for (BNNode node : nodes) {
//                System.out.println(
//                        node.getName() + ":\n" + node.getFunction().toString()
//                        + "\n");
//            }
//			
//            System.out.println("Marginal Probabilities after learning: \n\n");
//            for (BNNode node : nodes) {
//                System.out.println(
//                        node.getName() + ":\n" + node.getMarginal().toString()
//                        + "\n");
//            }
//			
//        } catch (Exception e) {
//			 
//            e.printStackTrace();
//        }
//			
//    }
//	
//    /*
//     * The following explains how to learn starting from the priors corresponding existing CPT
//     * of the nodes in network. The difference between this function and previous one 
//     * is just one line: learner.initializeWithPriors(network, NofVirtualSamples);
//     */
//    public static void learnWithPriors(String networkFile, String dataFile, int NofVirtualSamples) {
//		
//        XmlBifReader bifReader = new XmlBifReader();
//        BeliefNetwork network;
//
//        try {
//            // load network and show the marginals
//            network = bifReader.loadFromFile(networkFile);
//            if (network == null) {
//                System.out.println("Problems with loading the  network");
//                return;
//            }
//
//            Set<BNNode> nodes = network.vertexSet();
//
//            System.out.println("CPTs of original network\n");
//			
//            for (BNNode node : nodes) {
//                System.out.println(
//                        node.getName() + ":\n" + node.getFunction().toString()
//                        + "\n");
//            }
//		
//            // run inference
//            JTInference inference = new JTInference();
//
//            inference.initialize(network, new JoinTreeCompiler());
//            inference.run();
//
//            // output inference results
//            System.out.println("Marginals of original network\n");
//            for (BNNode node : nodes) {
//                System.out.println(
//                        node.getName() + ":\n" + node.getMarginal().toString()
//                        + "\n");
//            }
//			
//            // learning starts here
//			
//            // create a new learner
//            BayesianLearner learner = new BayesianLearner();
//
//            // you need to initialize the link between the learner and network
//            // this initialization will clear existing
//            // probability tables from the network and set up uniform Dirichlet priors
//            learner.initializeWithPriors(network, NofVirtualSamples);
//			
//            System.out.println("CPTs Before Learning \n");
//			 
//            for (BNNode node : nodes) {
//                System.out.println(
//                        node.getName() + ":\n" + node.getFunction().toString()
//                        + "\n");
//            }
//			
//            // create a new data source for the learner
//			
//            GraphDataFile graphData = new GraphDataFile();
//			
//            // now, populate the data source, in this case from file
//            graphData.readArff(dataFile);
//			
//            // you need to connect it too, which will help 
//            // the instance IGraphData to understand how to 
//            // format dta so that they fit the network
//            graphData.connect(network);
//			
//            // finally, learn!
//            learner.learnFromTable(graphData);
//			
//            inference = new JTInference();
//            inference.initialize(network, new JoinTreeCompiler());
//            inference.run();
//            // now, show the probabilities again
//            // nodes = network.vertexSet();
//            System.out.println("CPTs after learning\n");
//            nodes = network.vertexSet(); 
//            for (BNNode node : nodes) {
//                System.out.println(
//                        node.getName() + ":\n" + node.getFunction().toString()
//                        + "\n");
//            }
//			
//            System.out.println("Marginal Probabilities after learning: \n\n");
//            for (BNNode node : nodes) {
//                System.out.println(
//                        node.getName() + ":\n" + node.getMarginal().toString()
//                        + "\n");
//            }
//			
//        } catch (Exception e) {
//			 
//            e.printStackTrace();
//        }
//			
//    }
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//		
//        learn("examples/asia/asia.xml", "examples/asia/asia1000data-3.arff");
//		
//        // learnWithPriors( "examples/asia/asia.xml", "examples/asia/asia1000data-3.arff", 10000000);
//
//
//    }
//
//}
