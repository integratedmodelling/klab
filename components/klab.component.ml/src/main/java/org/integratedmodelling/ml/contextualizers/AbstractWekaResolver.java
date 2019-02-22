package org.integratedmodelling.ml.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.ml.context.WekaInstances;
import org.integratedmodelling.ml.context.WekaOptions;

import weka.classifiers.Classifier;

public abstract class AbstractWekaResolver<T extends Classifier> implements IResolver<IState> {

	Class<T> classifier;
	WekaOptions options;
	
	protected AbstractWekaResolver(Class<T> cls, IParameters<String> parameters) {
		this.classifier = cls;
		this.options = new WekaOptions(parameters);
	}
	
	@Override
	public IState resolve(IState ret, IComputationContext context) throws KlabException {

		WekaInstances instances = new WekaInstances(ret.getObservable(), context.getModel(), (IRuntimeContext)context);
		if (context.getModel().isLearning()) {
			
		}
		return ret;
	}

}
