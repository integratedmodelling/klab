package org.integratedmodelling.klab.stac;

import java.io.File;
import java.io.Writer;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.Triple;

public class STACImporter implements IResourceImporter {

    @Override
    public IResourceImporter withOption(String option, Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean acceptsMultiple() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<Builder> importResources(String importLocation, IProject project, IParameters<String> userData,
            IMonitor monitor) {
        // TODO Auto-generated method stub
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
    public List<Triple<String, String, String>> getExportCapabilities(IObservation observation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File exportObservation(File file, IObservation observation, ILocator locator, String format, IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> getExportCapabilities(IResource resource) {
        return Collections.emptyMap();
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
