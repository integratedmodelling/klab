package org.integratedmodelling.klab.ide.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.ITicket.Status;
import org.integratedmodelling.klab.client.messaging.ResourceMonitor;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.Pair;

public class ResourcesView extends ViewPart {

	public static final int LOCAL = 0;
	public static final int PUBLIC = 1;

	public static final String ID = "org.integratedmodelling.klab.ide.views.ResourcesView";

	private Table table;
	private TableViewer tableViewer;
	private Text searchField;
	private Label resultsLabel;
	private List<EResourceReference> currentMatches = new ArrayList<>();
	private List<Pair<String, IParameters<String>>> currentPublished = new ArrayList<>();
	private KlabPeer klab;
	private MenuItem mntmPreviewResource;
	private Combo targetSelector;
	private int mode = LOCAL;

	class LabelProvider implements ITableLabelProvider, IColorProvider, IFontProvider {

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (element instanceof EResourceReference) {
				// local. TODO change image if published, in error or pending
				return columnIndex == 0 ? ResourceManager.getPluginImage(Activator.PLUGIN_ID,
						(((ResourceReference) element).getGeometry().startsWith("#") ? "icons/resources.gif"
								: "icons/resource.gif"))
						: null;
			} else if (element instanceof Pair) {
				// TODO
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ResourceReference) {
				switch (columnIndex) {
				case 0:
					return ((EResourceReference) element).getLocalName();
				case 1:
					return ((EResourceReference) element).getProjectName();
				case 2:
					return ((EResourceReference) element).getAdapterType();
				case 3:
					return describeGeometry(((EResourceReference) element).getGeometry());
				}
			} else if (element instanceof Pair) {
				@SuppressWarnings("unchecked")
				Pair<String, IParameters<String>> pair = (Pair<String, IParameters<String>>) element;
				Date date = pair.getSecond().get(ResourceMonitor.DATE_RESOLVED_FIELD, Date.class);
				if (date == null) {
					date = pair.getSecond().get(ResourceMonitor.DATE_POSTED_FIELD, Date.class);
				}
				switch (columnIndex) {
				case 0:
					return pair.getSecond().get(ResourceMonitor.URN_FIELD, String.class);
				case 1:
					return pair.getSecond().get(ResourceMonitor.NODE_FIELD, String.class);
				case 2:
					return date.toString();
				case 3:
					return pair.getSecond().get(ResourceMonitor.LOCAL_URN_FIELD, String.class);
				}
			}
			return null;
		}

		private String describeGeometry(String geometry) {
			// TODO
			return geometry;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Font getFont(Object element) {
			if (element instanceof Pair) {
				Pair<String, IParameters<String>> pair = (Pair<String, IParameters<String>>) element;
				if (pair.getSecond().get(ResourceMonitor.STATUS_FIELD, ITicket.Status.class) == Status.OPEN) {
					return SWTResourceManager.getItalicFont(tableViewer.getTable().getFont());
				} else {
					return SWTResourceManager.getBoldFont(tableViewer.getTable().getFont());
				}
			}
			return null;
		}

		@Override
		public Color getForeground(Object element) {
			if (element instanceof Pair) {

				@SuppressWarnings("unchecked")
				Pair<String, IParameters<String>> pair = (Pair<String, IParameters<String>>) element;
				NodeReference node = null;
				if (Activator.klab().getNetwork() == null) {
					node = Activator.klab().getNetwork().getNodes()
							.get(pair.getSecond().get(ResourceMonitor.NODE_FIELD));
				}
				if (node == null || !node.isOnline()) {
					return SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY);
				}

				if (pair.getSecond().get(ResourceMonitor.STATUS_FIELD, ITicket.Status.class) == Status.RESOLVED) {
					return SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN);
				} else if (pair.getSecond().get(ResourceMonitor.STATUS_FIELD, ITicket.Status.class) == Status.ERROR) {
					return SWTResourceManager.getColor(SWT.COLOR_RED);
				}
			}
			return null;
		}

		@Override
		public Color getBackground(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	class ContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);

		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof List) {
				return ((List<?>) parentElement).toArray();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof ResourceReference) {
				return currentMatches;
			} else if (element instanceof Pair) {
				return currentPublished;
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return element instanceof List && ((List<?>) element).size() > 0;
		}

	}

	public ResourcesView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		{
			Composite searchContainer = new Composite(container, SWT.NONE);
			searchContainer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			searchContainer.setLayout(new GridLayout(2, false));
			searchField = new Text(searchContainer, SWT.BORDER);
			searchField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					String t = searchField.getText();
					search(t);
				}
			});
			searchField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			targetSelector = new Combo(searchContainer, SWT.READ_ONLY);
			targetSelector.add("Local ");
			targetSelector.add("Public");
			targetSelector.select(0);
			targetSelector.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					mode = targetSelector.getSelectionIndex();
					refresh();
				}
			});
		}
		{
			tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
			table = tableViewer.getTable();
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			{
				TableColumn tblclmnUrn = new TableColumn(table, SWT.NONE);
				tblclmnUrn.setWidth(550);
				tblclmnUrn.setText("Local name");
			}
			{
				TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
				tblclmnNewColumn.setWidth(160);
				tblclmnNewColumn.setText("Project");
			}
			{
				TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
				tblclmnNewColumn.setWidth(70);
				tblclmnNewColumn.setText("Type");
			}
			{
				TableColumn tblclmnGeometry = new TableColumn(table, SWT.NONE);
				tblclmnGeometry.setWidth(100);
				tblclmnGeometry.setText("Geometry");
			}

			Menu menu = new Menu(table);
			table.setMenu(menu);

			MenuItem mntmCopyUrn = new MenuItem(menu, SWT.NONE);
			mntmCopyUrn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Object object = table.getSelection().length > 0 ? table.getSelection()[0].getData() : null;
					if (object instanceof EResourceReference) {
						Eclipse.INSTANCE.copyToClipboard(((EResourceReference) object).getUrn());
					}
				}
			});
			mntmCopyUrn.setText("Copy URN");

			mntmPreviewResource = new MenuItem(menu, SWT.NONE);
			mntmPreviewResource.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					Object object = table.getSelection().length > 0 ? table.getSelection()[0].getData() : null;
					if (object instanceof EResourceReference) {
						Activator.session().previewResource((EResourceReference) object);
					}
				}
			});
			mntmPreviewResource.setText("Preview resource");
			mntmPreviewResource.setEnabled(Activator.engineMonitor().isRunning());

			tableViewer.setContentProvider(new ContentProvider());
			tableViewer.setLabelProvider(new LabelProvider());
			tableViewer.addDragSupport(DND.DROP_DEFAULT,
					new Transfer[] { TextTransfer.getInstance(), LocalSelectionTransfer.getTransfer() },
					new DragSourceListener() {

						@Override
						public void dragStart(DragSourceEvent event) {
							// TODO Auto-generated method stub
						}

						@Override
						public void dragSetData(DragSourceEvent event) {
							if (event.getSource() instanceof EResourceReference) {
								event.data = event.getSource();
							}
						}

						@Override
						public void dragFinished(DragSourceEvent event) {
							// TODO Auto-generated method stub
						}
					});
		}

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object object = event.getSelection() instanceof StructuredSelection
						? ((StructuredSelection) event.getSelection()).getFirstElement()
						: null;
				if (object instanceof EResourceReference) {
					try {
						IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
								.showView(ResourceEditor.ID);
						if (view != null) {
							((ResourceEditor) view).loadResource((EResourceReference) object);
						}
					} catch (PartInitException ex) {
						Eclipse.INSTANCE.handleException(ex);
					}
				}
			}
		});
		{
			resultsLabel = new Label(container, SWT.NONE);
			resultsLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			resultsLabel.setText("No results");
		}

		klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));

		createActions();
		initializeToolBar();
		initializeMenu();

	}

	private void handleMessage(IMessage message) {

		switch (message.getType()) {
		case EngineDown:
			Display.getDefault().asyncExec(() -> {
				mntmPreviewResource.setEnabled(false);
			});
			break;
		case EngineUp:
			Display.getDefault().asyncExec(() -> {
				mntmPreviewResource.setEnabled(true);
			});
			break;
		case TicketStatusChanged:
		case TicketCreated:
		case TicketResolved:
			refresh();
			break;
		default:
			break;
		}
	}

	/**
	 * Switch to public (1) or local (0)
	 */
	public void switchTo(int mode) {
		if (this.mode != mode) {
			this.mode = mode;
			this.targetSelector.select(mode);
			refresh();
		}
	}

	protected void search(String text) {
		currentMatches.clear();
		if (text.length() > 1) {
			if (this.mode == 0) {
				for (EResourceReference resource : Activator.klab().getProjectResources()) {
					if (resource.getLocalName().startsWith(text)) {
						currentMatches.add(0, resource);
					} else if (resource.getLocalName().contains(text)) {
						currentMatches.add(resource);
					}
				}
			} else {
				// public
			}
		}
		refresh();
	}

	private void refresh() {

		Display.getDefault().asyncExec(() -> {
			if (mode == 0) {
				tableViewer.setInput(currentMatches);
			} else {
				// refresh from resource manager
				this.currentPublished = Activator.session().getPublishedResources();
				tableViewer.setInput(currentPublished);
			}
		});
	}

	public void dispose() {
		klab.dispose();
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
