package org.integratedmodelling.klab.components.geospace.extents;

import java.util.BitSet;

import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Mask;
import org.integratedmodelling.klab.components.geospace.processing.Rasterizer;

public class GridMask extends BitSet implements IGrid.Mask {

	private static final long serialVersionUID = 1066602180194149853L;
	private IGrid grid;

	public GridMask(IGrid grid, IShape shape) {
		this.grid = grid;
		Rasterizer<Boolean> rasterizer = new Rasterizer<>(grid);
		rasterizer.add(shape, (s) -> true);
		rasterizer.finish((b, xy) -> { if (b != null && b) set((int)grid.getOffset(xy[0], xy[1])); } );
//		System.out.println("Mask has " + cardinality() + " out of " + grid.getCellCount());
	}

	@Override
	public void merge(Mask other, LogicalConnector connector) {
		if (connector.equals(LogicalConnector.UNION)) {
			or((GridMask) other);
		} else if (connector.equals(LogicalConnector.UNION)) {
			and((GridMask) other);
		} else
			throw new IllegalArgumentException("grid masks can only merge in UNION or INTERSECTION");
	}

	@Override
	public boolean isActive(long x, long y) {
		return get((int) grid.getOffset(x, y));
	}

	@Override
	public void activate(long x, long y) {
		set((int) grid.getOffset(x, y));
	}

	@Override
	public void deactivate(long x, long y) {
		set((int) grid.getOffset(x, y), false);
	}

	@Override
	public long totalActiveCells() {
		return cardinality();
	}

	@Override
	public long nextActiveOffset(long fromOffset) {
		return nextSetBit((int) fromOffset + 1);
	}

	@Override
	public void invert() {
		for (int i = 0; i < this.size(); i++) {
			flip(i);
		}
	}

	@Override
	public void deactivate() {
		clear();
	}

	@Override
	public void activate() {
		set(0, size());
	}

}
