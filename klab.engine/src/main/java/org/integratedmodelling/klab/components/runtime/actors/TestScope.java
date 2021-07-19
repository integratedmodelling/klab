package org.integratedmodelling.klab.components.runtime.actors;

/**
 * Additional scope for actions in test scripts.
 * 
 * @author Ferd
 *
 */
public class TestScope {
	/*
	 * match for the expected fire, if any
	 */
	public Object expect = null;

	public void onException(Throwable t) {
		// TODO Auto-generated method stub
		System.out.println("HAHAHA");
	}
}
