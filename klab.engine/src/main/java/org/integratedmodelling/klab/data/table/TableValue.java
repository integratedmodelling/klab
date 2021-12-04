package org.integratedmodelling.klab.data.table;

import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * This is the table that gets into states when a IArtifact.Type.TABLE is the representation type in
 * a state. The table is in fact a multiple-key hash with one and only one value column of type T,
 * and is optimized for size and speed of comparison. The keys can be of any type, while T describes
 * the type of the value. Like anything that is in a state, it can be reduced to a single atomic
 * value for display or conventional export. (IReducible interface TBI).
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class TableValue<T> implements ITable<T> {

    @Override
    public Iterator<Iterable<?>> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getDimensions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <E> E get(Class<E> cls, IContextualizationScope scope, Object... locators) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<T> asList(Object... locators) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITable<T> filter(Type target, Object... locators) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITable<T> filter(Filter filter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<T> getRowItems(Object rowLocator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<T> getColumnItems(Object columnLocator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T getItem(Object rowLocator, Object columnLocator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITable<T> collectIndices(List<Integer> indices) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Attribute getColumnDescriptor(String columnName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Attribute getColumnDescriptor(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITable<?> resetFilters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Filter> getFilters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITable<T> contextualize(IContextualizationScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public int[] size() {
		// TODO Auto-generated method stub
		return null;
	}

}
