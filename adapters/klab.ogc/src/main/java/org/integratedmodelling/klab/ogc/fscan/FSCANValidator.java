package org.integratedmodelling.klab.ogc.fscan;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;

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
	public IResource performOperation(IResource resource, String operationName, IResourceCatalog catalog, IMonitor monitor) {
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

}
