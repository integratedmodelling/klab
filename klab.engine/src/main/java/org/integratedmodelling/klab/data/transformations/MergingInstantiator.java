package org.integratedmodelling.klab.data.transformations;

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
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.utils.Pair;

public class MergingInstantiator implements IExpression, IInstantiator {

	public final static String FUNCTION_ID = "klab.runtime.instantiate";

	private IResource resource;
	private Map<String, String> urnParameters;

	// don't remove - only used as expression
	public MergingInstantiator() {
	}

	public MergingInstantiator(String urn) {
		Pair<String, Map<String, String>> call = Urns.INSTANCE.resolveParameters(urn);
		this.resource = Resources.INSTANCE.resolveResource(urn);
		if (this.resource == null || !Resources.INSTANCE.isResourceOnline(this.resource)) {
			throw new KlabResourceNotFoundException("resource with URN " + urn + " is unavailable or unknown");
		}
		this.urnParameters = call.getSecond();
	}

	public static IServiceCall getServiceCall(String urn, IContextualizable condition, boolean conditionNegated) {
		return KimServiceCall.create(FUNCTION_ID, "urn", urn);
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context)
			throws KlabException {

		List<IObjectArtifact> ret = new ArrayList<>();
		IResource res = this.resource.contextualize(context.getScale(), context.getTargetArtifact(), urnParameters,
				context);
		Map<String, String> parameters = urnParameters;

		if (this.resource instanceof MergedResource) {
			List<Pair<IResource, Map<String, String>>> resources = ((MergedResource) this.resource)
					.contextualize(context.getScale(), context.getTargetArtifact());
			if (resources.isEmpty()) {
				context.getMonitor()
						.warn("resource " + this.resource.getUrn() + " cannot be contextualized in this scale");
				return ret;
			}

			// TODO must contextualize the LIST, not just the first resource
			if (resources.size() > 1) {
				context.getMonitor().warn(
						"Warning: unimplemented use of multiple resources for one timestep. Choosing only the first.");
			}

			res = resources.get(0).getFirst();
			parameters = resources.get(0).getSecond();

		}

		IKlabData data = Resources.INSTANCE.getResourceData(res, parameters, context.getScale(), context);

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
		return new MergingInstantiator(parameters.get("urn", String.class));
	}

//	@Override
//	public IGeometry getGeometry() {
//		return resource.getGeometry();
//	}

	@Override
	public IArtifact.Type getType() {
		return resource.getType();
	}

}
