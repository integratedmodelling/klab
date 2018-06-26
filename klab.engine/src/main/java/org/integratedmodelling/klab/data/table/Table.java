package org.integratedmodelling.klab.data.table;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

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

	@Override
	public List<Object> lookup(int columnId, Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> lookup(String columnId, IExpression match, IParameters<String> parameters, IMonitor monitor)
			throws KlabException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> lookup(IExpression expression) {
		// TODO Auto-generated method stub
		return null;
	}
}
