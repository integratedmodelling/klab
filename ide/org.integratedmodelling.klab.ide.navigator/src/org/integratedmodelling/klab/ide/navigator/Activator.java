package org.integratedmodelling.klab.ide.navigator;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.integratedmodelling.kim.api.IKimWorkspace;
import org.integratedmodelling.kim.model.Kim;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.integratedmodelling.klab.ide.navigator";
	private static Activator plugin;
	private IKimWorkspace kimWorkspace;

	public void start(BundleContext context) throws Exception {

		super.start(context);

		// TODO this is a caret listener for the k.IM editors - somehow the 
//		workbenchWindow.getActivePage().addPartListener(new PartListener() {
//		    public void partOpened(IWorkbenchPartReference partRef) {
//		        //Check if this is an editor and its input is what I need
//		        AbstractTextEditor e =
//		            (AbstractTextEditor)((IEditorReference) partRef).getEditor(false);
//		        ((StyledText)e.getAdapter(Control.class)).addCaretListener(l);
//		    }
//		});
		
		plugin = this;

		URI uri = ResourcesPlugin.getWorkspace().getRoot().getLocationURI();
		List<File> projectRoots = new ArrayList<>();
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			projectRoots.add(project.getLocation().toFile());
		}

		/**
		 * TODO presync the worldview. Use an interval and lock file to avoid multiple
		 * loads from engine or new instances.
		 */

		/**
		 * Preload the workspace so that the navigator can work right away.
		 */
		this.kimWorkspace = Kim.INSTANCE.loadWorkspace(uri.toString(), projectRoots);
	}
}
