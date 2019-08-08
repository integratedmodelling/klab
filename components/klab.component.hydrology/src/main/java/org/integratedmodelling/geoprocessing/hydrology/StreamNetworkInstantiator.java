package org.integratedmodelling.geoprocessing.hydrology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.geoprocessing.GeoprocessingComponent;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

/**
 * TODO this is identical to StreamInstantiator, but should instead produce the 
 * StreamConnections and StreamJunctions, with a mandatory secondary output for
 * the latter.
 */
public class StreamNetworkInstantiator implements IInstantiator, IExpression {

	private static final double DEFAULT_TCA_THRESHOLD = 0.006;

	double threshold = Double.NaN;
	Map<Cell, List<Coordinate>> segments = new HashMap<>();
	List<com.vividsolutions.jts.geom.Geometry> lines = new ArrayList<>();

	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		StreamNetworkInstantiator ret = new StreamNetworkInstantiator();
		ret.threshold = parameters.get("tca.threshold", Double.NaN);
		return ret;
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context) throws KlabException {

		IState fdr = context.getArtifact("flow_directions_d8", IState.class);
		IState tca = context.getArtifact("upstream_cell_count", IState.class);
		Grid grid = Space.extractGrid(tca);

		if (grid == null) {
			throw new KlabValidationException("Flow accumulation must be computed on a grid extent");
		}

		if (Double.isNaN(threshold) || threshold == 0) {
			threshold = DEFAULT_TCA_THRESHOLD;
		}

		double tthreshold = (int) (grid.getCellCount() * threshold);
		context.getMonitor().info("TCA threshold is " + tthreshold);

		for (IArtifact artifact : context.getArtifact("stream_outlet")) {

			ISpace space = ((IObservation) artifact).getSpace();

			if (space == null) {
				continue;
			}

			Point point = ((Shape) space.getShape()).getJTSGeometry().getCentroid();
			long xy = grid.getOffsetFromWorldCoordinates(point.getX(), point.getY());
			Cell start = grid.getCell(xy);

			/*
			 * Trace all lines upstream of this outlet
			 */
			segments.put(start, startSegment(start));
			trace(start, tthreshold, tca, fdr, context);

		}

		List<IObjectArtifact> ret = new ArrayList<>();

		if (lines.size() > 0) {
			com.vividsolutions.jts.geom.Geometry shape = Geospace.gFactory.buildGeometry(lines);
			ret.add(context.newObservation(semantics, "stream_network",
					Scale.substituteExtent(context.getScale(), Shape.create(shape, grid.getProjection())),
					/* TODO send useful metadata */null));
		}
		return ret;
	}

	private List<Coordinate> startSegment(Cell... cell) {
		List<Coordinate> ret = new ArrayList<>();
		for (Cell c : cell) {
			ret.add(getCoordinate(c));
		}
		return ret;
	}

	private Coordinate getCoordinate(Cell cell) {
		double[] xy = cell.getCenter();
		return new Coordinate(xy[0], xy[1]);
	}

	private void trace(Cell cell, double thresh, IState tca, IState fdr, IContextualizationScope context) {

		if (context.getMonitor().isInterrupted()) {
			return;
		}

		List<Coordinate> segment = segments.get(cell);
		double tc = tca.get(cell, Double.class);

		if (tc < thresh) {
			flush(segment, cell);
			return;
		}

		List<Cell> upstream = GeoprocessingComponent.getUpstreamCells(cell, fdr,
				(c) -> tca.get(c, Double.class) >= thresh);

		if (upstream.size() == 0) {
			flush(segment, cell);
		} else if (upstream.size() == 1) {
			segment.add(getCoordinate(upstream.get(0)));
			segments.remove(cell);
			segments.put(upstream.get(0), segment);
			trace(upstream.get(0), thresh, tca, fdr, context);
		} else {
			flush(segment, cell);
			for (Cell up : upstream) {
				segments.put(up, startSegment(cell, up));
				trace(up, thresh, tca, fdr, context);
			}
		}

	}

	private void flush(List<Coordinate> segment, Cell cell) {
		if (segment.size() >= 2) {
			LineString line = Geospace.gFactory.createLineString(segment.toArray(new Coordinate[segment.size()]));
			lines.add(line);
		}
		segments.remove(cell);
	}
}
