package org.integratedmodelling.klab.extensions.groovy.model
//
//import org.integratedmodelling.klab.api.observations.scale.IExtent
//import org.integratedmodelling.klab.api.observations.scale.time.ITime
//import org.integratedmodelling.klab.common.LogicalConnector
//import org.integratedmodelling.klab.engine.runtime.code.groovy.Wrapper
//
//class Time extends Extent<ITime> {
//
//	Time(String id, Binding binding) {
//		super(id, binding);
//	}
//
//	Time(ITime time, Binding binding) {
//		super(time, binding);
//	}
//
//	def or(Object e) {
//		return new Time(unwrap().merge(e instanceof Wrapper ? (IExtent)((Wrapper)e).unwrap() : (IExtent)e, LogicalConnector.UNION), binding);
//	}
//
//	def and(Object e) {
//		return new Time(unwrap().merge(e instanceof Wrapper ? (IExtent)((Wrapper)e).unwrap() : (IExtent)e, LogicalConnector.INTERSECTION), binding);
//	}
//
//	public String toString() {
//		return unwrap().describe();
//	}
//}
