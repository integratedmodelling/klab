package org.integratedmodelling.klab.api.documentation.views;

/**
 * Simple API to wrap the writing of a table so we can support export and
 * visualization with the same functions. Columns do not exist - just rows
 * and cells, organized in tables which are part of sheets.
 * 
 * @author Ferd
 *
 */
public interface ITableView extends IDocumentationView {

	int sheet(String name);
	
	/**
	 * Create a table. Depending on implementation, this may be called more than
	 * once. Return the table handle.
	 * 
	 * @param caption
	 * @return
	 */
	int table(String caption, int sheet);

	/**
	 * Header section - top-level
	 * 
	 * @return
	 */
	int header(int table);

	/**
	 * Body section - top-level
	 * 
	 * @return
	 */
	int body(int table);

	/**
	 * Footer section - top-level
	 * 
	 * @return
	 */
	int footer(int table);

//	int newColumn(int table);

	/**
	 * 
	 * @return
	 */
	int newRow(int section);

	/**
	 * Shorthand for newCell(row, section, 1, 1). Cells are created in rows, the 
	 * number isn't checked for match with the number of columns.
	 * 
	 * @param row
	 * @return
	 */
	int newCell(int row);

	int newHeaderCell(int row, boolean rowScope);

	int newCell(int row, int colSpan, int rowSpan);

	/**
	 * 
	 * @param cell
	 * @param content
	 * @param options
	 */
	void write(int cell, Object content, Object... options);

}
