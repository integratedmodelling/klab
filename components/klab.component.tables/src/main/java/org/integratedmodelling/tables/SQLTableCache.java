package org.integratedmodelling.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.persistence.h2.H2Database;
import org.integratedmodelling.klab.persistence.h2.SQL;
import org.integratedmodelling.klab.utils.NumberUtils;
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

        // verify or create table
        if (database.hasTable("data")) {

            // TODO verify

        } else {

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
        return database.hasTable("data");
    }

    private String sanitize(String urn) {
        return urn.replaceAll(":", "_");
    }

    public void reset(ITable<?> table) {
        database.execute("DROP TABLE data;");
        loadData(table);
    }

    public Object getObject(int... locators) {
        return null; // dataCache.get(locators);
    }

    private void loadData(ITable<?> table) {

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

    public static void main(String[] args) {

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
    public List<Object> scan(Collection<Filter> filters, IContextualizationScope scope) {

        // LinkedHashSet<Integer> ret = new LinkedHashSet<>();
        // int done = 0;
        // for (Filter filter : filters) {
        // if (filter.getDimension() == dimensionIndex) {
        // ret = matchFilter(filter, dimensions, dimensionIndex, scope, ret);
        // done++;
        // if (ret.isEmpty()) {
        // break;
        // }
        // }
        // }
        // if ((!ret.isEmpty() || done == 0) && additionalFilters != null) {
        // for (Filter filter : additionalFilters) {
        // if (filter.getDimension() == dimensionIndex) {
        // ret = matchFilter(filter, dimensions, dimensionIndex, scope, ret);
        // done++;
        // if (ret.isEmpty()) {
        // break;
        // }
        // }
        // }
        // }
        //
        // if (done == 0) {
        // // no filters for this dimension, we want them all
        // ret = NumberUtils.enumerateAsSet(dimensions[dimensionIndex]);
        // }
        //
        // return ret;

        return null;
    }

    /**
     * If the filter is known, retain or remove any indices that the filter admits or excludes from
     * the passed set, then return the modified set. Handle the caching of the filter results for
     * the table as it is stored.
     * 
     * @param filter
     * @param ret
     * @return
     */
    private LinkedHashSet<Integer> matchFilter(Filter filter, int[] dimensions, int dimension, IContextualizationScope scope,
            Set<Integer> indicesSoFar) {

        LinkedHashSet<Integer> ret = new LinkedHashSet<>(indicesSoFar);
        // int[] cached = filter.isCached() ? filterCache.get(filter.getSignature()) : null;
        LinkedHashSet<Integer> indices = new LinkedHashSet<>();

        // if (cached == null) {

        switch(filter.getType()) {
        case COLUMN_EXPRESSION:
            break;
        case COLUMN_HEADER:
            break;
        case COLUMN_MATCH:

            // first locator must be a column indicator
            int col = filter.getArguments().get(0) instanceof Number ? ((Number) filter.getArguments().get(0)).intValue() : -1;
            Attribute attr = null;
            if (col < 0) {
                if (filter.getArguments().get(0) instanceof String) {
                    // attr = attributes.get(filter.getArguments().get(0).toString());
                } else if (filter.getArguments().get(0) instanceof Attribute) {
                    attr = (Attribute) filter.getArguments().get(0);
                }
                if (attr != null) {
                    col = attr.getIndex();
                }
            }

            if (col < 0) {
                throw new KlabIllegalStateException("table: column filter does not specify a valid column");
            }

            List<Object> match = new ArrayList<>();
            if (filter.getArguments().get(1) instanceof Collection) {
                match.addAll((Collection<?>) filter.getArguments().get(1));
            } else {
                match.add(filter.getArguments().get(1));
            }
            for (int i = 0; i < dimensions[0]; i++) {
                // Object object1 = dataCache.get(new int[]{i, col});
                // for (Object object2 : match) {
                // if (AbstractTable.checkEquals(table.mapValue(object1, attr), object2, attr,
                // scope)) {
                // indices.add(i);
                // break;
                // }
                // }
            }

            break;
        case INCLUDE_COLUMNS:

            Utils.collectValues(filter.getArguments(), indices, Integer.class);

            break;
        case INCLUDE_ROWS:
            break;
        case NO_RESULTS:
            break;
        case ROW_HEADER:
            break;
        case ROW_MATCH:
            break;
        }

        // save to avoid repeating
        if (filter.isCached()) {
            // cached = NumberUtils.intArrayFromCollection(indices);
            // filterCache.put(filter.getSignature(), cached);
            // db.commit();
        }

        // } else {
        // for (int n : cached) {
        // indices.add(n);
        // }
        // }

        if (ret.isEmpty()) {
            ret.addAll(indices);
        } else {
            // starting out: if we return empty, we stop
            ret.retainAll(indices);
        }

        return ret;

    }
}
