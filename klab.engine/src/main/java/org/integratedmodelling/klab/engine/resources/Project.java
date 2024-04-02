package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.templates.KimTemplates;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.documentation.ProjectReferences;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class Project implements IProject {

    IKimProject delegate;
    Set<String> localResourceUrns = new HashSet<>();
    private Set<ProjectReferences> references = null;
    private List<IProject> prerequisites = null;

    public Project(IKimProject project) {
        this.delegate = project;
        // TODO this is done twice in the engine. The sequencing is messy but eventually we should
        // remove this.
        synchronizeResources(project.getRoot());
    }

    @Override
    public Collection<String> getLocalResourceUrns() {
        return localResourceUrns;
    }

    public void synchronizeResources(File projectRoot) {
        File resourceDir = new File(projectRoot + File.separator + "resources");
        if (resourceDir.exists() && resourceDir.isDirectory()) {
            for (File rdir : resourceDir.listFiles()) {
                if (rdir.isDirectory()) {
                    ResourceReference rref = Resources.INSTANCE.synchronize(rdir);
                    if (rref != null) {
                        localResourceUrns.add(rref.getUrn());
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public File getRoot() {
        return delegate.getRoot();
    }

    @Override
    public List<INamespace> getNamespaces() {
        List<INamespace> namespaces = new ArrayList<>();
        for (IKimNamespace ns : delegate.getNamespaces()) {
            INamespace namespace = Namespaces.INSTANCE.getNamespace(ns.getName());
            if (namespace != null) {
                namespaces.add(namespace);
            }
        }
        return namespaces;
    }

    @Override
    public List<IProject> getPrerequisites() {
        if (this.prerequisites == null) {
            this.prerequisites = new ArrayList<>();
            for (String p : delegate.getRequiredProjectNames()) {
                
                if (p == null || p.trim().isEmpty()) {
                    continue;
                }
                
                IProject proj = Resources.INSTANCE.getProject(p);
                if (proj != null) {
                    this.prerequisites.add(proj);
                } else {
                    Klab.INSTANCE.getRootMonitor().warn("Project " + getName() + " requires unknown project " + p);
                }
            }
        }
        return this.prerequisites;
    }

    @Override
    public Version getVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public INamespace getUserKnowledge() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCanonical() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isRemote() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getOriginatingNodeId() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Create a namespace file and return it (do not load it).
     * 
     * @param namespaceId
     */
    public File createNamespace(String namespaceId, boolean createScenario, boolean isPrivate) {

        File ret = new File(getRoot() + File.separator + IKimProject.SOURCE_FOLDER + File.separator
                + namespaceId.replace('.', File.separatorChar) + ".kim");
        File npath = new File(MiscUtilities.getFilePath(ret.toString()));
        npath.mkdirs();
        try (PrintWriter out = new PrintWriter(ret)) {
            out.print((createScenario ? "scenario " : (isPrivate ? "private " : "")) + "namespace " + namespaceId + ";\n\n");
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
        
        Resources.INSTANCE.getLoader().add(ret);

        return ret;

    }

    /**
     * Create a namespace file and return it (do not load it).
     * 
     * @param namespaceId
     */
    public File createBehavior(String namespaceId, IKActorsBehavior.Type type, boolean isApp) {

        File ret = new File(getRoot() + File.separator + IKimProject.SOURCE_FOLDER + File.separator
                + namespaceId.replace('.', File.separatorChar) + ".kactor");
        File npath = new File(MiscUtilities.getFilePath(ret.toString()));
        npath.mkdirs();
        String statement = "behavior";
        switch(type) {
        case APP:
            statement = "app";
            break;
        case TRAITS:
            statement = isApp ? "library" : "trait";
            break;
        case USER:
            statement = "user";
            break;
        default:
            break;

        }
        try (PrintWriter out = new PrintWriter(ret)) {
            out.print(statement + " " + namespaceId + "\n\n");
        } catch (Exception e) {
            throw new KlabIOException(e);
        }

        return ret;

    }

    @Override
    public IKimProject getStatement() {
        return delegate;
    }

    public File createScript(String namespaceId, String scriptName, String scriptPath, String scriptType) {

        boolean isKim = "kim".equals(scriptType);

        File ret = new File(getRoot() + File.separator + IKimProject.SCRIPT_FOLDER);
        ret.mkdirs();
        ret = new File(getRoot() + File.separator + IKimProject.SCRIPT_FOLDER + File.separator
                + scriptPath.replaceAll("\\/", File.separator) + namespaceId.replace('.', File.separatorChar)
                + (isKim ? ".kim" : ".kactor"));
        ret.getParentFile().mkdirs();
        try (PrintWriter out = new PrintWriter(ret)) {
            if (isKim) {
                out.print(KimTemplates.scriptTemplate.replaceAll("__NAME__", namespaceId)
                        .replaceAll("__WORLDVIEW__", Resources.INSTANCE.getWorldview().getName())
                        .replaceAll("__SCRIPT_NAME__", scriptName));
            } else {
                out.print("app " + scriptName + "\n\naction main:\n\n");
            }
        } catch (Exception e) {
            throw new KlabIOException(e);
        }

        return ret;
    }

    public File createTestCase(String namespaceId, String scriptName, String scriptPath, String scriptType) {

        boolean isKim = "kim".equals(scriptType);

        File ret = new File(getRoot() + File.separator + IKimProject.TESTS_FOLDER);
        ret.mkdirs();
        ret = new File(getRoot() + File.separator + IKimProject.TESTS_FOLDER + File.separator
                + scriptPath.replaceAll("\\/", File.separator) + namespaceId.replace('.', File.separatorChar)
                + (isKim ? ".kim" : ".kactor"));
        ret.getParentFile().mkdirs();
        try (PrintWriter out = new PrintWriter(ret)) {
            if (isKim) {
                out.print(KimTemplates.testCaseTemplate.replaceAll("__NAME__", namespaceId)
                        .replaceAll("__WORLDVIEW__", Resources.INSTANCE.getWorldview().getName())
                        .replaceAll("__TEST_NAME__", scriptName));
            } else {
                out.print("testcase " + namespaceId + "\n\naction main:\n\n@test\naction test1:\n\n");
            }
        } catch (Exception e) {
            throw new KlabIOException(e);
        }

        return ret;
    }

    public Collection<ProjectReferences> collectReferences() {
        if (this.references == null) {
            this.references = new HashSet<>();
            this.references.add(new ProjectReferences(this));
        }
        for (IProject dep : getPrerequisites()) {
            this.references.addAll(((Project) dep).collectReferences());
        }
        return this.references;
    }

    @Override
    public IWorkspace getWorkspace() {
        return Resources.INSTANCE.getWorkspaceFor(getRoot());
    }

    @Override
    public IResource getLocalResource(String urn) {
        for (String u : getLocalResourceUrns()) {
            if (u.endsWith(":" + urn)) {
                return Resources.INSTANCE.resolveResource(u);
            }
        }
        return null;
    }

    @Override
    public List<IBehavior> getBehaviors() {
        List<IBehavior> ret = new ArrayList<>();
        for (String behaviorId : Actors.INSTANCE.getBehaviorIds()) {
            IBehavior behavior = Actors.INSTANCE.getBehavior(behaviorId);
            if (behavior.getProject().equals(this.getName()) && behavior.getDestination() == Type.BEHAVIOR) {
                ret.add(behavior);
            }
        }
        return ret;
    }

    @Override
    public List<IBehavior> getApps() {
        List<IBehavior> ret = new ArrayList<>();
        for (String behaviorId : Actors.INSTANCE.getBehaviorIds()) {
            IBehavior behavior = Actors.INSTANCE.getBehavior(behaviorId);
            if (behavior.getProject().equals(this.getName()) && behavior.getDestination() == Type.APP) {
                ret.add(behavior);
            }
        }
        return ret;
    }
    
    @Override
    public List<IBehavior> getUnitTests() {
        List<IBehavior> ret = new ArrayList<>();
        for (String behaviorId : Actors.INSTANCE.getBehaviorIds()) {
            IBehavior behavior = Actors.INSTANCE.getBehavior(behaviorId);
            if (behavior.getProject() != null && behavior.getProject().equals(this.getName()) && behavior.getDestination() == Type.UNITTEST) {
                ret.add(behavior);
            }
        }
        return ret;
    }
}
