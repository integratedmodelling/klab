package org.integratedmodelling.klab.stac;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacItem;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
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
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.ogc.STACAdapter;
import org.integratedmodelling.klab.raster.files.RasterEncoder;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Polygon;

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

		HMStacCollection collection = service.getCollectionById(resource.getParameters().get("collectionId", String.class));
		if (collection == null) {
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
		HMStacCollection collection = service.getCollectionById(resource.getParameters().get("collectionId", String.class));
		if (collection == null) {
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

			List<HMStacItem> items = collection.setGeometryFilter(poly)
					.setTimestampFilter(new Date(start.getMilliseconds()), new Date(end.getMilliseconds()))
					.searchItems();

			if (items.isEmpty()) {
				throw new KlabIllegalStateException("No STAC items found for this context.");
			}

			LogProgressMonitor lpm = new LogProgressMonitor();
			IGrid grid = space.getGrid();

			RegionMap region = RegionMap.fromBoundsAndGrid(space.getEnvelope().getMinX(), space.getEnvelope().getMaxX(),
					space.getEnvelope().getMinY(), space.getEnvelope().getMaxY(), (int) grid.getXCells(),
					(int) grid.getYCells());

			ReferencedEnvelope regionEnvelope = new ReferencedEnvelope(region.toEnvelope(),
					space.getProjection().getCoordinateReferenceSystem());
			RegionMap regionTransformed = RegionMap.fromEnvelopeAndGrid(regionEnvelope, (int) grid.getXCells(),
					(int) grid.getYCells());
			Set<Integer> ESPGsAtItems = items.stream().map(i -> i.getEpsg()).collect(Collectors.toUnmodifiableSet());
			if (ESPGsAtItems.size() > 1) {
				scope.getMonitor().warn("Multiple ESPGs found on the items " + ESPGsAtItems.toString() + ". The transformation process could affect the data.");
			}

			// Allow transform ensures the process to finish, but I would not bet on the resulting data.
			final boolean allowTransform = true;
			String bandName = resource.getParameters().contains("band") ? resource.getParameters().get("band", String.class) : "undefined title";
			HMRaster outRaster = HMStacCollection.readRasterBandOnRegion(regionTransformed, bandName, items, allowTransform, lpm);

			coverage = outRaster.buildCoverage();
			scope.getMonitor().info("Coverage: " + coverage);
		} catch (Exception e) {
			scope.getMonitor().error("Cannot create STAC file. " + e.getMessage());
		}

		encoder.encodeFromCoverage(resource, urnParameters, coverage, geometry, builder, scope);
	}

	@Override
	public void listDetail(IResource resource, OutputStream stream, boolean verbose, IMonitor monitor) {
		// TODO Auto-generated method stub

	}

}
