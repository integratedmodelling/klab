package org.integratedmodelling.kim.ui;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
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
                if (uri.toString().startsWith("platform:/resource/")) {
                	String uriPath = uri.toString().substring("platform:/resource/".length());
                	IResource ret = ResourcesPlugin.getWorkspace().getRoot().findMember(uriPath);
                	if (ret instanceof IFile) {
                		((IFile)ret).getProject();
                		String projectId = uriPath.substring(0, uriPath.indexOf('/'));
                		IPath resourceFile = ((IFile)ret).getLocation();
                		// ACHTUNG this won't work if the name of the project is in the namespace. Use the IFile.getProject() and
                		// take it from there.
                		while (!resourceFile.lastSegment().equals(projectId)) {
                			resourceFile = resourceFile.removeLastSegments(1);
                		}
                		return resourceFile.removeLastSegments(1).toFile();
                	}
            	}
                
                IFile myFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformString));
                return myFile.getProject().getLocation().removeLastSegments(1).toFile();
            }
        });
        
        super.start(context);
    }
}
