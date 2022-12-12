package org.integratedmodelling.geoprocessing.remotesensing;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.processing.Operations;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacItem;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.utils.CrsUtilities;
import org.hortonmachine.gears.utils.RegionMap;
import org.hortonmachine.gears.utils.geometry.GeometryUtilities;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class SentinelResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

    String band;
    final static HashMap<String, String> BANDS = new HashMap<>();
    static {
        BANDS.put("red", "Red (band 4) - 10m");
        BANDS.put("blue", "Blue (band 2) - 10m");
        BANDS.put("green", "Green (band 3) - 10m");
        BANDS.put("nir1", "NIR 1 (band 8) - 10m");
        BANDS.put("nir2", "NIR 2 (band 8A) - 20m");
        BANDS.put("nir3", "NIR 3 (band 9) - 60m");
        BANDS.put("swir1", "SWIR 1 (band 11) - 20m");
        BANDS.put("swir2", "SWIR 2 (band 12) - 20m");
    }

    @Override
    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public IState resolve(IState target, IContextualizationScope context) throws KlabException {
        TaskMonitor taskMonitor = new TaskMonitor(context.getMonitor());
        taskMonitor.setTaskName("Sentinel");

        String repoUrl = "https://earth-search.aws.element84.com/v1";
        String collectionId = "sentinel-2-l2a";

        ISpace space = target.getSpace();

        if (!(target.isSpatiallyDistributed() && space.isRegular())) {
            throw new KlabValidationException("Sentinel data can be retrieved only on a grid extent");
        }

        String stacBand = BANDS.get(band);
        if (stacBand == null) {
            throw new IllegalArgumentException(band + " is not an available band.");
        }

        ISpace ext = target.getScale().getSpace();
        IGrid grid = ((Space) ext).getGrid();

        double cols = grid.getXCells();
        double rows = grid.getYCells();
        double east = space.getEnvelope().getMaxX();
        double west = space.getEnvelope().getMinX();
        double north = space.getEnvelope().getMaxY();
        double south = space.getEnvelope().getMinY();
        RegionMap region = RegionMap.fromBoundsAndGrid(west, east, south, north, (int) cols, (int) rows);
        Polygon intersectionGeometry = GeometryUtilities.createPolygonFromEnvelope(region.toEnvelope());

        NumberFormat f = new DecimalFormat("00");
        ITimeInstant start = context.getScale().getTime().getStart();
        String day = start.getYear() + "-" + f.format(start.getMonth()) + "-" + f.format(start.getDay());

        try (HMStacManager stacManager = new HMStacManager(repoUrl, taskMonitor)) {
            stacManager.open();

            HMStacCollection collection = stacManager.getCollectionById(collectionId);

            List<HMStacItem> items = collection.setDayFilter(day, null).setGeometryFilter(intersectionGeometry).searchItems();
            int size = items.size();
            taskMonitor.message("Found " + size + " items matching the query.");

            if (size > 0) {
                Geometry coveredAreas = HMStacCollection.getCoveredArea(items);
                Geometry commonArea = coveredAreas.intersection(intersectionGeometry);
                double coveredArea = commonArea.getArea();
                double roiArea = intersectionGeometry.getArea();
                int percentage = (int) Math.round(coveredArea * 100 / roiArea);
                taskMonitor.message("Region of interest is covered by data in amout of " + percentage + "%");

                Integer srid = items.get(0).getEpsg();
                CoordinateReferenceSystem outputCrs = CrsUtilities.getCrsFromSrid(srid);
                ReferencedEnvelope regionEnvelopeTransformed = new ReferencedEnvelope(region.toEnvelope(),
                        DefaultGeographicCRS.WGS84).transform(outputCrs, true);
                RegionMap regionTransformed = RegionMap.fromEnvelopeAndGrid(regionEnvelopeTransformed, (int) cols, (int) rows);

                HMRaster outRaster = HMStacCollection.readRasterBandOnRegion(regionTransformed, stacBand, items, taskMonitor);
                GridCoverage2D outCoverage = outRaster.buildCoverage();

                if (!context.getMonitor().isInterrupted()) {
                    outCoverage = (GridCoverage2D) Operations.DEFAULT.resample(outCoverage, DefaultGeographicCRS.WGS84);
                    GeotoolsUtils.INSTANCE.coverageToState(outCoverage, target, context.getScale(), null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            taskMonitor.exceptionThrown(e.getLocalizedMessage());
        }

        return target;
    }

    @Override
    public Object eval(IContextualizationScope context, Object... params) throws KlabException {
        Parameters<String> parameters = Parameters.create(params);
        SentinelResolver ret = new SentinelResolver();
        ret.band = parameters.get("band", "nir1");
        return ret;
    }

}
