package org.integratedmodelling.klab.data.table.persistent;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.persistence.h2.H2Database;

/**
 * A table backed by a persistent SQL database, allowing spatial and temporal
 * queries along with automatic and transparent commit/rollback.
 * 
 * @author ferdinando.villa
 *
 */
public class DBTable implements ITable<Object> {
	
	H2Database database;
	String tableName;

	DBTable(H2Database database, String tableName) {
		this.database = database;
	}

	/**
	 * Get a builder to create a table or validate an existing one with the same ID.
	 * 
	 * @param ID
	 * @param structure
	 * @return a previously existing or new table.
	 */
	public static Builder<Object> builder(String id) {
		return new DBTableBuilder(id);
	}

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
	public Map<String, Object> map(int keyColumnIndex, int valueColumnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getRowAsMap(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getRow(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getColumnHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRowHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> getRows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Column<Object>> getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

}
