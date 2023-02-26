package org.integratedmodelling.klab.api.lang.kim;

import java.io.Serializable;

/**
 * Just a wrapper for some code and an optional language identifier. Used
 * explicitly only where code must be distinguished from other string values,
 * such as in classifiers.
 * 
 * @author Ferd
 *
 */
public interface KKimExpression extends Serializable {

	String getCode();
	
	String getLanguage();

	/**
	 * If true, this has been parsed from an expression introduced by #, which forces the
	 * evaluation to scalar.
	 * 
	 * @return
	 */
	boolean isForcedScalar();

	/**
	 * Return the reconstructed k.IM expression for inclusion in source code.
	 * 
	 * @return
	 */
	String getSourceCode();
}
