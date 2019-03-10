package org.integratedmodelling.klab.ogc.vector.files;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.data.adapters.AbstractFilesetImporter;
import org.integratedmodelling.klab.ogc.VectorAdapter;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class VectorImporter extends AbstractFilesetImporter {

    VectorValidator validator = new VectorValidator();

    public VectorImporter() {
        super(VectorAdapter.fileExtensions.toArray(new String[VectorAdapter.fileExtensions.size()]));
    }

    @Override
    protected Builder importFile(File file, IParameters<String> userData, IMonitor monitor) {
        try {

            Builder builder = validator.validate(file.toURI().toURL(), userData, monitor);

            if (builder != null) {
                builder.setResourceId(MiscUtilities.getFileBaseName(file).toLowerCase());
                for (File f : validator.getAllFilesForResource(file)) {
                    builder.addImportedFile(f);
                }
            }

            return builder;

        } catch (MalformedURLException e) {
            Logging.INSTANCE.error(e);
            return null;
        }
    }

    @Override
    public Map<String, String> getExportCapabilities(IObservation observation) {
        Map<String, String> ret = new HashMap<>();

        if (observation instanceof ObservationGroup) {
            observation = ((ObservationGroup) observation).groupSize() > 0
                    ? (IObservation)((ObservationGroup) observation).iterator().next()
                    : null;
        }
        if (observation instanceof IDirectObservation) {
            if (observation.getScale().getSpace() != null && !observation.getScale().getSpace().isRegular()) {
                ret.put("shp", "ESRI Shapefile");
            }
        }
        return ret;
    }

    @Override
    public File exportObservation(File file, IObservation observation, ILocator locator, String format) {
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
