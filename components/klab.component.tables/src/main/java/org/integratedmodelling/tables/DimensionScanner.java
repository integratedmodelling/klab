package org.integratedmodelling.tables;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.utils.NumberUtils;

/**
 * This matches a dimension in the table (according to specified encodings) to
 * the corresponding index on a table dimension or to the geometric extents
 * corresponding to it. These are computed by TableInterpreter and may be
 * cached.
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

	/*
	 * for temporal indexing
	 */
	NavigableMap<Long, Integer> temporalDimensions = null;

	// result of encoding of the geometry, stored by the TableInterpreter.
	public String encodedDimension = null;

	public DimensionScanner(String[] definition, Class<T> cls) {

		this.extent = cls;
		String[] ss = definition[0].split(":");
		switch (ss[0]) {
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
	 * table should come pre-filtered according to any other parameters, we add the
	 * column header filter to produce extent locators
	 */
	private void computeMatches(ITable<?> table) {

		this.table = table;
		this.extents = new ArrayList<T>();
		this.indices = new ArrayList<>();

		if (dimensionHeaderMatch != null) {

			int n = 0;
			for (Object o : table.filter(Filter.COLUMN_HEADER, dimensionHeaderMatch).collectIndices(this.indices)
					.asList(dimension == Dimension.ROW ? -1 : 0, dimension == Dimension.ROW ? 0 : -1)) {
				T extent = extractExtent(o);
				if (extent != null) {
					this.extents.add(extent);
				}
				n++;
			}

		} else if (this.columnName != null) {

		} else if (this.rowName != null) {

		}

	}

	@SuppressWarnings("unchecked")
	private T extractExtent(Object o) {
		if (o != null) {
			if (NumberUtils.encodesInteger(o.toString()) && ITime.class.isAssignableFrom(this.extent)) {
				int year = Integer.parseInt(o.toString());
				return (T) Time.create(year);
			}
		}
		return null;
	}

	public ITable<?> contextualize(ITable<?> table, T extent) {

		if (ITime.class.isAssignableFrom(this.extent) && extent instanceof ITime) {

			if (temporalDimensions == null) {
				scanExtents(table);
				this.temporalDimensions = new TreeMap<>();
				for (int i = 0; i < this.extents.size(); i++) {
					ITime text = (ITime) this.extents.get(i);
					int index = this.indices.get(i);
					this.temporalDimensions.put(text.getStart().getMilliseconds(), index);
				}
			}

			/*
			 * find the column(s) or row(s) covering the passed time and add a filter to the
			 * table for that.
			 */

		} else if (ISpace.class.isAssignableFrom(this.extent) && extent instanceof ISpace) {

			/*
			 * contextualize the resource in space and establish the mappings: column ->
			 * classification -> resource means resource -> inverse classification -> column
			 * filter (to add to any other in the table).
			 */
		}

		return table;
	}

}
