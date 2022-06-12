package org.integratedmodelling.klab.data.transformations;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.Pair;

public class AggregatingResolver extends AbstractContextualizer implements IResolver<IState>, IExpression, IProcessor {

    IConcept semantics;
    boolean ignoreNodata;

    /*
     * FIXME use a map of aggregators per observable; remove the observable from the add() call
     */
    
    public AggregatingResolver() {
    }

    public AggregatingResolver(IParameters<String> parameters, IContextualizationScope context) {

        this.ignoreNodata = parameters.get("nodata", Boolean.FALSE);
        IKimConcept concept = parameters.get("semantics", IKimConcept.class);
        if (concept != null) {
            this.semantics = Concepts.INSTANCE.declare(concept);
        }
    }

    @Override
    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new AggregatingResolver(parameters, context);
    }

    @Override
    public IState resolve(IState ret, IContextualizationScope context) throws KlabException {
        /*
         * this is for when the contextualizer is used directly without arguments in a 'using'
         * clause. In that circumstance, it means 'contextualize myself'.
         */
        if (semantics == null) {
            context.getMonitor().warn("aggregation: unrecognized or empty semantics");
            return ret;
        }

        Pair<String, IArtifact> group = ((IRuntimeScope) context).findArtifact(Observable.promote(this.semantics));

        List<IState> states = new ArrayList<>();
        if (group != null) {
            if (group.getSecond() instanceof IState) {
                states.add((IState) group.getSecond());
            } else if (group.getSecond() instanceof IObservationGroup) {
                for (int i = 0; i < ((IObservationGroup) group.getSecond()).groupSize(); i++) {
                    IArtifact state = ((IObservationGroup) group.getSecond()).getGroupMember(i);
                    if (!(state instanceof IState)) {
                        throw new KlabIllegalArgumentException(
                                "aggregator: the observation referenced does not resolve to states");
                    }
                    states.add((IState) state);
                }
            }
        }

        if (!states.isEmpty()) {
            Aggregator aggregator = new Aggregator(ret.getObservable(), context.getScale());
            for (ILocator locator : context.getScale()) {
                aggregator.reset();
                for (IState s : states) {
                    Object value = s.get(locator);
                    if (!ignoreNodata || Observations.INSTANCE.isData(value)) {
                        aggregator.add(value, locator);
                    }
                }
                ret.set(locator, aggregator.aggregate());
            }
        }

        return ret;
    }

}
