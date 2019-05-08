package org.integratedmodelling.klab.components.geospace.processing.viewshed;

class ViewEvent {

	final static int ENTERING = 0;
	final static int CENTER = 0;
	final static int EXITING = 0;

	long x;
	long y;

	/*
	 * [0] = entering [1] = center [2] = exiting
	 */
	double[] elevations;

	/*
	 * one of the three above
	 */
	short type;
}