package org.integratedmodelling.geoprocessing.hydrology;

import org.hortonmachine.gears.utils.time.UtcTimeUtilities;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Time;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.contrib.math.ExponentialIntegrals;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.joda.time.DateTime;

public class ScsRunoffResolver implements IResolver<IProcess>, IExpression {

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess runoffProcess, IContextualizationScope context) throws KlabException {
        if (Configuration.INSTANCE.isEchoEnabled()) {
            ITime time = context.getScale().getTime();
            String start = UtcTimeUtilities.toStringWithMinutes(new DateTime(time.getStart().getMilliseconds()));
            String end = UtcTimeUtilities.toStringWithMinutes(new DateTime(time.getEnd().getMilliseconds()));
            System.out.println("Enter ScsRunoffResolver at timestep : " + start + " -> " + end);
        }

        IState rainfallVolumeState = context.getArtifact("rainfall_volume", IState.class);
        IState streamPresenceState = context.getArtifact("presence_of_stream", IState.class);
        IState curveNumberState = context.getArtifact("curve_number", IState.class);
        IState numberOfEventsState = context.getArtifact("number_of_events", IState.class);
        if (numberOfEventsState == null) {
            context.getMonitor().warn("No number of events available, default to 1.");
        }

        IState runoffState = context.getArtifact("runoff_water_volume", IState.class);

        long validCells = 0;
        for(ILocator locator : context.getScale()) {
            Double rainfall = rainfallVolumeState.get(locator, Double.class);
            Boolean isStream = streamPresenceState.get(locator, Boolean.class);
            Double curveNumber = curveNumberState.get(locator, Double.class);
            Integer eventsNum = 0;
            if (numberOfEventsState != null) {
                eventsNum = numberOfEventsState.get(locator, Integer.class);
            } else {
                if (Observations.INSTANCE.isData(rainfall)) {
                    eventsNum = 1;
                }
            }
            if (!Observations.INSTANCE.isData(curveNumber)) {
                // TODO decide if it is better to calculate or set a default value
                curveNumber = 70.0;
            }
            boolean isValid = Observations.INSTANCE.isData(rainfall) && Observations.INSTANCE.isData(isStream)
                    && Observations.INSTANCE.isData(curveNumber) && Observations.INSTANCE.isData(eventsNum);
            double runoff = 0;
            if (isValid) {
                validCells++;
                rainfall = (double) Math.round(rainfall);
                if (rainfall == 0) {
                    runoff = 0;
                } else if (isStream) {
                    runoff = rainfall;
                } else {
                    double sScsCoeff = 1000.0 / curveNumber - 10.0; // TODO check cn unit
                    double meanRainDepth = rainfall / eventsNum / 25.4; // convert to inches
                    double rainParam = sScsCoeff / meanRainDepth;
                    double p1 = -0.2 * rainParam;
                    double p2 = 0.8 * rainParam;
                    double expP1 = Math.exp(p1);
                    double expP2 = Math.exp(p2);
                    runoff = eventsNum //
                            * ((meanRainDepth - sScsCoeff) * expP1 + Math.pow(sScsCoeff, 2.0) / meanRainDepth
                                    * expP2 * ExponentialIntegrals.enx(rainParam))//
                            * 25.4;// to mm

//                    if (rainfall > 0 && runoff <= 0) {
//                        Cell cell = locator.as(Cell.class);
//                        System.out.println("X: " + cell.getX() + " Y: " + cell.getY());
//                    }
                }
            } else {
                runoff = Double.NaN;
            }
            runoffState.set(locator, runoff);
        }

        long ts = context.getScale().getTime().getStart().getMilliseconds();
        SwyDebugUtils.dumpToRaster(ts, context, "ScsRunoffResolver", rainfallVolumeState,
                streamPresenceState, curveNumberState, numberOfEventsState, runoffState);

        if (Configuration.INSTANCE.isEchoEnabled()) {
            System.out.println("Exit ScsRunoffResolver. Processed valid cells: " + validCells);
        }
        return runoffProcess;
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new ScsRunoffResolver();
    }

}
