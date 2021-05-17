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
import org.integratedmodelling.klab.contrib.math.ExponentialIntegrals;
import org.integratedmodelling.klab.exceptions.KlabException;

public class ScsRunoffResolver implements IResolver<IProcess>, IExpression {

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess runoffProcess, IContextualizationScope context) throws KlabException {

        IState rainfallVolumeState = context.getArtifact("rainfall_volume", IState.class);
        IState streamPresenceState = context.getArtifact("presence_of_stream", IState.class);
        IState curveNumberState = context.getArtifact("curve_number", IState.class);
        IState numberOfEventsState = context.getArtifact("number_of_events", IState.class);

        IState runoffState = context.getArtifact("runoff_water_volume", IState.class);

        for(ILocator locator : context.getScale()) {
            Double rainfall = rainfallVolumeState.get(locator, Double.class);
            Boolean isStream = streamPresenceState.get(locator, Boolean.class);
            Double curveNumber = curveNumberState.get(locator, Double.class);
            Integer eventsNum = numberOfEventsState.get(locator, Integer.class);

            boolean isValid = Observations.INSTANCE.isData(rainfall) && Observations.INSTANCE.isData(isStream)
                    && Observations.INSTANCE.isData(curveNumber) && Observations.INSTANCE.isData(eventsNum);
            double runoff = 0;
            if (isValid) {
                if (isStream) {
                    runoff = rainfall;
                } else {
                    double sScsCoeff = 1000.0 / curveNumber - 10.0; // TODO check cn unit
                    double meanRainDepth = rainfall / eventsNum / 25.4; // convert to inches
                    double rainParam = sScsCoeff / meanRainDepth;
                    runoff = eventsNum //
                            * ((meanRainDepth - sScsCoeff) * Math.exp(-0.2 * rainParam) + Math.pow(sScsCoeff, 2.0) / meanRainDepth
                                    * Math.exp(0.8 * rainParam) * ExponentialIntegrals.enx(rainParam))//
                            * 25.4;// to mm
                }
            } else {
                runoff = Double.NaN;
            }
            runoffState.set(locator, runoff);
        }
        return runoffProcess;
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new ScsRunoffResolver();
    }

}
