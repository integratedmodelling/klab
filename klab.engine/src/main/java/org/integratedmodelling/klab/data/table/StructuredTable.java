package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.klab.api.data.DataType;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.general.IStructuredTable;
import org.integratedmodelling.klab.data.classification.Classifier;

public class StructuredTable<T> implements IStructuredTable<T> {

	private List<T[]> rows;
	private List<String> columnHeaders = new ArrayList<>();
	private List<String> rowHeaders = null;
	private String name;
	private Set<IKimExpression> expressions;

	public static class StructureImpl implements Structure {

		static public class ColumnImpl implements Field {

			private String name;
			private DataType dataType;
			private int width = -1;
			private boolean index;

			public ColumnImpl(String name, DataType type) {
				this.name = name;
				this.dataType = type;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public DataType getDataType() {
				return dataType;
			}

			@Override
			public int getWidth() {
				return width;
			}

			@Override
			public boolean isIndex() {
				return index;
			}

			
		}
		
		protected String name;
		protected List<Field> columns = new ArrayList<>();

		protected StructureImpl(String id) {
			this.name = id;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public StructureImpl column(String name, DataType type, Object...parameters) {
			ColumnImpl column = new ColumnImpl(name, type);
			if (parameters != null) {
				for (Object o : parameters) {
					if (o instanceof Boolean) {
						column.index = (Boolean)o;
					} else if (o instanceof Integer) {
						column.width = (Integer)o;
					}
				}
			}
			this.columns.add(column);
			return this;
		}

		@Override
		public int getColumnCount() {
			return columns.size();
		}

		@Override
		public List<Field> getColumns() {
			return columns;
		}

	}

	public static Structure structure(String name) {
		return new StructureImpl(name);
	}

	public static StructuredTable<IClassifier> create(IKimTable table) {

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

		StructuredTable<IClassifier> ret = new StructuredTable<>(rows, headers);
		ret.expressions = exprs;
		return ret;
	}

	private StructuredTable(List<T[]> rows, List<String> headers) {
		this.rows = rows;
		if (this.columnHeaders == null) {
			for (int i = 0; i < (rows.size() == 0 ? 0 : rows.get(i).length); i++) {
				this.columnHeaders.add("$" + (i + 1));
			}
		} else if (headers != null) {
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

	@Override
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
	public List<String> getRowHeaders() {
		if (rowHeaders == null) {
			rowHeaders = new ArrayList<>();
			for (int i = 0; i < getRowCount(); i++) {
				rowHeaders.add("row" + (i + 1));
			}
		}
		return rowHeaders;
	}

	public Set<IKimExpression> getExpressions() {
		return expressions;
	}

	@Override
	public List<Column<T>> getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

}
