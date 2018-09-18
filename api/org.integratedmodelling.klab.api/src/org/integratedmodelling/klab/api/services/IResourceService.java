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
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
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
     * Resolve a resource to data in a passed geometry. This involves retrieval of
     * the adapter, decoding of the resource (remotely or locally according to the
     * resource itself) and building of the data object. If no exceptions are
     * thrown, the result is guaranteed consistent with the geometry and free of
     * errors.
     * 
     * @param resource
     * @param urnParameters
     * @param geometry
     * @param context
     * @return KlabException if anything goes wrong
     */
    IKlabData getResourceData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, IComputationContext context);

    /**
     * Create or update a locally available resource from a specification or/and by
     * examining a local file. This is the beginning of a resource's life cycle.
     * When a resource is successfully created, its data will be stored in the
     * project under the resources folder, and synchronized with the local resource
     * catalog. If the file has been seen already, the resource is updated in the
     * local catalog with full history records.
     * <p>
     * The local resource will have a
     * {@code [urn:klab:]local:user:project:resourceid.version} URN which is visible
     * only within the project. All files and needed info are copied within the
     * resources project area.
     * <p>
     * The resource ID is created from the file name if an id field is not present
     * in the parameters. It is an error to pass a null file and no id.
     * <p>
     * The update parameter controls whether revisions are possible with files that
     * don't have a newer timestamp than the resource. It will normally be set to
     * true only when the resource creation is created explicitly. This function is
     * also used when reading or updating a resource for a file named in a k.IM
     * model.
     * <p>
     * Local resource versions are in the form 0.0.build with the build starting at
     * 1 and increasing at each update. Publishing them modifies the minor version,
     * starting at 0.1.build. Only their owners' explicit action, or peer review in
     * a reviewed repository, modifies the major version to make them 1.x.b or
     * anything higher than the initial version.
     * 
     * @param resourceId
     *            the ID for the resource, which will be part of the URN and must be
     *            unique within a project.
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
     * @param update
     *            if true, allow updating of the resource every time this is called.
     *            Otherwise just create if absent or update when the timestamp on
     *            the resource is older than that of the file.
     * @param asynchronous
     *            if true, spawn a validator thread and return a proxy for the
     *            resource without blocking.
     * @param monitor
     *            a
     *            {@link org.integratedmodelling.klab.api.runtime.monitoring.IMonitor}
     *            object.
     * @return a {@link org.integratedmodelling.klab.api.data.IResource} object.
     *         with a local URN if successful.
     */
    IResource createLocalResource(String resourceId, File file, IParameters<String> userData, IProject project, String adapterType, boolean update, boolean asynchronous, IMonitor monitor);

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
     * @param scale
     * 			scale of resolution, used to attribute proper default units to 
     * 			extensive observables when they are created from concepts.
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

    /**
     * Resolve a URN to data using default builder and context, using the full geometry of the resource and a suitable
     * scale (i.e. downscaling if the resulting artifact is too big to handle). Return the resulting
     * artifact, or null if things go wrong.
     * 
     * @param urn
     * @param monitor
     * @return a pair containing the context artifact and the artifact built by the resource (iterable if objects)
     */
    Pair<IArtifact, IArtifact> resolveResourceToArtifact(String urn, IMonitor monitor);

}
