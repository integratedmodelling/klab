package org.integratedmodelling.ml.legacy.riskwiz.tests;


import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.debugger.JTCompilerDebugger;
import org.integratedmodelling.ml.legacy.riskwiz.inference.ls.JoinTreeCompiler;
import org.integratedmodelling.ml.legacy.riskwiz.io.xmlbif.XmlBifReader;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTInference;
import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.TableFactory;


/**
 * @author Sergey Krivov
 * 
 */

public class LoadingNetwork {

    /*
     * The following explains how to load network, run inference and output the
     * inference results
     */
    public void sprinkler1() {
        // load network

        XmlBifReader bifReader = new XmlBifReader();
        BeliefNetwork network;

        try {
            network = bifReader.loadFromFile("examples/sprinkler.xml");
            if (network == null) {
                System.out.println("Problems with loading the  network");
                return;
            }
            // run inference
            JTInference inference = new JTInference();
            JTCompilerDebugger deb = new JTCompilerDebugger();

            deb.doAll();
			
            inference.initialize(network, new JoinTreeCompiler(), deb);
            inference.run();

            // output inference results

            Set<BNNode> nodes = network.vertexSet();

            for (BNNode node : nodes) {
                System.out.println(
                        node.getName() + ":\n" + node.getMarginal().toString()
                        + "\n");
            }
        } catch (Exception e) {
            System.out.println("Can't load network");
            e.printStackTrace();
        }

    }

    /*
     * This functions loads a network, then do examination of the network nodes.
     * After that run inference and output the inference results (but this time
     * in different way) which does excactly the same examination of nodes in
     * the infered network
     */
    public void sprinkler2() {
        XmlBifReader bifReader = new XmlBifReader();
        BeliefNetwork network = null;

        try {
            network = bifReader.loadFromFile("examples/sprinkler.xml");
            if (network == null) {
                System.out.println("Problems with processing the  network");
                return;
            }
        } catch (Exception e) {
            System.out.println("Can't load network");
            e.printStackTrace();
        }
		
        System.out.println("CPTs Before IInference \n");
        Set<BNNode> nodes = network.vertexSet();

        for (BNNode node : nodes) {
            System.out.println(
                    node.getName() + ":\n" + node.getFunction().toString()
                    + "\n");
        }
			
        // run inference
        JTInference inference = new JTInference();
        JTCompilerDebugger deb = new JTCompilerDebugger();

        deb.doAll();
			
        try {
            inference.initialize(network, new JoinTreeCompiler(), deb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        inference.run();
        // output inference results
        System.out.println("Marginals After IInference  \n");
			
        Set<BNNode> nodes1 = network.vertexSet();

        for (BNNode node : nodes1) {
            System.out.println(
                    node.getName() + ":\n" + node.getMarginal().toString()
                    + "\n");
        }
			
    }

    /*
     * This functions shows how to set evidence on a node. Yet another way to
     * render results is used
     * 
     */

    public void DryGrass() {
        XmlBifReader bifReader = new XmlBifReader();
		 
        BeliefNetwork network = bifReader.loadFromFile("examples/sprinkler.xml");

        if (network == null) {
            System.out.println("Can't load network");
            return;
        }

        JTInference inference = new JTInference();
        JTCompilerDebugger deb = new JTCompilerDebugger();

        deb.doAll();
			
        try {
            inference.initialize(network, new JoinTreeCompiler(), deb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        Set<BNNode> nodes = network.vertexSet();

        // creating new evidence
        BNNode node1 = network.getBeliefNode("Cloudy");

        inference.setObservation(node1, "false"); 
        // much better way to do just the same as in last two lines
        // inference.setObservation("Cloudy", "false");
			
			
        inference.run();

        System.out.println("Marginals After IInference  \n");
        for (BNNode node : nodes) {
            System.out.println(
                    node.getName() + ":\n" + node.getMarginal().toString()
                    + "\n");
        }
		 
    }

    /*
     * This functions shows how to manipulate evidence information
     * 
     */

    public void sprinklerWGev() {
        XmlBifReader bifReader = new XmlBifReader();

        try {
            BeliefNetwork network = bifReader.loadFromFile(
                    "examples/sprinkler.xml");

            if (network == null) {
                System.out.println("Can't load network");
                return;
            }
            JTInference inference = new JTInference();
            JTCompilerDebugger deb = new JTCompilerDebugger();

            deb.doAll();
			
            inference.initialize(network, new JoinTreeCompiler(), deb);
            BNNode node1;

            node1 = network.getBeliefNode("Cloudy");
			
            // alternative way to set observation, it should work for more general
            // situation where evidence is not just observation but a probability distribution 
            PT de = TableFactory.createObservation(node1.getDiscretizedDomain(),
                    "false");

            // normally this is better to use   for seting evidence, there is easy way with observations
            inference.setEvidence("Cloudy", de);
            inference.run();
            Set<BNNode> nodes = network.vertexSet();

            for (BNNode node : nodes) {
                System.out.println(
                        node.getName() + ":\n" + node.getMarginal().toString()
                        + "\n");
            }
			
            PT de1 = TableFactory.createObservation(node1.getDiscretizedDomain(),
                    "false");

            // normally this is better to use   for seting evidence, there is easy way with observations
            inference.setEvidence("Cloudy", de1);
            inference.run();
            for (BNNode node : nodes) {
                System.out.println(
                        node.getName() + ":\n" + node.getMarginal().toString()
                        + "\n");
            }
            // here is how we clear evidence, after this the probability for each
            // value
            // is taken from the specified CPF
            inference.retractEvidence("Cloudy");
            inference.run();
            System.out.println("IInference Results after clearing the evidence");
            for (BNNode node : nodes) {
                System.out.println(
                        node.getName() + ":\n" + node.getMarginal().toString()
                        + "\n");
            }
        } catch (Exception e) {
            System.out.println("Can't load network");
            e.printStackTrace();
        }		
    }

    /**
     * @param args
     *            no arguments options folks, run this from your java IDE
     */
    public static void main(String[] args) {
        LoadingNetwork ln = new LoadingNetwork();

        // comment out what you do not want to test
        ln.sprinkler1();
        // ln.sprinkler2();
        // ln.DryGrass();
        // ln.sprinklerWGev();

    }

}
