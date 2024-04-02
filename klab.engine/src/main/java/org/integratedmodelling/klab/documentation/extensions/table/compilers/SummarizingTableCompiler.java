package org.integratedmodelling.klab.documentation.extensions.table.compilers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimLookupTable.Argument.Dimension;
import org.integratedmodelling.kim.api.IParameters;
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
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IKnowledgeView.Attribute;
import org.integratedmodelling.klab.api.observations.IKnowledgeView.Builder;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
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
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.MapUtils;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;

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
    IObservedConcept target;
    String reportedValue;
    IUnit unit;
    String emptyValue = "0.0";
    String noDataValue = "0.0";
    Map<String, Object> templateVars;
    int nextYear = 0;
    Object rowSummary = null;
    Object colSummary = null;

    public static Set<String> phaseTokens = new HashSet<>();

    static {
        phaseTokens.add("init");
        phaseTokens.add("start");
        phaseTokens.add("end");
        phaseTokens.add("years");
    }

    private Boolean extensive;

    // these can only appear in one dimension and determine the phase to look at
    private List<DimensionConfig> phaseSelectors = new ArrayList<>();
    // these may be in multiple dimensions, although >1 isn't supported at the moment
    private List<DimensionConfig> otherSelectors = new ArrayList<>();
    private Object phaseDimension;

    private List<DimensionConfig> rowSelectors;
    private List<DimensionConfig> colSelectors;
    private String statistic;
    private IContextualizationScope scope;

    class DimensionConfig {

        public Object filter;
        public String title = "{classifier}";
        public Set<IKnowledgeView.Style> style = EnumSet.noneOf(IKnowledgeView.Style.class);

        public DimensionConfig(Object filter) {
            this.filter = filter;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getEnclosingInstance().hashCode();
            result = prime * result + Objects.hash(filter);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            DimensionConfig other = (DimensionConfig) obj;
            if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
                return false;
            return Objects.equals(filter, other.filter);
        }

        private SummarizingTableCompiler getEnclosingInstance() {
            return SummarizingTableCompiler.this;
        }

    }

    @Override
    public void initialize(IParameters<String> parameters, Map<?, ?> tableDefinition, IContextualizationScope scope) {

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
            colSelectors.add(new DimensionConfig("start"));
        } else if (rowSelectors == null && phaseSelectors == null) {
            rowSelectors = phaseSelectors = new ArrayList<>();
            rowSelectors.add(new DimensionConfig("start"));
        } else if (rowSelectors == null || colSelectors == null) {
            throw new KlabIllegalStateException(
                    "table summarizer needs explicit rows and columns if temporal phases are specified");
        }

        if (parameters.contains("group")) {
            // later. Could use concepts, classifiers or metadata fields
        }

        if (this.sourceState == null) {
            throw new KlabIllegalArgumentException(
                    "Summarizing table compiler called with insufficient arguments. Target state missing.");
        }

        // area, time, count, value [default]
        this.reportedValue = parameters.get("report", "value");
        if ("area".equals(this.reportedValue) && this.unit == null) {
            this.unit = Units.INSTANCE.SQUARE_KILOMETERS;
        }
        // true, false -> if true, force extensive measurement and must be value. Ignored if a unit
        // is passed.
        boolean targetIsExtensive = Observables.INSTANCE.isExtensive(this.target.getObservable());
        this.extensive = parameters.get("extensive", false);
        if (this.extensive && this.unit == null) {
            this.unit = sourceState.getObservable().getUnit();
            if (this.unit != null) {
                //
            }
        }
        // aggregation, sum, mean, count, nodatacount, datacount, variance, std, median, dominant
        // aggregation resolves to sum, mean or dominant according to unit and semantics
        this.statistic = parameters.get("statistic", "aggregation");
        this.contabilizeNulls = parameters.get("contabilize-nulls", Boolean.FALSE);
        this.emptyValue = parameters.get("empty", "0.0");
        this.noDataValue = parameters.get("no-data", "0.0");
        this.templateVars = getTemplateVars(scope);
        this.rowSummary = parameters.get("rowsummary");
        this.colSummary = parameters.get("colsummary");
        this.scope = scope;
        
    }

    private void processStructure(Object rowSpecs, Dimension dimension, IContextualizationScope scope) {

        boolean isPhase = false;
        for (Object selector : CollectionUtils.flatCollection(rowSpecs)) {
            if (isPhase(selector)) {
                phaseSelectors.add(new DimensionConfig(selector));
                isPhase = true;
            } else if (selector instanceof Map) {
                if (!((Map<?, ?>) selector).containsKey("filter")) {
                    throw new KlabIllegalStateException("dimension selectors must contain a filter value");
                }
                Object filter = ((Map<?, ?>) selector).get("filter");
                if (filter instanceof List) {
                    for (Object f : (List<?>) filter) {
                        processStructure(MapUtils.of("filter", f, "title", ((Map<?, ?>) selector).get("filter")), dimension,
                                scope);
                    }
                    return;
                } else {
                    filter = isPhase(filter) ? filter : Observables.INSTANCE.parseObservable(filter, scope.getMonitor());
                    DimensionConfig sel = new DimensionConfig(filter);
                    if (((Map<?, ?>) selector).containsKey("title")) {
                        sel.title = ((Map<?, ?>) selector).get("title").toString();
                    }
                    if (isPhase(sel.filter)) {
                        phaseSelectors.add(sel);
                        isPhase = true;
                    } else {
                        otherSelectors.add(sel);
                    }
                }
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
                        otherSelectors.add(new DimensionConfig(new Classifier((IKimClassifier) selector)));
                    } else {
                        throw new KlabIllegalStateException("table summarizer: can't use " + selector + " as selector");
                    }
                } else {
                    otherSelectors.add(new DimensionConfig(observable));
                }
            }
        }

        if (isPhase) {
            phaseDimension = dimension;
        }

        if (dimension == Dimension.ROW) {
            rowSelectors = isPhase ? phaseSelectors : otherSelectors;
        } else {
            colSelectors = isPhase ? phaseSelectors : otherSelectors;
        }

    }

    private boolean isPhase(Object selector) {
        if (selector instanceof DimensionConfig) {
            selector = ((DimensionConfig) selector).filter;
        }
        return selector instanceof String && phaseTokens.contains((String) selector);
    }

    private Map<String, Object> getTemplateVars(IContextualizationScope scope) {
        Map<String, Object> ret = new HashMap<>();
        Resolution resolution = scope.getRootSubject().getScale().getTime().getResolution();
        ret.put("init", "Start of " + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(), resolution));
        ret.put("start", (resolution.getType() == ITime.Resolution.Type.YEAR ? "Year " : "")
                + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(), resolution));
        ret.put("end",
                (resolution.getType() == ITime.Resolution.Type.YEAR ? "" : "Start of ")
                        + (resolution.getType() == ITime.Resolution.Type.YEAR
                                ? ("Year " + (scope.getRootSubject().getScale().getTime().getEnd().getYear() - 1))
                                : Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getEnd(), resolution)));
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
                Object value = processValue(sourceState.get(locator));
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
        Map<Object, String> columnIds = new LinkedHashMap<>();
        Map<Object, String> rowIds = new LinkedHashMap<>();

        List<List<Object>> flatSelectors = sort(Sets.cartesianProduct(dimensionClassifiers));
        for (Object rowsel : (phaseDimension == Dimension.ROW ? phaseClassifiers : flatSelectors)) {
            for (Object colsel : (phaseDimension == Dimension.ROW ? flatSelectors : phaseClassifiers)) {
                String columnId = columnIds.get(colsel);
                if (columnId == null) {
                    // TODO multiple classifiers
                    columnId = builder.getColumn(Attribute.HEADER, getLabel(colsel));
                    columnIds.put(colsel, columnId);
                }
                String rowId = rowIds.get(rowsel);
                if (rowId == null) {
                    rowId = builder.getRow(Attribute.HEADER, getLabel(rowsel));
                    rowIds.put(rowsel, rowId);
                }

                Pair<String, List<Object>> key = new Pair<>(phaseDimension == Dimension.ROW ? (String) rowsel : (String) colsel,
                        phaseDimension == Dimension.ROW ? (List<Object>) colsel : (List<Object>) rowsel);

                Statistics dat = data.get(key);
                if (dat != null) {
                    builder.setCell(rowId, columnId, getCellValue(dat));
                }
            }
        }

        if (colSummary != null) {
            for (Object specifier : CollectionUtils.flatCollection(colSummary)) {
                String directive = null;
                String label = null;
                if (specifier instanceof String) {
                    directive = (String) specifier;
                    label = StringUtil.capitalize(directive);
                } else if (specifier instanceof IParameters) {
                    if (!((Map<?, ?>) specifier).containsKey("filter")) {
                        throw new KlabIllegalStateException("summarizer: summary needs a field named 'filter'");
                    }
                    directive = ((Map<?, ?>) specifier).get("filter").toString();
                    label = ((IParameters<String>) specifier).get("title", "{classifier}").toString();
                }
                addSummary(Dimension.COLUMN, builder, directive, label);
            }
        }

        if (rowSummary != null) {
            for (Object specifier : CollectionUtils.flatCollection(rowSummary)) {
                String directive = null;
                String label = null;
                if (specifier instanceof String) {
                    directive = (String) specifier;
                    label = StringUtil.capitalize(directive);
                } else if (specifier instanceof IParameters) {
                    if (!((Map<?, ?>) specifier).containsKey("filter")) {
                        throw new KlabIllegalStateException("summarizer: summary needs a field named 'filter'");
                    }
                    directive = ((Map<?, ?>) specifier).get("filter").toString();
                    label = ((IParameters<String>) specifier).get("title", "{classifier}").toString();
                }
                addSummary(Dimension.ROW, builder, directive, label);
            }
        }
    }

    private Object processValue(Object object) {
        if (Observations.INSTANCE.isData(object)) {
            if (this.unit != null && object instanceof Number) {
            }
        }
        return object;
    }

    private void addSummary(Dimension dimension, Builder builder, String directive, String label) {

        List<String> rowIds = builder.getRowIds();
        List<String> colIds = builder.getColumnIds();

        if (directive == null || label == null || (dimension == Dimension.ROW ? rowIds.size() <= 1 : colIds.size() <= 1)) {
            return;
        }

        List<Number> results = new ArrayList<>();

        String dimId = null;
        for (String otherId : (dimension == Dimension.ROW ? colIds : rowIds)) {
            switch(directive) {
            case "total":
                /*
                 * compute the other dimensions' total
                 */
                double total = 0;
                for (String myId : (dimension == Dimension.ROW ? rowIds : colIds)) {
                    Object value = dimension == Dimension.ROW
                            ? builder.getCellValue(myId, otherId)
                            : builder.getCellValue(otherId, myId);
                    if (Observations.INSTANCE.isData(value) && value instanceof Number) {
                        total += ((Number) value).doubleValue();
                    }
                }
                results.add(total);
                break;
            case "mean":
                /*
                 * compute the other dimensions' total
                 */
                double mean = 0;
                int n = 0;
                for (String myId : (dimension == Dimension.ROW ? rowIds : colIds)) {
                    Object value = dimension == Dimension.ROW
                            ? builder.getCellValue(myId, otherId)
                            : builder.getCellValue(otherId, myId);
                    if (Observations.INSTANCE.isData(value) && value instanceof Number) {
                        mean += ((Number) value).doubleValue();
                        n ++;
                    }
                }
                if (n > 0) {
                	mean /= (double)n;
                }
                results.add(mean);
                break;
            case "change":
                label = "Net change";
                Object first = dimension == Dimension.ROW
                        ? builder.getCellValue(rowIds.get(0), otherId)
                        : builder.getCellValue(otherId, colIds.get(0));
                Object last = dimension == Dimension.ROW
                        ? builder.getCellValue(rowIds.get(rowIds.size() - 1), otherId)
                        : builder.getCellValue(otherId, colIds.get(colIds.size() - 1));

                results.add(Observations.INSTANCE.isData(first) && Observations.INSTANCE.isData(last) && first instanceof Number
                        && last instanceof Number ? ((Number) last).doubleValue() - ((Number) first).doubleValue() : 0.0);

                break;
            default:
                throw new KlabIllegalStateException("summarizer: unrecognized statistic " + directive);
            }
        }

        if (dimId == null) {
            dimId = dimension == Dimension.ROW ? builder.getRow(label) : builder.getColumn(label);
            int i = 0;
            for (String otherId : (dimension == Dimension.ROW ? colIds : rowIds)) {
                if (dimension == Dimension.ROW) {
                    builder.setCell(dimId, otherId, results.get(i++));
                } else {
                    builder.setCell(otherId, dimId, results.get(i++));
                }
            }
        }
    }

    // aggregation, sum, mean, count, nodatacount, datacount, variance, std, median, dominant
    // aggregation resolves to sum, mean or dominant according to unit and semantics
    private Object getCellValue(Statistics dat) {
        switch(this.statistic) {
        case "aggregation":
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

    private List<List<Object>> sort(Set<List<Object>> cartesianProduct) {

        List<List<Object>> ret = new ArrayList<>();
        boolean single = true;
        for (List<Object> obj : cartesianProduct) {
            ret.add(obj);
            if (obj.size() != 1) {
                single = false;
            }
        }

        if (single) {
            Collections.sort(ret, new Comparator<List<Object>>(){

                @Override
                public int compare(List<Object> o1, List<Object> o2) {
                    String s1 = getLabel(o1.get(0));
                    String s2 = getLabel(o2.get(0));
                    return s1.compareTo(s2);
                }
            });
        }

        return ret;
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
        for (DimensionConfig sel : otherSelectors) {
            Object o = sel.filter;
            Object match = null;
            if (o instanceof IObservable) {
                if (Observations.INSTANCE.isNodata(value)) {
                    o = Observable.promote(OWL.INSTANCE.getNothing());
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
        default:
            if (NumberUtils.encodesInteger(phase)) {
                for (int i = 1; i < sourceState.getScale().getTime().size(); i++) {
                    ITime time = sourceState.getScale().getTime().getExtent(i);
                    if (Integer.parseInt(phase) == time.getStart().getYear()) {
                        return sourceState.getScale().at(time);
                    }
                }
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
        for (DimensionConfig selector : phaseSelectors) {
            if ("start".equals(selector.filter)) {
                haveStart = true;
                if (isStatic) {
                    ret.add("init");
                    haveInit = true;
                } else {
                    ret.add("start");
                }
            } else if ("init".equals(selector.filter)) {
                if (!haveInit) {
                    ret.add("init");
                }
            } else if ("end".equals(selector.filter)) {
                if (!(haveStart && !isChanging)) {
                    ret.add("end");
                }
            } else if ("years".equals(selector.filter)) {
                if ((isStatic || !isChanging) && !haveStart) {
                    ret.add("start");
                } else {
                    if (sourceState.getScale().getTime().getResolution().getType() != Type.YEAR) {
                        throw new KlabIllegalStateException("summarizer: yearly reporting requested for a non-yearly context");
                    }
                    for (int i = 1; i < sourceState.getScale().getTime().size(); i++) {
                        ret.add(sourceState.getScale().getTime().getExtent(i).getStart().getYear() + "");
                    }
                }
            }
        }

        return ret;
    }

    private String getLabel(Object object) {

        /*
         * TODO use template from definitions
         */

        if (object instanceof Collection) {
        	String ret = "";
        	for (Object o : ((Collection<?>)object)) {     		
        		ret += (ret.isEmpty() ? "" : ", ") + getLabel(o);
        	}
        	return ret;
        }

        if (OWL.INSTANCE.getNothing().equals(object)) {
            return "Unaccounted";
        }
        if (object instanceof ISemantic) {
            return Concepts.INSTANCE.getDisplayLabel(((ISemantic) object).getType());
        } else if (phaseTokens.contains(object)) {
            return templateVars.get(object.toString()).toString();
        } else if (NumberUtils.encodesInteger(object.toString())) {
            return "Year " + object;
        }
        return "";
    }
}
