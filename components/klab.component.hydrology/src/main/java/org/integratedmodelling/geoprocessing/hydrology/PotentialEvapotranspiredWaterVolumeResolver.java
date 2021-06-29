package org.integratedmodelling.geoprocessing.hydrology;

import org.hortonmachine.gears.utils.time.UtcTimeUtilities;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.joda.time.DateTime;

public class PotentialEvapotranspiredWaterVolumeResolver implements IResolver<IProcess>, IExpression {

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess evapotranspirationProcess, IContextualizationScope context) throws KlabException {
        if (Configuration.INSTANCE.isEchoEnabled()) {
            ITime time = context.getScale().getTime();
            String start = UtcTimeUtilities.toStringWithMinutes(new DateTime( time.getStart().getMilliseconds()));
            String end = UtcTimeUtilities.toStringWithMinutes(new DateTime( time.getEnd().getMilliseconds()));
            System.out.println("Enter PotentialEvapotranspiredWaterVolumeResolver at timestep : " + start + " -> " + end);
        }


        IState cropCoefficientState = context.getArtifact("crop_coefficient", IState.class);
        IState maxTempState = context.getArtifact("maximum_temperature", IState.class);
        IState minTempState = context.getArtifact("minimum_temperature", IState.class);
        IState tempState = context.getArtifact("atmospheric_temperature", IState.class);
        IState solarRadiationState = context.getArtifact("solar_radiation", IState.class);
        IState rainfallState = context.getArtifact("rainfall_volume", IState.class);

        IState petState = context.getArtifact("potential_evapotranspired_water_volume", IState.class);
        long validCells = 0;
        for(ILocator locator : context.getScale()) {
            Double kc = cropCoefficientState.get(locator, Double.class);
            Double tMax = maxTempState.get(locator, Double.class);
            Double tMin = minTempState.get(locator, Double.class);
            Double tAvg = tempState.get(locator, Double.class);
            Double solarRad = solarRadiationState.get(locator, Double.class);
            Double rainfall = rainfallState.get(locator, Double.class);

            boolean isValid = Observations.INSTANCE.isData(kc) && Observations.INSTANCE.isData(tMax)
                    && Observations.INSTANCE.isData(tMin) && Observations.INSTANCE.isData(tAvg)
                    && Observations.INSTANCE.isData(solarRad) && Observations.INSTANCE.isData(rainfall);
            
            if (isValid) {
                validCells++;
                double referenceET = 0.0013 * 0.408 * solarRad * (tAvg + 17) * Math.pow((tMax - tMin - 0.0123 * rainfall), 0.76);
                double pet = kc * referenceET;
                petState.set(locator, pet);
                
                Cell cell = locator.as(Cell.class);
                if(cell.getX() == 500 && cell.getY() == 350) {
                    System.out.println("CHECK CELL PET: " + pet);
                }
            }
        }
        if (Configuration.INSTANCE.isEchoEnabled()) {
            System.out.println("Exit PotentialEvapotranspiredWaterVolumeResolver. Processed valid cells: " + validCells);
        }
        return evapotranspirationProcess;
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new PotentialEvapotranspiredWaterVolumeResolver();
    }

}
