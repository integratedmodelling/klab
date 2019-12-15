package nl.alterra.shared.utils.log;

import java.util.Arrays;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class Log {

	static IMonitor monitor;
	
	public static void setMonitor(IMonitor monitor) {
		Log.monitor = monitor;
	}
	
	public static void putTarget(Object...objects) {
		System.out.println("TODO Log::putTargets(" + Arrays.toString(objects) + ")");
	}

	public static void log(Object...objects) {
		if (monitor != null) {
			monitor.info(objects);
		}
	}

}
