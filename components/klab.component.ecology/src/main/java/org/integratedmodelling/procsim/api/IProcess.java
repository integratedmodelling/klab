package org.integratedmodelling.procsim.api;

public interface IProcess extends IModelObject {

	ISchedule getSchedule();

	void run();
}
