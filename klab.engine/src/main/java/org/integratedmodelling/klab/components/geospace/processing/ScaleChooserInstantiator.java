package org.integratedmodelling.klab.components.geospace.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Abstract instantiator that uses a list of increasingly detailed spatial
 * resources and chooses the one with extents that are most likely to incarnate
 * the object in the specific region.
 * <p>
 * Resources must be supplied in a <b>sorted</b> list from coarser to finer
 * scaled.
 * 
 * @author Ferd
 *
 */
public abstract class ScaleChooserInstantiator implements IInstantiator {

	/**
	 * If true, cover the context completely: either by choosing a larger watershed
	 * or by adding even small proportion coverage
	 */
	private boolean whole = false;

	/**
	 * Strategy used to cover the context
	 */
	enum Strategy {

		/**
		 * take as many smaller watersheds as it takes to compute the requested coverage
		 */
		COVER,

		/**
		 * Switch to the largest watershed even if it's larger than the context.
		 */
		EXTEND
	}

	/**
	 * Minimum coverage of context to include in results, unless whole is true.
	 */
	private double minCoverage = 0.2;
	private Strategy strategy = Strategy.COVER;

	private Map<String, String> urnParameters = new HashMap<>();

	public ScaleChooserInstantiator() {
	}

	protected ScaleChooserInstantiator(boolean whole) {
		this.whole = whole;
		urnParameters.put("intersect", "false");
	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	/**
	 * Return all URNs for the resources to choose from.
	 * 
	 * @return
	 */
	public abstract String[] getResourceUrns();

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context)
			throws KlabException {

		if (context.getScale().getSpace() == null || context.getScale().getSpace().getDimensionality() < 2) {
			throw new IllegalStateException("scaling instantiator must be executed in a 2-dimensional spatial context");
		}

		Integer np = null;
		int n = 0;
		for (String urn : getResourceUrns()) {
			IResource resource = Resources.INSTANCE.resolveResource(urn);
			IKlabData data = Resources.INSTANCE.getResourceData(resource, urnParameters, context.getScale(), context);
			int size = data.getArtifact().groupSize();
			if (np != null) {
				if (size > np) {
					break;
				}
			}
			np = size;
			n++;
		}

		List<IObjectArtifact> ret = new ArrayList<>();

		if (n < getResourceUrns().length) {
			IResource res = Resources.INSTANCE.resolveResource(getResourceUrns()[n]);
			IKlabData data = Resources.INSTANCE.getResourceData(res, urnParameters, context.getScale(), context);
			if (data.getArtifact() != null) {
				for (IArtifact artifact : data.getArtifact()) {
					if (artifact instanceof IObjectArtifact) {
						ret.add((IObjectArtifact) artifact);
					}
				}
			}
		}

		return ret;
	}

}
