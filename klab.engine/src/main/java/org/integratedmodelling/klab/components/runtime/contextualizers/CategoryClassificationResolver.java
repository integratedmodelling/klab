package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class CategoryClassificationResolver
		implements IResolver<IState>, IProcessor, IExpression, IDocumentationProvider {

	static final public String FUNCTION_ID = "klab.runtime.categorize";
	public static final String TABLE_ID = "aggregated.value.table";

	IArtifact classified;
	IConcept classifier;
	List<IDocumentationProvider.Item> docTags = new ArrayList<>();

	private Set<ValueOperator> modifiers;

	// don't remove - only used as expression
	public CategoryClassificationResolver() {
	}

	@SuppressWarnings("unchecked")
	public CategoryClassificationResolver(IArtifact classified, IConcept classifier, Object modifiers) {
		this.classified = classified;
		this.classifier = classifier;
		if (modifiers instanceof Set) {
			this.modifiers = (Set<ValueOperator>) modifiers;
		}
	}

	public static IServiceCall getServiceCall(IObservable classified, IConcept classifier, Set<ValueOperator> modifiers)
			throws KlabValidationException {
		return KimServiceCall.create(FUNCTION_ID, "artifact", classified.getName(), "classifier", classifier,
				"modifiers", modifiers);
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new CategoryClassificationResolver(context.getArtifact(parameters.get("artifact", String.class)),
				parameters.get("classifier", IConcept.class), parameters.get("modifiers"));
	}

	Map<Object, Aggregator> aggregators = new HashMap<>();
	
	@Override
	public IState resolve(IState ret, IContextualizationScope context) throws KlabException {

		IArtifact classfc = ((IRuntimeScope) context).getArtifact(classifier, IArtifact.class);

		if (!(classified instanceof IState) || !(classfc instanceof IState)) {
			throw new IllegalArgumentException(
					"Category classification is not possible: state for " + classifier + " not found or not a state");
		}

		IState values = (IState) classified;
		IState classf = (IState) classfc;

		for (ILocator locator : context.getScale()) {

			Object value = values.get(locator, Number.class);
			Object sclas = classf.get(locator);

			if (Observations.INSTANCE.isNodata(value) || Observations.INSTANCE.isNodata(sclas)) {
				continue;
			}

			Aggregator aggregator = aggregators.get(sclas);
			if (aggregator == null) {
				aggregator = new Aggregator(ret.getObservable(), context.getMonitor());
				aggregators.put(sclas, aggregator);
			}

			aggregator.add(value, values.getObservable(), locator);

		}

		Map<Object, Object> totals = new HashMap<>();
		for (Object key : aggregators.keySet()) {
			totals.put(key, aggregators.get(key).aggregate());
		}

		for (ILocator locator : ret.getScale()) {

			Object data = classf.get(locator);
			if (Observations.INSTANCE.isNodata(data)) {
				continue;
			}
			
			Aggregator aggregator = aggregators.get(data);
			if (aggregator != null) {
				ret.set(locator, aggregator.adjust(totals.get(data), locator));
			}
		}

		/*
		 * TODO set the table into the documentation outputs
		 * TODO add different tables according to @summarize annotations or other options
		 */
		addDocumentationTags(totals, classf);

		return ret;
	}

	private void addDocumentationTags(Map<Object, Object> cache, IState classifier) {

		final StringBuffer body = new StringBuffer(1024);
		String separator = "";
		for (String h : new String[] { Concepts.INSTANCE.getDisplayLabel(((IState) classifier).getObservable()),
				Concepts.INSTANCE.getDisplayLabel(((IState) classified).getObservable()) }) {
			body.append((separator.isEmpty() ? "" : "|") + h);
			separator += ((separator.isEmpty() ? "" : " |") + " :---");
		}
		body.append("\n");
		body.append(separator + "\n");

		for (Object key : cache.keySet()) {
			boolean first = true;
			for (Object item : new Object[] {
					key instanceof IConcept ? Concepts.INSTANCE.getDisplayLabel((IConcept) key) : key.toString(),
					NumberFormat.getInstance().format(cache.get(key)) }) {
				body.append((first ? "" : "|") + item);
				first = false;
			}
			body.append("\n");
		}

		docTags.add(new IDocumentationProvider.Item() {

			@Override
			public String getTitle() {
				return Concepts.INSTANCE.getDisplayName(((IState) classified).getObservable()) + " aggregated by "
						+ Concepts.INSTANCE.getDisplayLabel(classifier.getObservable());
			}

			@Override
			public String getMarkdownContents() {
				return body.toString();
			}

			@Override
			public String getId() {
				return TABLE_ID;
			}
		});

	}

	private double aggregateValue(Number value, IObservable observable, IScale scale) {
		return value.doubleValue();
	}

	@Override
	public IArtifact.Type getType() {
		return classified.getType();
	}

	@Override
	public Collection<Item> getDocumentation() {
		return docTags;
	}

}
