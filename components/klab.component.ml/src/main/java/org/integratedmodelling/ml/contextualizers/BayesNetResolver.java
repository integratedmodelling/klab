package org.integratedmodelling.ml.contextualizers;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;

import weka.classifiers.bayes.BayesNet;

public class BayesNetResolver extends AbstractWekaResolver<BayesNet> {

	protected BayesNetResolver(BayesNet classifier) {
		super(classifier);
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
