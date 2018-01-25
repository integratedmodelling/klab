package org.integratedmodelling.klab.api.runtime;

import org.integratedmodelling.klab.api.auth.IContextIdentity;
import org.integratedmodelling.klab.api.knowledge.IIndividual;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.observations.ISubject;

public interface IContext extends IContextIdentity {

    ISubject getSubject();
    
	ITask observe(String urn);
	
	IIndividual instantiate(IOntology ontology);

}
