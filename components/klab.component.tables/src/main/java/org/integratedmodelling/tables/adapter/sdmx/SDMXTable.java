package org.integratedmodelling.tables.adapter.sdmx;

import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.tables.AbstractTable;

public class SDMXTable extends AbstractTable<Object> {

    public SDMXTable(IResource resource, IMonitor monitor) {
        super(resource, Object.class, monitor);
    }

    @Override
    public List<Object> getRowItems(Object rowLocator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Object> getColumnItems(Object columnLocator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getItem(Object rowLocator, Object columnLocator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<Iterable<?>> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected AbstractTable<Object> copy() {
        // TODO Auto-generated method stub
        return null;
    }

}
