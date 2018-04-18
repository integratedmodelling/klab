package org.integratedmodelling.klab.auth;

import java.io.File;
import java.util.Collection;

import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.engine.resources.AbstractWorkspace;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.utils.StringUtils;

public class AnonymousCertificate implements ICertificate {

    private String worldview = KlabCertificate.DEFAULT_WORLDVIEW;
    private Collection<String> worldview_repositories = StringUtils
            .splitOnCommas(KlabCertificate.DEFAULT_WORLDVIEW_REPOSITORIES);

    public AnonymousCertificate() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public IWorldview getWorldview() {
        return new Worldview(worldview, Configuration.INSTANCE.getDataPath("worldview"), worldview_repositories,
                ((AbstractWorkspace) Resources.INSTANCE.getLocalWorkspace()).getProjectLocations()
                        .toArray(new File[((AbstractWorkspace) Resources.INSTANCE.getLocalWorkspace())
                                .getProjectLocations().size()]));
    }

    @Override
    public IIdentity getIdentity() {
        // no partner, no node, no token, no nothing. REST calls automatically accept the
        // anonymous user when secured as Roles.PUBLIC.
        return new KlabUser(Auth.ANONYMOUS_USER_ID, null);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String getInvalidityCause() {
        return null;
    }

}
