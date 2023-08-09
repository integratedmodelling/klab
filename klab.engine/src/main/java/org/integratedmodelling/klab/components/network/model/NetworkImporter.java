package org.integratedmodelling.klab.components.network.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.INetwork;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Triple;

public class NetworkImporter implements IResourceImporter {

	@Override
	public IResourceImporter withOption(String option, Object value) {
		return this;
	}

	@Override
	public boolean acceptsMultiple() {
		return false;
	}

	@Override
	public Collection<Builder> importResources(String importLocation, IProject project, IParameters<String> userData,
			IMonitor monitor) {
		return null;
	}

	@Override
	public boolean importIntoResource(URL importLocation, IResource target, IMonitor monitor) {
		/* Importing networks is not available */
		return false;
	}

	@Override
	public boolean canHandle(String importLocation, IParameters<String> userData) {
		return false;
	}

	@Override
	public boolean resourceCanHandle(IResource resource, String importLocation) {
		return false;
	}

	@Override
	public List<Triple<String, String, String>> getExportCapabilities(IObservation observation) {
		List<Triple<String, String, String>> ret = new ArrayList<>();
		
		if ( observation instanceof IConfiguration) {
			if (observation.getScale().getSpace() != null ) {
				// Maybe condition should be more restrictive
				ret.add(new Triple<>("json", "JSON format", "json"));
				ret.add(new Triple<>("gexf", "GEXF format", "gexf"));
				ret.add(new Triple<>("graphml", "GraphML format", "graphml"));
				ret.add(new Triple<>("csv", "CSV format", "csv"));
				ret.add(new Triple<>("dot", "DOT format", "dot"));
				ret.add(new Triple<>("gml", "GML format", "gml"));
				ret.add(new Triple<>("lemon", "LGF format", "lgf"));
			}
		}
		
		return ret;
	}

	@Override
	public File exportObservation(File file, IObservation observation, ILocator locator, String format,
			IMonitor monitor) {
		if ( observation instanceof IConfiguration) {
			
			File dir = new File(MiscUtilities.changeExtension(file.toString(), "dir"));     
            dir.getAbsoluteFile().getParentFile().mkdirs();
            File out = new File(dir.getAbsoluteFile().getParentFile(), MiscUtilities.getFileName(file));
            
            INetwork network = (INetwork) ((DirectObservation) observation).getOriginatingPattern(); 
            
            try(FileOutputStream fout = new FileOutputStream(out)) {
            	network.export(format, fout);
            } catch (IOException e) {
            	return null;
            }
		}
		return null;
	}

	@Override
	public Map<String, String> getExportCapabilities(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exportResource(File file, IResource resource, String format) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean write(Writer writer, IObservation observation, ILocator locator, IMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}

}
