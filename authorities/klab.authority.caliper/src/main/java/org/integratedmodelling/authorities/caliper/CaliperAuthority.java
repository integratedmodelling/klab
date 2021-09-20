package org.integratedmodelling.authorities.caliper;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Authority;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.rest.AuthorityReference;

@Authority(id = CaliperAuthority.ID, description = CaliperAuthority.DESCRIPTION, catalogs = {"ISIC4", "ICC10", "ICC11",
        "WCACROPS", "M49", "FPCD", "SDGEO", "FOODEX2", "CPC20", "CPC21", "CPC21AG", "CPC21FERT", "FCL", "HS",
        "WRB"}, version = Version.CURRENT)
public class CaliperAuthority implements IAuthority {

    public static final String ID = "CALIPER";
    public static final String DESCRIPTION = "";

    private static final String SPARQL_ENDPOINT = "https://stats-class.fao.uniroma2.it/AllVoc_Sparql/";
    
    @Override
    public Identity getIdentity(String identityId, String catalog) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Capabilities getCapabilities() {
        AuthorityReference ret = new AuthorityReference();
        ret.setSearchable(true);
        ret.setFuzzy(true);
        return ret;
    }
    @Override
    public void document(String identityId, String mediaType, OutputStream destination) {
        // TODO Auto-generated method stub
    }

    @Override
    public List<Identity> search(String query, String catalog) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean setup(Map<String, String> options) {
        // TODO Auto-generated method stub
        return false;
    }

}
