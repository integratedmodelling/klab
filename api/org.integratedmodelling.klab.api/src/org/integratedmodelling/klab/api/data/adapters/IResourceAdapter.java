/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.data.adapters;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;

/**
 * A {@code IResourceAdapter} is the interface for a plug-in providing a new
 * adapter for a resource type. A class implementing {@code IResourceAdapter}
 * must be annotated with a
 * {@link org.integratedmodelling.klab.api.extensions.ResourceAdapter}
 * annotation in order to be discovered by the runtime.
 *
 * The implementing class may specify initialization and finalization methods by
 * annotating them with the
 * {@link org.integratedmodelling.klab.api.extensions.component.Initialize} and
 * {@link org.integratedmodelling.klab.api.extensions.component.Shutdown}
 * annotations also used for components.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IResourceAdapter {

	/**
	 * The adapter name. Must be lowercase and simple.
	 * 
	 * @return the adapter name.
	 */
	String getName();

	/**
	 * Produce a new instance of the
	 * {@link org.integratedmodelling.klab.api.data.adapters.IResourceValidator} for
	 * this resource type.
	 *
	 * @return a
	 *         {@link org.integratedmodelling.klab.api.data.adapters.IResourceValidator}
	 *         object.
	 */
	IResourceValidator getValidator();

	/**
	 * Produce a new instance of a resource publisher that will receive the physical
	 * contents of a local resource and produce a public one from it. Used only in
	 * nodes.
	 *
	 * @return a
	 *         {@link org.integratedmodelling.klab.api.data.adapters.IResourcePublisher}
	 *         object.
	 */
	IResourcePublisher getPublisher();

	/**
	 * Produce a new instance of the
	 * {@link org.integratedmodelling.klab.api.data.adapters.IResourceEncoder} for
	 * this resource type.
	 *
	 * @return a
	 *         {@link org.integratedmodelling.klab.api.data.adapters.IResourceEncoder}
	 *         object.
	 */
	IResourceEncoder getEncoder();

	/**
	 * Return an importer for this resource type. This handles bulk import from
	 * URLs, directories or other resources. This may be null if no bulk importing
	 * is supported.
	 * 
	 * @return an importer, or null if the resource adapter does not support
	 *         importing.
	 */
	IResourceImporter getImporter();

	/**
	 * If this resource can be used to compute a value from a set of inputs, return
	 * a calculator that exposes an expression-like API. This normally applies to
	 * resources that compute values in scalar scope. The passed resource must be
	 * using this adapter.
	 * 
	 * @return a calculator if the resource supports it, null otherwise.
	 * @throws IllegalArgumentException if the resource does not use this adapter
	 */
	IResourceCalculator getCalculator(IResource resource);

	/**
	 * Return a prototype describing all the user-modifiable parameters that can be
	 * understood in resources using this adapter.
	 * 
	 * @return
	 */
	IPrototype getResourceConfiguration();

}
