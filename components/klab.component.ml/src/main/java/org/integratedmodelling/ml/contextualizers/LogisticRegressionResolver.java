package org.integratedmodelling.ml.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;

import weka.classifiers.functions.Logistic;

/**
 * <pre>
 * These are the command line options of LogisticRegression.
Options specific to weka.classifiers.functions.Logistic:


 -D
  Turn on debugging output.
 
 -R <ridge>
  Set the ridge in the log-likelihood.
 
 -M <number>
  Set the maximum number of iterations (default -1, until convergence).


 * </pre>
 *
 * @author Tarik
 *
 */
public class LogisticRegressionResolver extends AbstractWekaResolver<Logistic> implements IExpression {

//	private IContextualizationScope context;

	public LogisticRegressionResolver() {
	}

	public LogisticRegressionResolver(IParameters<String> parameters, IContextualizationScope context) {
		super(Logistic.class, checkDefaults(parameters), context.getTargetSemantics(), true, true, false);
//		this.context = context;
	}

	private static IParameters<String> checkDefaults(IParameters<String> parameters) {

		/*
		 * Both search and estimator parameters are mandatory. This way we enable
		 * defaults.
		 */
		/*
		 * if (!parameters.containsKey("search")) { parameters.put("search",
		 * KimServiceCall.create("weka.bayes.k2", "maxparents", 3)); } if
		 * (!parameters.containsKey("estimator")) { parameters.put("estimator",
		 * KimServiceCall.create("weka.bayes.simpleestimator", "alpha", 1.0)); }
		 */

		return parameters;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new LogisticRegressionResolver(parameters, context);
	}

}
