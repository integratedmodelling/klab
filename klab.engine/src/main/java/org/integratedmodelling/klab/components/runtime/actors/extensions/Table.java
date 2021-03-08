package org.integratedmodelling.klab.components.runtime.actors.extensions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Utils;

import groovy.lang.GroovyObjectSupport;

public class Table extends GroovyObjectSupport {

    /*
     * if a key column isn't defined in the constructor, it becomes "row" indexing the string value
     * of the row number.
     */
    String keyColumn = "row";
    Set<String> columns = new LinkedHashSet<>();
    // data are added by "row" using the primary key, and are filled in at each add() by column,
    // defining the
    // columns as we go.
    Map<String, Map<String, Object>> data = new LinkedHashMap<>();

    public Table() {

    }

    @Override
    public void setProperty(String key, Object value) {
        switch(key) {
        case "key":
            this.keyColumn = value.toString();
            break;
        default:
            super.setProperty(key, value);
        }
    }

    /**
     * Add a value to a column using a syntax like add(val, key=xxx, column=xxx). The associated
     * table must contain a key for the row (either a row number or a row key) and a column
     * identifier.
     * 
     * @param objects
     */
    public void add(Object value, Map<String, Object> keys) {
        if (!(keys.containsKey("key") || keys.containsKey(keyColumn)) || !keys.containsKey("column")) {
            throw new KlabValidationException("table: need to pass a key and a column to add a value");
        }
        Object key = keys.get("key");
        if (key == null) {
            key = keys.get(keyColumn);
        }
        if (key == null) {
            throw new KlabValidationException("table: key can't be null in add");
        }
        Object col = keys.get("column");
        if (col == null) {
            throw new KlabValidationException("table: column can't be null in add");
        }
        Map<String, Object> row = data.get(key.toString());
        if (row == null) {
            row = new HashMap<>();
            data.put(key.toString(), row);
        }
        row.put(col.toString(), value);
        columns.add(col.toString());
    }

    /**
     * Export as CSV. If the file has a different extension than .csv, we don't care and output a
     * CSV in it anyway. Tomorrow we may have a format option and be smarter about extensions.
     * 
     * @param filename
     */
    public void export(String filename) {

        java.io.File output = Configuration.INSTANCE.getExportFile(filename);
        try (FileWriter fileWriter = new FileWriter(output.toString()); PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(keyColumn + "," + Utils.join(columns, ","));
            for (String row : data.keySet()) {
                printWriter.print("\"" + row + "\"");
                Map<String, Object> values = data.get(row);
                for (String k : columns) {
                    Object value = values.get(k);
                    printWriter.print(",\"" + (value == null ? "" : value.toString()) + "\"");
                }
                printWriter.println();
            }
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

}
