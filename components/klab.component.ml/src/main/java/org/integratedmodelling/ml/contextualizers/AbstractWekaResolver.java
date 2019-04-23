package org.integratedmodelling.ml.contextualizers;

import java.io.File;

import javax.annotation.Nullable;

import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.ml.context.WekaClassifier;
import org.integratedmodelling.ml.context.WekaInstances;
import org.integratedmodelling.ml.context.WekaOptions;

import weka.classifiers.Classifier;
import weka.core.Instance;

public abstract class AbstractWekaResolver<T extends Classifier> implements IResolver<IState> {

	protected WekaClassifier classifier = null;
	protected WekaOptions options;
	private IServiceCall classDiscretizer;
	String instancesExport = null;
	String rawInstancesExport = null;
	protected int MIN_INSTANCES_FOR_TRAINING = 5;
	protected boolean predictionIsProbabilistic;
	private boolean admitsNodata;

	protected AbstractWekaResolver() {
	}

	protected AbstractWekaResolver(Class<T> cls, IParameters<String> parameters, boolean requiresDiscretization,
			boolean predictionIsProbabilistic, boolean admitsNodata) {
		this.options = new WekaOptions(cls, parameters);
		this.classifier = new WekaClassifier(cls, this.options, predictionIsProbabilistic);
		this.classDiscretizer = parameters.get("discretization", IServiceCall.class);
		this.instancesExport = parameters.get("instances", String.class);
		this.rawInstancesExport = parameters.get("rawinstances", String.class);
		this.admitsNodata = admitsNodata;
	}

	@Override
	public IState resolve(IState ret, IComputationContext context) throws KlabException {

		/*
		 * check if we're asking for uncertainty
		 */
		IState uncertainty = null;
		for (int i = 1; i < context.getModel().getObservables().size(); i++) {
			IObservable obs = context.getModel().getObservables().get(i);
			if (obs.is(Type.UNCERTAINTY)
					&& ret.getObservable().getType().is(Observables.INSTANCE.getInherentType(obs))) {
				uncertainty = context.getArtifact(obs.getLocalName(), IState.class);
			}
		}

		WekaInstances instances = new WekaInstances(ret, context.getModel(), (IRuntimeContext) context, true,
				admitsNodata, classDiscretizer);

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
		context.getMonitor().info("Start training " + classifier + " classifier on " + instances.size() + " instances");
		classifier.train(instances);
		context.getMonitor().info("Training completed successfully.");

		/*
		 * Produce the result using the classifier.
		 */
		for (ILocator locator : ret.getScale()) {

			Instance instance = instances.getInstance(locator);
			if (instance != null) {
				setValue(instances, locator, classifier.predict(instance, context.getMonitor()), ret, uncertainty);
			}
		}

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

	private void setValue(WekaInstances instances, ILocator locator, Object prediction, IState target,
			@Nullable IState uncertainty) {
		if (prediction instanceof double[]) {

			// predicted state must be discretized
			// FIXME this could be a categorical state without discretization
			EnumeratedRealDistribution distribution = new EnumeratedRealDistribution(
					instances.getPredictedDiscretization().getMidpoints(), (double[]) prediction);

			if (target.getObservable().getArtifactType() == IArtifact.Type.NUMBER) {
				target.set(locator, distribution.getNumericalMean());
			} else {
				// find the most likely class
				int val = NumberUtils.indexOfLargest((double[]) prediction);
				if (target.getObservable().getArtifactType() == IArtifact.Type.BOOLEAN) {
					target.set(locator, val == 0 ? Boolean.FALSE : Boolean.TRUE);
				} else if (target.getObservable().getArtifactType() == IArtifact.Type.CONCEPT) {
					target.set(locator, target.getDataKey().lookup(val));
				}
			}

			if (uncertainty != null) {
				// TODO categorical distribution should use Shannon - redo with original distribution
				uncertainty.set(locator, Math.sqrt(distribution.getNumericalVariance())/distribution.getNumericalMean());
			}

		} else {
			if (target.getObservable().getArtifactType() == IArtifact.Type.NUMBER) {
				target.set(locator, prediction);
			} else if (target.getObservable().getArtifactType() == IArtifact.Type.BOOLEAN) {
				target.set(locator, ((Number)prediction).intValue() == 0 ? Boolean.FALSE : Boolean.TRUE);
			} else if (target.getObservable().getArtifactType() == IArtifact.Type.CONCEPT) {
				target.set(locator, target.getDataKey().lookup(((Number)prediction).intValue()));
			}

		}
	}

}
