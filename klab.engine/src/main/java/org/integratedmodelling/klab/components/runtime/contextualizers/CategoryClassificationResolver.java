package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
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

    public static final String TABLE_ID = "aggregated.value.table";

    IArtifact classified;
    IArtifact classifier;
    Map<String, String> docTags = new HashMap<>();

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

        /*
         * TODO some values are extensive. others aren't
         */
        boolean isExtensive = values.getObservable().is(Type.EXTENSIVE_PROPERTY)
                || values.getObservable().is(Type.VALUE);

        IUnit propagateSpace = (ret.getScale().getSpace() != null
                && Units.INSTANCE.isArealDensity(values.getObservable().getUnit()))
                        ? Units.INSTANCE.getArealExtentUnit(values.getObservable().getUnit())
                        : null;
        IUnit propagateTime = (ret.getScale().getTime() != null
                && Units.INSTANCE.isRate(values.getObservable().getUnit()))
                        ? Units.INSTANCE.getTimeExtentUnit(values.getObservable().getUnit())
                        : null;

        for (ILocator locator : ret.getScale()) {

            Number value = values.get(locator, Number.class);
            Object sclas = classf.get(locator);

            //			if (propagateSpace || propagateTime) {
            //				// Observations.INSTANCE.getSpaceAndTimeExtents(locator);
            //			}

            if (value == null || (value instanceof Number && Double.isNaN(((Number) value).byteValue()))
                    || sclas == null) {
                continue;
            }

            double aggregated = cache.containsKey(sclas) ? cache.get(sclas).doubleValue() : 0;
            aggregated += aggregateValue(value, values.getObservable(), ret.getScale());
            cache.put(sclas, aggregated);
            long cnt = count.containsKey(sclas) ? count.get(sclas) : 0;
            count.put(sclas, cnt + 1);
        }

        if (!isExtensive) {
            for (Object key : cache.keySet()) {
                cache.put(key, cache.get(key) / count.get(key));
            }
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
        addDocumentationTags(cache);

        return ret;
    }

    private void addDocumentationTags(Map<Object, Double> cache) {

        StringBuffer body = new StringBuffer(1024);
        String separator = "";
        for (String h : new String[] { Concepts.INSTANCE.getDisplayName(((IState) classifier).getObservable()),
                Concepts.INSTANCE.getDisplayName(((IState) classified).getObservable()) }) {
            body.append((separator.isEmpty() ? "" : "|") + h);
            separator += ((separator.isEmpty() ? "" : "|") + ":---");
        }
        body.append("\n");
        body.append(separator + "\n");

        for (Object key : cache.keySet()) {
            boolean first = true;
            for (Object item : new Object[] { key, cache.get(key) }) {
                body.append((first ? "" : "|") + item.toString());
                first = false;
            }
            body.append("\n");
        }
        body.append("[[#" + TABLE_ID + "] " + " Aggregation of "
                + Concepts.INSTANCE.getDisplayName(((IState) classified).getObservable()) + " values by "
                + Concepts.INSTANCE.getDisplayName(((IState) classifier).getObservable()) + "  ]");
        body.append("{#" + TABLE_ID + " text-align: center}\n\n");

        docTags.put(TABLE_ID, body.toString());

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
        ret.add(TABLE_ID);
        return ret;
    }

    @Override
    public String getDocumentation(String tag) {
        return docTags.get(tag);
    }

}
