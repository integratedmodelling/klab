package org.integratedmodelling.klab.documentation.extensions.table;

/**
 * Simple API to wrap the writing of a table so we can support export and visualization with the 
 * same functions.
 * 
 * @author Ferd
 *
 */
public interface TableOutput {

	/**
	 * 
	 * @return
	 */
	int newRow();

	/**
	 * Shorthand for newCell(row, 1, 1);
	 * @param row
	 * @return
	 */
	int newCell(int row);

	int newCell(int row, int colSpan, int rowSpan);
	
	/**
	 * 
	 * @param cell
	 * @param content
	 * @param options
	 */
	void write(int cell, Object content, Object... options);

}
