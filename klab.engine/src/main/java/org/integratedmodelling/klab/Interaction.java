package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IComputableResource.InteractiveParameter;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.services.IInteractionService;

public enum Interaction implements IInteractionService {

	INSTANCE;
	
	/**
	 * Describe the passed parameter for the passed service call.
	 * 
	 * @param call
	 * @param parameter
	 * @return the interactive parameter descriptor.
	 */
	public InteractiveParameter getParameterDescriptor(IServiceCall call, String parameter) {
		InteractiveParameter p = new InteractiveParameter();
		p.setId(parameter);
		// TODO
		return p;
	}

	@Override
	public Collection<InteractiveParameter> getInteractiveParameters(IComputableResource computable) {
		List<InteractiveParameter> ret = new ArrayList<>();
		if (computable.getServiceCall() != null) {
			for (String id : computable.getServiceCall().getInteractiveParameters()) {
				ret.add(getParameterDescriptor(computable.getServiceCall(), id));
			}
		}
		return ret;
	}

}
