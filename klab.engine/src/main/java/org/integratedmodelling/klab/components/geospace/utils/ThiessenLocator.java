package org.integratedmodelling.klab.components.geospace.utils;
/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpatial;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.utils.Pair;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.triangulate.VoronoiDiagramBuilder;

/**
 * An object initialized with a raster spatial scale and a set of geometric
 * shapes whose centroids lie in or around that area. Will produce a set of
 * Thiessen polygons describing the influence zone of each shape, cut to the
 * area, and it will create an index so that it can be queried for the index of
 * the shape selected to describe each cell in the raster.
 * 
 * @author Ferd
 *
 */
public class ThiessenLocator<T extends ISpatial> {

	List<Pair<Polygon, T>> pairs = new ArrayList<>();
	short[] index;
	List<T> objects;

	/*
	 * this isn't null only when we have a single object to distribute. Then we use
	 * that without any polygons.
	 */
	T object = null;

	public ThiessenLocator(IScale scale, List<T> objects) {

		Grid grid = Space.extractGrid(scale);

		this.objects = objects;

		SpatialDisplay debug = new SpatialDisplay(scale.getSpace());

		if (objects.size() == 1) {

			object = objects.get(0);

		} else if (objects.size() > 0) {

			/*
			 * generate object influence polygons and associate them to the objects in the
			 * order identified.
			 */
			VoronoiDiagramBuilder db = new VoronoiDiagramBuilder();
			ArrayList<Coordinate> sites = new ArrayList<>();
			for (ISpatial o : objects) {
				IShape s = o.getShape();
				Point point = ((Shape) s).getStandardizedGeometry().getCentroid();
				sites.add(new Coordinate(point.getX(), point.getY()));
				debug.add(Shape.create(((Shape) s).getStandardizedGeometry(), Projection.getDefault()));
			}
			db.setSites(sites);
			Geometry diag = db.getDiagram(new GeometryFactory());

			/*
			 * attribute objects to polygons
			 */
			int pols = 0;
			for (pols = 0; pols < diag.getNumGeometries(); pols++) {
				Geometry g = diag.getGeometryN(pols);
				debug.add(Shape.create(g, Projection.getDefault()), "original");
				for (int s = 0; s < objects.size(); s++) {
					if (g.intersects(((Shape) objects.get(s)).getStandardizedGeometry()) && g instanceof Polygon) {
						pairs.add(new Pair<>((Polygon) g, objects.get(s)));
					}
				}
			}

			/*
			 * build index of which object is where. Drop those that fall out of the context
			 * shape in the process.
			 */
			GeometryFactory pm = new GeometryFactory();
			index = new short[(int) scale.getSpace().size()];
			for (int i = 0; i < scale.getSpace().size(); i++) {
				double[] xy = grid.getCoordinates(i);
				Point point = pm.createPoint(new Coordinate(xy[0], xy[1]));
				for (int j = 0; j < pairs.size(); j++) {
					if (pairs.get(j).getFirst().intersects(point)) {
						index[i] = (short) (j + 1);
						break;
					}
				}
			}
		}
	}

	public T get(int offset) {
		return object == null ? (index == null ? null : pairs.get(index[offset] - 1).getSecond()) : object;
	}

}
