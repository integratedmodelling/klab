package org.integratedmodelling.klab.engine.debugger;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.monitoring.IInspector;

public class Inspector implements IInspector {

	class Trigger {
		Asset asset;
		Metric metric;
		Event event;
		// the specific object, if any, that will trigger this - could be a model
		// object, a URN, a state value
		Object self;
		// condition to trigger: if not empty, the fields of the trigger will be
		// accessible with their names
		IExpression condition;
	}

	List<Trigger> triggers = new ArrayList<>();
	
	@Override
	public void setTrigger(Object... triggerArguments) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trigger(Object... triggerArguments) {
		// TODO Auto-generated method stub
		
	}
}
