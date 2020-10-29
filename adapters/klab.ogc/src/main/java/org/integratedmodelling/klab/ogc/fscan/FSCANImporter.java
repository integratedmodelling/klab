package org.integratedmodelling.klab.ogc.fscan;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.ogc.vector.files.VectorValidator;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Triple;

public class FSCANImporter implements IResourceImporter {

	VectorValidator vectorValidator = new VectorValidator();
	
	@Override
	public boolean acceptsMultiple() {
		return true;
	}

	@Override
	public Collection<Builder> importResources(String importLocation, IParameters<String> userData, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean importIntoResource(URL importLocation, IResource target, IMonitor monitor) {
		if (vectorValidator.canHandle(new File(importLocation.getFile()), null)) {
			
			Builder builder = vectorValidator.validate(importLocation, target.getParameters(), monitor);
			if (!builder.hasErrors()) {

				String filename = MiscUtilities.getFileName(importLocation.toString());
	            File originalFile = new File(importLocation.getFile());
	            File bifFile = new File(((Resource) target).getPath() + File.separator + filename);
	            try {
					FileUtils.copyFile(originalFile, bifFile);
				} catch (IOException e) {
					throw new KlabIOException(e);
				}
				int existing = target.getParameters().getLike("filesource").size()  / 2;
				target.getParameters().put("filesource.import" + (existing + 1) + ".name", filename);
				target.getParameters().put("filesource.import" + (existing + 1) + ".level", 0);
				// this triggers reconstruction of the index in the encoder.
				target.getParameters().remove("totalshapes");
				
				// TODO this should get whatever catalog the resource is in, otherwise it won't work on nodes
				Resources.INSTANCE.getCatalog(target).update(target, "Imported vector source " + importLocation);
				
				return true;
			}
		}
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
		List<Triple<String,String,String>> ret = new ArrayList<>();
		return ret;
	}

	@Override
	public File exportObservation(File file, IObservation observation, ILocator locator, String format,
			IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getExportCapabilities(IResource resource) {
		Map<String, String> ret = new HashMap<>();
		return ret;
	}

	@Override
	public boolean exportResource(File file, IResource resource, String format) {
		// TODO Auto-generated method stub
		return false;
	}

}
