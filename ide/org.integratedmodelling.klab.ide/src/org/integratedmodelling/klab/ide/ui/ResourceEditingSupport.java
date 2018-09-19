package org.integratedmodelling.klab.ide.ui;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class ResourceEditingSupport {

	public static class PropertySupport extends EditingSupport {

		private final TableViewer viewer;
		private final CellEditor editor;

		public PropertySupport(TableViewer viewer, String[] properties) {
			super(viewer);
			this.viewer = viewer;
			this.editor = new ComboBoxCellEditor(viewer.getTable(), properties);
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			// TODO Auto-generated method stub
			return editor;
		}

		@Override
		protected boolean canEdit(Object element) {
			// TODO edit only if cell is empty
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void setValue(Object element, Object value) {
			// TODO Auto-generated method stub
	        viewer.update(element, null);
		}

	}

	public static class ValueSupport extends EditingSupport {

		private final TableViewer viewer;
		private final CellEditor editor;

		public ValueSupport(TableViewer viewer) {
			super(viewer);
			this.viewer = viewer;
			this.editor = new TextCellEditor(viewer.getTable())	;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void setValue(Object element, Object value) {
			// TODO Auto-generated method stub
	        viewer.update(element, null);
		}

	}

}
