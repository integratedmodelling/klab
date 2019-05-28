//package org.integratedmodelling.ecology.biomass.lpjguess.drivers;
//
//import java.util.Map;
//
//import org.integratedmodelling.api.modelling.IState;
//import org.integratedmodelling.api.modelling.scheduling.ITransition;
//import org.integratedmodelling.common.states.States;
//import org.integratedmodelling.ecology.biomass.lpjguess.Gridcell;
//import org.integratedmodelling.ecology.co2.CO2Concentration;
//import org.integratedmodelling.hydrology.weather.Weather;
//import org.integratedmodelling.hydrology.weather.WeatherFactory;
//import org.joda.time.DateTime;
//
//public class LPJWeatherDriver {
//
//    /*
//     * either these four are defined...
//     */
//    private IState      precipitation;
//    private IState      minTemperature;
//    private IState      maxTemperature;
//    private ITransition transition;
//
//    /*
//     * ...or these five.
//     */
//    private Weather     weather;
//    private DateTime    time;
//
//    /**
//     * This version will use generated representative weather and a DateTime to retrieve
//     * data.
//     * 
//     * @param weather
//     */
//    public LPJWeatherDriver(Weather weather) {
//        this.weather = weather;
//    }
//
//    /**
//     * This version will use states and transitions to retrieve data
//     * 
//     * @param precipitation
//     * @param minTemperature
//     * @param maxTemperature
//     * @param percSunshine
//     */
//    public LPJWeatherDriver(IState precipitation, IState minTemperature, IState maxTemperature,
//            IState percSunshine) {
//        this.precipitation = precipitation;
//        this.minTemperature = minTemperature;
//        this.maxTemperature = maxTemperature;
//    }
//
//    public void setTransition(ITransition transition) {
//        this.transition = transition;
//        this.time = null;
//    }
//
//    public void setDate(DateTime transition) {
//        this.time = transition;
//        this.transition = null;
//    }
//
//    public void setClimate(Gridcell gridcell) {
//
//        if (time != null) {
//
//            Map<String, double[]> data = weather.defineVariables(time, WeatherFactory.PRECIPITATION_MM, WeatherFactory.MIN_TEMPERATURE_C, WeatherFactory.MAX_TEMPERATURE_C);
//            gridcell.climate.prec = 0;
//            double tsum = 0;
//            for (int offset : gridcell.spaceOffsets) {
//                gridcell.climate.prec += data.get(WeatherFactory.PRECIPITATION_MM)[offset];
//                tsum += (data.get(WeatherFactory.MAX_TEMPERATURE_C)[offset] - data.get(WeatherFactory.MIN_TEMPERATURE_C)[offset])/2;
//            }
//            gridcell.climate.temp = tsum / gridcell.spaceOffsets.size();
//            gridcell.climate.co2 = CO2Concentration.getCo2Concentration(time.getMillis());
//            
//            /*
//             * TODO what do I do with insolation?
//             */
//            gridcell.climate.insol = 70;
//            
//        } else if (transition != null) {
//
//            gridcell.climate.prec = 0;
//            double tsum = 0;
//            for (int offset : gridcell.spaceOffsets) {
//                gridcell.climate.prec += States.getDouble(precipitation, offset, transition);
//                tsum += (States.getDouble(maxTemperature, offset, transition) - States.getDouble(minTemperature, offset, transition))/2;
//            }
//            gridcell.climate.temp = tsum / gridcell.spaceOffsets.size();
//        }
//
//        gridcell.climate.co2 = CO2Concentration.getCo2Concentration(time.getMillis());
//    }
//
//}
