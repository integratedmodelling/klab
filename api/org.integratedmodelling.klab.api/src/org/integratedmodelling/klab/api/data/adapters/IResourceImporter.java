package org.integratedmodelling.klab.api.data.adapters;

import java.util.Collection;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public interface IResourceImporter {

	/**
	 * Import all the resources that can be resolved through the passed import
	 * location string. If there are no usable resources or the location is
	 * unrecognized, an empty collection should be returned without error.
	 * 
	 * @param importLocation
	 * @param userData
	 * @param monitor 
	 * @return builders for all found resources, possibly with errors.
	 */
	Collection<IResource.Builder> importResources(String importLocation, IParameters<String> userData,
			IMonitor monitor);

	/**
	 * Check if the passed location (file, URL or whatever) can be handled. In this
	 * case 'unknown' is a possible response, in which case we can return true as
	 * long as {@link #importResources(String, IParameters)} behaves gracefully.
	 * 
	 * @param importLocation
	 * @param userData
	 * @return true if we recognize this URL or we don't know and want to try
	 *         importing.
	 */
	boolean canHandle(String importLocation, IParameters<String> userData);
}
