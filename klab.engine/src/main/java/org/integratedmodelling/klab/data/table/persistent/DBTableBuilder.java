package org.integratedmodelling.klab.data.table.persistent;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.DataType;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Builder;
import org.integratedmodelling.klab.persistence.h2.H2Database;
import org.integratedmodelling.klab.utils.Triple;

public class DBTableBuilder implements Builder<Object> {

	static H2Database database = null;
	
	boolean deleteIfInconsistent = false;
	String id;
	List<Triple<String, DataType, Boolean>>	 columns = new ArrayList<>();
	
	public DBTableBuilder(String id) {
		this.id = id;
	}

	@Override
	public Builder<Object> deleteIfInconsistent() {
		this.deleteIfInconsistent = true;
		return this;
	}

	@Override
	public Builder<Object> column(String name, DataType type, boolean index) {
		this.columns.add(new Triple<>(name, type, index));
		return this;
	}

	@Override
	public ITable<Object> build() {
		
		if (database == null) {
			database = H2Database.createPersistent("");
		}
		
		// verify or create table
		if (database.hasTable(this.id)) {
			
		} else {
			
		}
		
		return new DBTable(database, this.id);
	}

}
