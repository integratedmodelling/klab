package org.integratedmodelling.geoprocessing;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.Orientation;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.utils.Pair;

@Component(id = "org.integratedmodelling.geoprocessing", version = Version.CURRENT)
public class GeoprocessingComponent {

	public GeoprocessingComponent() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the orientation corresponding to the standard d8 (1-8)
	 * 
	 * @param d8
	 * @return
	 */
	public static Orientation getOrientation(int d8) {

		switch (d8) {
		case 1:
			return Orientation.E;
		case 2:
			return Orientation.SE;
		case 3:
			return Orientation.S;
		case 4:
			return Orientation.SW;
		case 5:
			return Orientation.W;
		case 6:
			return Orientation.NW;
		case 7:
			return Orientation.N;
		case 8:
			return Orientation.NE;
		}
		return null;
	}

	/**
	 * Translate D8 code to insane ArcGIS output format, for the corporate lovers.
	 * 
	 * @param d8
	 * @return
	 */
	public static int getD8Pow(int d8) {
		return (int) Math.pow(2, (8 - d8 + 1) % 8);
	}

	/**
	 * Util: return a list of all the cells that flow into the passed one.
	 * 
	 * @param cell
	 * @param flowDirectionsD8 the flow direction as exponent to 2 in the standard
	 *                         scheme (values 1-8)
	 * @return
	 */
	public static List<Cell> getUpstreamCells(Cell cell, IState flowDirectionsD8, Function<Cell, Boolean> check) {

		List<Cell> ret = new ArrayList<>();

		Cell neighbor = cell.getNeighbor(Orientation.NW);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 8) {
			if (check == null || check.apply(neighbor))
				ret.add(neighbor);
		}
		neighbor = cell.getNeighbor(Orientation.N);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 7) {
			if (check == null || check.apply(neighbor))
				ret.add(neighbor);
		}
		neighbor = cell.getNeighbor(Orientation.NE);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 6) {
			if (check == null || check.apply(neighbor))
				ret.add(neighbor);
		}
		neighbor = cell.getNeighbor(Orientation.E);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 5) {
			if (check == null || check.apply(neighbor))
				ret.add(neighbor);
		}
		neighbor = cell.getNeighbor(Orientation.SE);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 4) {
			if (check == null || check.apply(neighbor))
				ret.add(neighbor);
		}
		neighbor = cell.getNeighbor(Orientation.S);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 3) {
			if (check == null || check.apply(neighbor))
				ret.add(neighbor);
		}
		neighbor = cell.getNeighbor(Orientation.SW);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 2) {
			if (check == null || check.apply(neighbor))
				ret.add(neighbor);
		}
		neighbor = cell.getNeighbor(Orientation.W);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 1) {
			if (check == null || check.apply(neighbor))
				ret.add(neighbor);
		}
		return ret;
	}

	/**
	 * Util: return a list of all the cells that flow into the passed one along with
	 * their relative orientation to the focal cell.
	 * 
	 * @param cell
	 * @param flowDirectionsD8 the flow direction as exponent to 2 in the standard
	 *                         scheme (values 1-8)
	 * @return
	 */
	public static List<Pair<Cell, Orientation>> getUpstreamCellsWithOrientation(Cell cell, IState flowDirectionsD8,
			Function<Cell, Boolean> check) {

		List<Pair<Cell, Orientation>> ret = new ArrayList<>();

		Cell neighbor = cell.getNeighbor(Orientation.NW);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 8) {
			if (check == null || check.apply(neighbor))
				ret.add(new Pair<>(neighbor, Orientation.NW));
		}
		neighbor = cell.getNeighbor(Orientation.N);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 7) {
			if (check == null || check.apply(neighbor))
				ret.add(new Pair<>(neighbor, Orientation.N));
		}
		neighbor = cell.getNeighbor(Orientation.NE);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 6) {
			if (check == null || check.apply(neighbor))
				ret.add(new Pair<>(neighbor, Orientation.NE));
		}
		neighbor = cell.getNeighbor(Orientation.E);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 5) {
			if (check == null || check.apply(neighbor))
				ret.add(new Pair<>(neighbor, Orientation.E));
		}
		neighbor = cell.getNeighbor(Orientation.SE);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 4) {
			if (check == null || check.apply(neighbor))
				ret.add(new Pair<>(neighbor, Orientation.SE));
		}
		neighbor = cell.getNeighbor(Orientation.S);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 3) {
			if (check == null || check.apply(neighbor))
				ret.add(new Pair<>(neighbor, Orientation.S));
		}
		neighbor = cell.getNeighbor(Orientation.SW);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 2) {
			if (check == null || check.apply(neighbor))
				ret.add(new Pair<>(neighbor, Orientation.SW));
		}
		neighbor = cell.getNeighbor(Orientation.W);
		if (neighbor != null && flowDirectionsD8.get(neighbor, Double.class) == 1) {
			if (check == null || check.apply(neighbor))
				ret.add(new Pair<>(neighbor, Orientation.W));
		}
		return ret;
	}

	/**
	 * Downstream cell with its orientation, based on simple d8 (unique cell) scheme.
	 * 
	 * @param cell
	 * @param flowDirectionsD8
	 * @return the pair of cell and orientation, or null if flowdirs are NaN.
	 */
	public static Pair<Cell, Orientation> getDownstreamCellWithOrientation(Cell cell, IState flowDirectionsD8) {
		int fdir = flowDirectionsD8.get(cell, Double.class).intValue();
		Orientation orientation = getOrientation(fdir);
		Cell neighbor = orientation == null ? null : cell.getNeighbor(orientation);
		return neighbor == null ? null : new Pair<>(neighbor, orientation);
	}

}
