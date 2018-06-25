/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.data.general;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;

/**
 * Unified table interface for both in-memory and persistent tables read from
 * disk or initialized in k.IM. Works as a regular table and has lookup
 * functions.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface ITable<T> {

	/**
	 * Table name. E.g. a sheet name in Excel. Tables with no asserted names should
	 * have a sensible placeholder here.
	 *
	 * @return the table name.
	 */
	String getName();

	/**
	 * Number of rows.
	 * 
	 * @return the number of rows
	 */
	int getRowCount();

	/**
	 * Number of columns.
	 * 
	 * @return the number of columns
	 */
	int getColumnCount();

	/**
	 * Return a map between the values in one column and the values in another. This
	 * is meant to be persisted and cached appropriately - meaning it will be slow
	 * the first time a specific mapping is asked for, and very fast afterwards
	 * unless the file changes, and in normal situations it should not even load the
	 * file at all. It should be implemented so that very large data tables can be
	 * handled efficiently.
	 *
	 * The key is always a string representation to avoid errors due to not knowing
	 * what the internal DB representation was for the column. use
	 * sanitizeKey(Object) to apply some default sanitization to a non-string key -
	 * e.g. converting a double w/o decimals into an integer.
	 *
	 * @param keyColumnName
	 *            a {@link java.lang.String} object.
	 * @param valueColumnName
	 *            a {@link java.lang.String} object.
	 * @return a map between values and names in matching columns.
	 * @throws org.integratedmodelling.klab.exceptions.KlabIOException
	 */
	Map<String, T> map(int keyColumnIndex, int valueColumnIndex) throws KlabIOException;

	/**
	 * Get the row as a keyed map, assuming the table has headers. If not, names
	 * such as "$n" are used.
	 * 
	 * @param rowIndex
	 * @return the row as a map
	 */
	Map<String, T> getRowAsMap(int rowIndex);

	/**
	 * Get the contents of indexed row.
	 * 
	 * @param rowIndex
	 * @return row data
	 */
	T[] getRow(int rowIndex);
	
	
	/**
	 * Get the content of the passed column. Should be used after checking that
	 * the column size is acceptable.
	 * 
	 * @param columnIndex
	 * @return column data
	 */
	T[] getColumn(int columnIndex);

	/**
	 * Lookup values in columnIndex based on matching the other values to the other
	 * columns. If other values are expressions, run them with the value of each
	 * column ID (or "$n" if columns are unnamed) in the current row as parameters.
	 * Values that are not expressions are matched to columns in left to right
	 * order, skipping the requested result column.
	 *
	 * @param columnId
	 *            the index of the column we want returned
	 * @param values
	 *            values or expressions to be matched to the other columns, left to
	 *            right.
	 * @return all the objects matching the values.
	 */
	List<Object> lookup(int columnId, Object... values);

	/**
	 * Lookup values in columnIndex based on matching the other values. If other
	 * values are expressions, run them with the value of each column ID in the
	 * current row as parameters. Values that are not expressions are matched to
	 * columns in left to right order, skipping the requested result column.
	 *
	 * @param columnId
	 *            the ID of the column we want returned
	 * @param match
	 *            a
	 *            {@link org.integratedmodelling.klab.api.data.general.IExpression}
	 *            object.
	 * @param parameters
	 *            additional parameters for the expression evaluation.
	 * @param monitor
	 *            a
	 *            {@link org.integratedmodelling.klab.api.runtime.monitoring.IMonitor}
	 *            object.
	 * @return all objects matching the expression once it's run with the passed
	 *         parameters and row values.
	 * @throws org.integratedmodelling.klab.exceptions.KlabException
	 */
	List<Object> lookup(String columnId, IExpression match, Map<String, Object> parameters, IMonitor monitor)
			throws KlabException;

	/**
	 * Return all the rows that match IExpression, which may use any of the column
	 * header IDs.
	 *
	 * @param expression
	 *            a
	 *            {@link org.integratedmodelling.klab.api.data.general.IExpression}
	 *            object.
	 * @return all matching rows
	 */
	List<Map<String, Object>> lookup(IExpression expression);

}
