package org.integratedmodelling.kim.api;

import java.util.List;

public interface IKimLookupTable extends IKimStatement {

	int getRowCount();

	int getColumnCount();

	/**
	 * The variables used for lookup. TODO these should be here, the rest in a
	 * lower-level IKimTable like in the engine peer class.
	 * 
	 * @return
	 */
	List<String> getArguments();

	/**
	 * The headers for the table, or null if not specified.
	 * 
	 * @return headers or null
	 */
	List<String> getHeaders();

	IKimClassifier[] getRow(int i);

}
