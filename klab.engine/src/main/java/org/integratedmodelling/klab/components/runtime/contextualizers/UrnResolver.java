package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Map;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
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

public class UrnResolver implements IExpression, IResolver<IArtifact> {

	public final static String FUNCTION_ID = "klab.runtime.resolve";

	private IResource resource;
	private Map<String, String> urnParameters;

	// don't remove - only used as expression
	public UrnResolver() {
	}

	public UrnResolver(String urn) {
		Pair<String, Map<String, String>> call = Urns.INSTANCE.resolveParameters(urn);
		this.resource = Resources.INSTANCE.resolveResource(urn);
		if (this.resource == null || !Resources.INSTANCE.isResourceOnline(this.resource)) {
			throw new KlabResourceNotFoundException("resource with URN " + urn + " is unavailable or unknown");
		}
		this.urnParameters = call.getSecond();
	}

	public static IServiceCall getServiceCall(String urn, IComputableResource condition, boolean conditionNegated) {
		return KimServiceCall.create(FUNCTION_ID, "urn", urn);
	}

	@Override
	public IArtifact resolve(IArtifact observation, IContextualizationScope context) {
		IKlabData data = Resources.INSTANCE.getResourceData(resource, urnParameters, context.getScale(), context);
		return data.getArtifact();
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		// TODO support multiple URNs
		return new UrnResolver(parameters.get("urn", String.class));
	}

//	@Override
//	public IGeometry getGeometry() {
//		return resource.getGeometry();
//	}

	@Override
	public Type getType() {
		return resource.getType();
	}

}
