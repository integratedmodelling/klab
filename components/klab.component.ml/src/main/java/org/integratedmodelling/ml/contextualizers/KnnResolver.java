package org.integratedmodelling.ml.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;

import weka.classifiers.lazy.IBk;

/**
 * <pre>
 * These are the command line options of Knn.
Options specific to weka.classifiers.lazy.IBk:

 -I
  Weight neighbours by the inverse of their distance
  (use when k > 1)
 -F
  Weight neighbours by 1 - their distance
  (use when k > 1)
 -K <number of neighbors>
  Number of nearest neighbours (k) used in classification.
  (Default = 1)
 -E
  Minimise mean squared error rather than mean absolute
  error when using -X option with numeric prediction.
 -W <window size>
  Maximum number of training instances maintained.
  Training instances are dropped FIFO. (Default = no window)
 -X
  Select the number of nearest neighbours between 1
  and the k value specified using hold-one-out evaluation
  on the training data (use when k > 1)
 -A
  The nearest neighbour search algorithm to use (default: weka.core.neighboursearch.LinearNNSearch).

 * </pre>
 *
 * @author Tarik
 *
 */
public class KnnResolver extends AbstractWekaResolver<IBk> implements IExpression {

//	private IContextualizationScope context;

	public KnnResolver() {
	}

	public KnnResolver(IParameters<String> parameters, IContextualizationScope context) {
		super(IBk.class, forDefaults(parameters), context.getTargetSemantics(), true, false, false);
//		this.context = context;
	}
	// change this method to for knn parameters    
	private static IParameters<String> forDefaults(IParameters<String> parameters) {

		/*
		 * search and estimator parameters are mandatory. This way we enable defaults.
		 */
		if (!parameters.containsKey("search")) {
			parameters.put("search", KimServiceCall.create("weka.bayes.k2", "maxparents", 3));
		}
		if (!parameters.containsKey("estimator")) {
			parameters.put("estimator", KimServiceCall.create("weka.bayes.simpleestimator", "alpha", 1.0));
		}
		
		return parameters;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new KnnResolver(parameters, context);
	}

}
