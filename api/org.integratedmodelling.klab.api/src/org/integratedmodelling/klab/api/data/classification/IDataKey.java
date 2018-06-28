package org.integratedmodelling.klab.api.data.classification;

import java.util.List;

/**
 * Tag interface for an object that supplements the semantics of a state's
 * values by providing an indexed key for interpretation. This could be a
 * classification or a lookup table.
 * 
 * @author Ferd
 *
 */
public interface IDataKey {

	/**
	 * The total amount of categories, rows or other mappings used in this key.
	 * 
	 * @return
	 */
	int size();

	/**
	 * Return the numeric index of the passed value, which must be part of the
	 * classification. The numeric value of the index must reflect semantics
	 * accurately when the values reflect a known ordering.
	 * 
	 * @param value
	 * @return the numeric rank, or -1 if the concept does not correspond to a
	 *         classifier
	 */
	public int reverseLookup(Object value);

	/**
	 * Get sound labels for each of the categories in an order matching the index
	 * {@link #reverseLookup(Object)} returns.
	 * 
	 * @return
	 */
	List<String> getLabels();

	/**
	 * True if the keyed categories reflect an ordering. 
	 * 
	 * @return true if ordered.
	 */
	boolean isOrdered();

}
