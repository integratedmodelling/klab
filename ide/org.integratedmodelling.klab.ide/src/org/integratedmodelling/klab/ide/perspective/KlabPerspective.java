package org.integratedmodelling.klab.ide.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.internal.e4.compatibility.ModeledPageLayout;
import org.integratedmodelling.klab.ide.views.DocumentationEditor;
import org.integratedmodelling.klab.ide.views.ReferencesEditor;
import org.integratedmodelling.klab.ide.views.ResourceEditor;
import org.integratedmodelling.klab.ide.views.ResourcesView;
import org.integratedmodelling.klab.ide.views.ScenarioView;
import org.integratedmodelling.klab.ide.views.SearchView;

public class KlabPerspective implements IPerspectiveFactory {

	/**
	 * Creates the initial layout for a page.
	 */
	public void createInitialLayout(IPageLayout layout) {
		
		// ugly trick to stack specific views in editor area. Apparently the only way to do so.
		if (layout instanceof ModeledPageLayout) {
			ModeledPageLayout layout4 = (ModeledPageLayout) layout;
			layout4.stackView(DocumentationEditor.ID, layout.getEditorArea(), false);
			layout4.stackView(ReferencesEditor.ID, layout.getEditorArea(), false);
			layout4.stackView(ResourceEditor.ID, layout.getEditorArea(), false);
		}

//		String editorArea = layout.getEditorArea();
		addFastViews(layout);
		addViewShortcuts(layout);
		addPerspectiveShortcuts(layout);
		layout.addView("org.integratedmodelling.thinkcap.ide.navigator", IPageLayout.LEFT, 0.24f,
				IPageLayout.ID_EDITOR_AREA);
		layout.addView("org.integratedmodelling.klab.ide.views.RuntimeView", IPageLayout.RIGHT, 0.72f,
				IPageLayout.ID_EDITOR_AREA);
		{
			IFolderLayout folderLayout = layout.createFolder("folder", IPageLayout.BOTTOM, 0.73f,
					IPageLayout.ID_EDITOR_AREA);
			folderLayout.addView(SearchView.ID);
			folderLayout.addView(ResourcesView.ID);
            folderLayout.addView(ScenarioView.ID);
		}
		layout.addView("org.integratedmodelling.klab.ide.views.ContextView", IPageLayout.BOTTOM, 0.73f,
				"org.integratedmodelling.thinkcap.ide.navigator");
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
