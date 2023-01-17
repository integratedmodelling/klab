package org.integratedmodelling.klab.node.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.auth.KlabPermissions;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.encoding.EncodingDataBuilder;
import org.integratedmodelling.klab.engine.indexing.ResourceIndexer;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.node.NodeApplication;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.node.controllers.EngineController;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.ResourceOperationRequest;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceManager {

    @Autowired
    TicketService ticketService;

    private Timer resourceChecker;

    /*
     * The public resource catalog. This is not persisted at the moment, but simply uses the
     * physical resources in ~/.klab/resources. Only this class must be able to access it as
     * insertion and deletion need to keep the resource paths updated, which doesn't happen
     * automatically at put().
     */
    private ResourceCatalog catalog;
    private Set<String> onlineResourceUrns = Collections.synchronizedSet(new LinkedHashSet<>());
    private Set<String> offlineResourceUrns = Collections.synchronizedSet(new LinkedHashSet<>());

    public ResourceManager() {
        this.catalog = new ResourceCatalog();
        for (String resource : this.catalog.keySet()) {
            ResourceIndexer.INSTANCE.index(this.catalog.get(resource));
        }
        ResourceIndexer.INSTANCE.commitChanges();
        this.resourceChecker = new Timer(true);
        this.resourceChecker.scheduleAtFixedRate(new TimerTask(){

            @Override
            public void run() {
                checkResources();
            }
        }, 0, Long.parseLong(
                // six minutes default
                Configuration.INSTANCE.getProperty(NodeApplication.RESOURCE_CHECKING_INTERVAL_SECONDS, "360")) * 1000L);

    }

    protected synchronized void checkResources() {

        List<String> urns = new ArrayList<>(this.catalog.keySet());
        List<String> online = new ArrayList<>();
        List<String> offline = new ArrayList<>();
        for (String urn : urns) {
            boolean ok = false;
            IResource resource = catalog.get(urn);
            if (resource != null) {
                IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());
                ok = adapter != null && adapter.getEncoder().isOnline(resource, Klab.INSTANCE.getRootMonitor());
            }
            if (ok) {
                online.add(urn);
            } else {
                offline.add(urn);
            }
        }

        this.offlineResourceUrns.clear();
        this.offlineResourceUrns.addAll(offline);
        this.onlineResourceUrns.clear();
        this.onlineResourceUrns.addAll(online);
    }

    /**
     * TODO this version accepts no inputs. The one that does should encode everything, including
     * geometry and URN, into a posted KlabData object.
     * 
     * @param urn
     * @param geometry
     * @param groups
     * @return
     */
    public KlabData getResourceData(String urn, IGeometry geometry, IArtifact.Type resultType, String resultName) {

        Urn kurn = new Urn(urn);

        /*
         * The monitor passed to the encoder notifies the client through the returned data object.
         */

        if (kurn.isUniversal()) {

            IUrnAdapter adapter = Resources.INSTANCE.getUrnAdapter(kurn.getCatalog());
            if (adapter == null) {
                return null;
            }

            EncodingDataBuilder overallBuilder = new EncodingDataBuilder();
            IKlabData.Builder builder = overallBuilder;
            boolean startState = false;
            if (resultType != null && resultType.isState()) {
                builder = overallBuilder.startState(resultName, null, null);
                startState = true;
            }

            adapter.encodeData(kurn, builder, geometry,
                    new ResourceScope(adapter.getResource(urn), geometry, ((EncodingDataBuilder) builder).getMonitor()));

            if (startState) {
                builder.finishState();
            }

            return overallBuilder.buildEncoded();

        }

        IResource resource = catalog.get(kurn.getUrn());
        if (resource == null) {
            throw new IllegalArgumentException("URN " + urn + " cannot be resolved");
        }

        EncodingDataBuilder overallBuilder = new EncodingDataBuilder();
        IKlabData.Builder builder = overallBuilder;
        boolean startState = false;
        if (resultType != null && resultType.isState()) {
            builder = overallBuilder.startState(resultName, null, null);
            startState = true;
        }

        IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());
        if (adapter == null) {
            throw new KlabUnsupportedFeatureException(
                    "adapter for resource of type " + resource.getAdapterType() + " not available");
        }

        adapter.getEncoder().getEncodedData(resource, kurn.getParameters(), geometry, builder,
                new ResourceScope(resource, geometry, ((EncodingDataBuilder) builder).getMonitor()));

        if (startState) {
            builder.finishState();
        }

        return overallBuilder.buildEncoded();

    }

    public void updateResource(String urn, ResourceReference content, EngineAuthorization user, IMonitor monitor) {

        if (!canAccess(urn, user)) {
            throw new SecurityException(urn);
        }

        catalog.update(urn, content, "Updated on " + new Date() + " by " + user.getUsername());
    }

    public boolean deleteResource(String urn, EngineAuthorization user, IMonitor monitor) {

        if (!canAccess(urn, user)) {
            throw new SecurityException(urn);
        }

        IResource resource = catalog.get(urn);
        if (resource == null) {
            throw new IllegalArgumentException("URN " + urn + " cannot be resolved");
        }

        IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());
        if (adapter == null) {
            throw new KlabUnsupportedFeatureException(
                    "adapter for resource of type " + resource.getAdapterType() + " not available");
        }

        if (!adapter.getPublisher().unpublish(resource, monitor)) {
            Logging.INSTANCE.warn("unpublishing resource " + urn + " returned false: check for side effects and leftover data");
        }

        return catalog.remove(urn) != null;
    }

    public ITicket publishResource(ResourceReference resourceReference, File uploadArchive, EngineAuthorization user,
            IMonitor monitor) {

        String originalUrn = null;
        File resourcePath = null;
        if (uploadArchive != null) {
            Logging.INSTANCE.info("unpacking resource archive from " + uploadArchive);
            Pair<File, String> unpacked = catalog.unpackArchive(uploadArchive);
            resourcePath = unpacked.getFirst();
            originalUrn = unpacked.getSecond();
            Logging.INSTANCE.info("resource archive unpacked into  " + resourcePath + " for " + originalUrn);
        } else {
            originalUrn = resourceReference.getUrn();
            Logging.INSTANCE.info("publishing logical resource " + originalUrn + " from posted descriptor");
        }

        final ITicket ret = ticketService.open(ITicket.Type.ResourcePublication, "resource", originalUrn, "user",
                user.getUsername());
        final File uploadDirectory = resourcePath;

        /*
         * spawn thread that will publish and resolve the ticket with the "urn" parameter set to the
         * public URN.
         */
        new Thread(){
            @Override
            public void run() {
                try {
                    IResource resource = null;
                    if (uploadDirectory != null) {
                        resource = catalog.importResource(uploadDirectory, user);
                    } else {
                        resource = catalog.importResource(resourceReference, user);
                    }
                    if (resource != null) {
                        ResourceIndexer.INSTANCE.index(resource);
                        ResourceIndexer.INSTANCE.commitChanges();
                    }
                    ret.resolve("urn", resource.getUrn());
                } catch (Throwable t) {
                    Logging.INSTANCE.error(
                            "exception when publishing " + resourceReference.getUrn() + ": " + ExceptionUtils.getStackTrace(t));
                    ret.error("Publishing failed with exception: " + t.getMessage());
                } finally {
                    checkResources();
                }
            }
        }.start();

        return ret;
    }

    public Collection<String> getOnlineResources() {
        return onlineResourceUrns;
    }

    public IResource getResource(String urn, Set<Group> groups) {
        if (Urns.INSTANCE.isUniversal(urn)) {
            Urn u = new Urn(urn);
            IUrnAdapter adapter = Resources.INSTANCE.getUrnAdapter(u.getCatalog());
            if (adapter != null) {
                return adapter.getResource(urn);
            }
        }
        return catalog.get(urn);
    }

    public Collection<String> getCatalogs() {
        // TODO maybe some catalogs could be reserved to specific groups/users
        return catalog.getCatalogs();
    }

    public Collection<String> getNamespaces() {
        // TODO maybe some namespaces could be reserved to specific groups/users
        return catalog.getNamespaces();
    }

    public String getDefaultCatalog() {
        return catalog.getDefaultCatalog();
    }

    public String getDefaultNamespace() {
        return catalog.getDefaultNamespace();
    }

    public List<Match> queryResources(String query) {
        return ResourceIndexer.INSTANCE.query(query);
    }

    public boolean canAccess(String urn, EngineAuthorization user) {

        Urn u = new Urn(urn);

        if (Urns.INSTANCE.isUniversal(urn)) {

            /*
             * just check if the adapter is allowed
             */
            IUrnAdapter adapter = Resources.INSTANCE.getUrnAdapter(u.getCatalog());
            if (adapter != null) {
                // TODO FIXME streamline this into extensions or resource service and handle
                // adapter authorizations
                // there; refactor code in EngineController and (maybe) ResourceManager to use
                // that.
                String authorized = Configuration.INSTANCE
                        .getProperty("klab.adapter." + adapter.getName().toLowerCase() + ".auth", "");
                if (EngineController.isAuthorized(user, authorized)) {
                    return true;
                }
            }
        }

        IResource resource = catalog.get(u.getUrn());
        if (resource != null) {
            if (user.getRoles().contains(Role.ROLE_ADMINISTRATOR)) {
                return true;
            }
            if (resource.getMetadata().containsKey(IMetadata.DC_CONTRIBUTOR)
                    && user.getUsername().equals(resource.getMetadata().get(IMetadata.DC_CONTRIBUTOR))) {
                return true;
            }
            KlabPermissions permissions = KlabPermissions.empty();
            if (resource.getMetadata().containsKey(IMetadata.IM_PERMISSIONS)) {
                permissions = KlabPermissions.create(resource.getMetadata().get(IMetadata.IM_PERMISSIONS).toString());
            }
            List<String> groups = new ArrayList<>();
            for (Group group : user.getGroups()) {
                groups.add(group.getId());
            }
            return permissions.isAuthorized(user.getUsername(), groups);
        }
        return false;
    }

    /**
     * Start an operation on a resource and return a ticket.
     * 
     * @param urn
     * @param resource
     * @param principal
     * @param rootMonitor
     * @return
     */
    public TicketResponse.Ticket updateResource(String urn, ResourceOperationRequest resource, EngineAuthorization principal,
            IMonitor rootMonitor) {
        // TODO Auto-generated method stub
        return null;

    }

    /**
     * Return information about the resource, using the adapter to report on the internal details.
     * For now just use a map instead of a specialized bean.
     * 
     * @param resource
     * @return
     */
    public Map<String, Object> getResourceInfo(IResource resource) {
        Map<String, Object> ret = new LinkedHashMap<>();
        ret.put("urn", resource.getUrn());
        ret.put("online", this.getOnlineResources().contains(resource.getUrn()));
        IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());
        ret.put("adapter", adapter == null ? "UNKNOWN" : adapter.getName());
        ret.putAll(adapter.getValidator().describeResource(resource));
        return ret;
    }

    public ResourceReference contextualizeResource(ResourceReference resource, IGeometry geometry, IObservable semantics) {
        /*
         * TODO check availability with adapter and if needed, insert availability record in
         * metadata
         */
        return resource;
    }

}
