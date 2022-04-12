package org.integratedmodelling.tables;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
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
	private Map<String, Attribute> attributes;
	private AbstractTable<?> table;
	
	public TableCache(IResource resource, AbstractTable<?> table, Map<String, Attribute> attributes) {
				
		this.table = table;
		this.attributes = attributes;
		db = DBMaker.fileDB(
				Configuration.INSTANCE.getDataPath("tables") + File.separator + sanitize(resource.getUrn()) + ".db")
				/* .fileMmapEnable() .transactionEnable()*/.make();
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
		return dataCache.get(locators);
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

		LinkedHashSet<Integer> ret = new LinkedHashSet<>();
		int[] dimensions = NumberUtils.intArrayFromString(properties.get("dimensions"));
		int done = 0;
		for (Filter filter : filters) {
			if (filter.getDimension() == dimensionIndex) {
				ret = matchFilter(filter, dimensions, dimensionIndex, scope, ret);
				done ++;
				if (ret.isEmpty()) {
					break;
				}
			}
		}
		if ((!ret.isEmpty() || done == 0) && additionalFilters != null) {
			for (Filter filter : additionalFilters) {
				if (filter.getDimension() == dimensionIndex) {
					ret = matchFilter(filter, dimensions, dimensionIndex, scope, ret);
					done ++;
					if (ret.isEmpty()) {
						break;
					}
				}
			}
		}
		
		if (done == 0) {
			// no filters for this dimension, we want them all
			ret = NumberUtils.enumerateAsSet(dimensions[dimensionIndex]);
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
	private LinkedHashSet<Integer> matchFilter(Filter filter, int[] dimensions, int dimension,
			IContextualizationScope scope, Set<Integer> indicesSoFar) {

		LinkedHashSet<Integer> ret = new LinkedHashSet<>(indicesSoFar);
		int[] cached = filter.isCached() ? filterCache.get(filter.getSignature()) : null;
		LinkedHashSet<Integer> indices = new LinkedHashSet<>();
		
		if (cached == null) {

			switch (filter.getType()) {
			case COLUMN_EXPRESSION:
				break;
			case COLUMN_HEADER:
				break;
			case COLUMN_MATCH:
				
				// first locator must be a column indicator
				int col = filter.getArguments().get(0) instanceof Number
						? ((Number) filter.getArguments().get(0)).intValue()
						: -1;
				Attribute attr = null;
				if (col < 0) {
					if (filter.getArguments().get(0) instanceof String) {
						attr = attributes.get(filter.getArguments().get(0).toString());
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
					Object object1 = dataCache.get(new int[] { i, col });
					for (Object object2 : match) {
						if (AbstractTable.checkEquals(table.mapValue(object1.toString(), attr), object2, attr, scope)) {
							indices.add(i);
							break;
						}
					}
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
				cached = NumberUtils.intArrayFromCollection(indices);
				filterCache.put(filter.getSignature(), cached);
				db.commit();
			}
			
		} else {
			for (int n : cached) {
				indices.add(n);
			}
		}

		if (ret.isEmpty()) {
			ret.addAll(indices);
		} else {
			// starting out: if we return empty, we stop
			ret.retainAll(indices);
		}

		return ret;

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
			row++;
		}
		db.commit();
	}

}
