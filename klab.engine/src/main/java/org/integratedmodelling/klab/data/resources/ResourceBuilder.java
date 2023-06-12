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
import java.util.List;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.rest.AttributeReference;
import org.integratedmodelling.klab.rest.CodelistReference;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * The Class ResourceBuilder.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class ResourceBuilder implements IResource.Builder {

	private Metadata metadata = new Metadata();
	private Parameters<String> parameters = new Parameters<>();
	private IGeometry geometry;
	private String localPath;
	private List<String> resourcePaths = new ArrayList<>();
	private List<ResourceReference> history = new ArrayList<>();
	private List<INotification> notifications = new ArrayList<>();
	private List<AttributeReference> attributes = new ArrayList<>();
	private List<AttributeReference> dependencies = new ArrayList<>();
	private List<AttributeReference> outputs = new ArrayList<>();
	private long resourceTimestamp = System.currentTimeMillis();
	private Version resourceVersion;
	private boolean error = false;
	private String adapterType;
	private IArtifact.Type type;
	private String projectName;
	private String localName;
	private List<String> requiredUrns;
	private List<String> categorizables = new ArrayList<>();
	private List<CodelistReference> codelists = new ArrayList<>();

	// for importers
	private String resourceId;
	private List<File> importedFiles = new ArrayList<>();
	private SpatialExtent spatialExtent;
	
	private String urn;

	public ResourceBuilder(String urn) {
		this.urn = urn;
	}
	
	/** {@inheritDoc} */
	@Override
	public IResource build() {

		Resource ret = new Resource();
		ret.urn = urn;
		ret.parameters.putAll(this.parameters);
		ret.metadata.putAll(metadata);
		ret.geometry = this.geometry;
		ret.outputs.addAll(this.outputs);
		ret.notifications.addAll(this.notifications);
		ret.history.addAll(this.history);
		ret.resourceTimestamp = this.resourceTimestamp;
		ret.version = this.resourceVersion;
		ret.adapterType = this.adapterType;
		ret.localPath = this.localPath;
		ret.localPaths.addAll(resourcePaths);
		ret.type = type;
		ret.projectName = this.projectName;
		ret.localName = this.localName;
		ret.spatialExtent = this.spatialExtent;
		ret.attributes.addAll(this.attributes);
		ret.inputs.addAll(this.dependencies);
		ret.categorizables.addAll(this.categorizables);
		if (this.requiredUrns != null) {
			ret.dependencies.addAll(this.requiredUrns);
		}
		for (CodelistReference cl : this.codelists) {
		    File clfile = getLocalFile("code_" + cl.getId() + ".json");
		    JsonUtils.save(cl, clfile);
		    ret.codelists.add(cl.getId());
		}
		return ret;
	}  

	private File getLocalFile(String filename) {
	    if (this.projectName != null && this.localPath != null) {
	        IProject project = Resources.INSTANCE.getProject(this.projectName);
	        if (project != null) {
	            File ret = new File(project.getRoot().getParentFile() + File.separator + this.localPath);
	            ret.mkdirs();
	            return new File(ret + File.separator + filename);
	        }
	    }
	    return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public ResourceBuilder withMetadata(String key, Object value) {
		metadata.put(key, value);
		return this;
	}
	
	@Override
	public ResourceBuilder withDependency(String urn) {
		requiredUrns.add(urn);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBuilder withParameter(String key, Object value) {
		parameters.put(key, value);
		return this;
	}

	@Override
	public ResourceBuilder withLocalPath(String localPath) {
		this.localPath = localPath;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBuilder addError(Object... o) {
		notifications.add(new KimNotification(NotificationUtils.getMessage(o), Level.SEVERE));
		error = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBuilder addWarning(Object... o) {
		notifications.add(new KimNotification(NotificationUtils.getMessage(o), Level.WARNING));
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBuilder addInfo(Object... o) {
		notifications.add(new KimNotification(NotificationUtils.getMessage(o), Level.INFO));
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBuilder withResourceVersion(Version v) {
		this.resourceVersion = v;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBuilder withResourceTimestamp(long timestamp) {
		this.resourceTimestamp = timestamp;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBuilder addHistory(IResource notification) {
		this.history.add(((Resource) notification).getReference());
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBuilder withGeometry(IGeometry s) {
		this.geometry = s;
		if (this.geometry.getDimension(Dimension.Type.SPACE) instanceof ISpace) {
			this.spatialExtent = ((ISpace) this.geometry.getDimension(Dimension.Type.SPACE)).getExtentDescriptor();
		}

		return this;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasErrors() {
		return error;
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBuilder withAdapterType(String string) {
		this.adapterType = string;
		return this;
	}

	@Override
	public Builder addLocalResourcePath(String path) {
		this.resourcePaths.add(path);
		return this;
	}

	@Override
	public Builder withParameters(IParameters<String> parameters) {
		this.parameters.putAll(parameters);
		return this;
	}

	@Override
	public Builder withType(Type type) {
		this.type = type;
		return this;
	}

	@Override
	public Builder withProjectName(String name) {
		this.projectName = name;
		return this;
	}

	@Override
	public Collection<File> getImportedFiles() {
		return importedFiles;
	}
	
	@Override
	public Builder withCategorizable(String id) {
	    this.categorizables.add(id);
	    return this;
	}

	@Override
	public String getResourceId() {
		return resourceId;
	}

	@Override
	public void setResourceId(String identifier) {
		this.resourceId = identifier;
	}

	@Override
	public void addImportedFile(File file) {
		this.importedFiles.add(file);
	}

	@Override
	public Builder withLocalName(String localName) {
		this.localName = localName;
		return this;
	}

	@Override
	public Builder withSpatialExtent(SpatialExtent extent) {
		this.spatialExtent = extent;
		return this;
	}

	@Override
	public Builder withAttribute(String name, IArtifact.Type type, boolean key, boolean optional) {
		AttributeReference attribute = new AttributeReference();
		attribute.setName(name);
		attribute.setType(type);
		attribute.setKey(key);
		attribute.setOptional(optional);
		// TODO example
		this.attributes.add(attribute);
		return this;
	}

	@Override
	public Builder withOutput(String name, IArtifact.Type type) {
		AttributeReference attribute = new AttributeReference();
		attribute.setName(name);
		attribute.setType(type);
		attribute.setKey(false);
		attribute.setOptional(true);
		this.outputs.add(attribute);
		return this;
	}
	
	@Override
	public Builder withDependency(String name, IArtifact.Type type, boolean key, boolean optional) {
		AttributeReference attribute = new AttributeReference();
		attribute.setName(name);
		attribute.setType(type);
		attribute.setKey(key);
		attribute.setOptional(optional);
		this.dependencies.add(attribute);
		return this;
	}

    @Override
    public Builder addCodeList(CodelistReference codelist) {
        this.codelists.add(codelist);
        return this;
    }

	@Override
	public String getUrn() {
		return this.urn;
	}
	
	public Parameters<String> getParameters() {
	    return this.parameters;
	}
}
