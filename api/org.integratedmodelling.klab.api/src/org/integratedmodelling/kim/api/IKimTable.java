package org.integratedmodelling.kim.api;

import java.util.List;

public interface IKimTable extends IKimStatement {
	/**
	 * The headers for the table, or null if not specified. Also null if the table is
	 * two-ways.
	 * 
	 * @return headers or null
	 */
	List<String> getHeaders();

	/**
	 * If the table is two-way, the headers are mandatory and describe both rows and columns.
	 * 
	 * @return
	 */
	boolean isTwoWay();
	
	/**
	 * Only not-null if the table is two-ways.
	 * 
	 * @return
	 */
	List<IKimClassifier> getRowClassifiers();

    /**
     * Only not-null if the table is two-ways.
     * 
     * @return
     */
	List<IKimClassifier> getColumnClassifiers();

	int getRowCount();

	int getColumnCount();

	IKimClassifier[] getRow(int i);
	
	/**
	 * Row classifiers start from the second element if the table is two-way.
	 * 
	 * @return
	 */
	List<IKimClassifier[]> getRows();

}
