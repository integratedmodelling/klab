package org.integratedmodelling.ml.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.ml.context.WekaClassifier;
import org.integratedmodelling.ml.context.WekaInstances;
import org.integratedmodelling.ml.context.WekaOptions;

import weka.classifiers.Classifier;

public abstract class AbstractWekaResolver<T extends Classifier> implements IResolver<IState> {

	protected WekaClassifier classifier = null;
	protected WekaOptions options;
	boolean outputModel = false;
	private IServiceCall classDiscretizer;
	String instancesExport = null;
	String rawInstancesExport = null;

	protected AbstractWekaResolver() {
	}

	protected AbstractWekaResolver(Class<T> cls, IParameters<String> parameters, boolean requiresDiscretization) {
		this.options = new WekaOptions(cls, parameters);
		this.classifier = new WekaClassifier(cls, this.options);
		this.classDiscretizer = parameters.get("discretization", IServiceCall.class);
		this.instancesExport = parameters.get("instances", String.class);
		this.rawInstancesExport = parameters.get("rawinstances", String.class);
	}

	@Override
	public IState resolve(IState ret, IComputationContext context) throws KlabException {

		WekaInstances instances = new WekaInstances(ret, context.getModel(), (IRuntimeContext) context, true,
				classDiscretizer);
		if (context.getModel().isLearning()) {
			// our main output is a model artifact
			outputModel = true;
		}

		if (instancesExport != null) {
			instances.export(Configuration.INSTANCE.getExportFile(instancesExport), false);
		}
		if (rawInstancesExport != null) {
			instances.export(Configuration.INSTANCE.getExportFile(rawInstancesExport), true);
		}
		
		return ret;
	}

}
