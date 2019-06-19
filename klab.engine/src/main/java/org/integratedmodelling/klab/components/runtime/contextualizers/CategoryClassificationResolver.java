package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class CategoryClassificationResolver
		implements IResolver<IState>, IProcessor, IExpression, IDocumentationProvider {

	static final public String FUNCTION_ID = "klab.runtime.categorize";

	IArtifact classified;
	IArtifact classifier;

	// don't remove - only used as expression
	public CategoryClassificationResolver() {
	}

	public CategoryClassificationResolver(IArtifact classified, IArtifact classifier) {
		this.classified = classified;
		this.classifier = classifier;
	}

	public static IServiceCall getServiceCall(IObservable classified, IObservable classifier)
			throws KlabValidationException {
		return KimServiceCall.create(FUNCTION_ID, "artifact", classified.getLocalName(), "classifier",
				classifier.getLocalName());
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new CategoryClassificationResolver(context.getArtifact(parameters.get("artifact", String.class)),
				context.getArtifact(parameters.get("classifier", String.class)));
	}

	@Override
	public IGeometry getGeometry() {
		return Geometry.scalar();
	}

	@Override
	public IState resolve(IState ret, IComputationContext context) throws KlabException {

		Map<Object, Double> cache = new HashMap<>();
		Map<Object, Long> count = new HashMap<>();

		if (!(classified instanceof IState) || !(classifier instanceof IState)) {
			throw new IllegalArgumentException(
					"Category classification is not possible unless all arguments are states");
		}

		IState values = (IState) classified;
		IState classf = (IState) classifier;

		for (ILocator locator : ret.getScale()) {

			Number value = values.get(locator, Number.class);
			Object sclas = classf.get(locator);

			if (value == null || (value instanceof Number && Double.isNaN(((Number) value).byteValue()))
					|| sclas == null) {
				continue;
			}

			double aggregated = cache.containsKey(sclas) ? cache.get(sclas).doubleValue() : 0;
			aggregated += aggregateValue(value, values.getObservable(), ret.getScale());
			cache.put(sclas, aggregated);
			long cnt = count.containsKey(sclas) ? count.get(sclas) : 0;
			count.put(sclas, cnt+1);
		}

		for (ILocator locator : ret.getScale()) {

			Object sclas = classf.get(locator);
			if (cache.containsKey(sclas)) {
				ret.set(locator, cache.get(sclas));
			}
		}

		/*
		 * TODO set the table into the documentation outputs
		 */

		return ret;
	}

	private double aggregateValue(Number value, IObservable observable, IScale scale) {
		return value.doubleValue();
	}

	@Override
	public IArtifact.Type getType() {
		return classified.getType();
	}

	@Override
	public List<String> getTags() {
		List<String> ret = new ArrayList<>();
		return ret;
	}

	@Override
	public String getDocumentation(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

}
