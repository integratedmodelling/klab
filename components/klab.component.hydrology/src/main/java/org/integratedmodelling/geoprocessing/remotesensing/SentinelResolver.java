package org.integratedmodelling.geoprocessing.remotesensing;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.processing.Operations;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.hortonmachine.gears.io.stac.EarthAwsElement84Sentinel2Bands;
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
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
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

        
        String stacBand = null;
        for(EarthAwsElement84Sentinel2Bands sentinelBand: EarthAwsElement84Sentinel2Bands.values()) {
            if(sentinelBand.name().equals(band)) {
                stacBand = sentinelBand.getRealName();
            }
        }
         
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
        
        // Sentinel will take max 5 days to cover the whole world
        Resolution resolution = org.integratedmodelling.klab.components.time.extents.Time.resolution(1,
                ITime.Resolution.Type.DAY);
        ITimeInstant end = start.plus(5, resolution);

        try (HMStacManager stacManager = new HMStacManager(repoUrl, taskMonitor)) {
            stacManager.open();

            HMStacCollection collection = stacManager.getCollectionById(collectionId);

            List<HMStacItem> items = collection
                    .setTimestampFilter(new Date(start.getMilliseconds()), new Date(end.getMilliseconds()))
                    .setGeometryFilter(intersectionGeometry).searchItems();
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
                
                // apply averaging based on the amount of times the cell has been assigned a value
                outRaster.applyCountAverage(taskMonitor);
                
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
        ret.band = EarthAwsElement84Sentinel2Bands.red.name();
        return ret;
    }

}
