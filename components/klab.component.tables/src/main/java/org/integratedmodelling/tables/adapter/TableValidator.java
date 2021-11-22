package org.integratedmodelling.tables.adapter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.rest.ResourceReference.OperationReference;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.tables.TablesComponent;

import com.google.common.collect.Lists;

public class TableValidator implements IResourceValidator {

	static public final String FILE_URL = "__source_url";
	String id;

	public TableValidator(String id) {
		this.id = id;
	}

	@Override
	public Builder validate(URL url, IParameters<String> userData, IMonitor monitor) {

		IResource.Builder ret = Resources.INSTANCE.createResourceBuilder();

		if (url != null) {
			userData.put(FILE_URL, url);
		}

		if (TablesComponent.getTableInterpreter(id).canHandle(url, userData)) {
			TablesComponent.getTableInterpreter(id).buildResource(userData, ret, monitor);
		}

		return ret;
	}

	@Override
	public List<Operation> getAllowedOperations(IResource resource) {
		List<Operation> ret = new ArrayList<>();
		OperationReference op = new OperationReference();
		op.setDescription("Recompute the resource's geometry based on the configuration");
		op.setName("Align geometry");
		op.setShouldConfirm(true);
		ret.add(op);
		return ret;
	}

	@Override
	public IResource performOperation(IResource resource, String operationName, IParameters<String> parameters,
			IResourceCatalog catalog, IMonitor monitor) {
		switch (operationName) {
		case "categorize":
			TablesComponent.getTableInterpreter(resource.getAdapterType()).categorize(resource, parameters, monitor);
			break;
		case "Align geometry":
			IGeometry original = resource.getGeometry();
			IGeometry geometry = TablesComponent.getTableInterpreter(resource.getAdapterType()).recomputeGeometry(resource,
					((Parameters<String>) parameters).asStringMap(), monitor);
			if (!original.equals(geometry)) {
				((Resource)resource).setGeometry(geometry);
				((Resource)resource).touch();
				Resources.INSTANCE.getLocalResourceCatalog().update(resource, "Resource geometry recomputed by external user action");
			}
			break;
		}

		return null;
	}

	@Override
	public boolean canHandle(File resource, IParameters<String> parameters) {

		if (resource == null) {
			return false;
		}

		try {
			if (TablesComponent.getTableInterpreter(id).canHandle(resource.toURI().toURL(), parameters)) {
				return true;
			}
		} catch (MalformedURLException e) {
			// move on
		}

		return false; // TODO
						// TableAdapter.fileExtensions.contains(MiscUtilities.getFileExtension(resource));
	}

	@Override
	public Collection<File> getAllFilesForResource(File file) {
		return Lists.newArrayList(file);
	}

	@Override
	public Map<String, Object> describeResource(IResource resource) {
		Map<String, Object> ret = new LinkedHashMap<>();
		// TODO
		return ret;
	}

	@Override
	public IResource update(IResource resource, ResourceCRUDRequest updateData) {
		IGeometry geometry = null;
		if (updateData.getParameters().containsKey("time.encoding")
				|| updateData.getParameters().containsKey("space.encoding")) {
			geometry = TablesComponent.getTableInterpreter(id).recomputeGeometry(resource, updateData.getParameters(),
					Klab.INSTANCE.getRootMonitor());
		}
		((Resource) resource).update(updateData);
		if (geometry != null) {
			((Resource) resource).setGeometry(geometry);
		}
		return resource;
	}

	@Override
	public boolean isObservationAllowed(IResource resource, Map<String, String> urnParameters,
			Description description) {
		if (description == Description.CHARACTERIZATION && urnParameters.containsKey("collect")) {
			/*
			 * TODO check if the collected attribute is conceptual (through an admitted code
			 * mapping)
			 */
			return true;
		}
		return false;
	}

}
