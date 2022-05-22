package org.integratedmodelling.klab.ide.views;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.contrib.jgrapht.Graph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultDirectedGraph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimWorkspace;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.navigator.e3.TreeContentProvider;

public class ScenarioView extends ViewPart {
    private static class ViewerLabelProvider extends LabelProvider {
        public Image getImage(Object element) {
            return super.getImage(element);
        }
        public String getText(Object element) {
            return super.getText(element);
        }
    }

    public static final String ID = "org.integratedmodelling.klab.ide.views.ScenarioView";
    
    KlabPeer klab;
    private Graph<String, DefaultEdge> dependencies;
    private Map<String, IKimNamespace> scenarios;
    private Set<String> checked = new HashSet<>(); // only a proxy for those in the session
    private Text text;
    boolean autoReset = true;
    boolean autoAlign = false;
    
    private Action resetAction;
    private Action filterApplicableAction;
    private Action autoResetAction;

    private TreeViewer treeViewer;

    public ScenarioView() {
        klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));
        refreshScenarios();
    }

    /**
     * Create contents of the view part.
     * 
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout(2, false));

        Label lblSearch = new Label(container, SWT.NONE);
        lblSearch.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblSearch.setText("Filter");

        text = new Text(container, SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        treeViewer = new TreeViewer(container, SWT.BORDER | SWT.CHECK | SWT.MULTI);
        Tree tree = treeViewer.getTree();
        tree.setLinesVisible(true);
        tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        treeViewer.setLabelProvider(new ViewerLabelProvider(){

            @Override
            public Image getImage(Object element) {
                return ResourceManager.getPluginImage("org.integratedmodelling.klab.ide",
                        "icons/globe.png");
            }

        });
        treeViewer.setContentProvider(new TreeContentProvider(){

            @Override
            public Object[] getChildren(Object parent) {
                if (parent instanceof Graph) {
                    return dependencies.vertexSet().toArray();
                } else if (parent instanceof String) {
                    return dependencies.incomingEdgesOf((String) parent).toArray();
                }
                return null;
            }

            @Override
            public Object getParent(Object element) {
                if (element instanceof String) {
                    Set<DefaultEdge> deps = dependencies.outgoingEdgesOf((String) element);
                    if (!deps.isEmpty()) {
                        return deps.iterator().next();
                    }
                }
                return null;
            }

            @Override
            public boolean hasChildren(Object element) {
                if (element instanceof Graph) {
                    return dependencies.vertexSet().isEmpty();
                } else if (element instanceof String) {
                    return dependencies.incomingEdgesOf((String) element).isEmpty();
                }
                return false;
            }
        });

        createActions();
        initializeToolBar();
        initializeMenu();
    }

    /**
     * Create the actions.
     */
    private void createActions() {
        // Create the actions
        {
            resetAction = new Action("Uncheck all scenarios"){
                @Override
                public void run() {
                    resetScenarios();
                }
            };
            resetAction.setEnabled(false);
            // resetAction.setEnabled(Activator.engineMonitor().isRunning());
            resetAction.setImageDescriptor(
                    ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide",
                            "icons/target_red.png"));
            resetAction.setToolTipText("Reset scenarios");
        }

        {
            autoResetAction = new Action("Automatic reset", SWT.PUSH){
                @Override
                public void run() {
                    if (autoResetAction.isChecked()) {
                        
                    }
                }
            };
            autoResetAction.setChecked(true);
            // resetAction.setEnabled(Activator.engineMonitor().isRunning());
            autoResetAction.setImageDescriptor(
                    ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide",
                            "icons/target_red.png"));
            autoResetAction.setToolTipText("Automatically reset scenarios at context reset");
        }
        {
            filterApplicableAction = new Action("Show applicable", SWT.PUSH){
                @Override
                public void run() {
                    if (autoResetAction.isChecked()) {
                        
                    }
                }
            };
            filterApplicableAction.setChecked(false);
            // resetAction.setEnabled(Activator.engineMonitor().isRunning());
            filterApplicableAction.setImageDescriptor(
                    ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide",
                            "icons/target_red.png"));
            filterApplicableAction.setToolTipText("Only show scenarios that affect the current context");
        }
    }

    /**
     * Initialize the toolbar.
     */
    private void initializeToolBar() {
        // condition is only to keep WindowBuilder happy, doesn't happen in actual use.
        if (getViewSite() != null) {
            IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
            tbm.add(resetAction);
            tbm.add(autoResetAction);
            tbm.add(filterApplicableAction);
        }
    }

    /**
     * Initialize the menu.
     */
    private void initializeMenu() {
        // IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
    }

    @Override
    public void setFocus() {
        // Set the focus
    }

    private void handleMessage(IMessage message) {
        switch(message.getType()) {
        case ProjectFileModified:
        case ProjectFileAdded:
        case ProjectFileDeleted:
        case EngineDown:
        case EngineUp:
            refreshScenarios();
            break;
        default:
            break;
        }
    }

    private void resetScenarios() {
        
    }
    
    private void refreshScenarios() {

        dependencies = new DefaultDirectedGraph<String, DefaultEdge>(
                DefaultEdge.class);
        scenarios = new HashMap<>();

        if (Activator.klab() != null) {
            for (IKimWorkspace workspace : Kim.INSTANCE.getWorkspaces()) {
                for (String project : workspace.getProjectNames()) {
                    IKimProject proj = Kim.INSTANCE.getProject(project);
                    for (IKimNamespace namespace : proj.getNamespaces()) {
                        if (namespace.isScenario() && filter(namespace)) {
                            scenarios.put(namespace.getName(), namespace);
                            dependencies.addVertex(namespace.getName());
                            for (IKimNamespace precursor : namespace.getImported()) {
                                dependencies.addVertex(precursor.getName());
                                scenarios.put(precursor.getName(), precursor);
                                dependencies.addEdge(namespace.getName(), precursor.getName());
                            }
                        }
                    }
                }
            }
        }

        Display.getDefault().asyncExec(() -> {
            if (treeViewer != null) {
                treeViewer.setInput(dependencies);
            }
        });

    }

    private boolean filter(IKimNamespace namespace) {
        // TODO Auto-generated method stub
        return true;
    }
}
