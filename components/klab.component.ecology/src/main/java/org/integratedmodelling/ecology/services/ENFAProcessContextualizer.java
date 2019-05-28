///*******************************************************************************
// * Copyright (C) 2007, 2015:
// * 
// * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
// * other authors listed in @author annotations
// *
// * All rights reserved. This file is part of the k.LAB software suite, meant to enable
// * modular, collaborative, integrated development of interoperable data and model
// * components. For details, see http://integratedmodelling.org.
// * 
// * This program is free software; you can redistribute it and/or modify it under the terms
// * of the Affero General Public License Version 3 or any later version.
// *
// * This program is distributed in the hope that it will be useful, but without any
// * warranty; without even the implied warranty of merchantability or fitness for a
// * particular purpose. See the Affero General Public License for more details.
// * 
// * You should have received a copy of the Affero General Public License along with this
// * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
// * 330, Boston, MA 02111-1307, USA. The license is also available at:
// * https://www.gnu.org/licenses/agpl.html
// *******************************************************************************/
//package org.integratedmodelling.ecology.services;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Random;
//import java.util.Set;
//
//import org.integratedmodelling.api.knowledge.IObservation;
//import org.integratedmodelling.api.modelling.IActiveDirectObservation;
//import org.integratedmodelling.api.modelling.IActiveProcess;
//import org.integratedmodelling.api.modelling.IModel;
//import org.integratedmodelling.api.modelling.INumericObserver;
//import org.integratedmodelling.api.modelling.IObservableSemantics;
//import org.integratedmodelling.api.modelling.IObserver;
//import org.integratedmodelling.api.modelling.IPresenceObserver;
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
//import org.integratedmodelling.common.states.States;
//import org.integratedmodelling.common.vocabulary.NS;
//import org.integratedmodelling.ecology.enfa.math.IMatrix;
//import org.integratedmodelling.ecology.enfa.math.MatrixFactory;
//import org.integratedmodelling.ecology.enfa.math.NicheFactorAnalysis;
//import org.integratedmodelling.exceptions.KlabException;
//import org.integratedmodelling.exceptions.KlabValidationException;
//
//@Prototype(
//        id = "im.ecology.enfa",
//        published = false,
//        returnTypes = { NS.PROCESS_CONTEXTUALIZER },
//        args = {
//                "? nf",
//                Prototype.INT,
//                "? invert",
//                Prototype.BOOLEAN,
//                "? min",
//                Prototype.FLOAT,
//                "? max",
//                Prototype.FLOAT,
//                "? sample",
//                Prototype.FLOAT
//        })
//public class ENFAProcessContextualizer implements IProcessContextualizer {
//
//    boolean                    canDispose       = false;
//    IMonitor                   monitor;
//    private IScale             scale;
//    public static final String OCCURRENCE_TRAIT = "im.enfa:Occurrence";
//
//    private boolean            invertValues     = true;
//    private double             minValue         = Double.NaN;
//    private double             maxValue         = Double.NaN;
//
//    Map<String, IObservation>  outputStates     = new HashMap<>();
//
//    private Set<String>        occurrenceLayers = new HashSet<>();
//    private Set<String>        numericInputs    = new HashSet<>();
//    private Set<String>        booleanInputs    = new HashSet<>();
//
//    /*
//     * FIXME - unused
//     */
//    int                        nf               = 0;
//    int                        rows             = 0;
//    int                        cols             = 0;
//    // private boolean occurrenceNumeric = true;
//    private IMatrix            egvs;
//    private IMatrix            pres;
//
//    private double             sampleFraction   = Double.NaN;
//    private Random             random           = new Random();
//
//    @Override
//    public boolean canDispose() {
//        return canDispose;
//    }
//
//    @Override
//    public void setContext(Map<String, Object> parameters, IModel model, IProject project, IProvenance.Artifact provenance) {
//        if (parameters.containsKey("nf")) {
//            nf = ((Number) parameters.get("nf")).intValue();
//        }
//        if (parameters.containsKey("invert")) {
//            invertValues = (Boolean) parameters.get("invert");
//        }
//        if (parameters.containsKey("min")) {
//            minValue = ((Number) parameters.get("min")).doubleValue();
//        }
//        if (parameters.containsKey("max")) {
//            maxValue = ((Number) parameters.get("max")).doubleValue();
//        }
//        if (parameters.containsKey("sample")) {
//            sampleFraction = ((Number) parameters.get("sample")).doubleValue();
//        }
//    }
//
//    @Override
//    public Map<String, IObservation> initialize(IActiveProcess process, IActiveDirectObservation context, IResolutionScope resolutionContext, Map<String, IObservableSemantics> expectedInputs, Map<String, IObservableSemantics> expectedOutputs, IMonitor monitor)
//            throws KlabException {
//
//        canDispose = !context.getScale().isTemporallyDistributed();
//        this.monitor = monitor;
//        this.scale = context.getScale();
//
//        if (expectedOutputs.isEmpty())
//            throw new KlabException("ENFA needs at least one output observable");
//        if (expectedInputs.isEmpty() || expectedInputs.size() < 2)
//            throw new KlabException("ENFA needs at least two input observables");
//
//        for (String inp : expectedInputs.keySet()) {
//            IObservableSemantics o = expectedInputs.get(inp);
//            if (process.getRolesFor(o).contains(NS.ARCHETYPE_ROLE)) {
//                occurrenceLayers.add(inp);
//            } else if (process.getRolesFor(o).contains(NS.EXPLANATORY_QUALITY_ROLE)) {
//                IObserver obsrv = o.getObserver();
//                if (obsrv instanceof INumericObserver) {
//                    numericInputs.add(inp);
//                } else if (obsrv instanceof IPresenceObserver) {
//                    booleanInputs.add(inp);
//                } else {
//                    throw new KlabValidationException("ENFA: occurrence state " + inp
//                            + " is not numeric or boolean");
//                }
//            }
//        }
//
//        cols = numericInputs.size() + booleanInputs.size();
//        rows = (int) scale.getSpace().getMultiplicity();
//
//        Map<String, IState> states = States.matchStatesToInputs(context, expectedInputs);
//
//        Set<Integer> missingIndexes = new HashSet<>();
//
//        int col = 0;
//        for (String inp : numericInputs) {
//            IState state = states.get(inp);
//            for (int n : scale.getIndex(IScale.Locator.INITIALIZATION)) {
//                double value = States.getDouble(state, n);
//                if (Double.isNaN(value)
//                        || (!Double.isNaN(sampleFraction) && random.nextDouble() < sampleFraction)) {
//                    missingIndexes.add(n);
//                }
//            }
//            col++;
//        }
//
//        egvs = MatrixFactory.createMatrix(rows - missingIndexes.size(), cols);
//
//        col = 0;
//        for (String inp : numericInputs) {
//            monitor.info("ENFA: reading values for " + inp, Messages.INFOCLASS_MODEL);
//
//            IState state = states.get(inp);
//            int row = 0;
//            for (int n : scale.getIndex(IScale.Locator.INITIALIZATION)) {
//                if (!missingIndexes.contains(n)) {
//                    double value = States.getDouble(state, n);
//                    egvs.setElement(row, col, value);
//                    row++;
//                }
//            }
//            col++;
//        }
//
//        for (String inp : booleanInputs) {
//            // monitor.info("ENFA: processing" + inp , Messages.INFOCLASS_MODEL);
//            monitor.info("ENFA: reading values for " + inp, Messages.INFOCLASS_MODEL);
//            IState state = states.get(inp);
//            int row = 0;
//            for (int n : scale.getIndex(IScale.Locator.INITIALIZATION)) {
//                if (!missingIndexes.contains(n)) {
//                    boolean value = States.getBoolean(state, n);
//                    egvs.setElement(row, col, (value ? 1 : 0));
//                    row++;
//                }
//            }
//            col++;
//        }
//
//        // // TODO: Presence data should be filtered according to Output traits
//        pres = MatrixFactory.createMatrix(rows - missingIndexes.size(), 1);
//
//        for (String inp : occurrenceLayers) {
//            monitor.info("ENFA: processing occurrence " + inp, Messages.INFOCLASS_MODEL);
//
//            IState state = states.get(inp);
//            int row = 0;
//            for (int n : scale.getIndex(IScale.Locator.INITIALIZATION)) {
//                if (!missingIndexes.contains(n)) {
//                    double value = pres.getElement(row, 0);
//                    if (state.getObserver() instanceof INumericObserver)
//                        value += States.getDouble(state, n);
//                    else {
//                        boolean bool = States.getBoolean(state, n);
//                        value += (bool ? 1 : 0);
//                    }
//
//                    pres.setElement(row, 0, value);
//                    row++;
//                }
//
//            }
//        }
//
//        /**
//         * TODO print to associated report, not stdout
//         */
//        pres.printStatistics("Occurrence");
//
//        for (String out : expectedOutputs.keySet()) {
//
//            /*
//             * allow single assessment output as learned quality even without the 
//             * role - currently ENFA is not really a learning algorithm and must use
//             * assess, not learn, to avoid messing with learning process semantics.
//             */
//            if (expectedOutputs.size() == 1 || process.getRolesFor(expectedOutputs.get(out)).contains(NS.LEARNED_QUALITY_ROLE)) {
//
//                IObservableSemantics obs = expectedOutputs.get(out);
//                // TODO: Based on the expected output traits, need to filter the
//                // Occurrence
//                // Data in Matrix pres.
//
//                NicheFactorAnalysis model = new NicheFactorAnalysis();
//                IMatrix maha = model.train(pres, egvs);
//                IState outState = context.getStaticState(obs);
//
//                /**
//                 * compute value statistics for normalization and inversion
//                 */
//                int row = 0;
//                double offset = 0;
//                double multiplier = 1;
//                double min = Double.NaN;
//                double max = Double.NaN;
//                for (int n : scale.getIndex(IScale.Locator.INITIALIZATION)) {
//                    if (!missingIndexes.contains(n)) {
//                        double value = maha.getElement(row, 0);
//                        if (!Double.isNaN(value) && (Double.isNaN(min) || min > value)) {
//                            min = value;
//                        }
//                        if (!Double.isNaN(value) && (Double.isNaN(max) || max < value)) {
//                            max = value;
//                        }
//                        row++;
//                    }
//                }
//
//                /*
//                 * TODO support value offsetting or throw an exception if only one is
//                 * given.
//                 */
//                if (!Double.isNaN(minValue) && !Double.isNaN(maxValue) && !Double.isNaN(max)
//                        && !Double.isNaN(min)) {
//                    multiplier = (maxValue - minValue) / (max - min);
//                }
//
//                /**
//                 * To access all the states within a transition (e.g. all points in space
//                 * at the time) we use the scale index. and pass the transition, which
//                 * implements IScale.Locator - an interface that "locks" one or more
//                 * dimensions and returns an iterator for the states along the others. We
//                 * pass a null here, which is understood as the initialization transition.
//                 * There are locators for space.
//                 */
//                row = 0;
//                for (int n : scale.getIndex(IScale.Locator.INITIALIZATION)) {
//                    if (!missingIndexes.contains(n)) {
//                        double value = maha.getElement(row, 0);
//                        if (!Double.isNaN(value) && invertValues) {
//                            value = (max - min) - value;
//                        }
//                        States.set(outState, (value + offset) * multiplier, n);
//                        row++;
//                    } else {
//                        States.set(outState, Double.NaN, n);
//                    }
//                }
//
//                /**
//                 * Set the state as an output. This phase isn't strictly necessary as
//                 * createState() has already created it in the subject - API may change
//                 * later.
//                 */
//                outputStates.put(out, outState);
//            }
//
//        }
//
//        /**
//         * the software will take care of setting these inputs in the context or streaming
//         * them back to the calling engine if we're a remote service.
//         */
//        return outputStates;
//    }
//
//    private IMatrix prepareEGVs(Map<String, IState> inputs) throws KlabException {
//        if (inputs.isEmpty())
//            throw new KlabException("ENFA can't create states");
//        // Check that all states have the same number of values.
//        String firstkey = (String) inputs.keySet().toArray()[0];
//        long length = inputs.get(firstkey).getValueCount();
//        IMatrix m = MatrixFactory.createMatrix(inputs.size(), (int) length);
//        int col = 0;
//        for (String key : inputs.keySet()) {
//            IState state = inputs.get(key);
//            if (state.getValueCount() != length)
//                throw new KlabException("ENFA states do not have the same dimensions");
//            for (int row = 0; row < length; row++) {
//                m.setElement(row, col, (double) state.getValue(row));
//            }
//        }
//        return m;
//    }
//
//    @Override
//    public Map<String, IObservation> compute(ITransition transition, Map<String, IState> inputs)
//            throws KlabException {
//        canDispose = transition.isLast();
//        return null;
//    }
//
//}
