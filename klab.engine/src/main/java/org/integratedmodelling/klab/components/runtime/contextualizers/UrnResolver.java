package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.components.runtime.observations.DelegatingArtifact;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.ActivityBuilder;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

public class UrnResolver extends AbstractContextualizer implements IExpression, IResolver<IArtifact> {

    public final static String FUNCTION_ID = "klab.runtime.contextualize";

    // only used for initial validation
    private IResource originalResource;
    private IResource resource;
    private Map<String, String> urnParameters;
    private Map<IArtifact, Set<String>> incorporated = new HashMap<>();

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

        ActivityBuilder stats = getStatistics() == null ? null : getStatistics().forTarget(resource);

        /*
         * ensure we don't retrieve the data more than once when we use multiple resources where
         * another one forces recontextualization. TODO this won't stop downstream mediations -
         * should return null or empty artifact to interrupt the chain.s
         */
        Urn urn = new Urn(resource.getUrn(), urnParameters);
        IArtifact destination = observation;
        while(destination instanceof DelegatingArtifact) {
            destination = ((DelegatingArtifact) destination).getDelegate();
        }
        if (destination instanceof Observation && ((Observation) destination).includesResource(urn.toString())) {
            // this should only happen if contextualizing the resource now does not add
            // anything, which means there is one state in it (type != grid and size <= 1).
            Dimension time = resource.getGeometry().getDimension(Dimension.Type.TIME);
            if (time == null || isStatic(time)) {
                // FIXME OK, this is correct but we may have to overwrite the "other" resource
                // anyway - which could be done using previous states, but
                // the logic is extreme
                // System.err.println("SKIPPING RESOURCE " + urn + ": PREVIOUSLY CONTEXTUALIZED");
                // return null;
            }
        }

        if (Configuration.INSTANCE.isEchoEnabled()) {
            System.err.println("GETTING DATA FROM " + this.resource.getUrn());
        }

        try {
            IKlabData data = Resources.INSTANCE.getResourceData(this.resource, parameters, scope.getScale(), scope, observation);

            if (Configuration.INSTANCE.isEchoEnabled()) {
                System.err.println("DONE " + this.resource.getUrn());
            }

            if (data == null) {
                scope.getMonitor().error("Cannot extract data from resource " + resource.getUrn());
                if (stats != null) {
                    stats.error();
                }
            } else if (destination instanceof Observation) {
                ((Observation) destination).includeResource(urn.toString());
                if (stats != null) {
                    stats.success();
                }

            }

            return data == null ? observation : data.getArtifact();
        
        } catch (Throwable t) {
            if (stats != null) {
                stats.exception(t);
            }
            throw t;
        }
        
    }

    private boolean isStatic(Dimension time) {
        if (time.size() > 1) {
            return false;
        }
        Object ttype = time.getParameters().get(Geometry.PARAMETER_TIME_REPRESENTATION);
        if (ITime.Type.GRID.name().toLowerCase().equals(ttype)) {
            return false;
        }
        return true;
    }

    @Override
    public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
        // TODO support multiple URNs
        return new UrnResolver(Parameters.create(parameters).get("urn", String.class), context);
    }

    @Override
    public Type getType() {
        return resource.getType();
    }

}
