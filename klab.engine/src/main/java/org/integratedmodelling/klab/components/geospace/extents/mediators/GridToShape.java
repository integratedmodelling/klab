package org.integratedmodelling.klab.components.geospace.extents.mediators;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.utils.Pair;

public class GridToShape implements IScaleMediator {

	public GridToShape(Grid grid, Shape shape) {
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		return null;
	}

}
