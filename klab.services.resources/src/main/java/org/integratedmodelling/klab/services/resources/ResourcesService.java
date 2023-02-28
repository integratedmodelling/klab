package org.integratedmodelling.klab.services.resources;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.xtext.testing.IInjectorProvider;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kdl.model.Kdl;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.KimLoader;
import org.integratedmodelling.kim.model.KimLoader.NamespaceDescriptor;
import org.integratedmodelling.klab.api.data.KKlabData;
import org.integratedmodelling.klab.api.knowledge.KResource;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KContextScope;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KScope;
import org.integratedmodelling.klab.api.knowledge.organization.KProject;
import org.integratedmodelling.klab.api.knowledge.organization.KWorkspace;
import org.integratedmodelling.klab.api.lang.kactors.KKActorsBehavior;
import org.integratedmodelling.klab.api.lang.kdl.KKdlDataflow;
import org.integratedmodelling.klab.api.lang.kim.KKimNamespace;
import org.integratedmodelling.klab.api.services.KResources;
import org.integratedmodelling.klab.configuration.Configuration;
import org.integratedmodelling.klab.services.resources.configuration.ResourcesConfiguration;
import org.integratedmodelling.klab.services.resources.lang.KactorsInjectorProvider;
import org.integratedmodelling.klab.services.resources.lang.KdlInjectorProvider;
import org.integratedmodelling.klab.services.resources.lang.KimInjectorProvider;
import org.integratedmodelling.klab.utils.Utils;
import org.springframework.stereotype.Service;

import com.google.inject.Injector;

@Service
public class ResourcesService implements KResources, KResources.Admin {

    private static boolean languagesInitialized;
    private KimLoader kimLoader;
    private ResourcesConfiguration configuration = new ResourcesConfiguration();
    
    Map<String, KProject> localProjects = Collections.synchronizedMap(new HashMap<>());
    Map<String, KWorkspace> localWorkspaces = Collections.synchronizedMap(new HashMap<>());
    Map<String, KKimNamespace> localNamespaces = Collections.synchronizedMap(new HashMap<>());
    Map<String, KKActorsBehavior> localBehaviors = Collections.synchronizedMap(new HashMap<>());

    public ResourcesService() {
        initializeLanguageServices();
        File config = new File(Configuration.INSTANCE.getDataPath() + File.separator + "resources.yaml");
        if (config.exists()) {
            configuration = Utils.YAML.load(config, ResourcesConfiguration.class);
        }
    }
    
    private void saveConfiguration() {
        File config = new File(Configuration.INSTANCE.getDataPath() + File.separator + "resources.yaml");
        Utils.YAML.save(this.configuration, config);
    }

    private void initializeLanguageServices() {

        if (!languagesInitialized) {

            /*
             * set up access to the k.IM grammar
             */
            IInjectorProvider kimInjectorProvider = new KimInjectorProvider();
            Injector kimInjector = kimInjectorProvider.getInjector();
            if (kimInjector != null) {
                Kim.INSTANCE.setup(kimInjector);
            }

            this.kimLoader = new KimLoader((nss) -> loadNamespaces(nss));

            /*
             * k.DL....
             */
            IInjectorProvider kdlInjectorProvider = new KdlInjectorProvider();
            Injector kdlInjector = kdlInjectorProvider.getInjector();
            if (kdlInjector != null) {
                Kdl.INSTANCE.setup(kdlInjector);
            }

            /*
             * ...and k.Actors
             */
            IInjectorProvider kActorsInjectorProvider = new KactorsInjectorProvider();
            Injector kActorsInjector = kActorsInjectorProvider.getInjector();
            if (kActorsInjector != null) {
                KActors.INSTANCE.setup(kActorsInjector);
            }

            languagesInitialized = true;
        }

    }

    private void loadNamespaces(List<NamespaceDescriptor> namespaces) {
        System.out.println("ohoho");
    }

    @Override
    public KProject resolveProject(String urn, KScope scope) {

        /*
         * must be a known project, either here or on a federated service.
         */

        return null;
    }

    @Override
    public KKActorsBehavior resolveBehavior(String urn, KScope scope) {

        return null;
    }

    @Override
    public KResource resolveResource(String urn, KScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KResource contextualizeResource(KResource originalResource, KContextScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKlabData contextualize(KResource contextualizedResource, KScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKdlDataflow resolveDataflow(String urn, KScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KKimNamespace> getDependents(String namespaceId) {
        return null;
    }

    @Override
    public List<KKimNamespace> getPrecursors(String namespaceId) {
        return null;
    }

    @Override
    public boolean addProjectToLocalWorkspace(String workspaceName, String projectUrl) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void removeProjectFromLocalWorkspace(String workspaceName, String projectName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeWorkspace(String workspaceName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<KWorkspace> getWorkspaces() {
        // TODO Auto-generated method stub
        return null;
    }
}
