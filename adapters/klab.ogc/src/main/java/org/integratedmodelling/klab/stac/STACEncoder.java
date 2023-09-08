package org.integratedmodelling.klab.stac;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacItem;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
import org.hortonmachine.gears.utils.CrsUtilities;
import org.hortonmachine.gears.utils.RegionMap;
import org.hortonmachine.gears.utils.geometry.GeometryUtilities;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.ogc.STACAdapter;
import org.integratedmodelling.klab.raster.files.RasterEncoder;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Polygon;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class STACEncoder implements IResourceEncoder {

	/**
	 * The raster encoder that does the actual work after we get our coverage from
	 * the service.
	 */
	RasterEncoder encoder = new RasterEncoder();

	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {
		STACService service = STACAdapter.getService(resource.getParameters().get("catalogUrl", String.class));

		if (service == null) {
			monitor.error("Service " + resource.getParameters().get("catalogUrl", String.class)
					+ " does not exist: likely the service URL is wrong or offline");
			return false;
		}

		Optional<HMStacCollection> collection;
		try {
			collection = service.getCollectionById(resource.getParameters().get("collectionId", String.class));
		} catch (Exception e) {
			monitor.error(
					"Collection " + resource.getParameters().get("catalogUrl", String.class) + " cannot be find.");
			return false;
		}

		if (collection.isEmpty()) {
			monitor.error(
					"Collection " + resource.getParameters().get("catalogUrl", String.class) + " cannot be find.");
			return false;
		}

		return true;
	}

	@Override
	public IResource contextualize(IResource resource, IScale scale, IArtifact targetObservation,
			Map<String, String> urnParameters, IContextualizationScope scope) {
		// TODO Auto-generated method stub
		return resource;
	}

	@Override
	public ICodelist categorize(IResource resource, String attribute, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IContextualizationScope scope) {
		STACService service = STACAdapter.getService(resource.getParameters().get("catalogUrl", String.class));
		Optional<HMStacCollection> collection = null;
		try {
			collection = service.getCollectionById(resource.getParameters().get("collectionId", String.class));
		} catch (Exception e) {
			scope.getMonitor().error(
					"Collection " + resource.getParameters().get("catalogUrl", String.class) + " cannot be find.");
		}

		GridCoverage2D coverage = null;

		Space space = (Space) geometry.getDimensions().stream().filter(d -> d instanceof Space).findFirst()
				.orElseThrow();
		Time time = (Time) geometry.getDimensions().stream().filter(d -> d instanceof Time).findFirst().orElseThrow();

		ITimeInstant start = time.getStart();
		ITimeInstant end = time.getEnd();

		/**
		 * TODO! Adjust time span depending of context (if generic, adjust to latest
		 * available, minding the resolution and semantics of request)
		 */

		IEnvelope envelope = space.getEnvelope();
		Envelope env = new Envelope(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY());
		Polygon poly = GeometryUtilities.createPolygonFromEnvelope(env);

		try {

			List<HMStacItem> items = collection.get().setGeometryFilter(poly)
					.setTimestampFilter(new Date(start.getMilliseconds()), new Date(end.getMilliseconds()))
					.searchItems();

			if (items.isEmpty()) {
				scope.getMonitor().warn("STAC resource did not return any items in this context");
				return;
			}

			LogProgressMonitor lpm = new LogProgressMonitor();
			IGrid grid = space.getGrid();

			RegionMap region = RegionMap.fromBoundsAndGrid(space.getEnvelope().getMinX(), space.getEnvelope().getMaxX(),
					space.getEnvelope().getMinY(), space.getEnvelope().getMaxY(), (int) grid.getXCells(),
					(int) grid.getYCells());

			Integer srid = items.get(0).getEpsg();
			CoordinateReferenceSystem outputCrs = CrsUtilities.getCrsFromSrid(srid);
			ReferencedEnvelope regionEnvelope = new ReferencedEnvelope(region.toEnvelope(), DefaultGeographicCRS.WGS84)
					.transform(outputCrs, true);
			RegionMap regionTransformed = RegionMap.fromEnvelopeAndGrid(regionEnvelope, (int) grid.getXCells(),
					(int) grid.getYCells());
			String assetId = resource.getParameters().get("asset", String.class);

			// TODO Inigo check this. I think this needs some discussion. Allow transform
			// ensures the process to finish, but I would not bet on the resulting data.
			boolean allowTransform = true;
			HMRaster outRaster = HMStacCollection.readRasterBandOnRegion(regionTransformed, assetId, items, true, lpm);

			coverage = outRaster.buildCoverage();
			scope.getMonitor().info("Coverage: " + coverage);
		} catch (Exception e) {
			scope.getMonitor().error("Cannot create STAC file." + e.getMessage());
		}

		encoder.encodeFromCoverage(resource, urnParameters, coverage, geometry, builder, scope);
	}

	@Override
	public void listDetail(IResource resource, OutputStream stream, boolean verbose, IMonitor monitor) {
		// TODO Auto-generated method stub

	}

}
