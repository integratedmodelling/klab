package org.integratedmodelling.klab.api.knowledge.observation.scale;

import java.util.Collection;

import org.integratedmodelling.klab.api.collections.impl.Pair;

/**
 * A {@code IScaleMediator} assists the mediation of a value from an extent to
 * another by establishing a mediation strategy consisting of a set of offsets
 * and weights.
 * <p>
 * A <strong>conformant</strong> mediator is one where the mapping is
 * offset-to-offset, i.e. no aggregation or distribution is necessary. This for
 * example applies to grid-to-grid mediators where one grid is a simple subset
 * of the other. Conformant mediators should be produced whenever possible, for
 * example as a result of applying a scale resulting from a merge() of a subset
 * on the original.
 * <p>
 * Each mediator handles one-way mapping between one extent and another. Two-way
 * mediation uses two sets of mediators.
 * <p>
 * 
 * @author ferdinando.villa
 *
 */
public interface KScaleMediator {

	/**
	 * A conformant mediator maps one offset to another, with no aggregation
	 * required.
	 * 
	 * @return true if conformant
	 */
	boolean isConformant();

	/**
	 * If {@link #isConformant()} returned true, use this one to map an offset of
	 * the original extent into the correspondent offset of the mediated one.
	 * 
	 * @param originalOffset
	 * @return the mediated offset
	 */
	long mapConformant(long originalOffset);

	/**
	 * For non-conformant mediators, map the passed offset of the original extent to
	 * the offset(s) of the mediated, returning a weight (0 &le; w &le; 1) for any
	 * partial match in specific offsets. Implementations should ensure that
	 * mediations where the weights are insignificant to aggregation/propagation do
	 * not waste time in computing them and return them as 1.0.
	 * 
	 * @param originalOffset
	 * @return the mediation strategy.
	 */
	Collection<Pair<Long, Double>> map(long originalOffset);

}
