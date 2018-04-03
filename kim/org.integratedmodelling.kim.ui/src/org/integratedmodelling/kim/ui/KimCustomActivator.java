package org.integratedmodelling.kim.ui;

import java.io.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.UriResolver;
import org.integratedmodelling.kim.ui.internal.KimActivator;
import org.osgi.framework.BundleContext;

public class KimCustomActivator extends KimActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        
        /*
         * allow the Kim manager to resolve platform URIs to their workspaces.
         */
        Kim.INSTANCE.setUriResolver("platform", new UriResolver() {
            @Override
            public File resolveResourceUriToWorkspaceRootDirectory(URI uri) {
                String platformString = uri.toPlatformString(true);
                IFile myFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformString));
                return myFile.getProject().getLocation().removeLastSegments(1).toFile();
            }
        });
        
        /*
         *  Library initialization logics for UI (w/o code generation) moved 
         *  to {@link KimNatureAddingEditorCallback}
         *  
         *  TODO may want to have callbacks for synchronization and engine start here.
         */
        ResourcesPlugin.getWorkspace().addResourceChangeListener(new KimResourceListener());
        
        super.start(context);
    }
}
