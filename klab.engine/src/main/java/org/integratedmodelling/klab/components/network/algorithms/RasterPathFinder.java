/*
 * Copyright (C) 2014 Laboratoire ThéMA - UMR 6049 - CNRS / Université de Franche-Comté
 * http://thema.univ-fcomte.fr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.integratedmodelling.klab.components.network.algorithms;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygonal;
import java.awt.image.BandedSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferDouble;
import java.awt.image.DataBufferFloat;
import java.awt.image.Raster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Calculates leastcost path between patches or between points.
 * 
 * This class is not thread safe. Create one instance for each thread. Uses
 * dijkstra algorithm.
 * 
 * @author Gilles Vuidel
 */
public final class RasterPathFinder implements SpacePathFinder {

	private final Raster rasterPatch;
	private final Raster costRaster;
	private final Raster demRaster;

	private final double[] cost;
	private final double coefSlope;

	private final Project project;

	private final double resolution;
	private final int CON = 8;
	private final int[] X = new int[] { -1, 0, 0, +1, -1, -1, +1, +1 };
	private final int[] Y = new int[] { 0, -1, +1, 0, -1, +1, -1, +1 };
	private final double[] COST = new double[] { 1.0, 1.0, 1.0, 1.0, 1.4142136, 1.4142136, 1.4142136, 1.4142136 };
	private final int[] IND, IND_ANTE;
	private final boolean doublePrec;

	private PixelPriorityQueue queue;

	private int xd, yd, wd, hd;
	private double[] distDouble;
	private float[] distFloat;
	private byte[] ante;

	/**
	 * Create pathfinder from landscape map
	 * 
	 * @param prj        the project
	 * @param codeRaster
	 * @param cost
	 * @param coefSlope  0 = ignore slope
	 * @throws IOException
	 */
	public RasterPathFinder(Project prj, Raster codeRaster, double[] cost, double coefSlope) {
		this.project = prj;
		this.rasterPatch = null; // project.getRasterPatch();
		this.costRaster = codeRaster;
		this.cost = cost != null ? Arrays.copyOf(cost, cost.length) : null;
		this.coefSlope = coefSlope;
		int w = rasterPatch.getWidth();
		IND = new int[] { -1, -w, +w, +1, -w - 1, +w - 1, -w + 1, +w + 1 };
		IND_ANTE = new int[] { +1, +w, -w, -1, w + 1, -w + 1, +w - 1, -w - 1 };

		demRaster = coefSlope != 0 ? project.getDemRaster() : null;
		resolution = project.getResolution();
		doublePrec = true;// !MpiLauncher.IsMPIWorker();
	}

	/**
	 * Create pathfinder from external cost raster
	 * 
	 * @param prj        the project
	 * @param costRaster
	 * @param coefSlope  0 = ignore slope
	 * @throws IOException
	 */
	public RasterPathFinder(Project prj, Raster costRaster, double coefSlope) {
		this(prj, costRaster, null, coefSlope);
	}

	/**
	 * Initialize pathfinder from origin coord
	 * 
	 * @param coord
	 */
	private void initCoord(Coordinate coord) {
		Coordinate cp = project.getSpace2grid().transform(coord, new Coordinate());
		initCoord((int) cp.x, (int) cp.y);
	}

	/**
	 * Initialize pathfinder from origin (rx, ry) in raster coordinate
	 * 
	 * @param rx
	 * @param ry
	 */
	private void initCoord(int rx, int ry) {
		queue = new PixelPriorityQueue();

		initDistBuf(rx - 100, ry - 100, 200, 200);
		final int w = rasterPatch.getWidth();
		// starting node
		queue.add(ry * w + rx, 0);
		setDist(ry * w + rx, 0);
	}

	/**
	 * Initialize pathfinder from geom
	 * 
	 * @param geom must be polygonal
	 * @throws IllegalArgumentException if geom is not polygonal
	 */
	private void initGeom(Geometry geom) {
		if (!(geom instanceof Polygonal)) {
			throw new IllegalArgumentException("Geometry must be polygonal");
		}
		final int w = rasterPatch.getWidth();
		GeometryFactory geomFactory = geom.getFactory();
		Geometry geomGrid = project.getSpace2grid().transform(geom);
		Envelope env = geomGrid.getEnvelopeInternal();

		initDistBuf((int) env.getMinX() - 100, (int) env.getMinY() - 100, (int) env.getWidth() + 200,
				(int) env.getHeight() + 200);

		queue = new PixelPriorityQueue();
		for (double y = (int) env.getMinY() + 0.5; y <= Math.ceil(env.getMaxY()); y++) {
			for (double x = (int) env.getMinX() + 0.5; x <= Math.ceil(env.getMaxX()); x++) {
				if (geomGrid.contains(geomFactory.createPoint(new Coordinate(x, y)))) {
					queue.add((int) y * w + (int) x, 0);
					setDist((int) y * w + (int) x, 0);
				}
			}
		}

	}

	/**
	 * Initialize pathfinder from origin oPatch.<br/>
	 * All pixels of oPatch are initialize to zero or just the border depending on
	 * initAll. initAll parameter is used only for keeping exact numeric results
	 * with previous version which always initialize only the border. Without this
	 * param, the cost is always the same but the length of the path vary slightly
	 * with previous versions and breaks the tests
	 * 
	 * @param oPatch
	 * @param initAll init all pixels of the patch to 0 or just border pixels
	 */
	private void initPatch(Feature oPatch, boolean initAll) {

		final int w = rasterPatch.getWidth();
		final int[] tab = new int[9];

		final int id = (Integer) oPatch.getId();

		Envelope env = oPatch.getGeometry().getEnvelopeInternal();
		Geometry gEnv = new GeometryFactory().toGeometry(env);
		gEnv.apply(project.getSpace2grid());
		env = gEnv.getEnvelopeInternal();

		initDistBuf((int) env.getMinX() - 100, (int) env.getMinY() - 100, (int) env.getWidth() + 200,
				(int) env.getHeight() + 200);

		// ajout dans la queue des pixels de bord
		queue = new PixelPriorityQueue();
		for (int i = (int) env.getMinY(); i <= env.getMaxY(); i++) {
			for (int j = (int) env.getMinX(); j <= env.getMaxX(); j++) {
				if (rasterPatch.getSample(j, i, 0) == id) {
					rasterPatch.getPixels(j - 1, i - 1, 3, 3, tab);
					boolean border = false;
					for (int k = 0; !border && k < 9; k++) {
						if (tab[k] != id) {
							border = true;
						}
					}
					if (border) {
						queue.add(i * w + j, 0);
					}
					if (border || initAll) {
						setDist(j, i, 0);
					}
				}
			}
		}
	}

	/**
	 * Calcule les distances cout à partir du point p vers tous les destinations
	 * dests
	 * 
	 * @param p     start point
	 * @param dests destination points
	 * @return les couts et longueurs des chemins de p vers les destinations
	 */
	@Override
	public List<double[]> calcPaths(Coordinate p, List<Coordinate> dests) {
		initCoord(p);
		final int w = rasterPatch.getWidth();
		HashSet<Integer> indDests = new HashSet<>();
		for (Coordinate dest : dests) {
			Coordinate cp = project.getSpace2grid().transform(dest, new Coordinate());
			int rx = (int) cp.x;
			int ry = (int) cp.y;
			indDests.add(ry * w + rx);
		}

		while (!queue.isEmpty() && !indDests.isEmpty()) {
			int ind = updateNextNodes(queue, false, false);
			indDests.remove(ind);
		}

		List<double[]> distances = new ArrayList<>(dests.size());
		for (Coordinate dest : dests) {
			Coordinate cp = project.getSpace2grid().transform(dest, new Coordinate());
			int rx = (int) cp.x;
			int ry = (int) cp.y;
			distances.add(new double[] { (double) getDist(ry * w + rx), getPath(ry * w + rx).getLength() });
		}
		return distances;
	}

	/**
	 * Calcule les distances cout à partir du point p vers tous les destinations
	 * dests en restant à l'intérieur du patch. Utilisé pour calculer les distances
	 * intra taches
	 * 
	 * @param p     start point
	 * @param dests destination points
	 * @return les couts et longueurs des chemins de p vers les destinations
	 */
//    @Override
	public List<double[]> calcPathsInsidePatch(Coordinate p, List<Coordinate> dests) {
		initCoord(p);

		while (!queue.isEmpty()) {
			updateNextNodes(queue, false, true);
		}

		final int w = rasterPatch.getWidth();
		List<double[]> distances = new ArrayList<>(dests.size());
		for (Coordinate dest : dests) {
			Coordinate cp = project.getSpace2grid().transform(dest, new Coordinate());
			int rx = (int) cp.x;
			int ry = (int) cp.y;
			distances.add(new double[] { (double) getDist(ry * w + rx), getPath(ry * w + rx).getLength() });
		}
		return distances;
	}

	@Override
	public HashMap<DefaultFeature, Path> calcPaths(Coordinate p, double maxCost, boolean realPath) {
		return calcPaths(new GeometryFactory().createPoint(p), maxCost, realPath);
	}

	@Override
	public HashMap<DefaultFeature, Path> calcPaths(Geometry geom, double maxCost, boolean realPath) {
		if (geom instanceof Point) {
			initCoord(((Point) geom).getCoordinate());
		} else {
			initGeom(geom);
		}

		DefaultFeature geomPatch = new DefaultFeature(geom.getCentroid().getCoordinate().toString(), geom);
		HashMap<DefaultFeature, Path> paths = new HashMap<>();
		while (!queue.isEmpty()) {
			int ind = updateNextNodes(queue, true, false);
			if (maxCost > 0 && getDist(ind) > maxCost) {
				break;
			}
			int curId = rasterPatch.getSample(getX(ind), getY(ind), 0);
			if (curId > 0) {
				DefaultFeature dest = project.getPatch(curId);
				if (!paths.keySet().contains(dest)) {
					LineString line = getPath(ind);
					if (realPath) {
						paths.put(dest, new Path(geomPatch, dest, getDist(ind), line));
					} else {
						paths.put(dest, new Path(geomPatch, dest, getDist(ind), line.getLength()));
					}
				}
			}
		}

		return paths;
	}

	/**
	 * Calculates the paths from oPatch to all other patches if all == false,
	 * calculates for patches where id is greater than oPatch id
	 * 
	 * @param oPatch   the origin patch
	 * @param realPath keep the real path or just a straight line between centroid
	 *                 patches ?
	 * @return for each destination patch the path from oPatch
	 */
	public HashMap<Feature, Path> calcPaths(Feature oPatch, double maxCost, boolean realPath, boolean all) {

		initPatch(oPatch, false);

		final int id = (Integer) oPatch.getId();

		HashMap<Feature, Path> distances = new HashMap<>();
		while (!queue.isEmpty()) {
			int ind = updateNextNodes(queue, false, false);
			if (maxCost > 0 && getDist(ind) > maxCost) {
				break;
			}
			int curId = rasterPatch.getSample(getX(ind), getY(ind), 0);
			if (curId > 0 && curId != id && (all || curId > id)) {
				Feature dest = project.getPatch(curId);
				if (distances.keySet().contains(dest)) {
					continue;
				}

				LineString line = getPath(ind);
				distances.put(dest, realPath ? new Path(dest, oPatch, getDist(ind), line)
						: new Path(dest, oPatch, getDist(ind), line.getLength()));
			}
		}

		return distances;
	}

	/**
	 * Calculates the paths from oPatch to all dPatch
	 * 
	 * @param oPatch  the origin patch
	 * @param dPatch  the destinations patches
	 * @param maxCost maximal distance, zero for no maximum
	 * @return for each destination patch the path from oPatch
	 */
	public HashMap<Feature, Path> calcPaths(Feature oPatch, Collection<Feature> dPatch, double maxCost) {

		initPatch(oPatch, false);

		HashMap<Integer, Feature> destId = new HashMap<>();
		for (Feature f : dPatch) {
			destId.put((Integer) f.getId(), f);
		}

		HashMap<Feature, Path> distances = new HashMap<>();
		while (!queue.isEmpty() && !destId.isEmpty()) {
			int ind = updateNextNodes(queue, false, false);
			if (maxCost > 0 && getDist(ind) > maxCost) {
				break;
			}
			int curId = rasterPatch.getSample(getX(ind), getY(ind), 0);
			if (curId > 0 && destId.keySet().contains(curId)) {
				Feature dest = destId.remove(curId);
				LineString line = getPath(ind);
				distances.put(dest, new Path(dest, oPatch, getDist(ind), line));
			}
		}

		return distances;
	}

//    @Override
	public double[] calcPathNearestPatch(Point p) {
		Coordinate cp = project.getSpace2grid().transform(p.getCoordinate(), new Coordinate());
		return calcPathNearestPatch((int) cp.x, (int) cp.y);
	}

	/**
	 * Calc nearest patch from raster coordinate
	 * 
	 * @param rx the x coordinate on the raster
	 * @param ry the y coordinate on the raster
	 * @return an array with id of nearest patch, cost and dist
	 */
	public double[] calcPathNearestPatch(int rx, int ry) {

		initCoord(rx, ry);

		while (!queue.isEmpty()) {
			int ind = updateNextNodes(queue, false, false);

			int curId = rasterPatch.getSample(getX(ind), getY(ind), 0);
			if (curId > 0) {
				double len = getPath(ind).getLength();
				return new double[] { curId, getDist(ind), len };
			}

		}

		throw new RuntimeException("Impossible de calculer le chemin !");

	}

	/**
	 * Calcule la surface en pixel autour du patch oPatch jusqu'à une distance
	 * maxCost pour les codes du raster contenus dans codes
	 * 
	 * @param oPatch       origin patch
	 * @param maxCost      max cost distance
	 * @param codes        set of codes included in sum
	 * @param costWeighted if true sum is weighted by cost
	 * @return
	 */
	public double getNeighborhood(Feature oPatch, double maxCost, Raster rasterCode, HashSet<Integer> codes,
			boolean costWeighted) {

		initPatch(oPatch, true);

		double neighborhood = 0;
		double alpha = -Math.log(0.05) / maxCost;
		HashSet<Integer> counts = new HashSet<>();

		while (!queue.isEmpty()) {
			int ind = updateNextNodes(queue, false, false);
			if (getDist(ind) > maxCost) {
				break;
			}
			int code = rasterCode.getSample(getX(ind), getY(ind), 0);
			if (getDist(ind) > 0 && codes.contains(code) && !counts.contains(ind)) {
				neighborhood += costWeighted ? Math.exp(-alpha * getDist(ind)) : 1;
				counts.add(ind);
			}
		}

		return neighborhood;
	}

	/**
	 * Return a raster of cost distances from oPatch.
	 * 
	 * The raster may be smaller than landscape raster if maxCost &gt; 0
	 * 
	 * @param oPatch  patch origin
	 * @param maxCost max cost distance, if 0 : no max
	 * @return
	 */
	public Raster getDistRaster(Feature oPatch, double maxCost) {

		initPatch(oPatch, true);

		while (!queue.isEmpty()) {
			int ind = updateNextNodes(queue, false, false);
			if (maxCost > 0 && getDist(ind) > maxCost) {
				break;
			}
		}
		if (doublePrec) {
			return Raster.createRaster(new BandedSampleModel(DataBuffer.TYPE_DOUBLE, wd, hd, 1),
					new DataBufferDouble(distDouble, distDouble.length), new java.awt.Point(xd, yd));
		} else {
			return Raster.createRaster(new BandedSampleModel(DataBuffer.TYPE_DOUBLE, wd, hd, 1),
					new DataBufferFloat(distFloat, distFloat.length), new java.awt.Point(xd, yd));
		}
	}

	/**
	 * 
	 * @param ind end index of the path
	 * @return the path geometry in world coordinate
	 */
	private LineString getPath(int ind) {
		int w = rasterPatch.getWidth();
		ArrayList<Coordinate> coords = new ArrayList<>();
		coords.add(new Coordinate(ind % w + 0.5, ind / w + 0.5));
		int curInd = ind;
		int idAnte = getAnte(ind);
		while (idAnte != -1) {
			curInd += IND_ANTE[idAnte];
			Coordinate coord = new Coordinate(curInd % w + 0.5, curInd / w + 0.5);
			coords.add(coord);
			idAnte = getAnte(curInd);
		}
		// simplify linestring
		ArrayList<Coordinate> simpCoords = new ArrayList<>();
		for (int i = 1; i < coords.size() - 1; i++) {
			Coordinate precCoord = coords.get(i - 1);
			Coordinate coord = coords.get(i);
			Coordinate nextCoord = coords.get(i + 1);
			double a1 = (coord.x - precCoord.x) / (coord.y - precCoord.y);
			double a2 = (nextCoord.x - coord.x) / (nextCoord.y - coord.y);
			if (a1 != a2) {
				simpCoords.add(coord);
			}
		}
		simpCoords.add(0, coords.get(0));
		simpCoords.add(coords.get(coords.size() - 1));
		LineString line = new GeometryFactory().createLineString(simpCoords.toArray(new Coordinate[simpCoords.size()]));
		line.apply(project.getGrid2space());
		return line;
	}

	/**
	 * 
	 * @param queue          current queue
	 * @param startFromPatch true for using patch cost defined by cost array for the
	 *                       first pixel (used for patch addition)
	 * @param insidePatch    true for limiting calculation inside the patch (used
	 *                       for intrapatch distance calculation)
	 * @return
	 */
	private double[] node = new double[2]; // used only for this function to avoid many allocations

	private int updateNextNodes(PixelPriorityQueue queue, boolean startFromPatch, boolean insidePatch) {
		queue.poll(node);
		int currentInd = (int) node[0];
		double currentDist = node[1];
		while (!queue.isEmpty() && currentDist > getDist(currentInd)) {
			queue.poll(node);
			currentInd = (int) node[0];
			currentDist = node[1];
		}

		final int x = currentInd % rasterPatch.getWidth();
		final int y = currentInd / rasterPatch.getWidth();
		double currentCost = getCost(x, y);
		if (startFromPatch && cost != null && currentDist == 0) {
			currentCost = cost[project.getPatchCodes().iterator().next()];
		}
		int patchId = rasterPatch.getSample(x, y, 0);
		for (int i = 0; i < CON; i++) {
			if (isInside(x + X[i], y + Y[i])) {
				if (insidePatch && rasterPatch.getSample(x + X[i], y + Y[i], 0) != patchId) {
					continue;
				}
				final double c = getCost(x + X[i], y + Y[i]);
				final double newCost = currentDist + (COST[i] * (currentCost + c) / 2)
						* (1 + (coefSlope != 0 ? getSlope(x, y, i) * coefSlope : 0));
				if (newCost <= currentDist) {
					throw new IllegalStateException("Negative or null cost is forbidden. Check your cost.");
				}
				final int ind = currentInd + IND[i];
				if (newCost < getDist(ind)) {
					setDist(ind, newCost);
					setAnte(ind, (byte) i);
					queue.add(ind, newCost);
				}
			}
		}

		return currentInd;
	}

	private boolean isInside(int x, int y) {
		return rasterPatch.getSample(x, y, 0) > -1;// && (zone == null || zone.contains(x, y));
	}

	private double getCost(int x, int y) {
		return cost == null ? costRaster.getSampleDouble(x, y, 0) : cost[costRaster.getSample(x, y, 0)];
	}

	private double getSlope(int x, int y, int dir) {
		return Math.abs(demRaster.getSampleDouble(x + X[dir], y + Y[dir], 0) - demRaster.getSampleDouble(x, y, 0))
				/ (COST[dir] * resolution);
	}

	private int getX(int ind) {
		return ind % rasterPatch.getWidth();
	}

	private int getY(int ind) {
		return ind / rasterPatch.getWidth();
	}

	private void initDistBuf(int x, int y, int w, int h) {
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		if (x + w > rasterPatch.getWidth()) {
			w = rasterPatch.getWidth() - x;
		}
		if (y + h > rasterPatch.getHeight()) {
			h = rasterPatch.getHeight() - y;
		}

		ante = new byte[w * h];
		Arrays.fill(ante, (byte) -1);

		if (doublePrec) {
			distDouble = new double[w * h];
			Arrays.fill(distDouble, Double.MAX_VALUE);
		} else {
			distFloat = new float[w * h];
			Arrays.fill(distFloat, Float.MAX_VALUE);
		}

		xd = x;
		yd = y;
		wd = w;
		hd = h;
	}

	private double getDist(int ind) {
		int x = getX(ind) - xd;
		int y = getY(ind) - yd;
		if (x < 0 || x >= wd || y < 0 || y >= hd) {
			resizeDistBuf();
			return doublePrec ? Double.MAX_VALUE : Float.MAX_VALUE;
		} else {
			return doublePrec ? distDouble[y * wd + x] : distFloat[y * wd + x];
		}

	}

	private void setDist(int x, int y, double val) {
		int xi = x - xd;
		int yi = y - yd;
		if (xi < 0 || xi >= wd || yi < 0 || yi >= hd) {
			resizeDistBuf();
			setDist(x, y, val);
		} else {
			if (doublePrec) {
				distDouble[yi * wd + xi] = val;
			} else {
				distFloat[yi * wd + xi] = (float) val;
			}
		}
	}

	private void setDist(int ind, double val) {
		int x = getX(ind) - xd;
		int y = getY(ind) - yd;
		if (x < 0 || x >= wd || y < 0 || y >= hd) {
			resizeDistBuf();
			setDist(ind, val);
		} else {
			if (doublePrec) {
				distDouble[y * wd + x] = val;
			} else {
				distFloat[y * wd + x] = (float) val;
			}
		}
	}

	private byte getAnte(int ind) {
		int x = getX(ind) - xd;
		int y = getY(ind) - yd;
		return ante[y * wd + x];
	}

	private void setAnte(int ind, byte val) {
		int x = getX(ind) - xd;
		int y = getY(ind) - yd;
		if (x < 0 || x >= wd || y < 0 || y >= hd) {
			resizeDistBuf();
			setAnte(ind, val);
		} else {
			ante[y * wd + x] = val;
		}
	}

	private void resizeDistBuf() {
		int w = (int) (wd * 1.4 + 1);
		int h = (int) (hd * 1.4 + 1);
		int x = xd - (int) (wd * 0.2 + 1);
		int y = yd - (int) (hd * 0.2 + 1);
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		if (x + w > rasterPatch.getWidth()) {
			w = rasterPatch.getWidth() - x;
		}
		if (y + h > rasterPatch.getHeight()) {
			h = rasterPatch.getHeight() - y;
		}

		if (doublePrec) {
			double[] buf = new double[w * h];
			Arrays.fill(buf, Double.MAX_VALUE);
			for (int i = 0; i < hd; i++) {
				for (int j = 0; j < wd; j++) {
					buf[(i + yd - y) * w + j + (xd - x)] = distDouble[i * wd + j];
				}
			}
			distDouble = buf;
		} else {
			float[] buf = new float[w * h];
			Arrays.fill(buf, Float.MAX_VALUE);
			for (int i = 0; i < hd; i++) {
				for (int j = 0; j < wd; j++) {
					buf[(i + yd - y) * w + j + (xd - x)] = distFloat[i * wd + j];
				}
			}
			distFloat = buf;
		}

		byte[] bufAnte = new byte[w * h];
		Arrays.fill(bufAnte, (byte) -1);
		for (int i = 0; i < hd; i++) {
			for (int j = 0; j < wd; j++) {
				bufAnte[(i + yd - y) * w + j + (xd - x)] = ante[i * wd + j];
			}
		}

		xd = x;
		yd = y;
		wd = w;
		hd = h;
		ante = bufAnte;
	}
}
