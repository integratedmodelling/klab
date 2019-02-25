package org.integratedmodelling.ml.context;

import weka.classifiers.Classifier;

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
		// TODO create classifier instance
		// TODO set options
	}

	public void train(WekaInstances instances) {

	}

	public Classifier getClassifier() {
		return classifier;
	}

}
