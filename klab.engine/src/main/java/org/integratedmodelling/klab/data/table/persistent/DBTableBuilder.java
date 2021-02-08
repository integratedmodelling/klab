//package org.integratedmodelling.klab.data.table.persistent;
//
//import org.integratedmodelling.klab.api.data.general.ITable;
//import org.integratedmodelling.klab.api.data.general.ITable.Builder;
//import org.integratedmodelling.klab.data.table.Table.StructureImpl;
//import org.integratedmodelling.klab.persistence.h2.H2Database;
//
//public class DBTableBuilder extends StructureImpl implements Builder<Object> {
//
//	public DBTableBuilder(String id) {
//		super(id);
//	}
//
//	static H2Database database = null;
//	
//	boolean deleteIfInconsistent = false;
//
//	@Override
//	public ITable<Object> build() {
//		
//		if (database == null) {
//			database = H2Database.createPersistent("");
//		}
//		
//		// verify or create table
//		if (database.hasTable(getName())) {
//			
//		} else {
//			
//		}
//		
//		return new DBTable(database, getName());
//	}
//
//
//	@Override
//	public Builder<Object> deleteIfInconsistent() {
//		this.deleteIfInconsistent = true;
//		return this;
//	}
//}
