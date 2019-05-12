package org.integratedmodelling.ml.context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.Path;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.net.BIFReader;
import weka.classifiers.bayes.net.EditableBayesNet;
import weka.core.Instance;
import weka.core.OptionHandler;
import weka.core.SerializationHelper;

/**
 * TODO use text options and the Evaluation class to streamline access using
 * Weka conventions. May have to override Evaluation::evaluateModel(Classifier
 * classifier, String[] options) to structure outputs.
 * 
 * @author Ferd
 *
 */
public class WekaClassifier {

    private Classifier          classifier;
    private WekaOptions         options;
    private boolean             predictionIsProbabilistic;
    private boolean             errorWarning = false;
    private Map<String, Object> docs         = new HashMap<>();

    public WekaClassifier(Class<? extends Classifier> cls, WekaOptions options,
            boolean predictionIsProbabilistic) {
        this.classifier = Extensions.INSTANCE.createDefaultInstance(cls, Classifier.class);
        this.options = options;
        this.predictionIsProbabilistic = predictionIsProbabilistic;
        if (this.classifier instanceof OptionHandler) {
            try {
                ((OptionHandler) this.classifier).setOptions(options.getWekaOptions());
            } catch (Exception e) {
                throw new IllegalStateException("Weka: error setting options for " + cls + ": '" + options
                        + "': " + e.getMessage());
            }
        }
    }

    public WekaClassifier(File localFile, boolean predictionIsProbabilistic) {
        try {
            this.classifier = (Classifier) SerializationHelper.read(localFile.toString());
            this.predictionIsProbabilistic = predictionIsProbabilistic;
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }
    
    public WekaClassifier(File importFile, String classifierClass, boolean predictionIsProbabilistic) {
        try {
            if (BayesNet.class.getCanonicalName().equals(classifierClass)) {
                BIFReader bifReader = new BIFReader();
                bifReader.processString(FileUtils.readFileToString(importFile));
                this.classifier = new EditableBayesNet(bifReader);
            } else {
                throw new KlabIOException("Weka: don't know what to do with import file: " + importFile);
            }
            this.predictionIsProbabilistic = predictionIsProbabilistic;
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }

    /**
     * Train the model over a set of instances and evaluate the results. Set the
     * evaluation results into variables.
     * 
     * @param instances
     */
    public void train(WekaInstances instances) {

        try {

            instances.getInstances().setClassIndex(0);
            classifier.buildClassifier(instances.getInstances());
            Evaluation eval = new Evaluation(instances.getInstances());
            eval.evaluateModel(classifier, instances.getInstances());

            docs.put("weka.summary", eval.toSummaryString());
            docs.put("weka.details", eval.toClassDetailsString());
            docs.put("weka.matrix", eval.toMatrixString());
            docs.put("weka.rmsq", eval.rootMeanSquaredError());
            docs.put("weka.rmpsq", eval.rootMeanPriorSquaredError());
            docs.put("weka.rrsq", eval.rootRelativeSquaredError());

            System.out.println(eval.toSummaryString());
            System.out.println(eval.toClassDetailsString());
            System.out.println(eval.toMatrixString());

        } catch (Exception e) {
            throw new IllegalStateException("Weka: training failed with error: " + e.getMessage());
        }
    }

    /**
     * Produce the predicted class value for the passed instance (which can be
     * obtained through
     * {@link WekaInstances#getInstance(org.integratedmodelling.klab.api.data.ILocator)}).
     * The object returned may be a double or a double[] (according to the
     * return value of {@link #isPredictionProbabilistic()}, to be matched to the
     * predicted state's semantics.
     * 
     * @param instance
     * @return
     */
    public Object predict(Instance instance, IMonitor monitor) {

        try {
            if (isPredictionProbabilistic()) {
                return classifier.distributionForInstance(instance);
            } else {
                return classifier.classifyInstance(instance);
            }
        } catch (Exception e) {
            if (!errorWarning) {
                errorWarning = true;
                monitor.warn("Classification of instance generated an error (further errors will not be reported): "
                        + e.getMessage());
            }
        }
        return null;
    }

    public boolean isPredictionProbabilistic() {
        return predictionIsProbabilistic;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public String toString() {
        return "WEKA " + Path.getLast(classifier.getClass().getCanonicalName(), '.');
    }

    public void export(File clmodel) {
        try {
            SerializationHelper.write(clmodel.toString(), this.classifier);
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }

    public WekaOptions getOptions() {
        return this.options;
    }

}
