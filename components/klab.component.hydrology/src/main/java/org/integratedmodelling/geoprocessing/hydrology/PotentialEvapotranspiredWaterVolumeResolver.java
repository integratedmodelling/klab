package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;
import java.util.function.Function;

import org.geotools.coverage.grid.GridCoverage2D;
import org.hortonmachine.hmachine.modules.hydrogeomorphology.etp.OmsPotentialEvapotranspiredWaterVolume;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
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

        IState petState = context.getArtifact("potential_evapotranspired_water_volume", IState.class);

        TaskMonitor taskMonitor = new TaskMonitor(context.getMonitor());
        taskMonitor.setTaskName("Potential Evapotranspiration");

        if (cropCoefficientState != null) {

            OmsPotentialEvapotranspiredWaterVolume pet = new OmsPotentialEvapotranspiredWaterVolume();
            pet.pm = taskMonitor;
            pet.inCropCoefficient = getGridCoverage(context, cropCoefficientState);
            pet.inMaxTemp = getGridCoverage(context, maxTempState);
            pet.inMinTemp = getGridCoverage(context, minTempState);
            pet.inAtmosphericTemp = getGridCoverage(context, tempState);

            // FIXME for now divide by 1000000, since the solarradiation enters as J and not MJ as
            // requested
            Function<Object, Object> transform = (value) -> {
                if (value instanceof Number && !Double.isNaN(((Number) value).doubleValue())) {
                    return ((Number) value).doubleValue() / 1_000_000.0;
                }
                return value;
            };
            GridCoverage2D solarRadiationGc = GeotoolsUtils.INSTANCE.stateToCoverage(solarRadiationState, context.getScale(),
                    DataBuffer.TYPE_FLOAT, floatNovalue, false, transform);
            pet.inSolarRadiation = solarRadiationGc;// getGridCoverage(context,
                                                    // solarRadiationState);
            pet.inRainfall = getGridCoverage(context, rainfallState);
            pet.inReferenceEtp = null; // TODO consider a case in which reference etp is passed?

            try {
                pet.process();
            } catch (Exception e) {
                throw new KlabException(e);
            }
            if (!context.getMonitor().isInterrupted()) {
                GeotoolsUtils.INSTANCE.coverageToState(pet.outputPet, petState, context.getScale(), null);
            }

            GeotoolsUtils.INSTANCE.dumpToRaster(context, "PET", cropCoefficientState, rainfallState, tempState, maxTempState,
                    minTempState, solarRadiationState, petState);
        }
        return evapotranspirationProcess;
    }

    private GridCoverage2D getGridCoverage(IContextualizationScope context, IState state) {
        if (state == null) {
            return null;
        }
        return GeotoolsUtils.INSTANCE.stateToCoverage(state, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue, false);
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new PotentialEvapotranspiredWaterVolumeResolver();
    }

}
