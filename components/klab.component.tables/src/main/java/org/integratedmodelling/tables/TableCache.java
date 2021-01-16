package org.integratedmodelling.tables;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Utils;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

/**
 * The table cache is dedicated to one table and will cache values (in
 * persistent, fast, indexed storage) and filters once they have been computed,
 * turned into the correspondent indices. If the table changes, the cache must
 * be reset.
 * <p>
 * Data are stored as strings, in a single hash indexed by the int[] dimension
 * locator. Don't think storing as byte[] would be much better performance-wise.
 * Conversion to the desired type is left to the outside logics.
 * 
 * @author Ferd
 *
 */
public class TableCache {

	private DB db;

	private Map<int[], String> dataCache;
	private Map<String, int[]> filterCache;
	private Map<String, String> properties;

	public TableCache(IResource resource) {
		db = DBMaker.fileDB(
				Configuration.INSTANCE.getDataPath("tables") + File.separator + sanitize(resource.getUrn()) + ".db")
				/* .fileMmapEnable() */.transactionEnable().make();
		this.dataCache = db.hashMap("data", Serializer.INT_ARRAY, Serializer.STRING).createOrOpen();
		this.filterCache = db.hashMap("filters", Serializer.STRING, Serializer.INT_ARRAY).createOrOpen();
		this.properties = db.hashMap("properties", Serializer.STRING, Serializer.STRING).createOrOpen();
	}

	public boolean isEmpty() {
		return properties.isEmpty();
	}

	private String sanitize(String urn) {
		return urn.replaceAll(":", "_");
	}

	public void reset(ITable<?> table) {
		dataCache.clear();
		filterCache.clear();
		properties.clear();
		loadData(table);
	}

	public String getObject(int... locators) {
		return null;
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
	public Iterable<Integer> scanDimension(int dimensionIndex, Collection<Filter> filters) {

		int[] dimensions = NumberUtils.intArrayFromString(properties.get("dimensions"));
		LinkedHashSet<Integer> ret = NumberUtils.enumerateAsSet(dimensions[dimensionIndex]);
		for (Filter filter : filters) {
			if (filter.getDimension() == dimensionIndex) {
				ret.retainAll(matchFilter(filter, dimensions, dimensionIndex));
				if (ret.isEmpty()) {
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * If the filter is known, retain or remove any indices that the filter admits
	 * or excludes from the passed set, then return the modified set. Handle the
	 * caching of the filter results for the table as it is stored.
	 * 
	 * @param filter
	 * @param ret
	 * @return
	 */
	private Set<Integer> matchFilter(Filter filter, int[] dimensions, int dimension) {

		int[] ret = filterCache.get(filter.getSignature());
		if (ret == null) {
			List<Number> indices = new ArrayList<>();
			switch (filter.getType()) {
			case ATTRIBUTE_VALUE:
				break;
			case COLUMN_EXPRESSION:
				break;
			case COLUMN_HEADER:
				break;
			case COLUMN_MATCH:
				break;
			case EXCLUDE_COLUMNS:
				break;
			case EXCLUDE_ROWS:
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
			}
			ret = NumberUtils.intArrayFromCollection(indices);
			filterCache.put(filter.getSignature(), ret);
		}

		Set<Integer> rset = new HashSet<>();
		for (int n : ret) {
			rset.add(n);
		}

		return rset;

	}

	private void loadData(ITable<?> table) {

		this.properties.put("dimensions", NumberUtils.toString(table.getDimensions()));
		int row = 0;
		for (Iterable<?> it : table) {
			int col = 0;
			for (Object value : it) {
				if (value == null) {
					value = "";
				}
				dataCache.put(new int[] { row, col }, value.toString());
				col++;
			}
			row ++;
		}
		db.commit();
	}

}
