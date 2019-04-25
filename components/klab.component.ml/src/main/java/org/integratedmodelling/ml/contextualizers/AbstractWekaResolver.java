package org.integratedmodelling.ml.contextualizers;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nullable;

import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.data.encoding.StandaloneResourceBuilder;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.ml.context.WekaClassifier;
import org.integratedmodelling.ml.context.WekaInstances;
import org.integratedmodelling.ml.context.WekaInstances.DiscretizerDescriptor;
import org.integratedmodelling.ml.context.WekaOptions;

import weka.classifiers.Classifier;
import weka.core.Attribute;
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
	private String resourceId = null;

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
		this.resourceId = parameters.get("resource", String.class);
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
		IResource resource = null;
		if (context.getModel().isLearning() || resourceId != null) {
			resource = buildResource(instances, context);
		}

		/*
		 * Produce the model if requested
		 */
		if (context.getModel().isLearning() && resource != null) {
			// our main output is a model artifact
		}

		return ret;
	}

	private IResource buildResource(WekaInstances instances, IComputationContext context) {

		if (resourceId == null) {
			resourceId = NameGenerator.newName("weka");
		}

		IProject project = context.getModel().getNamespace().getProject();
		if (project == null) {
			throw new IllegalStateException("Weka: cannot write a resource from a model that is not part of a project");
		}

		/*
		 * Geometry will be the coverage of the dataflow or, if global, S2T1 reflecting the extents
		 * in the training context.
		 */
		Scale scale = ((Scale) ((IRuntimeContext) context).getDataflow().getCoverage());
		Geometry geometry = scale == null ? Geometry.create(context.getScale().getTime() == null ? "S2" : "S2T1")
				: scale.asGeometry();

		StandaloneResourceBuilder builder = new StandaloneResourceBuilder(project, resourceId);
		builder
			.withResourceVersion(Version.create("0.0.1"))
			.withGeometry(geometry)
			.withAdapterType("weka")
			.withType(instances.getPredicted().getType())
			.withParameter("classifier", classifier.getClassifier().getClass().getCanonicalName())
			.withParameter("classifier:options", classifier.getOptions().toString());

		for (Attribute attribute : instances.getAttributes()) {

			if (attribute.name().equals(instances.getPredicted().getObservable().getLocalName())) {
				continue;
			}

			IState state = instances.getPredictor(attribute.name());
			builder.withDependency(attribute.name(), state.getType(), true, true);
			DiscretizerDescriptor descriptor = instances.getDiscretization(attribute.name());
			if (descriptor != null) {
				try {
					File discretizer = File.createTempFile("d_" + attribute.name(), ".bin");
					descriptor.export(discretizer);
					builder.addFile(discretizer);
					builder.withParameter("predictor:" + attribute.name() + ":discretizer", descriptor.getJavaClass())
							.withParameter("predictor:" + attribute.name() + ":discretizer:file",
									MiscUtilities.getFileName(discretizer))
							.withParameter("predictor:" + attribute.name() + ":discretizer:options",
									descriptor.getOptions());
				} catch (IOException e) {
					throw new KlabIOException(e);
				}
			}
		}

		try {

			context.getMonitor().info("exporting " + resourceId + " resource in project " + project.getName());

			File dataset = File.createTempFile("instances", ".arff");
			File dataraw = File.createTempFile("rawinstances", ".arff");
			File clmodel = File.createTempFile("classifier", ".bin");

			instances.export(dataset, false);
			instances.export(dataraw, false);
			classifier.export(clmodel);

			builder.addFile(dataset);
			builder.addFile(dataraw);
			builder.addFile(clmodel);

			/*
			 * TODO add all metadata including those about the training, execution,
			 * validation etc.
			 */

		} catch (IOException e) {
			throw new KlabIOException(e);
		}

		/*
		 * build the resource using the session to notify clients.
		 */
		return builder.build(context.getMonitor().getIdentity().getParentIdentity(ISession.class));
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
				// TODO categorical distribution should use Shannon - redo with original
				// distribution
				uncertainty.set(locator,
						Math.sqrt(distribution.getNumericalVariance()) / distribution.getNumericalMean());
			}

		} else {
			if (target.getObservable().getArtifactType() == IArtifact.Type.NUMBER) {
				target.set(locator, prediction);
			} else if (target.getObservable().getArtifactType() == IArtifact.Type.BOOLEAN) {
				target.set(locator, ((Number) prediction).intValue() == 0 ? Boolean.FALSE : Boolean.TRUE);
			} else if (target.getObservable().getArtifactType() == IArtifact.Type.CONCEPT) {
				target.set(locator, target.getDataKey().lookup(((Number) prediction).intValue()));
			}

		}
	}

}
