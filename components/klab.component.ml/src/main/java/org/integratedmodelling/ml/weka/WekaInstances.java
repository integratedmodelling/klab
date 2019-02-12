package org.integratedmodelling.ml.weka;

import java.io.File;
import java.io.IOException;

import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;

import weka.core.Instances;

public class WekaInstances {

	private Instances instances;

	WekaInstances(IModel model, IRuntimeContext context) {

	}

	/**
	 * Build if necessary and return a WEKA instances set from the model
	 * configuration and the context.
	 * 
	 * @return
	 */
	public Instances getInstances() {
		if (instances == null) {
			build();
		}
		return instances;
	}

	private void build() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Export to an AIRFF file.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void export(File file) throws IOException {
		
	}

}
