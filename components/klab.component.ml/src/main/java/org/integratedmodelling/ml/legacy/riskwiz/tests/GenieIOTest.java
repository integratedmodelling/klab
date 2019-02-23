package org.integratedmodelling.ml.legacy.riskwiz.tests;


import java.io.FileInputStream;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.io.genie.GenieReader;
import org.integratedmodelling.ml.legacy.riskwiz.io.genie.GenieWriter;


public class GenieIOTest {
	
    /*
     aestheticService_AestheticProximityUse.xdsl
     aestheticService_AestheticViewshedUse.xdsl
     aestheticService_ProximityToBeauty.xdsl
     aestheticService_SensoryEnjoyment.xdsl
     carbonService_AllPeopleEverywhere.xdsl
     carbonService_ClimateStability.xdsl
     */

    /**
     * @param args
     */
    public static void main(String[] args) {
        GenieReader r = new GenieReader();
        GenieWriter w = new  GenieWriter();

        try {
            BeliefNetwork bn = r.load(
                    new FileInputStream("examples/CarbonSourceValue.xdsl"));
             
            System.out.println("----------------------------------");  
            w.save(System.out, bn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
