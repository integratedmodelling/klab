package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ObjectClassificationResolver
        implements IResolver<IState>, IProcessor, IExpression, IDocumentationProvider {

    static final public String FUNCTION_ID = "klab.runtime.aggregate";

    IArtifact classified;
    IArtifact classifier;
    List<IDocumentationProvider.Item> documentation = new ArrayList<>();
    
    // don't remove - only used as expression
    public ObjectClassificationResolver() {
    }

    public ObjectClassificationResolver(IArtifact classified, IArtifact classifier) {
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
        return new ObjectClassificationResolver(context.getArtifact(parameters.get("artifact", String.class)),
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

        if (!(classified instanceof IState) || !(classifier instanceof ObservationGroup)) {
            throw new IllegalArgumentException("Object classification requires a state classified through objects");
        }

        IState values = (IState) classified;
        ObservationGroup classf = (ObservationGroup) classifier;

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

        for (IArtifact a : classf) {
            // compute covered quantity and set into table
        }
        
        for (ILocator locator : ret.getScale()) {

            //          if (propagateSpace || propagateTime) {
            //              // Observations.INSTANCE.getSpaceAndTimeExtents(locator);
            //          }

            for (IArtifact a : classf) {
                // set the artifact's value wherever it's covering the locator
                if (!cache.containsKey(a)) {
                    continue;
                }
                
                IState view = Observations.INSTANCE.getStateView(ret, ((IObservation)a).getScale(), context);
                view.fill(cache.get(a));
            }
        }

        /*
         * TODO set the table into the documentation outputs
         */
        addDocumentationTags(cache);
        return ret;
    }

    private void addDocumentationTags(Map<Object, Double> cache) {
        // TODO Auto-generated method stub
        
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
