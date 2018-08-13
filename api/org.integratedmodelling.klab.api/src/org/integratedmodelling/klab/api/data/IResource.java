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
package org.integratedmodelling.klab.api.data;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.rest.SpatialExtent;

/**
 * A IResource represents non-semantic information content that is identified by
 * a URN and can be processed semantically through a worldview. k.LAB provides
 * methods to resolve a URN into a IResource and to retrieve the data content,
 * in a separate operation to optimize speed. The services available on a k.LAB
 * node allow to upload resources in the form of files, literals, or URLs for
 * services, and make their content available under a URN that becomes a secure
 * endpoint for their use in semantic engines.
 * <p>
 * Data services implemented in a k.LAB node allow bridging to multiple types of
 * resources, linking
 * {@link org.integratedmodelling.klab.api.data.adapters.IResourceAdapter}
 * plug-ins to a resource type expressed as a string. Each plug-in exposes a
 * ({@link org.integratedmodelling.klab.api.data.adapters.IResourceValidator}
 * validator), a
 * ({@link org.integratedmodelling.klab.api.data.adapters.IResourcePublisher}
 * publisher/unpublisher) and an
 * ({@link org.integratedmodelling.klab.api.data.adapters.IResourceEncoder}
 * encoder) for each new resource type supported. IResources have a
 * {@link org.integratedmodelling.klab.api.data.IGeometry} that can be turned
 * into a semantic
 * {@link org.integratedmodelling.klab.api.observations.scale.IScale} through a
 * {@link org.integratedmodelling.klab.api.knowledge.IWorldview}. This way,
 * engines do not need to know the details of any specific data protocol as the
 * contents are returned from the engine in encoded form upon a request for a
 * URN's contents in a compatible scale.
 * <p>
 * Resolution of a URN (operated by the configured
 * {@link org.integratedmodelling.klab.api.services.IResourceService} returns a
 * IResource, which can be coupled with a {@link IGeometry} to yield an
 * immutable {@link IKlabData artifact} that a semantic
 * {@link org.integratedmodelling.klab.api.model.IModel} can process into an
 * {@link org.integratedmodelling.klab.api.observations.IObservation}.
 * <p>
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IResource extends Serializable {

	/**
	 * The URN that identifies this resource.
	 *
	 * @return the resource's URN.
	 */
	String getUrn();

	/**
	 * Get the geometry associated with the resource, without fetching the entire
	 * data content.
	 *
	 * @return the resource's geometry
	 */
	IGeometry getGeometry();

	/**
	 * Get the version associated with the resource.
	 *
	 * @return the resource's version.
	 */
	Version getVersion();

	/**
	 * The data adapter that published this resource and will be used to encode it.
	 *
	 * @return the adapter. Should only be null when no adapter is used: resources
	 *         that depend on an adapter should never be created if the adapter
	 *         isn't found.
	 */
	String getAdapterType();

	/**
	 * Resources come with both system-defined and user-defined metadata. User
	 * metadata will be indexed by Dublin Core properties. Other metadata fields
	 * will depend on the adapter used (for example, no-data values or metadata
	 * attributes such as name).
	 *
	 * @return any metadata associated with the resource. Never null.
	 */
	IMetadata getMetadata();

	/**
	 * Get the history of this resource as a list of all its versions.
	 *
	 * @return the list of previous resources in order of timestamp (oldest first).
	 */
	List<IResource> getHistory();

	/**
	 * URNs coming with parameters will list them here.
	 *
	 * @return parameter map, possibly empty, never null.
	 */
	IParameters<String> getParameters();

	/**
	 * The type of the artifact produced.
	 * 
	 * @return the type
	 */
	IArtifact.Type getType();

	/**
	 * A builder can be obtained through
	 * {@link IResourceService#createResourceBuilder()} and is used to set all the
	 * properties of a {@link IResource} that will be built at publication. The
	 * builder is returned by {@link IResourceValidator#validate}.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	interface Builder {

		/**
		 * 
		 * @param type
		 * @return the builder itself
		 */
		Builder withType(IArtifact.Type type);

		/**
		 * 
		 * @param key
		 * @param value
		 * @return the builder itself
		 */
		Builder withMetadata(String key, Object value);

		/**
		 * 
		 * @param key
		 * @param value
		 * @return the builder itself
		 */
		Builder withParameter(String key, Object value);

		/**
		 * 
		 * @param geometry
		 * @return the builder itself
		 */
		Builder withGeometry(IGeometry geometry);

		/**
		 * Add a local resource path.
		 * 
		 * @param path
		 * @return the builder itself
		 */
		Builder addLocalResourcePath(String path);

		/**
		 * 
		 * @param o
		 * @return the builder itself
		 */
		Builder addError(Object... o);

		/**
		 * 
		 * @param o
		 * @return the builder itself
		 */
		Builder addWarning(Object... o);

		/**
		 * 
		 * @param o
		 * @return the builder itself
		 */
		Builder addInfo(Object... o);

		/**
		 * 
		 * @param v
		 * @return the builder itself
		 */
		Builder withResourceVersion(Version v);

		/**
		 * 
		 * @param timestamp
		 * @return the builder itself
		 */
		Builder withResourceTimestamp(long timestamp);

		/**
		 * Add a history item. The passed resource should have no history of its own and
		 * these should be added in order of timestamp, oldest first.
		 * 
		 * @param previousResource
		 * @return the builder itself
		 */
		Builder addHistory(IResource previousResource);
		
		/**
		 * For display.
		 * 
		 * @param extent
		 * @return
		 */
		Builder withSpatialExtent(SpatialExtent extent);

		/**
		 * True if error() was ever called.
		 * 
		 * @return true in error
		 */
		boolean hasErrors();

		/**
		 * Build the resource with the passed URN. If there are errors, build a resource
		 * with errors; never return null.
		 * 
		 * @param urn
		 *            the resource URN to use
		 * @return the built resource
		 */
		IResource build(String urn);

		/**
		 * Set the adapter type of the built resource.
		 * 
		 * @param string
		 *            the adapter type
		 * @return the builder itself
		 */
		Builder withAdapterType(String string);

		/**
		 * Set the local resource path.
		 * 
		 * @param localPath
		 *            the local resource path
		 * @return the builder itself
		 */
		Builder withLocalPath(String localPath);

		/**
		 * Set the local resource name - for file resources, this should be the name of
		 * the primary file they were loaded from, without any path but with the
		 * extension.
		 * 
		 * @param localName
		 *            the local resource name
		 * @return the builder itself
		 */
		Builder withLocalName(String localName);

		/**
		 * Add all the passed parameters
		 * 
		 * @param parameters
		 *            a parameters object
		 * @return the builder itself
		 */
		Builder withParameters(IParameters<String> parameters);

		/**
		 * Set the project name. Only for local resources. Project name enters the local
		 * URN but is not exposed by the IResource API. Stored to enable easier
		 * management and retrieval.
		 * 
		 * @param name
		 * @return the builder itself
		 */
		Builder withProjectName(String name);

		/**
		 * Return all the files that compose this resource in their original locations.
		 * <p>
		 * This is only called on builders created by importers.
		 * 
		 * @return
		 */
		Collection<File> getImportedFiles();

		/**
		 * Return a suitable local ID for this resource.
		 * <p>
		 * This is only called on builders created by importers.
		 * 
		 * @return
		 */
		String getResourceId();

		/**
		 * Exclusively for use by importers. Resource IDs become part of URNs and are
		 * normally handled externally unless we import in batch. Resource IDs don't
		 * need to check for uniqueness (the importer will change suitably) but unique
		 * IDs should be set when possible, as the unique-fied built externally can be
		 * ugly.
		 * 
		 * @param identifier
		 *            an identifier describing the resource, suitable for use as part of
		 *            a URN (so no colons, punctuation, uppercase characters or anything
		 *            but underscores as separators).
		 */
		void setResourceId(String identifier);

		/**
		 * Exclusively for use by importers that use physical files. All files set in
		 * here are copied to the resource directory and exported when published.
		 * 
		 * @param file
		 *            original, existing file (will be copied to final location by
		 *            import handler)
		 */
		void addImportedFile(File file);

	}

	/**
	 * Return a timestamp that matches the time of last modification of the resource
	 * described.
	 *
	 * @return a long.
	 */
	long getResourceTimestamp();

	/**
	 * True if there is any error notification for this resource. Should always be
	 * checked after URN retrieval.
	 *
	 * @return a boolean.
	 */
	boolean hasErrors();

	/**
	 * Return all local resource file paths, as slash-separated strings starting at
	 * a point depending on the resource type (e.g. in local resources it will start
	 * at the project name). May be empty, never null. All paths will start with the
	 * return value of {@link #getLocalPath()}.
	 * 
	 * @return all local resource file paths
	 */
	List<String> getLocalPaths();

	/**
	 * If the resource is local, a local path should be defined and will identify a
	 * directory where all the {@link #getLocalPaths() local file resources} are
	 * found.
	 * 
	 * @return local path
	 */
	String getLocalPath();

	/**
	 * In local resources, this is the short name that the resource can be referred
	 * to in k.IM <strong>within the project that owns it</strong>. It will be null
	 * in public resources and won't be recognized within different local projects.
	 * 
	 * @return local name
	 */
	String getLocalName();

	/**
	 * In local resources, this is the name of the containing project and must be
	 * valid. In public resources it must be null.
	 * 
	 * @return project name
	 */
	String getLocalProjectName();
}
