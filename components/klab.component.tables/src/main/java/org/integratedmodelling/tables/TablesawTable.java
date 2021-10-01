package org.integratedmodelling.tables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.rest.AttributeReference;

import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

public class TablesawTable extends AbstractTable<Object> {

    Table table;
    
    public TablesawTable(Table table, IMonitor monitor) {
        super(getAttributes(table), Object.class, monitor);
        this.table = table;
    }

    public static List<Attribute> getAttributes(Table table) {
        List<Attribute> ret = new ArrayList<>();
        for (int i = 0; i < table.columnCount(); i++) {
            Column<?> column = table.column(i);
            AttributeReference attribute = new AttributeReference();
            attribute.setIndex(i);
            attribute.setKey(false);
            attribute.setName(column.name());
            attribute.setOptional(true);
            attribute.setType(getColumnType(column));
            ret.add(attribute);
        }
        return ret;
    }

    private static Type getColumnType(Column<?> column) {
        if (column instanceof StringColumn) {
            return Type.TEXT;
        } // TODO date, time, datetime, etc
        return Type.NUMBER;
    }
    
    @Override
    public List<Object> getRowItems(Object rowLocator) {
        List<Object> ret = new ArrayList<>();
        Row row = table.row(getRowIndex(rowLocator));
        for (int i = 0; i < row.columnCount(); i++) {
            ret.add((T)row.getObject(i));
        }
        return ret;
    }

    private int getRowIndex(Object rowLocator) {
        if (rowLocator instanceof Integer) {
            return (Integer)rowLocator;
        }
        throw new KlabIllegalArgumentException("not a table row locator: " + rowLocator);
    }
    
    private int getColumnIndex(Object rowLocator) {
        if (rowLocator instanceof Integer) {
            return (Integer)rowLocator;
        }
        Attribute attribute = getColumnDescriptor(rowLocator.toString());
        if (attribute != null) {
            return attribute.getIndex();
        }
        throw new KlabIllegalArgumentException("not a table column locator: " + rowLocator);
    }

    @Override
    public List<Object> getColumnItems(Object columnLocator) {
        List<Object> ret = new ArrayList<>();
        Column<?> column = table.column(getColumnIndex(columnLocator));
        for (int i = 0; i < column.size(); i++) {
            ret.add(column.get(i));
        }
        return ret;
    }

    @Override
    public Object getItem(Object rowLocator, Object columnLocator) {
        Column<?> column = table.column(getColumnIndex(columnLocator));
        return column.get(getRowIndex(rowLocator));
    }

    @Override
    public Iterator<Iterable<?>> iterator() {
        return new Iterator<Iterable<?>>() {

            int row = 0;
            
            @Override
            public boolean hasNext() {
                return this.row < table.rowCount();
            }

            @Override
            public Iterable<?> next() {
                final Row row = table.row(this.row++);
                return new Iterable<Object>() {
                    @Override
                    public Iterator<Object> iterator() {
                        return new Iterator<Object>() {

                            int idx = 0;
                            
                            @Override
                            public boolean hasNext() {
                                return idx < row.columnCount();
                            }

                            @Override
                            public Object next() {
                                return row.getObject(idx++);
                            }
                        };
                    }
                };
            }
        };
    }

    @Override
    protected AbstractTable<Object> copy() {
        return new TablesawTable(this.table, this.monitor);
    }


}
