package org.integratedmodelling.klab;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.ICertificate.Type;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IAuthenticationService;
import org.integratedmodelling.klab.auth.AnonymousEngineCertificate;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.KlabUser;
import org.integratedmodelling.klab.auth.NetworkSession;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.auth.api.IAuthenticatedIdentity;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabMissingCredentialsException;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.ObservableReference;
import org.integratedmodelling.klab.utils.FileCatalog;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormat;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.google.common.collect.Sets;

public enum Authentication implements IAuthenticationService {

    /**
     * The global instance singleton.
     */
    INSTANCE;

    String LOCAL_HUB_URL = "http://127.0.0.1:8284/hub";

    /**
     * Local catalog of all partner identities registered from the network.
     * 
     */
    Map<String, Partner> partners = Collections.synchronizedMap(new HashMap<>());

    /**
     * All identities available for inspection (sessions, users). The network session is a singleton
     * (or a zeroton) so it's not included as its ID conflicts with the user holding it.
     */
    Map<String, IIdentity> identities = Collections.synchronizedMap(new HashMap<>());

    /**
     * any external credentials taken from the .klab/credentials.json file if any.
     */
    private FileCatalog<ExternalAuthenticationCredentials> externalCredentials;

    /**
     * Projects coming from the network record the groups that enable them here. Used to check
     * permissions in user sessions that don't belong to the engine user.
     */
    private Map<String, Set<String>> projectPermissions = Collections.synchronizedMap(new HashMap<>());

    /**
     * Can be installed by a service to translate the principal in any authenticated call to a user
     * identity.
     */
    private Function<Principal, IUserIdentity> principalTranslator = null;

    /**
     * Status of a user wrt. the network. Starts at UNKNOWN.
     */
    public enum Status {
        /**
         * User is authenticated locally but not online, either for lack of authentication or lack
         * of network connection/
         */
        OFFLINE,
        /**
         * User is authenticated and online with the network.
         */
        ONLINE,
        /**
         * User has not been submitted for authentication yet.
         */
        UNKNOWN
    }

    /**
     * ID for an anonymous user. Unsurprising.
     */
    public static final String ANONYMOUS_USER_ID = "anonymous";

    /**
     * Id for anonymous user who is connecting from the local host. Will have admin privileges.
     */
    public static final String LOCAL_USER_ID = "local";

    private Client client = Client.create();

    private ICertificate certificate;

    private Authentication() {

        /*
         * Create or load the external credentials file
         */
        File file = new File(Configuration.INSTANCE.getDataPath() + File.separator + "credentials.json");
        if (!file.exists()) {
            try {
                FileUtils.writeStringToFile(file, "{}", StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new KlabIOException(e);
            }
        }
        externalCredentials = FileCatalog.create(file, ExternalAuthenticationCredentials.class,
                ExternalAuthenticationCredentials.class);

//        Services.INSTANCE.registerService(this, IAuthenticationService.class);
    }

    /**
     * If the partner mentioned in the response bean is already known, return it, otherwise create
     * it.
     * 
     * @param partner
     * @return a partner identity for the passed description
     */
    public Partner requirePartner(IdentityReference partner) {
        Partner ret = partners.get(partner.getId());
        if (ret == null) {
            ret = new Partner(partner);
            partners.put(partner.getId(), ret);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IIdentity> T getIdentity(String id, Class<T> type) {
        IIdentity ret = identities.get(id);
        if (ret != null && type.isAssignableFrom(ret.getClass())) {
            return (T) ret;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IIdentity> T getAuthenticatedIdentity(Class<T> type) {
        for(IIdentity id : identities.values()) {
            if (type.isAssignableFrom(id.getClass())) {
                return (T) id;
            }
        }
        return null;
    }

    /**
     * Install a server-specific strategy to translate the principal into a user identity
     * 
     * @param function
     */
    public void setPrincipalTranslator(Function<Principal, IUserIdentity> function) {
        this.principalTranslator = function;
    }

    /**
     * Register a new session and inject a close listener that will de-register it on expiration.
     * 
     * @param session
     */
    public void registerSession(ISession session) {

        session.addListener(new ISession.Listener(){
            @Override
            public void onClose(ISession session) {
                identities.remove(session.getId());
            }
        });
        identities.put(session.getId(), session);
    }

    /**
     * Register any new identity with this.
     * 
     * @param identity
     */
    public void registerIdentity(IIdentity identity) {
        identities.put(identity.getId(), identity);
    }

    /**
     * Util to extract an identity from the principal of a Spring request. TODO make another to
     * return a specific type or throw an authorization exception
     * 
     * @param principal
     * @return the identity or null
     */
    public IIdentity getIdentity(Principal principal) {
        if (principal instanceof PreAuthenticatedAuthenticationToken
                && ((PreAuthenticatedAuthenticationToken) principal).getPrincipal() instanceof IIdentity) {
            return (IIdentity) ((PreAuthenticatedAuthenticationToken) principal).getPrincipal();
        }
        return null;
    }

    /**
     * If a session with the default flag was started, return it.
     * 
     * @return
     */
    public ISession getDefaultSession() {
        for(IIdentity id : identities.values()) {
            if (id instanceof ISession && ((ISession) id).isDefault()) {
                return (ISession) id;
            }
        }
        return null;
    }

    /**
     * Check which groups we adopt that define a worldview and extract the root worldview name from
     * the projects that compose it. Makes lots of assumptions.
     * 
     * @return
     */
    public String getWorldview(IIdentity user) {
        String ret = null;
        if (user instanceof IUserIdentity) {
            for(Group group : ((IUserIdentity) user).getGroups()) {
                if (group.isWorldview()) {
                    for(String project : group.getProjectUrls()) {
                        String pid = MiscUtilities.getURLBaseName(project);
                        if (ret == null && !pid.contains(".")) {
                            ret = pid;
                            break;
                        }
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public IUserIdentity authenticate(ICertificate certificate) throws KlabAuthorizationException {

        IUserIdentity ret = null;
        EngineAuthenticationResponse authentication = null;

        if (certificate instanceof AnonymousEngineCertificate) {
            // no partner, no node, no token, no nothing. REST calls automatically accept
            // the
            // anonymous user when secured as Roles.PUBLIC.
            Logging.INSTANCE.info("No user certificate: continuing in anonymous offline mode");

            return new KlabUser(Authentication.ANONYMOUS_USER_ID, null);
        }

        if (!certificate.isValid()) {
            /*
             * expired or invalid certificate: throw away the identity, continue as anonymous.
             */
            Logging.INSTANCE.info("Certificate is invalid or expired: continuing in anonymous offline mode");

            return new KlabUser(Authentication.ANONYMOUS_USER_ID, null);
        }

        if (certificate.getType() == Type.NODE && getAuthenticatedIdentity(INodeIdentity.class) != null) {
            ret = new KlabUser(certificate.getProperty(ICertificate.KEY_NODENAME), getAuthenticatedIdentity(INodeIdentity.class));
            registerIdentity(ret);
            return ret;
        }

        String authenticationServer = null;

        /**
         * TODO try new hub @ https://integratedmodelling.org/hub/api/auth-cert/engine"
         * 
         * if experimental property set in properties
         */
        // try local hub, let fail if not active
        if (client.ping(LOCAL_HUB_URL)) {
            authenticationServer = LOCAL_HUB_URL;
            Logging.INSTANCE.info("local hub is available: trying local authentication");
        } else {
            authenticationServer = certificate.getProperty(KlabCertificate.KEY_PARTNER_HUB);
        }

        if (authenticationServer != null) {

            Logging.INSTANCE.info("authenticating " + certificate.getProperty(KlabCertificate.KEY_USERNAME) + " with hub "
                    + authenticationServer);

            /*
             * Authenticate with server(s). If authentication fails because of a 403, invalidate the
             * certificate. If no server can be reached, certificate is valid but engine is offline.
             */
            EngineAuthenticationRequest request = new EngineAuthenticationRequest(
                    certificate.getProperty(KlabCertificate.KEY_USERNAME), certificate.getProperty(KlabCertificate.KEY_SIGNATURE),
                    certificate.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE),
                    certificate.getProperty(KlabCertificate.KEY_CERTIFICATE), certificate.getLevel(),
                    certificate.getProperty(KlabCertificate.KEY_AGREEMENT));

            // add email if we have it, so the hub can notify in any case if so configured
            request.setEmail(certificate.getProperty(KlabCertificate.KEY_USERNAME));

            try {
                authentication = client.authenticateEngine(authenticationServer, request);
            } catch (Throwable e) {
                Logging.INSTANCE.error("authentication failed for user " + certificate.getProperty(KlabCertificate.KEY_USERNAME)
                        + ": " + e.getMessage());
                // TODO inspect exception; fatal if 403, warn and proceed offline otherwise
                System.out.println("AUTH EXCEPTION is " + MiscUtilities.getExceptionPrintout(e));
            }
        }

        if (authentication != null) {

            DateTime expiry = null;
            /*
             * check expiration
             */
            try {
                expiry = DateTime.parse(authentication.getUserData().getExpiry());
            } catch (Throwable e) {
                Logging.INSTANCE.error("bad date or wrong date format in certificate. Please use latest version of software.");
                return null;
            }
            if (expiry == null) {
                Logging.INSTANCE.error("certificate has no expiration date. Please obtain a new certificate.");
                return null;
            } else if (expiry.isBeforeNow()) {
                Logging.INSTANCE.error("certificate expired on " + expiry + ". Please obtain a new certificate.");
                return null;
            }
        }

        /*
         * build the identity
         */
        if (certificate.getType() == Type.ENGINE) {

            // if we have connected, insert network session identity
            if (authentication != null) {

                HubReference hubNode = authentication.getHub();
                Hub hub = new Hub(hubNode);
                hub.setOnline(true);
                NetworkSession networkSession = new NetworkSession(authentication.getUserData().getToken(), hub);

                ret = new KlabUser(authentication.getUserData(), authentication.getAuthentication(), networkSession);

                Network.INSTANCE.buildNetwork(authentication);

                Logging.INSTANCE.info("User " + ((IUserIdentity) ret).getUsername() + " logged in through hub " + hubNode.getId()
                        + " owned by " + hubNode.getPartner().getId());

                Logging.INSTANCE.info("The following nodes are available:");
                for(INodeIdentity n : Network.INSTANCE.getNodes()) {
                    Duration uptime = new Duration(n.getUptime());
                    DateTime boottime = DateTime.now(DateTimeZone.UTC).minus(uptime.toPeriod());
                    IPartnerIdentity partner = n.getParentIdentity();
                    Logging.INSTANCE.info("   " + n.getName() + " online since " + boottime);
                    Logging.INSTANCE.info("      " + partner.getName() + " (" + partner.getEmailAddress() + ")");
                    Logging.INSTANCE.info("      " + "online " + PeriodFormat.getDefault().print(uptime.toPeriod()));
                }

            } else {

                // offline node with no partner
                IIdentity.Type type;
                if (certificate.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE) != null) {
                    type = IIdentity.Type.valueOf(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE));
                } else {
                    type = IIdentity.Type.NODE;
                }
                Node node = new Node(certificate.getProperty(KlabCertificate.KEY_NODENAME), type, null);
                ((Node) node).setOnline(false);
                ret = new KlabUser(certificate.getProperty(KlabCertificate.KEY_USERNAME), node);

                Logging.INSTANCE.info("User " + ((IUserIdentity) ret).getUsername() + " activated in offline mode");
            }

            ((KlabUser) ret).setOnline(authentication != null);

        } else {
            throw new KlabAuthorizationException(
                    "wrong certificate for an engine: cannot create identity of type " + certificate.getType());
        }

        if (ret != null) {
            registerIdentity(ret);
        }

        this.certificate = certificate;

        return ret;
    }

    /**
     * Attempts to re-authenticate the engine using a cached version of the certificate
     */
    public IUserIdentity reauthenticate() {
      if (this.certificate == null) {
        return null;
      }
      return authenticate(this.certificate);
    }

    /**
     * Returns a credential for the selected host. It can be an ID or an URL as long as it is presented in the credentials.
     * @param endpoint as a URL or an ID defined in the credentials
     * @return
     */
    public ExternalAuthenticationCredentials getCredentials(String endpoint) {
        if (endpoint.startsWith("http://") || endpoint.startsWith("https://")) {
            return getCredentialsByUrl(endpoint);
        }
        return getCredentialsById(endpoint);
    }

    /**
     * Returns a credential for the selected host URL. If multiple host URL are present, the first in alphabetical order will be returned.
     * @param hostUrl
     * @return the credential or null if not present
     */
    private ExternalAuthenticationCredentials getCredentialsByUrl(String hostUrl) {
        return externalCredentials.values().stream().filter(credential -> credential.getURL().equals(hostUrl))
                .sorted().findFirst().orElse(null);
    }

    /**
     * Returns a credential for the selected ID.
     * @param id
     * @return the credential or null if not present
     */
    private ExternalAuthenticationCredentials getCredentialsById(String id) {
        return externalCredentials.get(id);
    }

    /**
     * Return a new credential provider that knows the credentials saved into the k.LAB database and
     * will log appropriate messages when credentials aren't found.
     * 
     * @return
     */
    public CredentialsProvider getCredentialProvider() {

        return new CredentialsProvider(){

            @Override
            public void clear() {
            }

            @Override
            public Credentials getCredentials(AuthScope arg0) {

                String auth = arg0.getHost() + (arg0.getPort() == 80 ? "" : (":" + arg0.getPort()));

                ExternalAuthenticationCredentials credentials = externalCredentials.get(auth);

                if (credentials == null) {
                    throw new KlabMissingCredentialsException(auth);
                }

                return new UsernamePasswordCredentials(credentials.getCredentials().get(0), credentials.getCredentials().get(1));
            }

            @Override
            public void setCredentials(AuthScope arg0, org.apache.http.auth.Credentials arg1) {
                // TODO Auto-generated method stub

            }
        };
    }

    public void setProjectPermissions(String projectId, Set<String> groups) {
        this.projectPermissions.put(projectId, groups);
    }

    public Set<String> getProjectPermissions(String projectId) {
        if (this.projectPermissions.containsKey(projectId)) {
            return this.projectPermissions.get(projectId);
        }
        return Collections.emptySet();
    }

    public boolean canAccess(IUserIdentity user, String projectId) {
        Set<String> userGroups = user.getGroups().stream().map((group) -> group.getName()).collect(Collectors.toSet());
        Set<String> permissions = this.projectPermissions.get(projectId);
        if (permissions != null && !permissions.isEmpty()) {
            return Sets.intersection(permissions, userGroups).size() > 0;
        }
        return true;
    }

    public List<ObservableReference> getDefaultObservables(IIdentity identity) {
        List<ObservableReference> ret = new ArrayList<>();
        IUserIdentity user = identity.getParentIdentity(IUserIdentity.class);
        if (user != null) {
            for(Group group : user.getGroups()) {
                ret.addAll(group.getObservables());
            }
        }
        return ret;
    }

    @Override
    public Collection<ISession> getSessions() {
        List<ISession> ret = new ArrayList<>();
        for(IIdentity identity : identities.values()) {
            if (identity instanceof ISession) {
                ret.add((ISession) identity);
            }
        }
        return ret;
    }

    /**
     * This method has been created so legacy credential scheme is updated into the new credentials scheme if possible.
     * The main changes are:
     * - Use of a unique ID instead of the url as key
     * - Move the URL as its own parameter
     * - Changes on the scheme of oidc credentials
     */
    public void updateLegacyCredentials() {
        boolean isLegacyDetected = externalCredentials.values().stream()
                .anyMatch(credential -> credential.getURL() == null || credential.getURL().isEmpty());
        if (!isLegacyDetected) {
            return;
        }
        Set<String> legacyCredentialIds = new HashSet<String>();
        externalCredentials.keySet().parallelStream().forEach(id -> {
            if (externalCredentials.get(id).getURL() != null && !externalCredentials.get(id).getURL().isEmpty()) {
                return;
            }
            if (!(id.startsWith("http://") || id.startsWith("https://"))) {
                legacyCredentialIds.add(id);
                return;
            }
            ExternalAuthenticationCredentials credentials = externalCredentials.get(id);
            credentials.setURL(id);
            // Former scheme of oidc: "url", "grant_type", "client_id", "client_secrets", "scope", "provider_id"
            if (credentials.getScheme().equals("oidc")) {
                credentials.getCredentials().remove(4); // Former scope
                credentials.getCredentials().remove(0); // Former url
            }
            try {
                String hostname = new URL(id).getHost();
                externalCredentials.put(hostname, credentials);
                legacyCredentialIds.add(id);
            } catch (MalformedURLException e) {
                // Do nothing, this credential will be removed
            }
        });
        // We remove the old credentials by their IDs
        legacyCredentialIds.forEach(id -> externalCredentials.remove(id));
        externalCredentials.write();
    }

    public void addExternalCredentials(String id, ExternalAuthenticationCredentials credentials) {
        externalCredentials.put(id, credentials);
        externalCredentials.write();
    }

    public IUserIdentity getUserIdentity(Principal principal) {
        if (principalTranslator != null) {
            return principalTranslator.apply(principal);
        }
        throw new KlabInternalErrorException(
                "no adapter for the principal identity has been installed in the Authentication instance");
    }

    public boolean hasEitherGroup(IUserIdentity user, String... groups) {
        for(Group g : user.getGroups()) {
            for(String grp : groups) {
                if (g.getName().equals(grp)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasRole(Object user, Role role) {
        if (user instanceof IAuthenticatedIdentity) {
            return ((IAuthenticatedIdentity) user).getRoles().contains(role);
        }
        return false;
    }

    public Collection<String> getWorldviewRepositories(IUserIdentity user) {
        Set<String> ret = new LinkedHashSet<>();
        if (user != null) {
            for(Group group : user.getGroups()) {
                if (group.isWorldview()) {
                    ret.addAll(group.getProjectUrls());
                }
            }
        }
        return ret;
    }

    // public boolean hasEitherRole(IUserIdentity user, String... groups) {
    // for (Group g : user.getRoles()) {
    // for (String grp : groups) {
    // if (g.getId().equals(grp)) {
    // return true;
    // }
    // }
    // }
    // return false;
    // }

}
