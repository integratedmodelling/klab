package org.integratedmodelling.geoprocessing.hydrology;

import org.integratedmodelling.geoprocessing.GeoprocessingComponent;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

import com.vividsolutions.jts.geom.Point;

public class RunoffResolver implements IResolver<IState>, IExpression {
	
//	@Override
//	public IGeometry getGeometry() {
//		return Geometry.create("S2");
//	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public IState resolve(IState target, IContextualizationScope context) throws KlabException {

		IState flowdirection = context.getArtifact("flow_directions_d8", IState.class);
		IState precipitation = context.getArtifact("precipitation_volume", IState.class);
		IState curvenumber = context.getArtifact("curve_number", IState.class);

		IUnit tUnit = target.getObservable().getUnit();
		Grid grid = Space.extractGrid(target);
		
		if (grid == null) {
			throw new KlabValidationException("Runoff must be computed on a grid extent");
		}
		
//		double cellArea = grid.getCell(0).getStandardizedArea();
		if (tUnit != null && tUnit.equals(Units.INSTANCE.SQUARE_METERS)) {
			tUnit = null;
		}

		for (IArtifact artifact : context.getArtifact("stream_outlet")) {

			ISpace space = ((IObservation) artifact).getSpace();

			if (space == null) {
				continue;
			}

			Point point = ((Shape) space.getShape()).getJTSGeometry().getCentroid();
			long xy = grid.getOffsetFromWorldCoordinates(point.getX(), point.getY());
			Cell start = grid.getCell(xy);
			computeRunoff(start, flowdirection, precipitation, curvenumber, target);
		}

		return target;
	}

	/*
	 * Computation of runoff accumulates the runoff from upstream cells, ending at the outlet
	 * This function is called with the outlet cell as parameter. 
	 */
	private double computeRunoff(Cell cell, IState flowdirection, IState precipitation, IState curvenumber, IState runoff) {
		double prec = precipitation.get(cell, Double.class);
		double cn = curvenumber.get(cell, Double.class);
		double mret = (25400 / cn) - 254;
		double outflow = ((prec - (0.2 * mret))*(prec - (0.2 * mret)))/(prec + (0.8 * mret));
		for (Cell upstream : GeoprocessingComponent.getUpstreamCells(cell, flowdirection, null)) {
			outflow += computeRunoff(upstream, flowdirection, precipitation, curvenumber, runoff);
		}
		runoff.set(cell, outflow);
		return outflow;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		RunoffResolver ret = new RunoffResolver();
		return ret;
	}
}
