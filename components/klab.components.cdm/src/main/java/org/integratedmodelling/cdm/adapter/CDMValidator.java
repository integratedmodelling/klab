package org.integratedmodelling.cdm.adapter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;

public class CDMValidator implements IResourceValidator {

	@Override
	public Builder validate(URL url, IParameters<String> userData, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Operation> getAllowedOperations(IResource resource) {
		List<Operation> ret = new ArrayList<>();
		return ret;
	}

	@Override
	public IResource performOperation(IResource resource, String operationName, IParameters<String> parameters,
			IResourceCatalog catalog,
			IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canHandle(File resource, IParameters<String> parameters) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<File> getAllFilesForResource(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> describeResource(IResource resource) {
		Map<String, Object> ret = new LinkedHashMap<>();
		// TODO
		return ret;
	}

	@Override
	public IResource update(IResource resource, ResourceCRUDRequest updateData) {
		((Resource) resource).update(updateData);
		return resource;
	}

	@Override
	public boolean isObservationAllowed(IResource resource, Map<String, String> urnParameters,
			Description description) {
		// TODO Auto-generated method stub
		return false;
	}

}
