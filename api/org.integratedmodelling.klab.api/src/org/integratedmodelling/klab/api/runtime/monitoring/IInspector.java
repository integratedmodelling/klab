package org.integratedmodelling.klab.api.runtime.monitoring;

/**
 * The monitor may carry an inspector which, if present, is notified of a
 * variety of possible situations and events useful for debugging and profiling.
 * The inspector may be activated/deactivated and programmed from k.Actors or
 * the CLI.
 * 
 * @author Ferd
 *
 */
public interface IInspector {

	// the context of the recording
	public enum Asset {
		MODEL,
		RESOURCE,
		STATE_SLICE
	}
	
	// what to record
	public enum Metric {
		
	}
	
	// when to record
	public enum Event {
		CREATION,
		START,
		FINISH
	}
	
	
}
