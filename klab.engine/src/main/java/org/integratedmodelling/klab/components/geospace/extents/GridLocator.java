package org.integratedmodelling.klab.components.geospace.extents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.processing.Rasterizer;
import org.integratedmodelling.klab.data.storage.FileMappedStorage;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NumberUtils;

/**
 * Initialize with a grid, pass as many shapes as needed, and quickly figure out
 * which shape(s) a grid locator intersects. Uses storage and pre-rasterization,
 * so it's fast in retrieval and slower to build. Also it creates an AWT image,
 * so it can't be used for arbitrarily large extents.
 * <p>
 * In this version, the polygons are not expected to overlap: if a polygon
 * overlaps another that comes before it, it will take over it in the areas of
 * overlap. Only one polygon will ve returned by
 * {@link #getObservations(ILocator)}. This is fine for tessellations, like
 * administrative regions, countries or watersheds, but not for other
 * observables. If overlaps must be recognized, a spatial index should be used.
 * 
 * @author Ferd
 *
 */
public class GridLocator {

	IStorage<Short> storage;
	IGrid grid;
	Map<Short, String> indices = Collections.synchronizedMap(new HashMap<>());
	Map<Short, IDirectObservation> objects = Collections.synchronizedMap(new HashMap<>());
	
	public GridLocator(IScale scale, IObjectArtifact artifacts) {
		if (scale.getSpace() instanceof Space && ((Space) scale.getSpace()).getGrid() != null) {
			this.grid = ((Space) scale.getSpace()).getGrid();
		}
		if (grid == null) {
			throw new KlabUnsupportedFeatureException("grid locator used on a non-gridded spatial extent");
		}
		Scale sscale = Scale.create(scale.getSpace());
		IGeometry geometry = sscale.asGeometry();
		storage = new FileMappedStorage<>(sscale, Short.class);
		short counter = 1;
		Rasterizer<Short> rasterizer = new Rasterizer<>(this.grid);
		for (IArtifact artifact : artifacts) {
			if (artifact instanceof IDirectObservation && ((IObservation) artifact).getScale().getSpace() != null) {
				IShape shape = ((IObservation) artifact).getScale().getSpace().getShape();
				final short id = counter++;
				rasterizer.add(shape, (s) -> {
					objects.put(id, (IDirectObservation) artifact);
					return id;
				});
			}
		}
		rasterizer.finish((n, vals) -> {
			storage.put(n, new Offset(geometry, NumberUtils.asLong(vals)));
		});
	}

	public List<IObservation> getObservations(ILocator locator) {
		List<IObservation> ret = new ArrayList<>();
		short n = storage.get(locator);
		if (n >= 1) {
			ret.add(objects.get(n));
		}
		return ret;
	}

}
