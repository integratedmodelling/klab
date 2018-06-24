package org.integratedmodelling.kim.api;

import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.utils.Range;

public interface IKimObservable extends IKimStatement {

	/**
	 * 
	 * @return the main concept
	 */
	IKimConcept getMain();

	/**
	 * 
	 * @return the 'down to' concept
	 */
	IKimConcept getDownTo();

	/**
	 * 
	 * @return the 'by' concept
	 */
	IKimConcept getBy();

	/**
	 * 
	 * @return the range
	 */
	Range getRange();

	/**
	 * 
	 * @return the unit
	 */
	String getUnit();

	/**
	 * 
	 * @return the currency
	 */
	String getCurrency();

	/**
	 * 
	 * @return the 'named' name
	 */
	String getFormalName();

	/**
	 * 
	 * @return the literal value
	 */
	Object getValue();

	/**
	 * 
	 * @return true if abstract
	 */
	boolean isAbstractObservable();

	/**
	 * If the observable specification had an identifier (rather than a literal
	 * value) before an 'as' clause introducing the semantics, this will return true
	 * and the {@link #getValue()} method will return a string with the identifier's
	 * value. The interpretation of the identifier is context-dependent as it may
	 * refer to a value previously defined in a 'define' statement, or to an
	 * attribute to be looked up in a referenced resource.
	 * 
	 * @return true if identified by an attribute to be resolved
	 */
	boolean hasAttributeIdentifier();

	/**
	 * True if the 'optional' clause has been passed.
	 * 
	 * @return true if optional
	 */
	boolean isOptional();

	/**
	 * If the observable is tied to a predefined model, return the model fully
	 * qualified name here.
	 * 
	 * @return
	 */
	String getModelReference();

	/**
	 * If this returns anything other than null, we are looking at the observable of
	 * a non-semantic model, and implementations will need to handle this properly,
	 * for example creating a recognizable, unique concept of the returned type
	 * using the name set into modelReference, or using completely independent
	 * logics.
	 * 
	 * @return
	 */
	IArtifact.Type getNonSemanticType();

}
