package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.utils.Pair;

public class UrnCharacterizer implements IResolver<IArtifact>, IProcessor, IExpression {

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
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new UrnCharacterizer(parameters.get("urn", String.class));
	}

	@Override
	public Type getType() {
		return Type.VOID;
	}

	@Override
	public IArtifact resolve(IArtifact ret, IContextualizationScope context) throws KlabException {
		// TODO Auto-generated method stub
		return ret;
	}
}
