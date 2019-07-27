package org.integratedmodelling.klab.ide.kim;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.integratedmodelling.kim.model.Kim;

public class KimResourceListener implements IResourceChangeListener {

    class DeltaPrinter implements IResourceDeltaVisitor {
        public boolean visit(IResourceDelta delta) {
//            IResource res = delta.getResource();
            switch (delta.getKind()) {
            case IResourceDelta.ADDED:
                break;
            case IResourceDelta.REMOVED:
                break;
            case IResourceDelta.CHANGED:
                break;
            }
            return true; // visit the children
        }
    }

    @Override
    public void resourceChanged(IResourceChangeEvent event) {

        try {
            IResource res = event.getResource();
            switch (event.getType()) {
            case IResourceChangeEvent.PRE_CLOSE:
                System.out.print("Project ");
                System.out.print(res.getFullPath());
                System.out.println(" is about to close.");
                Kim.INSTANCE.closeProject(res.getProject().getName());
                // TODO call the engine?
                break;
            case IResourceChangeEvent.PRE_DELETE:
                System.out.print("Project ");
                System.out.print(res.getFullPath());
                System.out.println(" is about to be deleted.");
//                Kim.INSTANCE.closeProject(res.getProject().getName());
                break;
            case IResourceChangeEvent.POST_CHANGE:
//                System.out.println("Resources have changed.");
                event.getDelta().accept(new DeltaPrinter());
                break;
            case IResourceChangeEvent.PRE_BUILD:
//                System.out.println("Build about to run.");
                event.getDelta().accept(new DeltaPrinter());
                break;
            case IResourceChangeEvent.POST_BUILD:
//                System.out.println("Build complete.");
                event.getDelta().accept(new DeltaPrinter());
                break;
            }
        } catch (Exception e) {
            // diocan
        }
    }
}
