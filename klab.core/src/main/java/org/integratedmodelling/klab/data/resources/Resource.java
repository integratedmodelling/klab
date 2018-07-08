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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.services.IResourceService;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.Parameters;
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
 * {@link org.integratedmodelling.kim.api.IComputableResource} which is passed
 * to the {@link IRuntimeProvider runtime} and turned into a KDL function call
 * or literal, which encodes their computation or resolution. Executing the KDL
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
	List<IResource> history = new ArrayList<>();
	List<INotification> notifications = new ArrayList<>();
	String projectName;

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
		for (ResourceReference ref : reference.getHistory()) {
			this.history.add(new Resource(ref));
		}
		for (String key : reference.getParameters().keySet()) {
			this.parameters.put(key, Utils.asPOD(reference.getParameters().get(key)));
		}
		for (String key : reference.getMetadata().keySet()) {
			this.metadata.put(key, Utils.asPOD(reference.getParameters().get(key)));
		}
		for (Notification notification : reference.getNotifications()) {
			this.notifications.add(new KimNotification(notification.getMessage(), notification.getLevel(),
					notification.getTimestamp()));
		}
	}

	public ResourceReference getReference() {
		
		ResourceReference ret = new ResourceReference();
		ret.setUrn(this.urn);
		ret.setVersion(this.version.toString());
		ret.setGeometry(this.getGeometry().encode());
		ret.setAdapterType(this.getAdapterType());
		ret.setLocalPath(this.localPath);
		ret.getLocalPaths().addAll(this.localPaths);
		ret.setResourceTimestamp(this.resourceTimestamp);
		ret.setProjectName(this.projectName);
		ret.setType(this.type);
		
		for (IResource h : this.history) {
			ret.getHistory().add(((Resource) h).getReference());
		}
		for (String key : this.parameters.keySet()) {
			if (Utils.isPOD(this.parameters.get(key))) {
				ret.getParameters().put(key, this.parameters.get(key).toString());
			}
		}
		for (String key : this.metadata.keySet()) {
			if (Utils.isPOD(this.metadata.get(key))) {
				ret.getParameters().put(key, this.metadata.get(key).toString());
			}
		}
		for (INotification notification : this.notifications) {
			ret.getNotifications().add(
					new Notification(notification.getMessage(), notification.getLevel(), notification.getTimestamp()));
		}

		return ret;
	}

	Resource() {
	}

	/**
	 * Create a resource with the passed URN and a list of errors.
	 *
	 * @param urn
	 *            the urn
	 * @param errors
	 *            the errors
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
		return history;
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
				if (notification.getLevel() == Level.SEVERE) {
					return true;
				}
			}
		}
		return false;
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

			// TODO metadata consistency check for adapter
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
	public IArtifact.Type getType() {
		return type;
	}

}
