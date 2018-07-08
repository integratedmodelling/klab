package org.integratedmodelling.klab.data.table;

import java.util.Map;

import org.integratedmodelling.klab.api.data.general.ITable;

public class Table<T> implements ITable<T> {

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Map<String, T> map(int keyColumnIndex, int valueColumnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, T> getRowAsMap(int rowIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T[] getRow(int rowIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T[] getColumn(int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }
}
