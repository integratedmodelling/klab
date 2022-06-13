package org.integratedmodelling.klab.ide.views;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.ui.TestXViewerFactory;
import org.integratedmodelling.klab.rest.TestStatistics;

public class TestView extends ViewPart {
    private static class TreeContentProvider implements ITreeContentProvider {
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
        public void dispose() {
        }
        public Object[] getElements(Object inputElement) {
            return getChildren(inputElement);
        }
        public Object[] getChildren(Object parentElement) {
            return new Object[] { "item_0", "item_1", "item_2" };
        }
        public Object getParent(Object element) {
            return null;
        }
        public boolean hasChildren(Object element) {
            return getChildren(element).length > 0;
        }
    }
    private static class ViewerLabelProvider extends LabelProvider {
        public Image getImage(Object element) {
            return super.getImage(element);
        }
        public String getText(Object element) {
            return super.getText(element);
        }
    }

    public static final String ID = "org.integratedmodelling.klab.ide.views.TestView"; //$NON-NLS-1$
    private KlabPeer klab;
    private XViewer treeViewer;
    private Map<String, TestStatistics> testcases = new LinkedHashMap<>();

    TestStatistics test = null;
    private Tree tree;
    
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
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {
            // TODO Auto-generated method stub
            return null;
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

        try {
            treeViewer = new XViewer(container, SWT.BORDER, new TestXViewerFactory());
            tree = treeViewer.getTree();
            tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
            treeViewer.setContentProvider(new TreeContentProvider() {

                @Override
                public Object[] getChildren(Object parentElement) {
                    // TODO Auto-generated method stub
                    return super.getChildren(parentElement);
                }

                @Override
                public Object getParent(Object element) {
                    // TODO Auto-generated method stub
                    return super.getParent(element);
                }
                
            });
            treeViewer.setLabelProvider(new TreeLabelProvider());
            
            klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));
        } catch (Throwable t) {
            // ignore a resource not properly disposed error, this seems to be a Nebula bug
            System.out.println("WARNING: Nebula bug persists on XViewer initialization");
        }

        
        
        createActions();
        // Uncomment if you wish to add code to initialize the toolbar
        // initializeToolBar();
        initializeMenu();
    }

    private void handleMessage(IMessage message) {
        switch (message.getType()) {
        case TestStarted:
            updateUI();
            break;
        case TestFinished:
            updateUI();
            break;
        case TestCaseStarted:
            updateUI();
            break;
        case TestCaseFinished:
            updateUI();
            break;
        case TestRunStarted:
            this.test = message.getPayload(TestStatistics.class);
            break;
        case TestRunFinished:
            updateUI();
            break;
        default:
            break;
        }
    }

    private void updateUI() {
        // TODO Auto-generated method stub
        
    }

    /**
     * Create the actions.
     */
    private void createActions() {
        // Create the actions
    }

    @Override
    public void dispose() {
        treeViewer.dispose();
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
