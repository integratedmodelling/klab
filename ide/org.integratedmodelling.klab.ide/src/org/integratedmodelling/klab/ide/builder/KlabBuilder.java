package org.integratedmodelling.klab.ide.builder;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.rest.ProjectModificationNotification;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class KlabBuilder extends IncrementalProjectBuilder {

    public static final String BUILDER_ID = "org.integratedmodelling.klab.ide.klabBuilder";

    /**
     * This keeps the Xtext internal model in sync with the loader and ensures that
     * dependencies, which are handled outside of Xtext, are refreshed.
     * 
     * @author ferdinando.villa
     *
     */
    class KlabDeltaVisitor implements IResourceDeltaVisitor {

        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            IResource resource = delta.getResource();
            switch (delta.getKind()) {
            case IResourceDelta.ADDED:
            	System.out.println("ADDED: " + delta);
                //				if (resource instanceof IFile && isRelevant((IFile) resource)) {
                //					Activator.loader().add(((IFile) resource).getLocation().toFile());
                //					Activator.post(IMessage.MessageClass.ProjectLifecycle, IMessage.Type.ProjectFileModified,
                //							new ProjectModificationNotification(ProjectModificationNotification.Type.ADDITION,
                //									((IFile) resource).getLocation().toFile()));
                //				}
                break;
            case IResourceDelta.REMOVED:
            	System.out.println("REMOVED: " + delta);
                // just close the editor; everything else is dealt with by the loader and callback
                break;
            case IResourceDelta.CHANGED:
            	System.out.println("CHANGED: " + delta);
                if (resource instanceof IFile && isRelevant((IFile) resource)) {
                    int i = 0;
                    for (IKimNamespace ns : Activator.loader().touch(((IFile) resource).getLocation().toFile())) {
                        // looks like nothing will make the editor contents reload. Still unsure about the viewer.
                        //					    if (i++ == 0) {
                        //					        // don't reload the first
                        //					        continue;
                        //					    }
                        //						// ACHTUNG this will probably call back here, unleashing a linear but useless
                        //						// chain of multiple
                        //						// reloads. Should find a way to prevent more touching besides refresh of the
                        //						// loader's contents. Use a thread with
                        //						// a context setting to control the recurse parameter in the loader so that the
                        //						// inner touch() only affects one resource at a time.
                        //					    File file = ns.getFile();
                        //					    if (file == null) {
                        //					        System.out.println("DIOCARO file is null " + ns.getName());
                        //					    } else {
                        //					        IFile diocan = Eclipse.INSTANCE.getIFile(file);
                        //					        if (diocan == null) {
                        //	                            System.out.println("DIOCANTA ifile is null " + file);
                        // these happen during init - IFile not there yet because the UI isn't up but the builder is active.
                        //					        } else {
                        //					            diocan.touch(new NullProgressMonitor());
                        //					        }
                        //					    }
                    }
                    Activator.post(IMessage.MessageClass.ProjectLifecycle, IMessage.Type.ProjectFileModified,
                            new ProjectModificationNotification(ProjectModificationNotification.Type.CHANGE,
                                    ((IFile) resource).getLocation().toFile()));
                }
                break;
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

    class XMLErrorHandler extends DefaultHandler {

        private IFile file;

        public XMLErrorHandler(IFile file) {
            this.file = file;
        }

        private void addMarker(SAXParseException e, int severity) {
            KlabBuilder.this.addMarker(file, e.getMessage(), e.getLineNumber(), severity);
        }

        public void error(SAXParseException exception) throws SAXException {
            addMarker(exception, IMarker.SEVERITY_ERROR);
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            addMarker(exception, IMarker.SEVERITY_ERROR);
        }

        public void warning(SAXParseException exception) throws SAXException {
            addMarker(exception, IMarker.SEVERITY_WARNING);
        }
    }

    private static final String MARKER_TYPE = "org.integratedmodelling.klab.ide.builder.klabProblem";

    private void addMarker(IFile file, String message, int lineNumber, int severity) {
        try {
        	System.out.println("Adding custom marker: " + file + ":" + lineNumber + ":" + message);
            IMarker marker = file.createMarker(MARKER_TYPE);
            marker.setAttribute(IMarker.MESSAGE, message);
            marker.setAttribute(IMarker.SEVERITY, severity);
            if (lineNumber == -1) {
                lineNumber = 1;
            }
            marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
        } catch (CoreException e) {
        }
    }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
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

    // void checkXML(IResource resource) {
    // if (resource instanceof IFile && resource.getName().endsWith(".xml")) {
    // IFile file = (IFile) resource;
    // deleteMarkers(file);
    // XMLErrorHandler reporter = new XMLErrorHandler(file);
    // try {
    // getParser().parse(file.getContents(), reporter);
    // } catch (Exception e1) {
    // }
    // }
    // }

    //	private void deleteMarkers(IFile file) {
    //		try {
    //			file.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
    //		} catch (CoreException ce) {
    //		}
    //	}

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
        return resource.toString().endsWith(".kim") || resource.toString().contains("META-INF/klab.properties");
    }
}
