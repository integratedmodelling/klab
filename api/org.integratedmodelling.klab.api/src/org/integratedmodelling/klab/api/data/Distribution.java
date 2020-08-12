package org.integratedmodelling.klab.api.data;

/**
 * The type of redistribution needed to make a contextualized value of an
 * observable match the context of another.
 * 
 * @author Ferd
 *
 */
public enum Distribution {
	NONE, LENGTH, AREA, VOLUME, TEMPORAL, AREA_TEMPORAL, LENGTH_TEMPORAL, VOLUME_TEMPORAL,
}
