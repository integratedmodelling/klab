package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Map;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.utils.Pair;

public class UrnResolver extends AbstractContextualizer implements IExpression, IResolver<IArtifact> {

    public final static String FUNCTION_ID = "klab.runtime.contextualize";

    // only used for initial validation
    private IResource originalResource;
    private IResource resource;
    private Map<String, String> urnParameters;

    // don't remove - only used as expression
    public UrnResolver() {
    }

    public UrnResolver(String urn, IContextualizationScope overallContext) {
        Pair<String, Map<String, String>> call = Urns.INSTANCE.resolveParameters(urn);
        this.originalResource = Resources.INSTANCE.resolveResource(urn);
        if (this.originalResource == null || !Resources.INSTANCE.isResourceOnline(this.originalResource)) {
            throw new KlabResourceNotFoundException("resource with URN " + urn + " is unavailable or offline");
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
    public IArtifact resolve(IArtifact observation, IContextualizationScope scope) {

        if (this.resource.getAvailability() != null) {
            switch(this.resource.getAvailability().getAvailability()) {
            case DELAYED:
                scope.getMonitor().addWait(this.resource.getAvailability().getRetryTimeSeconds());
                return observation;
            case NONE:
                scope.getMonitor().error("resource " + resource.getUrn() + " has no data available in this context");
                return observation;
            case PARTIAL:
                scope.getMonitor().warn("resource " + resource.getUrn() + " reports partial availability in this context");
                break;
            case COMPLETE:
                break;
            }
        }

        Map<String, String> parameters = urnParameters;
        if (Configuration.INSTANCE.isEchoEnabled()) {
            System.err.println("GETTING DATA FROM " + this.resource.getUrn());
        }
        
        IKlabData data = Resources.INSTANCE.getResourceData(this.resource, parameters, scope.getScale(), scope, observation);
        if (Configuration.INSTANCE.isEchoEnabled()) {
            System.err.println("DONE " + this.resource.getUrn());
        }

        if (data == null) {
            scope.getMonitor().error("Cannot extract data from resource " + resource.getUrn());
        }

        return data == null ? observation : data.getArtifact();
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        // TODO support multiple URNs
        return new UrnResolver(parameters.get("urn", String.class), context);
    }

    @Override
    public Type getType() {
        return resource.getType();
    }

}
