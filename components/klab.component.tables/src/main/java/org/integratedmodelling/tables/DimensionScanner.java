package org.integratedmodelling.tables;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.api.data.general.ITable.Filter.Type;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.tables.CodeList.Mapping;

/**
 * This matches a dimension in the table (according to specified encodings) to the corresponding
 * index on a table dimension or to the geometric extents corresponding to it. These are computed by
 * TableInterpreter and may be cached.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class DimensionScanner<T> {

    public enum Dimension {
        COLUMN, ROW
    };

    // if not null, this matches a row/column header (according to dimension) to
    // extract a string to match to the extent.
    private Pattern dimensionHeaderMatch;

    // scan this dimension
    private Dimension dimension = Dimension.ROW;
    // we encode this extent
    private Class<?> extent;

    private String columnName;
    private String rowName;

    // cache for computed extents and correspondent dimension indices
    private List<Integer> indices = null;
    private List<T> extents = null;
    private ITable<?> table;
    private Filter filter = null;

    /*
     * extents matched through a table dimension will come from a column and be matched backwards to
     * the column, after going through the mappings.
     * 
     * TODO use a specialized CodeMapping instead of hard-coding in here.
     */
    private IResource auxiliaryResource = null;

    /*
     * mappings are listed in the order they come in the specifications, and will be matched
     * backwards from the resource to obtain the values to use for filtering.
     */
    private List<CodeList> mappings = new ArrayList<>();

    /*
     * for temporal indexing to specific columns or rows. For now this is only used when one column
     * refers to one year.
     */
    NavigableMap<Long, Integer> temporalDimensions = null;
    IDataArtifact spatialContextualizer = null;

    // result of encoding of the geometry, stored by the TableInterpreter.
    public String encodedDimension = null;

    private String auxiliaryResourceUrn;

    private DimensionScanner(DimensionScanner<T> other) {
        this.temporalDimensions = other.temporalDimensions;
        this.dimension = other.dimension;
        this.dimensionHeaderMatch = other.dimensionHeaderMatch;
        this.mappings.addAll(other.mappings);
        this.auxiliaryResource = other.auxiliaryResource;
        this.spatialContextualizer = other.spatialContextualizer;
        this.table = other.table;
        this.extent = other.extent;
        this.extents = other.extents;
        this.indices = other.indices;
        this.rowName = other.rowName;
        this.columnName = other.columnName;
        this.auxiliaryResourceUrn = other.auxiliaryResourceUrn;
    }

    public DimensionScanner(IResource resource, String[] definition, Class<T> cls) {

        this.extent = cls;
        String[] ss = definition[0].split(":");

        switch(ss[0]) {
        case "COLUMN_HEADER":
            this.dimension = Dimension.COLUMN;
            this.dimensionHeaderMatch = Pattern.compile(ss[1]);
            break;
        case "ROW_HEADER":
            this.dimension = Dimension.COLUMN;
            this.dimensionHeaderMatch = Pattern.compile(ss[1]);
            break;
        case "ROW":
            this.dimension = Dimension.ROW;
            this.rowName = ss[1];
            break;
        case "COLUMN":
            this.dimension = Dimension.COLUMN;
            this.columnName = ss[1];
            break;
        default:
            // column
            this.dimension = Dimension.COLUMN;
            this.columnName = ss[0];
        }

        for (int i = 1; i < definition.length; i++) {
            if (Urns.INSTANCE.isUrn(definition[i])) {
                if (this.auxiliaryResource != null) {
                    throw new KlabValidationException(
                            "cannot have more than one auxiliary resource in a table encoding");
                }
                this.auxiliaryResource = Resources.INSTANCE.resolveResource(definition[i]);
                this.auxiliaryResourceUrn = definition[i];
                if (this.auxiliaryResource == null
                        || !Resources.INSTANCE.isResourceOnline(this.auxiliaryResource)) {
                    throw new KlabValidationException(
                            "auxiliary resource in table encoding is unknown or offline");
                }
            } else {
                /*
                 * must be a code mapping or a recognized identifier
                 */
                if ("YEAR".equals(definition[i]) && ITime.class.isAssignableFrom(cls)) {
                    mappings.add(new CodeList(Mapping.YEAR, null));
                } else if ("PERIOD".equals(definition[i]) && ITime.class.isAssignableFrom(cls)) {
                    mappings.add(new CodeList(Mapping.PERIOD, ss[0]));
                    break;

                } else if (Time.class.isAssignableFrom(cls)
                        && org.integratedmodelling.klab.Time.INSTANCE.isTimePattern(definition[i])) {
                    mappings.add(new CodeList(Mapping.DATE_PATTERN, definition[i]));
                } else {

                    /*
                     * if none of these, must be a codelist
                     */
                    File mapfile = new File(
                            ((Resource) resource).getPath() + File.separator + "code_" + definition[i]
                                    + ".properties");
                    if (mapfile == null || !mapfile.exists()) {
                        throw new KlabValidationException(
                                "code mapping " + definition[i] + " cannot be matched to a definition");

                    }
                    mappings.add(new CodeList(definition[i], mapfile));
                }
            }
        }
    }

    public Iterable<T> scanExtents(ITable<?> table) {
        if (this.extents == null || this.table != table) {
            computeMatches(table);
        }
        return this.extents;
    }

    public Iterable<Integer> scanIndices(ITable<?> table) {
        if (this.indices == null || this.table != table) {
            computeMatches(table);
        }
        return this.indices;
    }

    /*
     * table should come pre-filtered according to any other parameters, we add the column header
     * filter to produce extent locators
     */
    private void computeMatches(ITable<?> table) {

        this.table = table;
        this.extents = new ArrayList<T>();
        this.indices = new ArrayList<>();

        if (dimensionHeaderMatch != null) {

            int n = 0;
            for (Object o : table.resetFilters().filter(Filter.Type.COLUMN_HEADER, dimensionHeaderMatch)
                    .collectIndices(this.indices)
                    .asList(dimension == Dimension.ROW ? -1 : 0, dimension == Dimension.ROW ? 0 : -1)) {
                T extent = extractExtent(o);
                if (extent != null) {
                    this.extents.add(extent);
                }
                n++;
            }

        } else if (this.columnName != null) {

            Object[] cols = new Object[]{this.columnName};
            if (this.columnName.contains(",")) {
                cols = this.columnName.split(",");
            }

            Set<Object> items = new LinkedHashSet<>();
            for (Object o : table.resetFilters().asList(cols, ITable.SearchOptions.UNIQUE)) {
                if (Observations.INSTANCE.isData(o)) {
                    items.add(o);
                }
            }
            for (Object o : items) {
                T extent = extractExtent(o);
                if (extent != null) {
                    this.extents.add(extent);
                }
            }
        } else if (this.rowName != null) {

        }

    }

    @SuppressWarnings("unchecked")
    private T extractExtent(Object o) {
        if (o != null) {
            if (!this.mappings.isEmpty()) {
                for (CodeList mapping : this.mappings) {
                    o = mapping.value(o);
                }
                return (T) o;
            } else if (NumberUtils.encodesInteger(o.toString())
                    && ITime.class.isAssignableFrom(this.extent)) {
                int year = Integer.parseInt(o.toString());
                return (T) Time.create(year);
            }
        }
        return null;
    }

    public DimensionScanner<T> contextualize(ITable<?> table, T extent, IContextualizationScope scope) {

        DimensionScanner<T> ret = this;

        if (ITime.class.isAssignableFrom(this.extent) && extent instanceof ITime) {

            if (temporalDimensions == null) {
                scanExtents(table);
                this.temporalDimensions = new TreeMap<>();
                for (int i = 0; i < this.extents.size(); i++) {
                    ITime text = (ITime) this.extents.get(i);
                    /*
                     * if the time extent is encoded in rows through a mapping, just insert the
                     * index of the extent in the array, and leave indices == null to denote that
                     * the mapping is not done to specific indices.
                     */
                    int index = this.indices.isEmpty() ? i : this.indices.get(i);
                    this.temporalDimensions.put(text.getStart().getMilliseconds(), index);
                }
            }

        } else if (ISpace.class.isAssignableFrom(this.extent) && extent instanceof ISpace) {

            /*
             * contextualize the resource in space and establish the mappings: column ->
             * classification -> resource means resource -> inverse classification -> column filter
             * (to add to any other in the table).
             */

            ret = this.copy();

            if (auxiliaryResource != null && spatialContextualizer == null) {

                VisitingDataBuilder builder = new VisitingDataBuilder().keepStates(scope.getScale())
                        .startState("space", null, scope);
                IKlabData data = Resources.INSTANCE.getResourceData(auxiliaryResourceUrn, builder,
                        scope.getScale(),
                        scope.getMonitor());
                ret.spatialContextualizer = (IDataArtifact) data.getArtifact();
            } else if (columnName != null) {

                ICodelist codelist = table.getCodelist(columnName);
                if (codelist != null) {
                    /*
                     * space is mapped to a codelist. 
                     */
                }

            }
        }

        return ret;
    }

    private DimensionScanner<T> copy() {
        return new DimensionScanner<>(this);
    }

    /**
     * Return the filter that will select dimensions corresponding to the passed locator. Null means
     * all dimensions OK.
     * 
     * @param table
     * @param locator
     * @return
     */
    public Filter locate(ITable<?> table, ILocator locator) {

        if (this.spatialContextualizer != null) {
            Object value = this.spatialContextualizer.get(locator);
            if (value == null) {
                // return no-values filter
                return ((AbstractTable<?>) table).stop();
            } else if (this.columnName != null) {
                for (int i = mappings.size() - 1; i >= 0; i--) {
                    value = mappings.get(i).key(value);
                }
                return ((AbstractTable<?>) table).newFilter(Filter.Type.COLUMN_MATCH,
                        new Object[]{this.columnName, value});
            }
        } else if (ITime.class.isAssignableFrom(this.extent)) {

            ITime time = locator instanceof ITime
                    ? (ITime) locator
                    : (locator instanceof IScale ? ((IScale) locator).getTime() : null);

            if (time != null && this.temporalDimensions != null) {

                if (time.getTimeType() == ITime.Type.INITIALIZATION && time.size() > 1) {
                    time = Time.getPreviousExtent(time);
                }

                /*
                 * find the column(s) or row(s) covering the passed time and add a filter to the
                 * table for that.
                 */
                Entry<Long, Integer> index = this.temporalDimensions
                        .floorEntry(time.getStart().getMilliseconds());

                if (index == null) {

                    // no way
                    return ((AbstractTable<?>) table).stop();

                } else {

                    if (this.indices.isEmpty() && !mappings.isEmpty()) {

                        /*
                         * Values are mapped to columns through a key. Set a column filter for the
                         * matching value in the code mapping.
                         */
                        List<Object> filtered = new ArrayList<>();
                        filtered.add(this.extents.get(index.getValue()));
                        for (Long other : this.temporalDimensions
                                .subMap(index.getKey(), false, time.getEnd().getMilliseconds(), false)
                                .keySet()) {
                            filtered.add(this.extents.get(this.temporalDimensions.get(other)));
                        }

                        /**
                         * remap the extents to the original value(s) through the dimension scanner
                         */
                        for (int n = 0; n < filtered.size(); n++) {
                            Object mappedExtent = filtered.get(n);
                            for (int i = mappings.size() - 1; i >= 0; i--) {
                                mappedExtent = mappings.get(i).key(mappedExtent);
                            }
                            filtered.set(n, mappedExtent);
                        }

                        return ((AbstractTable<?>) table).newFilter(
                                this.dimension == Dimension.COLUMN
                                        ? Filter.Type.COLUMN_MATCH
                                        : Filter.Type.ROW_MATCH,
                                new Object[]{
                                        (this.dimension == Dimension.COLUMN ? this.columnName : this.rowName),
                                        filtered});

                    } else {

                        /*
                         * all row or columns with compatible values get in the filter.
                         */
                        List<Integer> indices = new ArrayList<>();
                        indices.add(index.getValue());
                        for (Long other : this.temporalDimensions
                                .subMap(index.getKey(), false, time.getEnd().getMilliseconds(), false)
                                .keySet()) {
                            indices.add(this.temporalDimensions.get(other));
                        }

                        return ((AbstractTable<?>) table)
                                .newFilter(this.dimension == Dimension.COLUMN
                                        ? Filter.Type.INCLUDE_COLUMNS
                                        : Filter.Type.INCLUDE_ROWS, new Object[]{indices});
                    }
                }
            }
        }

        return null;
    }

    /**
     * Return a table filtered to only return rows and columns that match this dimension.
     * 
     * @param table2
     * @return
     */
    public ITable<?> subset(ITable<?> table, IScale scale) {

        if (ISpace.class.isAssignableFrom(this.extent)) {
            if (this.spatialContextualizer != null && this.filter == null) {
                /*
                 * build a filter for the represented classes in the column
                 */
                Set<Object> values = new HashSet<>();
                Set<Object> origin = new HashSet<>();
                for (ILocator locator : scale) {
                    Object value = this.spatialContextualizer.get(locator);
                    if (value != null) {
                        origin.add(value);
                    }
                }

                if (origin.isEmpty()) {
                    this.filter = ((AbstractTable<?>) table).stop();
                } else {
                    for (Object o : origin) {
                        for (int i = mappings.size() - 1; i >= 0; i--) {
                            o = mappings.get(i).key(o);
                        }
                        if (o != null) {
                            values.add(o);
                        }
                    }
                    if (this.dimension == Dimension.COLUMN) {
                        this.filter = ((AbstractTable<?>) table).newFilter(Type.COLUMN_MATCH,
                                new Object[]{this.columnName, values});
                    } else if (this.dimension == Dimension.ROW) {
                        this.filter = ((AbstractTable<?>) table).newFilter(Type.ROW_MATCH,
                                new Object[]{this.rowName, values});
                    }
                }
            }
        } else if (ITime.class.isAssignableFrom(this.extent)) {
        }

        if (this.filter != null) {
            table = table.filter(this.filter);
        }

        return table;
    }

}
