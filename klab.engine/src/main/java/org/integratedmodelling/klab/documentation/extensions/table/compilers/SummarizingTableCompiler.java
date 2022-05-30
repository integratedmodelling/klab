package org.integratedmodelling.klab.documentation.extensions.table.compilers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimLookupTable.Argument.Dimension;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.extensions.ITableCompiler;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.observations.IKnowledgeView.Attribute;
import org.integratedmodelling.klab.api.observations.IKnowledgeView.Builder;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.debugger.Statistics;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;

/**
 * Contabilize a variable or a context attribute by arbitrarily defined rows and columns. Optionally
 * add row and column totals. Smart about time requests that point to the same time.
 * 
 * @author Ferd
 *
 */
public class SummarizingTableCompiler implements ITableCompiler {

    IState sourceState;
    boolean contabilizeNulls = false;
    List<?> comparedStates;
    IObservedConcept target;
    String reportedValue;
    // boolean rowTotals = false;
    // boolean colTotals = false;
    IUnit unit;
    String emptyValue = "0.0";
    String noDataValue = "0.0";
    boolean hasNulls = false;
    Map<String, Object> templateVars;
    int nextYear = 0;

    public static Set<String> phaseTokens = new HashSet<>();

    static {
        phaseTokens.add("init");
        phaseTokens.add("start");
        phaseTokens.add("end");
        phaseTokens.add("years");
    }

    /*
     * hashes to keep the correspondence between the original values and their numeric value and the
     * combined values and the value for the final histogram.
     */
    int nextId = 2; // use 1 for nodata and start at 2 for actual data
    BiMap<Object, Integer> codes = HashBiMap.create();
    Map<Pair<Object, Object>, Double> bins = new HashMap<>();
    private Boolean extensive;

    // these can only appear in one dimension and determine the phase to look at
    private List<Object> phaseSelectors = new ArrayList<>();
    // these may be in multiple dimensions, although >1 isn't supported at the moment
    private List<Object> otherSelectors = new ArrayList<>();
    private Map<Object, List<Object>> collectedSelectors = new HashMap<>();
    private Object phaseDimension;

    private List<Object> rowSelectors;
    private List<Object> colSelectors;
    private String statistic;
    private IContextualizationScope scope;

    @Override
    public void initialize(Parameters<String> parameters, Map<?, ?> tableDefinition, IContextualizationScope scope) {

        if (scope == null) {
            // not viable, but this instance will not be used.
            return;
        }

        if (tableDefinition.containsKey("target")) {
            this.target = Observables.INSTANCE.asObservedConcept(tableDefinition.get("target"));
            if (this.target != null) {
                IObservation state = ((IRuntimeScope) scope).getCatalog().get(this.target);
                if (state instanceof IState) {
                    this.sourceState = (IState) state;
                }
            }
        }

        if (parameters.contains("unit")) {
            unit = Unit.create(parameters.get("unit", String.class));
        }

        if (parameters.contains("rows")) {
            processStructure(parameters.get("rows"), Dimension.ROW, scope);
        }

        if (parameters.contains("columns")) {
            processStructure(parameters.get("columns"), Dimension.COLUMN, scope);
        }

        /*
         * validate the monstrosity
         */
        if (colSelectors == null && rowSelectors == null) {
            throw new KlabIllegalStateException("table summarizer needs explicit rows and columns arguments");
        } else if (colSelectors == null && phaseSelectors == null) {
            colSelectors = phaseSelectors = new ArrayList<>();
            colSelectors.add("start");
        } else if (rowSelectors == null && phaseSelectors == null) {
            rowSelectors = phaseSelectors = new ArrayList<>();
            rowSelectors.add("start");
        } else if (rowSelectors == null || colSelectors == null) {
            throw new KlabIllegalStateException(
                    "table summarizer needs explicit rows and columns if temporal phases are specified");
        }

        if (parameters.contains("group")) {
            // later. Could use concepts, classifiers or metadata fields
        }

        // area, time, count, value [default]
        this.reportedValue = parameters.get("report", "value");
        if ("area".equals(this.reportedValue) && this.unit == null) {
            this.unit = Units.INSTANCE.SQUARE_KILOMETERS;
        }
        // true, false -> if true, force extensive measurement and must be value
        this.extensive = parameters.get("extensive", false);
        // aggregation, sum, mean, count, nodatacount, datacount, variance, std, median, dominant
        // aggregation resolves to sum, mean or dominant according to unit and semantics
        this.statistic = parameters.get("statistic", "aggregation");
        this.comparedStates = parameters.get("compare", List.class);
        this.contabilizeNulls = parameters.get("contabilize-nulls", Boolean.FALSE);
        this.emptyValue = parameters.get("empty", "0.0");
        this.noDataValue = parameters.get("no-data", "0.0");
        this.templateVars = getTemplateVars(scope);
        if (this.sourceState == null) {
            throw new KlabIllegalArgumentException("Summarizing table compiler called with insufficient arguments");
        }
        this.scope = scope;

    }

    private void processStructure(Object rowSpecs, Dimension dimension, IContextualizationScope scope) {

        boolean isPhase = false;
        for (Object selector : CollectionUtils.flatCollection(rowSpecs)) {
            if (isPhase(selector)) {
                phaseSelectors.add(selector);
                isPhase = true;
            } else if (isPhase) {
                throw new KlabIllegalStateException(
                        "table summarizer: a dimension containing temporal phases must be consistent and contain no other selector types");
            } else {
                /*
                 * could be a classifier or an observable; start by parsing it as observable and see
                 * what comes out
                 */
                IObservable observable = Observables.INSTANCE.parseObservable(selector, scope.getMonitor());
                if (!(observable instanceof IObservable)) {
                    if (observable instanceof IKimClassifier) {
                        otherSelectors.add(new Classifier((IKimClassifier) selector));
                    } else {
                        throw new KlabIllegalStateException("table summarizer: can't use " + selector + " as selector");
                    }
                } else {
                    otherSelectors.add(observable);
                }
            }
        }

        if (isPhase) {
            if (phaseDimension != null) {
                throw new KlabIllegalStateException("table summarizer: can't have more than one dimension for the phase");
            }
            phaseDimension = dimension;
        }

        if (dimension == Dimension.ROW) {
            rowSelectors = isPhase ? phaseSelectors : otherSelectors;
        } else {
            colSelectors = isPhase ? phaseSelectors : otherSelectors;
        }

    }

    private boolean isPhase(Object selector) {
        return selector instanceof String && phaseTokens.contains((String) selector);
    }

    private Map<String, Object> getTemplateVars(IContextualizationScope scope) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("init", "Start of " + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
                scope.getRootSubject().getScale().getTime().getResolution()));
        ret.put("start", Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
                scope.getRootSubject().getScale().getTime().getResolution()));
        ret.put("end", "Start of " + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getEnd(),
                scope.getRootSubject().getScale().getTime().getResolution()));
        return ret;
    }

    @Override
    public void compile(Builder builder) {

        builder.setEmptyCells(emptyValue, noDataValue);
        this.nextYear = 0;
        Map<Pair<Object, List<Object>>, Statistics> data = new HashMap<>();

        List<String> phaseClassifiers = new ArrayList<>();
        List<Set<Object>> dimensionClassifiers = new ArrayList<>();

        Map<IObservedConcept, IObservation> catalog = ((IRuntimeScope) scope).getCatalog();

        for (String phase : getPhaseSelectors()) {

            IScale scale = getScale(phase);
            if (scale == null) {
                break;
            }

            phaseClassifiers.add(phase);

            for (ILocator locator : scale) {

                /*
                 * categorize re: all the other locators
                 */
                Object value = sourceState.get(locator);
                List<Object> classifiers = getClassifiers(value, locator, catalog, scope);
                if (classifiers != null) {

                    for (int i = 0; i < classifiers.size(); i++) {
                        Set<Object> ccl = null;
                        if (dimensionClassifiers.size() < i + 1) {
                            ccl = new LinkedHashSet<>();
                            dimensionClassifiers.add(ccl);
                        } else {
                            ccl = dimensionClassifiers.get(i);
                        }
                        ccl.add(classifiers.get(i));
                    }

                    Pair<Object, List<Object>> key = new Pair<>(phase, classifiers);
                    Statistics d = data.get(key);
                    if (d == null) {
                        d = new Statistics();
                        data.put(key, d);
                    }
                    d.add(getValue(value, locator));
                }
            }
        }

        // by row, add columns as we go
        boolean first = true;
        Map<Object, String> columnIds = new HashMap<>();
        Map<Object, String> rowIds = new HashMap<>();

        Set<List<Object>> flatSelectors = sort(Sets.cartesianProduct(dimensionClassifiers));
        for (Object rowsel : (phaseDimension == Dimension.ROW ? phaseClassifiers : flatSelectors)) {
            for (Object colsel : (phaseDimension == Dimension.ROW ? flatSelectors : phaseClassifiers)) {
                String columnId = columnIds.get(colsel);
                if (columnId == null) {
                    // TODO multiple classifiers
                    columnId = builder.getColumn(Attribute.HEADER, colsel);
                    columnIds.put(colsel, columnId);
                }
                String rowId = rowIds.get(rowsel);
                if (rowId == null) {
                    rowId = builder.getRow(Attribute.HEADER, rowsel);
                    rowIds.put(rowsel, rowId);
                }

                Pair<String, List<Object>> key = new Pair<>(phaseDimension == Dimension.ROW ? (String) rowsel : (String) colsel,
                        phaseDimension == Dimension.ROW ? (List<Object>) colsel : (List<Object>) rowsel);

                Statistics dat = data.get(key);
                if (dat != null) {
                    builder.setCell(rowId, columnId, getCellValue(dat));
                }

            }

            if (first) {

                /*
                 * add columns for desired row statistics
                 */

            }

            first = false;
        }

        /*
         * add rows for desired column statistics, including the added statistics for rows
         */

        /*
         * reconstruct the table based on the specs
         */

        // bins.clear();
        // codes.clear();
        // nextId = 2;
        //
        //
        // /*
        // * Get the two slices to compare; use the storage directly if possible. If same slice or
        // * non-existing, give up.
        // */
        // ITime first = getTime(sourceState.getScale().getTime(), this.comparedStates.get(0));
        // ITime last = getTime(sourceState.getScale().getTime(), this.comparedStates.get(1));
        //
        // Map<Object, Statistics> data = new HashMap<>();
        //
        // /*
        // * Create temporary storage during the first pass
        // */
        // try (BasicFileMappedStorage<Double> storage = new
        // BasicFileMappedStorage<Double>(Double.class,
        // sourceState.getSpace().size(), 1)) {
        //
        // /*
        // * first pass
        // */
        // long ofs = 0;
        // for (ILocator locator : sourceState.getScale().at(first)) {
        //
        // double value = 1;
        // if ("area".equals(reportedValue)) {
        // value = areaUnit
        // .convert(((IScale) locator).getSpace().getStandardizedArea(),
        // Units.INSTANCE.SQUARE_METERS)
        // .doubleValue();
        // }
        //
        // Object val = sourceState.get(locator);
        // int code = getCode(val);
        // storage.set((double) code, ofs++);
        //
        // if (val == null) {
        // val = OWL.INSTANCE.getNothing();
        // }
        //
        //// SData dat = data.get(val);
        //// if (dat == null) {
        //// dat = new SData();
        //// data.put(val, dat);
        //// }
        //
        //// dat.opening += value;
        // }
        //
        //
        // /*
        // * build the fucka
        // */
        // SData unassigned = null;
        // Map<String, Object> labels = new HashMap<>();
        // for (Object key : data.keySet()) {
        // if (OWL.INSTANCE.getNothing().equals(key)) {
        //// unassigned = data.get(key);
        // } else {
        // labels.put(getLabel(key), key);
        // }
        // }
        //
        // List<String> labs = new ArrayList<>(labels.keySet());
        // Collections.sort(labs);
        // if (unassigned != null) {
        // labs.add("Unaccounted");
        // labels.put("Unaccounted", OWL.INSTANCE.getNothing());
        // }
        //
        //// String rowIncoming = builder.getRow(Attribute.HEADER_0,
        //// TemplateUtils.expandMatches("Opening area {start}", templateVars).get(0), Style.BOLD);
        //// String rowAdditions = builder.getRow(Attribute.HEADER_1, "Expansions");
        //// String rowReductions = builder.getRow(Attribute.HEADER_1, "Regressions");
        //// String rowNetChange = builder.getRow(Attribute.HEADER_1, "Net change");
        //// String rowOutgoing = builder.getRow(Attribute.HEADER_0,
        //// TemplateUtils.expandMatches("Closing area {end}", templateVars).get(0), Style.BOLD);
        //
        //// SData totals = new SData();
        //
        //// for (Object label : labs) {
        //// SData dat = data.get(labels.get(label));
        //// String column = builder.getColumn("Unaccounted".equals(label) ? label :
        // labels.get(label));
        //// builder.setCell(rowIncoming, column, dat.opening);
        //// builder.setCell(rowOutgoing, column, dat.closing);
        //// builder.setCell(rowNetChange, column, dat.closing - dat.opening);
        //// builder.setCell(rowAdditions, column, dat.additions);
        //// builder.setCell(rowReductions, column, dat.reductions);
        ////
        //// totals.opening += dat.opening;
        //// totals.closing += dat.closing;
        //// totals.additions += dat.additions;
        //// totals.reductions += dat.reductions;
        //// }
        ////
        //// String colTotals = builder.getColumn("Totals", Style.BOLD);
        //// builder.setCell(rowIncoming, colTotals, totals.opening);
        //// builder.setCell(rowOutgoing, colTotals, totals.closing);
        //// builder.setCell(rowNetChange, colTotals, totals.closing - totals.opening);
        //// builder.setCell(rowAdditions, colTotals, totals.additions);
        //// builder.setCell(rowReductions, colTotals, totals.reductions);

    }

    // aggregation, sum, mean, count, nodatacount, datacount, variance, std, median, dominant
    // aggregation resolves to sum, mean or dominant according to unit and semantics
    private Object getCellValue(Statistics dat) {
        switch(this.statistic) {
        case "aggregate":
            // TODO
        case "sum":
            return dat.getSum();
        case "mean":
            return dat.getMean();
        case "count":
            return dat.getN();
        case "nodatacount":
            return dat.getNodataCount();
        case "datacount":
            return dat.getTotalCount();
        case "variance":
            return dat.getVariance();
        case "std":
            return dat.getStandardDeviation();
        }
        return null;
    }

    private Set<List<Object>> sort(Set<List<Object>> cartesianProduct) {
        // TODO sort by first, then others, using reliable labels
        return cartesianProduct;
    }

    private Object getValue(Object value, ILocator locator) {
        Object ret = value;
        if ("area".equals(this.reportedValue)) {
            ISpace space = locator instanceof IScale
                    ? ((IScale) locator).getSpace()
                    : (locator instanceof ISpace ? (ISpace) locator : null);
            double area = space == null ? 0 : space.getStandardizedArea();
            if (unit != null) {
                area = unit.convert(area, Units.INSTANCE.SQUARE_METERS).doubleValue();
            }
            ret = area;
        } else if (ret instanceof Number && unit != null && sourceState.getObservable().getUnit() != null) {
            ret = unit.convert((Number) ret, sourceState.getObservable().getUnit());
        }
        return ret;
    }

    private List<Object> getClassifiers(Object value, ILocator locator, Map<IObservedConcept, IObservation> catalog,
            IContextualizationScope scope) {

        if (!contabilizeNulls && Observations.INSTANCE.isNodata(value)) {
            return null;
        }

        List<Object> ret = new ArrayList<>();
        for (Object o : otherSelectors) {
            Object match = null;
            if (o instanceof IObservable) {
                if (Observations.INSTANCE.isNodata(value)) {
                    o = OWL.INSTANCE.getNothing();
                }
                IObservation obs = catalog.get(new ObservedConcept((IObservable) o));
                match = obs instanceof IState ? ((IState) obs).get(locator) : null;
            } else if (o instanceof IClassifier) {
                match = ((IClassifier) o).classify(value, scope) ? o : null;
            }

            if (match != null) {
                ret.add(match);
            } else {
                return null;
            }
        }
        return ret;
    }

    private IScale getScale(String phase) {

        switch(phase) {
        case "init":
            return sourceState.getScale().initialization();
        case "start":
            return sourceState.getScale().at(sourceState.getScale().getTime().earliest());
        case "end":
            return sourceState.getScale().at(sourceState.getScale().getTime().latest());
        case "years":
            if (nextYear < sourceState.getScale().getTime().size() - 2) {
                nextYear++;
                return sourceState.getScale().at(sourceState.getScale().getTime().getExtent(nextYear));
            }
        }

        return null;
    }

    private List<String> getPhaseSelectors() {

        List<String> ret = new ArrayList<>();

        /*
         * simplify if needed
         */
        boolean isStatic = sourceState.getScale().getTime() == null || sourceState.getScale().getTime().size() == 1;
        boolean isChanging = !isStatic && sourceState.getScale().getTime().size() > 2;

        /*
         * static: start -> init less than 2 temporal states: start == end, leave init
         */
        boolean haveStart = false;
        boolean haveInit = false;
        for (Object selector : phaseSelectors) {
            if ("start".equals(selector)) {
                haveStart = true;
                if (isStatic) {
                    ret.add("init");
                    haveInit = true;
                } else {
                    ret.add("start");
                }
            } else if ("init".equals(selector)) {
                if (!haveInit) {
                    ret.add("init");
                }
            } else if ("end".equals(selector)) {
                if (!(haveStart && !isChanging)) {
                    ret.add("end");
                }
            } else if ("years".equals(selector)) {
                if ((isStatic || !isChanging) && !haveStart) {
                    ret.add("start");
                } else {
                    if (sourceState.getScale().getTime().getResolution().getType() != Type.YEAR) {
                        throw new KlabIllegalStateException("summarizer: yearly reporting requested for a non-yearly context");
                    }
                    ret.add("years");
                }
            }
        }

        return ret;
    }

    private String getLabel(Object object) {
        if (OWL.INSTANCE.getNothing().equals(object)) {
            return "Unaccounted";
        }
        if (object instanceof ISemantic) {
            return Concepts.INSTANCE.getDisplayLabel(((ISemantic) object).getType());
        }
        return "";
    }

    private int getCode(Object object) {
        if (Observations.INSTANCE.isData(object)) {
            Integer ret = codes.get(object);
            if (ret == null) {
                ret = nextId;
                codes.put(object, ret);
                nextId++;
            }
            return ret;
        }
        return 1;
    }

    private ITime getTime(ITime overall, Object object) {
        if (object instanceof Number) {
            return (ITime) overall.getExtent(((Number) object).longValue());
        } else if (object instanceof String) {
            switch((String) object) {
            case "init":
                return Time.initialization(overall);
            case "start":
                return overall.earliest();
            case "end":
                return overall.latest();
            }
        } else if (object instanceof ITime) {
            return (ITime) object;
        }
        throw new KlabIllegalArgumentException("pairwise table compiler: cannot infer temporal state from " + object);
    }

}
