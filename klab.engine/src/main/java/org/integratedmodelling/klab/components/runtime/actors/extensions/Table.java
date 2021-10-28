package org.integratedmodelling.klab.components.runtime.actors.extensions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.Utils;

import groovy.lang.GroovyObjectSupport;

public class Table extends GroovyObjectSupport {

	/*
	 * if a key column isn't defined in the constructor, it becomes "row" indexing
	 * the string value of the row number.
	 */
	Set<String> keyColumns = new LinkedHashSet<>();
	Set<String> columns = new LinkedHashSet<>();
	// data are added by "row" using the primary key, and are filled in at each
	// add() by column,
	// defining the
	// columns as we go.
	Map<String, Map<String, Object>> data = new LinkedHashMap<>();

	public Table() {
	}

	@Override
	public void setProperty(String key, Object value) {
		switch (key) {
		case "key":
			if (value instanceof Collection) {
				for (Object o : ((Collection<?>) value)) {
					keyColumns.add(o.toString());
				}
			} else {
				this.keyColumns.add(value.toString());
			}
			break;
		default:
			super.setProperty(key, value);
		}
	}

	/**
	 * Set a value into a column using a syntax like set(val, key=xxx, column=xxx).
	 * The associated table must contain a key for each of the keys configured,
	 * identifying the row (either a row number or a row key) and a column
	 * identifier to choose the cell to set into.
	 * 
	 * @param objects
	 */
	public void set(Object value, Map<String, Object> keys) {

		String rowKey = "";
		Map<String, Object> keysig = new LinkedHashMap<>();
		for (String key : keyColumns) {
			if (!keys.containsKey(key)) {
				throw new KlabValidationException("table: need to pass all keys and a column to add a value");
			}
			Object kvalue = keys.get(key);
			rowKey += (rowKey.isEmpty() ? "" : ",") + kvalue.toString();
			keys.put(key, kvalue);
		}

		Object col = keys.get("column");
		if (!keys.containsKey("column")) {
			throw new KlabValidationException("table: column can't be null in add");
		}

		if (col == null) {
			return;
		}

		Map<String, Object> row = data.get(rowKey);
		if (row == null) {
			row = new HashMap<>();
			for (String key : keysig.keySet()) {
				keysig.put(key, keysig.get(key));
			}
			data.put(rowKey, row);
		}

		row.put(col.toString(), value);
		columns.add(col.toString());
	}

	/**
	 * Same as set, but if there is a numeric value in the column already and we get
	 * a valid number, add to it instead of substituting it.
	 * 
	 * @param value
	 * @param keys
	 */
	public void add(Object value, Map<String, Object> keys) {

		String rowKey = "";
		Map<String, Object> keysig = new LinkedHashMap<>();
		for (String key : keyColumns) {
			if (!keys.containsKey(key)) {
				throw new KlabValidationException("table: need to pass all keys and a column to add a value");
			}
			Object kvalue = keys.get(key);
			rowKey += (rowKey.isEmpty() ? "" : ",") + kvalue.toString();
		}

		Object col = keys.get("column");
		if (!keys.containsKey("column")) {
			throw new KlabValidationException("table: column can't be null in add");
		}

		if (col == null) {
			return;
		}

		Map<String, Object> row = data.get(rowKey);
		if (row == null) {
			row = new HashMap<>();
			for (String key : keyColumns) {
				row.put(key, keys.get(key));
			}
			data.put(rowKey, row);
		}

		Object existing = row.get(col.toString());
		if (value instanceof Number && Observations.INSTANCE.isData(value)) {
			value = (Observations.INSTANCE.isData(existing) && existing instanceof Number)
					? ((Number) existing).doubleValue() + ((Number) value).doubleValue()
					: value;
		}

		row.put(col.toString(), value);
		columns.add(col.toString());
	}

	/**
	 * Export as CSV. If the file has a different extension than .csv, we don't care
	 * and output a CSV in it anyway. Tomorrow we may have a format option and be
	 * smarter about extensions.
	 * 
	 * @param filename
	 */
	public void export(String filename) {

		java.io.File output = Configuration.INSTANCE.getExportFile(filename);
		try (OutputStream out = new FileOutputStream(output)) {
			out.write(getCSV().getBytes());
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
	}
	
	public String toString() {
		return getCSV();
	}

	public String getCSV() {

		StringWriter sw = new StringWriter();
		PrintWriter printWriter = new PrintWriter(sw);
		
		printWriter.println(StringUtils.joinCollection(keyColumns, ',') + "," + Utils.join(columns, ","));
		for (String row : data.keySet()) {

			Map<String, Object> values = data.get(row);
			boolean first = true;
			for (String k : keyColumns) {
				Object value = values.get(k);
				printWriter.print((first ? "\"" : ",\"") + (value == null ? "" : value.toString()) + "\"");
			}
			for (String k : columns) {
				if (keyColumns.contains(k)) {
					continue;
				}
				Object value = values.get(k);
				printWriter.print(",\"" + (value == null ? "" : value.toString()) + "\"");
			}
			printWriter.println();
		}
		return sw.toString();
	}

}
