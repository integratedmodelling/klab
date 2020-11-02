package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ChangingResourceResolver implements IResolver<IArtifact>, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.resourcechange";

	private MergedResource resource;
	private IArtifact.Type type;
	private String lastContextualized;

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

		List<IResource> resources = ((MergedResource) this.resource).contextualize(context.getScale());
		if (resources.isEmpty()) {
			context.getMonitor().warn("resource " + this.resource.getUrn() + " cannot be contextualized in this scale");
			return ret;
		}

		// TODO must contextualize the LIST, not just the first resource. For now it can
		// only happen with
		// multiple spatial extents, but it could happen also with multiple temporal
		// slices.
		if (resources.size() > 1) {
			context.getMonitor().warn(
					"Warning: unimplemented use of multiple resources for one timestep. Choosing only the first.");
		}
		
		IResource res = resources.get(0);
		Urn urn = new Urn(res.getUrn());

		if (lastContextualized !=  null && lastContextualized.equals(urn.getUrn())) {
			System.out.println("SKIPPING PRE-CONTEXTUALIZED SUCKA " + lastContextualized);
			return ret;
		}
		
		System.err.println("GETTING DATA FROM " + res.getUrn());
		IKlabData data = Resources.INSTANCE.getResourceData(res, urn.getParameters(), context.getScale(), context);
		this.lastContextualized = urn.getUrn();
		System.err.println("DONE " + res.getUrn());

		if (data == null) {
			context.getMonitor().error("Cannot extract data from resource " + resource.getUrn());
		}

		return data == null ? ret : data.getArtifact();
	}
}
