package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.engine.runtime.ActivityBuilder;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

public class UrnCharacterizer extends AbstractContextualizer implements IResolver<IArtifact>, IProcessor, IExpression {

    public final static String FUNCTION_ID = "klab.runtime.characterize";

    private IResource resource;
    private Map<String, String> urnParameters;

    // don't remove - only used as expression
    public UrnCharacterizer() {
    }

    public UrnCharacterizer(String urn) {
        Pair<String, Map<String, String>> call = Urns.INSTANCE.resolveParameters(urn);
        this.resource = Resources.INSTANCE.resolveResource(urn);
        if (this.resource == null || !Resources.INSTANCE.isResourceOnline(this.resource)) {
            throw new KlabResourceNotFoundException("resource with URN " + urn + " is unavailable or unknown");
        }
        this.urnParameters = call.getSecond();
    }

    public static IServiceCall getServiceCall(String urn) {
        return KimServiceCall.create(FUNCTION_ID, "urn", urn);
    }

    @Override
    public void notifyContextualizedResource(IContextualizable resource, IArtifact target, IContextualizationScope scope) {
        this.resource = resource.getResource();
    }

    @Override
    public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
        return new UrnCharacterizer(Parameters.create(parameters).get("urn", String.class));
    }

    @Override
    public Type getType() {
        return Type.VOID;
    }

    @Override
    public IArtifact resolve(IArtifact ret, IContextualizationScope context) throws KlabException {

        // IResource res = this.resource.contextualize(context.getScale(),
        // context.getTargetArtifact(), urnParameters, context);

        if (this.resource.getAvailability() != null) {
            switch(this.resource.getAvailability().getAvailability()) {
            case DELAYED:
                context.getMonitor().addWait(this.resource.getAvailability().getRetryTimeSeconds());
                return ret;
            case NONE:
                context.getMonitor().error("resource " + resource.getUrn() + " has no data available in this context");
                return ret;
            case PARTIAL:
                context.getMonitor().warn("resource " + resource.getUrn() + " reports partial availability in this context");
                break;
            case COMPLETE:
                break;
            }
        }

        ActivityBuilder stats = getStatistics() == null ? null : getStatistics().forTarget(resource);

        try {

            IKlabData data = Resources.INSTANCE.getResourceData(this.resource, urnParameters, context.getScale(), context, ret);
            if (data != null) {
                IConcept concept = data.getSemantics();
                if (concept != null) {
                    IConcept toResolve = context.getTargetSemantics().getType();
                    List<IConcept> traits = concept.is(IKimConcept.Type.INTERSECTION)
                            ? Collections.singletonList(concept)
                            : concept.getOperands();
                    if (ret instanceof IDirectObservation) {
                        for (IConcept predicate : traits) {
                            ((IRuntimeScope) context).newPredicate((IDirectObservation) ret, predicate);
                        }
                    } else {
                        ((IRuntimeScope) context).setConcreteIdentities(toResolve, traits);
                    }
                }
                if (stats != null) {
                    stats.success();
                }
            } else {
                if (stats != null) {
                    stats.error();
                }
            }
        } catch (Throwable t) {
            if (stats != null) {
                stats.exception(t);
            }
            throw t;
        }

        return ret;
    }
}
