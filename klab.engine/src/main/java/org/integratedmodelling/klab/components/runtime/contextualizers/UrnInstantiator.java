package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IParameters;
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
import org.integratedmodelling.klab.documentation.Report;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.utils.Pair;

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
        // IResource res = this.resource.contextualize(scope.getScale(), scope.getTargetArtifact(),
        // urnParameters, scope);
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

        // if (this.resource instanceof MergedResource) {
        //
        // System.out.println("PORRCOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        //
        // List<Pair<IResource, Map<String, String>>> resources = ((MergedResource) this.resource)
        // .contextualize(scope.getScale(), scope.getTargetArtifact(), scope);
        // if (resources.isEmpty()) {
        // // it's OK if the resource was already contextualized up to the available data. TODO
        // // distinguish the use cases.
        // // context.getMonitor().warn("resource " + this.resource.getUrn() + " cannot be
        // // contextualized in this scale");
        // return ret;
        // }
        //
        // for (Pair<IResource, Map<String, String>> pr : resources) {
        // ((Report) scope.getReport()).addContextualizedResource(this.resource.getUrn(),
        // pr.getFirst());
        // }
        //
        // // TODO must contextualize the LIST, not just the first resource
        // if (resources.size() > 1) {
        // scope.getMonitor()
        // .warn("Warning: unimplemented use of multiple resources for one timestep. Choosing only
        // the first.");
        // }
        //
        // this.resource = resources.get(0).getFirst();
        // parameters = resources.get(0).getSecond();
        //
        // }

        IKlabData data = Resources.INSTANCE.getResourceData(this.resource, parameters, scope.getScale(), scope);

        if (data != null && data.getArtifact() != null) {
            for (IArtifact artifact : data.getArtifact()) {
                if (artifact instanceof IObjectArtifact) {
                    ret.add((IObjectArtifact) artifact);
                }
            }
        }
        return ret;
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new UrnInstantiator(parameters.get("urn", String.class));
    }

    // @Override
    // public IGeometry getGeometry() {
    // return resource.getGeometry();
    // }

    @Override
    public IArtifact.Type getType() {
        return resource.getType();
    }

}
