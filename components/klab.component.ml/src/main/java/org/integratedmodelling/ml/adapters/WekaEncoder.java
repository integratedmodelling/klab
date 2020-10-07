package org.integratedmodelling.ml.adapters;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
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
	IState uncertaintyState = null;

	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {
		return !resource.hasErrors();
	}

	/**
	 * This creates non-semantic observables and uses stored datakeys. Use only when
	 * building offline calculators.
	 * 
	 * @param resource
	 */
	public void initialize(IResource resource) {

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

		this.instances = new WekaInstances(null, resource.getInputs().size());
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

		IObservable predictedObservable = Observable
				.promote(OWL.INSTANCE.getNonsemanticPeer("predicted", resource.getType())).named("predicted");
		// setPredicted() will need the datakey, so we call this first to allow
		// setDatakey() to work, then call the actual method later. Bit involved.
		instances.setPredictedObservable(predictedObservable);

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

		Range prange = Range.create(resource.getParameters().get("predicted.range", String.class));
		instances.setPredicted("predicted", predictedObservable, null, discretizer);
		instances.setPredictedRange(prange);

		/*
		 * Initialize the instances; check ranges of learned instances and warn if our
		 * inputs are outside.
		 */
		for (Attribute dependency : resource.getInputs()) {

			discretizer = null;
			if (resource.getParameters().containsKey("predictor." + dependency.getName() + ".discretizer.file")) {
				try {
					discretizer = (Filter) SerializationHelper.read(((Resource) resource)
							.getLocalFile("predictor." + dependency.getName() + ".discretizer.file").toString());
				} catch (Exception e) {
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

			instances.addPredictor(dependency.getName(),
					Observable.promote(OWL.INSTANCE.getNonsemanticPeer(dependency.getName(), dependency.getType()))
							.named(dependency.getName()),
					null, index, discretizer);

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
		}

		/*
		 * Initialize the instances and set up missing data filter
		 */
		instances.initializeForPrediction(((Resource) resource).getLocalFile("instances.file"));
		classifier.setTrainingDataset(instances);
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

		if (context.getModel() != null) {
			for (int i = 1; i < context.getModel().getObservables().size(); i++) {
				IObservable observable = context.getModel().getObservables().get(i);
				if (observable.getName().endsWith("_uncertainty") || observable.is(Type.UNCERTAINTY)
						&& Observables.INSTANCE.getDescribedType(observable.getType())
								.equals(context.getModel().getObservables().get(0).getType())) {
					this.uncertaintyState = context.getArtifact(observable.getType(), IState.class);
				}
			}
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

			/*
			 * we may have less predictors than during training, so we put them in the
			 * original place leaving any others as null. The index is the position in the
			 * instances, which starts at 1 for the class attribute, so we subtract 2 to
			 * obtain the predictor index.
			 */
			int index = Integer.parseInt(
					resource.getParameters().get("predictor." + dependency.getName() + ".index").toString()) - 2;

			instances.addPredictor(dependency.getName(), state.getObservable(), state, index, discretizer);

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

			StateSummary summary = Observations.INSTANCE.getStateSummary(state, context.getScale());
			String range = resource.getParameters().get("predictor." + dependency.getName() + ".range", String.class);
			if (range != null) {
				//
				Range original = Range.create(range);
				Range actual = Range.create(summary.getRange());
				if (!original.contains(actual)) {
					context.getMonitor()
							.warn("predictor " + dependency.getName()
									+ " has values outside the training range: original = " + original
									+ ", predictor = " + actual);
				}
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

		Set<String> sperma = new HashSet<>();
		
		/*
		 * proceed to inference
		 */
		for (ILocator locator : context.getScale()) {
			Instance instance = instances.getInstance(locator);
			if (instance != null) {
				Object result = classifier.predict(instance, context.getMonitor());
				if (!sperma.contains(instance.toString())) {
					System.out.println(instance + " -> " + Arrays.toString((double[])result));
					sperma.add(instance.toString());
				}
				setValue(locator, result, builder, resource,
						instances.getDatakey("predicted"));
			}
		}
	}

	private void setValue(ILocator locator, Object prediction, Builder target, IResource resource, IDataKey dataKey) {

		if (prediction instanceof double[]) {

			// a numeric predicted state must be discretized
			if (resource.getType() == IArtifact.Type.NUMBER) {
				EnumeratedRealDistribution distribution = new EnumeratedRealDistribution(
						instances.getPredictedDiscretization().getMidpoints(), (double[]) prediction);
				target.add(distribution.getNumericalMean(), locator);

				if (this.uncertaintyState != null) {
					/*
					 * TODO categorical distribution should use Shannon - redo with original
					 * distribution
					 */
					/*
					 * FIXME this uses the state directly, which breaks the resource's contract in
					 * case the adapter is used remotely. Must set the builder up for additional
					 * outputs instead.
					 */
					this.uncertaintyState.set(locator,
							Math.sqrt(distribution.getNumericalVariance()) / distribution.getNumericalMean());
				}

			} else {
//				// find the most likely class. TODO give the option of stochastic inference.
//				EnumeratedDistribution<IConcept> concept = new EnumeratedDistribution<IConcept>(
//						makeProbabilities(dataKey, (double[]) prediction));
				int val = NumberUtils.indexOfLargest((double[]) prediction);
				if (resource.getType() == IArtifact.Type.BOOLEAN) {
					target.add(val == 0 ? Boolean.FALSE : Boolean.TRUE, locator);
				} else if (resource.getType() == IArtifact.Type.CONCEPT) {
					target.add(dataKey.lookup(val), locator);
				}
			}

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
