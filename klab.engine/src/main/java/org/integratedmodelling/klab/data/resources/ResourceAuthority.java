package org.integratedmodelling.klab.data.resources;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.rest.AuthorityReference;

/**
 * Actuator for any authority based on a resource's codelist.
 * 
 * @author Ferd
 *
 */
public class ResourceAuthority implements IAuthority {
    
    ICodelist<?,?> codelist;

    ResourceAuthority(ICodelist<?,?> codelist) {
        this.codelist = codelist;
        indexCodelist();
    }
    
    private void indexCodelist() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Identity getIdentity(String identityId, String catalog) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Capabilities getCapabilities() {

        AuthorityReference ret = new AuthorityReference(codelist.getAuthorityId());
        ret.setSearchable(true);
        ret.setFuzzy(true);
        ret.setWorldview(codelist.getWorldview());
        ret.setDescription(codelist.getDescription());
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
