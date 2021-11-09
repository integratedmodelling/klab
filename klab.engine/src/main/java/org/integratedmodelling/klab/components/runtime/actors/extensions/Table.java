package org.integratedmodelling.klab.components.runtime.actors.extensions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.TableStructure;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.Utils;

import groovy.lang.GroovyObjectSupport;

public class Table extends GroovyObjectSupport {

	String name = "table";
	boolean cache = false;

	/*
	 * if a key column isn't defined in the constructor, it becomes "row" indexing
	 * the string value of the row number.
	 */
	Set<String> keyColumns = Collections.synchronizedSet(new LinkedHashSet<>());
	Set<Object> keyObjects = Collections.synchronizedSet(new LinkedHashSet<>());
	Set<String> columns = Collections.synchronizedSet(new LinkedHashSet<>());
	Map<String, Map<String, Object>> data = Collections.synchronizedMap(new LinkedHashMap<>());
	java.io.File outputDirectory = Configuration.INSTANCE.getExportFile("dummy").getParentFile();
	TableStructure structure = new TableStructure();
	Set<String> cachedKeys = new LinkedHashSet<>();
	boolean cacheLoaded = false;

	public Table() {
	}

	public void initialize() {
		File cf = getStructureFile();
		if (cf.exists()) {
			this.structure = JsonUtils.load(cf, TableStructure.class);
		}

		for (Object o : keyObjects) {
			recordColumn(o, o.toString(), true);
		}

		JsonUtils.printAsJson(this.structure, cf);

	}

	@Override
	public void setProperty(String key, Object value) {
		switch (key) {
		case "key":
			if (value instanceof Collection) {
				for (Object o : ((Collection<?>) value)) {
					keyObjects.add(o);
					keyColumns.add(o.toString());
				}
			} else {
				keyObjects.add(value);
				this.keyColumns.add(value.toString());
			}
			break;
		case "outputdir":
			this.outputDirectory = new java.io.File(value.toString());
			this.outputDirectory.mkdirs();
			break;
		case "cache":
			this.cache = value instanceof Boolean ? (Boolean) value : false;
			break;
		case "name":
			this.name = value.toString();
			break;
		default:
			super.setProperty(key, value);
		}
	}

	@SuppressWarnings("unchecked")
	private void loadCache() {

		java.io.File cf = getCacheFile();
		if (cf.exists()) {
			Map<?, ?> dat = JsonUtils.load(cf, Map.class);
			if (dat != null) {
				this.cachedKeys.addAll(((Collection<String>) dat.get("keys")));
				this.columns.addAll(((Collection<String>) dat.get("columns")));
				this.data.putAll(((Map<String, Map<String, Object>>) dat.get("data")));
			}
		}

		this.cacheLoaded = true;
	}

	public boolean isCached(Object[] keys) {

		if (!cache) {
			return false;
		}

		if (!cacheLoaded) {
			loadCache();
		}

		String key = "";
		for (Object k : keys) {
			key += (key.isEmpty() ? "" : ",") + k;
		}
		return cachedKeys.contains(key);
	}

	public void updatecache(Object[] keys) {

		if (!cacheLoaded) {
			loadCache();
		}

		String key = "";
		for (Object k : keys) {
			key += (key.isEmpty() ? "" : ",") + k;
		}
		cachedKeys.add(key);

		Map<String, Object> cache = new LinkedHashMap<>();
		cache.put("columns", columns);
		cache.put("data", data);
		cache.put("keys", cachedKeys);

		JsonUtils.save(cache, getCacheFile());
	}

	private java.io.File getCacheFile() {
		return new java.io.File(outputDirectory + File.separator + name + ".cache.json");
	}

	private java.io.File getStructureFile() {
		return new java.io.File(outputDirectory + File.separator + name + ".structure.json");
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

		if (this.cache && !this.cacheLoaded) {
			loadCache();
		}

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

		if (Observations.INSTANCE.isData(value)) {
			recordColumn(col, value, false);
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

		if (this.cache && !this.cacheLoaded) {
			loadCache();
		}

		String rowKey = "";
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

		if (Observations.INSTANCE.isData(value)) {
			recordColumn(col, value, false);
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

	private void recordColumn(Object col, Object value, boolean key) {
		if (!columns.contains(col.toString())) {
			TableStructure.Column column = new TableStructure.Column();
			column.setName(col.toString());
			column.setType(Utils.getArtifactType(value.getClass()));
			column.setReferenceType(Utils.getArtifactType(col.getClass()));
			column.setReferenceValue(col instanceof IConcept ? ((IConcept) col).getDefinition() : col.toString());
			column.setKey(key);
			column.setHeader(false);
			this.structure.getColumns().add(column);
			JsonUtils.printAsJson(this.structure, getStructureFile());
		}
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

		if (this.cache && !this.cacheLoaded) {
			loadCache();
		}

		StringWriter sw = new StringWriter();
		PrintWriter printWriter = new PrintWriter(sw);

		printWriter.println(StringUtils.joinCollection(keyColumns, ',') + "," + Utils.join(columns, ","));
		for (String row : data.keySet()) {

			Map<String, Object> values = data.get(row);
			boolean first = true;
			for (String k : keyColumns) {
				Object value = values.get(k);
				printWriter.print((first ? "\"" : ",\"") + (value == null ? "" : value.toString()) + "\"");
				first = false;
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
