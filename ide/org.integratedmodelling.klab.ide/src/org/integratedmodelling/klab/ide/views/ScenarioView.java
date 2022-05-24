package org.integratedmodelling.klab.ide.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.contrib.jgrapht.Graph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultDirectedGraph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimWorkspace;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.navigator.e3.TreeContentProvider;
import org.integratedmodelling.klab.rest.ScenarioSelection;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class ScenarioView extends ViewPart {

    public static final String ID = "org.integratedmodelling.klab.ide.views.ScenarioView";

    private static class Edge extends DefaultEdge {

        boolean isDependency = false;
        private static final long serialVersionUID = 1L;

        public Edge(boolean isDependency) {
            this.isDependency = isDependency;
        }

    }

    KlabPeer klab;
    private Graph<String, Edge> dependencies;
    private Map<String, IKimNamespace> scenarios;
    private Set<String> checked = new HashSet<>(); // only a proxy for those in the session
    private Text text;
    boolean autoReset = true;
    boolean autoAlign = false;

    private Action resetAction;
    private Action filterApplicableAction;
    private Action autoResetAction;

    private TreeViewer treeViewer;
    protected String filter;

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
        text.addModifyListener(new ModifyListener(){

            public void modifyText(ModifyEvent e) {
                filter = text.getText();
                refreshScenarios();
            }
        });
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        treeViewer = new TreeViewer(container, SWT.BORDER | SWT.CHECK | SWT.MULTI);
        Tree tree = treeViewer.getTree();
        tree.setHeaderVisible(true);
        tree.setLinesVisible(true);
        tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

        tree.addListener(SWT.Selection, new Listener(){
            public void handleEvent(Event event) {
                if (event.detail == SWT.CHECK) {

                    String scenario = event.item.getData().toString();

                    if (((TreeItem) event.item).getChecked()) {
                        checked.add(event.item.getData().toString());
                    } else {
                        checked.remove(event.item.getData().toString());
                    }

                    for (Edge edge : dependencies.incomingEdgesOf(scenario)) {
                        if (edge.isDependency && !checked.contains(dependencies.getEdgeSource(edge))) {
                            checked.add(dependencies.getEdgeSource(edge));
                        }
                    }

                    for (Edge edge : dependencies.outgoingEdgesOf(scenario)) {
                        if (!edge.isDependency && checked.contains(dependencies.getEdgeTarget(edge))) {
                            checked.remove(dependencies.getEdgeTarget(edge));
                        }
                    }
                    check();
                }
            }
        });

        TreeColumn trclmnScenarioId = new TreeColumn(tree, SWT.NONE);
        trclmnScenarioId.setWidth(320);
        trclmnScenarioId.setText("Scenario ID");

        TreeColumn trclmnDescription = new TreeColumn(tree, SWT.NONE);
        trclmnDescription.setWidth(660);
        trclmnDescription.setText("Description");
        treeViewer.setLabelProvider(new ITableLabelProvider(){

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
                return columnIndex == 0
                        ? ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/globe.png")
                        : null;
            }

            @Override
            public String getColumnText(Object element, int columnIndex) {
                return columnIndex == 0
                        ? element.toString()
                        : scenarios.get(element) == null
                                ? ""
                                : scenarios.get(element).getMetadata().get(IMetadata.DC_COMMENT, "No description supplied");
            }

        });

        treeViewer.setContentProvider(new TreeContentProvider(){

            @Override
            public Object[] getChildren(Object parent) {
                if (parent instanceof Graph) {
                    List<String> ret = new ArrayList<>(dependencies.vertexSet());
                    Collections.sort(ret);
                    return ret.toArray();
                } else if (parent instanceof String) {
                    Set<String> ret = new HashSet<>();
                    for (Edge edge : dependencies.incomingEdgesOf((String) parent)) {
                        if (edge.isDependency) {
                            ret.add(dependencies.getEdgeSource(edge));
                        }
                    }
                    return ret.toArray();
                }
                return null;
            }

            @Override
            public Object getParent(Object element) {
                if (element instanceof String) {
                    Set<String> ret = new HashSet<>();
                    for (Edge edge : dependencies.outgoingEdgesOf((String) element)) {
                        if (edge.isDependency) {
                            ret.add(dependencies.getEdgeTarget(edge));
                        }
                    }
                    return ret.isEmpty() ? dependencies : ret.iterator().next();
                }
                return null;
            }

            @Override
            public boolean hasChildren(Object element) {
                if (element instanceof Graph) {
                    return !dependencies.vertexSet().isEmpty();
                } else if (element instanceof String) {
                    return dependencies.incomingEdgesOf((String) element).stream().filter(edge -> edge.isDependency)
                            .toArray().length > 0;
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
                    ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide", "icons/target_red.png"));
            resetAction.setToolTipText("Reset scenarios");
        }

        {
            autoResetAction = new Action("Automatic reset", SWT.TOGGLE){
                @Override
                public void run() {
                    autoReset = autoResetAction.isChecked();
                }
            };
            autoResetAction.setChecked(true);
            // resetAction.setEnabled(Activator.engineMonitor().isRunning());
            autoResetAction.setImageDescriptor(
                    ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide", "icons/reset.png"));
            autoResetAction.setToolTipText("Automatically reset scenarios at context reset");
        }
        {
            filterApplicableAction = new Action("Show applicable", SWT.TOGGLE){
                @Override
                public void run() {
                    autoAlign = filterApplicableAction.isChecked();
                }
            };
            filterApplicableAction.setChecked(false);
            // resetAction.setEnabled(Activator.engineMonitor().isRunning());
            filterApplicableAction.setImageDescriptor(
                    ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide", "icons/localvariable_obj.png"));
            filterApplicableAction.setToolTipText("Localize scenarios to current context");
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
        case EngineDown:
        case EngineUp:
            resetScenarios();
            break;
        case ResetScenarios:
            resetScenarios();
            break;
        case ResetContext:
            if (autoReset) {
                resetScenarios();
            }
            break;
        case ProjectFileModified:
        case ProjectFileAdded:
        case ProjectFileDeleted:
            refreshScenarios();
            break;
        default:
            break;
        }
    }

    private void resetScenarios() {
        checked.clear();
        check();
    }

    private void check() {

        Display.getDefault().asyncExec(() -> {
            for (Item item : treeViewer.getTree().getItems()) {
                check(item);
            }
            resetAction.setEnabled(checked.size() > 0);
        });

        if (Activator.session() != null) {
            ScenarioSelection bean = new ScenarioSelection();
            bean.getScenarios().addAll(checked);
            Activator.session().send(MessageClass.UserInterface, IMessage.Type.ScenariosSelected, bean);
        }
    }

    private void check(Item item) {
        if (item.getData() instanceof String) {
            ((TreeItem) item).setChecked(checked.contains(item.getData().toString()));
        }
        for (Item i : ((TreeItem) item).getItems()) {
            check(i);
        }
    }

    private void refreshScenarios() {

        dependencies = new DefaultDirectedGraph<String, Edge>(Edge.class);
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
                                dependencies.addEdge(precursor.getName(), namespace.getName(), new Edge(true));
                            }
                            for (String disjoint : namespace.getDisjointNamespaces()) {
                                dependencies.addVertex(disjoint);
                                if (!disjoint.equals(namespace.getName())) {
                                    dependencies.addEdge(disjoint, namespace.getName(), new Edge(false));
                                    dependencies.addEdge(namespace.getName(), disjoint, new Edge(false));
                                }
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
        if (filter != null && !filter.isEmpty()) {
            return namespace.getName().contains(filter.toLowerCase());
        }
        return true;
    }
}
