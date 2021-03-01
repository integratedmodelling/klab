package org.integratedmodelling.tables;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.AttributeReference;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.tables.adapter.TableAdapter;

public abstract class AbstractTable<T> implements ITable<T> {

    class FilterDescriptor implements Filter {

        Filter.Type filter;
        List<Object> locators = new ArrayList<>();
        Object matched = null;
        // the dimension this filter selects upon
        int dimension = -1; // special filter
        String signature;
        boolean cached = false;

        public FilterDescriptor(Filter.Type filter, Object[] locators) {
            this.filter = filter;
            if (locators != null) {
                for (Object o : locators) {
                    this.locators.add(o);
                }
            }
            switch(filter) {
            case COLUMN_EXPRESSION:
                this.dimension = 1;
                this.cached = true;
                break;
            case COLUMN_HEADER:
                this.dimension = 0;
                break;
            case COLUMN_MATCH:
                this.dimension = 0;
                this.cached = true;
                break;
            case INCLUDE_COLUMNS:
                this.dimension = 1;
                break;
            case INCLUDE_ROWS:
                this.dimension = 0;
                break;
            case NO_RESULTS:
                break;
            case ROW_HEADER:
                this.dimension = 0;
                this.cached = true;
                break;
            case ROW_MATCH:
                this.cached = true;
                this.dimension = 0;
                break;
            }

            validate();
        }

        void validate() {
            switch(filter) {
            case COLUMN_EXPRESSION:
                break;
            case COLUMN_HEADER:
                break;
            case COLUMN_MATCH:
                /*
                 * preprocess the attributes
                 */
                List<Object> values = new ArrayList<>();
                for (int i = 0; i < this.locators.size(); i++) {

                    String key = this.locators.get(i).toString();
                    Attribute a = getColumnDescriptor(key);
                    Object v = i < this.locators.size() - 1 ? this.locators.get(++i) : null;
                    if (a != null) {
                        v = convertValue(v, a);
                    }

                    values.add(key);
                    values.add(v);
                }
                this.locators.clear();
                this.locators.addAll(values);

                break;

            case INCLUDE_COLUMNS:
                break;
            case INCLUDE_ROWS:
                break;
            case NO_RESULTS:
                break;
            case ROW_HEADER:
                break;
            case ROW_MATCH:
                break;
            default:
                break;

            }
        }

        public boolean isCached() {
            return cached;
        }

        boolean matches(AbstractTable<?> table, Object o, int... location) {

            switch(filter) {
            case COLUMN_HEADER:
                if (locators != null && locators.size() > 0 && locators.get(0) instanceof Pattern) {
                    Attribute attr = table.getColumnDescriptor(location[1]);
                    if (attr != null) {
                        String cname = attr.getName();
                        Matcher matcher = ((Pattern) locators.get(0)).matcher(cname);
                        if (matcher.matches()) {
                            if (matcher.groupCount() > 0) {
                                this.matched = matcher.group(1);
                            }
                            System.out.println(
                                    "MATCHED " + cname + " with value " + this.matched + " and INDEX = " + attr.getIndex());
                            return true;
                        }
                    }
                }
                break;
            case INCLUDE_COLUMNS:
                break;
            case INCLUDE_ROWS:
                break;
            case ROW_HEADER:
                break;
            case NO_RESULTS:
                break;
            case COLUMN_EXPRESSION:
                break;
            case COLUMN_MATCH:
                for (int i = 0; i < locators.size(); i++) {
                    Object key = locators.get(i);
                    Object val = locators.get(++i);
                    if (!(val instanceof Collection)) {
                        val = Collections.singleton(val);
                    }
                    for (Object value : (Collection<?>) val) {
                        // TODO match. These are in OR so the first match exits.
                        if (true) {
                            return true;
                        }
                    }
                    return false;
                }
                break;
            case ROW_MATCH:
                break;
            }

            return false;
        }

        Object filter(Object o) {
            if (filter == Filter.Type.NO_RESULTS) {
                return null;
            }
            if (matched != null) {
                o = matched;
                this.matched = null;
            }
            return o;
        }

        @Override
        public Type getType() {
            return filter;
        }

        @Override
        public List<Object> getArguments() {
            return locators;
        }

        @Override
        public String toString() {
            return "FilterDescriptor [filter=" + filter + ", locators=" + locators + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((filter == null) ? 0 : filter.hashCode());
            result = prime * result + ((locators == null) ? 0 : locators.hashCode());
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            FilterDescriptor other = (FilterDescriptor) obj;
            if (filter != other.filter)
                return false;
            if (locators == null) {
                if (other.locators != null)
                    return false;
            } else if (!locators.equals(other.locators))
                return false;
            return true;
        }

        @Override
        public String getSignature() {
            if (this.signature == null) {
                this.signature = "" + dimension + " F:" + filter + " L:" + locators;
            }
            return this.signature;
        }

        @Override
        public int getDimension() {
            return this.dimension;
        }

        @Override
        public Filter contextualize(IContextualizationScope scope) {
            List<Object> args = new ArrayList<>();
            boolean changed = false;
            for (Object o : this.locators) {
                if (o instanceof IConcept && ((IConcept) o).is(IKimConcept.Type.PREDICATE)) {
                    o = scope.localizePredicate((IConcept) o);
                    changed = true;
                }
                args.add(o);
            }
            return changed ? new FilterDescriptor(this.filter, args.toArray()) : this;
        }

    }

    protected IResource resource;
    Map<String, Attribute> attributes_ = null;
    Map<Integer, Attribute> attributesByIndex_ = null;
    List<Filter> filters = new ArrayList<>();
    Class<? extends T> valueClass;
    protected List<Integer> lastScannedIndices;
    protected boolean empty = false;
    Map<String, List<CodeList>> mappings = Collections.synchronizedMap(new HashMap<>());
    private SQLTableCache cache_ = null;
    IMonitor monitor;
    private Map<String, CodeList> mappingCatalog = Collections.synchronizedMap(new HashMap<>());

    public AbstractTable(IResource resource, Class<? extends T> cls, IMonitor monitor) {
        this.resource = resource;
        this.valueClass = cls;
        buildAttributeIndex();
        this.cache_ = new SQLTableCache(resource);
        this.monitor = monitor;
    }

    protected AbstractTable(AbstractTable<T> table) {
        this.resource = table.resource;
        this.attributes_ = table.attributes_;
        this.attributesByIndex_ = table.attributesByIndex_;
        this.filters.addAll(table.filters);
        this.valueClass = table.valueClass;
        this.empty = table.empty;
        this.cache_ = table.cache_;
        this.monitor = table.monitor;
        this.mappings.putAll(table.mappings);
    }

    public FilterDescriptor stop() {
        return new FilterDescriptor(Filter.Type.NO_RESULTS, null);
    }

    public SQLTableCache getCache() {
        /*
         * TODO! FIXME! add properties with a timestamp for the source to ensure we rebuild the
         * cache if the source data have changed.
         */
        if (cache_.isEmpty()) {
            monitor.info("building table cache for " + resource.getUrn());
            cache_.reset(this);
        }
        return cache_;
    }

    private void validateFilters() {
        // TODO
    }

    @Override
    public List<Filter> getFilters() {
        return filters;
    }

    protected abstract AbstractTable<T> copy();

    /**
     * Builds the attribute set and adds the column index.
     * 
     * @return
     */
    @Override
    public Attribute getColumnDescriptor(String columnName) {
        if (attributes_ == null) {
            buildAttributeIndex();
        }
        return attributes_.get(columnName);
    }

    @Override
    public Attribute getColumnDescriptor(int index) {
        if (attributesByIndex_ == null) {
            buildAttributeIndex();
        }
        return attributesByIndex_.get(index);
    }

    public Collection<Attribute> getColumnDescriptors() {
        if (attributesByIndex_ == null) {
            buildAttributeIndex();
        }
        return attributesByIndex_.values();
    }

    private void buildAttributeIndex() {
        attributes_ = new HashMap<>();
        attributesByIndex_ = new HashMap<>();
        for (Attribute a : resource.getAttributes()) {
            this.attributes_.put(a.getName(), a);
            int index = Integer.parseInt(resource.getParameters().get("column." + a.getName() + ".index").toString());
            ((AttributeReference) a).setIndex(index);
            attributesByIndex_.put(index, a);
            Object mapping = resource.getParameters().get("column." + a.getName() + ".mapping");
            // TODO allow for a chain of mappings separated by ->
            if (mapping != null && !mapping.toString().trim().isEmpty()) {

                String[] mapchain = mapping.toString().trim().split(Pattern.quote("->"));
                List<CodeList> chain = new ArrayList<>();
                for (String mapid : mapchain) {
                    CodeList map = getMapping(mapid);
                    if (map == null) {
                        throw new KlabValidationException("no mapping named " + mapid + " is defined for table");
                    }

                    chain.add(map);
                    if (map.getType() != null) {
                        ((AttributeReference) a).setType(map.getType());
                    }
                }
                mappings.put(a.getName(), chain);
            }
        }

    }

    @Override
    public int[] getDimensions() {
        return new int[]{Integer.parseInt(resource.getParameters().get("rows.data").toString()),
                Integer.parseInt(resource.getParameters().get("columns.data").toString())};
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> asList(Object... locators) {

        int row = -1;
        int col = -1;

        if (this.lastScannedIndices != null) {
            this.lastScannedIndices.clear();
        }

        if (locators != null) {
            if (locators.length == 2) {
                row = getIndex(locators[0], 0);
                col = getIndex(locators[1], 1);
            } else if (locators.length == 1) {
                if (TableAdapter.COLUMN_HEADER_CATEGORIZABLE.equals(locators[0].toString())) {
                    List<T> ret = new ArrayList<>();
                    for (int n = 0; n < getColumnDescriptors().size(); n++) {
                        ret.add((T) getColumnDescriptor(n).getName());
                    }
                    return ret;
                }
                col = getIndex(locators[0], 1);
            }
        }

        if (row < 0 && col < 0) {
            throw new KlabResourceAccessException(
                    "CSV: need one or two indices or header names to turn a table resource into a list");
        }

        List<T> ret = new ArrayList<>();

        if (empty) {
            return ret;
        }

        if (col < 0) {
            // take all rows items for a given column
            // FIXME use cache!
            int i = 0;
            for (Object o : getRowItems(row)) {
                boolean ok = true;
                for (Filter f : filters) {
                    if (ok && ((FilterDescriptor) f).matches(this, o, row, i)) {
                        o = ((FilterDescriptor) f).filter(o);
                    } else {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    ret.add(Utils.asType(o, valueClass));
                    if (this.lastScannedIndices != null) {
                        this.lastScannedIndices.add(i);
                    }
                }
                i++;
            }
        } else if (row < 0) {
            // take all columns for a given row
            ret.addAll(getColumnItems(col));
        } else {
            // return a list with a single element for row,col, no filtering
            ret.add(getItem(row, col));
        }

        return ret;
    }

    /**
     * Get an aggregated value that reflects the current filters, aggregating as necessary. For now
     * this only works if columns and rows are positively chosen by filters - no filter means no
     * result, unless the table has exactly one element.
     * 
     * @param aggregator may be null, but an exception will be thrown if aggregation is needed.
     * @param scope the scope of contextualization, used to localize abstract concept matches. Can
     *        be null.
     * @return
     */
    public Object get(Aggregator aggregator, IContextualizationScope scope) {

        /*
         * TODO if all dimensions are 1, just return the only element we have.
         */

        Set<Integer> filteredColumns = new LinkedHashSet<>();
        Set<Integer> filteredRows = new LinkedHashSet<>();

        Map<String, Object> attributeFilters = new HashMap<>();
        IExpression filterExpression = null;

        for (Filter filter : this.filters) {

            switch(filter.getType()) {
            case COLUMN_EXPRESSION:
                // TODO store in filter to avoid recompiling each time
                filterExpression = Extensions.INSTANCE.compileExpression(filter.getArguments().get(0).toString(),
                        Extensions.DEFAULT_EXPRESSION_LANGUAGE, CompilerOption.ForcedScalar, CompilerOption.IgnoreContext);
                break;
            case COLUMN_MATCH:
                for (int i = 0; i < filter.getArguments().size(); i++) {
                    attributeFilters.put(filter.getArguments().get(i).toString(), filter.getArguments().get(++i));
                }
                break;
            case INCLUDE_COLUMNS:
                addIndices(filteredColumns, filter.getArguments());
                break;
            case INCLUDE_ROWS:
                addIndices(filteredRows, filter.getArguments());
                break;
            case COLUMN_HEADER:
            case ROW_HEADER:
                break;
            case NO_RESULTS:
                return null;
            case ROW_MATCH:
                break;
            default:
                break;
            }
        }

        if (!attributeFilters.isEmpty() || filterExpression != null) {
            for (int i = 0; i < getDimensions()[0]; i++) {
                List<?> row = getRowItems(i);
                if (matches(row, attributeFilters, filterExpression, scope)) {
                    filteredRows.add(i);
                }
            }
        }

        /*
         * TODO this implies that a filter must exist - without filters, no items will be
         * aggregated.
         */
        if (filteredColumns.size() > 0 && filteredRows.size() > 0) {
            List<Object> ret = new ArrayList<>();
            for (int row : filteredRows) {
                for (int col : filteredColumns) {
                    Object item = this.getItem(row, col);
                    if (item != null) {
                        ret.add(item);
                    }
                }
            }

            if (aggregator == null && ret.size() > 1) {
                throw new KlabIllegalStateException("filtered table produces multiple values but no aggregator is specified");
            }

            System.out.println("GOT " + ret);

            return ret.isEmpty() ? null : (ret.size() == 1 ? ret.get(0) : (aggregator.aggregate(ret)));
        }

        return null;
    }

    private boolean matches(List<?> row, Map<String, Object> attributeFilters, IExpression filterExpression,
            IContextualizationScope scope) {

        if (row.size() != attributes_.size()) {
            return false;
        }

        boolean ok = true;
        if (attributeFilters != null) {
            for (String attribute : attributeFilters.keySet()) {
                Attribute attr = getColumnDescriptor(attribute);
                if (attr == null || !checkEquals(row.get(attr.getIndex()), attributeFilters.get(attribute), attr, scope)) {
                    ok = false;
                    break;
                }
            }
        }
        if (ok && filterExpression != null) {
            // TODO
        }

        return ok;
    }

    public static boolean checkEquals(Object object1, Object object2, Attribute attribute, IContextualizationScope scope) {

        if (object1 == null || object2 == null) {
            return object1 == null && object2 == null;
        }

        if (object1.equals(object2) || object1.toString().equals(object2.toString())) {
            return true;
        }

        if (object1 instanceof Number && object2 instanceof String) {
            try {
                if (NumberUtils.equal(((Number) object1).doubleValue(), Double.parseDouble((String) object2))) {
                    return true;
                }
            } catch (Throwable t) {
                // just return false
            }
        }

        /*
         * TODO check for classifier and/or numeric match
         */

        return false;
    }

    private void addIndices(Set<Integer> collection, Collection<?> toadd) {
        for (Object o : toadd) {
            if (o instanceof Integer) {
                collection.add((Integer) o);
            } else if (o instanceof Collection) {
                addIndices(collection, (Collection<?>) o);
            }
        }
    }

    private int getIndex(Object object, int dimension) {
        if (object instanceof Integer) {
            return (Integer) object;
        } else if (dimension == 1) {
            /*
             * column descriptor
             */
            Attribute attribute = getColumnDescriptor(object.toString());
            if (attribute != null) {
                return attribute.getIndex();
            }
        }
        // TODO
        return -1;
    }

    @Override
    public ITable<T> filter(Filter.Type target, Object... locators) {
        AbstractTable<T> ret = copy();
        if (target == Filter.Type.NO_RESULTS) {
            ret.empty = true;
        }
        ret.filters.add(new FilterDescriptor(target, locators));
        validateFilters();
        return ret;
    }

    @Override
    public ITable<T> filter(Filter filter) {
        AbstractTable<T> ret = copy();
        ret.filters.add(filter);
        validateFilters();
        return ret;
    }

    @Override
    public ITable<T> resetFilters() {
        AbstractTable<T> ret = copy();
        ret.filters.clear();
        return ret;
    }

    @Override
    public boolean isEmpty() {
        return empty /* TODO also check that we have columns and rows */;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E get(Class<E> cls, IContextualizationScope scope, Object... locators) {

        boolean scanRows = false;
        List<Attribute> columns = new ArrayList<>();
        int row = -1;
        List<Integer> cols = new ArrayList<>();
        Object ret = null;

        // single-valued list output
        final int LIST = 0;
        // map c1 -> c2 output
        final int MAP = 1;
        // list of iterables with multiple columns values output
        final int ALIST = 2;

        // if set, aggregate everything before return unless it's just one value
        Aggregator aggregator = null;

        /*
         * TODO support indexing by row name/attribute too, with same mechanism as for integers
         */
        if (locators != null) {
            for (Object locator : locators) {
                if (locator instanceof Aggregator) {
                    aggregator = (Aggregator) locator;
                } else if (locator instanceof Attribute) {
                    columns.add((Attribute) locator);
                    cols.add(((Attribute) locator).getIndex());
                } else if (locator instanceof String && getColumnDescriptor(locator.toString()) != null) {
                    columns.add(getColumnDescriptor(locator.toString()));
                    cols.add(columns.get(columns.size() - 1).getIndex());
                } else if (locator instanceof Integer) {
                    if (scanRows) {
                        cols.add((Integer) locator);
                    } else {
                        scanRows = true;
                        row = (Integer) locator;
                    }
                }
            }
        }

        List<Filter> located = new ArrayList<>();
        if (!cols.isEmpty()) {
            located.add(newFilter(Filter.Type.INCLUDE_COLUMNS, new Object[]{cols}));
        }
        if (row >= 0) {
            located.add(newFilter(Filter.Type.INCLUDE_ROWS, new Object[]{row}));
        }

        boolean singleValue = cols.size() > 0 && row >= 0;

        if (singleValue) {

        } else/* if (columns.size() > 0) */ {

            int restype = -1;

            /**
             * columns not empty and row = -1
             * 
             * Return type should be list if 1 column, table or map otherwise. If map, should be 2
             * columns and the first will be used as key. This requires two steps in scanDimension,
             * one that returns the dimensionality of the result, another that scans it.
             */
            if (aggregator != null || (List.class.isAssignableFrom(cls) && cols.size() == 1)) {
                restype = LIST;
            } else if (Map.class.isAssignableFrom(cls) && cols.size() == 2) {
                restype = MAP;
            } else if (List.class.isAssignableFrom(cls) && cols.size() > 1) {
                restype = ALIST;
            } else {
                throw new KlabValidationException("table get() called with inconsistent arguments");
            }

            switch(restype) {
            case LIST:
            case ALIST:
                ret = new ArrayList<Object>();
                break;
            case MAP:
                ret = new LinkedHashMap<Object, Object>();
                break;
            }

            Collection<Object> data = getCache().scan(this, CollectionUtils.join(this.filters, located), scope);
            if (data.size() == 0) {
                return aggregator == null ? Utils.emptyValue(cls) : null;
            }
            switch(restype) {
            case LIST:
                ((List<Object>) ret).addAll(data);
                break;
            // TODO build a list of arrays, turn into a map if requested
            // case MAP:
            // String key = getCache().getObject(rowIndex, cols.get(0));
            // value = getCache().getObject(rowIndex, cols.get(1));
            // ((Map<Object, Object>) ret).put(getValue(key, columns.get(0)),
            // getValue(value, columns.get(1)));
            // break;
            // case ALIST:
            // List<Object> rval = new ArrayList<>();
            // for (int c = 0; c < cols.size(); c++) {
            // value = getCache().getObject(rowIndex, cols.get(c));
            // rval.add(getValue(value, columns.get(c)));
            // }
            // ((List<Object>) ret).add(rval);
            // break;
            }
        }

        if (ret instanceof List && aggregator != null) {
            return (E) aggregator.aggregate((List<?>) ret);
        }

        return (E) ret;
    }

    /**
     * Return a value from the table after going through all the mappings that are defined for the
     * correspondent attribute.
     * 
     * @param value
     * @param attribute
     * @return
     */
    public Object mapValue(Object value, Attribute attribute) {

        if (mappings.containsKey(attribute.getName())) {
            for (CodeList cmap : mappings.get(attribute.getName())) {
                value = value == null ? cmap.map("null") : cmap.map(value);
            }
        }

        return value;
    }

    public Object unmapValue(Object value, Attribute attribute) {

        if (mappings.containsKey(attribute.getName())) {
            List<CodeList> mps = mappings.get(attribute.getName());
            for (int i = mps.size() - 1; i >= 0; i--) {
                value = value == null ? mps.get(i).reverseMap("null") : mps.get(i).reverseMap(value);
            }
        }

        return value;
    }

    /**
     * Map a table value if necessary, then convert it to the specified type.
     * 
     * @param value
     * @param attribute
     * @return
     */
    public Object getValue(Object value, Attribute attribute) {
        return convertValue(mapValue(value, attribute), attribute);
    }

    /**
     * Convert a value to the type specified by the attribute. The value must have been mapped if
     * mappings exist.
     * 
     * @param value
     * @param attribute
     * @return
     */
    private Object convertValue(Object value, Attribute attribute) {
        return Utils.convertValue(value, attribute.getType());
    }

    @Override
    public ITable<T> collectIndices(List<Integer> indices) {
        this.lastScannedIndices = indices;
        return this;
    }

    @Override
    public ITable<T> contextualize(IContextualizationScope scope) {
        AbstractTable<T> ret = copy();
        ret.filters.clear();
        for (Filter filter : this.filters) {
            ret.filters.add(filter.contextualize(scope));
        }
        return ret;
    }

    public Filter newFilter(Filter.Type filter, Object[] objects) {
        return new FilterDescriptor(filter, objects);
    }

    public CodeList getMapping(String string) {
        CodeList ret = this.mappingCatalog.get(string);
        if (ret == null) {
            File mapfile = new File(((Resource) resource).getPath() + File.separator + "code_" + string + ".properties");
            if (mapfile.exists()) {
                ret = new CodeList(string, mapfile);
                this.mappingCatalog.put(string, ret);
            }
        }
        return ret;
    }

}
