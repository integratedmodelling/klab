package org.integratedmodelling.klab.ogc.fscan;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.ogc.FSCANAdapter;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.utils.Path;

public class FSCANValidator implements IResourceValidator {

	@Override
	public Builder validate(URL url, IParameters<String> userData, IMonitor monitor) {

		IResource.Builder ret = Resources.INSTANCE.createResourceBuilder();

		// TODO - start empty and populate by adding new resources.
		ret.withGeometry(Geometry.scalar());

		return ret;

	}

	@Override
	public List<Operation> getAllowedOperations(IResource resource) {
		List<Operation> ret = new ArrayList<>();
		ret.add(new Operation() {

			@Override
			public String getName() {
				return "index";
			}

			@Override
			public String getDescription() {
				return "Rebuild shape index";
			}

			@Override
			public boolean isShouldConfirm() {
				return true;
			}

		});
		return ret;
	}

	@Override
	public IResource performOperation(IResource resource, String operationName, IParameters<String> parameters,
			IResourceCatalog catalog, IMonitor monitor) {
		switch (operationName) {
		case "index":
			new FSCANEncoder().indexShapes(resource, catalog);
		}
		// return whatever has changed
		return catalog.get(resource.getUrn());
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
		FSCANEncoder encoder = new FSCANEncoder();

		for (String key : resource.getParameters().keySet()) {
			if (key.startsWith("filesource.import")) {
				File file = ((Resource) resource).getLocalFile(Path.getLeading(key, '.') + '.' + "name");
				ret.put("file_" + Path.getLeading(key, '.') + '.' + "name",
						(file != null && file.exists()) ? "EXISTS" : "DOES NOT EXIST");
			}
		}

		if (!encoder.isOnline(resource, Klab.INSTANCE.getRootMonitor())) {
			ret.put("status", "DATA OFFLINE");
		} else {
			ret.putAll(FSCANAdapter.getPostgis().describeContents(new Urn(resource.getUrn())));
		}

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
