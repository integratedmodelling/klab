package org.integratedmodelling.klab.data.table;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;

public class Table implements ITable {

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
	public Map map(int keyColumnIndex, int valueColumnIndex) throws KlabIOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getRowAsMap(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getRow(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getColumn(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List lookup(int columnId, Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List lookup(String columnId, IExpression match, Map parameters, IMonitor monitor) throws KlabException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List lookup(IExpression expression) {
		// TODO Auto-generated method stub
		return null;
	}

}
