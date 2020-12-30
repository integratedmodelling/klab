package org.integratedmodelling.tables;

import java.util.ArrayList;
import java.util.Map;

import org.integratedmodelling.klab.api.data.general.ITable;

/**
 * This matches a dimension in the table (according to specified encodings) to
 * the corresponding index on a table dimension or to the geometric extents corresponding to
 * it.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class DimensionScanner<T> {

	public enum Dimension {
		COLUMN, ROW
	};

	public interface Filter {
		Iterable<Integer> getDimensionIndices();
	}

	public DimensionScanner(String[] definition, Map<String, String> urnParameters) {

	}

	// scan this dimension
	private Dimension dimension = Dimension.ROW;
	// filter for the scanned dimension
	private Filter scanFilter;
	// filter to localize the complementary dimension (if it selects > 1 row or
	// column, they will be aggregated)
	private Filter dataFilter;

	public Iterable<T> scanExtents(ITable<?> table) {
		return new ArrayList<T>();
	}

	public Iterable<Integer> scanIndices(ITable<?> table) {
		return new ArrayList<Integer>();
	}
}
