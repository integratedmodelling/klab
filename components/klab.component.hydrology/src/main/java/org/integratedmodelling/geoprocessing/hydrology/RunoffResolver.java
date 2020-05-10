package org.integratedmodelling.geoprocessing.hydrology;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

import com.vividsolutions.jts.geom.Point;

public class RunoffResolver implements IResolver<IProcess>, IExpression {

	// only one official output for now (see k.DL specs)
	String[] outputIds = { "runoff_water_volume" };
	
	@Override
	public Type getType() {
		return Type.PROCESS;
	}

	@Override
	public IProcess resolve(IProcess runoffProcess, IContextualizationScope context) throws KlabException {

		IState flowdirection = context.getArtifact("flow_directions_d8", IState.class);
		IState precipitation = context.getArtifact("precipitation_volume", IState.class);
		IState curvenumber = context.getArtifact("curve_number", IState.class);

		IState runoffState = null;
		
		for (int i = 1; i < context.getModel().getObservables().size(); i++) {

			// FIXME - laborious; find a simpler way that can be codified somewhere
			for (String output : outputIds) {
				if (output.equals(context.getModel().getObservables().get(i).getName())) {
					IState state = context.getArtifact(output, IState.class);
					if (state == null) {
						context.getMonitor().warn("runoff: cannot find state for " + output);
					} else {
						switch (output) {
						case "runoff_water_volume":
							runoffState = state;
							break;
						}
					}
					break;
				}
			}
		}
		
		if (runoffState == null) {
			return runoffProcess;
		}
		
		IUnit tUnit = runoffState.getObservable().getUnit();
		Grid grid = Space.extractGrid(runoffState);
		
		if (grid == null) {
			throw new KlabValidationException("Runoff must be computed on a grid extent");
		}
		
//		double cellArea = grid.getCell(0).getStandardizedArea();
		if (tUnit != null && tUnit.equals(Units.INSTANCE.SQUARE_METERS)) {
			tUnit = null;
		}
		
		int nouts = 0, nuouts = 0;
		
		// TODO this should be in the watershed but it's in the region.
		for (IArtifact artifact : context.getArtifact("stream_outlet")) {

			nouts++;
			
			ISpace space = ((IObservation) artifact).getSpace();

			if (space == null) {
				continue;
			}

			Point point = ((Shape) space.getShape()).getJTSGeometry().getCentroid();
			long xy = grid.getOffsetFromWorldCoordinates(point.getX(), point.getY());
			Cell start = grid.getCell(xy);
			computeRunoff(start, flowdirection, precipitation, curvenumber, runoffState);
			
			nuouts ++;
		}
		
		return runoffProcess;
	}

	/*
	 * Computation of runoff accumulates the runoff from upstream cells, ending at the outlet
	 * This function is called with the outlet cell as parameter. 
	 * 
	 */
	
	/* These two formulas for modified mret and outflow are referenced from 
	 * "Hawkins, R.H., R.Jiang, D.E.Woodward, A.T.Hjelmfelt, and J.A.Van Mullem (2002)
	 *  Runoff Curve Number Method: Examination of the Initial Abstraction Ratio" 
	*/
	
	private double computeRunoff(Cell cell, IState flowdirection, IState precipitation, IState curvenumber, IState runoff) {
		double prec = precipitation.get(cell, Double.class);
		double cn = curvenumber.get(cell, Double.class);
		
	//	double mret = (25400 / cn) - 254;
		
	/* These two formulas for modified mret and outflow are referenced from 
		* "Hawkins, R.H., R.Jiang, D.E.Woodward, A.T.Hjelmfelt, and J.A.Van Mullem (2002)
		*  Runoff Curve Number Method: Examination of the Initial Abstraction Ratio" 
	*/
		double mret =  1.33 * 25.4 * Math.pow((1000 / cn - 10),1.15);
		double outflow;
		if (prec <= 0.05 * mret) {
		    outflow = 0;
		}
		else {
		    outflow = ((prec - (0.05 * mret))*(prec - (0.05 * mret)))/(prec + (0.95 * mret));
		}
		
		for (Cell upstream : Geospace.getUpstreamCells(cell, flowdirection, null)) {
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
