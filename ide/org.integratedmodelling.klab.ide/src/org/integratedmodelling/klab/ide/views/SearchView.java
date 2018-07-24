package org.integratedmodelling.klab.ide.views;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.api.knowledge.IConcept;

public class SearchView extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.SearchView"; //$NON-NLS-1$
	// private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text text;
	private TreeViewer treeViewer;
	private Tree tree;

	public SearchView() {
	}

    class ContentProvider implements ITreeContentProvider {

        @Override
        public void dispose() {
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

        @Override
        public Object[] getElements(Object inputElement) {
            return getChildren(inputElement);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Object[] getChildren(Object parentElement) {
//            if (parentElement instanceof Collection<?>) {
//                return ((ArrayList<Object>) parentElement).toArray();
//            } else if (parentElement instanceof IConcept) {
//                return expandConcept((IConcept) parentElement);
//            } else if (parentElement instanceof Child) {
//                return ((Child) parentElement).getChildren();
//            }
            return null;
        }

        @Override
        public Object getParent(Object element) {
//            if (!(element instanceof Collection<?>)) {
//                return matches;
//            } else if (element instanceof Child) {
//                return ((Child) element)._parent;
//            }
            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean hasChildren(Object element) {
//            if (element instanceof Collection<?>) {
//                return ((ArrayList<Object>) element).size() > 0;
//            } else if (element instanceof Child) {
//                return ((Child) element).hasChildren();
//            } else if (element instanceof IConcept) {
//                return ((IConcept) element).getChildren().size() > 0 ||
//                        ((IConcept) element).getAllParents().size() > 0;
//            }

            return false;
        }

    }
	
	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		// toolkit.paintBordersFor(container);
		container.setLayout(new GridLayout(1, false));
		text = new Text(container, SWT.BORDER);
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String t = text.getText();
				// search(t);
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		// toolkit.adapt(text, true, true);
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		// toolkit.adapt(composite);
		// toolkit.paintBordersFor(composite);
		// TODO put a palette in here, show the tree only when typing
		Composite normalSearchView = new Composite(composite, SWT.NONE);
		// toolkit.adapt(normalSearchView);
		// toolkit.paintBordersFor(normalSearchView);
		TreeColumnLayout tcl_normalSearchView = new TreeColumnLayout();
		normalSearchView.setLayout(tcl_normalSearchView);
		treeViewer = new TreeViewer(normalSearchView, SWT.BORDER);
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {

				Object o = ((TreeSelection) (event.getSelection())).getFirstElement();
				//
				// if (o instanceof Child)
				// o = ((Child) o)._target;
				//
				// showOntology(o);
			}
		});
		tree = treeViewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		// toolkit.adapt(tree);
		// toolkit.paintBordersFor(tree);
		{
			TreeColumn imageColumn = new TreeColumn(tree, SWT.NONE);
			imageColumn.setText("Name");
			tcl_normalSearchView.setColumnData(imageColumn, new ColumnPixelData(220, true, true));
		}
		TreeColumn namespaceColumn = new TreeColumn(tree, SWT.NONE);
		namespaceColumn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("namespace:");
				text.setSelection("namespace:".length());
				text.forceFocus();
			}
		});
		tcl_normalSearchView.setColumnData(namespaceColumn, new ColumnPixelData(130, true, true));
		namespaceColumn.setText("Namespace");
		TreeColumn descriptionColumn = new TreeColumn(tree, SWT.NONE);
		descriptionColumn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("description:");
				text.setSelection("description:".length());
				text.forceFocus();
			}
		});
		tcl_normalSearchView.setColumnData(descriptionColumn, new ColumnPixelData(400, true, true));
		descriptionColumn.setText("Description");
		treeViewer.setContentProvider(new ContentProvider());
		treeViewer.setLabelProvider(new LabelProvider());
		treeViewer.addDragSupport(DND.DROP_DEFAULT, new Transfer[] { TextTransfer.getInstance() },
				new DragSourceListener() {

					@Override
					public void dragStart(DragSourceEvent event) {
						// TODO Auto-generated method stub
						// System.out.println("ciao");
					}

					@Override
					public void dragSetData(DragSourceEvent event) {
						// if (event.getSource() instanceof IConcept) {
						// event.data = ((IConcept) event.getSource()).toString();
						// } else if (event.getSource() instanceof IModelObject) {
						// event.data = ((IModelObject) event.getSource()).getName();
						// }
					}

					@Override
					public void dragFinished(DragSourceEvent event) {
						// TODO Auto-generated method stub
						// System.out.println("ciao");
					}
				});
		
		Composite resultSet = new Composite(container, SWT.NONE);
		resultSet.setLayout(new GridLayout(1, false));
		resultSet.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
				Label lblNewLabel = new Label(resultSet, SWT.NONE);
				lblNewLabel.setText("No results");
		createActions();
		initializeToolBar();
		initializeMenu();
	}

	public void dispose() {
		// toolkit.dispose();
		super.dispose();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

}
