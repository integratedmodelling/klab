package org.integratedmodelling.ml.legacy.riskwiz.tests;


import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.inference.ls.JoinTreeCompiler;
import org.integratedmodelling.ml.legacy.riskwiz.io.xmlbif.XmlBifWriter;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTInference;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularCPD;


public class CreatingNetwork {

    // how to create a belief network, manipulate its structure and save it into
    // a file
    public void waterNetwork() {
        // creating a network
        BeliefNetwork bn = new BeliefNetwork("MyNetwork");

        // creating a boolean node

        String[] boolDomain = { "true", "false" };

        BNNode bnodeHasRiver = new BNNode("HasRiver", boolDomain);
        // creating more general discrete node
        String[] HMLDomain = { "high", "medium", "low" };
        BNNode bnodeWaterSupply = new BNNode("WaterSupply", HMLDomain);

        // creating another discrete node, note new instance of class Discrete
        // has to be created,
        // though you can reuse the string array boolDomain
        BNNode bnodePolution = new BNNode("WaterSupply", boolDomain);

        // add nodes to the network
        bn.addBeliefNode(bnodeHasRiver);
        bn.addBeliefNode(bnodePolution);
        bn.addBeliefNode(bnodeWaterSupply);

        // connecting nodes
        bn.addEdge(bnodeHasRiver, bnodeWaterSupply);
        bn.addEdge(bnodePolution, bnodeWaterSupply);

        // save the created network
        String filename = "MyNetwork.xml";

        try {
            XmlBifWriter writer = new XmlBifWriter();

            writer.saveToFile(filename, bn);
            System.out.println("file MyNetwork.xml is saved...");
        } catch (Exception e) {
            System.out.println("Unable to save file:  " + filename);
        }

    }

    // this function creates network shown in the image sprinkler.jpg, see it in
    // the directory
    // api_example

    public void sprinklerNetwork() {

        BeliefNetwork bn = new BeliefNetwork();
        String[] boolDomain = { "true", "false" };

        BNNode bnodeCloudy = new BNNode("Cloudy", boolDomain);
        BNNode bnodeRain = new BNNode("Rain", boolDomain);
        BNNode bnodeSprinkler = new BNNode("Sprinkler", boolDomain);
        BNNode bnodeWetGrass = new BNNode("WetGrass", boolDomain);

        bn.addBeliefNode(bnodeRain);
        bn.addBeliefNode(bnodeCloudy);
        bn.addBeliefNode(bnodeWetGrass);
        bn.addBeliefNode(bnodeSprinkler);

        // note the order of execution of connect().
        // domains are added in sequence as parent nodes are connected
        bn.addEdge(bnodeCloudy, bnodeRain);
        bn.addEdge(bnodeCloudy, bnodeSprinkler);
        bn.addEdge(bnodeSprinkler, bnodeWetGrass);
        bn.addEdge(bnodeRain, bnodeWetGrass);

        // this is simple
        TabularCPD table = (TabularCPD) bnodeCloudy.getFunction();

        table.setValue(0, 0.5);
        table.setValue(1, 0.5);

        // see image sprinkler.jpg to make sense out of the order
        // plus mind the rder of execution of connect() above
        table = (TabularCPD) bnodeSprinkler.getFunction();
        table.setValue(0, 0.1);
        table.setValue(1, 0.5);
        table.setValue(2, 0.9);
        table.setValue(3, 0.5);

        // see image sprinkler.jpg to make sense out of the order
        table = (TabularCPD) bnodeRain.getFunction();
        table.setValue(0, 0.8);
        table.setValue(1, 0.2);
        table.setValue(2, 0.2);
        table.setValue(3, 0.8);

        // see image sprinkler.jpg to make sense out of the order
        table = (TabularCPD) bnodeWetGrass.getFunction();
        table.setValue(0, 0.99);
        table.setValue(1, 0.9);
        table.setValue(2, 0.9);
        table.setValue(3, 0.0);
        table.setValue(4, .01);
        table.setValue(5, 0.1);
        table.setValue(6, 0.1);
        table.setValue(7, 1.0);

        // Rain.setEvidence(new DiscreteEvidence((Discrete)Rain.getDomain(),
        // "true"));;
        // WetGrass.setEvidence(new
        // DiscreteEvidence((Discrete)Sprinkler.getDomain(), "true"));;

        // Rain.setEvidence(new PolyEvidence());
        // // WetGrass.setEvidence(new PolyEvidence());
        // System.out.println("Here are the CPFS");
        // System.out.println("r:" + bnodeCloudy.getName() + ":\n"
        // +  bnodeCloudy.getCPF().toString() );
        // System.out.println("r:" + bnodeRain.getName() + ":\n"
        // +  bnodeRain.getCPF().toString() );
        // System.out.println("r:" + bnodeSprinkler.getName() + ":\n"
        // +  bnodeSprinkler.getCPF().toString() );
        // System.out.println("r:" + bnodeWetGrass.getName() + ":\n"
        // +  bnodeWetGrass.getCPF().toString() );

		 
        JTInference inference = new JTInference();

        try {
            inference.initialize(bn, new JoinTreeCompiler());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        inference.run();

        // System.out.println("Here are the marginal after inference with Belief
        // Elimination");
        // System.out.println("r:"+ bnodeCloudy.getName() + ":\n" +
        // cpf2string(VE.queryMarginal(bnodeCloudy)));
        // System.out.println("r:"+ bnodeSprinkler.getName() + ":\n" +
        // cpf2string(VE.queryMarginal(bnodeSprinkler)));
        // System.out.println("r:"+ bnodeRain.getName() + ":\n" +
        // cpf2string(VE.queryMarginal(bnodeRain)));
        // System.out.println("r:"+ bnodeWetGrass.getName() + ":\n" +
        // cpf2string(VE.queryMarginal(bnodeWetGrass)));
		
        Set<BNNode> nodes1 = bn.vertexSet();

        for (BNNode node : nodes1) {
            System.out.println(
                    node.getName() + ":\n" + node.getMarginal().toString()
                    + "\n");
        }
        //
        System.out.println(
                "Here are the marginal after inference with Belief Elimination");
        System.out.println(
                "r:" + bnodeCloudy.getName() + ":\n"
                + inference.getMarginal(bnodeCloudy));
        System.out.println(
                "r:" + bnodeSprinkler.getName() + ":\n"
                + inference.getMarginal(bnodeSprinkler));
        System.out.println(
                "r:" + bnodeRain.getName() + ":\n"
                + inference.getMarginal(bnodeRain));
        System.out.println(
                "r:" + bnodeWetGrass.getName() + ":\n"
                + inference.getMarginal(bnodeWetGrass));

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        CreatingNetwork cn = new CreatingNetwork();

        // cn.waterNetwork();
        cn.sprinklerNetwork();
    }

}
