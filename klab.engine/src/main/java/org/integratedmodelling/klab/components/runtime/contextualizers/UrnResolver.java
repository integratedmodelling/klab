package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.documentation.Report;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.utils.Pair;

public class UrnResolver implements IExpression, IResolver<IArtifact> {

    public final static String FUNCTION_ID = "klab.runtime.contextualize";

    private IResource resource;
    private Map<String, String> urnParameters;
    // private boolean localized = false;
    // private IScale overallScale;

    // don't remove - only used as expression
    public UrnResolver() {
    }

    public UrnResolver(String urn, IContextualizationScope overallContext) {
        Pair<String, Map<String, String>> call = Urns.INSTANCE.resolveParameters(urn);
        this.resource = Resources.INSTANCE.resolveResource(urn);
        if (this.resource == null || !Resources.INSTANCE.isResourceOnline(this.resource)) {
            throw new KlabResourceNotFoundException("resource with URN " + urn + " is unavailable or offline");
        }
        this.urnParameters = call.getSecond();
        // this.overallScale = overallContext.getContextObservation().getScale();
    }

    public static IServiceCall getServiceCall(String urn, IContextualizable condition, boolean conditionNegated) {
        return KimServiceCall.create(FUNCTION_ID, "urn", urn);
    }

    @Override
    public IArtifact resolve(IArtifact observation, IContextualizationScope context) {

        /**
         * Contextualize the resource to the passed context and parameters.
         */
        IResource res = this.resource.contextualize(context.getScale(), observation, urnParameters, context);
        Map<String, String> parameters = urnParameters;
        // this.localized = true;

        if (this.resource instanceof MergedResource) {

            List<Pair<IResource, Map<String, String>>> resources = ((MergedResource) this.resource)
                    .contextualize(context.getScale(), observation);
            if (resources.isEmpty()) {
                // it's OK if the resource was already contextualized up to the available data. TODO distinguish the use cases.
                // context.getMonitor().warn("resource " + this.resource.getUrn() + " could not be contextualized in this scale");
                return observation;
            }

            for (Pair<IResource, Map<String, String>> pr : resources) {
                ((Report) context.getReport()).getDocumentationTree().addContextualizedResource(this.resource.getUrn(),
                        pr.getFirst());
            }

            // TODO must contextualize the LIST, not just the first resource. For now it can only
            // happen with
            // multiple spatial extents, but it could happen also with multiple temporal slices.
            if (resources.size() > 1) {
                context.getMonitor()
                        .warn("Warning: unimplemented use of multiple resources for one timestep. Choosing only the first.");
            }

            res = resources.get(0).getFirst();
            parameters = resources.get(0).getSecond();
        }

        System.err.println("GETTING DATA FROM " + res.getUrn());
        IKlabData data = Resources.INSTANCE.getResourceData(res, parameters, context.getScale(), context);
        System.err.println("DONE " + res.getUrn());

        if (data == null) {
            context.getMonitor().error("Cannot extract data from resource " + resource.getUrn());
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
