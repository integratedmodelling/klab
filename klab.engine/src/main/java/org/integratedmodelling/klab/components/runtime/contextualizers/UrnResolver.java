package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.utils.Pair;

public class UrnResolver implements IExpression, IResolver<IArtifact> {

	public final static String FUNCTION_ID = "klab.runtime.contextualize";

	private IResource resource;
	private Map<String, String> urnParameters;
	private boolean localized = false;
	private IScale overallScale;

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
		this.overallScale = overallContext.getContextObservation().getScale();
	}

	public static IServiceCall getServiceCall(String urn, IContextualizable condition, boolean conditionNegated) {
		return KimServiceCall.create(FUNCTION_ID, "urn", urn);
	}

	@Override
	public IArtifact resolve(IArtifact observation, IContextualizationScope context) {

		// choose the T-specific resource(s). TODO use a set of resources to fill in
		// nodata (and potentially add up values over multiple temporal granularities).
		IResource res = this.resource;
		if (!localized && resource.getGeometry().getDimension(Dimension.Type.TIME) != null
				&& overallScale.getTime() != null && !overallScale.getTime().isGeneric()
				&& resource.getGeometry().getDimension(Dimension.Type.TIME).isGeneric()) {
			// localize the resource if needed
			res = res.localize(overallScale.getTime());
		}
		this.localized = true;

		if (this.resource instanceof MergedResource) {

			List<IResource> resources = ((MergedResource) this.resource).contextualize(context.getScale(), observation);
			if (resources.isEmpty()) {
				context.getMonitor()
						.warn("resource " + this.resource.getUrn() + " could not be contextualized in this scale");
				return observation;
			}

			// TODO must contextualize the LIST, not just the first resource. For now it can only happen with
			// multiple spatial extents, but it could happen also with multiple temporal slices.
			if (resources.size() > 1) {
				context.getMonitor().warn(
						"Warning: unimplemented use of multiple resources for one timestep. Choosing only the first.");
			}

			res = resources.get(0);
		}

		System.err.println("GETTING DATA FROM " + res.getUrn());
		IKlabData data = Resources.INSTANCE.getResourceData(res, urnParameters, context.getScale(), context);
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
