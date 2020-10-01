package org.integratedmodelling.klab.documentation.extensions.table;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.Dimension;

/**
 * Collection of beans for the table Groovy API, included if mentioned to access columns, classifiers and
 * the like from computed expressions.
 * 
 * @author Ferd
 *
 */
public class TableApiObjects {

	public static class TableCell {
		
		private ILocator locator;
		
		public TableCell(TableArtifact table, Dimension column, Dimension row, ILocator locator) {
			this.locator = locator;
		}
		
		public boolean changed(Object from, Object to, Object locateFrom, Object locateTo) {
			return false;
		}
		 
		public Object getProperty(String state) {
			return null;
		}
	}
	
	public static class TableDimension {
		
		private Dimension dimension;
		private IConcept concept;
		
		public TableDimension(Dimension dimension, ILocator locator) {
			this.dimension = dimension;
		}

		public IConcept getConcept() {
			return concept;
		}
		
		public Object getProperty(String state) {
			return null;
		}
		
	}
	
}
