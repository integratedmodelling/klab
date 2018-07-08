package org.integratedmodelling.klab.components.geospace.extents.mediators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.utils.Pair;

public class GridToShape implements IScaleMediator {

	private Subgrid subgrid;

	public GridToShape(Grid grid, Shape shape) {
		this.subgrid = Subgrid.create(grid, shape);
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
		for (Cell cell : subgrid) {
			Cell originalCell = subgrid.getOriginalCell(cell);
			ret.add(new Pair<>(originalCell.getOffsetInGrid(), 1.0));
		}
		return ret;
	}

}
