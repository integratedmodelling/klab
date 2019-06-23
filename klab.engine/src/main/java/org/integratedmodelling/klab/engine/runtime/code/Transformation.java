package org.integratedmodelling.klab.engine.runtime.code;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.knowledge.IObservable;

/**
 * A ComputableResource for computations linked to transforming models (e.g.
 * normalizers), which must be specialized to accept the ID of the transformed
 * observable at each use.
 * 
 * @author Ferd
 *
 */
public class Transformation extends ComputableResource {

	private static final long serialVersionUID = 6352140622030486134L;

	public Transformation(IComputableResource resource, IObservable transformedObservable) {
		KimServiceCall call = new KimServiceCall(resource.getServiceCall().getName(),
				resource.getServiceCall().getParameters());
		call.getParameters().put("artifact", transformedObservable.getName());
		setServiceCall(call);
		setType(Type.SERVICE);
	}

}
