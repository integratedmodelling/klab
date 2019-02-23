package org.integratedmodelling.ml.legacy.riskwiz.io;


import java.io.FileInputStream;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.io.genie.GenieReader;
import org.integratedmodelling.ml.legacy.riskwiz.io.genie.GenieWriter;
import org.integratedmodelling.ml.legacy.riskwiz.io.riskwiz.RiskWizWriter;
import org.integratedmodelling.ml.legacy.riskwiz.io.xmlbif.XmlBifReader;
import org.integratedmodelling.ml.legacy.riskwiz.io.xmlbif.XmlBifWriter;


public class Converter {
	
    public static void bifToGenie(String fileIn, String fileOut) {
        XmlBifReader r = new XmlBifReader();
        GenieWriter w = new  GenieWriter();

        try {
            BeliefNetwork bn = r.load(new FileInputStream(fileIn));
             
            System.out.println("----------------------------------");  
            w.save(System.out, bn);
            w.saveToFile(fileOut, bn);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
    public static void genieToBif(String fileIn, String fileOut) {
        GenieReader r = new GenieReader();
        XmlBifWriter w = new  XmlBifWriter();

        try {
            BeliefNetwork bn = r.load(new FileInputStream(fileIn));
             
            System.out.println("----------------------------------");  
            w.save(System.out, bn);
            w.saveToFile(fileOut, bn);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
    public static void genieToRiskwiz(String fileIn, String fileOut) {
        GenieReader r = new GenieReader();
        RiskWizWriter w = new  RiskWizWriter();

        try {
            BeliefNetwork bn = r.load(new FileInputStream(fileIn));
             
            System.out.println("----------------------------------");  
            w.save(System.out, bn);
            w.saveToFile(fileOut, bn);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
