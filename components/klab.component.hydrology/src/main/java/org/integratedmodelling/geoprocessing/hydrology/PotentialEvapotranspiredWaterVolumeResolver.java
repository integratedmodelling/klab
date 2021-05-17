package org.integratedmodelling.geoprocessing.hydrology;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;

public class PotentialEvapotranspiredWaterVolumeResolver implements IResolver<IProcess>, IExpression {

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess evapotranspirationProcess, IContextualizationScope context) throws KlabException {

        IState cropCoefficientState = context.getArtifact("crop_coefficient", IState.class);
        IState maxTempState = context.getArtifact("maximum_temperature", IState.class);
        IState minTempState = context.getArtifact("minimum_temperature", IState.class);
        IState tempState = context.getArtifact("atmospheric_temperature", IState.class);
        IState solarRadiationState = context.getArtifact("solar_radiation", IState.class);
        IState rainfallState = context.getArtifact("rainfall_volume", IState.class);

        IState petState = context.getArtifact("evapotranspired_water_volume", IState.class);

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
            double pet = 0;
            if (isValid) {
                double referenceET = 0.0013 * 0.408 * solarRad * (tAvg + 17) * Math.pow((tMax - tMin - 0.0123 * rainfall), 0.76);
                pet = kc * referenceET;
            } else {
                pet = Double.NaN;
            }
            petState.set(locator, pet);
        }

        return evapotranspirationProcess;
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new PotentialEvapotranspiredWaterVolumeResolver();
    }

}
