package org.integratedmodelling.klab.data.adapters.container;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;

public class ContainerResourceValidator implements IResourceValidator {

	@Override
	public Builder validate(String urn, URL url, IParameters<String> userData, IMonitor monitor) {
		Builder ret = Resources.INSTANCE.createResourceBuilder(urn).withType(IArtifact.Type.VOID);
		ret.withGeometry(Geometry.empty());
		return ret;
	}

	@Override
	public IResource update(IResource resource, ResourceCRUDRequest updateData) {
		// TODO Auto-generated method stub
		return resource;
	}

	@Override
	public List<Operation> getAllowedOperations(IResource resource) {
		return new ArrayList<>();
	}

	@Override
	public boolean isObservationAllowed(IResource resource, Map<String, String> urnParameters,
			Description description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IResource performOperation(IResource resource, String operationName, IParameters<String> parameters,
			IResourceCatalog catalog, IMonitor monitor) {
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
		return new ArrayList<>();
	}

	@Override
	public Map<? extends String, ? extends Object> describeResource(IResource resource) {
		// TODO
		return new HashMap<String, Object>();
	}

}
