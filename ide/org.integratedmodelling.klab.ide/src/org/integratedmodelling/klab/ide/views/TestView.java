package org.integratedmodelling.klab.ide.views;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.rest.ActionStatistics;
import org.integratedmodelling.klab.rest.AssertionStatistics;
import org.integratedmodelling.klab.rest.TestRun;
import org.integratedmodelling.klab.rest.TestStatistics;

public class TestView extends ViewPart {

    private class TreeContentProvider implements ITreeContentProvider {
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
        public void dispose() {
        }
        public Object[] getElements(Object inputElement) {
            return getChildren(inputElement);
        }
        public Object[] getChildren(Object parentElement) {
            if (parentElement != null) {
                if (parentElement instanceof Map) {
                    return ((Map<?,?>)parentElement).values().toArray();
                } else 
                if (parentElement instanceof TestRun) {
                    return testcases.values().toArray();
                } else if (parentElement instanceof TestStatistics) {
                    return ((TestStatistics) parentElement).getActions().toArray();
                } else if (parentElement instanceof ActionStatistics) {
                    return ((ActionStatistics) parentElement).getAssertions().toArray();
                }
            }
            return new Object[]{};
        }
        public Object getParent(Object element) {
            if (element instanceof ActionStatistics) {
                return testcases.get(((ActionStatistics) element).getTestCaseName());
            } else if (element instanceof TestStatistics) {
                return testcases;
            } else if (element instanceof AssertionStatistics) {
                
            }
            return null;
        }
        public boolean hasChildren(Object element) {
            return getChildren(element).length > 0;
        }
    }

    public static final String ID = "org.integratedmodelling.klab.ide.views.TestView"; //$NON-NLS-1$
    private KlabPeer klab;
    private TreeViewer treeViewer;
    private Map<String, TestStatistics> testcases = Collections.synchronizedMap(new LinkedHashMap<>());
    private Tree tree;
    private Action resetAction;

    public TestView() {
    }

    class TreeLabelProvider extends LabelProvider implements ITableLabelProvider, IColorProvider {

        @Override
        public Color getForeground(Object element) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Color getBackground(Object element) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            if (columnIndex == 0) {

            } else if (columnIndex == 2) {

            }
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {

            switch(columnIndex) {
            case 0:
                if (element instanceof ActionStatistics) {
                    return ((ActionStatistics) element).getName();
                } else if (element instanceof TestStatistics) {
                    return ((TestStatistics) element).getName();
                } else if (element instanceof AssertionStatistics) {
                    return "Assertion";
                }
                break;
            case 1:
                if (element instanceof ActionStatistics) {
                    return ((ActionStatistics) element).getDescription();
                } else if (element instanceof TestStatistics) {
                    return ((TestStatistics) element).getDescription();
                } else if (element instanceof AssertionStatistics) {
                    return ((AssertionStatistics) element).getDescriptor();
                }
                break;
            }
            return "";
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
        container.setLayout(new GridLayout(1, false));

        treeViewer = new TreeViewer(container, SWT.BORDER);
        tree = treeViewer.getTree();
        tree.setLinesVisible(true);
        tree.setHeaderVisible(true);
        tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        TreeColumn trclmnId = new TreeColumn(tree, SWT.NONE);
        trclmnId.setWidth(100);
        trclmnId.setText("Id");

        TreeColumn trclmnDescription = new TreeColumn(tree, SWT.NONE);
        trclmnDescription.setWidth(600);
        trclmnDescription.setText("Description");

        TreeColumn trclmnOutcome = new TreeColumn(tree, SWT.NONE);
        trclmnOutcome.setWidth(100);
        trclmnOutcome.setText("Outcome");
        treeViewer.setContentProvider(new TreeContentProvider());
        treeViewer.setLabelProvider(new TreeLabelProvider());

        klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));

        createActions();
        // Uncomment if you wish to add code to initialize the toolbar
        // initializeToolBar();
        initializeMenu();
    }

    private synchronized void handleMessage(IMessage message) {
        switch(message.getType()) {
        case TestStarted:
        case TestFinished:
            ActionStatistics action = message.getPayload(ActionStatistics.class);
            testcases.get(action.getTestCaseName()).getActions().add(action);
            updateUI();
            break;
        case TestCaseStarted:
        case TestCaseFinished:
            TestStatistics testdata = message.getPayload(TestStatistics.class);
            testcases.put(testdata.getName(), testdata);
            updateUI();
            break;
        case TestRunStarted:
        case TestRunFinished:
            updateUI();
            break;
        default:
            break;
        }
    }

    private synchronized void updateUI() {
        Display.getDefault().asyncExec(() -> {
            if (this.testcases.isEmpty()) {
                resetAction.setEnabled(false);
            } else {
                resetAction.setEnabled(true);
            }
            treeViewer.setInput(this.testcases);
        });
    }

    /**
     * Create the actions.
     */
    private void createActions() {
        {
            resetAction = new Action("Clear test logs"){
                @Override
                public void run() {
                    testcases.clear();
                    updateUI();
                }
            };
            resetAction.setEnabled(false);
            resetAction.setImageDescriptor(
                    ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide", "icons/target_red.png"));
            resetAction.setToolTipText("Clear test logs");
        }
    }

    @Override
    public void dispose() {
        klab.dispose();
        super.dispose();
    }

    /**
     * Initialize the toolbar.
     */
    private void initializeToolBar() {
        if (getViewSite() != null) {
            IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
        }
    }

    /**
     * Initialize the menu.
     */
    private void initializeMenu() {
        if (getViewSite() != null) {
            IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
        }
    }

    @Override
    public void setFocus() {
        // Set the focus
    }
}
