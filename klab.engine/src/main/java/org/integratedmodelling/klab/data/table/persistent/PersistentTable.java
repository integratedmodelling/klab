package org.integratedmodelling.klab.data.table.persistent;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.general.IPersistentTable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.table.Table.StructureImpl;
import org.integratedmodelling.klab.persistence.h2.H2Database;

public class PersistentTable<K,V> implements IPersistentTable<K,V> {

	static H2Database database = null;
	
	public class PersistentTableBuilder<K, V> extends StructureImpl {

		public PersistentTableBuilder(String id) {
			super(id);
		}

//		@Override
		public IPersistentTable<K, V> build() {
			
			if (database == null) {
				database = H2Database.createPersistent("");
			}
			
			// verify or create table
			if (database.hasTable(getName())) {
				
			} else {
				
			}
			
			return null;
		}

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V retrieve(K id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(K id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(K key, V object, IMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<K> query(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long count(String query) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<V> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
