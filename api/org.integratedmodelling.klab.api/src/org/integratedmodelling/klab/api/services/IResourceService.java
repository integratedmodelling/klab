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
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.Pair;

/**
 * The <code>IResourceService</code> service provides access to all
 * URN-identified non-semantic assets. It also bridges to read-only semantic
 * assets such as observations and concepts when identified through URNs.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IResourceService {

	/**
	 * An importer for programmatic import of local artifact.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public interface Importer {

		/**
		 * Specify the adapter. Optional.
		 * 
		 * @param adapter
		 * @return the importer itself
		 */
		Importer withAdapter(String adapter);

		/**
		 * Specify a parameter and its value. Optional.
		 * 
		 * @param adapter
		 * @return the importer itself
		 */
		Importer with(String parameter, Object value);

		/**
		 * Specify an ID for the resource. Optional.
		 * 
		 * @param adapter
		 * @return the importer itself
		 */

		Importer withId(String id);

		/**
		 * Call the validation and import service and return the finished resource.
		 * 
		 * @return
		 */
		IResource finish();
	}

	/**
	 * Get a calculator for the passed resource, or null if the resource adapter
	 * can't make one.
	 * 
	 * @param resource
	 * @return
	 */
	IResourceCalculator getCalculator(IResource resource);

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
	 * Resolve the passed URN to a resource.
	 *
	 * @param urn the
	 * @return a resource
	 * @throws org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException
	 * @throws org.integratedmodelling.klab.exceptions.KlabAuthorizationException
	 */
	IResource resolveResource(String urn);

	/**
	 * Resolve a resource with the option of passing a local URN with just the local
	 * name and a target project to look into. This enables resolution of
	 * project-local resources using only the local name as URN, which for
	 * file-based resources will just be the file name.
	 * 
	 * @param urn
	 * @param project
	 * @return
	 */
	IResource resolveResource(String urn, IProject project);

	/**
	 * Low-level data retrieval using a URN and a geometry. The result is set in the
	 * returned IKlabData and won't have any semantics.
	 * 
	 * @param urn
	 * @param geometry
	 * @param monitor
	 * @return
	 */
	IKlabData getResourceData(String urn, IGeometry geometry, IMonitor monitor);

	/**
	 * High-level data retrieval, using the scope and the scale it contains to
	 * create the artifact according to whatever semantic context it represents.
	 * Return the main artifact built from the resource.
	 * 
	 * @param urn
	 * @param geometry
	 * @param scope
	 * @return
	 */
	IArtifact contextualizeResource(String urn, IContextualizationScope scope);

//	IKlabData getResourceData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
//			IContextualizationScope context);

//	IResource createLocalResource(String resourceId, File file, IParameters<String> userData, IProject project,
//			String adapterType, boolean update, boolean asynchronous, IMonitor monitor);

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
	 * The service workspace contains one project per session user where to define
	 * uploads, learned models and on-the-fly scenarios, plus one temporary project
	 * per user where extemporaneous resources are saved.
	 *
	 * @return a {@link org.integratedmodelling.klab.api.knowledge.IWorkspace}
	 *         object.
	 */
	IWorkspace getServiceWorkspace();

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
	 * Retrieve a model object identified through a URN - either an observer or a
	 * model, local or remote, in the latter case triggering any necessary
	 * synchronization with the network.
	 *
	 * @param urn a {@link java.lang.String} object.
	 * @return the model object corresponding to the urn, or null if not found.
	 */
	IKimObject getModelObject(String urn);

	/**
	 * Retrieve a resolvable object identified by a URN, promoting any resource that
	 * is not directly resolvable to a correspondent resolvable when possible.
	 *
	 * @param urn   either a formal URN or one of the abbreviated forms recognized
	 *              in k.IM (such as a concept identifier)
	 * @param scale scale of resolution, used to attribute proper default units to
	 *              extensive observables when they are created from concepts.
	 * @return a resolvable resource, or null if nothing can be found.
	 */
	IResolvable getResolvableResource(String urn, IScale scale);

	/**
	 * Return the project with the passed name as a k.LAB wrapper.
	 * 
	 * @param name
	 * @return
	 */
	IProject getProject(String name);

//	Pair<IArtifact, IArtifact> resolveResourceToArtifact(String urn, IMonitor monitor);

	/**
	 * Resolve the URN and return true if the resolution is successful and the
	 * resource is online, false otherwise.
	 * 
	 * @param urn
	 * @return true if resource can be used right away
	 */
	boolean isResourceOnline(String urn);

	/**
	 * Submit a resource for publication to the node identified by nodeId, which
	 * must be an online node on the network. Return an open ticket that will be
	 * closed when publication is finished.
	 * 
	 * @param resource
	 * @param nodeId
	 * @param publicationData any user suggestions about name, namespace, catalog
	 *                        and permissions.
	 * @return a temporary ID to track the publishing process.
	 */
	ITicket submitResource(IResource resource, String nodeId, Map<String, String> publicationData);

	/**
	 * 
	 * @param resource
	 * @return
	 */
	boolean validateForPublication(IResource resource);

	/**
	 * Return the directory where the resource is located on the filesystem. This
	 * will differ for local (project-based) and public resources (in nodes).
	 * Universal resources (klab:....) have no file location and will return null.
	 * 
	 * @param resource
	 * @return
	 */
	File getFilesystemLocation(IResource resource);

}
