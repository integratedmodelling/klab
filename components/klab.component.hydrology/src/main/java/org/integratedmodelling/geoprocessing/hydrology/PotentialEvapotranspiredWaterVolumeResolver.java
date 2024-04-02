package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;
import java.util.function.Function;

import org.geotools.coverage.grid.GridCoverage2D;
import org.hortonmachine.hmachine.modules.hydrogeomorphology.etp.OmsPotentialEvapotranspiredWaterVolume;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.NumberUtils;

public class PotentialEvapotranspiredWaterVolumeResolver extends AbstractContextualizer
        implements
            IResolver<IProcess>,
            IExpression {

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess evapotranspirationProcess, IContextualizationScope context) throws KlabException {

        IState cropCoefficientState = getInput("crop_coefficient", IState.class);
        IState maxTempState = getInput("maximum_temperature", IState.class);
        IState minTempState = getInput("minimum_temperature", IState.class);
        IState tempState = getInput("atmospheric_temperature", IState.class);
        IState solarRadiationState = getInput("solar_radiation", IState.class);
        IState rainfallState = getInput("rainfall_volume", IState.class);

        IState petState = getOutput("potential_evapotranspired_water_volume", IState.class);

        TaskMonitor taskMonitor = new TaskMonitor(context.getMonitor());
        taskMonitor.setTaskName("Potential Evapotranspiration");

        OmsPotentialEvapotranspiredWaterVolume pet = new OmsPotentialEvapotranspiredWaterVolume();
        pet.pm = taskMonitor;

        int startDay = context.getScale().getTime().getStart().getDayOfYear();
        int endDay = context.getScale().getTime().getEnd().getDayOfYear();
        int days = endDay - startDay;
        pet.pDaysInTimestep = (double) days;
        pet.inCropCoefficient = getGridCoverage(context, cropCoefficientState);
        pet.inMaxTemp = getGridCoverage(context, maxTempState);
        pet.inMinTemp = getGridCoverage(context, minTempState);
        pet.inAtmosphericTemp = getGridCoverage(context, tempState);

        Function<Object, Object> transform = null;
        // (value) -> {
        // if (value instanceof Number && !Double.isNaN(((Number) value).doubleValue())) {
        // // FIXME do to a wrong annotated solar radiation, we need to check and fix here.
        // // Radiation in MJ should be around 10-20MJ, so if it is > 1000, let's assume it
        // // is in kJ and divide by 10000.
        // //
        // // This should be removed once copernicus data are used.
        // double doubleValue = ((Number) value).doubleValue();
        // if (doubleValue > 1000) {
        // return doubleValue / 1000;
        // }
        // }
        // return value;
        // };
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

        // REMOVE
        GeotoolsUtils.INSTANCE.dumpToRaster(context, "PETRAW", pet.outputPet);

        if (!context.getMonitor().isInterrupted()) {
            GeotoolsUtils.INSTANCE.coverageToState(pet.outputPet, petState, context.getScale(),
                    (val) -> NumberUtils.equal(val, -9999) ? Double.NaN : val);
        }

        GeotoolsUtils.INSTANCE.dumpToRaster(context, "PET", cropCoefficientState, rainfallState, tempState, maxTempState,
                minTempState, solarRadiationState, petState);

        return evapotranspirationProcess;
    }

    private GridCoverage2D getGridCoverage(IContextualizationScope context, IState state) {
        if (state == null) {
            return null;
        }
        return GeotoolsUtils.INSTANCE.stateToCoverage(state, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue, false);
    }

    @Override
    public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
        return new PotentialEvapotranspiredWaterVolumeResolver();
    }

}
