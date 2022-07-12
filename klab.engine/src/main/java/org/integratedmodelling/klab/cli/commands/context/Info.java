package org.integratedmodelling.klab.cli.commands.context;

import java.util.Map;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.localstorage.impl.AbstractAdaptiveStorage;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.scale.Scale;

public class Info implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

        String ret = "";

        if (call.getParameters().containsKey("arguments")) { 
	        for (Object urn : call.getParameters().get("arguments", java.util.List.class)) { 
	            IKimObject o = Resources.INSTANCE.getModelObject(urn.toString());
	            if (o instanceof IAcknowledgement) {
	                /*
	                 * build a scale and report it as a geometry
	                 */
	                IScale scale = Scale.create(((IAcknowledgement)o).getContextualization().getExtents(session.getMonitor()));
	                ret += (ret.isEmpty() ? "" : "\n") + ((Scale)scale).asGeometry();
	            } else {
	                ret = "Unsupported parameters. Exiting.";
	            }
	        }
	        return ret;
	    }
	    
		ISubject ctx = session.getState().getCurrentContext();
		if (ctx != null) {
			Map<IObservedConcept, IObservation> catalog = ((IRuntimeScope)((Observation)ctx).getScope()).getCatalog();
			for (IObservedConcept c : catalog.keySet()) {
				IObservation obs = catalog.get(c);
				ret += c + ":\n";
				ret += dump(obs, 0);
			}
		} else {
			ret += "No current context";
		}
		return ret;
	}

	private String dump(IObservation obs, int indent) {
		String ret = "";
		// TODO remaining cases
		if (obs instanceof State) {
			State state = (State)obs;
			if (state.getStorage() instanceof AbstractAdaptiveStorage) {
				AbstractAdaptiveStorage<?> storage = (AbstractAdaptiveStorage<?>) state.getStorage();
				ret += storage.getInfo(indent + 3);
			}
		}
		return ret;
	}

}
