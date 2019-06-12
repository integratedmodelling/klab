package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.data.classification.Classifier;

public class Table<T> implements ITable<T> {

	private List<T[]> rows;
	private List<String> columnHeaders = new ArrayList<>();
	private List<String> rowHeaders = null;
	private String name;
	private Set<IKimExpression> expressions;
	
	public static Table<IClassifier> create(IKimTable table) {

		List<String> headers = table.getHeaders();
		List<IClassifier[]> rows = new ArrayList<>();
		Set<IKimExpression> exprs = new HashSet<>();

		for (int i = 0; i < table.getRowCount(); i++) {
			IClassifier[] row = new IClassifier[table.getColumnCount()];
			int y = 0;
			for (IKimClassifier element : table.getRow(i)) {
				row[y] = new Classifier(element);
				if (element.getExpressionMatch() != null) {
					exprs.add(element.getExpressionMatch());
				}
				y++;
			}
			rows.add(row);
		}
		
		Table<IClassifier> ret = new Table<>(rows, headers);
		ret.expressions = exprs;
		return ret;
	}
	
	private Table(List<T[]> rows, List<String> headers) {
		this.rows = rows;
		if (this.columnHeaders == null) {
			for (int i = 0; i < (rows.size() == 0 ? 0 : rows.get(i).length); i++) {
				this.columnHeaders.add("$" + (i+1));
			}
		} else {
			this.columnHeaders.addAll(headers);
		}
	}
	
	/**
	 * Set a value. Based on the locators passed and whether they are already in the
	 * table or not, expand the table and set the headers if necessary.
	 * 
	 * @param rowLocator
	 * @param columnLocator
	 * @param value
	 */
	public void set(Object rowLocator, Object columnLocator, T value) {
		// TODO
	}
	
	
	@Override
	public List<String> getColumnHeaders() {
		return this.columnHeaders;
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

	public List<T[]> getRows() {
		return rows;
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
		return rows.get(rowIndex);
	}

	@Override
	public T[] getColumn(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRowHeaders() {
		if (rowHeaders == null) {
			rowHeaders = new ArrayList<>();
			for (int i = 0; i < getRowCount(); i++) {
				rowHeaders.add("row"+(i+1));
			}
		}
		return rowHeaders;
	}

	public Set<IKimExpression> getExpressions() {
		return expressions;
	}
	
}
