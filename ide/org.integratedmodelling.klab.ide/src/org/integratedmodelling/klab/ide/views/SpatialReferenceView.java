package org.integratedmodelling.klab.ide.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.ui.WorldWidget;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.graphics.Point;

public class SpatialReferenceView extends ViewPart {

    public static final String ID      = "org.integratedmodelling.klab.ide.views.SpatialReferenceView"; //$NON-NLS-1$
    private Composite viewer;
    private KlabPeer klab;
    private WorldWidget map;
//    private final FormToolkit  toolkit = new FormToolkit(Display.getCurrent());

    public SpatialReferenceView() {
    }

    /**
     * Create contents of the view part.
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {
        
        viewer = new Composite(parent, SWT.NONE);
        viewer.setSize(new Point(360, 181));
        viewer.setLayout(new FillLayout(SWT.HORIZONTAL));
        map = new WorldWidget(viewer, SWT.NONE);
//        toolkit.paintBordersFor(container);
        
        klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));

        createActions();
        initializeToolBar();
        initializeMenu();
    }

    private void handleMessage(IMessage message) {
        if (message.getType() == Type.RegionOfInterest) {
            Display.getDefault().asyncExec(() -> map.setExtent(message.getPayload(SpatialExtent.class)));
        }
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
