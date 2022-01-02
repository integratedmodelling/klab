package org.integratedmodelling.klab.documentation.style;

import java.util.Map;

import org.integratedmodelling.kim.api.IKimSymbolDefinition;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.Parameters;

public class StyleDefinition extends Parameters<Object> {

	public StyleDefinition(Object definition, IKimSymbolDefinition statement, INamespace namespace, IMonitor monitor) {	
		if (definition instanceof Map) {
			
			if (((Map<?,?>)definition).containsKey("inherit")) {
				Object inherit = ((Map<?,?>)definition).get("inherit");
				if (inherit instanceof Map) {
					this.putAll((Map<?,?>)inherit);
				}
			}
			
			this.putAll((Map<?,?>)definition);
			this.remove("inherit");
		}
	}

}
