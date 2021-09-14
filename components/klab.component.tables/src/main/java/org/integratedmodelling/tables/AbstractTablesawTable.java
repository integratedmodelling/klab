package org.integratedmodelling.tables;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;

import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

public abstract class AbstractTablesawTable<T> extends AbstractTable<T> {

    protected AbstractTablesawTable(AbstractTable<T> table) {
        super(table);
    }
    
    public AbstractTablesawTable(IResource resource, Class<? extends T> cls, IMonitor monitor) {
        super(resource, cls, monitor);
    }

    /**
     * Make sure the table is cached as a member
     * 
     * @return
     */
    abstract Table getTable();
    
    abstract int getRowIndex(Object rowLocator);

    abstract int getColumnIndex(Object rowLocator);

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getRowItems(Object rowLocator) {
        List<T> ret = new ArrayList<>();
        Row row = getTable().row(getRowIndex(rowLocator));
        for (int i = 0; i < row.columnCount(); i++) {
            ret.add((T)row.getObject(i));
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getColumnItems(Object columnLocator) {
        List<T> ret = new ArrayList<>();
        Column<?> column = getTable().column(getColumnIndex(columnLocator));
        for (int i = 0; i < column.size(); i++) {
            ret.add((T)column.get(i));
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getItem(Object rowLocator, Object columnLocator) {
        Column<?> column = getTable().column(getColumnIndex(columnLocator));
        return (T)column.get(getRowIndex(rowLocator));
    }

    @Override
    public Iterator<Iterable<?>> iterator() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AbstractTable<T> copy() {
        try {
            return this.getClass().getDeclaredConstructor(this.getClass()).newInstance(this);
        } catch (Throwable e) {
            throw new KlabInternalErrorException(e);
        }
    }

}
