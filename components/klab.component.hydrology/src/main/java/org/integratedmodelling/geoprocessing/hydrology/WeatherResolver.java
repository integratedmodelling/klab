package org.integratedmodelling.geoprocessing.hydrology;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
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
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

import com.vividsolutions.jts.geom.Point;

/**
 * Resolve any of the observables provided by the weather component into
 * interpolated temporal data surfaces using weather stations. Use the universal
 * URN for the service from the node that the network assigns to us.
 * <p>
 * Requires elevation data and global (annual) temperature data for adjustment.
 * 
 * @author Ferd
 *
 */
public class WeatherResolver implements IResolver<IState>, IExpression {

	/**
	 * The station artifacts contextualized to the full distributed spatio/temporal
	 * context.
	 */
	private IKlabData data;

	public WeatherResolver() {
	}

	public WeatherResolver(IParameters<String> parameters, IContextualizationScope context) {

		String urn = "klab:weather:stations:";

		/*
		 * TODO use the source we have chosen, or all if no choice. Add all the
		 * requested observables.
		 */
		urn += "all";
		IResource resource = Resources.INSTANCE.resolveResource(urn);
		Urn kurn = new Urn(urn);
		if (resource == null || !Resources.INSTANCE.isResourceOnline(resource)) {
			throw new KlabResourceNotFoundException(
					"k.LAB weather services are not online on any server at the moment");
		}

		/*
		 * put away station data
		 */
		this.data = Resources.INSTANCE.getResourceData(resource, kurn.getParameters(),
				context.getDataflow().getResolutionScale(), context);
	}

	@Override
	public Type getType() {
		return Type.PROCESS;
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
	 * Computation of runoff accumulates the runoff from upstream cells, ending at
	 * the outlet This function is called with the outlet cell as parameter.
	 * 
	 */

	/*
	 * These two formulas for modified mret and outflow are referenced from
	 * "Hawkins, R.H., R.Jiang, D.E.Woodward, A.T.Hjelmfelt, and J.A.Van Mullem
	 * (2002) Runoff Curve Number Method: Examination of the Initial Abstraction
	 * Ratio"
	 */

	private double computeRunoff(Cell cell, IState flowdirection, IState precipitation, IState curvenumber,
			IState runoff) {
		double prec = precipitation.get(cell, Double.class);
		double cn = curvenumber.get(cell, Double.class);

		// double mret = (25400 / cn) - 254;

		/*
		 * These two formulas for modified mret and outflow are referenced from
		 * "Hawkins, R.H., R.Jiang, D.E.Woodward, A.T.Hjelmfelt, and J.A.Van Mullem
		 * (2002) Runoff Curve Number Method: Examination of the Initial Abstraction
		 * Ratio"
		 */
		double mret = 1.33 * 25.4 * Math.pow((1000 / cn - 10), 1.15);
		double outflow;
		if (prec <= 0.05 * mret) {
			outflow = 0;
		} else {
			outflow = ((prec - (0.05 * mret)) * (prec - (0.05 * mret))) / (prec + (0.95 * mret));
		}

		for (Cell upstream : Geospace.getUpstreamCells(cell, flowdirection, null)) {
			outflow += computeRunoff(upstream, flowdirection, precipitation, curvenumber, runoff);
		}
		runoff.set(cell, outflow);
		return outflow;

	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new WeatherResolver(parameters, context);
	}
}
