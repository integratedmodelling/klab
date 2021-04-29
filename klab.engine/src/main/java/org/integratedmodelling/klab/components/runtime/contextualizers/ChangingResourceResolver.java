package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.documentation.Report;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;

public class ChangingResourceResolver implements IResolver<IArtifact>, IExpression {

    static final public String FUNCTION_ID = "klab.runtime.resourcechange";

    private MergedResource resource;

    // don't remove - only used as expression
    public ChangingResourceResolver() {
    }

    public ChangingResourceResolver(IConcept changeProcess, MergedResource resource) {
        this.resource = resource;
    }

    public static IServiceCall getServiceCall(IConcept change, MergedResource resource) throws KlabValidationException {
        return KimServiceCall.create(FUNCTION_ID, "change", change.getDefinition(), "resources", resource.getUrns());
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        IConcept change = Observables.INSTANCE.declare(parameters.get("change", String.class)).getType();
        @SuppressWarnings("unchecked")
        List<String> urns = parameters.get("resources", List.class);
        return new ChangingResourceResolver(change, new MergedResource(urns, (IRuntimeScope) context));
    }

    @Override
    public IArtifact.Type getType() {
        return this.resource.getType();
    }

    @Override
    public IArtifact resolve(IArtifact ret, IContextualizationScope context) throws KlabException {

        List<Pair<IResource, Map<String, String>>> resources = ((MergedResource) this.resource).contextualize(context.getScale(),
                ret);

        if (resources.isEmpty()) {
            // this can happen when the resource can't add anything to the artifact.
            return ret;
        }

        for (Pair<IResource, Map<String, String>> pr : resources) {
            ((Report) context.getReport()).getDocumentationTree().addContextualizedResource(this.resource.getUrn(),
                    pr.getFirst());
        }

        // TODO must contextualize the LIST, not just the first resource. For now it can
        // only happen with
        // multiple spatial extents, but it could happen also with multiple temporal
        // slices.
        if (resources.size() > 1) {
            context.getMonitor()
                    .warn("Warning: unimplemented use of multiple resources for one timestep. Choosing only the first.");
        }

        IResource res = resources.get(0).getFirst();

        if (Configuration.INSTANCE.isEchoEnabled()) {
            System.err.println("GETTING DATA FROM " + res.getUrn());
        }
        IKlabData data = Resources.INSTANCE.getResourceData(res, resources.get(0).getSecond(), context.getScale(), context);
        if (Configuration.INSTANCE.isEchoEnabled()) {
            System.err.println("DONE " + res.getUrn());
        }

        if (data == null) {
            context.getMonitor().error("Cannot extract data from resource " + resource.getUrn());
        }

        return data == null ? ret : data.getArtifact();
    }
}
