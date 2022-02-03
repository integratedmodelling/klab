package org.integratedmodelling.klab.components.network.algorithms;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import java.util.HashMap;
import java.util.List;

/**
 * Calculates paths between patches or between points.
 * 
 * @author Gilles Vuidel
 */
public interface SpacePathFinder {

	/**
	 * Calcule les distances à partir du point p vers toutes les destinations dests
	 * 
	 * @param p     the origin coordinate
	 * @param dests the destination coordinates
	 * @return cost and length of paths between p and ech dests
	 */
	public List<double[]> calcPaths(Coordinate p, List<Coordinate> dests);

	/**
	 * Calcule les distances à partir du point p vers toutes les destinations dests
	 * en restant à l'intérieur de la tache
	 * 
	 * @param p     the origin coordinate
	 * @param dests the destination coordinates
	 * @return cost and length of paths between p and ech dests
	 */
	public List<double[]> calcPathsInsidePatch(Coordinate p, List<Coordinate> dests);

	/**
	 * Calcule les chemins à partir du point p vers tous les patch dont la distance
	 * cout est inférieure ou égale à maxCost
	 * 
	 * @param p        the origin coordinate
	 * @param maxCost  maximal distance, zero for no maximum
	 * @param realPath keep the real path or just a straight line between centroid
	 *                 patches ?
	 * @return for each destination patch the path from oPatch
	 */
	public HashMap<DefaultFeature, Path> calcPaths(Coordinate p, double maxCost, boolean realPath);

	/**
	 * Calcule les chemins à partir de la géométrie geom vers tous les patch dont la
	 * distance cout est inférieure ou égale à maxCost
	 * 
	 * @param geom     can be Point or Polygonal
	 * @param maxCost  maximal distance, zero for no maximum
	 * @param realPath keep the real path or just a straight line between centroid
	 *                 patches ?
	 * @return for each destination patch the path from oPatch
	 */
	public HashMap<DefaultFeature, Path> calcPaths(Geometry geom, double maxCost, boolean realPath);

	/**
	 * Calc nearest patch from point p
	 * 
	 * @param p the origin point
	 * @return an array with id of nearest patch, cost and dist
	 */
	public double[] calcPathNearestPatch(Point p);

}
