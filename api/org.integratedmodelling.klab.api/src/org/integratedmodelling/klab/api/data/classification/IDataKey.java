package org.integratedmodelling.klab.api.data.classification;

import java.util.List;

import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.rest.Histogram;
import org.integratedmodelling.klab.utils.Pair;

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
	 * If the datakey indexes concepts, return the list of concepts in order of
	 * rank. Otherwise return null (not an empty list).
	 * 
	 * @return
	 */
	List<IConcept> getConcepts();

	/**
	 * Get the value corresponding to the result of reverseLookup() for that object.
	 * 
	 * @param index
	 * @return the original object
	 */
	public Object lookup(int index);

	/**
	 * Get value/label pairs
	 */
	List<Pair<Integer, String>> getAllValues();

	/**
	 * True if the keyed categories reflect an ordering.
	 * 
	 * @return true if ordered.
	 */
	boolean isOrdered();

	/**
	 * Get a list of the objects (not their labels) in the key, in a deserializable
	 * form so that the key can be reconstructed from it. In all current
	 * applications, this will mean the declarations of concepts in order of rank.
	 * 
	 * @return
	 */
	List<String> getSerializedObjects();

	/**
	 * Ensure that the key includes the passed value, which should be compatible in
	 * type with those already present. If the value is compatible with the key type,
	 * translate it to the state type and return it.
	 * 
	 * @param value
	 */
	Object include(Object value);

	/**
	 * The datakey may be a proxy for a codelist, which is returned here.
	 * 
	 * @return
	 */
	IAuthority getAuthority();


}
