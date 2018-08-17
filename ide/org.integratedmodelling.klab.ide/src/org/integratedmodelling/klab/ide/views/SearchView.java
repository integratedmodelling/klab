package org.integratedmodelling.klab.ide.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.ui.Palette;
import org.integratedmodelling.klab.rest.SearchMatch;
import org.integratedmodelling.klab.rest.SearchMatchAction;
import org.integratedmodelling.klab.rest.SearchRequest;
import org.integratedmodelling.klab.rest.SearchResponse;

public class SearchView extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.SearchView";

	private Text text;
	private TreeViewer treeViewer;
	private Tree tree;
	private List<SearchMatch> matches = new ArrayList<>();
	private String contextId = null;
	private long requestId;
	private List<SearchMatch> accepted = new ArrayList<>();
	private StyledText resultText;
	private KlabPeer klab;
	private Composite topContainer;
	private Composite searchView;
	private Palette paletteView;
	GridData gd_paletteView, gd_searchView;
	private Composite actionArea;

	private boolean searchShowStatus;

	public SearchView() {
	}

	class SearchLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getImage(Object element) {
			return null;
		}

		@Override
		public String getText(Object element) {
			return null;
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (element instanceof SearchMatch && columnIndex == 0) {

				SearchMatch match = (SearchMatch) element;

				if (match.getMatchType() == Match.Type.CONCEPT) {

					if (match.getSemanticType().contains(Type.QUALITY)) {
						return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/quality.png");
					} else if (match.getSemanticType().contains(Type.SUBJECT)
							|| match.getSemanticType().contains(Type.AGENT)) {
						return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/subject.png");
					} else if (match.getSemanticType().contains(Type.PROCESS)) {
						return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/process.png");
					} else if (match.getSemanticType().contains(Type.EVENT)) {
						return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/event.png");
					} else if (match.getSemanticType().contains(Type.RELATIONSHIP)) {
						return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/relationship.png");
					} else if (match.getSemanticType().contains(Type.IDENTITY)) {
						return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/identity.png");
					} else if (match.getSemanticType().contains(Type.REALM)) {
						return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/realm.png");
					} else if (match.getSemanticType().contains(Type.ATTRIBUTE)) {
						return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/attribute.png");
					} else if (match.getSemanticType().contains(Type.ROLE)) {
						return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/role.png");
					} else if (match.getSemanticType().contains(Type.CONFIGURATION)) {
						return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/configuration.png");
					}
				} // TODO else etc
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof SearchMatch) {
				switch (columnIndex) {
				case 0:
					return ((SearchMatch) element).getName();
				case 1:
					return ((SearchMatch) element).getId();
				case 2:
					return ((SearchMatch) element).getDescription();
				}
			}
			return null;
		}

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

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof List) {
				return ((List<?>) parentElement).toArray();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			if (!(element instanceof Collection<?>)) {
				return matches;
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return element instanceof List && ((List<?>) element).size() > 0;
		}

	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		topContainer = new Composite(parent, SWT.NONE);
		// toolkit.paintBordersFor(container);
		topContainer.setLayout(new GridLayout(1, false));
		text = new Text(topContainer, SWT.BORDER);
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN) {
					treeViewer.getTree().forceFocus();
				} else if (e.keyCode == SWT.ESC) {
					reset();
				} else {
					search(text.getText());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (text.getText().isEmpty() && e.keyCode == SWT.BS) {
					removeLastMatch();
				}
			}

		});
		text.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (text.getText().isEmpty() && !accepted.isEmpty() /* TODO and have observable */) {
					observeMatching();
				}
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		actionArea = new Composite(topContainer, SWT.NONE);
		GridLayout gl_actionArea = new GridLayout(1, false);
		gl_actionArea.verticalSpacing = 0;
		gl_actionArea.marginWidth = 0;
		gl_actionArea.marginHeight = 0;
		gl_actionArea.horizontalSpacing = 0;
		actionArea.setLayout(gl_actionArea);
		actionArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		// begin comment out
		paletteView = new Palette(null, actionArea, SWT.NONE);
		paletteView.setVisible(true);
		gd_paletteView = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_paletteView.exclude = false;
		paletteView.setLayoutData(gd_paletteView);
		// end comment out
		
		searchView = new Composite(actionArea, SWT.NONE);
		gd_searchView = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);

		// begin comment out
		searchView.setVisible(false);
		gd_searchView.exclude = true;
		// end comment out
		
		searchView.setLayoutData(gd_searchView);
		TreeColumnLayout tcl_searchView = new TreeColumnLayout();
		searchView.setLayout(tcl_searchView);
		treeViewer = new TreeViewer(searchView, SWT.BORDER);
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {

				Object object = event.getSelection() instanceof StructuredSelection
						? ((StructuredSelection) event.getSelection()).getFirstElement()
						: null;
				if (object instanceof SearchMatch) {
					acceptMatch((SearchMatch) object, matches.indexOf(object));
				}
			}
		});
		tree = treeViewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		{
			TreeColumn imageColumn = new TreeColumn(tree, SWT.NONE);
			imageColumn.setText("Name");
			tcl_searchView.setColumnData(imageColumn, new ColumnPixelData(220, true, true));
		}
		TreeColumn namespaceColumn = new TreeColumn(tree, SWT.NONE);
		tcl_searchView.setColumnData(namespaceColumn, new ColumnPixelData(130, true, true));
		namespaceColumn.setText("Full URN");
		TreeColumn descriptionColumn = new TreeColumn(tree, SWT.NONE);
		tcl_searchView.setColumnData(descriptionColumn, new ColumnPixelData(400, true, true));
		descriptionColumn.setText("Description");

		treeViewer.setContentProvider(new ContentProvider());
		treeViewer.setLabelProvider(new SearchLabelProvider());
		treeViewer.addDragSupport(DND.DROP_DEFAULT,
				new Transfer[] { TextTransfer.getInstance(), LocalSelectionTransfer.getTransfer() },
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

		Composite resultSet = new Composite(topContainer, SWT.BORDER | SWT.EMBEDDED);
		resultSet.setLayout(new GridLayout(2, false));
		resultSet.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Label lblNewLabel = new Label(resultSet, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 16;
		lblNewLabel.setLayoutData(gd_lblNewLabel);

		resultText = new StyledText(resultSet, SWT.NONE);
		resultText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		resultText.setText("No results");

		klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));

		createActions();
		initializeToolBar();
		initializeMenu();

		text.setEnabled(Activator.engineMonitor().isRunning());

	}

	protected void observeMatching() {
		System.out.println("OBSERVING " + getMatchedText());
		Activator.session().observe(getMatchedText());
	}

	private void handleMessage(IMessage message) {

		switch (message.getType()) {
		case EngineDown:
			Display.getDefault().asyncExec(() -> {
				text.setEnabled(false);
			});
			break;
		case EngineUp:
			Display.getDefault().asyncExec(() -> {
				text.setEnabled(true);
			});
			break;
		default:
			break;

		}
	}

	protected void reset() {
		clearMatches();
		accepted.clear();
		setMatchedText();
		text.setText("");
		contextId = null;
	}

	// probably doing way more than needed but I spent enough time. Secret to success was layout() instead of pack().
	private void showSearchResults(boolean show) {

		if (this.searchShowStatus != show) {

			this.searchShowStatus = show;

			if (show) {
				
				gd_paletteView.exclude = true;
				gd_searchView.exclude = false;
				paletteView.setVisible(false);
				searchView.setVisible(true);
				gd_searchView.grabExcessHorizontalSpace = true;
				gd_searchView.grabExcessVerticalSpace = true;
				gd_searchView.horizontalAlignment = SWT.FILL;
				gd_searchView.verticalAlignment = SWT.FILL;

			} else {

				gd_paletteView.exclude = false;
				gd_searchView.exclude = true;
				paletteView.setVisible(true);
				searchView.setVisible(false);
				gd_paletteView.grabExcessHorizontalSpace = true;
				gd_paletteView.grabExcessVerticalSpace = true;
				gd_paletteView.horizontalAlignment = SWT.FILL;
				gd_paletteView.verticalAlignment = SWT.FILL;
			}
			actionArea.layout(true, true);

		}
	}

	protected void acceptMatch(SearchMatch object, int matchIndex) {
		accepted.add(object);
		setMatchedText();
		text.setText("");
		text.forceFocus();
		clearMatches();
		SearchMatchAction action = new SearchMatchAction();
		action.setContextId(contextId);
		action.setMatchIndex(matchIndex);
		Activator.post(IMessage.MessageClass.Search, IMessage.Type.MatchAction, action);
	}

	private void clearMatches() {
		matches.clear();
		showSearchResults(false);
		treeViewer.setInput(matches);
	}

	protected void search(String text) {

		if (text.trim().isEmpty()) {
			return;
		}

		showSearchResults(true);
		matches.clear();

		SearchRequest request = new SearchRequest();

		request.setMaxResults(50);
		request.setQueryString(text);
		request.setContextId(this.contextId);
		request.setRequestId(this.requestId++);

		Activator.post((message) -> {
			SearchResponse response = message.getPayload(SearchResponse.class);
			this.matches.addAll(response.getMatches());
			this.contextId = response.getContextId();
			Display.getDefault().asyncExec(() -> {
				treeViewer.setInput(matches);
			});
		}, IMessage.MessageClass.Search, IMessage.Type.SubmitSearch, request);

	}

	private void removeLastMatch() {
		if (accepted.size() == 1) {
			reset();
		} else if (accepted.size() > 0) {
			accepted.remove(accepted.size() - 1);
			setMatchedText();
		}
	}

	private void setMatchedText() {
		String txt = "";
		List<StyleRange> styles = new ArrayList<>();
		for (SearchMatch match : accepted) {
			int start = txt.length() - 1;
			txt += (txt.isEmpty() ? "" : " ") + match.getId();
			// Color blue = display.getSystemColor(SWT.COLOR_BLUE);
			// StyleRange range = new StyleRange(0, 4, blue, null);
			// resultText.setStyleRange(range);
		}
		resultText.setText(txt);
	}

	private String getMatchedText() {
		String ret = "";
		for (SearchMatch match : accepted) {
			ret += (ret.isEmpty() ? "" : " ") + match.getId();
		}
		return ret;
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
