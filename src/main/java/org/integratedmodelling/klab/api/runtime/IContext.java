package org.integratedmodelling.klab.api.runtime;

import org.integratedmodelling.klab.api.auth.IContextIdentity;
import org.integratedmodelling.klab.api.knowledge.IIndividual;
import org.integratedmodelling.klab.api.knowledge.IOntology;

public interface IContext extends IContextIdentity {

	ITask observe(String urn);
	
	IIndividual instantiate(IOntology ontology);

}
