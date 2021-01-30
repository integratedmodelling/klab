package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.GridLocator;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ObjectClassificationResolver
		implements IResolver<IState>, IProcessor, IExpression, IDocumentationProvider {

	static final public String FUNCTION_ID = "klab.runtime.aggregate";
	public static final String TABLE_ID = "aggregated.value.table";

	IArtifact classified;
	IConcept classifier;
	GridLocator glocator = null;
	IGrid grid = null;
	List<IDocumentationProvider.Item> documentation = new ArrayList<>();

	// don't remove - only used as expression
	public ObjectClassificationResolver() {
	}

	public ObjectClassificationResolver(IArtifact classified, IConcept classifier) {
		this.classified = classified;
		this.classifier = classifier;
	}

	public static IServiceCall getServiceCall(IObservable classified, IConcept classifier, Set<ValueOperator> modifiers)
			throws KlabValidationException {
		return KimServiceCall.create(FUNCTION_ID, "artifact", classified.getName(), "classifier", classifier);
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new ObjectClassificationResolver(context.getArtifact(parameters.get("artifact", String.class)),
				parameters.get("classifier", IConcept.class));
	}

	@Override
	public IState resolve(IState ret, IContextualizationScope context) throws KlabException {

		Map<IArtifact, Aggregator> aggregators = new HashMap<>();

		if (!(classified instanceof IState) || !(classifier.is(Type.COUNTABLE))) {
			throw new IllegalArgumentException("Object classification requires a state classified through objects");
		}

		if (grid == null && ret.getScale().getSpace() != null && ret.getScale().getSpace() instanceof Space) {
			this.grid = ((Space) ret.getScale().getSpace()).getGrid();
		}

		IObservationGroup classf = context.getArtifact(classifier, IObservationGroup.class);

		if (glocator == null && grid != null) {

			/*
			 * TODO subscribe to artifact to revise the locator if objects are added or
			 * removed. The slow and easy solution would be to just set a flag and if it's
			 * true recreate the locator even if it's not null.
			 */
			this.glocator = new GridLocator(ret.getScale(), classf);
			for (IArtifact a : classf) {
				aggregators.put(a, new Aggregator(ret.getObservable(), context.getMonitor()));
			}
		}

		for (ILocator locator : ret.getScale()) {

			for (IArtifact a : glocator.getObservations(locator)) {

				// set the artifact's value wherever it's covering the locator
				Aggregator aggregator = aggregators.get(a);
				if (aggregator != null) {
					aggregator.add(((IState) classified).get(locator), ((IState) classified).getObservable(), locator);
				}
			}

		}

		Map<IDirectObservation, Object> aggregated = new HashMap<>();
		for (IArtifact a : aggregators.keySet()) {
			aggregated.put((IDirectObservation) a, aggregators.get(a).aggregate());
		}

		this.glocator.distributeValues(aggregated, ret);

		addDocumentationTags(aggregated);

		return ret;
	}

	private void addDocumentationTags(Map<IDirectObservation, Object> cache) {

		final StringBuffer body = new StringBuffer(1024);
		String separator = "";
		for (String h : new String[] { Concepts.INSTANCE.getDisplayLabel(classifier),
				Concepts.INSTANCE.getDisplayLabel(((IState) classified).getObservable()) }) {
			body.append((separator.isEmpty() ? "" : "|") + h);
			separator += ((separator.isEmpty() ? "" : " |") + " :---");
		}
		body.append("\n");
		body.append(separator + "\n");

		for (IDirectObservation key : cache.keySet()) {
			boolean first = true;
			Object value = cache.get(key);
			if (value instanceof Number) {
				value = NumberFormat.getInstance().format(value);
			}
			for (Object item : new Object[] { key.getName(), value }) {
				body.append((first ? "" : "|") + item);
				first = false;
			}
			body.append("\n");
		}

		documentation.add(new IDocumentationProvider.Item() {

			@Override
			public String getTitle() {
				return Concepts.INSTANCE.getDisplayName(((IState) classified).getObservable()) + " aggregated by "
						+ Concepts.INSTANCE.getDisplayLabel(classifier);
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

	@Override
	public IArtifact.Type getType() {
		return classified.getType();
	}

	@Override
	public Collection<Item> getDocumentation() {
		return documentation;
	}

}
