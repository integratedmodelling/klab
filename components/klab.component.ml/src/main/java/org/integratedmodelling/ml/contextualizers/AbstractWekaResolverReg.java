package org.integratedmodelling.ml.contextualizers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.GeometryBuilder;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.data.encoding.StandaloneResourceBuilder;
import org.integratedmodelling.klab.data.storage.MergingState;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.ml.context.WekaRegressor;
import org.integratedmodelling.ml.context.WekaInstances;
import org.integratedmodelling.ml.context.WekaInstances.DiscretizerDescriptor;
import org.integratedmodelling.ml.context.WekaOptions;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;

public abstract class AbstractWekaResolverReg<T extends Classifier> extends AbstractContextualizer
        implements
            IResolver<IState>,
            IDocumentationProvider {

    protected WekaRegressor classifier = null;
    protected WekaOptions options;
    private IServiceCall classDiscretizer;
    String instancesExport = null;
    String rawInstancesExport = null;
    protected int MIN_INSTANCES_FOR_TRAINING = 5;
    protected boolean predictionIsProbabilistic;
    private boolean admitsNodata;
    private String resourceId = null;
    private String learnedGeometry = null;
    private List<IDocumentationProvider.Item> documentation = new ArrayList<>();

    private IKimExpression selector;
    private double selectFraction = Double.NaN;
    protected IObservable targetObservable;

    // private Bagging bagging = null;

    protected AbstractWekaResolverReg() {
    }

    public IArtifact.Type getType() {
        return targetObservable.getArtifactType();
    }

    protected AbstractWekaResolverReg(Class<T> cls, IParameters<String> parameters, IObservable observable,
            boolean requiresDiscretization, boolean predictionIsProbabilistic, boolean admitsNodata) {
        this.options = new WekaOptions(cls, parameters);
        this.targetObservable = observable;
        this.classifier = new WekaRegressor(cls, this.options, predictionIsProbabilistic);
        this.classDiscretizer = parameters.get("discretization", IServiceCall.class);
        this.instancesExport = parameters.get("instances", String.class);
        this.rawInstancesExport = parameters.get("rawinstances", String.class);
        this.admitsNodata = admitsNodata;
        this.resourceId = parameters.get("resource", String.class);
        this.learnedGeometry = parameters.get("geometry", String.class);
        this.selector = parameters.get("select", IKimExpression.class);
        this.selectFraction = parameters.get("sample", this.selector == null ? Double.NaN : 1.0);
    }

    protected AbstractWekaResolverReg(Classifier cls, IParameters<String> parameters, IObservable observable,
            boolean requiresDiscretization, boolean predictionIsProbabilistic, boolean admitsNodata) {
        // this.bagging = cls;
        this.options = new WekaOptions(cls./* getClassifier(). */getClass(), parameters);
        this.targetObservable = observable;
        this.classifier = new WekaRegressor(cls, this.options, predictionIsProbabilistic);
        this.classDiscretizer = parameters.get("discretization", IServiceCall.class);
        this.instancesExport = parameters.get("instances", String.class);
        this.rawInstancesExport = parameters.get("rawinstances", String.class);
        this.admitsNodata = admitsNodata;
        this.resourceId = parameters.get("resource", String.class);
        this.learnedGeometry = parameters.get("geometry", String.class);
        this.selector = parameters.get("select", IKimExpression.class);
        this.selectFraction = parameters.get("sample", this.selector == null ? Double.NaN : 1.0);
    }

    @Override
    public IState resolve(IState ret, IContextualizationScope context) throws KlabException {

        /*
         * check if we're asking for uncertainty
         */
        IState uncertainty = null;
        for (int i = 1; i < context.getModel().getObservables().size(); i++) {
            IObservable obs = context.getModel().getObservables().get(i);
            if (obs.getType().is(Type.UNCERTAINTY)
                    && ret.getObservable().getType().is(Observables.INSTANCE.getDescribedType(obs.getType()))) {
                uncertainty = context.getArtifact(obs.getName(), IState.class);
            }
        }

        WekaInstances instances = new WekaInstances(ret, context.getModel(), (IRuntimeScope) context, false, admitsNodata,
                    classDiscretizer, selector, selectFraction);

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
        documentation.addAll(classifier.train(instances));
        context.getMonitor().info("Training completed successfully.");

        if (!ret.isArchetype()) {
            /*
             * if it's distributed w/o @distribute it should create a merging state to substitute
             * ret.
             */
            if (((Model) context.getModel()).learnsWithinArchetype() && !((Model) context.getModel()).distributesLearning()) {
                ret = MergingState.promote(ret, context.getObservations(((Model) context.getModel()).getArchetype().getType()));
            } else {
                for (ILocator locator : ret.getScale()) {
                    Instance instance = instances.getInstance(locator);
                    if (instance != null) {
                        setValue(instances, locator, classifier.predict(instance, context.getMonitor()), ret, uncertainty);
                    }
                }
            }
        }

        /*
         * Export the resource if requested, including all discretization parameters to reconstruct
         * the filters.
         */
        IResource resource = null;
        if (context.getModel().isLearning() || resourceId != null) {
            resource = buildResource(instances, context);
            context.getMonitor().info("local resource '" + resource.getUrn() + "' contains the learned model");
        }

        return ret;
    }

    private void setValue(WekaInstances instances, ILocator locator, Object prediction, IState target,
            @Nullable IState uncertainty) {

        if (prediction instanceof double[]) {

            // predicted state must be discretized unless it's not numeric
            EnumeratedRealDistribution distribution = null;
            if (target.getObservable().getArtifactType().isNumeric()) {
                distribution = new EnumeratedRealDistribution(instances.getPredictedDiscretization().getMidpoints(),
                        (double[]) prediction);
            }

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
                uncertainty.set(locator, Math.sqrt(distribution.getNumericalVariance()) / distribution.getNumericalMean());
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

    private IResource buildResource(WekaInstances instances, IContextualizationScope context) {

        if (resourceId == null) {
            resourceId = "weka" + NameGenerator.shortUUID();
        }

        IProject project = context.getModel().getNamespace().getProject();
        if (project == null) {
            throw new IllegalStateException("Weka: cannot write a resource from a model that is not part of a project");
        }

        /*
         * Geometry will be the coverage of the dataflow or, if global, S2T1 reflecting the extents
         * in the training context.
         */
        Scale scale = ((Scale) ((IRuntimeScope) context).getDataflow().getCoverage());
        Geometry geometry = null;
        if (learnedGeometry != null) {
            if ("coverage".equals(learnedGeometry)) {
                if (scale != null) {
                    geometry = scale.asGeometry();
                } else {
                    GeometryBuilder gb = Geometry.builder();
                    if (context.getScale().getSpace() != null) {
                        gb.space().generic();
                    }
                    if (context.getScale().getTime() != null) {
                        gb.time().generic();
                    }
                    geometry = gb.build();
                }
            } else {
                geometry = Geometry.create(learnedGeometry);
            }
        } else {
            /*
             * default: cover the learning context, using same geometry minus resolution and shape
             * if any.
             */
            geometry = ((Scale) context.getScale())
                    .asGeometry()/*
                                  * .withGridResolution(null).withTemporalResolution(null)
                                  * .withShape(null)
                                  */;
        }

        /*
         * FIXME NB: removing time for now.
         */
        // geometry = geometry.without(Dimension.Type.TIME);

        StandaloneResourceBuilder builder = new StandaloneResourceBuilder(project, resourceId);
        builder.withResourceVersion(Version.create("0.0.1")).withGeometry(geometry).withAdapterType("weka")
                .withType(instances.getPredictedObservable().getArtifactType())
                .withParameter("wekaVersion", weka.core.Version.VERSION).withParameter("model", context.getModel().getName())
                .withParameter("submitNodata", "true")
                .withParameter("classifier", classifier.getClassifier().getClass().getCanonicalName())
                .withParameter("classifier.options", classifier.getOptions().toString())
                .withParameter("classifier.probabilistic", classifier.isPredictionProbabilistic() ? "true" : "false");

        int i = 1;
        for (Attribute attribute : instances.getAttributes()) {

            boolean predicted = false;

            if (attribute.name().equals(instances.getPredictedObservable().getName())) {
                predicted = true;
            }

            IState state = predicted ? instances.getPredictedState() : instances.getPredictor(attribute.name());
            if (state != null && !state.isArchetype()) {

                // goes through here even when training on objects....
                StateSummary summary = Observations.INSTANCE.getStateSummary(state, context.getScale());
                if (!predicted) {
                    builder.withParameter("predictor." + attribute.name() + ".index", i).withDependency(attribute.name(),
                            state.getType(), true, true);
                } else {
                    builder.withParameter("predicted.index", i);
                }

                // ...hence this:
                Range range = instances.getDataRange(attribute.name());
                if (!range.isInfinite()) {
                    // trained on objects
                    builder.withParameter(predicted ? "predicted.range" : ("predictor." + attribute.name() + ".range"),
                            "[" + range.getLowerBound() + "," + range.getUpperBound() + "]");
                } else {
                    // trained on state
                    builder.withParameter(predicted ? "predicted.range" : ("predictor." + attribute.name() + ".range"),
                            "[" + summary.getRange().get(0) + "," + summary.getRange().get(1) + "]");
                }
            } else {

                IObservable observable = predicted
                        ? instances.getPredictedObservable()
                        : instances.getPredictorObservable(attribute.name());
                if (!predicted) {
                    builder.withParameter("predictor." + attribute.name() + ".index", i).withDependency(attribute.name(),
                            observable.getArtifactType(), true, true);
                } else {
                    builder.withParameter("predicted.index", i);
                }

                Range range = instances.getDataRange(observable.getName());
                if (!range.isInfinite()) {
                    builder.withParameter(predicted ? "predicted.range" : ("predictor." + attribute.name() + ".range"),
                            "[" + range.getLowerBound() + "," + range.getUpperBound() + "]");
                }
            }

            /*
             * if we have a key, serialize it to reconstruct it in inference. The output is
             * identified as "predicted" as we do not know which specific type it will be used to
             * predict. The predictor keys should be used to filter out input concepts that have not
             * been seen by the classifier.
             */
            List<String> key = instances.getDatakeyDefinitions(attribute.name());
            if (key != null) {
                try {
                    File keyfile = File.createTempFile("key_" + (predicted ? "predicted" : attribute.name()), ".dat");
                    FileUtils.writeLines(keyfile, key);
                    builder.addFile(keyfile);
                    builder.withParameter("key." + (predicted ? "predicted" : attribute.name()),
                            MiscUtilities.getFileName(keyfile));
                } catch (IOException e) {
                    throw new KlabIOException(e);
                }
            }

            DiscretizerDescriptor descriptor = instances.getDiscretization(attribute.name());
            if (descriptor != null) {
                try {

                    File discretizer = File.createTempFile("d_" + attribute.name(), ".bin");
                    descriptor.export(discretizer);
                    builder.addFile(discretizer);
                    double[] cutpoints = descriptor.getDiscretizationBreakpoints();

                    if (predicted) {
                        builder.withParameter("predicted.discretizer", descriptor.getJavaClass())
                                .withParameter("predicted.discretizer.file", MiscUtilities.getFileName(discretizer))
                                .withParameter("predicted.name", attribute.name())
                                .withParameter("predicted.discretizer.options", descriptor.getOptions());

                        if (cutpoints != null) {
                            builder.withParameter("predicted.discretizer.cutpoints", Arrays.toString(cutpoints));
                        }

                        if (classifier.isPredictionProbabilistic()) {
                            builder.withOutput("uncertainty", IArtifact.Type.NUMBER);
                        }

                    } else {

                        builder.withParameter("predictor." + attribute.name() + ".discretizer", descriptor.getJavaClass())
                                .withParameter("predictor." + attribute.name() + ".discretizer.file",
                                        MiscUtilities.getFileName(discretizer))
                                .withParameter("predictor." + attribute.name() + ".discretizer.options", descriptor.getOptions());

                        if (cutpoints != null) {
                            builder.withParameter("predictor." + attribute.name() + ".discretizer.cutpoints",
                                    Arrays.toString(cutpoints));
                        }

                        // TODO encode data key

                    }

                } catch (IOException e) {
                    throw new KlabIOException(e);
                }
            }

            i++;
        }

        try

        {

            context.getMonitor().info("exporting " + resourceId + " resource in project " + project.getName());

            File dataset = File.createTempFile("instances", ".arff");
            File dataraw = File.createTempFile("rawinstances", ".arff");
            File clmodel = File.createTempFile("classifier", ".bin");

            instances.export(dataset, false);
            instances.export(dataraw, true);
            classifier.export(clmodel);

            builder.addFile(dataset).addFile(dataraw).addFile(clmodel)
                    .withParameter("classifier.file", MiscUtilities.getFileName(clmodel))
                    .withParameter("instances.file", MiscUtilities.getFileName(dataset))
                    .withParameter("instances.file.raw", MiscUtilities.getFileName(dataraw));

            /*
             * TODO add all metadata including those about the training, execution, validation etc.
             * 
             * TODO include source code of learned model in resources. Needs to predict the URN in
             * the wrong place - ask it to the StandaloneBuilder so it's consistent.
             */

        } catch (IOException e) {
            throw new KlabIOException(e);
        }

        /*
         * build the resource using the session to notify clients.
         */
        return builder.build(context.getMonitor().getIdentity().getParentIdentity(ISession.class));
    }

    @Override
    public Collection<Item> getDocumentation() {
        return documentation;
    }

}
