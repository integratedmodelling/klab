package org.integratedmodelling.tables.adapter.sdmx;

import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.tables.AbstractTable;
import org.integratedmodelling.tables.DimensionScanner.Dimension;

public class SDMXTable extends AbstractTable<Object> {

    public SDMXTable(IResource resource, IMonitor monitor) {
        super(resource, resource.getUrn().replaceAll(":", "_"), Object.class, monitor);
    }
    
    public SDMXTable(SDMXTable sdmxTable) {
    	super(sdmxTable);
	}

	@Override
    public List<Object> getRowItems(Object... rowLocator) {
        return getCache(resource).query(Dimension.ROW, rowLocator);
    }

    @Override
    public List<Object> getColumnItems(Object... columnLocator) {
        return getCache(resource).query(Dimension.COLUMN, columnLocator);
    }

    @Override
    public Object getItem(Object rowLocator, Object columnLocator) {
        return getCache(resource).query(rowLocator, columnLocator);
    }

    @Override
    public Iterator<Iterable<?>> iterator() {
    	return getCache(resource).iterator();
    }

    @Override
    protected AbstractTable<Object> copy() {
        return new SDMXTable(this);
    }

    
    
}
