package org.integratedmodelling.klab.ide.builder;

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.rest.ProjectModificationNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KlabBuilder extends IncrementalProjectBuilder {

    public static final String BUILDER_ID = "org.integratedmodelling.klab.ide.klabBuilder";
    private static Logger logger = LoggerFactory.getLogger(KlabBuilder.class);

    /**
     * This keeps the Xtext internal model in sync with the loader and ensures that dependencies,
     * which are handled outside of Xtext, are refreshed.
     * 
     * @author ferdinando.villa
     *
     */
    class KlabDeltaVisitor implements IResourceDeltaVisitor {

        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            IResource resource = delta.getResource();

            // TODO if it's a k.LAB resource, we must re-read the relevant resource list.
            // This may happen at git pull for example. Some cases are taken care of by the
            // loader and installed callbacks, others aren't.

            switch(delta.getKind()) {
            case IResourceDelta.ADDED:
                if (resource instanceof IProject
                        && Kim.INSTANCE.isKimProject(((IProject) resource).getLocation().toFile())) {
                    logger.info("ADDED PROJECT: " + delta);
                }
                // if (resource instanceof IFile && isRelevant((IFile) resource)) {
                // Activator.loader().add(((IFile) resource).getLocation().toFile());
                // Activator.post(IMessage.MessageClass.ProjectLifecycle,
                // IMessage.Type.ProjectFileModified,
                // new
                // ProjectModificationNotification(ProjectModificationNotification.Type.ADDITION,
                // ((IFile) resource).getLocation().toFile()));
                // }
                KlabNavigator.refresh();
                break;
            case IResourceDelta.REMOVED:
                logger.info("REMOVED: " + delta);
                // just close the editor; everything else is dealt with by the loader and
                // callback
                KlabNavigator.refresh();
                break;
            case IResourceDelta.CHANGED:
                if (resource instanceof IFile && isRelevant((IFile) resource)) {
                    File file = ((IFile) resource).getLocation().toFile();
                    if (KActors.INSTANCE.isKActorsFile(file)) {
                        KActors.INSTANCE.touch(file);
                    } else {
                        Activator.loader().touch(file);
                    }

                    Activator.post((message) -> {
                        if (Activator.session() != null) {
                            Activator.session().send(message);
                        }
                    }, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.ProjectFileModified,
                            new ProjectModificationNotification(ProjectModificationNotification.Type.CHANGE,
                                    file));
                }
                break;
            case IResourceDelta.OPEN:
                if (resource instanceof IProject
                        && Kim.INSTANCE.isKimProject(((IProject) resource).getLocation().toFile())) {
                    /**
                     * 
                     */
                    logger.info("OPEN/CLOSE/ADD k.IM PROJECT: " + delta);
                    KlabNavigator.refresh();
                }
            }
            // return true to continue visiting children.
            return true;
        }
    }

    class SampleResourceVisitor implements IResourceVisitor {

        public boolean visit(IResource resource) {
            // checkXML(resource);
            // return true to continue visiting children.
            // System.out.println("Visiting " + resource);
            return true;
        }
    }

    // class XMLErrorHandler extends DefaultHandler {
    //
    // private IFile file;
    //
    // public XMLErrorHandler(IFile file) {
    // this.file = file;
    // }
    //
    // private void addMarker(SAXParseException e, int severity) {
    // KlabBuilder.this.addMarker(file, e.getMessage(), e.getLineNumber(), severity);
    // }
    //
    // public void error(SAXParseException exception) throws SAXException {
    // addMarker(exception, IMarker.SEVERITY_ERROR);
    // }
    //
    // public void fatalError(SAXParseException exception) throws SAXException {
    // addMarker(exception, IMarker.SEVERITY_ERROR);
    // }
    //
    // public void warning(SAXParseException exception) throws SAXException {
    // addMarker(exception, IMarker.SEVERITY_WARNING);
    // }
    // }
    //
    private static final String MARKER_TYPE = "org.integratedmodelling.klab.ide.builder.klabProblem";
    //
    // private void addMarker(IFile file, String message, int lineNumber, int severity) {
    // try {
    // System.out.println("Adding custom marker: " + file + ":" + lineNumber + ":" + message);
    // IMarker marker = file.createMarker(MARKER_TYPE);
    // marker.setAttribute(IMarker.MESSAGE, message);
    // marker.setAttribute(IMarker.SEVERITY, severity);
    // if (lineNumber == -1) {
    // lineNumber = 1;
    // }
    // marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
    // } catch (CoreException e) {
    // }
    // }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor)
            throws CoreException {
        if (kind == FULL_BUILD) {
            fullBuild(monitor);
        } else {
            IResourceDelta delta = getDelta(getProject());
            if (delta == null) {
                fullBuild(monitor);
            } else {
                incrementalBuild(delta, monitor);
            }
        }
        KlabNavigator.refresh();
        return null;
    }

    protected void clean(IProgressMonitor monitor) throws CoreException {
        // delete markers set and files created
        getProject().deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
    }

    protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
        try {
            getProject().accept(new SampleResourceVisitor());
        } catch (CoreException e) {
        }
    }

    protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
        // the visitor does the work.
        delta.accept(new KlabDeltaVisitor());
    }

    public boolean isRelevant(IFile resource) {
        return resource.toString().endsWith(".kim") || resource.toString().endsWith(".kactor")
                || resource.toString().contains("META-INF/klab.properties");
    }
}
