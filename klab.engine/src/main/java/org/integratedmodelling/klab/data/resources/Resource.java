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
package org.integratedmodelling.klab.data.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Services;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.rest.AttributeReference;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.ResourceReference.AvailabilityReference;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.Utils;

/**
 * The k.LAB resource is identified by a URN. A URN is resolved (using the
 * <code>resolve</code> API call) to a IResource; the IResource can then be
 * contextualized to a {@link org.integratedmodelling.klab.api.data.IGeometry}
 * (using the <code>get</code> API call) to produce the corresponding
 * {@link org.integratedmodelling.klab.api.data.adapters.IKlabData} that will be
 * used to build {@link IArtifact artifacts}.
 *
 * When a URN is referenced in k.IM, it is turned into a
 * {@link org.integratedmodelling.kim.api.IContextualizable} which is passed to
 * the {@link IRuntimeProvider runtime} and turned into a KDL function call or
 * literal, which encodes their computation or resolution. Executing the KDL
 * call as part of a
 * {@link org.integratedmodelling.klab.api.runtime.dataflow.IDataflow} builds
 * the corresponding
 * {@link org.integratedmodelling.klab.api.provenance.IArtifact}.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public class Resource implements IResource {

	private static final long serialVersionUID = -923039635832182164L;

	// these are used during resource publication as user preferences for the final
	// URN. Namespace is taken from the "geographical area" in metadata, catalog
	// from
	// the thematic area if unspecified.
	public static final String PREFERRED_NAMESPACE_METADATA_KEY = "klab.resource.preferred.catalog";
	public static final String PREFERRED_CATALOG_METADATA_KEY = "klab.resource.preferred.catalog";
	public static final String PREFERRED_LOCALNAME_METADATA_KEY = "klab.resource.preferred.localname";

	// properties for configuration, used only in nodes at publication.
	public static final String DEFAULT_CATALOG_PROPERTY = "klab.node.default.catalog";
	public static final String DEFAULT_NAMESPACE_PROPERTY = "klab.node.default.namespace";

	String urn;
	Version version;
	String adapterType;
	String localPath;
	IGeometry geometry;
	IArtifact.Type type;
	long resourceTimestamp;
	IMetadata metadata = new Metadata();
	Parameters<String> parameters = new Parameters<>();
	List<String> localPaths = new ArrayList<>();
	List<ResourceReference> history = new ArrayList<>();
	List<INotification> notifications = new ArrayList<>();
	List<Attribute> attributes = new ArrayList<>();
	List<Attribute> inputs = new ArrayList<>();
	List<Attribute> outputs = new ArrayList<>();
	List<String> categorizables = new ArrayList<>();
	String projectName;
	String localName;
	// copied from adapter at creation
	private Map<String, String> exports = new LinkedHashMap<>();
	// for display in resource descriptors
	SpatialExtent spatialExtent;
	List<String> dependencies = new ArrayList<>();
	AvailabilityReference availability;
	/*
	 * This is an absolute location only defined in node (public) resources.
	 */
	File resourcePath;

	/*
	 * TRY TEMPLATES FOR:
	 * 
	 * CRU: (unzip)
	 * https://crudata.uea.ac.uk/cru/data/hrg/cru_ts_4.03/cruts.1905011326.v4.03/cld
	 * /cru_ts4.03.{year1}.{year2}.cld.dat.nc.gz
	 * 
	 * TRMM:
	 * https://disc2.gesdisc.eosdis.nasa.gov:443/opendap/TRMM_L3/TRMM_3B42_Daily.7/{
	 * year}/{month}/3B42_Daily.{year}{month}{day}.7.nc4 year: 1998-2019 month:
	 * 01-12 day: 01-31
	 */

	private Map<String, Object> runtimeData = Collections.synchronizedMap(new HashMap<>());

	// folder where all the resource files were uploaded, only for the publisher
	File uploadFolder = null;

	public Resource(ResourceReference reference) {

		this.urn = reference.getUrn();
		this.version = Version.create(reference.getVersion());
		this.adapterType = reference.getAdapterType();
		this.localPath = reference.getLocalPath();
		this.type = reference.getType();
		this.resourceTimestamp = reference.getResourceTimestamp();
		this.localPaths.addAll(reference.getLocalPaths());
		this.geometry = Geometry.create(reference.getGeometry());
		this.projectName = reference.getProjectName();
		this.localName = reference.getLocalName();
		this.spatialExtent = reference.getSpatialExtent();
		this.attributes.addAll(reference.getAttributes());
		this.categorizables.addAll(reference.getCategorizables());
		this.exports.putAll(reference.getExportFormats());
		this.availability = reference.getAvailability();
		
		for (ResourceReference ref : reference.getHistory()) {
			this.history.add(ref);
		}
		for (String key : reference.getParameters().keySet()) {
			this.parameters.put(key, Utils.asPOD(reference.getParameters().get(key)));
		}
		for (String key : reference.getMetadata().keySet()) {
			this.metadata.put(key, Utils.asPOD(reference.getMetadata().get(key)));
		}
		for (org.integratedmodelling.klab.api.runtime.rest.INotification notification : reference.getNotifications()) {
			this.notifications.add(new KimNotification(notification.getMessage(), Level.parse(notification.getLevel()),
					notification.getTimestamp()));
		}
		if (reference.getDependencies() != null) {
			this.inputs.addAll(reference.getDependencies());
		}
	}

	public ResourceReference getReference() {

		ResourceReference ret = new ResourceReference();

		ret.setUrn(this.urn);
		ret.setVersion(this.version == null ? null : this.version.toString());
		ret.setGeometry(this.getGeometry() == null ? null : this.getGeometry().encode());
		ret.setAdapterType(this.getAdapterType());
		ret.setLocalPath(this.localPath);
		ret.getLocalPaths().addAll(this.localPaths);
		ret.setResourceTimestamp(this.resourceTimestamp);
		ret.setProjectName(this.projectName);
		ret.setLocalName(getLocalName());
		ret.setType(this.type);
		ret.getCategorizables().addAll(this.categorizables);
		ret.setSpatialExtent(spatialExtent);
		ret.getExportFormats().putAll(this.getExports());

		for (ResourceReference h : this.history) {
			ret.getHistory().add(h);
		}
		for (String key : this.parameters.keySet()) {
			if (Utils.isPOD(this.parameters.get(key))) {
				ret.getParameters().put(key, this.parameters.get(key).toString());
			}
		}
		for (String key : this.metadata.keySet()) {
			if (Utils.isPOD(this.metadata.get(key))) {
				ret.getMetadata().put(key, this.metadata.get(key).toString());
			}
		}
		for (INotification notification : this.notifications) {
			ret.getNotifications().add(
					new Notification(notification.getMessage(), notification.getLevel(), notification.getTimestamp()));
		}
		for (Attribute attribute : attributes) {
			ret.getAttributes().add((AttributeReference) attribute);
		}
		if (this.inputs.size() > 0) {
			ret.setDependencies(new ArrayList<AttributeReference>());
			for (Attribute dependency : this.inputs) {
				ret.getDependencies().add((AttributeReference) dependency);
			}
		}
		if (this.outputs.size() > 0) {
			ret.setOutputs(new ArrayList<AttributeReference>());
			for (Attribute output : this.outputs) {
				ret.getOutputs().add((AttributeReference) output);
			}
		}

		ret.setAvailability(this.availability);
		
		return ret;
	}

	public Resource copy() {
		return new Resource(getReference());
	}
	
	Resource() {
	}

	/**
	 * Create a resource with the passed URN and a list of errors.
	 *
	 * @param urn    the urn
	 * @param errors the errors
	 * @return the resource
	 */
	public static Resource error(String urn, List<Throwable> errors) {
		Resource ret = new Resource();
		ret.urn = urn;
		for (Throwable t : errors) {
			ret.notifications.add(new KimNotification(t.getMessage(), Level.SEVERE));
		}
		return ret;
	}

	/** {@inheritDoc} */
	@Override
	public Version getVersion() {
		return version;
	}

	/** {@inheritDoc} */
	@Override
	public IMetadata getMetadata() {
		return metadata;
	}

	/** {@inheritDoc} */
	@Override
	public String getUrn() {
		return urn;
	}

	/** {@inheritDoc} */
	@Override
	public List<IResource> getHistory() {
		List<IResource> ret = new ArrayList<>();
		for (ResourceReference ref : history) {
			ret.add(new Resource(ref));
		}
		return ret;
	}

	/** {@inheritDoc} */
	@Override
	public IGeometry getGeometry() {
		return geometry;
	}

	/** {@inheritDoc} */
	@Override
	public IParameters<String> getParameters() {
		return parameters;
	}

	/** {@inheritDoc} */
	@Override
	public String getAdapterType() {
		return adapterType;
	}

	@Override
	public List<String> getLocalPaths() {
		return localPaths;
	}

	/**
	 * <p>
	 * Getter for the field <code>resourceTimestamp</code>.
	 * </p>
	 *
	 * @return a long.
	 */
	public long getResourceTimestamp() {
		return resourceTimestamp;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasErrors() {
		if (notifications != null) {
			for (INotification notification : notifications) {
				if (notification.getLevel().equals(Level.SEVERE.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Set the upload folder
	 * 
	 * @param uploadFolder
	 * @return
	 */
	public Resource in(File uploadFolder) {
		this.uploadFolder = uploadFolder;
		return this;
	}

	public void validate(IResourceService resourceService) {

		if (!hasErrors()) {

			if (adapterType == null) {
				throw new IllegalStateException("invalid resource: adapter type is undefined");
			}
			if (version == null) {
				throw new IllegalStateException("invalid resource: version is undefined");
			}
			if (geometry == null) {
				throw new IllegalStateException("invalid resource: geometry is undefined");
			}
			if (urn == null) {
				throw new IllegalStateException("invalid resource: urn is undefined");
			}
		}

		// TODO more checks: consistent version history
	}

	@Override
	public String toString() {
		return "Resource [urn=" + urn + ", version=" + version + ", adapterType=" + adapterType + ", geometry="
				+ geometry + ", parameters=" + parameters + ", history=" + history + ", notifications=" + notifications
				+ "]";
	}

	@Override
	public String getLocalPath() {
		return localPath;
	}

	@Override
	public String getLocalProjectName() {
		return projectName;
	}

	@Override
	public IArtifact.Type getType() {
		return type;
	}

	@Override
	public String getLocalName() {
		return localName == null ? Path.getLast(urn, ':') : localName;
	}

	/**
	 * Copy the current resource state to the history so that it can be modified
	 * with full records, adding a notification to explain the change.
	 */
	public void copyToHistory(String modificationLog) {
		ResourceReference ref = this.getReference();
		ref.getNotifications().add(new Notification(modificationLog, Level.INFO.getName(), System.currentTimeMillis()));
		ref.getHistory().clear();
		history.add(ref);
	}

	public void setLocalProject(String name) {
		this.projectName = name;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public void setLocalPath(String string) {
		this.localPath = string;
	}

	@Override
	public Collection<Attribute> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<Attribute> getInputs() {
		return inputs;
	}

	/**
	 * If the passed parameters determine a type modification, copy the resource and
	 * return a new one with the modified type.
	 * 
	 * @param second
	 * @return this or a new resource if different
	 */
	public IResource applyParameters(Map<String, String> parameters) {
		Resource ret = this;
		String attribute = parameters.get(IServiceCall.DEFAULT_PARAMETER_NAME);
		if (attribute != null) {
			IArtifact.Type type = null;
			for (Attribute attr : attributes) {
				if (attr.getName() == null) {
					Logging.INSTANCE.warn("Corrupted resource " + urn + ": null attributes");
					continue;
				}
				if (attr.getName().equalsIgnoreCase(attribute)) {
					type = attr.getType();
					break;
				}
			}
			if (type != null) {
				ret = new Resource(getReference());
				ret.type = type;
			}
		}
		return ret;
	}

	public void update(ResourceCRUDRequest request) {

		this.resourceTimestamp = System.currentTimeMillis();

		for (String key : request.getParameters().keySet()) {
			this.parameters.put(key, Utils.asPOD(request.getParameters().get(key)));
		}
		for (String key : request.getMetadata().keySet()) {
			this.metadata.put(key, request.getMetadata().get(key));
		}
		if (request.getGeometry() != null) {
			this.geometry = Geometry.create(request.getGeometry());
		}
	}

	/**
	 * Return the file pointed to (as a filename relative to the resource dir) by
	 * the passed parameter.
	 * 
	 * @param parameter
	 * @return
	 */
	public File getLocalFile(String parameter) {

		if (this.parameters.containsKey(parameter)) {
			if (projectName != null) {
				IProject project = Services.INSTANCE.getService(IResourceService.class).getProject(projectName);
				if (project != null) {
					File folder = new File(project.getRoot() + File.separator + IKimProject.RESOURCE_FOLDER
							+ File.separator + Path.getLast(urn, ':'));
					if (!folder.exists()) {
						folder = new File(folder + ".v" + version);
					}
					if (folder.exists()) {
						return new File(folder + File.separator + this.parameters.get(parameter));
					}
				}
			} else {
				/*
				 * node resource, simpler because getLocalPath is set up for that
				 */
				return new File(getLocalPath() + File.separator + this.parameters.get(parameter));
			}
		}
		return null;

	}

	/**
	 * Return the file path that stores this resource's files (at minimum
	 * resource.json).
	 * 
	 * @return
	 */
	public File getPath() {
		File ret = null;
		IProject project = Services.INSTANCE.getService(IResourceService.class).getProject(projectName);
		if (project != null) {
			ret = new File(project.getRoot() + File.separator + IKimProject.RESOURCE_FOLDER + File.separator
					+ Path.getLast(urn, ':'));
			if (!ret.exists() && version != null) {
				ret = new File(ret + ".v" + version);
			}
		}
		return ret;
	}

	@Override
	public Map<String, String> getExports() {
		return exports;
	}

	@Override
	public Collection<Attribute> getOutputs() {
		return outputs;
	}

	@Override
	public String getId() {
		return getUrn();
	}

	@Override
	public long getTimestamp() {
		return getResourceTimestamp();
	}

	@Override
	public IProvenance getProvenance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDynamic() {
		return false; // TODO time geometry has size() > 1 or is a grid. May be superseded by
						// contextualize().
	}

	@Override
	public String getStatusMessage() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public List<IActivity> getActions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource contextualize(IScale scale, IArtifact observation, Map<String, String> urnParameters,
			IContextualizationScope scope) {
	    return Resources.INSTANCE.contextualizeResource(this, urnParameters, scale, observation, scope);
	}

	public void setGeometry(IGeometry geometry) {
		this.geometry = geometry;
	}

	@Override
	public List<String> getDependencies() {
		return dependencies;
	}

	/**
	 * Runtime data for resources that need to cache stuff at contextualization. The
	 * objects in the map may be persisted but the contents won't be part of the
	 * resource definition, so each adapter must find its own strategy if persistent
	 * info needs to be swapped to/from disk. The storage also won't be
	 * automatically added to any copy of the resource (i.e. if that is needed,
	 * {@link IResourceEncoder#contextualize(IResource, IScale, IArtifact, Map, IContextualizationScope)}
	 * must copy the data) and won't be part of the ResourceReference bean used in
	 * the catalog.
	 */
	public Map<String, Object> getRuntimeData() {
		return runtimeData;
	}

    @Override
    public Collection<String> getCategorizables() {
        return this.categorizables;
    }

    @Override
    public AvailabilityReference getAvailability() {
        return availability;
    }

}
