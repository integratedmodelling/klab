package org.integratedmodelling.tables;

import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.api.data.general.ITable.SearchOptions;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.data.table.TableValue;
import org.integratedmodelling.klab.documentation.style.StyleDefinition;
import org.integratedmodelling.klab.exceptions.KlabStorageException;
import org.integratedmodelling.klab.persistence.h2.H2Database;
import org.integratedmodelling.klab.persistence.h2.H2Database.DBIterator;
import org.integratedmodelling.klab.persistence.h2.SQL;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.tables.DimensionScanner.Dimension;

import tech.tablesaw.api.Table;

/**
 * H2-based cache - hopefully faster than the other based on DB. Can be created from a resource
 * after partial ingestion or pre-built by passing a table and a URN.
 * 
 * @author Ferd
 *
 */
public class SQLTableCache {

    H2Database database;
    String dbname;
    List<Attribute> sortedAttributes_ = null;
    IResource resource;
    int[] dimensions;
    Map<String, String> sanitizedNames = new HashMap<>();

    public static long createCache(String id, Table table, IMonitor monitor) {

        SQLTableCache cache = new SQLTableCache();
        List<Attribute> sortedAttributes = TablesawTable.getAttributes(table);
        for (Attribute attribute : sortedAttributes) {
            cache.sanitizedNames.put(cache.sanitize(attribute.getName()), attribute.getName());
        }

        cache.dbname = cache.sanitize(id);
        cache.sortedAttributes_ = sortedAttributes;
        cache.database = H2Database.createPersistent(cache.dbname);
        if (cache.isEmpty()) {
            cache.createStructure();
            cache.loadData(new TablesawTable(table, monitor));
        }
        return cache.database.countRows("data");
    }

    private SQLTableCache() {
    }

    public SQLTableCache(IResource resource, String dbname) {

        this.dbname = dbname;
        this.resource = resource;
        this.dimensions = new int[]{Integer.parseInt(resource.getParameters().get("rows.data").toString()),
                Integer.parseInt(resource.getParameters().get("columns.data").toString())};
        for (Attribute attribute : resource.getAttributes()) {
            this.sanitizedNames.put(sanitize(attribute.getName()), attribute.getName());
        }
        if (database == null) {
            database = H2Database.createPersistent(dbname);
        }
    }

    public List<Attribute> getSortedAttributes() {
        if (this.sortedAttributes_ == null) {
            this.sortedAttributes_ = new ArrayList<>();
            for (Attribute attribute : resource.getAttributes()) {
                this.sortedAttributes_.add(attribute);
            }
            Collections.sort(this.sortedAttributes_, new Comparator<Attribute>(){
                @Override
                public int compare(Attribute o1, Attribute o2) {
                    return Integer.compare(o1.getIndex(), o2.getIndex());
                }
            });
        }
        return this.sortedAttributes_;
    }

    private void createStructure() {

        /**
         * Re-entrant or we can't test anything. TODO should be an option for when things are
         * expensive or temporary.
         */
        database.execute("DROP TABLE data IF EXISTS;");

        String sql = "CREATE TABLE data (\n   oid LONG";
        for (Attribute column : getSortedAttributes()) {
            sql += ",\n   " + sanitize(column.getName()) + " " + SQL.getType(column.getType());
            if (getWidth(column) > 0) {
                sql += "(" + getWidth(column) + ")";
            }
        }

        sql += ");\nCREATE INDEX oid_index ON data(oid);";
        for (Attribute column : getSortedAttributes()) {
            if (isIndexed(column)) {
                sql += "\nCREATE " + (column.getType() == IArtifact.Type.SPATIALEXTENT ? "SPATIAL" : "") + " INDEX "
                        + sanitize(column.getName()) + "_index ON data (" + sanitize(column.getName()) + ");";
            }
        }

        database.execute(sql);
    }

    /**
     * True if the resource attributes request indexing for the specified column.
     * 
     * @param attribute
     * @return
     */
    public boolean isIndexed(Attribute attribute) {
        if (resource == null) {
            return attribute.getType() == Type.TEXT;
        }
        return "true".equalsIgnoreCase(resource.getParameters().get("column." + attribute.getName() + ".searchable", "false"));
    }

    /**
     * Return the field width for the attribute if it's text, using any specs set in the resource
     * parameters or defaults. Return -1 if no width should be specified (e.g. the attribute is
     * numeric).
     * 
     * @param attribute
     * @return
     */
    public int getWidth(Attribute attribute) {
        Integer ret = resource == null
                ? -1
                : Integer.parseInt(resource.getParameters().get("column." + attribute.getName() + ".size", "-1"));
        if (ret < 0 && attribute.getType() == Type.TEXT) {
            return 1024;
        }
        return attribute.getType() == Type.TEXT ? ret : -1;
    }

    public boolean isEmpty() {
        long rows = database.countRows("data");
        return rows <= 0;
    }

    private String sanitize(String urn) {
        return urn.replaceAll(":", "_");
    }

    public void reset(ITable<?> table, boolean force) {
        if (isEmpty() || force) {
            database.execute("DROP TABLE IF EXISTS data;");
            createStructure();
            loadData(table);
        }
    }

    public Object getObject(int... locators) {
        return null; // dataCache.get(locators);
    }

    /**
     * Dump the data as CSV with the specified row limit. If rows == 0, dump the headers only.
     * 
     * @param rows
     * @param writer
     */
    public void dumpData(int rows, OutputStreamWriter writer) {

        boolean first = true;
        List<String> fields = new ArrayList<>();

        try {

            for (DBIterator res = database.query("SELECT * FROM data LIMIT " + (rows == 0 ? 1 : rows) + ";"); res.hasNext(); res
                    .advance()) {

                if (first) {
                    for (int i = 0; i < res.result.getMetaData().getColumnCount(); i++) {
                        String field = res.result.getMetaData().getColumnName(i + 1);
                        fields.add(field);
                        writer.append((i == 0 ? "" : ",") + field);
                    }
                    writer.append("\n");
                }

                if (rows > 0) {
                    boolean firstfld = true;
                    for (String s : fields) {
                        writer.append((firstfld ? "" : ",") + res.result.getObject(s));
                        firstfld = false;
                    }
                    writer.append("\n");
                }

                first = false;
            }
        } catch (Exception s) {
            throw new KlabStorageException(s);
        }

    }

    public int loadData(ITable<?> table) {

        // this.properties.put("dimensions",
        // NumberUtils.toString(table.getDimensions()));
        int row = 0;
        Connection connection = database.getConnection();
        for (Iterable<?> it : table) {
            int col = 0;
            String sql = "INSERT INTO data VALUES (" + row;
            for (Object value : it) {
                sql += ", " + SQL.wrapPOD(value, getSortedAttributes().get(col).getType());
                col++;
            }
            sql += ");";
            database.execute(sql, connection);
            row++;
        }

        try {
            connection.commit();
        } catch (SQLException e) {
            throw new KlabStorageException(e);
        }

        return row;
    }

    /**
     * Pass a set of filters and return the result to be reduced as requested through the return
     * type and the resource specs.
     * 
     * @param dimensionIndex
     * @param filters
     * @return
     */
    public TableValue scan(AbstractTable<?> table, Collection<Filter> filters, IContextualizationScope scope,
            Aggregator aggregator, ILocator locator) {

        String fields = "";
        String where = "";

        Set<String> searchedColumns = new HashSet<>();
        Set<String> keyFields = new LinkedHashSet<>();
        Set<String> reqFields = new LinkedHashSet<>();

        // we look for the style early as it may mandate keeping keys that we wouldn't
        // otherwise
        StyleDefinition style = (scope instanceof RuntimeScope ? ((RuntimeScope) scope).getOutputStyle() : null);
        if (style != null && style.get("keep") instanceof Collection) {
            for (Object o : (Collection<?>) style.get("keep")) {
                if (table.getColumnDescriptor(o.toString()) != null) {
                    reqFields.add(o.toString());
                }
            }
        }
        boolean functional = "true".equals(resource.getParameters().get("value.functional", "false"));
        String valueField = resource.getParameters().get("value.column", (String) null);

        for (Filter filter : filters) {
            switch(filter.getType()) {
            case COLUMN_EXPRESSION:
                break;
            case COLUMN_HEADER:
                break;
            case COLUMN_MATCH:

                for (int i = 0; i < filter.getArguments().size(); i++) {

                    int col = filter.getArguments().get(i) instanceof Number
                            ? ((Number) filter.getArguments().get(i)).intValue()
                            : -1;
                    Attribute attribute = null;
                    if (col < 0) {
                        if (filter.getArguments().get(i) instanceof String) {
                            attribute = table.getColumnDescriptor(filter.getArguments().get(i).toString());
                        } else if (filter.getArguments().get(i) instanceof Attribute) {
                            attribute = (Attribute) filter.getArguments().get(i);
                        }
                    } else {
                        attribute = table.getColumnDescriptor(col);
                    }

                    Object value = filter.getArguments().get(++i);
                    if (attribute != null) {
                        value = table.unmapValue(value, attribute);
                        searchedColumns.add(sanitize(attribute.getName()));
                        where += (where.isEmpty() ? "(" : " AND (") + getCondition(sanitize(attribute.getName()), value) + ")";
                    }
                }

                break;
            case INCLUDE_COLUMNS:

                List<Integer> indices = new ArrayList<>();
                Utils.collectValues(filter.getArguments(), indices, Integer.class);
                for (Integer index : indices) {
                    Attribute attribute = table.getColumnDescriptor(index);
                    if (attribute != null) {
                        fields += (fields.isEmpty() ? "" : ", ") + sanitize(attribute.getName());
                        keyFields.add(sanitize(attribute.getName()));
                    }
                    if (valueField == null) {
                        valueField = attribute.getName();
                    }
                }
                break;
            case INCLUDE_ROWS:
                break;
            case NO_RESULTS:
                return TableValue.empty();
            case ROW_HEADER:
                break;
            case ROW_MATCH:
                break;
            case TIME_BETWEEN_FIELDS:
                String timecond = sanitize(filter.getArguments().get(0).toString()) + " >= "
                        + scope.getScale().getTime().getStart().getMilliseconds() + " AND "
                        + sanitize(filter.getArguments().get(0).toString()) + " < "
                        + scope.getScale().getTime().getEnd().getMilliseconds();
                where += (where.isEmpty() ? "(" : " AND (") + timecond + ")";
                break;
            default:
                break;
            }
        }

        Map<String, ICodelist> codelists = new HashMap<>();

        if (keyFields.isEmpty() && functional) {
            for (Pair<String, ICodelist> codes : table.getClassifiedColumns()) {
                if (!searchedColumns.contains(codes.getFirst())) {
                    codelists.put(codes.getFirst(), codes.getSecond());
                    keyFields.add(codes.getFirst());
                    fields += (fields.isEmpty() ? "" : ", ") + codes.getFirst();
                } else if (reqFields.contains(codes.getFirst())) {
                    codelists.put(codes.getFirst(), codes.getSecond());
                }
            }
        }

        if (fields.isEmpty()) {
            fields = "*";
            for (Attribute attr : getSortedAttributes()) {
                keyFields.add(sanitize(attr.getName()));
            }
        }

        /*
         * List of all retrieved fields including those searched
         */
        List<String> allFields = new ArrayList<>(keyFields);

        for (String reqField : reqFields) {
            if (!keyFields.contains(reqField)) {
                fields += (fields.isEmpty() ? "" : ", ") + reqField;
                allFields.add(reqField);
                keyFields.add(reqField);
            }
        }

        if (valueField != null && !valueField.trim().isEmpty() && !keyFields.contains(valueField)) {
            fields += (fields.isEmpty() ? "" : ", ") + valueField;
            allFields.add(valueField);
        }

        /*
         * ensure all the columns we're searching are indexed
         */
        for (String searched : searchedColumns) {
            if (!keyFields.contains(searched) && !"*".equals(fields)) {
                fields += ", " + searched;
                allFields.add(searched);
            }
            database.execute("CREATE INDEX IF NOT EXISTS " + searched + "_index ON data(" + searched + ");");
        }

        List<Object> ret = new ArrayList<>();

        String query = "SELECT " + fields + " FROM data" + (where.isEmpty() ? "" : (" WHERE " + where)) + ";";

        System.out.println(query);

        try (DBIterator result = database.query(query)) {
            while(result.hasNext()) {
                try {
                    if (keyFields.size() == 1) {
                        Object res = result.result.getObject(1);
                        ret.add(table.mapValue(res == null ? null : res,
                                table.getColumnDescriptor(sanitizedNames.get(keyFields.iterator().next()))));
                    } else if (keyFields.size() > 1) {
                        List<Object> row = new ArrayList<>();
                        for (int i = 0; i < allFields.size(); i++) {
                            Object res = result.result.getObject(allFields.get(i));
                            row.add(table.mapValue(res == null ? null : res,
                                    table.getColumnDescriptor(sanitizedNames.get(allFields.get(i)))));
                        }
                        ret.add(row);
                    }
                    result.advance();
                } catch (SQLException e) {
                    // shouldn't happen
                    throw new KlabStorageException(e);
                }
            }
        }

        return new TableValue(ret, allFields, keyFields, valueField, codelists, aggregator, style, scope, locator);
    }

    private String getCondition(String field, Object value) {

        if (value instanceof Collection) {
            String ret = "";
            for (Object o : ((Collection<?>) value)) {
                ret += (ret.isEmpty() ? "(" : " OR (") + getCondition(field, o) + ")";
            }
            return ret;
        } else if (value != null && value.getClass().isArray()) {
            String ret = "";
            for (int i = 0; i < Array.getLength(value); i++) {
                ret += (ret.isEmpty() ? "(" : " OR (") + getCondition(field, Array.get(value, i)) + ")";
            }
            return ret;

        }
        return field + " = " + SQL.wrapPOD(value);
    }

    public Iterator<Iterable<?>> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Object> query(Dimension dimension, Object... columnLocators) {

        String what = "";
        String select = "SELECT";
        int nfields = 0;
        for (Object locs : columnLocators) {
            for (Object locator : expand(locs)) {
                if (SearchOptions.UNIQUE.equals(locator)) {
                    select += " DISTINCT";
                } else if (locator instanceof Integer) {
                    Attribute attr = getSortedAttributes().get((Integer) locator);
                    what += (what.isEmpty() ? "" : ", ") + attr.getName();
                    nfields++;
                } else if (locator instanceof String) {
                    what += (what.isEmpty() ? "" : ", ") + locator;
                    nfields++;
                }
            }
        }

        List<Object> ret = new ArrayList<>();
        if (!what.isEmpty()) {
            try (DBIterator result = database.query(select + " " + what + " FROM data;")) {
                while(result.hasNext()) {
                    List<Object> rres = new ArrayList<>();
                    for (int i = 0; i < nfields; i++) {
                        rres.add(result.result.getObject(i + 1));
                    }
                    ret.add(rres.size() == 1 ? rres.get(0) : rres.toArray());
                    result.advance();
                }
            } catch (Exception e) {
                throw new KlabStorageException(e);
            }
        }

        return ret;
    }

    private Collection<Object> expand(Object locs) {
        List<Object> ret = new ArrayList<>();
        if (locs instanceof Collection) {
            for (Object o : ((Collection<?>) locs)) {
                ret.add(o);
            }
        } else if (locs.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(locs); i++) {
                ret.add(Array.get(locs, i));
            }
        } else {
            ret.add(locs);
        }
        return ret;
    }

    public Object query(Object rowLocator, Object columnLocator) {
        // TODO Auto-generated method stub
        return null;
    }

}
