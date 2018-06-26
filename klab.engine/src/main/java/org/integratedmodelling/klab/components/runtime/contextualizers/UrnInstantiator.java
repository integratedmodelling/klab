package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Collections;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

public class UrnInstantiator implements IExpression, IInstantiator {

	public final static String FUNCTION_ID = "klab.runtime.instantiate";

	private IResource resource;

	// don't remove - only used as expression
	public UrnInstantiator() {
	}

	public UrnInstantiator(String urn) {
		this.resource = Resources.INSTANCE.resolveResource(urn);
		if (this.resource == null || !Resources.INSTANCE.isResourceOnline(this.resource)) {
			throw new KlabResourceNotFoundException("resource with URN " + urn + " is unavailable or unknown");
		}
	}

	public static IServiceCall getServiceCall(String urn) {
		return KimServiceCall.create(FUNCTION_ID, "urn", urn);
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {
		IKlabData data = Resources.INSTANCE.getResourceData(resource, context.getScale(), context);
		return Collections.singletonList((IObjectArtifact)data.getArtifact());
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new UrnInstantiator(parameters.get("urn", String.class));
	}

	@Override
	public IGeometry getGeometry() {
		return resource.getGeometry();
	}

	@Override
	public IArtifact.Type getType() {
		return resource.getType();
	}

}
