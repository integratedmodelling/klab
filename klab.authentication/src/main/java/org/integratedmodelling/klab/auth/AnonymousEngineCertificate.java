package org.integratedmodelling.klab.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.ICertificate;

public class AnonymousEngineCertificate implements ICertificate {

    private String worldview = KlabCertificate.DEFAULT_WORLDVIEW;
    private Map<String, Set<String>> worldview_repositories = new HashMap<>();

    public AnonymousEngineCertificate() {
//        for (String w : StringUtil
//                .splitOnCommas(KlabCertificate.DEFAULT_WORLDVIEW_REPOSITORIES)) {
//            worldview_repositories.put(w, new HashSet<>());
//        }
    }

    @Override
    public String getWorldview() {
        return worldview;
    }

    @Override
    public Map<String, Set<String>> getWorldviewRepositories(String worldview) {
        return worldview_repositories;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String getInvalidityCause() {
        return null;
    }

    @Override
    public String getProperty(String property) {
        switch(property) {
        case KEY_PARTNER_HUB:
            return "http://127.0.0.1:8284/klab";
        case KEY_CERTIFICATE_LEVEL:
            return ICertificate.Level.ANONYMOUS.name();
        case KEY_USERNAME:
            return "anonymous";
        }
        return null;
    }

    @Override
    public Type getType() {
        return Type.ENGINE;
    }

    @Override
    public Level getLevel() {
        return Level.ANONYMOUS;
    }

}
