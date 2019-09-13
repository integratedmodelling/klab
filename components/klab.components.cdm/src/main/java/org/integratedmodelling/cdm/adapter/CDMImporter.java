package org.integratedmodelling.cdm.adapter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IMultipleResourceImporter;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.Triple;

import com.vividsolutions.jts.geom.Geometry;

public class CDMImporter implements IMultipleResourceImporter {

	@Override
	public Collection<Builder> importResources(String importLocation, IParameters<String> userData, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Builder> importResources(List<String> importLocation, IParameters<String> userData,
			IMonitor monitor) {
		
		Geometry geometry = importResource(importLocation.get(0), userData, monitor);
		
		// TODO Auto-generated method stub
		return null;
	}

	Geometry importResource(String url, IParameters<String> userData, IMonitor monitor) {
		return null;
	}

	@Override
	public boolean importIntoResource(URL importLocation, IResource target, IMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canHandle(String importLocation, IParameters<String> userData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resourceCanHandle(IResource resource, String importLocation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Triple<String, String, String>> getExportCapabilities(IObservation observation) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public File exportObservation(File file, IObservation observation, ILocator locator, String format,
			IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getExportCapabilities(IResource resource) {
		// TODO Auto-generated method stub
		return new HashMap<>();
	}

	@Override
	public boolean exportResource(File file, IResource resource, String format) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean acceptsMultiple() {
		return true;
	}

}
