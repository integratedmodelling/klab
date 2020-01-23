package org.integratedmodelling.ml.adapters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
import org.apache.commons.math3.random.Well19937c;
import org.apache.commons.math3.util.Pair;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Utils;

import weka.core.Instance;

public class WekaCalculator implements IResourceCalculator {

	private IResource resource;
	private WekaEncoder encoder = new WekaEncoder();
	private Well19937c generator = new Well19937c();
	
	public WekaCalculator(IResource resource) {
		this.resource = resource;
		this.encoder.initialize(resource);
	}

	@Override
	public <T> T eval(IParameters<String> arguments, Class<? extends T> cls, IMonitor monitor) {

		Instance instance = encoder.instances.getInstance(arguments);
		if (instance == null) {
			return null;
		}
		Object prediction = encoder.classifier.predict(instance, monitor);
		return interpretPrediction(prediction, cls, monitor);
	}

	@SuppressWarnings("unchecked")
	private <T> T interpretPrediction(Object prediction, Class<? extends T> cls, IMonitor monitor) {

		if (prediction instanceof double[]) {

			if (EnumeratedRealDistribution.class.isAssignableFrom(cls)) {
				// TODO
			} else if (EnumeratedDistribution.class.isAssignableFrom(cls)) {
				IDataKey dataKey = encoder.instances.getDatakey("predicted");
				if (dataKey != null) {
					List<IConcept> concepts = dataKey.getConcepts();
					if (concepts != null) {
						List<Pair<IConcept, Double>> pairs = new ArrayList<>();
						for (int i = 0; i < concepts.size(); i++) {
							pairs.add(Pair.create(concepts.get(i), ((double[])prediction)[i]));
						}
						return (T) new EnumeratedDistribution<IConcept>(generator, pairs);
					}
				}
			}
			// TODO numbers etc.

		} else {
			IDataKey dataKey = encoder.instances.getDatakey("predicted");
			if (dataKey != null) {
				// TODO
			} else {
				return Utils.asType(prediction, cls);
			}
		}
		throw new KlabValidationException(
				"cannot interpret predicted value " + prediction + " as " + cls.getCanonicalName());
	}

	@Override
	public <T> T eval(IContextualizationScope scope, ILocator locator, Class<? extends T> cls, IMonitor monitor) {
		return eval(scope, cls, monitor);
	}

	@Override
	public IResource getResource() {
		return resource;
	}

}
