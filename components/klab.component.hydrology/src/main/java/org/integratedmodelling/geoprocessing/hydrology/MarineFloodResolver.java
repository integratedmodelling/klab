package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import javax.media.jai.iterator.RandomIter;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.util.CoverageUtilities;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.utils.RegionMap;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;

import oms3.gen.doubleAccess;

import java.util.ArrayList;

public class MarineFloodResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public IState resolve(IState target, IContextualizationScope context) throws KlabException {

		IState seeds = context.getArtifact("seeds", IState.class);
		IState dem = context.getArtifact("elevation", IState.class);

		GridCoverage2D seedsCover = GeotoolsUtils.INSTANCE.stateToCoverage(seeds, context.getScale(),
				DataBuffer.TYPE_FLOAT, floatNovalue, false);

		GridCoverage2D demCover = GeotoolsUtils.INSTANCE.stateToCoverage(dem, context.getScale(), DataBuffer.TYPE_FLOAT,
				floatNovalue, false);

		GridCoverage2D finalWatLevCov = null;

		HMRaster demRaster = HMRaster.fromGridCoverage(demCover);

		HMRaster seedsRaster = HMRaster.fromGridCoverage(seedsCover);

		int cols, rows;

		double[][] elevations;
		
		double[][] newElevations;

		double novalue = demRaster.getNovalue();

		HMRaster finalWatLev = new HMRaster.HMRasterWritableBuilder().setTemplate(demCover).setNoValue(novalue).build();

		RegionMap regionMap = demRaster.getRegionMap();
		cols = regionMap.getCols();
		rows = regionMap.getRows();

		elevations = new double[cols][rows];

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				double elValue = demRaster.getValue(col, row);
				if (!demRaster.isNovalue(elValue)) {
					elevations[col][row] = elValue;
				} else {
					elevations[col][row] = Double.NaN;
				}
			}
		}

		ArrayList<double[]> inWatLev = new ArrayList<double[]>();

		double[] lst = new double[3];

		double[] lst1 = new double[3];
        
		//Make a copy of elevations to modify in the future
		newElevations = new double[cols][rows];
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
					newElevations[col][row] = elevations[col][row];
				}
		}

	
	try {		
		
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {

				double watValue = seedsRaster.getValue(col, row);

				if ((!seedsRaster.isNovalue(watValue)) && (watValue >= 0) && (elevations[col][row] < watValue)) {

					lst = new double[3];
					lst[0] = row;
					lst[1] = col;
					lst[2] = watValue;
					inWatLev.add(lst);

				}
			}
		}
		
		seedsRaster.close();
		demRaster.close();
		
		int i = 0;

		while (inWatLev.size() != 0) {

			int rw = (int) inWatLev.get(i)[0];

			int cl = (int) inWatLev.get(i)[1];

			double val = (double) inWatLev.get(i)[2];

			// iterate over 8 pixels surrounding flooded cell
			for (int rnear = -1; rnear <= 1; rnear++) {
				for (int cnear = -1; cnear <= 1; cnear++) {

					// check if not out of boundaries
					if ((((cl + cnear) >= 0) && (cl + cnear) < cols) && ((rw + rnear) >= 0) && ((rw + rnear) < rows)
							&& ((cl != 0) && (rw != 0))
							&& (newElevations[cl + cnear][rw + rnear] != Double.NaN)) {

						if ((newElevations[cl + cnear][rw + rnear] < val)) {

							newElevations[cl + cnear][rw + rnear] = val;

							lst1 = new double[3];
							lst1[0] = rw + rnear;
							lst1[1] = cl + cnear;
							lst1[2] = val;
							inWatLev.add(lst1);

						}
					}
					
				 }
	
			   }
			
			 inWatLev.remove(i);
			
			}



					for (int frow = 0; frow < rows; frow++) {
						for (int fcol = 0; fcol < cols; fcol++) {

							if (newElevations[fcol][frow] != Double.NaN) {

								finalWatLev.setValue(fcol, frow, newElevations[fcol][frow] - elevations[fcol][frow]);

							} else {

								finalWatLev.setValue(fcol, frow, novalue);

							}

						}
					}
					
					finalWatLevCov = finalWatLev.buildCoverage();
					finalWatLev.close();
					
					

				} catch (Exception e) {
					
					throw new KlabException(e);
				
				}

				if (!context.getMonitor().isInterrupted()) {

					GeotoolsUtils.INSTANCE.coverageToState(finalWatLevCov, target, context.getScale(), (a) -> {
						if (a == (double) floatNovalue) {
							return Double.NaN;
						}
						return a;
					});

				}

		return target;

	}

	@Override
	public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
		MarineFloodResolver ret = new MarineFloodResolver();
		return ret;
	}

}
