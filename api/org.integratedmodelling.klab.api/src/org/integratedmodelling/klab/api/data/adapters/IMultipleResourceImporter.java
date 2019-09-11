package org.integratedmodelling.klab.api.data.adapters;

import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public interface IMultipleResourceImporter extends IResourceImporter {
	/**
	 * Import all the resources that can be resolved through the passed import
	 * location strings, building a single resource out of them. If there are no
	 * usable resources or the location is unrecognized, an empty collection should
	 * be returned without error.
	 * 
	 * @param importLocation
	 * @param userData
	 * @param monitor
	 * @return builders for all found resources, possibly with errors.
	 */
	Collection<IResource.Builder> importResources(List<String> importLocation, IParameters<String> userData,
			IMonitor monitor);
}
