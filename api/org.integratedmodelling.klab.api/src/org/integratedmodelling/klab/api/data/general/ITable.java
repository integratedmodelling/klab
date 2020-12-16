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

import org.integratedmodelling.klab.api.data.DataType;

/**
 * Unified table interface for both in-memory and persistent tables read from
 * disk or initialized in k.IM. Works as a regular table and has lookup
 * functions.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface ITable<T> {

//	/**
//	 * 
//	 * @author Ferd
//	 *
//	 */
//	interface ObjectTable extends ITable<Object> {
//	}

	interface Structure {

		public interface Field {

			String getName();

			DataType getDataType();

			int getWidth();

			boolean isIndex();

		}
		
		String getName();

		/**
		 * Define a column. The optional parameters can be used for further
		 * specification.
		 * 
		 * @param name
		 * @param type
		 * @param parameters a Boolean will be interpreted as whether to build an index
		 *                   or not. An integer is a lenght field used for varchars or the
		 *                   like.
		 * @return
		 */
		Structure column(String name, DataType type, Object... parameters);
		
		int getColumnCount();

		List<Field> getColumns();

	}

	interface Builder<T> extends Structure {

		Structure deleteIfInconsistent();

		ITable<T> build();

	}

	interface Row<T> {

		/**
		 * Row name is "#n" unless the table has been initialized with explicit row
		 * names.
		 * 
		 * @return
		 */
		String getName();

		/**
		 * @return number of rows with values in column.
		 */
		int getValueCount();

		/**
		 * @return all values.
		 */
		Iterable<T> getValues();

		/**
		 * Get the value as a suitable subclass of T, throwing unchecked exceptions if
		 * called with wrong arguments.
		 * 
		 * @param <K>
		 * @param index
		 * @param cls
		 * @return
		 */
		<K extends T> K getValue(int index, Class<K> cls);

		/**
		 * Get the value as a suitable subclass of T, throwing unchecked exceptions if
		 * called with wrong arguments.
		 * 
		 * @param <K>
		 * @param index
		 * @param cls
		 * @return
		 */
		<K extends T> K getValue(String columnName, Class<K> cls);

	}

	interface Column<T> {

		/**
		 * Column name is "$n" unless the table has been initialized with explicit
		 * column names.
		 * 
		 * @return
		 */
		String getName();

		/**
		 * @return number of rows with values in column.
		 */
		int getValueCount();

		/**
		 * @return all values.
		 */
		Iterable<T> getValues();

		/**
		 * Get the value as a suitable subclass of T, throwing unchecked exceptions if
		 * called with wrong arguments.
		 * 
		 * @param <K>
		 * @param index
		 * @param cls
		 * @return
		 */
		<K extends T> K getValue(long index, Class<K> cls);

	}

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
	 * The columns themselves.
	 * 
	 * @return
	 */
	List<Column<T>> getColumns();

	/**
	 * Return a map between the values in one column and the values in another. This
	 * is meant to be persisted and cached appropriately - meaning it will be slow
	 * the first time a specific mapping is asked for, and very fast afterwards
	 * unless the file changes, and in normal situations it should not even load the
	 * file at all. It should be implemented so that very large data tables can be
	 * handled efficiently.
	 *
	 * @param keyColumnName   a {@link java.lang.String} object.
	 * @param valueColumnName a {@link java.lang.String} object.
	 * @return a map between values and names in matching columns.
	 * @throws org.integratedmodelling.klab.exceptions.KlabIOException
	 */
	Map<String, T> map(int keyColumnIndex, int valueColumnIndex);

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
	 * This may return a list of header names or a list of variables like "$1" if
	 * headers were not defined.
	 * 
	 * @return headers. Never null.
	 */
	List<String> getColumnHeaders();

	/**
	 * This may return a list of header names or a list of variables like "row1" if
	 * headers were not defined.
	 * 
	 * @return headers. Never null.
	 */
	List<String> getRowHeaders();

	/**
	 * Return all rows as a list.
	 * 
	 * @return
	 */
	List<T[]> getRows();

}
