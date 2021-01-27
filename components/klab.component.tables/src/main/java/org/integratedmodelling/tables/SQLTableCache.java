package org.integratedmodelling.tables;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

public class SQLTableCache {
    
    public SQLTableCache(IResource resource, AbstractTable<?> table, Map<String, Attribute> attributes) {
    }

    public boolean isEmpty() {
        return true;
    }

    private String sanitize(String urn) {
        return urn.replaceAll(":", "_");
    }

    public void reset(ITable<?> table) {
//        dataCache.clear();
//        filterCache.clear();
//        properties.clear();
        loadData(table);
    }
    
    public String getObject(int... locators) {
        return null; // dataCache.get(locators);
    }

    private void loadData(ITable<?> table) {

//        this.properties.put("dimensions", NumberUtils.toString(table.getDimensions()));
//        int row = 0;
//        for (Iterable<?> it : table) {
//            int col = 0;
//            for (Object value : it) {
//                if (value == null) {
//                    value = "";
//                }
//                dataCache.put(new int[] { row, col }, value.toString());
//                col++;
//            }
//            row++;
//        }
//        db.commit();
    }
    
    /**
     * Pass all the filters for a table and return the filter indices along the
     * passed dimension. Filters that haven't been seen before will be precomputed
     * and cached. Resetting the table will remove all caches.
     * 
     * @param dimensionIndex
     * @param filters
     * @return
     */
    public Collection<Integer> scanDimension(int dimensionIndex, Collection<Filter> filters,
            IContextualizationScope scope, @Nullable Collection<Filter> additionalFilters) {
        return null;
    }

}
