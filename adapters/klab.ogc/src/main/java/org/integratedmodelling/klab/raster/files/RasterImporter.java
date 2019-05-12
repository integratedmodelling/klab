package org.integratedmodelling.klab.raster.files;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.data.adapters.AbstractFilesetImporter;
import org.integratedmodelling.klab.ogc.RasterAdapter;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Triple;

public class RasterImporter extends AbstractFilesetImporter {

    RasterValidator validator = new RasterValidator();

    public RasterImporter() {
        super(RasterAdapter.fileExtensions.toArray(new String[RasterAdapter.fileExtensions.size()]));
    }

    @Override
    protected Builder importFile(File file, IParameters<String> userData, IMonitor monitor) {
        try {

            Builder builder = validator.validate(file.toURI().toURL(), userData, monitor);

            if (builder != null) {
                String layerId = MiscUtilities.getFileBaseName(file).toLowerCase();
                builder.withLocalName(layerId).setResourceId(layerId);
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
    public Collection<Triple<String, String, String>> getExportCapabilities(IObservation observation) {
        List<Triple<String, String, String>> ret = new ArrayList<>();

        if (observation instanceof IState) {
            if (observation.getScale().getSpace() != null && observation.getScale().getSpace().isRegular()
                    && observation.getScale().isSpatiallyDistributed()) {
                ret.add(new Triple<>("tiff", "GeoTIFF raster", "tiff"));
                ret.add(new Triple<>("png", "PNG image", "png"));
            }
        }

        return ret;
    }

    @Override
    public File exportObservation(File file, IObservation observation, ILocator locator, String format, IMonitor monitor) {

        if (observation instanceof IState && observation.getGeometry().getDimension(Type.SPACE) != null) {

            if (observation.getScale().isSpatiallyDistributed()
                    && observation.getScale().getSpace().isRegular()) {

                GridCoverage2D coverage = GeotoolsUtils.INSTANCE
                        .stateToCoverage((IState) observation, locator, Float.NaN);

                if (format.equalsIgnoreCase("tiff")) {
                    try {
                        GeoTiffWriter writer = new GeoTiffWriter(file);
                        writer.write(coverage, null);
                        return file;
                    } catch (IOException e) {
                        return null;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Map<String, String> getExportCapabilities(IResource resource) {
        Map<String, String> ret = new HashMap<>();
        ret.put("tiff", "GeoTiff");
        return ret;
    }

    @Override
    public boolean exportResource(File file, IResource resource, String format) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean importIntoResource(URL importLocation, IResource target, IMonitor monitor) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean resourceCanHandle(IResource resource, String importLocation) {
        // TODO Auto-generated method stub
        return false;
    }

}
