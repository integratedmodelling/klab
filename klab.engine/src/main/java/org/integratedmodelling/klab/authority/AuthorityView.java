package org.integratedmodelling.klab.authority;

import org.integratedmodelling.klab.api.knowledge.IAuthority;

/**
 * A view of an authority that constrains the concepts that can be extracted and makes them a finite
 * set with a known size. This can be done by enumerating codes, pre-scanning all those that match a
 * particular query, or just imposing a size and allowing all concepts. The results are cached in
 * the global singleton for future reference.
 * 
 * @author Ferd
 *
 */
public class AuthorityView {

    IAuthority authority;

    public AuthorityView(IAuthority authority) {
        this.authority = authority;
    }

    /**
     * Total extent in number of possible states.
     * 
     * @return
     */
    public long size() {
        // TODO Auto-generated method stub
        return 0;
    }

}
