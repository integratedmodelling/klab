package org.integratedmodelling.klab.data.table.persistent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.integratedmodelling.klab.api.data.DataType;
import org.integratedmodelling.klab.api.data.general.IPersistentTable;
import org.integratedmodelling.klab.api.data.general.IStructuredTable.Structure.Field;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.table.StructuredTable.StructureImpl;
import org.integratedmodelling.klab.persistence.h2.H2Database;
import org.integratedmodelling.klab.persistence.h2.H2Database.DBIterator;
import org.integratedmodelling.klab.persistence.h2.SQL;

public class PersistentTable<K, V> implements IPersistentTable<K, V> {

	H2Database database;
	private Structure structure;
	DataType keyType = null;

	Function<V, Map<String, Object>> serializer;
	Function<Map<String, Object>, V> deserializer;
	Function<V, K> keyGenerator;

	public static class PersistentTableBuilder<K, V> extends StructureImpl {

		DataType keyType = null;

		public PersistentTableBuilder(String id, Class<? extends K> keyClass, Class<? extends V> valueClass) {
			super(id);
			keyType = DataType.forClass(keyClass);
			if (keyType == null) {
				throw new IllegalArgumentException(
						"cannot build a persistent table with key type = " + keyClass.getCanonicalName());
			}
		}

		H2Database database = null;

		public PersistentTableBuilder<K, V> in(H2Database database) {
			this.database = database;
			return this;
		}

		@Override
		public PersistentTableBuilder<K, V> column(String name, DataType type, Object... parameters) {
			super.column(name, type, parameters);
			return this;
		}

		public PersistentTable<K, V> build(Function<V, Map<String, Object>> serializer,
				Function<Map<String, Object>, V> deserializer, Function<V, K> keyGenerator) {

			if (database == null) {
				database = H2Database.createPersistent(getName());
			}

			// verify or create table
			if (database.hasTable(getName())) {

				// TODO verify

			} else {

				String sql = "CREATE TABLE " + getName() + " (\n   oid " + SQL.sqlTypes.get(keyType)
						+ (keyType == DataType.TEXT ? "(1024)" : "");
				for (Field column : columns) {
					sql += ",\n   " + column.getName() + " " + SQL.sqlTypes.get(column.getDataType());
					if (column.getWidth() > 0) {
						sql += "(" + column.getWidth() + ")";
					}
				}
				sql += ");\nCREATE INDEX oid_index ON " + getName() + "(oid);";
				for (Field column : columns) {
					if (column.isIndex()) {
						sql += "\nCREATE " + (column.getDataType() == DataType.SHAPE ? "SPATIAL" : "") + " INDEX "
								+ column.getName() + "_index ON " + getName() + "(" + column.getName() + ");";
					}
				}

				database.execute(sql);
			}

			return new PersistentTable<K, V>(database, this, serializer, deserializer, keyGenerator);
		}

	}

	public PersistentTable(H2Database database, Structure structure, Function<V, Map<String, Object>> serializer,
			Function<Map<String, Object>, V> deserializer, Function<V, K> keyGenerator) {
		this.database = database;
		this.structure = structure;
		this.serializer = serializer;
		this.deserializer = deserializer;
		this.keyGenerator = keyGenerator;
	}

	@Override
	public String getName() {
		return structure.getName();
	}

	@Override
	public int getRowCount() {
		return (int) count();
	}

	@Override
	public int getColumnCount() {
		return structure.getColumnCount();
	}

	@Override
	public List<Column<V>> getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, V> map(int keyColumnIndex, int valueColumnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, V> getRowAsMap(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V[] getRow(int rowIndex) {
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
	public List<V[]> getRows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K store(V object, IMonitor monitor) {

		Map<String, Object> data = serializer.apply(object);

		String sql = "INSERT INTO " + getName() + " VALUES (" + SQL.wrapPOD(keyGenerator.apply(object));
		for (Field column : structure.getColumns()) {
			sql += ", " + SQL.wrapPOD(data.get(column.getName()));
		}
		sql += ");";
		database.execute(sql);
		return keyGenerator.apply(object);
	}

	@Override
	public V retrieve(K id) {

		List<V> ret = new ArrayList<>();
		String query = "SELECT * FROM " + getName() + " WHERE oid = " + SQL.wrapPOD(id) + ";";

		database.query(query, new SQL.SimpleResultHandler() {
			@Override
			public void onRow(ResultSet rs) {
				try {
					Map<String, Object> data = new HashMap<>();
					for (Field column : structure.getColumns()) {
						data.put(column.getName(), rs.getObject(column.getName()));
					}
					ret.add(deserializer.apply(data));
				} catch (SQLException e) {
				}
			}
		});

		return ret.isEmpty() ? null : ret.get(0);
	}

	@Override
	public boolean delete(K id) {
		database.execute("DELETE FROM " + getName() + " WHERE oid = " + SQL.wrapPOD(id) + ";");
		return true;
	}

	@Override
	public boolean update(V object, IMonitor monitor) {
		delete(keyGenerator.apply(object));
		store(object, monitor);
		return false;
	}

	@Override
	public Iterable<V> query(String query) {

		List<V> ret = new ArrayList<>();
//		if (!database.hasTable(getName())) {
//			return ret;
//		}
		database.query(query, new SQL.SimpleResultHandler() {
			@Override
			public void onRow(ResultSet rs) {
				try {
					Map<String, Object> data = new HashMap<>();
					for (Field column : structure.getColumns()) {
						data.put(column.getName(), rs.getObject(column.getName()));
					}
					ret.add(deserializer.apply(data));
				} catch (SQLException e) {
				}
			}
		});
		return ret;
	}

	@Override
	public long count() {
		return count("SELECT COUNT(*) from " + getName() + ";");
	}

	@Override
	public long count(String query) {

		if (database == null || !database.hasTable(getName())) {
			return 0;
		}

		/*
		 * ch'agg'a fa'.
		 */
		final List<Long> ret = new ArrayList<>();
		database.query(query, new SQL.SimpleResultHandler() {
			@Override
			public void onRow(ResultSet rs) {
				try {
					ret.add(rs.getLong(1));
				} catch (SQLException e) {
				}
			}
		});

		return ret.size() > 0 ? ret.get(0) : 0l;
	}
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<V> iterator() {
		return new TableIterator(database.query("SELECT * from " + getName() + ";"));
	}

	class TableIterator implements Iterator<V> {

		DBIterator it;
		
		TableIterator(DBIterator it) {
			this.it = it;
		}
		
		@Override
		public boolean hasNext() {
			return !it.finished;
		}

		@Override
		public V next() {
			Map<String, Object> data = new HashMap<>();
			for (Field column : structure.getColumns()) {
				try {
					data.put(column.getName(), it.result.getObject(column.getName()));
				} catch (SQLException e) {
					// fock
				}
			}
			V ret = deserializer.apply(data);
			it.advance();
			return ret;
		}
		
	}
	
	public H2Database getDatabase() {
		return database;
	}

}
