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
package org.integratedmodelling.klab.api.services;

import java.io.File;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;

/**
 * The <code>IResourceService</code> service handles all semantic and
 * non-semantic assets.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IResourceService {

	/**
	 * The local resource catalog is for resources created from local files or
	 * specifications. These resources are created by the {@link IResourceValidator
	 * validator} of an {@link IResourceAdapter adapter}, and must be published
	 * before they can be shared. A project containing local URNs cannot be
	 * published on a k.LAB node.
	 *
	 * @return the local resource catalog
	 */
	IResourceCatalog getLocalResourceCatalog();

	/**
	 * The public resource catalog contains resources after they have been published
	 * by the {@link IResourcePublisher publisher} of the adapter that created the
	 * resource. These resources can be shared with others and projects using their
	 * URNs can be shared on k.LAB nodes.
	 *
	 * @return the public resource catalog
	 */
	IResourceCatalog getPublicResourceCatalog();

	/**
	 * Resolve the passed URN to a resource.
	 *
	 * @param urn
	 *            the
	 * @return a resource
	 * @throws org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException
	 * @throws org.integratedmodelling.klab.exceptions.KlabAuthorizationException
	 */
	IResource resolveResource(String urn) throws KlabResourceNotFoundException, KlabAuthorizationException;

	/**
	 * Create a locally available resource from a specification or/and by examining
	 * a local file. This is the beginning of a resource's life cycle. When a
	 * resource is successfully created, its data will be stored in the project
	 * under the resources folder, and synchronized with the local resource catalog.
	 * <p>
	 * The local resource will have a
	 * {@code [urn:klab:]local:user:project:resourceid.version} URN which is visible
	 * only within the project. All files and needed info are copied within the
	 * resources project area.
	 * <p>
	 * The resource ID is created from the file name if an id field is not present
	 * in the parameters. It is an error to pass a null file and no id.
	 *
	 * @param file
	 *            a {@link java.io.File} object. May be null if userData contain all
	 *            relevant info. The local path of the file (starting at the project
	 *            folder, inclusive) is stored in metadata and checked in case of
	 *            redefinition, so that the URN is versioned rather than recreated.
	 * @param userData
	 *            user data. May be empty (if all that's needed is the file). Must
	 *            contain a suitable id if the file is null. These are used to
	 *            define URN parameters at the discretion of the adapter.
	 * @param project
	 *            the project for the resource. Can't be null. All local resources
	 *            are project-local; only public resources are visible globally.
	 * @param adapterType
	 *            pass null to interrogate all adapters and choose the first fitting
	 *            adapter. Must be passed if file is null.
	 * @param monitor
	 *            a
	 *            {@link org.integratedmodelling.klab.api.runtime.monitoring.IMonitor}
	 *            object.
	 * @return a {@link org.integratedmodelling.klab.api.data.IResource} object.
	 *         with a local URN if successful.
	 */
	IResource getLocalResource(File file, IParameters userData, IProject project, String adapterType, IMonitor monitor);

	/**
	 * The workspace with all local projects. The only workspace that is not read
	 * only and is monitored for changes, with automatic reload of any updated
	 * knowledge. Never null, possibly empty.
	 *
	 * @return a {@link org.integratedmodelling.klab.api.knowledge.IWorkspace}
	 *         object.
	 */
	IWorkspace getLocalWorkspace();

	/**
	 * The upper ontology workspace, automatically synchronized and read only.
	 *
	 * @return a {@link org.integratedmodelling.klab.api.knowledge.IWorkspace}
	 *         object.
	 */
	IWorkspace getUpperOntology();

	/**
	 * All the projects composing the worldview, automatically synchronized and read
	 * only, but overridden by any of the same projects in the local workspace.
	 *
	 * @return a {@link org.integratedmodelling.klab.api.knowledge.IWorldview}
	 *         object.
	 */
	IWorldview getWorldview();

	/**
	 * The components workspace, including projects (with or without binary assets)
	 * that are managed by the engine as new components are requested during
	 * resolution. Read only. In development configuration also contains any locally
	 * available components.
	 *
	 * @return a {@link org.integratedmodelling.klab.api.knowledge.IWorkspace}
	 *         object.
	 */
	IWorkspace getComponentsWorkspace();

	/**
	 * <p>
	 * getProject.
	 * </p>
	 *
	 * @param projectId
	 *            a {@link java.lang.String} object.
	 * @return a {@link org.integratedmodelling.klab.api.knowledge.IProject} object.
	 */
	IProject getProject(String projectId);

	/**
	 * Retrieve a model object identified through a URN - either an observer or a
	 * model, local or remote, in the latter case triggering any necessary
	 * synchronization with the network.
	 *
	 * @param urn
	 *            a {@link java.lang.String} object.
	 * @return the model object corresponding to the urn, or null if not found.
	 */
	IKimObject getModelObject(String urn);

	/**
	 * Retrieve a resolvable object identified by a URN, promoting any resource that
	 * is not directly resolvable to the correspondent resolvable when possible.
	 *
	 * @param urn
	 *            either a formal URN or one of the abbreviated forms recognized in
	 *            k.IM (such as a concept identifier)
	 * @return a resolvable resource, or null if nothing can be found.
	 */
	IResolvable getResolvableResource(String urn);

	/**
	 * Create a builder to describe a future valid resource or the errors that will
	 * prevent it from being published.
	 *
	 * @return a {@link org.integratedmodelling.klab.api.data.IResource.Builder}
	 *         object.
	 */
	IResource.Builder createResourceBuilder();

}
