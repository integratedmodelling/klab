package org.integratedmodelling.ml.adapters;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.ml.context.WekaClassifier;
import org.integratedmodelling.ml.context.WekaInstances;

import weka.core.Instance;
import weka.core.SerializationHelper;
import weka.filters.Filter;

public class WekaEncoder implements IResourceEncoder {

	WekaClassifier classifier = null;
	WekaInstances instances = null;
	boolean initialized = false;

	@Override
	public boolean isOnline(IResource resource) {
		return !resource.hasErrors();
	}

	public void initialize(IState predictedState, IResource resource, IContextualizationScope context) {
		/*
		 * load the classifier
		 */
		File imported = new File(((Resource) resource).getPath() + File.separator + "import.xml");
		if (imported.exists()) {
			this.classifier = new WekaClassifier(imported, resource.getParameters().get("classifier", String.class),
					resource.getParameters().get("classifier.probabilistic", "false").equals("true"));
		} else {
			this.classifier = new WekaClassifier(((Resource) resource).getLocalFile("classifier.file"),
					resource.getParameters().get("classifier.probabilistic", "false").equals("true"));
		}

		this.instances = new WekaInstances(context, resource.getInputs().size());
		this.instances.admitNodata(resource.getParameters().get("submitNodata", "true").equals("true"));

		/*
		 * Set the predicted state parameters and discretizer in the instances. This
		 * must be done as the first step so that attributes are created in the correct
		 * order.
		 */
		Filter discretizer = null;
		if (resource.getParameters().containsKey("predicted.discretizer.file")) {
			try {
				discretizer = (Filter) SerializationHelper
						.read(((Resource) resource).getLocalFile("predicted.discretizer.file").toString());
			} catch (Exception e) {
				throw new KlabIOException(e);
			}
		}

		Range prange = Range.create(resource.getParameters().get("predicted.range", String.class));
		instances.setPredicted(context.getTargetName(), predictedState.getObservable(), predictedState, discretizer);
		instances.setPredictedRange(prange);

		if (resource.getParameters().containsKey("key.predicted")) {
			// build output datakey
			try {
				File file = ((Resource) resource).getLocalFile("key.predicted");
				List<String> key = FileUtils.readLines(file);
				instances.setDatakey("predicted", key);
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
		}

		/*
		 * Initialize the instances; check ranges of learned instances and warn if our
		 * inputs are outside.
		 */
		for (Attribute dependency : resource.getInputs()) {

			IState state = context.getArtifact(dependency.getName(), IState.class);
			if (state == null) {
				continue;
			}

			discretizer = null;
			if (resource.getParameters().containsKey("predictor." + dependency.getName() + ".discretizer.file")) {
				try {
					discretizer = (Filter) SerializationHelper.read(((Resource) resource)
							.getLocalFile("predictor." + dependency.getName() + ".discretizer.file").toString());
				} catch (Exception e) {
					throw new KlabIOException(e);
				}
			}

			if (resource.getParameters().containsKey("key." + dependency.getName())) {
				// build predictor datakey
				try {
					File file = ((Resource) resource).getLocalFile("key." + dependency.getName());
					List<String> key = FileUtils.readLines(file);
					instances.setDatakey(dependency.getName(), key);
				} catch (IOException e) {
					throw new KlabIOException(e);
				}
			}

			/*
			 * we may have less predictors than during training, so we put them in the
			 * original place leaving any others as null. The index is the position in the
			 * instances, which starts at 1 for the class attribute, so we subtract 2 to
			 * obtain the predictor index.
			 */
			int index = Integer.parseInt(
					resource.getParameters().get("predictor." + dependency.getName() + ".index").toString()) - 2;

			instances.addPredictor(dependency.getName(), state.getObservable(), state, index, discretizer);

			StateSummary summary = Observations.INSTANCE.getStateSummary(state, context.getScale());
			Range original = Range
					.create(resource.getParameters().get("predictor." + dependency.getName() + ".range", String.class));
			Range actual = Range.create(summary.getRange());
			if (!original.contains(actual)) {
				context.getMonitor().warn("predictor " + dependency.getName()
						+ " has values outside the training range: original = " + original + ", predictor = " + actual);
			}
		}

		/*
		 * Initialize the instances and set up missing data filter
		 */
		instances.initializeForPrediction(((Resource) resource).getLocalFile("instances.file"));
		classifier.setTrainingDataset(instances);
	}

	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IContextualizationScope context) {

		IState predictedState = null;
		if (context.getTargetArtifact() instanceof IState) {
			predictedState = ((IState) context.getTargetArtifact());
		} else {
			throw new IllegalStateException("Weka: the predicted observation is not a quality.");
		}

		if (!initialized) {
			initialize(predictedState, resource, context);
		}

		/*
		 * proceed to inference
		 */
		for (ILocator locator : context.getScale()) {
			Instance instance = instances.getInstance(locator);
			if (instance != null) {
				setValue(locator, classifier.predict(instance, context.getMonitor()), builder, resource,
						instances.getDatakey("predicted"));
			}
		}
	}

	private void setValue(ILocator locator, Object prediction, Builder target, IResource resource, IDataKey dataKey) {

		if (prediction instanceof double[]) {

			// predicted state must be discretized
			// FIXME this could be a categorical state without discretization
			if (resource.getType() == IArtifact.Type.NUMBER) {
				EnumeratedRealDistribution distribution = new EnumeratedRealDistribution(
						instances.getPredictedDiscretization().getMidpoints(), (double[]) prediction);
				target.add(distribution.getNumericalMean(), locator);
			} else {
//				// find the most likely class
//				EnumeratedDistribution<IConcept> concept = new EnumeratedDistribution<IConcept>(
//						makeProbabilities(dataKey, (double[]) prediction));
				int val = NumberUtils.indexOfLargest((double[]) prediction);
				if (resource.getType() == IArtifact.Type.BOOLEAN) {
					target.add(val == 0 ? Boolean.FALSE : Boolean.TRUE, locator);
				} else if (resource.getType() == IArtifact.Type.CONCEPT) {
					target.add(dataKey.lookup(val), locator);
				}
			}

			// if (uncertainty != null) {
			// // TODO categorical distribution should use Shannon - redo with original
			// // distribution
			// uncertainty.set(locator, Math.sqrt(distribution.getNumericalVariance())
			// / distribution.getNumericalMean());
			// }

		} else {
			if (resource.getType() == IArtifact.Type.NUMBER) {
				target.add(prediction, locator);
			} else if (resource.getType() == IArtifact.Type.BOOLEAN) {
				target.add(((Number) prediction).intValue() == 0 ? Boolean.FALSE : Boolean.TRUE, locator);
			} else if (resource.getType() == IArtifact.Type.CONCEPT) {
				target.add(dataKey.lookup(((Number) prediction).intValue()), locator);
			}

		}
	}

}
