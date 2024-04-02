package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.engine.runtime.ActivityBuilder;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

public class UrnInstantiator extends AbstractContextualizer implements IExpression, IInstantiator {

    public final static String FUNCTION_ID = "klab.runtime.instantiate";

    private IResource resource;
    private Map<String, String> urnParameters;

    // don't remove - only used as expression
    public UrnInstantiator() {
    }

    public UrnInstantiator(String urn) {
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
    public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope scope) throws KlabException {

        List<IObjectArtifact> ret = new ArrayList<>();
        Map<String, String> parameters = urnParameters;

        if (this.resource.getAvailability() != null) {
            switch(this.resource.getAvailability().getAvailability()) {
            case DELAYED:
                scope.getMonitor().addWait(this.resource.getAvailability().getRetryTimeSeconds());
                return ret;
            case NONE:
                scope.getMonitor().error("resource " + resource.getUrn() + " has no data available in this context");
                return ret;
            case PARTIAL:
                scope.getMonitor().warn("resource " + resource.getUrn() + " reports partial availability in this context");
                break;
            case COMPLETE:
                break;
            }
        }

        ActivityBuilder stats = getStatistics() == null ? null : getStatistics().forTarget(resource);

        try {

            IKlabData data = Resources.INSTANCE.getResourceData(this.resource, parameters, scope.getScale(), scope);

            if (data != null && data.getArtifact() != null) {
                for (IArtifact artifact : data.getArtifact()) {
                    if (artifact instanceof IObjectArtifact) {
                        ret.add((IObjectArtifact) artifact);
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

    @Override
    public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
        return new UrnInstantiator(Parameters.create(parameters).get("urn", String.class));
    }

    @Override
    public IArtifact.Type getType() {
        return resource.getType();
    }

}
