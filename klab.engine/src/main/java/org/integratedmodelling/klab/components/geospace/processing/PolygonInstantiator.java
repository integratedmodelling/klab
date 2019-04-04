package org.integratedmodelling.klab.components.geospace.processing;

import java.awt.geom.Point2D;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import org.geotools.coverage.grid.GridCoverage2D;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.engine.runtime.code.groovy.GroovyExpression;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.Parameters;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import ij.IJ;
import ij.ImagePlus;
import ij.blob.Blob;
import ij.blob.ManyBlobs;
import ij.process.ImageProcessor;

public class PolygonInstantiator implements IExpression, IInstantiator {

	Descriptor exprDescriptor = null;
	private IGrid grid;
	private Shape boundingBox;
	GeometryFactory gfact = new GeometryFactory();

	// TODO
	private boolean createPointFeatures;
	// TODO
	private boolean computeConvexHull;
	// TODO
	private boolean ignoreHoles;

	public PolygonInstantiator() {
	}

	/**
	 * Use this to extract features through
	 * {@link #extractShapes(IState, IExpression, IMonitor)} or
	 * {@link #extractShapes(GridCoverage2D, IProjection, IExpression, IComputationContext)}
	 * outside of a k.LAB contextualizer.
	 * 
	 * @param grid
	 */
	public PolygonInstantiator(IGrid grid) {
		this.grid = grid;
	}

	public PolygonInstantiator(IParameters<String> parameters, IComputationContext context)
			throws KlabValidationException {
		if (parameters.containsKey("select")) {
			Object expression = parameters.get("select");
			if (expression instanceof IKimExpression) {
				expression = ((IKimExpression) expression).getCode();
			}
			this.exprDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE)
					.describe(expression.toString(), context);
		}

		IScale scale = context.getScale();
		if (!(scale.isSpatiallyDistributed() && scale.getDimension(Type.SPACE).size() > 1
				&& scale.getDimension(Type.SPACE).isRegular())) {
			throw new KlabValidationException(
					"feature extraction only works on regular distributed spatial extents (grids)");
		}

		this.grid = ((Space) scale.getSpace()).getGrid();
		this.boundingBox = (Shape) scale.getSpace().getShape();
		this.ignoreHoles = !parameters.get("holes", Boolean.TRUE);
		this.createPointFeatures = parameters.get("points", Boolean.FALSE);

		// TODO these are obviously still unfeasible dimensions for an in-memory image.
		if (this.grid == null || this.grid.getXCells() > Integer.MAX_VALUE
				|| this.grid.getYCells() > Integer.MAX_VALUE) {
			throw new KlabValidationException("feature extractor: the spatial extent is too large or not a grid");
		}

	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {

		List<IState> sourceStates = new ArrayList<>();
		List<IObjectArtifact> ret = new ArrayList<>();
		Map<IState, String> stateIdentifiers = new HashMap<>();

		IExpression expression = null;
		if (exprDescriptor != null) {
			// check inputs and see if the expr is worth anything in this context
			for (String input : exprDescriptor.getIdentifiers()) {
				if (exprDescriptor.isScalar(input) && context.getArtifact(input, IState.class) != null) {
					IState state = context.getArtifact(input, IState.class);
					sourceStates.add(state);
					stateIdentifiers.put(state, input);
				}
			}
			if (sourceStates.isEmpty()) {
				throw new KlabResourceNotFoundException(
						"feature extractor: the selection expression does not reference any known state");
			}
			expression = exprDescriptor.compile();
		}

		if (context.contains("source-state")) {
			IState sourceState = context.getArtifact(context.get("source-state", String.class), IState.class);
			if (sourceState == null) {
				throw new KlabResourceNotFoundException("feature extractor: source state "
						+ context.get("source-state", String.class) + " not found or not a state");
			}
			sourceStates.add(sourceState);
		}

		// build mask
		ImagePlus image = IJ.createImage("blobs", "8-bit black", (int) grid.getXCells(), (int) grid.getYCells(), 1);
		ImageProcessor imp = image.getProcessor();
		boolean warned = false;
		Parameters<String> parameters = new Parameters<>();

		for (Cell cell : grid) {

			Object o = null;

			if (expression != null) {

				parameters.clear();
				for (IState state : sourceStates) {
					o = state.get(cell, Object.class);
					parameters.put(stateIdentifiers.get(state), o);
				}

				o = expression.eval(parameters, context);
				if (o == null) {
					o = Boolean.FALSE;
				}
				if (!(o instanceof Boolean)) {
					throw new KlabValidationException(
							"feature extractor: feature extraction selector must return true/false");
				}

			} else if (!warned) {
				context.getMonitor().warn("feature extractor: no input: specify either select or select fraction");
				warned = true;
			}

			imp.set((int) cell.getX(), (int) cell.getY(), ((Boolean) o) ? 0 : 255);
		}

		// build blobs
		String baseName = CamelCase.toLowerCase(semantics.getLocalName(), '-');
		ManyBlobs blobs = new ManyBlobs(image);
		blobs.findConnectedComponents();
		int created = 0;
		int skipped = 0;
		for (Blob blob : blobs) {
			Shape shape = getShape(blob);
			if (shape != null) {
				Scale instanceScale = Scale.createLike(context.getScale(), shape);
				ret.add(context.newObservation(semantics, baseName + "_" + (created + 1), instanceScale,
						/* TODO send useful metadata */null));
				created++;
			} else {
				skipped++;
			}
		}

		context.getMonitor().info("feature extractor: built " + created + " observations of type " + semantics);
		if (skipped > 0) {
			context.getMonitor()
					.info("feature extractor: skipped " + skipped + " features not conformant with passed options");
		}

		return ret;
	}

	private Shape getShape(Blob blob) {

		/*
		 * TODO apply filters, if any, and cull unsuitable candidates.
		 */

		Geometry polygon = null;
		if (blob.getOuterContour().npoints < 4) {
			if (this.createPointFeatures) {
				polygon = getPolygon(blob.getCenterOfGravity());
			}
		} else {

			/*
			 * create spatial context
			 */
			LinearRing shell = getLinearRing(blob.getOuterContour());
			if (shell == null) {
				return null;
			}

			/*
			 * safest strategy - allows holes that overlap the perimeter
			 */
			polygon = new Polygon(shell, null, gfact);
			polygon = polygon.buffer(0);
			if (this.computeConvexHull) {
				polygon = polygon.convexHull();
			}

			if (!this.ignoreHoles) {
				for (LinearRing hole : getLinearRings(blob.getInnerContours())) {
					Geometry h = new Polygon(hole, null, gfact);
					h = h.buffer(0);
					polygon = polygon.difference(h);
				}
			}
		}

		/*
		 * clip to context shape
		 */
		if (polygon != null) {
			polygon = polygon.intersection(boundingBox.getJTSGeometry());
		}

		if (polygon == null || polygon.isEmpty()) {
			return null;
		}

		return Shape.create(polygon, this.grid.getProjection());
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new PolygonInstantiator(parameters, context);
	}

	@Override
	public IGeometry getGeometry() {
		// TODO Auto-generated method stub
		return null;
	}

	private LinearRing[] getLinearRings(List<java.awt.Polygon> rings) {
		ArrayList<LinearRing> ret = new ArrayList<>();
		for (java.awt.Polygon p : rings) {
			LinearRing ring = getLinearRing(p);
			if (p != null) {
				ret.add(ring);
			}
		}
		return ret.toArray(new LinearRing[ret.size()]);
	}

	private Geometry getPoint(Point2D point2d) {

		int x = (int) point2d.getX();
		int y = (int) point2d.getY();
		double[] xy = grid.getCoordinates(grid.getOffset(x, y));
		return gfact.createPoint(new Coordinate(xy[0], xy[1]));
	}

	private Geometry getPolygon(Point2D point2d) {

		int x = (int) point2d.getX();
		int y = (int) point2d.getY();
		return ((Shape) ((Grid) grid).getCell(grid.getOffset(x, y)).getShape()).getJTSGeometry();
	}

	private LinearRing getLinearRing(java.awt.Polygon p) {

		if (p.npoints < 4) {
			return null;
		}

		ArrayList<Coordinate> coords = new ArrayList<>();
		for (int i = 0; i < p.npoints; i++) {

			int x = p.xpoints[i];
			int y = p.ypoints[i];

			double[] xy = grid.getCoordinates(grid.getOffset(x, y));
			coords.add(new Coordinate(xy[0], xy[1]));
		}

		return new LinearRing(new CoordinateArraySequence(coords.toArray(new Coordinate[coords.size()])), gfact);
	}

	@Override
	public IArtifact.Type getType() {
		return IArtifact.Type.OBJECT;
	}

	public Collection<IShape> extractShapes(GridCoverage2D state, IExpression selector, IComputationContext context) {

		List<IShape> ret = new ArrayList<>();

		RenderedImage raster = state.getRenderedImage();
		ImagePlus image = IJ.createImage("blobs", "8-bit black", raster.getWidth(), raster.getHeight(), 1);
		ImageProcessor imp = image.getProcessor();

		RandomIter itera = RandomIterFactory.create(raster, null);

		for (int x = 0; x < raster.getWidth(); x++) {
			for (int y = 0; y < raster.getHeight(); y++) {
				double value = itera.getSampleDouble(x, y, 0);
				Object on = selector.eval(Parameters.create("value", value), context);
				imp.set(x, y, on instanceof Boolean && ((Boolean) on) ? 0 : 255);
			}
		}

		ManyBlobs blobs = new ManyBlobs(image);
		blobs.findConnectedComponents();

		for (Blob blob : blobs) {

			Geometry polygon = null;
			if (blob.getOuterContour().npoints < 4) {
				if (createPointFeatures) {
					polygon = getPoint(blob.getCenterOfGravity());
				}
			} else {
				/*
				 * create spatial context
				 */
				LinearRing shell = getLinearRing(blob.getOuterContour());
				if (shell == null) {
					continue;
				}

				/*
				 * safest strategy - allows holes that overlap the perimeter
				 */
				polygon = new Polygon(shell, null, gfact);
				polygon = polygon.buffer(0);
				if (computeConvexHull) {
					polygon = polygon.convexHull();
				}

				if (!ignoreHoles) {
					for (LinearRing hole : getLinearRings(blob.getInnerContours())) {
						Geometry h = new Polygon(hole, null, gfact);
						h = h.buffer(0);
						polygon = polygon.difference(h);
					}
				}

				if (polygon == null || polygon.isEmpty()) {
					continue;
				}

				ret.add(Shape.create(polygon, grid.getProjection()));

			}
		}

		return ret;
	}

	public Collection<IShape> extractShapes(IState state, IExpression selector, IMonitor monitor) {

		List<IShape> ret = new ArrayList<>();

		grid = Space.extractGrid(state);
		ImagePlus image = IJ.createImage("blobs", "8-bit black", (int) grid.getXCells(), (int) grid.getYCells(), 1);
		ImageProcessor imp = image.getProcessor();

		for (Cell cell : grid) {
			Object value = state.get(cell);
			Object on = selector.eval(Parameters.create("value", value), GroovyExpression.emptyContext(monitor));
			if (on instanceof Boolean && ((Boolean) on)) {
				imp.set((int) cell.getX(), (int) cell.getY(), 255);
			}
		}

		ManyBlobs blobs = new ManyBlobs(image);
		blobs.findConnectedComponents();

		for (Blob blob : blobs) {

			Geometry polygon = null;
			if (blob.getOuterContour().npoints < 4) {
				if (createPointFeatures) {
					polygon = getPoint(blob.getCenterOfGravity());
				}
			} else {
				/*
				 * create spatial context
				 */
				LinearRing shell = getLinearRing(blob.getOuterContour());
				if (shell == null) {
					continue;
				}

				/*
				 * safest strategy - allows holes that overlap the perimeter
				 */
				polygon = new Polygon(shell, null, gfact);
				polygon = polygon.buffer(0);
				if (computeConvexHull) {
					polygon = polygon.convexHull();
				}

				if (!ignoreHoles) {
					for (LinearRing hole : getLinearRings(blob.getInnerContours())) {
						Geometry h = new Polygon(hole, null, gfact);
						h = h.buffer(0);
						polygon = polygon.difference(h);
					}
				}

				if (polygon == null || polygon.isEmpty()) {
					continue;
				}

				ret.add(Shape.create(polygon, grid.getProjection()));

			}
		}

		return ret;
	}

}
