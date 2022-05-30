package org.integratedmodelling.klab.documentation.extensions.table.compilers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.extensions.ITableCompiler;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.observations.IKnowledgeView.Builder;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.engine.debugger.Statistics;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

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
    IUnit areaUnit = Units.INSTANCE.SQUARE_KILOMETERS;
    String emptyValue = "0.0";
    String noDataValue = "0.0";
    boolean hasNulls = false;
    Map<String, Object> templateVars;

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
    private List<List<Object>> otherSelectors = new ArrayList<>();
    private Map<Object, List<Object>> collectedSelectors = new HashMap<>();

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
            areaUnit = Unit.create(parameters.get("unit", String.class));
        }

        if (parameters.contains("rows")) {

            // can be a map col: () row: (start end) col: landcover:LandCoverType
            // if (start end) and time start == end (1 timestep), simplify to start
            // if >1 concept, grouping left to right
            // classifiers also admitted
        } else {
            throw new KlabIllegalStateException("table summarizer needs explicit rows and columns arguments");
        }

        if (parameters.contains("columns")) {

            // can be a map col: () row: (start end) col: landcover:LandCoverType
            // if (start end) and time start == end (1 timestep), simplify to start
            // if >1 concept, grouping left to right
            // classifiers also admitted
        } else {
            throw new KlabIllegalStateException("table summarizer needs explicit rows and columns arguments");
        }

        if (parameters.contains("group")) {
            // later. Could use concepts, classifiers or metadata fields
        }

        // area, time, count, value [default]
        this.reportedValue = parameters.get("report", "value");
        // true, false -> if true, force extensive measurement and must be value
        this.extensive = parameters.get("extensive", false);

        if (parameters.contains("summary")) {

            // map: row: sum col: sum || row: (mean sum) col: std
            // single value: assumed as row only
            // values can be sum, mean, std, variance, median, nodata, nodatap, count, nodatac,
            // datac
        }

        this.comparedStates = parameters.get("compare", List.class);
        this.contabilizeNulls = parameters.get("contabilize-nulls", Boolean.FALSE);
        this.emptyValue = parameters.get("empty", "0.0");
        this.noDataValue = parameters.get("no-data", "0.0");
        this.templateVars = getTemplateVars(scope);
        if (this.sourceState == null || this.comparedStates == null || this.comparedStates.size() != 2) {
            throw new KlabIllegalArgumentException("Summarizing table compiler called with insufficient or wrong arguments");
        }

    }

    private Map<String, Object> getTemplateVars(IContextualizationScope scope) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("init", "at start of " + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
                scope.getRootSubject().getScale().getTime().getResolution()));
        ret.put("start", Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
                scope.getRootSubject().getScale().getTime().getResolution()));
        ret.put("end", "at start of " + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getEnd(),
                scope.getRootSubject().getScale().getTime().getResolution()));
        return ret;
    }

    @Override
    public void compile(Builder builder) {

        builder.setEmptyCells(emptyValue, noDataValue);
        Map<Pair<Object, List<Object>>, Statistics> data = new HashMap<>();

        for (Object phase : getPhaseSelectors()) {

            for (ILocator locator : getScale(phase)) {

                /*
                 * categorize re: all the other locators
                 */
                Object value = sourceState.get(locator);
                
                if (Observations.INSTANCE.isData(value)) {
                    
                    if (matches(value, phase, otherSelectors)) {
                        Pair<Object, List<Object>> key = new Pair<>(phase, null);
                        Statistics d = data.get(key);
                        if (d == null) {
                            d = new Statistics();
                            data.put(key, d);
                        }
                        d.add(value);
                    }
                } else if (contabilizeNulls) {
                    
                }

            }
        }

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

    private boolean matches(Object value, Object phase, List<List<Object>> otherSelectors2) {
        // TODO Auto-generated method stub
        return false;
    }

    private IScale getScale(Object o) {
        // TODO Auto-generated method stub
        return null;
    }

    private Object[] getPhaseSelectors() {
        // TODO Auto-generated method stub
        return null;
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
