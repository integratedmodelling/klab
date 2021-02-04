package org.integratedmodelling.tables;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.persistence.h2.H2Database;
import org.integratedmodelling.klab.persistence.h2.H2Database.DBIterator;
import org.integratedmodelling.klab.persistence.h2.SQL;
import org.integratedmodelling.klab.utils.Utils;

/**
 * H2-based cache - hopefully faster than the other based on DB
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

    public SQLTableCache(IResource resource) {

        this.dbname = sanitize(resource.getUrn());
        this.resource = resource;
        this.dimensions = new int[]{Integer.parseInt(resource.getParameters().get("rows.data").toString()),
                Integer.parseInt(resource.getParameters().get("columns.data").toString())};

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

        String sql = "CREATE TABLE data (\n   oid LONG";
        for (Attribute column : getSortedAttributes()) {
            sql += ",\n   " + column.getName() + " " + SQL.getType(column.getType());
            if (getWidth(column) > 0) {
                sql += "(" + getWidth(column) + ")";
            }
        }

        sql += ");\nCREATE INDEX oid_index ON data(oid);";
        for (Attribute column : getSortedAttributes()) {
            if (isIndexed(column)) {
                sql += "\nCREATE " + (column.getType() == IArtifact.Type.SPATIALEXTENT ? "SPATIAL" : "") + " INDEX "
                        + column.getName() + "_index ON data (" + column.getName() + ");";
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
        Integer ret = Integer.parseInt(resource.getParameters().get("column." + attribute.getName() + ".searchable", "-1"));
        if (ret < 0 && attribute.getType() == Type.TEXT) {
            return 1024;
        }
        return attribute.getType() == Type.TEXT ? ret : -1;
    }

    public boolean isEmpty() {
        return !database.hasTable("data");
    }

    private String sanitize(String urn) {
        return urn.replaceAll(":", "_");
    }

    public void reset(ITable<?> table) {
        database.execute("DROP TABLE data;");
        createStructure();
        loadData(table);
    }

    public Object getObject(int... locators) {
        return null; // dataCache.get(locators);
    }

    public void loadData(ITable<?> table) {

        // this.properties.put("dimensions", NumberUtils.toString(table.getDimensions()));
        int row = 0;
        for (Iterable<?> it : table) {
            int col = 0;
            String sql = "INSERT INTO data VALUES (" + row;
            for (Object value : it) {
                sql += ", " + SQL.wrapPOD(value, getSortedAttributes().get(col).getType());
                col++;
            }
            sql += ");";
            database.execute(sql);
            row++;
        }
    }

    /**
     * Pass a set of filters and return a list of results - either a singleton, a list or a list of
     * lists according to the dimensionality of the result. Lists of lists can be nested as needed
     * to represent n-dimensional results.
     * 
     * @param dimensionIndex
     * @param filters
     * @return
     */
    public List<Object> scan(AbstractTable<?> table, Collection<Filter> filters, IContextualizationScope scope) {

        String fields = "";
        String where = "";

        Set<String> searchedColumns = new HashSet<>();
        List<String> retrieved = new ArrayList<>();

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
                        searchedColumns.add(attribute.getName());
                        where += (where.isEmpty() ? "(" : " AND (") + getCondition(attribute.getName(), value) + ")";
                    }
                }

                break;
            case INCLUDE_COLUMNS:

                List<Integer> indices = new ArrayList<>();
                Utils.collectValues(filter.getArguments(), indices, Integer.class);
                for (Integer index : indices) {
                    Attribute attribute = table.getColumnDescriptor(index);
                    if (attribute != null) {
                        fields += (fields.isEmpty() ? "" : ", ") + attribute.getName();
                        retrieved.add(attribute.getName());
                    }
                }
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

        if (fields.isEmpty()) {
            fields = "*";
            for (Attribute attr : getSortedAttributes()) {
                retrieved.add(attr.getName());
            }
        }

        /*
         * ensure all the columns we're searching are indexed
         */
        for (String searched : searchedColumns) {
            if (!retrieved.contains(searched) && !"*".equals(fields)) {
                fields += ", " + searched;
            }
            database.execute("CREATE INDEX IF NOT EXISTS " + searched + "_index ON data(" + searched + ");");
        }

        List<Object> ret = new ArrayList<>();

        String query = "SELECT " + fields + " FROM data" + (where.isEmpty() ? "" : (" WHERE " + where)) + ";";

        System.out.println(query);

        try (DBIterator result = database.query(query)) {
            while(result.hasNext()) {
                try {
                    if (retrieved.size() == 1) {
                        ret.add(table.mapValue(result.result.getObject(1), table.getColumnDescriptor(retrieved.get(0))));
                    } else if (retrieved.size() > 1) {
                        List<Object> row = new ArrayList<>();
                        for (int i = 0; i < retrieved.size(); i++) {
                            row.add(table.mapValue(result.result.getObject(i + 1), table.getColumnDescriptor(retrieved.get(i))));
                        }
                        ret.add(row);
                    }
                    result.advance();
                } catch (SQLException e) {
                    // TODO shouldn't happen
                }
            }
        }

        return ret;
    }

    private String getCondition(String field, Object value) {

        if (value instanceof Collection) {
            String ret = "";
            for (Object o : ((Collection<?>) value)) {
                ret += (ret.isEmpty() ? "(" : " OR (") + getCondition(field, o) + ")";
            }
            return ret;
        }
        return field + " = " + SQL.wrapPOD(value);
    }

}
