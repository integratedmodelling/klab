package org.integratedmodelling.ml.adapters;

import java.util.Map;

import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.ml.context.WekaClassifier;
import org.integratedmodelling.ml.context.WekaInstances;

import weka.core.Instance;
import weka.core.SerializationHelper;
import weka.filters.Filter;

public class WekaEncoder implements IResourceEncoder {

    WekaClassifier classifier = null;
    WekaInstances  instances  = null;

    @Override
    public boolean isOnline(IResource resource) {
        return !resource.hasErrors();
    }

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder, IComputationContext context) {

        /*
         * load the classifier
         */
        this.classifier = new WekaClassifier(((Resource) resource)
                .getLocalFile("classifier.file"), resource.getParameters()
                        .get("classifier.probabilistic", "false").equals("true"));

        this.instances = new WekaInstances(context, resource.getDependencies().size());
        this.instances.admitNodata(resource.getParameters().get("submitNodata", "true").equals("true"));

        IState predictedState = null;
        if (context.getTargetArtifact() instanceof IState) {
            predictedState = ((IState) context.getTargetArtifact());
        } else {
            throw new IllegalStateException("Weka: the predicted observation is not a quality.");
        }

        /*
         * Set the predicted state parameters and discretizer in the instances. This must be
         * done as the first step so that attributes are created in the correct order.
         */
        Filter discretizer = null;
        if (resource.getParameters().containsKey("predicted.discretizer.file")) {
            try {
                discretizer = (Filter) SerializationHelper.read(((Resource) resource)
                        .getLocalFile("predicted.discretizer.file").toString());
            } catch (Exception e) {
                throw new KlabIOException(e);
            }
        }

        Range prange = Range.create(resource.getParameters().get("predicted.range", String.class));
        instances.setPredicted(context.getTargetName(), predictedState, discretizer);
        instances.setPredictedRange(prange);

        /*
         * Initialize the instances;
         * check ranges of learned instances and warn if our inputs are outside.
         */
        for (Attribute dependency : resource.getDependencies()) {

            IState state = context.getArtifact(dependency.getName(), IState.class);
            if (state == null) {
                continue;
            }

            discretizer = null;
            if (resource.getParameters()
                    .containsKey("predictor." + dependency.getName() + ".discretizer.file")) {
                try {
                    discretizer = (Filter) SerializationHelper.read(((Resource) resource)
                            .getLocalFile("predictor." + dependency.getName() + ".discretizer.file")
                            .toString());
                } catch (Exception e) {
                    throw new KlabIOException(e);
                }
            }
            /*
             * we may have less predictors than during training, so we put them in the original place
             * leaving any others as null. The index is the position in the instances, which starts at
             * 1 for the class attribute, so we subtract 2 to obtain the predictor index.
             */
            int index = Integer.parseInt(resource.getParameters()
                    .get("predictor." + dependency.getName() + ".index").toString()) - 2;

            instances.addPredictor(dependency.getName(), state, index, discretizer);

            StateSummary summary = Observations.INSTANCE.getStateSummary(state, context.getScale());
            Range original = Range.create(resource.getParameters()
                    .get("predictor." + dependency.getName() + ".range", String.class));
            Range actual = Range.create(summary.getRange());
            if (!original.contains(actual)) {
                context.getMonitor()
                        .warn("predictor " + dependency.getName()
                                + " has values outside the training range: original = " + original
                                + ", predictor = " + actual);
            }
        }

        /*
         * Initialize the instances
         */
        instances.initializeForPrediction(((Resource) resource).getLocalFile("instances.file"));

        /*
         * proceed to inference
         */
        for (long offset = 0; offset < context.getScale().size(); offset++) {
            ILocator locator = context.getScale().getLocator(offset);
            Instance instance = instances.getInstance(locator);
            if (instance != null) {
                setValue(offset, classifier.predict(instance, context
                        .getMonitor()), builder, resource, /* ACHTUNG probably wrong - should serialize the data key? */predictedState
                                .getDataKey());
            }
        }
    }

    private void setValue(long offset, Object prediction, Builder target, IResource resource, IDataKey dataKey) {

        if (prediction instanceof double[]) {

            // predicted state must be discretized
            // FIXME this could be a categorical state without discretization
            EnumeratedRealDistribution distribution = new EnumeratedRealDistribution(instances
                    .getPredictedDiscretization().getMidpoints(), (double[]) prediction);

            if (resource.getType() == IArtifact.Type.NUMBER) {
                target.add(distribution.getNumericalMean(), offset);
            } else {
                // find the most likely class
                int val = NumberUtils.indexOfLargest((double[]) prediction);
                if (resource.getType() == IArtifact.Type.BOOLEAN) {
                    target.add(val == 0 ? Boolean.FALSE : Boolean.TRUE, offset);
                } else if (resource.getType() == IArtifact.Type.CONCEPT) {
                    target.add(dataKey.lookup(val), offset);
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
                target.add(prediction, offset);
            } else if (resource.getType() == IArtifact.Type.BOOLEAN) {
                target.add(((Number) prediction).intValue() == 0 ? Boolean.FALSE : Boolean.TRUE, offset);
            } else if (resource.getType() == IArtifact.Type.CONCEPT) {
                target.add(dataKey.lookup(((Number) prediction).intValue()), offset);
            }

        }
    }

}
