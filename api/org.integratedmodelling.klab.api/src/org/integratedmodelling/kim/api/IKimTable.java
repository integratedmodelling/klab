package org.integratedmodelling.kim.api;

import java.util.List;

public interface IKimTable extends IKimStatement {
	/**
	 * The headers for the table, or null if not specified.
	 * 
	 * @return headers or null
	 */
	List<String> getHeaders();

	int getRowCount();

	int getColumnCount();

	IKimClassifier[] getRow(int i);

}
