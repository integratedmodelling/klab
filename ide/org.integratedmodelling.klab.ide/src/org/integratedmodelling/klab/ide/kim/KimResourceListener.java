package org.integratedmodelling.klab.ide.kim;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KimResourceListener implements IResourceChangeListener {

    private static Logger logger = LoggerFactory.getLogger(KimResourceListener.class);
    
	class DeltaPrinter implements IResourceDeltaVisitor {
		public boolean visit(IResourceDelta delta) {
//            IResource res = delta.getResource();
			switch (delta.getKind()) {
//			case IResourceDelta.ADDED:
//				break;
//			case IResourceDelta.REMOVED:
//				if (delta.getResource() instanceof IProject) {
//					logger.debug("PROJECT REMOVED: " + delta.getResource());
//				}
//				break;
			case IResourceDelta.CHANGED:
				if (delta.getResource() instanceof IProject) {
					// TODO may be already there
					logger.debug("PROJECT ADDED: " + delta.getResource());
					return false;
				}
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
//				System.out.print("Project ");
//				System.out.print(res.getFullPath());
//				logger.debug(" is about to close.");
				Kim.INSTANCE.closeProject(res.getProject().getName());
				logger.debug("PROJECT CLOSED: " + res.getProject().getName());
				KlabNavigator.refresh();
				// TODO call the engine?
				break;
			case IResourceChangeEvent.PRE_DELETE:
				logger.debug("Project "+res.getFullPath()+" is about to be deleted.");
//                Kim.INSTANCE.closeProject(res.getProject().getName());
				break;
			case IResourceChangeEvent.POST_CHANGE:
//                logger.debug("Resources have changed.");
				if (event.getSource() instanceof IWorkspace) {
					event.getDelta().accept(new DeltaPrinter());
				}
				break;
			case IResourceChangeEvent.PRE_BUILD:
//                logger.debug("Build about to run.");
				event.getDelta().accept(new DeltaPrinter());
				break;
			case IResourceChangeEvent.POST_BUILD:
//                logger.debug("Build complete.");
				event.getDelta().accept(new DeltaPrinter());
				break;
			default:
				event.getDelta().accept(new DeltaPrinter());
			}
		} catch (Exception e) {
			// diocan
		}
	}
}
