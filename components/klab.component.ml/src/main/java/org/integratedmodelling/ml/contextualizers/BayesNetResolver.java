package org.integratedmodelling.ml.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.Scale;

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
public class BayesNetResolver extends AbstractWekaResolver<BayesNet> implements IExpression {

	private IComputationContext context;

	public BayesNetResolver() {}
	
	public BayesNetResolver(IParameters<String> parameters,IComputationContext context) {
		super(BayesNet.class, parameters, true);
		this.context = context;
	}

	@Override
	public IGeometry getGeometry() {
		// TODO check
		return ((Scale)context.getScale().at(ITime.INITIALIZATION)).asGeometry();
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new BayesNetResolver(parameters, context);
	}

}
