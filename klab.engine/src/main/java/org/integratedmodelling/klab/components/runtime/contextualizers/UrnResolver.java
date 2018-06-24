package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

public class UrnResolver implements IExpression, IResolver<IArtifact> {

	public final static String FUNCTION_ID = "klab.runtime.resolve";

	private IResource resource;

	// don't remove - only used as expression
	public UrnResolver() {
	}

	public UrnResolver(String urn) {
		this.resource = Resources.INSTANCE.resolveResource(urn);
		if (this.resource == null || !Resources.INSTANCE.isResourceOnline(this.resource)) {
			throw new KlabResourceNotFoundException("resource with URN " + urn + " is unavailable or unknown");
		}
	}

	public static IServiceCall getServiceCall(String urn) {
		return KimServiceCall.create(FUNCTION_ID, "urn", urn);
	}

	@Override
	public IArtifact resolve(IArtifact observation, IComputationContext context) {
		IKlabData data = Resources.INSTANCE.getResourceData(resource, context.getScale(), context);
		return data.getArtifact();
	}

	@Override
	public Object eval(IParameters parameters, IComputationContext context) throws KlabException {
		// TODO resolve URN, generate the appropriate contextualizer for type and
		// geometry
		// TODO support multiple URNs
		return new UrnResolver(parameters.get("urn", String.class));
	}

	@Override
	public IGeometry getGeometry() {
		return resource.getGeometry();
	}

	@Override
	public Type getType() {
		return resource.getType();
	}

}
