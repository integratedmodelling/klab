package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.general.ITable;

public class Table<T> implements ITable<T> {

	private List<T[]> rows;
	private List<String> headers = new ArrayList<>();
	private String name;
	
	public Table(List<T[]> rows, List<String> headers) {
		this.rows = rows;
		if (this.headers == null) {
			for (int i = 0; i < (rows.size() == 0 ? 0 : rows.get(i).length); i++) {
				this.headers.add("$" + (i+1));
			}
		} else {
			this.headers.addAll(headers);
		}
	}
	
	@Override
	public List<String> getHeaders() {
		return this.headers;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return rows.size() == 0 ? 0 : rows.get(0).length;
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
