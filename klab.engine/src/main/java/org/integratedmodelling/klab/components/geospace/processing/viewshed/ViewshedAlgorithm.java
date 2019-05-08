package org.integratedmodelling.klab.components.geospace.processing.viewshed;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;

import com.vividsolutions.jts.geom.Point;

public class ViewshedAlgorithm {

	IState dem = null;
	IState out = null;
	ISpace space = null;
	Grid grid = null;
	Point viewpoint;
	
	List<ViewEvent> eventList = new ArrayList<>();

	public ViewshedAlgorithm(IState dem, IState output, IShape viewpoint) {
		
		this.dem = dem;
		this.out = output;
		this.space = dem.getSpace();
		
		if (this.space == null || !(this.space instanceof Space) || ((Space)this.space).getGrid() == null) {
			throw new IllegalArgumentException("viewshed algorithm must be run on a gridded context");
		}

		this.viewpoint = (Point)((Shape)viewpoint).getCentroid().getJTSGeometry();
		this.grid = (Grid) ((Space)this.space).getGrid();
	}
	
	/**
	 * Corresponds to in-memory version of Grass. Just use disk-backed states for disk raster.
	 */
	public void run() {

	}

}
