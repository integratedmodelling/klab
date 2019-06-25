//package org.integratedmodelling.ml.legacy.riskwiz.tests;
//
//
//import java.io.FileNotFoundException;
//import java.util.Set;
//
//import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
//import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
//import org.integratedmodelling.ml.legacy.riskwiz.inference.ls.JoinTreeCompiler;
//import org.integratedmodelling.ml.legacy.riskwiz.io.genie.GenieReader;
//import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTInference;
//import org.nfunk.jep.ParseException;
//
//
//public class Carbon {
//
//    public Carbon() {}
//
//    public void test() {
//        GenieReader gReader = new GenieReader();
//
//        try {
//            BeliefNetwork network = gReader.loadFromFile("examples/carbon3.xdsl");
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
//                System.out.println(
//                        node.getName() + ":\n" + node.getMarginal().toString()
//                        + "\n");
//            }
//            //
//            // PT de1 = TableFactory.createObservation(node1.getDomain(),
//            // "false");
//            // // normally this is better to use for seting evidence, there is
//            // easy
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
//    public void testJoinTree() {
//        GenieReader gReader = new GenieReader();
//
//        BeliefNetwork network = null;
//
//        try {
//            network = gReader.loadFromFile("examples/carbon3.xdsl");
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//
//            if (network == null) {
//                System.out.println("Can't run network");
//                return;
//            }
//        }
//    }
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        Carbon cb = new Carbon();
//
//        cb.test();
//
//    }
//
//}
