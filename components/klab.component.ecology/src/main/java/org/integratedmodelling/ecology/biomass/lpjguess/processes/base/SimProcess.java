package org.integratedmodelling.ecology.biomass.lpjguess.processes.base;

import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IProcess;
import org.integratedmodelling.procsim.api.ISchedule;

public abstract class SimProcess implements IProcess {

	protected IConfiguration _configuration;

	protected SimProcess(IConfiguration configuration) {
		_configuration = configuration;
	}

	@Override
	public ISchedule getSchedule() {
		return _configuration.getSchedule();
	}

	@Override
	public IConfiguration getConfiguration() {
		return _configuration;
	}

	/**
	 * Each subclass defines this one. The main driver will call it only at
	 * appropriate time points based on the interfaces implemented.
	 *
	 * @param patch
	 */
	protected abstract void process(Patch patch);

	public final void run() {

		/*
		 * do the proper thing based on the temporal resolution implemented by
		 * this specific process.
		 */
	}

}
