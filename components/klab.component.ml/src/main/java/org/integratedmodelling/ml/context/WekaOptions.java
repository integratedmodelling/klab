package org.integratedmodelling.ml.context;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Extensions;

import weka.classifiers.Classifier;
import weka.core.Utils;

public class WekaOptions {
	
	private String[] options;
	private String commandLine;
	
	public WekaOptions(Class<? extends Classifier> cls, IParameters<String> parameters) {
		
		IPrototype prototype = parameters.get(Extensions.PROTOTYPE_PARAMETER, IPrototype.class);
		if (prototype == null) {
			throw new IllegalStateException("Weka: internal error: no prototype in function call");
		}
		
		this.commandLine = Kim.INSTANCE.createCommandLine(parameters, prototype, "",  (call) -> {
			IPrototype proto = Extensions.INSTANCE.getPrototype(call.getName());
			String options = Kim.INSTANCE.createCommandLine(call.getParameters(), proto, proto.getExecutorClass().getCanonicalName()  + " --");
			return options;
		});

		try {
			this.options = Utils.splitOptions(commandLine);
		} catch (Exception e) {
			throw new IllegalStateException("Weka: error parsing reconstructed command line '" + commandLine + "'");
		}
	}
	
	public String[] getWekaOptions() {
		return options;
	}
	
	@Override
	public String toString() {
		return this.commandLine;
	}
	
}
