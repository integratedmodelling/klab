package org.integratedmodelling.klab.services.resources.lang.kim;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.klab.api.lang.kim.KKimNamespace;
import org.integratedmodelling.klab.api.lang.kim.impl.KimNamespace;

/**
 * For now just a translator of a legacy IKimNamespace into a serializable KimNamespace. Later we'll
 * start directly from the ECore beans and remove the legacy classes.
 * 
 * @author Ferd
 *
 */
public class KimParser {

    public static KKimNamespace parse(IKimNamespace namespace) {

        KimNamespace ret = new KimNamespace();
        
        

        return ret;
    }

}
