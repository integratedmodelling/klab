package org.integratedmodelling.ml.legacy.riskwiz.tests;
// package org.integratedmodelling.riskwiz.tests;
//
// import org.integratedmodelling.riskwiz.bn.BeliefNetwork;
// import org.integratedmodelling.riskwiz.io.genie.GenieReader;
// import org.integratedmodelling.riskwiz.io.genie.GenieWriter;
// import org.integratedmodelling.riskwiz.learning.bndata.GraphDataSourceAAIGrid;
// import org.integratedmodelling.riskwiz.learning.bndata.IDiscretizer;
// import org.integratedmodelling.riskwiz.learning.parameter.bayes.BayesianLearner;
//
// public class CarbonLearning {
//
// public static void learnWithPriors( ) {
//
// GenieReader gReader = new GenieReader();
// try {
//
// //change the directory as necessary
// BeliefNetwork network = gReader
// .loadFromFile("examples/carbon3.xdsl");
// if (network == null) {
// System.out.println("Can't load network");
// return;
// }
//
//
//
// //create a new learner
// BayesianLearner learner= new BayesianLearner();
//
// learner.initializeWithPriors(network, 1000);
//
// CarbonDiscretizerFactory discretizer= CarbonDiscretizerFactory.newDiscretizer() ;
//
// //			//change the directory as necessary, delete and remove files as necessary
//
// String[] aaiGridFiles = 
// new String[] {"examples/GrowingSeason.asc", 
// "examples/SoilDepth.asc",
// "examples/AnnualPrecipitation.asc",
// "examples/VegetationType.asc" ,
// "examples/SoilCSequestration.asc", 
// "examples/VegetativeCSequestration.asc",
// "examples/SoilCStorage.asc",
// "examples/VegetativeCStorage.asc"};
//
// String[] varNames = 
// new String[] {"GrowingSeason",
// "SoilDepth", 
// "AnnualPrecipitation",
// "VegetationType", 
// "SoilCSequestration", 
// "VegetativeCSequestration" ,
// "SoilCStorage",
// "VegetativeCStorage"};
// GraphDataSourceAAIGrid graphData = new  GraphDataSourceAAIGrid( aaiGridFiles,   varNames, discretizer );
//
//
//
// //you need to connect it too, which will help 
// //the instance IGraphData to understand how to 
// //format dta so that they fit the network
// graphData.connect(network);
//
// //finally, learn!
// learner.learnFromDataSource(graphData);
//
// GenieWriter w= new  GenieWriter();
// w.saveToFile("examples/carbon-learned.xdsl", learner.getFinalResult());
//
// } catch (Exception e) {
//
// e.printStackTrace();
// }
//
//
//
// }
//
//
//
// /**
// * @param args
// */
// public static void main(String[] args) {
//
//
//
// learnWithPriors( );
//
//
// }
//
//
// }
