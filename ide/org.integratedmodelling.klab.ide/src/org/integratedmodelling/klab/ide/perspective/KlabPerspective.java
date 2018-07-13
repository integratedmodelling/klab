package org.integratedmodelling.klab.ide.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;


public class KlabPerspective implements IPerspectiveFactory {

    /**
     * Creates the initial layout for a page.
     */
    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        addFastViews(layout);
        addViewShortcuts(layout);
        addPerspectiveShortcuts(layout);
    	layout.addView("org.integratedmodelling.thinkcap.ide.navigator", IPageLayout.LEFT, 0.24f, IPageLayout.ID_EDITOR_AREA);
    	layout.addView("org.integratedmodelling.klab.ide.views.RuntimeView", IPageLayout.RIGHT, 0.72f, IPageLayout.ID_EDITOR_AREA);
    	layout.addView("org.integratedmodelling.klab.ide.views.ContextView", IPageLayout.BOTTOM, 0.73f, "org.integratedmodelling.thinkcap.ide.navigator");
    }

    /**
     * Add fast views to the perspective.
     */
    private void addFastViews(IPageLayout layout) {
    }

    /**
     * Add view shortcuts to the perspective.
     */
    private void addViewShortcuts(IPageLayout layout) {
    }

    /**
     * Add perspective shortcuts to the perspective.
     */
    private void addPerspectiveShortcuts(IPageLayout layout) {
    }

}
