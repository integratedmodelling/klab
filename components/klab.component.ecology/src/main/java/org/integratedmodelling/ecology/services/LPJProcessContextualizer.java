//package org.integratedmodelling.ecology.services;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.integratedmodelling.api.knowledge.IConcept;
//import org.integratedmodelling.api.knowledge.IObservation;
//import org.integratedmodelling.api.modelling.IActiveDirectObservation;
//import org.integratedmodelling.api.modelling.IActiveProcess;
//import org.integratedmodelling.api.modelling.IModel;
//import org.integratedmodelling.api.modelling.IObservableSemantics;
//import org.integratedmodelling.api.modelling.IScale;
//import org.integratedmodelling.api.modelling.IState;
//import org.integratedmodelling.api.modelling.contextualization.IProcessContextualizer;
//import org.integratedmodelling.api.modelling.resolution.IResolutionScope;
//import org.integratedmodelling.api.modelling.scheduling.ITransition;
//import org.integratedmodelling.api.monitoring.IMonitor;
//import org.integratedmodelling.api.monitoring.Messages;
//import org.integratedmodelling.api.project.IProject;
//import org.integratedmodelling.api.provenance.IProvenance;
//import org.integratedmodelling.api.services.annotations.Prototype;
//import org.integratedmodelling.api.space.ISpatialExtent;
//import org.integratedmodelling.common.configuration.KLAB;
//import org.integratedmodelling.common.states.States;
//import org.integratedmodelling.common.utils.parallel.Parallel;
//import org.integratedmodelling.common.utils.parallel.ParallelOp;
//import org.integratedmodelling.common.vocabulary.EcologyNS;
//import org.integratedmodelling.common.vocabulary.GeoNS;
//import org.integratedmodelling.common.vocabulary.NS;
//import org.integratedmodelling.common.vocabulary.Traits;
//import org.integratedmodelling.ecology.biomass.lpjguess.Configuration;
//import org.integratedmodelling.ecology.biomass.lpjguess.Gridcell;
//import org.integratedmodelling.ecology.biomass.lpjguess.drivers.LPJ;
//import org.integratedmodelling.ecology.biomass.lpjguess.drivers.LPJOutputDriver;
//import org.integratedmodelling.ecology.biomass.lpjguess.drivers.LPJWeatherDriver;
//import org.integratedmodelling.engine.geospace.gis.MapClassifier;
//import org.integratedmodelling.engine.time.literals.TimeValue;
//import org.integratedmodelling.exceptions.KlabException;
//import org.integratedmodelling.exceptions.KlabRuntimeException;
//import org.integratedmodelling.exceptions.KlabValidationException;
//import org.integratedmodelling.hydrology.weather.Weather;
//import org.integratedmodelling.hydrology.weather.WeatherFactory;
//import org.integratedmodelling.lang.IAggregation;
//import org.integratedmodelling.procsim.api.IConfiguration.VegetationMode;
//import org.joda.time.DateTime;
//import org.joda.time.Days;
//import org.joda.time.Period;
//import org.joda.time.format.PeriodFormat;
//
///**
// * This is a copy of the example Process Contextualiser, to use as a starting point for
// */
//@Prototype(id = "im.ecology.lpjguess", returnTypes = { NS.PROCESS_CONTEXTUALIZER }, args = {
//        "? mode",
//        "cohort|population",
//        "? spinup-years",
//        Prototype.INT,
//        "? spinup-start-year",
//        Prototype.INT }, published = false)
//public class LPJProcessContextualizer implements IProcessContextualizer {
//
//    boolean                   runDistributed          = false;
//    boolean                   runInParallel           = false;
//    boolean                   canDispose              = false;
//    IScale                    scale                   = null;
//    Map<String, IObservation> outputStates            = new ConcurrentHashMap<>();
//    String                    mode                    = "population";
//    private IMonitor          monitor;
//
//    Configuration             config;
//
//    // Input states
//    private IState            precipitation;
//    private IState            maxTemperature;
//    private IState            minTemperature;
//    private IState            percSunshine;
//
//    // Output states
//    private IState            aboveGroundBiomassState;
//
//    private List<Gridcell>    ecologicalResponseUnits = new ArrayList<>();
//    private List<LPJ>         processes;
//    private int               spinupStartYear        = 1980;
//    private int               spinupYears            = -1;
//
//    @Override
//    public boolean canDispose() {
//        return canDispose;
//    }
//
//    @Override
//    public Map<String, IObservation> initialize(IActiveProcess process, IActiveDirectObservation context, IResolutionScope resolutionContext, Map<String, IObservableSemantics> expectedInputs, Map<String, IObservableSemantics> expectedOutputs, IMonitor monitor)
//            throws KlabException {
//
//        // Store the scale and monitor
//        this.scale = process.getScale();
//        this.monitor = monitor;
//
//        // We can get rid of this object after initialisation if there is no
//        // temporal aspect to
//        // the context
//        canDispose = !context.getScale().isTemporallyDistributed();
//
//        // Get the input states here, and put them in sensible members
//        // using States.findState()
//        // TODO: Will return null if it can't find it - so make sure we check
//        // here
//        this.maxTemperature = States
//                .findStateWith(context, EcologyNS.ATMOSPHERIC_TEMPERATURE, EcologyNS.MAXIMUM_TRAIT);
//        this.minTemperature = States
//                .findStateWith(context, EcologyNS.ATMOSPHERIC_TEMPERATURE, EcologyNS.MINIMUM_TRAIT);
//        this.precipitation = States
//                .findStateWithout(context, EcologyNS.PRECIPITATION_VOLUME, EcologyNS.YEARLY_TRAIT);
//
//        /*
//         * GlobCover, as before. We should switch to something better as soon as it's
//         * guaranteed available, expecially given the little detail we need, and overlay a
//         * 'presence of peatland' observation for peatlands.
//         */
//        IState landcover = States.findState(context, GeoNS.GLOBCOVER_CLASS);
//        IState elevation = States.findState(context, GeoNS.ELEVATION);
//        IState annualPrecipitation = States
//                .findStateWith(context, EcologyNS.PRECIPITATION_VOLUME, EcologyNS.YEARLY_TRAIT);
//
//        EcologyNS.synchronize();
//
//        IObservableSemantics aboveGroundBiomassObservation = null;
//        String aboveGroundBiomassId = null;
//        for (String iname : expectedOutputs.keySet()) {
//            if (expectedOutputs.get(iname).is(EcologyNS.ABOVE_GROUND_BIOMASS)) {
//                aboveGroundBiomassId = iname;
//                aboveGroundBiomassObservation = expectedOutputs.get(iname);
//                aboveGroundBiomassState = context.getState(aboveGroundBiomassObservation);
//            }
//        }
//
//        // Create one Configuration object
//        this.config = new Configuration(VegetationMode.get(mode));
//
//        monitor.info("lpj-guess initializing in " + mode + " mode", Messages.INFOCLASS_MODEL);
//
//        // Set the initial timestamp of the simulation to be the start time
//        // of the temporal range
//        TimeValue tv = (TimeValue) scale.getTime().getStart();
//        config.getSchedule().setInitialTimestamp(tv);
//
//        // if (runDistributed) {
//        // initializeDistributed();
//        // } else {
//
//        List<IState> classifiers = new ArrayList<>();
//        for (IState s : context.getStates()) {
//            /*
//             * TODO use roles and align with namespace imports
//             */
//            if (Traits.hasTrait(s.getObservable().getSemantics(), KLAB.c("ecology:Habitat"))) {
//                classifiers.add(s);
//            }
//        }
//
//        if (classifiers.isEmpty()) {
//            throw new KlabValidationException("no classifiers defined: cannot run LPJ in fully distributed mode");
//        }
//
//        MapClassifier classifier = new MapClassifier(classifiers, 20, monitor, (IScale.Locator) null);
//        int eruCount = classifier.classify();
//        Gridcell[] erus = new Gridcell[eruCount];
//
//        for (int n : scale.getIndex(IScale.Locator.INITIALIZATION)) {
//
//            int spaceOffset = scale.getExtentOffset(scale.getSpace(), n);
//            int eruIdx = classifier.getClass(spaceOffset);
//            ISpatialExtent space = scale.getSpace().getExtent(spaceOffset);
//            IConcept lc = (IConcept) States.get(landcover, n);
//
//            /*
//             * TODO link to data
//             */
//            int soilCode = 1;
//
//            Gridcell eru = erus[eruIdx];
//            if (eru == null) {
//                eru = new Gridcell(config);
//                erus[eruIdx] = eru;
//            }
//
//            /*
//             * Scan maps: go over LC, climatic vars, and soil Record highest/lowest
//             * lat/lon Compute landcover fractions. TODO: also feed slow pool data from
//             * previous runs. Afraid those pools are pooled and we have to partition them
//             * somehow. The C and N mass go into SOM within each Patch - distribute by
//             * land use fraction. The rest, no idea for now.
//             */
//            eru.addExtent(space, spaceOffset, lc, soilCode);
//
//        }
//
//        /*
//         * Finish ERU initialization, throwing away the degenerate ones.
//         */
//        for (Gridcell eru : erus) {
//            if (eru.finish()) {
//                ecologicalResponseUnits.add(eru);
//            }
//        }
//
//        monitor.info("initialized " + ecologicalResponseUnits.size()
//                + " ecological response units", Messages.INFOCLASS_MODEL);
//
//        /*
//         * Get historical climate object and adapt into each ERU for short spin-up.
//         */
//
//        monitor.info("generating representative weather for short spin-up since 1980", Messages.INFOCLASS_MODEL);
//
//        List<IState> contextData = new ArrayList<>();
//        if (elevation != null) {
//            contextData.add(elevation);
//        }
//        if (annualPrecipitation != null) {
//            contextData.add(annualPrecipitation);
//        }
//
//        DateTime endSpinup = new DateTime(scale.getTime().getEnd().getMillis()).minusDays(1);
//        if (spinupYears > 0) {
//            spinupStartYear = endSpinup.getYear();
//        } else if (spinupStartYear < 0) {
//            spinupStartYear = 1980;
//        }
//
//        Weather spinupWeather = WeatherFactory
//                .getRepresentativeWeather(scale, 1980, 1985, contextData, monitor);
//
//        /*
//         * Compute 1980-start time spin-up for faster pools.
//         */
//        DateTime currentDate = new DateTime(spinupStartYear, 1, 1, 0, 0);
//
//        KLAB.info("starting " + (endSpinup.getYear() - currentDate.getYear() + 1)
//                + "-year short spin-up cycle");
//
//        runPeriod(currentDate, endSpinup, new LPJWeatherDriver(spinupWeather), null);
//
//        /*
//         * Report on short spin-up.
//         */
//        KLAB.info((endSpinup.getYear() - currentDate.getYear() + 1)
//                + "-year spin-up finished: initialization complete");
//
//        /*
//         * Setup current climate and get ready for actual run.
//         */
//        LPJWeatherDriver runWeather = new LPJWeatherDriver(precipitation, minTemperature, maxTemperature, percSunshine);
//        this.processes = new ArrayList<>();
//
//        /*
//         * Redistribute initial maps for all outputs requested.
//         */
//
//        // }
//        outputStates.put(aboveGroundBiomassId, aboveGroundBiomassState);
//        return outputStates;
//    }
//
//    private void runPeriod(DateTime currentDate, DateTime endSpinup, LPJWeatherDriver weather, LPJOutputDriver output) {
//
//        config.getSchedule().setInitialTimestamp(new TimeValue(currentDate.getMillis()));
//        this.processes = new ArrayList<>();
//        for (Gridcell eru : ecologicalResponseUnits) {
//            processes.add(new LPJ(eru, weather, output));
//        }
//
//        long start = System.nanoTime();
//        for (int i = 0; currentDate.isBefore(endSpinup); currentDate = currentDate.plusDays(1), i++) {
//            runDay(processes, currentDate, weather);
//            if (i == 0) {
//                long dayend = System.nanoTime();
//                int ndays = Days.daysBetween(currentDate.toLocalDate(), endSpinup.toLocalDate()).getDays();
//                Period period = new Period(ndays * (dayend - start)/1000000);
//                monitor.info("lpj: estimated end of spin-up in "
//                        + PeriodFormat.getDefault().print(period), Messages.INFOCLASS_MODEL);
//            }
//        }
//        
//        redistributeOutputs(processes, ITransition.INITIALIZATION);
//        
//    }
//
//    private void redistributeOutputs(List<LPJ> procs, ITransition initialization) {
//
//        int i = 0;
//        for (LPJ lpj : procs) {
//            monitor.info("[" + i + "] cLitter =  " + lpj.getOutputs().c_litter, Messages.INFOCLASS_HAPPY);
//            monitor.info("[" + i + "] cFast =    " + lpj.getOutputs().c_fast, Messages.INFOCLASS_HAPPY);
//            monitor.info("[" + i + "] cSlow =    " + lpj.getOutputs().c_slow, Messages.INFOCLASS_HAPPY);
//            monitor.info("[" + i + "] cMass =    " + lpj.getOutputs().cmass_stand, Messages.INFOCLASS_HAPPY);
//            monitor.info("[" + i + "] Dens =     " + lpj.getOutputs().dens_stand, Messages.INFOCLASS_HAPPY);
//            monitor.info("[" + i + "] LAI =      " + lpj.getOutputs().lai_stand, Messages.INFOCLASS_HAPPY);
//            monitor.info("[" + i + "] FluxFire = " + lpj.getOutputs().flux_fire, Messages.INFOCLASS_HAPPY);
//            monitor.info("[" + i + "] fluxSoil = " + lpj.getOutputs().flux_soil, Messages.INFOCLASS_HAPPY);
//            monitor.info("[" + i + "] fluxVeg =  " + lpj.getOutputs().flux_veg, Messages.INFOCLASS_HAPPY);
//            monitor.info("[" + i + "] ANPP =     " + lpj.getOutputs().anpp_stand, Messages.INFOCLASS_HAPPY);
//        }
//        
//    }
//
//    /**
//     * Run one day of simulation on the set of ERUs using the passed weather and output
//     * drivers.
//     * 
//     * @param processes
//     * @param currentDate
//     * @param lpjWeatherDriver
//     */
//    private void runDay(List<LPJ> processes, DateTime currentDate, LPJWeatherDriver weather) {
//        config.getSchedule().setTimestamp(new TimeValue(currentDate.getMillis()));
//        weather.setDate(currentDate);
//        for (LPJ lpj : processes) {
//            lpj.run();
//        }
//    }
//
//    /**
//     * Run one transition on the set of ERUs using the passed weather and output drivers.
//     * May do nothing if the transition does not imply a day since the last run.
//     * 
//     * @param processes
//     * @param currentDate
//     * @param lpjWeatherDriver
//     * @param object
//     */
//    private void runDay(List<LPJ> processes, ITransition currentTransition, LPJWeatherDriver weather) {
//        config.getSchedule().setTimestamp(new TimeValue(currentTransition.getTime().getStart().getMillis()));
//        weather.setTransition(currentTransition);
//        for (LPJ lpj : processes) {
//            lpj.run();
//        }
//    }
//
//    // private void initializeDistributed() {
//    // // TODO Auto-generated method stub
//    // // For each of the spatial areas (ie. grid cells)
//    // // initialise LPJ
//    // for (int n : scale.getIndex((IScale.Locator) null)) {
//    //
//    // int spaceOffset = scale.getExtentOffset(scale.getSpace(), n);
//    //
//    // // monitor.info("Creating a stand (for a gridcell)", null);
//    //
//    // // // Get latitude of centre of current spatial area (grid cell)
//    // // // to give to the Climate object eventually
//    // ISpatialExtent currentSpace = scale.getSpace().getExtent(spaceOffset);
//    // Point point = ((IGeometricShape)
//    // currentSpace).getStandardizedGeometry().getCentroid();
//    //
//    // double latitude = point.getY();
//    // double longitude = point.getX();
//    //
//    // // monitor.info("Location: Lat = " + latitude + ", Lon = " +
//    // // longitude, null);
//    // Gridcell gridcell = new Gridcell(config, Soiltype.getLPJSoiltype(2), latitude,
//    // longitude);
//    //
//    // // Set constant LC fractions - which also creates the required
//    // // stands of the
//    // // required StandTypes
//    // // TODO: Update this to do dynamic LC
//    // gridcell.setLCFractions(0.1, 0.0, 0.4, 0.1, 0.3, 0.1);
//    //
//    // // monitor.info("Created Stands", null);
//    //
//    // // Store the list of stands so we can use it in the compute calls
//    // // gridcells.put(spaceOffset, gridcell);
//    //
//    // // Set all outputs to zero
//    // States.set(aboveGroundBiomassState, 0.0, n);
//    // }
//    // }
//
//    @Override
//    public Map<String, IObservation> compute(ITransition transition, Map<String, IState> inputs)
//            throws KlabException {
//
//        Map<String, IObservation> ret = new HashMap<>();
//
//        canDispose = transition.isLast();
//
//        // Set the current timestamp in the config object
//        TimeValue tv = (TimeValue) transition.getTime().getStart();
//        // DataRecorder.get().info("Setting timestamp to: " + tv.asText());
//        config.getSchedule().setTimestamp(tv);
//        // DataRecorder.get().info("Day of month (0-based): " +
//        // config.getSchedule().dayofmonth());
//        // DataRecorder.get().info("Day of month): " + tv.getDayOfMonth());
//        // DataRecorder.get().info("Days in month?" + tv.getNDaysInMonth());
//        // DataRecorder.get().info("End of month?" +
//        // config.getSchedule().isLastDayOfMonth());
//
//        if (runInParallel) {
//            Parallel.ForEach(ecologicalResponseUnits, new StateExecutor(transition, inputs), 8);
//        } else {
//            for (Gridcell cell : ecologicalResponseUnits) {
//                new StateExecutor(transition, inputs).run(cell);
//            }
//        }
//
//        ret.putAll(outputStates);
//        return ret;
//    }
//
//    @Override
//    public void setContext(Map<String, Object> parameters, IModel model, IProject project, IProvenance.Artifact provenance) {
//
//        if (parameters.containsKey("mode")) {
//            mode = parameters.get("mode").toString();
//        }
//        if (parameters.containsKey("spinup-start-year") && parameters.containsKey("spinup-years")) {
//            throw new KlabRuntimeException("im.ecology.lpjguess: cannot specify both spinup-start-year and spinup-years");
//        }
//        if (parameters.containsKey("spinup-start-year")) {
//            spinupStartYear = Integer.parseInt(parameters.get("spinup-start-year").toString());
//            spinupYears = -1;
//        }
//        if (parameters.containsKey("spinup-years")) {
//            spinupYears = Integer.parseInt(parameters.get("spinup-years").toString());
//            spinupStartYear = -1;
//        }
//    }
//
//    public class StateExecutor implements ParallelOp<Gridcell> {
//
//        ITransition         transition;
//        Map<String, IState> inputs;
//
//        public StateExecutor(ITransition transition, Map<String, IState> inputs) {
//            super();
//            this.transition = transition;
//            this.inputs = inputs;
//        }
//
//        @Override
//        public void run(Gridcell gridcell) {
//
//            // Get the climatic data from the states
//            // Add this in when we can get percentage sunshine data from weather
//            // (or somewhere else)
//            // double sunshine = States.getDouble(perc_sunshine, n);
//            // Random r = new Random();
//            // double sunshine = r.nextDouble() * 100;
//            // double sunshine = 70.0;
//
//            gridcell.setClimate(/*
//                                 * TODO find way to pass multiple states and operation
//                                 * gridcell.getState(minTemperature, maxTemperature,
//                                 * aggregation)
//                                 */ 0, ((Number) gridcell.getState(precipitation, IAggregation.AGGREGATE))
//                    .doubleValue(), ((Number) gridcell.getState(percSunshine, IAggregation.AVERAGE))
//                            .doubleValue());
//
//            LPJ lpj = new LPJ(gridcell, null, null);
//            lpj.run();
//            lpj.getOutputs().annualStandOutput();
//
//            // DataRecorder.get().info("C mass:" +
//            // lpj.getOutputs().cmass_stand);
//            // DataRecorder.get().info("LAI: " + lpj.getOutputs().lai_stand);
//            // DataRecorder.get().info(lpj.getOutputs().anpp_stand);
//
//            /*
//             * for <list of output states> setState(state,
//             * lpj.getOutput(state.getObservable().getType()))
//             */
//
//            // States.set(aboveGroundBiomassState, lpj.getOutputs().cmass_stand, n);
//        }
//
//    }
//
//}
