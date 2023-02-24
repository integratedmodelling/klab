package org.integratedmodelling.klab.services.reasoner.owl;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.KConcept;

/**
 * Simple cache for is() operations to minimize the use of the reasoner.
 * 
 * @author Ferd
 *
 */
public class ReasonerCache {

	ThreadLocal<Map<String, Boolean>> cache = new ThreadLocal<>();
	
	public ReasonerCache() {
		cache.set(new HashMap<>());
	}
	
	public boolean is(KConcept a, KConcept b) {
		String desc = a.getUrn() + "|" + b.getUrn();
		Boolean ret = cache.get().get(desc);
		if (ret == null) {
			ret = a.is(b);
			cache.get().put(desc, ret);
		}
		return ret;
	}
	
}
