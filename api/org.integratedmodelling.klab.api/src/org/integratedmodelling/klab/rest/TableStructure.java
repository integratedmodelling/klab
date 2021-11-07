package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.provenance.IArtifact;

public class TableStructure {

	public static class Column {

		private boolean header;
		private boolean key;
		private String name;
		private IArtifact.Type type;
		private IArtifact.Type referenceType;
		private String referenceValue;
		private String mapping;

		/**
		 * If true, the cells in this column are row headers.
		 * 
		 * @return
		 */
		public boolean isHeader() {
			return header;
		}

		public void setHeader(boolean header) {
			this.header = header;
		}

		public boolean isKey() {
			return key;
		}

		public void setKey(boolean key) {
			this.key = key;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public IArtifact.Type getType() {
			return type;
		}

		public void setType(IArtifact.Type type) {
			this.type = type;
		}

		/**
		 * The reference value is by default equal to the name (header) and should be
		 * convertible directly into the reference type. E.g. a column may be named
		 * "1990" and have type TimeExtent, or referenceName=agriculture:Peach,
		 * name=Peach and type CONCEPT.
		 * 
		 * @return
		 */
		public String getReferenceValue() {
			return referenceValue;
		}

		public void setReferenceValue(String referenceValue) {
			this.referenceValue = referenceValue;
		}

		/**
		 * Reference type is the type of the indexing object, not of the cell content. A
		 * column named agricultural:Peach (reference type = CONCEPT) may contain the
		 * peach yield (type = NUMBER).
		 * 
		 * @return
		 */
		public IArtifact.Type getReferenceType() {
			return referenceType;
		}

		public void setReferenceType(IArtifact.Type referenceType) {
			this.referenceType = referenceType;
		}

		public String getMapping() {
			return mapping;
		}

		public void setMapping(String mapping) {
			this.mapping = mapping;
		}

	}

	private int rowCount;
	private List<Column> columns = new ArrayList<>();

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

}
