package org.integratedmodelling.geoprocessing;

import org.hortonmachine.gears.libs.monitor.IHMProgressMonitor;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.Engine.Monitor;

public class TaskMonitor implements IHMProgressMonitor {

    IMonitor       monitor;
    private String name;
    public int errors = 0;

    public TaskMonitor(IMonitor monitor) {
        this.monitor = monitor;
    }

	@Override
	public <T> T adapt(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beginTask(String arg0, int arg1) {
		monitor.info(name + ": starting " + arg0);
	}

	@Override
	public void done() {
		monitor.info(name + " algorithm finished.");
	}

	@Override
	public void errorMessage(String arg0) {
		monitor.error(arg0);
		errors ++;
	}

	@Override
	public void exceptionThrown(String arg0) {
		monitor.error(arg0);
		errors ++;
	}

	@Override
	public void internalWorked(double arg0) {
		monitor.info("progress: " + arg0);
	}

	@Override
	public boolean isCanceled() {
		return monitor.isInterrupted();
	}

	@Override
	public void message(String arg0) {
		monitor.info(arg0);
	}

	@Override
	public void onModuleExit() {
	}

	@Override
	public void setCanceled(boolean arg0) {
		if (arg0) {
			((Monitor)monitor).interrupt();
		}
	}

	@Override
	public void setTaskName(String arg0) {
		this.name = arg0;
	}

	@Override
	public void subTask(String arg0) {
		monitor.info(name + " entering phase " + arg0);
	}

	@Override
	public void worked(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
