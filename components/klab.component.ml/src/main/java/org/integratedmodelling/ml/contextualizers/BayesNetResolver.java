package org.integratedmodelling.ml.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;

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

Note that it is important that the -E options should be used after the -Q option. Extra options 
can be passed to the search algorithm and the estimator after the class name specified following '-'.

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

	private IContextualizationScope context;

	public BayesNetResolver() {}
	
	public BayesNetResolver(IParameters<String> parameters,IContextualizationScope context) {
		super(BayesNet.class, checkDefaults(parameters), true, true, false);
		this.context = context;
	}

	private static IParameters<String> checkDefaults(IParameters<String> parameters) {

		/*
		 * Both search and estimator parameters are mandatory. This way we enable defaults.
		 */
		if (!parameters.containsKey("search")) {
			parameters.put("search", KimServiceCall.create("weka.bayes.k2", "maxparents", 3));
		}
		if (!parameters.containsKey("estimator")) {
			parameters.put("estimator", KimServiceCall.create("weka.bayes.simpleestimator", "alpha", 1.0));
		}
		
		return parameters;
	}

//	@Override
//	public IGeometry getGeometry() {
//		// TODO check
//		return ((Scale)context.getScale().at(ITime.INITIALIZATION)).asGeometry();
//	}
//
	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new BayesNetResolver(parameters, context);
	}

}
