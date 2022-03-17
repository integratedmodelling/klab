package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.IResolutionConstraint;

/**
 * Resolution constraints can be added through k.Actors or options to affect the
 * resolution. They may include a whitelist of URNs for models or resources or
 * other parameters. For now used only during unit testing driven by k.Actors.
 * 
 * @author Ferd
 *
 */
public class ResolutionConstraint implements IResolutionConstraint {

	public static ResolutionConstraint whitelist(Object... objects) {
		ResolutionConstraint ret = new ResolutionConstraint();
		if (objects != null) {
			for (Object o : objects) {
				if (o instanceof IModel) {
					
				} else if (o instanceof IResource) {
					
				} else if (o instanceof String) {
					
				}
			}
		}
		return ret;
	}

	List<IModel> modelWhitelist = new ArrayList<>();
	List<IResource> resourceWhitelist = new ArrayList<>();
	List<IModel> modelBlacklist = new ArrayList<>();
	List<IResource> resourceBlacklist = new ArrayList<>();
	boolean feasible = true;

	@Override
	public boolean accepts(IModel model) {
		return true;
	}

	@Override
	public boolean accepts(IResource model) {
		return true;
	}

	public boolean isFeasible() {
		return feasible;
	}

	@Override
	public boolean accepts(INamespace namespace) {
		return true;
	}
}
