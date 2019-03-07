package org.integratedmodelling.ml.contextualizers;

import java.io.File;

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
	private IServiceCall classDiscretizer;
	String instancesExport = null;
	String rawInstancesExport = null;

	protected int MIN_INSTANCES_FOR_TRAINING = 5;
	
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

		if (instances.getInstances().isEmpty()) {
			context.getMonitor().warn("No instances in training set: cannot train Weka classifier");
			return ret;
		}
		
		if (instances.getInstances().size() < MIN_INSTANCES_FOR_TRAINING) {
			context.getMonitor().warn("Not enough instances in training set: cannot train Weka classifier");
			return ret;
		}

		/*
		 * Any exports requested
		 */
		if (instancesExport != null) {
			File export = Configuration.INSTANCE.getExportFile(instancesExport);
			instances.export(export, false);
			context.getMonitor().info("Weka: training set exported to " + export);
		}
		if (rawInstancesExport != null) {
			File export = Configuration.INSTANCE.getExportFile(rawInstancesExport);
			instances.export(export, true);
			context.getMonitor().info("Weka: untransformed training set exported to " + export);
		}

		/*
		 * Do the training
		 */
		context.getMonitor().info("Start training " + classifier + " classifier on " +  instances.size() + " instances");
		classifier.train(instances);
		context.getMonitor().info("Training completed successfully.");

		/*
		 * Produce the result using the resource adapter
		 */

		/*
		 * Export the resource if requested, including all discretization parameters to
		 * reconstruct the filters.
		 */

		/*
		 * Produce the model if requested
		 */
		if (context.getModel().isLearning()) {
			// our main output is a model artifact
		}

		return ret;
	}

}
