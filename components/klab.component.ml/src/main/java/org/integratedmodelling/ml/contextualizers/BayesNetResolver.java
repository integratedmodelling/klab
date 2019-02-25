package org.integratedmodelling.ml.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;

import weka.classifiers.bayes.BayesNet;

/**
 * <pre>
 * These are the command line options of BayesNet.
Options specific to weka.classifiers.bayes.BayesNet:

-D
        Do not use ADTree data structure

-B <BIF file>
        BIF file to compare with

-Q weka.classifiers.bayes.net.search.SearchAlgorithm
        Search algorithm

-E weka.classifiers.bayes.net.estimate.SimpleEstimator
        Estimator algorithm
The search algorithm option -Q and estimator option -E options are mandatory.

Note that it is important that the -E options should be used after the -Q option. Extra options can be passed to the search algorithm and the estimator after the class name specified following '-'.

For example:

java weka.classifiers.bayes.BayesNet -t iris.arff -D \
  -Q weka.classifiers.bayes.net.search.local.K2 -- -P 2 -S ENTROPY \
  -E weka.classifiers.bayes.net.estimate.SimpleEstimator -- -A 1.0
 * </pre>
 *
 * @author Ferd
 *
 */
public class BayesNetResolver extends AbstractWekaResolver<BayesNet> {

	protected BayesNetResolver(IParameters<String> parameters) {
		super(BayesNet.class, parameters, true);
	}

	@Override
	public IGeometry getGeometry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
