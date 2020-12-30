package org.integratedmodelling.tables;

public class ScanningStrategy {

	public enum Dim {
		COLUMN,
		ROW
	};
	
	public interface Filter {
		Iterable<Integer> getDimensionIndices();
	}
	
	// scan this dimension
	public Dim dimension;
	// filter for the scanned dimension
	public Filter scanFilter;
	// filter to localize the complementary dimension (if it selects > 1 row or column, they will be aggregated)
	public Filter dataFilter;
	
	public Iterable<Object> getValues() {
		return null;
	}
}
