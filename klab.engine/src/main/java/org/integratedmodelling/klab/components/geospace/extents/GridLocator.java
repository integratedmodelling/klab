package org.integratedmodelling.klab.components.geospace.extents;

import java.util.List;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.data.storage.FileMappedStorage;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;

/**
 * Initialize with a grid, pass as many shapes as needed, and quickly figure out
 * which shape(s) a grid locator intersects. Uses a short int storage to index
 * the objects rather than using point-in-polygon algorithms.
 * 
 * @author Ferd
 *
 */
public class GridLocator {

	IStorage<Short> storage;
	IGrid grid;
	
	public GridLocator(IScale scale, IObjectArtifact artifacts) {
		if (scale.getSpace() instanceof Space && ((Space)scale.getSpace()).getGrid() != null) {
			this.grid = ((Space)scale.getSpace()).getGrid();
		}
		if (grid == null) {
			throw new KlabUnsupportedFeatureException("grid locator used on a non-gridded spatial extent");
		}
		storage = new FileMappedStorage<>(scale, Short.class);
	}
	
	public List<IObservation> getObservations(ILocator locator) {
		return null;
	}
	

}
