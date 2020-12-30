package org.integratedmodelling.tables;

import java.util.ArrayList;
import java.util.Map;

import org.integratedmodelling.klab.api.data.general.ITable;

public class ScanStrategy {

	public enum Dimension {
		COLUMN,
		ROW
	};
	
	public interface Filter {
		Iterable<Integer> getDimensionIndices();
	}
	
	public ScanStrategy(String[] definition, Map<String, String> urnParameters) {
		
	}
	
	// scan this dimension
	public Dimension dimension = Dimension.ROW;
	// filter for the scanned dimension
	public Filter scanFilter;
	// filter to localize the complementary dimension (if it selects > 1 row or column, they will be aggregated)
	public Filter dataFilter;
	
	public <T> Iterable<T> scan(ITable<T> table) {
		return new ArrayList<T>();
	}
}
