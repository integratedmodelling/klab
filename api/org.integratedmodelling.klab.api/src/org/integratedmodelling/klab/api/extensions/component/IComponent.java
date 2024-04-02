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
package org.integratedmodelling.klab.api.extensions.component;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The Interface IComponent.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IComponent extends IProject {

	/**
	 * Return any binary assets included in the component that must be loaded
	 * explicitly after the component is created.
	 *
	 * @return all loadable binary files.
	 */
	Collection<File> getBinaryAssets();

	/**
	 * The API, as a set of prototypes for all the services provided by this
	 * component.
	 *
	 * @return all prototypes provided
	 */
	Collection<IPrototype> getAPI();

	/**
	 * Called to establish if the component has been properly initialized and is
	 * ready to be used.
	 *
	 * @return true if usable.
	 */
	boolean isActive();

	/**
	 * Start the component setup and return a ticket to inquire about the results.
	 * 
	 * @return
	 * @throws KlabException
	 */
	ITicket setup() throws KlabException;

	/**
	 * Return metadata describing the current status of the component, including
	 * operational status and any information about the setup phase which may be
	 * needed, ongoing or not needed.
	 * 
	 * @return arbitrary metadata
	 */
	IMetadata getStatus();

	/**
	 * Properties corresponding to ~/.klab/<name>.properties. Never null, possiblyl
	 * empty.
	 * 
	 * @return
	 */
	Properties getProperties();

	/**
	 * Return the implementation of the class if an implementing class is defined,
	 * creating it if necessary. The implementation may be null and is the class
	 * annotated with the {@link Component} annotation.
	 * 
	 * @return the implementation or null
	 */
	Object getImplementation();
}
