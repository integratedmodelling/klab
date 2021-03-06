/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.data.adapters;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;

/**
 * The Interface IResourceValidator.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IResourceValidator {

	/**
	 * Describe all additional operations that a validator can perform on a
	 * resource. Returned by getAllowedOperations.
	 * 
	 * @author Ferd
	 *
	 */
	interface Operation {
		/**
		 * ID of operation. Will be seen by users.
		 * 
		 * @return
		 */
		String getName();

		/**
		 * Description. Should clarify all possible consequences and wait times.
		 * 
		 * @return
		 */
		String getDescription();

		/**
		 * True if we should confirm before attempting the operation.
		 * 
		 * @return
		 */
		boolean isShouldConfirm();
	}

	/**
	 * Validate the resource pointed to by the URL and tagged with the passed
	 * user-provided data (possibly empty). Returns a builder that will be used to
	 * produce the resource to be published or to report any errors resulting from
	 * unsuccessful validation.
	 *
	 * @param urn      the URN that will be assigned to the resource if the validator
	 *                 succeeds. This should be used to create the resource builder.
	 * @param url      the URL to the raw resource (normally a file resource). In
	 *                 some situations, e.g. when wrapping service calls that are
	 *                 not directly described by a single URL, this may be null and
	 *                 all the information may be given as userData. May be null.
	 * @param userData a {@link org.integratedmodelling.kim.api.IParameters} object
	 *                 describing any user metadata to accompany the raw resource
	 *                 URL. In some situations this may be empty, in others it may
	 *                 be the entire description. Not null.
	 * @param monitor  for notifications and identity retrieval
	 * @return a builder for the resource, containing any validation errors. Never
	 *         null.
	 */
	Builder validate(String urn, URL url, IParameters<String> userData, IMonitor monitor);

	/**
	 * Called to revalidate a resource after an update done from the resource
	 * editor. Modification may concern the parameters or the geometry (for now only
	 * the temporal aspects), passed in the bean.
	 * 
	 * @param resource
	 * @param updateData
	 * @return
	 */
	IResource update(IResource resource, ResourceCRUDRequest updateData);

	/**
	 * Return all the operations allowed on this resource, or all operations if the
	 * resource is null. If a resource is passed, the result must not include any
	 * operations already performed whose results are irreversible.
	 * 
	 * @param resource. Must accept null to retrieve a list of all operations
	 * @return all allowed operations
	 */
	List<Operation> getAllowedOperations(IResource resource);

	/**
	 * Called in certain situations to assess if the resource can support the passed
	 * observation type with the given parameters. For now used only to check if
	 * resources can produce an individual concept to characterize a context,
	 * 
	 * @param resource
	 * @param urnParameters
	 * @param description
	 * @return
	 */
	boolean isObservationAllowed(IResource resource, Map<String, String> urnParameters, Description description);

	/**
	 * Perform the passed operation on a resource, returning the modifier resource
	 * when finished. May run long so should be called in a separate thread.
	 * Anything including errors, success etc should be reported through the
	 * monitor.
	 * 
	 * @param resource
	 * @param operationName
	 * @param parameters
	 * @param monitor
	 * @return
	 */
	IResource performOperation(IResource resource, String operationName, IParameters<String> parameters,
			IResourceCatalog catalog, IMonitor monitor);

	/**
	 * Check if the passed file and/or parameters can be validated by this
	 * validator. Should be a quick check.
	 * 
	 * @param resource   a file resource. Can be null.
	 * @param parameters parameters associated with a creation request. Can be
	 *                   empty.
	 * @return true if input can be validated
	 */
	boolean canHandle(File resource, IParameters<String> parameters);

	/**
	 * Return all the files that make up a resource identified by the main file
	 * imported, including the main file itself. Returned files must exist.
	 * 
	 * @param file
	 * @return all relevant files for the resource.
	 */
	Collection<File> getAllFilesForResource(File file);

	/**
	 * Return a map with all known and useful details about this resource,
	 * particularly those related to the internal storage built to support it.
	 * Called by the {@link API.NODE.RESOURCE} INFO endpoint to report about the
	 * status of a resource in a remote node.
	 * 
	 * @param resource
	 * @return
	 */
	Map<? extends String, ? extends Object> describeResource(IResource resource);

}
