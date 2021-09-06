package org.integratedmodelling.klab.components.geospace.random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.utils.SpatialDisplay;
import org.integratedmodelling.klab.scale.Scale;

import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

/**
 * Generator of
 * 
 * @author Ferd
 *
 */
public enum RandomShapes {

	INSTANCE;

	Random random = new Random();

	/**
	 * Create a number of random boxes by subdividing the sides of an envelope into
	 * a specified number of divisions (approximate), then put a random shape in
	 * each with a specified number of vertices and probability. The resulting
	 * shapes may be of different area but will never overlap.
	 *
	 * @param envelope
	 * @param xdivs
	 * @param ydivs
	 * @param frequency
	 * @param vertices  if 1, generate a point; if 2, generate a line; if 3+,
	 *                  generate a polygon
	 * @return
	 */
	public Collection<IShape> create(IEnvelope envelope, int xdivs, int ydivs, double frequency, int vertices) {

		List<IShape> ret = new ArrayList<>();

		double xwidth = (envelope.getMaxX() - envelope.getMinX()) / xdivs;
		double ywidth = (envelope.getMaxY() - envelope.getMinY()) / ydivs;

		NormalDistribution xnormal = new NormalDistribution(xwidth, 0.1);
		NormalDistribution ynormal = new NormalDistribution(ywidth, 0.1);

		if (xdivs == 1 || ydivs == 1) {

			/*
			 * FIXME wrong: if any of the two is > 1 it should create a vbox/hbox.
			 */
			ret.add(createPolygon(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY(),
					vertices, envelope.getProjection()));

		} else {

			List<Double> xbreaks = new ArrayList<>();
		List<Double> ybreaks = new ArrayList<>();
		for (double xlimit = envelope.getMinX(); xlimit < envelope.getMaxX(); xlimit += randomIncrement(xwidth,
				xnormal)) {
			xbreaks.add(xlimit);
		}
		for (double ylimit = envelope.getMinY(); ylimit < envelope.getMaxY(); ylimit += randomIncrement(ywidth,
				ynormal)) {
			ybreaks.add(ylimit);
		}


			for (int x = 1; x < xbreaks.size(); x++) {
				for (int y = 1; y < ybreaks.size(); y++) {
					if (Math.random() <= frequency) {
						ret.add(createPolygon(xbreaks.get(x - 1), xbreaks.get(x), ybreaks.get(y - 1), ybreaks.get(y),
								vertices, envelope.getProjection()));
					}
				}

			}
		}

		return ret;

	}

	private double randomIncrement(double width, NormalDistribution distribution) {
		double avg = width;
		double shift = distribution.sample();
		double ret = avg + shift;
		if (ret < 0) {
			ret = random.nextDouble() * width;
		}
		if (ret > (width * 2)) {
			ret = random.nextDouble() * (width * 1.4);
		}
		return ret;
	}

	private IShape createPolygon(double x0, double x1, double y0, double y1, int vertices, IProjection projection) {

		Geometry poly = null;
		boolean valid = false;
		while (!valid) {
			ArrayList<Coordinate> points = new ArrayList<Coordinate>();
			for (int i = 0; i < vertices; i++) {
				double x = x0 + (random.nextDouble() * (x1 - x0));
				double y = y0 + (random.nextDouble() * (y1 - y0));
				points.add(new Coordinate(x, y));
			}
			poly = new ConvexHull(points.toArray(new Coordinate[points.size()]), Geospace.gFactory).getConvexHull();
			valid = poly.isValid();
		}
		return Shape.create(poly, projection);
	}

	public static void main(String[] args) {

		String tzShape = "EPSG:4326 POLYGON((33.796 -7.086, 35.946 -7.086, 35.946 -9.41, 33.796 -9.41, 33.796 -7.086))";
		IShape context = Shape.create(tzShape);
		SpatialDisplay display = new SpatialDisplay(Scale.create(context));
		int n = 1;
		for (IShape shape : INSTANCE.create(context.getEnvelope(), 4, 3, 1, 5)) {
			System.out.println((n++) + " [area=" + shape.getArea(Units.INSTANCE.SQUARE_METERS) + "] : " + shape);
			display.add(shape, "Random polygons v=5");
		}

		display.show();

	}

}
