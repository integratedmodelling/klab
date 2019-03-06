package org.integratedmodelling.ml.context;

import org.integratedmodelling.klab.Extensions;

import weka.classifiers.Classifier;
import weka.core.OptionHandler;

/**
 * TODO use text options and the Evaluation class to streamline access using
 * Weka conventions. May have to override Evaluation::evaluateModel(Classifier
 * classifier, String[] options) to structure outputs.
 * 
 * @author Ferd
 *
 */
public class WekaClassifier {

	private Classifier classifier;

	public WekaClassifier(Class<? extends Classifier> cls, WekaOptions options) {
		this.classifier = Extensions.INSTANCE.createDefaultInstance(cls, Classifier.class);
		if (this.classifier instanceof OptionHandler) {
			try {
				((OptionHandler)this.classifier).setOptions(options.getWekaOptions());
			} catch (Exception e) {
				throw new IllegalStateException("Weka: error setting options for " + cls + ": '" + options + "': " + e.getMessage());
			}
		}
	}

	public void train(WekaInstances instances) {

	}

	public Classifier getClassifier() {
		return classifier;
	}

}
