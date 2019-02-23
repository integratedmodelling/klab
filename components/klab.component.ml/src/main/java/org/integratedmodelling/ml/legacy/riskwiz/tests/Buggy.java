package org.integratedmodelling.ml.legacy.riskwiz.tests;


import java.io.FileInputStream;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.inference.ls.JoinTreeCompiler;
import org.integratedmodelling.ml.legacy.riskwiz.io.genie.GenieReader;
import org.integratedmodelling.ml.legacy.riskwiz.io.xmlbif.XmlBifReader;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTInference;


public class Buggy {

    /**
     * @param args
     */
    public static void main(String[] args) {
		
        GenieReader r = new GenieReader();
        XmlBifReader rb = new XmlBifReader();

        try {
        	
            System.out.println("ciao");
            BeliefNetwork bn = r.load(new FileInputStream("examples/water.xdsl"));
            // BeliefNetwork bn =rb.load(new FileInputStream("examples/sprinkler.xml"));

            JTInference inference = new JTInference();

            inference.initialize(bn, new JoinTreeCompiler());
            // inference.setObservation("Cloudy", "true");
            inference.run();
    		
            for (BNNode n : bn.vertexSet()) {
                System.out.println(n.getName() + ": " + n.getMarginal());
            }
        
        } catch (Exception e) {

            e.printStackTrace();
        }
        
    }

}
