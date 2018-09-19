package org.integratedmodelling.klab.ide.ui;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.utils.Pair;

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
			// TODO also check if field is final
			return Activator.engineMonitor().isRunning();
		}

		@Override
		protected Object getValue(Object element) {
			if (element instanceof Pair) {
				String key = (String)((Pair<?,?>)element).getFirst();
				// TODO match to adapter field choice
				return 0;
			}
			return "";
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
			// TODO also check if field is final
			return Activator.engineMonitor().isRunning();
		}

		@Override
		protected Object getValue(Object element) {
			if (element instanceof Pair) {
				return ((Pair<?,?>)element).getSecond();
			}
			return "";

		}

		@Override
		protected void setValue(Object element, Object value) {
			// TODO Auto-generated method stub
	        viewer.update(element, null);
		}

	}

}
