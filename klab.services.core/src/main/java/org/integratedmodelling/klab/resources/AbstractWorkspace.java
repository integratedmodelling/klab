package org.integratedmodelling.klab.resources;
//package org.integratedmodelling.klab.engine.resources;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//import org.integratedmodelling.kim.api.IKimLoader;
//import org.integratedmodelling.kim.api.IKimProject;
//import org.integratedmodelling.kim.model.KimWorkspace;
//import org.integratedmodelling.klab.Models;
//import org.integratedmodelling.klab.Resources;
//import org.integratedmodelling.klab.api.knowledge.IProject;
//import org.integratedmodelling.klab.api.knowledge.IWorkspace;
//import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
//import org.integratedmodelling.klab.exceptions.KlabException;
//import org.integratedmodelling.klab.exceptions.KlabIOException;
//import org.integratedmodelling.klab.utils.FileUtils;
//
//public abstract class AbstractWorkspace implements IWorkspace {
//
//    protected KimWorkspace delegate;
//
//    AbstractWorkspace() {
//    }
//
//    public AbstractWorkspace(String name, File root) {
//        delegate = new KimWorkspace(root, name);
//    }
//
//    @Override
//    public String getName() {
//        return delegate.getName();
//    }
//
//    public Collection<File> getProjectLocations() {
//        return delegate.getProjectLocations();
//    }
//
//    public Collection<String> getProjectNames() {
//        return delegate.getProjectNames();
//    }
//
//    protected void loadResources() {
//        /*
//         * Read resources before loading. 
//         */
//        for (File projectRoot : getProjectLocations()) {
//            File resourceDir = new File(projectRoot + File.separator + "resources");
//            if (resourceDir.exists() && resourceDir.isDirectory()) {
//                for (File rdir : resourceDir.listFiles()) {
//                    if (rdir.isDirectory()) {
////                    	Logging.INSTANCE.info("read resource " + rdir);
//                        /* ResourceReference resource = */Resources.INSTANCE.synchronize(rdir);
//                        
//                    }
//                }
//            }
//        }
//    }
//    
//    protected void readProjects() {
//        delegate.readProjects();
//    }
//
//    /**
//     * Add a project from a local directory.
//     * 
//     * @param root
//     * @return
//     */
//    public IProject loadProject(String project, IMonitor monitor) {
//        File projectFile = new File(getRoot() + File.separator + project);
//        if (!projectFile.isDirectory()) {
//            return null;
//        }
//        IKimProject ret = delegate.loadProject(projectFile);
//        if (ret != null) {
//            Resources.INSTANCE.getLoader().load(Collections.singleton(ret));
//        }
//        return ret == null ? null : Resources.INSTANCE.retrieveOrCreate(ret);
//    }
//
//    @Override
//    public IKimLoader load(IMonitor monitor) throws KlabException {
//        loadResources();
//        return delegate.load();
//    }
//
//    @Override
//    public IKimLoader load(IKimLoader loader, IMonitor monitor) throws KlabException {
//        loadResources();
//        return delegate.load(loader);
//    }
//
//    @Override
//    public File getRoot() {
//        return delegate.getRoot();
//    }
//
//    public Collection<IProject> getProjects() {
//        List<IProject> ret = new ArrayList<>();
//        for (String projectId : delegate.getProjectNames()) {
//            ret.add(Resources.INSTANCE.retrieveOrCreate(delegate.getProject(projectId)));
//        }
//        return ret;
//    }
//
//    @Override
//    public IProject getProject(String projectId) {
//        IKimProject ret = delegate.getProject(projectId);
//        return ret == null ? null : Resources.INSTANCE.retrieveOrCreate(ret);
//    }
//
//    public void deleteProject(IProject project) {
//        
//        /*
//         * unload all resources and namespaces belonging to this project
//         */
//        Resources.INSTANCE.unregisterProjectResources(project);
//        Models.INSTANCE.releaseProject(project);
//        
//        delegate.deleteProject(((Project) project).delegate);
//        try {
//            FileUtils.deleteDirectory(project.getRoot());
//        } catch (IOException e) {
//            throw new KlabIOException(e);
//        }
//    }
//
//}
