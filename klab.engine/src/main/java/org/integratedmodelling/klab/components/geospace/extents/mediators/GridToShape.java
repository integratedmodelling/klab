package org.integratedmodelling.klab.components.geospace.extents.mediators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.IShape.Type;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.utils.Pair;

import org.locationtech.jts.geom.Point;

public class GridToShape implements IScaleMediator {

	private Subgrid subgrid;
	private long pointOffset = -1;

	public GridToShape(Shape shape, Grid grid) {
		if (shape.getGeometryType() == Type.POINT) {
			this.pointOffset = grid.getOffsetFromWorldCoordinates(((Point) shape.getJTSGeometry()).getX(),
					((Point) shape.getJTSGeometry()).getY());
		} else {
			this.subgrid = Subgrid.create(grid, shape);
		}
	}

	@Override
	public boolean isConformant() {
		return false;
	}

	@Override
	public long mapConformant(long originalOffset) {
		throw new IllegalStateException("GridToShape called with wrong state: grids and shape cannot be conformant");
	}

	@Override
	public Collection<Pair<Long, Double>> map(long originalOffset) {

		List<Pair<Long, Double>> ret = new ArrayList<>();
		if (subgrid == null) {
			ret.add(new Pair<>(pointOffset, 1.0));
		} else {
			for (Cell cell : subgrid) {
				Cell originalCell = subgrid.getOriginalCell(cell);
				ret.add(new Pair<>(originalCell.getOffsetInGrid(), 1.0));
			}
		}
		return ret;
	}

}
